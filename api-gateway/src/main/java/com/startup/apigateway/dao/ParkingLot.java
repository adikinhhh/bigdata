package com.startup.apigateway.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "parkinglots")
public class ParkingLot {
    @Id
    private String id;

    private Date timestamp;

    private Location location;

    private String isFree;
}
