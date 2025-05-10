package de.starwit.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import de.starwit.aic.model.Module;

@Service
public class ModuleSynchronizationService {

    Logger log = LoggerFactory.getLogger(ModuleSynchronizationService.class);

    @Value("${cockpit.hostname}")
    private String cockpitHostname;

    @Value("${cockpit.moduleapi}")
    private String moduleAPI;

    @Value("${cockpit.aicapi}")
    private String aicAPI;

    @Value("${cockpit.auth.enabled:false}")
    private boolean authEnabled;    

    private final RestTemplate restTemplate;

    @Autowired
    AuthService authService;

    public ModuleSynchronizationService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Async
    public void synchModuleData(Module module) {
        try {
            log.info("Sending new module to Cockpit: " + cockpitHostname + aicAPI);
            log.debug(module.toString());
            String response = "";
            
            if(authEnabled) {
                HttpEntity<Module> httpEntity = prepareHTTPEntity(module);
                ResponseEntity<String> resp = restTemplate.postForEntity(cockpitHostname + aicAPI, httpEntity,
                        String.class);
                response =  resp.getBody();
            } else {
                ResponseEntity<String> resp = restTemplate.postForEntity(cockpitHostname + aicAPI, module,
                        String.class);
                response =  resp.getBody();
            }
            log.info(response);
        } catch (Exception ex) {
            log.error("Failed to synch module to cockpit: " + ex.getMessage());
        }
    }

    public List<Module> getAllModules() {
        List<Module> modules = new LinkedList<>();
        try {
            log.debug("Loading existing modules from cockpit: " + cockpitHostname + aicAPI);
            ResponseEntity<Module[]> resp;
            if(authEnabled) {
                HttpEntity<String> httpEntity = prepareHTTPEntity("");
                resp = restTemplate.exchange(cockpitHostname + aicAPI, HttpMethod.GET, httpEntity, Module[].class);
            } else {
                resp = restTemplate.getForEntity(cockpitHostname + aicAPI, Module[].class);
            }
            modules = List.of(resp.getBody());
        } catch (Exception ex) {
            log.error("Failed to load existing modules from cockpit: " + ex.getMessage());
        }

        return modules;
    }

    public boolean checkIfModuleExists(String name) {
        try {
            log.info("Checking if module already exist: " + cockpitHostname + moduleAPI);
            var uri = UriComponentsBuilder.fromUriString(cockpitHostname + moduleAPI + "/byname/{name}")
                .buildAndExpand(name)
                .toUri();
            log.debug(uri.toString());
            ResponseEntity<Module> resp;
            if(authEnabled) {
                HttpEntity<String> httpEntity = prepareHTTPEntity("");
                resp = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Module.class);
            } else {
                resp = restTemplate.getForEntity(uri, Module.class);
            }
            
            log.info("Result of existence test " + resp.getStatusCode().toString());
            if (resp.getStatusCode().is2xxSuccessful()) {
                return true;
            } else {
                return false;
            }
        } catch (HttpClientErrorException ex) {
            log.info("Existence test for module resulted in response code " + ex.getStatusCode());
            if (ex.getStatusCode().value() == 404) {
                log.info("Module does not exist yet");
                return false;
            }
            return true;
        }
        catch (Exception ex) {
            log.error("Failed to test if module exists: " + ex.getMessage());
            return true;
        }
    }

    private <T> HttpEntity<T> prepareHTTPEntity(T entity) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer " + authService.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> httpEntity = new HttpEntity<>(entity, headers);
        return httpEntity;
    }

    public ValidationFeedback validateModuleData(Module module) {
        ValidationFeedback feedback = new ValidationFeedback();
        feedback.setValid(true);

        for (String sbom : module.getsBOMLocation().keySet()) {
            try {
                if(module.getsBOMLocation().get(sbom) == null) {
                    feedback.getInvalidUris().add(sbom);
                    feedback.setValid(false);
                    continue;
                }
                URL sbomLocation = new URI(module.getsBOMLocation().get(sbom).getUrl()).toURL();
                if( !checksURLAvailability(sbomLocation)) {
                    feedback.getUnreachableUris().add(sbomLocation);
                    feedback.setValid(false);
                }
            } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
                log.debug("Invalid URI for sbom {} of module {}", sbom, module.getName());
                feedback.getInvalidUris().add(sbom + ": " + module.getsBOMLocation().get(sbom).getUrl());
                feedback.setValid(false);
            }
        }

        if(module.getUseAI()) {
            feedback.setHasIncompleteModelData(false);
            if(module.getModel() == null || module.getModel().getModelLink() == null) {
                log.debug("No model data for module {}", module.getName());
                feedback.setValid(false);
            } 

            if(module.getModel().getModelLink() == null) {
                log.debug("No model link for module {}", module.getName());
                feedback.setHasIncompleteModelData(true);
            } else {
                try {
                    URL modelLink = module.getModel().getModelLink().toURL();
                    if(!checksURLAvailability(modelLink)) {
                        feedback.getUnreachableUris().add(modelLink);
                        feedback.setHasIncompleteModelData(true);
                    }
                    URL trainingDataLink = module.getModel().getModelLink().toURL();
                    if(module.getModel().getPublicTrainingData() && !checksURLAvailability(trainingDataLink)) {
                        feedback.getUnreachableUris().add(trainingDataLink);
                        feedback.setHasIncompleteModelData(true);
                    }
                } catch (MalformedURLException e) {
                    log.info("Invalid model link for module {}", module.getName());
                    feedback.setHasIncompleteModelData(true);
                    feedback.getInvalidUris().add(module.getModel().getModelLink().toString());
                }              
            }
        }

        //check if successor/relations work
        return feedback;
    }

    private boolean checksURLAvailability(URL url) {
        try {
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            if(huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return true;
            }
        } catch (IOException e) {
            log.debug("Could not connect to {}", url.toString());
            return false;
        }
        return false;
    }    
}
