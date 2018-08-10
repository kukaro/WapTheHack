var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index');
});

router.post('/test', (req,res,next)=>{
  console.log(req.body);
});

router.post('/test2', (req,res,next)=>{
    console.log(req.body);
});

module.exports = router;
