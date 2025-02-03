package de.starwit.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import de.starwit.aic.model.ModuleSBOMLocationValue;

@Service
public class ModuleDataService {
    Logger log = LoggerFactory.getLogger(ModuleDataService.class);

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

    private List<Module> modules = new ArrayList<>();

    @Autowired
    ObjectMapper mapper;    

    @PostConstruct
    public void init() {
        if(setupScenario) {
            log.info("Setting up scenario data from env vars");
            if("internal".equals(scenarioImportFolder)) {
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
            Module[] mods =  mapper.readValue(inputStream, Module[].class);
            updateUris(mods);
            modules = Arrays.asList(mods);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } 
    }

    private void updateUris(Module[] mods) {
        for (Module module : mods) {
            for(String key: module.getsBOMLocation().keySet()) {
                ModuleSBOMLocationValue locValue = module.getsBOMLocation().get(key);
                var uri = locValue.getUrl();
                if(!uri.contains("http")) {
                    locValue.setUrl(serviceUri + "/" + uri);
                    module.getsBOMLocation().put(key, locValue);
                }
            }
        }
    }

    private void loadScenarioData() {
        File file = new File(scenarioImportFolder + "/" + sampleDataFileName);
        if(file.exists()){
            log.info("Loading scenario data from {}", file.getAbsolutePath());
            try{
                modules = mapper.readValue(file, new TypeReference<List<Module>>(){});
            } catch (IOException e) {
                log.error("Error reading scenario data" + e.getMessage());
            }
            
        } else {
            log.error("Scenario data path does not exist " + file.getAbsolutePath());
        }
    }

    public List<Module> getModules() {
        return modules;
    } 
}
