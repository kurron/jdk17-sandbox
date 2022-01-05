package org.kurron.non.reactive.gateway.inbound;

import java.time.Instant;
import java.util.List;
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
        var junk = calculateJunk();
        var dto = new ResponseDTO(id, message, junk);
        return ResponseEntity.ok(dto);
    }

    @SuppressWarnings({"UnnecessaryUnboxing", "PointlessArithmeticExpression", "ConstantConditions"})
    private Object calculateJunk() {
        var data = selectData();
        // pattern matching in switch statements is in preview so we'll do it the old fashion way
        if (data instanceof Byte it) {
            return it.byteValue() - it.byteValue();
        }
        else if (data instanceof Short it) {
            return it.shortValue() - it.shortValue();
        }
        else if (data instanceof Integer it) {
            return it.intValue() - it.intValue();
        }
        else if (data instanceof Long it) {
            return it.longValue() - it.longValue();
        }
        else if (data instanceof Float it) {
            return it.floatValue() - it.floatValue();
        }
        else if (data instanceof Double it) {
            return it.doubleValue() - it.doubleValue();
        }
        else if (data instanceof Boolean it) {
            return !it.booleanValue();
        }
        else if (data instanceof Character it) {
            return it.charValue();
        }
        else if (data instanceof String it) {
            return it.toUpperCase();
        }
        System.err.println("Object type is " + data.getClass().getSimpleName());
        throw new IllegalArgumentException("Something isn't right!");
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

    private static List<Object> junkDrawer = List.of(Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE, Float.MAX_VALUE, Double.MAX_VALUE, Boolean.valueOf(true), Character.valueOf('X'), String.valueOf("Avengers assemble!"));

    private Object selectData() {
        return junkDrawer.get(ThreadLocalRandom.current().nextInt(junkDrawer.size()));
    }
}
