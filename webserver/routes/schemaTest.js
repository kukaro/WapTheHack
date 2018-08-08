var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');

var SchemaTest = mongoose.Schema;

var riskZone = new SchemaTest({
    재해위험지역 : String,
    재해위험지역상세주소: String,
    재해위험등급: String
});

module.exports = router;
