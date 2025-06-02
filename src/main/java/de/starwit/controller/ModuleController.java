package de.starwit.controller;

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
import de.starwit.dto.ValidationDto;
import de.starwit.service.CockpitService;
import de.starwit.service.ReportGenerationService;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-08T11:21:46.515327837+02:00[Europe/Berlin]")
@Controller
@RequestMapping("${openapi.aICockpitTransparency.base-path:/v0}")
public class ModuleController implements ModuleControllerInterface {

    Logger log = LoggerFactory.getLogger(ModuleController.class);

    private final NativeWebRequest request;

    @Autowired
    private CockpitService moduleNotificationService;

    @Autowired
    ReportGenerationService reportService;

    /**
     * This is the URI under which this API will deliver sboms, if hosted here.
     */
    @Value("${app.service_uri}")
    private String serviceUri;

    @Autowired
    ObjectMapper mapper;

    public ModuleController(NativeWebRequest request) {
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
        return ModuleControllerInterface.super.updateModule(id, module);
    }

    @Override
    public ResponseEntity<ValidationDto> registerModule(@Valid Module module) {
        log.info("Registering new module");
        var moduleExist = moduleNotificationService.checkIfModuleExists(module.getName());
        if (moduleExist) {
            log.info("Module with name {} already exists", module.getName());
            ValidationDto validation = new ValidationDto();
            validation.setNameTaken(true);
            return new ResponseEntity<>(validation, HttpStatus.CONFLICT);
        } else {
            log.info("Registering module {}", module.getName());
            var validation = moduleNotificationService.validateModuleData(module);
            if (validation.isValid()) {
                moduleNotificationService.sendModuleDataToCockpit(module);
                reportService.createReports(module);
                return new ResponseEntity<>(validation, HttpStatus.OK);
            }
            return new ResponseEntity<>(validation, HttpStatus.BAD_REQUEST);
        }
    }

}
