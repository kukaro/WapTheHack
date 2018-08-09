var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var bodyParser = require('body-parser');
var cors = require('cors');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var weatherRouter = require('./routes/weather');
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

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({extended: false}));
app.use(cookieParser());
app.use(cors());
app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'node_modules')));

app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/weather', weatherRouter);
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
