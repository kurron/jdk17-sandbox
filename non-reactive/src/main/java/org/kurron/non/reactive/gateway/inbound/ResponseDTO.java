package org.kurron.non.reactive.gateway.inbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Data Transfer Object for the GET request. Here is a good discussion about Java Records: https://dzone.com/articles/jdk-14-records-for-spring-devs
 */
public record ResponseDTO(@JsonProperty("id") long id,  @JsonProperty("message") String message) {

    // do some silly constructor validation
    public ResponseDTO {
        if ( 0 == id % 2) {
            throw new SillyValidationError();
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "ID cannot be an even number!")
    static class SillyValidationError extends IllegalArgumentException {}
}
