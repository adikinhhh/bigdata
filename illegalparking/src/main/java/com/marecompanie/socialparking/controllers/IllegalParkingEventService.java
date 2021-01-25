package com.marecompanie.socialparking.controllers;

import com.marecompanie.socialparking.model.IllegalParkingEvent;
import com.marecompanie.socialparking.repository.IllegalParkingEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import io.vavr.control.Option;

import java.util.List;

@Service
public class IllegalParkingEventService {
    private final static double EARTH_RADIUS_MILES = 3963.2;
    private final static double MILE_KM_RATIO = 1.6;

    private final IllegalParkingEventRepository illegalParkingEventRepository;

    @Autowired
    public IllegalParkingEventService(IllegalParkingEventRepository illegalParkingEventRepository) {
        this.illegalParkingEventRepository = illegalParkingEventRepository;
    }

    public ResponseEntity<?> postIllegalParkingEvent(IllegalParkingEvent illegalParkingEvent) {
        return Option.of(illegalParkingEventRepository.save(illegalParkingEvent))
                .fold(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null), ResponseEntity::ok);
    }

    public ResponseEntity<List<IllegalParkingEvent>> getAllIllegalParkingEvents() {
        return ResponseEntity.ok(illegalParkingEventRepository.findAll());
    }

    public ResponseEntity<?> deleteIllegalParkingEvent(String id) throws EntityNotFoundException {
        IllegalParkingEvent illegalParkingEvent = illegalParkingEventRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        illegalParkingEventRepository.deleteById(id);
        return ResponseEntity.ok(illegalParkingEvent);
    }

    public ResponseEntity<List<IllegalParkingEvent>> getAllIllegalParkingEventsByLocation(Double longitude, Double latitude, Double radius) {
        return ResponseEntity.ok(illegalParkingEventRepository.findAllByLocation(longitude, latitude, radius / (MILE_KM_RATIO * EARTH_RADIUS_MILES)));
    }

    public ResponseEntity<?> getIllegalParkingEventById(String id) throws EntityNotFoundException {
        return ResponseEntity.ok(illegalParkingEventRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public ResponseEntity<?> updateIllegalParkingReputationScoreById(String id, String type) throws EntityNotFoundException {
        IllegalParkingEvent illegalParkingEvent = illegalParkingEventRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if(type.equals("LIKE")) {
            illegalParkingEvent.setReputationScore(illegalParkingEvent.getReputationScore() + 1);
        } else {
            illegalParkingEvent.setReputationScore(illegalParkingEvent.getReputationScore() - 1);
        }
        illegalParkingEventRepository.save(illegalParkingEvent);
        return ResponseEntity.ok(illegalParkingEvent);
    }
}
