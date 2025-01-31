package de.starwit;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import de.starwit.aic.model.Info;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-04-01T15:47:00.058415289+02:00[Europe/Berlin]")
@Controller
@RequestMapping("${openapi.aICockpitTransparency.base-path:/v0}")
public class InfoApiController implements InfoApi {

    private final NativeWebRequest request;

    @Autowired
    public InfoApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<List<Info>> getInfo() {
        ArrayList<Info> result = new ArrayList<>();
        Info i =  new Info();
        i.setApiVersion("0.0.1");
        i.setGenerationDate(OffsetDateTime.parse("2024-04-01T12:00:00+01:00"));
        i.setSystemDescription("This is the transparency API for Starwit's Traffic management system.");
        result.add(i);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
