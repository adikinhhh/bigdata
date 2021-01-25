const express = require('express');
const mongoose = require('mongoose');
const logger = require('morgan');
const createError = require('http-errors');
const bodyParser = require('body-parser');
var cors = require('cors');

const app = express();

app.use(bodyParser.json());
app.use(cors());

const parkingLotsRouter = require('./controllers/parkingLots.js');

app.use('', parkingLotsRouter);

app.use((req, res, next) => {
    next(createError(404));
});

app.use((err, req, res, next) => {
    console.log(req.url)
    res.status(err.status || 500);
    res.json({error: "Could not process request."});
})

mongoose.connect(`mongodb://${process.env.MONGO_INITDB_ROOT_USERNAME}:${process.env.MONGO_INITDB_ROOT_PASSWORD}@mongo:27017/socialparking?authSource=admin`, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useFindAndModify: true,
})

let db = mongoose.connection;
db.on('error', console.error.bind(console, "Connection error"));
db.on('open', () => {
    console.log("Connected to DB");
})

// Start listening
app.listen(5000);