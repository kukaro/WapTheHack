var express = require('express');
var router = express.Router();
var request = require('request');


/* GET home page. */
router.get('/', function(req, res, next) {
    var url = 'https://web.kma.go.kr/weather/forecast/distribution_vsrt_element_metsky.jsp?fcttype=VSRT&data1=RN1&size=400&tm_fc=201808110200&tm_ef=201808110400&mode=H&effect=GT&h=04';
    request(url, function(error, response, html){
        if (error) {throw error};
        res.render('test',{map:html});
    });
});

module.exports = router;
