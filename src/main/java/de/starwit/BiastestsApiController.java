package de.starwit;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import de.starwit.aicockpit.BiasTest;
import de.starwit.services.BiastestsService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("${openapi.aICockpitTransparency.base-path:/v0}")
public class BiastestsApiController implements BiastestsApi {

    static final Logger LOG = LoggerFactory.getLogger(BiastestsApiController.class);

    @Autowired
    private BiastestsService biastestsService;
    
    private final NativeWebRequest request;

    @Autowired
    public BiastestsApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Void> createBiasTest(@Valid BiasTest biasTest) {
        biastestsService.add(biasTest);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BiasTest>> getAllBiasTests() {
        List<BiasTest> result = biastestsService.findAll();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateBiasTest(Integer id, @Valid BiasTest biasTest) {
        biastestsService.saveOrUpdate(biasTest);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }

}
