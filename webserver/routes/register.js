var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');

mongoose.connect('mongodb://localhost:27017/mydb');
var db = mongoose.connection;
var person = mongoose.Schema({
    name : String,
    id : String,
    password : String,
    address  : String,
    phoneNumber : String,
    emergencyNumber : String,
    gender : String,
    disabilityType : String,
    disabilityGrade : String
},{collection :'user' });

var model = mongoose.model('user',person);

/* GET home page. */
router.get('/', function(req, res, next) {
    res.render('register');
});

router.post('/',function (req,res,next) {
    console.log(req.body);
    model.find({id:req.body.id},function (err,data) {
        if(data.length==0){ //동일 아이디가 없을 경우
            var newUser = new model({
                name : req.body.name,
                id : req.body.id,
                password : req.body.password,
                address : req.body.address,
                phoneNumber : req.body.phoneNumber,
                emergencyNumber : req.body.emergencyNumber,
                gender : req.body.gender
            });
            newUser.save(function (err,newUser) {
                if(err)return console.error(err);
                else{
                    console.dir(newUser);
                    res.redirect('/');
                }
            });
        }else{
            console.log('ID is already exist.');
            res.redirect('/register');
        }
    })
});

module.exports = router;
