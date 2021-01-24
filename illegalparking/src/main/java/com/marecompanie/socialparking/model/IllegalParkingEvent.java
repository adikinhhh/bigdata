package com.marecompanie.socialparking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "illegal_parking")
public class IllegalParkingEvent {
    @Id
    private String id;

    private Date timestamp;
    private Location location;
    private String type;
    private String description;
    private String base64EncodedPhoto;
    private int reputationScore;
    private String tag;

    @Override
    public String toString() {
        return "IllegalParkingEvent{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", location=" + location +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", base64EncodedPhoto='" + base64EncodedPhoto + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
