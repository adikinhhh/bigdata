const express = require('express');
const mongoose = require('mongoose');
const logger = require('morgan');
const createError = require('http-errors');
const bodyParser = require('body-parser');

const app = express();

app.use(bodyParser.json());

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

mongoose.console(`mongodb://${process.env.MONGO_INITDB_ROOT_USERNAME}:${process.env.MONGO_INITDB_ROOT_PASSWORD}@mongo:27017/socialparking`, {
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