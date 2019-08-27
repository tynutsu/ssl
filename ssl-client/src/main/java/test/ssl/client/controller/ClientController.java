package test.ssl.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;

@RestController
public class ClientController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/test")
    public String test() {
        ResponseEntity<String> resp = restTemplate.exchange("https://localhost:8091/serve", GET, null, String.class);
        return "Status: " + resp.getStatusCode().toString() + " response: " + resp.getBody();
    }

}
