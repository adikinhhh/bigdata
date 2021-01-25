const mongoose = require('mongoose');

const ParkingLotSchema = mongoose.Schema({
    id: String,
    timestamp: Date,
    location: Object,
    isFree: String,
});

module.exports = mongoose.model('ParkingLots', ParkingLotSchema);