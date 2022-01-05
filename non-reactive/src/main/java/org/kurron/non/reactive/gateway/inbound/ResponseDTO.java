package org.kurron.non.reactive.gateway.inbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Data Transfer Object for the GET request. Discussions about Java Records:
 * https://www.baeldung.com/java-record-keyword
 * https://dzone.com/articles/jdk-14-records-for-spring-devs
 */
public record ResponseDTO(@JsonProperty("id") long id,  @JsonProperty("message") String message) {

    // do some silly constructor validation
    public ResponseDTO {
        if ( 0 == id % 2) {
            throw new SillyValidationError();
        }
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "ID cannot be an even number!")
    static class SillyValidationError extends IllegalArgumentException {}
}
