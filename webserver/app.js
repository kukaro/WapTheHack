var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');


var cors = require('cors');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var weatherRouter = require('./routes/weather');
var disasterRiskZoneRouter = require('./routes/disasterRiskZone');
var disasterAreaTestRouter = require('./routes/disasterAreaTest');

var app = express();

mongoose.connect('mongodb://localhost:27017/mydb'); //db연결
var db = mongoose.connection;
db.on('error',function () {
    console.log('db connection failed');
});
db.once('open',function () {
    console.log('db connected');
});

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(cors());
app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname,'node_modules')));

app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/weather',weatherRouter);
app.use('/disasterRiskZone',disasterRiskZoneRouter);
app.use('/disasterAreaTest',disasterAreaTestRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
