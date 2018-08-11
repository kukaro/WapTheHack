var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var bodyParser = require('body-parser');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
// var weatherRouter = require('./routes/weather');
var loginRouter = require('./routes/login');
var notFoundRouter = require('./routes/404');
var blankRouter = require('./routes/blank');
var chartsRouter = require('./routes/charts');
var errorRouter = require('./routes/error');
var forgotPasswordRouter = require('./routes/forgot-password');
var registerRouter = require('./routes/register');
var tablesRouter = require('./routes/tables');
var adminRouter = require('./routes/admin');

var app = express();

app.all('/*', function (req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    next();
});

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({extended: false}));
app.use(cookieParser());

app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'node_modules')));

app.use('/', indexRouter);
app.use('/users', usersRouter);
// app.use('/weather', weatherRouter);
app.use('/login', loginRouter);
app.use('/not-found', notFoundRouter);
app.use('/blank', blankRouter);
app.use('/charts', chartsRouter);
app.use('/error', errorRouter);
app.use('/forgot-password', forgotPasswordRouter);
app.use('/register', registerRouter);
app.use('/tables', tablesRouter);
app.use('/admin', adminRouter);

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

    socket.on('joinRoom', function (data) {
        console.log('joined room' + data.roomID);
        socket.join('room' + data.roomID);
    });
    io.sockets.on('test', function (data) {
        console.log(data);
    });
    socket.on('rasp', function (data) {
        try {
            inWater = data.inWater;
            outWater = data.outWater;
            gas = data.gas;
            console.log(inWater, outWater, gas);
            if (gas > 300)
                socket.emit('gasOff', {'send': 'g'});
            if ((300 < inWater && inWater < 500) || (300 < outWater && outWater < 500)) {
                io.sockets.emit('sendMsg', {'msg': '1'});
            } else if ((500 < inWater && inWater < 700) || (500 < outWater && outWater < 700)) {
                io.sockets.emit('sendMsg', {'msg': '2'});
            } else if ((700 < inWater && inWater < 900) || (700 < outWater && outWater < 900)) {
                io.sockets.emit('sendMsg', {'msg': '3'});
                socket.emit('lightOff', {'lightoff': 'l'});
            } else if (900 < inWater || 900 < outWater) {
                io.sockets.emit('sendMsg', {'msg': '4'});
                socket.emit('lightOff', {'lightoff': 'l'});
            } else {
                io.sockets.emit('sendMsg', {'msg': '0'});
            }
        } catch (exception) {
            console.log("라즈베리파이에서 데이터 손실");
        }
    });

});
