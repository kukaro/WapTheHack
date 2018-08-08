var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');

var schemaTest = mongoose.Schema;

var disasterArea = new schemaTest({
    재해위험지역 : String,
    재해위험지역상세주소: String,
    재해위험등급: String
},{collection:'disaster_area'});
var areaModel = mongoose.model('disaster_area',disasterArea);

/* GET home page. */
router.get('/', function(req, res, next) {
    areaModel.find({},function (err,data) {
        var dataLength = data.length;
        var areas = new Array();
        var dangerDegree = new Array();
        for(var i = 0;i<dataLength;i++){
            areas[i]='주소 : '+ data[i].재해위험지역 + data[i].재해위험지역상세주소;
            dangerDegree[i] = '위험 등급 : '+data[i].재해위험등급;
        }
        res.render('disasterArea', { title: 'Express',areas:areas, dangerDegree:dangerDegree });
    });
});

module.exports = router;
