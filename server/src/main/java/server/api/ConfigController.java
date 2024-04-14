package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.Config;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private Config appConfig;

    @PostMapping("/changePort/{port}")
    public String setPort(@PathVariable("port") int port) {
        appConfig.setPort(port);
        return "Port updated successfully!";
    }
}