package org.kurron.non.reactive.gateway.inbound;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Inbound gateway handling communications in a RESTful manner.
 */
@RestController
public class RestGateway {

    @GetMapping(path="/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> handleGet() {
        var now = Instant.now();
        var id = now.getEpochSecond();
        var message = calculateMessage();
        var dto = new ResponseDTO(id, message);
        return ResponseEntity.ok(dto);
    }

    private String calculateMessage() {
        var selected = selectRealm();
        return switch (selected) {
            case Asgard -> "Asgard";
            case Midgard -> "Earth";
            case Jotunheim, Svartalfheim -> selected.toString().toUpperCase();
            case Vanaheim, Nidavellir -> selected.toString().toLowerCase();
            case Niflheim -> "Niflheim";
            case Muspelheim -> "Muspelheim";
            default -> {
                var message = """
                        This is a
                        multi-line message
                        indicating that one of the "impossible to miss" cases
                        was missing!
                        """;
                System.err.println(message);
                yield message;
            }
        };
    }

    private Realm selectRealm() {
        var all = Realm.values();
        return all[ThreadLocalRandom.current().nextInt(all.length)];
    }

    private enum Realm {
        Asgard,
        Midgard,
        Jotunheim,
        Svartalfheim,
        Vanaheim,
        Nidavellir,
        Niflheim,
        Muspelheim,
        Alfheim
    }
}
