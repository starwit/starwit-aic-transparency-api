package org.openapitools;

import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.kicockpit.model.Module;

@SpringBootTest
class OpenApiGeneratorApplicationTests {

    @Autowired
    ObjectMapper mapper;

    @Test
    void parseSampleData() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sampledata.json");
        Module[] mods =  mapper.readValue(inputStream, Module[].class);
        List<Module> modules = Arrays.asList(mods);
    }

}