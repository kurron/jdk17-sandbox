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
        // some examples of Local-Variable Type Inference -- https://www.baeldung.com/java-10-local-variable-type-inference
        var now = Instant.now();
        var id = now.getEpochSecond();
        var message = calculateMessage();
        var dto = new ResponseDTO(id, message);
        return ResponseEntity.ok(dto);
    }

    private String calculateMessage() {
        // Switch Expression discussion: https://www.baeldung.com/java-switch-pattern-matching
        var selected = selectRealm();
        return switch (selected) {
            case Asgard -> "Asgard";
            case Midgard -> "Earth";
            case Jotunheim, Svartalfheim -> selected.toString().toUpperCase();
            case Vanaheim, Nidavellir -> selected.toString().toLowerCase();
            case Niflheim -> "Niflheim";
            case Muspelheim -> "Muspelheim";
            default -> {
                System.err.println("One case is missing!");
                yield "Unknown";
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
