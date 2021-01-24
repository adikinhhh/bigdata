package com.marecompanie.socialparking.controllers;

import com.marecompanie.socialparking.model.IllegalParkingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class IllegalParkingEventController {

    private final IllegalParkingEventService illegalParkingEventService;

    @Autowired
    public IllegalParkingEventController(IllegalParkingEventService illegalParkingEventService) {
        this.illegalParkingEventService = illegalParkingEventService;
    }

    @PostMapping(path = "/api/illegalparkingevent")
    public ResponseEntity<?> postIllegalParkingEvent(@RequestBody IllegalParkingEvent illegalParkingEvent) {
        return illegalParkingEventService.postIllegalParkingEvent(illegalParkingEvent);
    }

    @GetMapping(path = "/api/illegalparkingevent")
    public ResponseEntity<List<IllegalParkingEvent>> getAllIllegalParkingEvents(@RequestParam(required = false) Double latitude,
                                                                                @RequestParam(required = false) Double longitude,
                                                                                @RequestParam(required = false) Double radius) {
        if (Objects.isNull(latitude) || Objects.isNull(longitude) || Objects.isNull(radius)) {
            return illegalParkingEventService.getAllIllegalParkingEvents();
        }
        return illegalParkingEventService.getAllIllegalParkingEventsByLocation(latitude, longitude, radius);
    }

    @GetMapping(path = "/api/illegalparkingevent/{id}")
    public ResponseEntity<?> getIllegalParkingEventById(@PathVariable String id) throws EntityNotFoundException {
        return illegalParkingEventService.getIllegalParkingEventById(id);
    }

    @DeleteMapping(path = "/api/illegalparkingevent/{id}")
    public ResponseEntity<?> deleteIllegalParkingEvent(@PathVariable String id) throws EntityNotFoundException {
        return illegalParkingEventService.deleteIllegalParkingEvent(id);
    }

    @PutMapping(path = "/api/illegalparkingevent/{id}")
    public ResponseEntity<?> updateIllegalParkingReputationScoreById(@PathVariable String id, @RequestParam String type) throws EntityNotFoundException {
        return illegalParkingEventService.updateIllegalParkingReputationScoreById(id, type);
    }
}
