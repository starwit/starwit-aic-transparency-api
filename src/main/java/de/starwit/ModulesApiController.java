package de.starwit;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.starwit.aic.model.Module;
import de.starwit.services.ModuleDataService;
import de.starwit.services.ModuleSynchronizationService;
import de.starwit.services.ValidationFeedback;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-08T11:21:46.515327837+02:00[Europe/Berlin]")
@Controller
@RequestMapping("${openapi.aICockpitTransparency.base-path:/v0}")
public class ModulesApiController implements ModulesApi {

    Logger log = LoggerFactory.getLogger(ModulesApiController.class);

    private final NativeWebRequest request;

    @Autowired
    ModuleDataService moduleDataService;

    @Autowired
    private ModuleSynchronizationService moduleNotificationService;

    /**
     * This is the URI under which this API will deliver sboms, if hosted here.
     */
    @Value("${app.service_uri}")
    private String serviceUri;

    @Autowired
    ObjectMapper mapper;

    public ModulesApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<List<Module>> getModules() {
        return new ResponseEntity<List<Module>>(moduleNotificationService.getAllModules(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateModule(Integer id, @Valid Module module) {
        log.info("Updating module {}", id);
        return ModulesApi.super.updateModule(id, module);
    }

    @Override
    public ResponseEntity<ValidationFeedback> registerModule(@Valid Module module) {
        log.info("Registering new module");
        var moduleExist = moduleNotificationService.checkIfModuleExists(module.getName());
        log.info(module.getModel().getLastDeployment().toString());
        if (moduleExist) {
            log.info("Module with name {} already exists", module.getName());
            ValidationFeedback validation = new ValidationFeedback();
            validation.setNameTaken(true);
            return new ResponseEntity<>(validation, HttpStatus.CONFLICT);
        } else {
            log.info("Registering module {}", module.getName());
            var validation = moduleNotificationService.validateModuleData(module);
            if(validation.isValid())
            {
                moduleNotificationService.synchModuleData(module);
                return new ResponseEntity<>(validation,HttpStatus.OK);            
            }
            return new ResponseEntity<>(validation, HttpStatus.BAD_REQUEST);
        }
    }

}
