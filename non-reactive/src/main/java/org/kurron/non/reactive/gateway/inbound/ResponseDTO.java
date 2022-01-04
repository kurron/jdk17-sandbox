package org.kurron.non.reactive.gateway.inbound;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for the GET request.
 */
public record ResponseDTO(@JsonProperty("id") long id,  @JsonProperty("message") String message) { }
