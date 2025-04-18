package de.starwit.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import de.starwit.aic.model.Module;

@Service
public class ModuleNotificationService {

    Logger log = LoggerFactory.getLogger(ModuleNotificationService.class);

    @Value("${cockpit.url}")
    private String cockpitURL;

    private final RestTemplate restTemplate;

    public ModuleNotificationService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Async
    public void notifyExternalSystem(Module module) {
        try {
            // convert to cockpit data structure
            restTemplate.postForEntity(cockpitURL, module, Void.class);
        } catch (Exception ex) {
            log.error("Failed to notify external system: " + ex.getMessage());
        }
    }
}
