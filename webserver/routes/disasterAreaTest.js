var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');

var SchemaTest = mongoose.Schema;

var riskZone = new SchemaTest({
    재해위험지역 : String,
    재해위험지역상세주소: String,
    재해위험등급: String
},{collection:'disasterArea'});
var areaModel = mongoose.model('disaster_area',riskZone);

/* GET home page. */
router.get('/', function(req, res, next) {
    areaModel.find({},function (err,data) {
        //console.log(data);
        res.render('disasterAreaTest', { title: 'Express',data:data });
    });
});

module.exports = router;
