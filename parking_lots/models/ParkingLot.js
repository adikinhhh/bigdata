const mongoose = require('mongoose');

const ParkingLotSchema = mongoose.Schema({
    id: String,
    timestamp: Date,
    location: Object,
    isFree: Boolean,
});

module.exports = mongoose.model('ParkingLots', ParkingLotSchema);