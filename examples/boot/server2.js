var app = require('express')();
var http = require('http').Server(app);

app.get('/', function(req, res){
  res.send('<h1>Hello world</h1>');
});

app.get('/ba', function(req, res){
  res.send('<h1>Anchorena</h1>');
});

app.get('/file', function(req, res){
  res.sendfile('index.html');
});



http.listen(3000, function(){
  console.log('listening on *:3000');
});