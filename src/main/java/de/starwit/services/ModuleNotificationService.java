package de.starwit.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import de.starwit.aic.model.Module;

@Service
public class ModuleNotificationService {

    Logger log = LoggerFactory.getLogger(ModuleNotificationService.class);

    @Value("${cockpit.hostname}")
    private String cockpitHostname;

    @Value("${cockpit.moduleapi}")
    private String moduleAPI;

    @Value("${cockpit.transparencyapi}")
    private String transparencyAPI;

    private final RestTemplate restTemplate;

    public ModuleNotificationService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Async
    public void synchModuleData(Module module) {
        try {
            log.info("Sending new module to Cockpit: " + cockpitHostname + transparencyAPI);
            ResponseEntity<String> resp = restTemplate.postForEntity(cockpitHostname + transparencyAPI, module, String.class);
            log.info(resp.getBody());
        } catch (Exception ex) {
            log.error("Failed to synch module to cockpit: " + ex.getMessage());
        }
    }

    public boolean checkIfModuleExists(String name) {
        try {
            log.info("Checking if module already exist: " + cockpitHostname + moduleAPI);
            ResponseEntity<Boolean> resp = restTemplate.getForEntity(cockpitHostname + moduleAPI + "/" + name, Boolean.class);
            log.info(resp.getStatusCode().toString());
            if (resp.getStatusCode().is2xxSuccessful()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            log.error("Failed to test if module exists: " + ex.getMessage());
        } 
        return false;
    }
}
