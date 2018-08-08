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
        var dataLength = data.length;
        var areas = new Array();
        var degree = new Array();
        for(var i = 0;i<dataLength;i++){
            areas[i]='주소 : '+ data[i].재해위험지역 + data[i].재해위험지역상세주소;
            degree[i] = '위험 등급 : '+data[i].재해위험등급;
        }
        res.render('disasterAreaTest', { title: 'Express',data2:degree, data:areas });
    });
});

module.exports = router;
