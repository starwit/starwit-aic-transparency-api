package de.starwit.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import de.starwit.aicockpit.BiasTest;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@ApplicationScope
@Service
public class BiastestsService {

    static final Logger LOG = LoggerFactory.getLogger(BiastestsService.class);

    private List<BiasTest> biastests;

    @PostConstruct
    private void setup() {
        biastests = new ArrayList<>();
        BiasTest b1 = new BiasTest();
        b1.setId((long) 1);
        b1.setName("BiasTest1");
        biastests.add(b1);

        b1 = new BiasTest();
        b1.setId((long) 2);
        b1.setName("BiasTest2");
        biastests.add(b1);

        b1 = new BiasTest();
        b1.setId((long) 3);
        b1.setName("BiasTest3");
        biastests.add(b1);        

    }

    public void add(@Valid BiasTest biasTest) {
        biastests.add(biasTest);
    }

    public List<BiasTest> findAll() {
        return biastests;
    }

    public void saveOrUpdate(@Valid BiasTest biasTest) {
        for (int i = 0; i < biastests.size(); i++) {
            BiasTest b = biastests.get(i);
            if(b.getId() == biasTest.getId()) {
                biastests.set(i, biasTest);
                return;
            }
        }
        biastests.add(biasTest);        
    }
    


}
