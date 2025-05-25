package de.starwit.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.starwit.aic.model.Module;

/**
 * This service interacts with the report generation service, to get created
 * reports as files.
 */
@Service
public class ReportGenerationService {

    Logger log = LoggerFactory.getLogger(ReportGenerationService.class);

    @Value("${sbom.generator.uri:}")
    private String reportGeneratorUri;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    MinioService minioService;

    public void createReports(Module module) {
        log.info("Try to generate reports");
        var sbomLocations = module.getsBOMLocation();

        if (sbomLocations != null) {
            for (var sbomLocation : sbomLocations.keySet()) {
                String sbomUrl = sbomLocations.get(sbomLocation).getUrl();
                if (!sbomUrl.isEmpty()) {
                    log.info("Creating reports for " + sbomLocation);
                    byte[] pdfReport = loadReport(sbomUrl, "pdf");
                    byte[] excelReport = loadReport(sbomUrl, "spreadsheet");
                    minioService.uploadFile(pdfReport, "pdf", module);
                    minioService.uploadFile(excelReport, "spreadsheet", module);
                    var sbomContent = loadSbom(sbomUrl);
                    minioService.uploadFile(sbomContent.getBytes(), module, sbomLocation);
                }
            }
        }

    }

    private String loadSbom(String sbomUri) {
        log.info("Loading sbom from remote URI " + sbomUri);
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(sbomUri, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                return "";
            }
        } catch (Exception e) {
            log.error("Can't load sbom from remote URI " + e.getMessage());
            return "";
        }
    }

    private byte[] loadReport(String sbomUri, String type) {
        byte[] result = null;
        String reportRequest = createRequestBody(sbomUri);
        String apiEndpoint = getReportAPIEndpoint(type);

        try {
            log.info("Requesting report (" + type + ") from remote URI " + reportGeneratorUri);
            log.debug("Request body: " + reportRequest);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            RequestEntity<String> requestEntity = RequestEntity.post(reportGeneratorUri + apiEndpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(reportRequest);
            ResponseEntity<byte[]> response = restTemplate.exchange(requestEntity, byte[].class);

            if (response.getStatusCode().is2xxSuccessful()) {
                result = response.getBody();
            } else {
                log.info("Can't load report from remote URI " + response.getStatusCode());

            }
        } catch (Exception e) {
            log.info("Can't load report from remote URI. Are you using CyclonDX format?");
        }

        return result;
    }

    private String createRequestBody(String sbomUri) {

        sbomUri = "[\"" + sbomUri + "\"]";

        String reportRequest = "{\"sbomURI\":" + sbomUri + ",";
        reportRequest += "\"dcId\": 0,";
        reportRequest += "\"compact\": true,";
        reportRequest += "\"sbom\": []";
        reportRequest += "}";

        return reportRequest;
    }

    private String getReportAPIEndpoint(String type) {
        String apiEndpoint = "/api/report/remote";
        switch (type) {
            case "pdf":
                apiEndpoint = "/api/report/remote";
                break;
            case "spreadsheet":
                apiEndpoint = "/api/report/excel/remote";
                break;
            default:
                break;
        }
        return apiEndpoint;
    }
}
