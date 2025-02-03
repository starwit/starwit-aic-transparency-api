package de.starwit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-08T11:21:46.515327837+02:00[Europe/Berlin]")
@Controller
@RequestMapping("${openapi.aICockpitTransparency.base-path:/v0}")
public class ModulesApiController implements ModulesApi {

    private final NativeWebRequest request;

    @Autowired
    ModuleDataService moduleDataService;

    /**
     * This is the URI under which this API will deliver sboms, if hosted here.
     */
    @Value("${app.service_uri}")
    private String serviceUri;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    public ModulesApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }    

    @Override
    public ResponseEntity<List<Module>> getModule(Integer id) {
        ArrayList<Module> result = new ArrayList<>();
        for (Module module : moduleDataService.getModules()) {
            if((long)module.getId() == id) {
                result.add(module);
            }
        }
        return new ResponseEntity<List<Module>>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Module>> getModules() {
        return new ResponseEntity<List<Module>>(moduleDataService.getModules(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateModule(Integer id, @Valid Module module) {
        return ModulesApi.super.updateModule(id, module);
    }
}
