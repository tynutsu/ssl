package test.ssl.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    @RequestMapping("serve")
    public String serve() {
        return "Hello TSL";
    }
}
