package com.startup.apigateway.repository;

import com.startup.apigateway.dao.ParkingLot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends MongoRepository<ParkingLot, String> {
}
