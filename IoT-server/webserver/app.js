var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var template1Router = require('./routes/template1');
var template2Router = require('./routes/template2');
var template3Router = require('./routes/template3');


var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({extended: false}));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/temp1', template1Router);
app.use('/temp2', template2Router);
app.use('/temp3', template3Router);


// catch 404 and forward to error handler
app.use(function (req, res, next) {
    next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
    // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get('env') === 'development' ? err : {};

    // render the error page
    res.status(err.status || 500);
    res.render('error');
});

module.exports = app;

var port = 8801;
var io = require('socket.io').listen(port);

var inWater;
var outWater;
var gas;

console.log('server running at ' + port + ' port');

io.sockets.on('connection', function (socket) {
    socket.emit('connect');
    console.log('connected');
    socket.on('rasp', function (data) {
        try {
            inWater = data.inWater;
            outWater = data.outWater;
            gas = data.gas;
            console.log(inWater, outWater, gas);
            if (gas > 500)
                socket.emit('gasOff', {'send': 'g'});
            if (inWater > 500 || outWater > 500) {
                io.sockets.emit('sendMsg', {'msg': 'Hello World!!'});
            }
        } catch (exception) {
            console.log("라즈베리파이에서 데이터 손실");
        }
    });
    //socket.emit('sendMsg', {'msg': 'Hello World!!'});

    // socket.on('joinRoom', function (data) {
    //     console.log('joined room' + data.roomID);
    //     socket.join('room' + data.roomID);
    //     //if (inWater > 100 || outWater > 100)
    //     socket.emit('sendMsg', {'msg': 'Hello World!!'});
    // });


});