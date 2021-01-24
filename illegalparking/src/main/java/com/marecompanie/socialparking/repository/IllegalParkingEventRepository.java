package com.marecompanie.socialparking.repository;

import com.marecompanie.socialparking.model.IllegalParkingEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IllegalParkingEventRepository extends MongoRepository<IllegalParkingEvent, String> {
    @Query("{ location: { $geoWithin: { $centerSphere: [[?0, ?1], ?2]}}}")
    List<IllegalParkingEvent> findAllByLocation(Double latitude, Double longitude, Double radius);
}
