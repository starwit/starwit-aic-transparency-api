package de.starwit.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.starwit.aic.model.Module;
import jakarta.annotation.PostConstruct;

@Service
public class InitializationService {
    Logger log = LoggerFactory.getLogger(InitializationService.class);

    @Autowired
    CockpitService cockpitService;

    @Autowired
    ReportGenerationService reportService;

    /**
     * This is the URI under which this API will deliver sboms, if hosted here.
     */
    @Value("${app.service_uri}")
    private String serviceUri;

    @Value("${scenario.setup:false}")
    private boolean setupScenario = true;

    @Value("${scenario.import.location:import-data/modules.json}")
    private String scenarioImportLocation;

    @Autowired
    ObjectMapper mapper;

    @PostConstruct
    public void init() {
        if (setupScenario) {
            log.info("Setting up scenario data from env vars");
            log.info("Load scenario data");
            loadScenarioData();
        } else {
            log.info("start empty transparency API, modules must register!");
        }
    }

    private void loadScenarioData() {
        File file = new File(scenarioImportLocation);
        if (file.exists()) {
            log.info("Loading scenario data from {}", file.getAbsolutePath());
            try {
                var mods = mapper.readValue(file, new TypeReference<List<Module>>() {
                });
                sendModuleDataToCockpit(mods);
            } catch (IOException e) {
                log.error("Error reading scenario data" + e.getMessage());
            }

        } else {
            log.error("Scenario data path does not exist " + file.getAbsolutePath());
        }
    }

    private void sendModuleDataToCockpit(List<Module> mods) {
        for (Module module : mods) {
            var validation = cockpitService.validateModuleData(module);
            log.info("Validation result : " + validation.toString());
            if (cockpitService.checkIfModuleExists(module.getName())) {
                log.info("Module with name {} already exists or cockpit is unreachable", module.getName());
                continue;
            } else {
                log.info("Registering module {}", module.getName());
                cockpitService.sendModuleDataToCockpit(module);
                reportService.createReports(module);
            }
        }
    }
}
