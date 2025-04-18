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
            modules = new ArrayList<>(Arrays.asList(mods));
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
                var mods = mapper.readValue(file, new TypeReference<List<Module>>(){});
                modules = new ArrayList<>(mods);
            } catch (IOException e) {
                log.error("Error reading scenario data" + e.getMessage());
            }
            
        } else {
            log.error("Scenario data path does not exist " + file.getAbsolutePath());
        }
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

        //check if successor/submodule relations work
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

    public List<Module> getModules() {
        return modules;
    } 

    public Module findModuleByName(String name) {
        for (Module module : modules) {
            if(name.equals(module.getName())) {
                return module;
            }
        }
        return null;
    }    
}
