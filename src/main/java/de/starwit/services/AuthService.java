package de.starwit.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthService {

    Logger log = LoggerFactory.getLogger(AuthService.class);

    @Value("${cockpit.auth.client_id:aicockpit}")
    private String clientId;

    @Value("${cockpit.auth.username}")
    private String username;

    @Value("${cockpit.auth.password}")
    private String password;
    
    @Value("${cockpit.auth.url}")
    private String authUrl;

    private LocalDateTime tokenTimeStamp;
    
    private String token = null;

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ObjectMapper mapper;

    public void getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("realm", "default");
        map.add("client_id", clientId);
        map.add("grant_type", "password"); 
        map.add("username", username);
        map.add("password", password);        

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        HttpEntity<String> response;
        try {
            response = restTemplate.postForEntity(authUrl, request, String.class);
        } catch (HttpClientErrorException e) {
            log.error("Can't get access token for user " + username + " with error: " + e.getMessage());
            token = null;
            return;
        }

        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            AuthTokenResponse authResponse = mapper.readValue(response.getBody(), AuthTokenResponse.class);
            token = authResponse.getAccessToken();
            tokenTimeStamp = LocalDateTime.now();
            log.debug("Token succesfully loaded");
        } catch (JsonProcessingException e) {
            log.error("Can't parse auth response " + e.getMessage());
        }
    }

    private void checkIfTokenIsStillValid() {
        if(token == null) {
            getAccessToken();
        } else {
            LocalDateTime now = LocalDateTime.now();
            long diff = ChronoUnit.MILLIS.between(tokenTimeStamp, now);
            log.debug("Token age " + diff);
            // token is too old, try again to aqcuire one
            if(diff > 2590000) {
                log.debug("Token too old, get a new one");
                getAccessToken();
            }
        }
    }

    public String getToken() {
        checkIfTokenIsStillValid();
        return token;
    }    
}
