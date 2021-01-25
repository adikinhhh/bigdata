package com.marecompanie.socialparking.api;

import com.marecompanie.socialparking.controllers.EntityNotFoundException;
import com.marecompanie.socialparking.model.IllegalParkingEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IllegalParkingEventApi {
    @PostMapping(path = "/api/illegalparkingevent")
    ResponseEntity<?> postIllegalParkingEvent(@RequestBody IllegalParkingEvent illegalParkingEvent);

    @GetMapping(path = "/api/illegalparkingevent")
    ResponseEntity<List<IllegalParkingEvent>> getAllIllegalParkingEvents(@RequestParam(required = false) Double longitude,
                                                                         @RequestParam(required = false) Double latitude,
                                                                         @RequestParam(required = false) Double radius);

    @GetMapping(path = "/api/illegalparkingevent/{id}")
    ResponseEntity<?> getIllegalParkingEventById(@PathVariable String id) throws EntityNotFoundException;

    @DeleteMapping(path = "/api/illegalparkingevent/{id}")
    ResponseEntity<?> deleteIllegalParkingEvent(@PathVariable String id) throws EntityNotFoundException;

    @PutMapping(path = "/api/illegalparkingevent/{id}")
    ResponseEntity<?> updateIllegalParkingReputationScoreById(@PathVariable String id, @RequestParam String type) throws EntityNotFoundException;
}
