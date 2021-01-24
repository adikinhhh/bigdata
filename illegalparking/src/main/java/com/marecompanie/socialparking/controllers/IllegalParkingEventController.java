package com.marecompanie.socialparking.controllers;

import com.marecompanie.socialparking.api.IllegalParkingEventApi;
import com.marecompanie.socialparking.model.IllegalParkingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class IllegalParkingEventController implements IllegalParkingEventApi {

    private final IllegalParkingEventService illegalParkingEventService;

    @Autowired
    public IllegalParkingEventController(IllegalParkingEventService illegalParkingEventService) {
        this.illegalParkingEventService = illegalParkingEventService;
    }

    public ResponseEntity<?> postIllegalParkingEvent(IllegalParkingEvent illegalParkingEvent) {
        return illegalParkingEventService.postIllegalParkingEvent(illegalParkingEvent);
    }

    public ResponseEntity<List<IllegalParkingEvent>> getAllIllegalParkingEvents(Double latitude,
                                                                                Double longitude,
                                                                                Double radius) {
        if (Objects.isNull(latitude) || Objects.isNull(longitude) || Objects.isNull(radius)) {
            return illegalParkingEventService.getAllIllegalParkingEvents();
        }
        return illegalParkingEventService.getAllIllegalParkingEventsByLocation(latitude, longitude, radius);
    }

    public ResponseEntity<?> getIllegalParkingEventById(String id) throws EntityNotFoundException {
        return illegalParkingEventService.getIllegalParkingEventById(id);
    }

    public ResponseEntity<?> deleteIllegalParkingEvent(String id) throws EntityNotFoundException {
        return illegalParkingEventService.deleteIllegalParkingEvent(id);
    }

    public ResponseEntity<?> updateIllegalParkingReputationScoreById(String id, String type) throws EntityNotFoundException {
        return illegalParkingEventService.updateIllegalParkingReputationScoreById(id, type);
    }
}
