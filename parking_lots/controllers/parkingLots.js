const express = require('express');
const ParkingLot = require('../models/ParkingLot');
const router = express.Router();
const parkingLot = require('../models/ParkingLot');

/* FindAll */
router.get('/api/parkinglots/', async (req, res) => {
    parkingLot.find()
    .then(parkingLots => {
        res.json(parkingLots);
    })
    .catch(err => {
        console.log(err);
        res.status(500);
    })
})

/* FindByDistance */
router.get('/api/parkinglots/distance', async (req, res) => {
    lat = req.query.latitude;
    long = req.query.longitude;
    radius = req.query.radius;

    parkingLot.find({
        location: {
            $geoWithin: {
                $centerSphere: [[long, lat], radius]
            }
        }
    })
    .then(parkingLots => {
        res.json(parkingLots);
    })
    .catch(err => {
        console.log(err);
        res.status(500);
    })
})

/* Update isFree */
router.patch('/api/parkinglots/:parkingLotID', async(req, res) => {
    parkingLot.findByIdAndUpdate(
        req.params.parkingLotID,
        {isFree: req.body.isFree},
        {new: true}
    )
    .then(lot => {
        res.json(lot);
    })
    .catch(err => {
        console.log(err);
        res.status(500);
    })
})

/* Post parkingLot */
router.post('/api/parkinglots/', async(req, res) => {
    var lot = new ParkingLot({
        timestamp: req.body.timestamp,
        location: req.body.location,
        isFree: req.body.isFree
    })
    lot
    .save()
    .then(() => {
        lot.id;
    })
    .catch(err => {
        console.log(err);
        res.status(500);
    })
})

module.exports = router;