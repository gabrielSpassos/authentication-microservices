const express = require('express');
const bodyParser = require('body-parser');
const consign = require('consign');
const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extend: false}));

app.set('view engine', 'ejs');
app.set('views', './views');

consign()
    .include('./routes')
    .then('./client/userClient.js')
    .then('./services/tokenService.js')
    .then('./services/userService.js')
    .into(app);

module.exports = app;