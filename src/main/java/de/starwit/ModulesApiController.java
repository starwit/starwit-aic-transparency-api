package de.starwit;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
import de.starwit.aic.model.ModuleSBOMLocationValue;
import jakarta.annotation.Generated;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-08-08T11:21:46.515327837+02:00[Europe/Berlin]")
@Controller
@RequestMapping("${openapi.aICockpitTransparency.base-path:/v0}")
public class ModulesApiController implements ModulesApi {

    private final NativeWebRequest request;

    private List<Module> modules = new ArrayList<>();

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

    @PostConstruct
    private void init() {
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

    @Override
    public ResponseEntity<List<Module>> getModule(Integer id) {
        ArrayList<Module> result = new ArrayList<>();
        for (Module module : modules) {
            if((long)module.getId() == id) {
                result.add(module);
            }
        }
        return new ResponseEntity<List<Module>>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Module>> getModules() {
        return new ResponseEntity<List<Module>>(modules, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateModule(Integer id, @Valid Module module) {
        return ModulesApi.super.updateModule(id, module);
    }
}
