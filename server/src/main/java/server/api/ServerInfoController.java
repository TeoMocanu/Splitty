package server.api;

import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

@RestController
public class ServerInfoController {

    private final InfoEndpoint infoEndpoint;

    @Autowired
    public ServerInfoController(InfoEndpoint infoEndpoint) {
        this.infoEndpoint = infoEndpoint;
    }

    @GetMapping("/custom/info")
    public Map<String, Object> showServerInfo() {
        // Directly return the map provided by the InfoEndpoint
        return infoEndpoint.info();
    }
}
