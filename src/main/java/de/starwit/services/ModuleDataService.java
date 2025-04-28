package de.starwit.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.starwit.aic.model.ModuleSBOMLocationValue;
import jakarta.annotation.PostConstruct;

import de.starwit.aic.model.Module;

@Service
public class ModuleDataService {
    Logger log = LoggerFactory.getLogger(ModuleDataService.class);

    @Autowired
    ModuleSynchronizationService moduleSynchService;

    /**
     * This is the URI under which this API will deliver sboms, if hosted here.
     */
    @Value("${app.service_uri}")
    private String serviceUri;

    @Value("${scenario.setup:false}")
    private boolean setupScenario = true;

    @Value("${scenario.importFolder:internal}")
    private String scenarioImportFolder;

    private String sampleDataFileName = "moduledata.json";

    @Autowired
    ObjectMapper mapper;

    @PostConstruct
    public void init() {
        if (setupScenario) {
            log.info("Setting up scenario data from env vars");
            if ("internal".equals(scenarioImportFolder)) {
                log.info("Load pre-packaged module data, check if this is intended!");
                loadPrePackagedDemoData();
            } else {
                log.info("Load scenario data");
                loadScenarioData();
            }
        } else {
            log.info("start empty transparency API, modules must register!");
        }
    }

    private void loadPrePackagedDemoData() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sampledata.json");
        try {
            Module[] mods = mapper.readValue(inputStream, Module[].class);
            updateUris(mods);
            for (Module module : mods) {
                var validation = moduleSynchService.validateModuleData(module);
                log.info("Validation result : " + validation.toString());
                if (moduleSynchService.checkIfModuleExists(module.getName())) {
                    log.info("Module with name {} already exists or cockpit is unreachable", module.getName());
                    continue;
                } else {
                    log.info("Registering module {}", module.getName());
                    moduleSynchService.synchModuleData(module);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void updateUris(Module[] mods) {
        for (Module module : mods) {
            for (String key : module.getsBOMLocation().keySet()) {
                ModuleSBOMLocationValue locValue = module.getsBOMLocation().get(key);
                var uri = locValue.getUrl();
                if (!uri.contains("http")) {
                    locValue.setUrl(serviceUri + "/" + uri);
                    module.getsBOMLocation().put(key, locValue);
                }
            }
        }
    }

    private void loadScenarioData() {
        File file = new File(scenarioImportFolder + "/" + sampleDataFileName);
        if (file.exists()) {
            log.info("Loading scenario data from {}", file.getAbsolutePath());
            try {
                var mods = mapper.readValue(file, new TypeReference<List<Module>>() {
                });
                for (Module module : mods) {
                    moduleSynchService.synchModuleData(module);
                }
            } catch (IOException e) {
                log.error("Error reading scenario data" + e.getMessage());
            }

        } else {
            log.error("Scenario data path does not exist " + file.getAbsolutePath());
        }
    }
}
