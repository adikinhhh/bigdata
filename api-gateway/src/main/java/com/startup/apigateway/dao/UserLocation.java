package com.startup.apigateway.dao;

import lombok.Builder;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

@Data
@Builder
public class UserLocation {
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("latitude")
    private Double latitude;
    private Date userLocationTimestamp;
}
