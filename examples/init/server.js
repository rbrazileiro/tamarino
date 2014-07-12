var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var userin = 0;
var five = require("johnny-five"),
    board = new five.Board();

board.on("ready", function() {
  vino = new five.Led({
    pin: 2
  }); 

  app.get('/', function(req, res){
    res.sendfile('index.html');
  });

  io.on('connection', function(socket){
    console.log('a user connected');    
    socket.on('disconnect', function(){
      console.log('user disconnected');
    });

    socket.on('malbec', function(data) {
      vino.on();
      vino.wait(100, function() {
        vino.off();
      });
    });
  });
  this.repl.inject({
      led: led
  });
});
http.listen(3000, function(){
  console.log('listening on *:3000');
});