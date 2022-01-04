package org.kurron.non.reactive.gateway.inbound;

import java.time.Instant;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Inbound gateway handling communications in a RESTful manner.
 */
@RestController
public class RestGateway {

    @GetMapping(path="/hello",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> handleGet() {
        var now = Instant.now();
        var id = now.getEpochSecond();
        var message = now.toString();
        var dto = new ResponseDTO(id, message);
        return ResponseEntity.ok(dto);
    }
}
