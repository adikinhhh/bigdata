package com.startup.apigateway.dao;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserLocation {
    private Double latitude;
    private Double longitude;
    private Date userLocationTimestamp;
}
