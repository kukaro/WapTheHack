var express = require('express');
var router = express.Router();

var test;

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index');
});

router.post('/test', (req,res,next)=>{
  console.log(req.body);
  test = req.body;
});

router.post('/test2', (req,res,next)=>{
    console.log(req.body);
});

module.exports = router;
