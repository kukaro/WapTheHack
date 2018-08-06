var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/', function (req, res, next) {
    var myJson = {'name': 'youjin', 'id': 'jinzzam', 'pw': '1234'}
    res.send(myJson);
});

module.exports = router;
