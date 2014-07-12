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
tango = new five.Led({
    pin: 3
  }); 
messi = new five.Led({
    pin: 4
  }); 
masche = new five.Led({
    pin: 5
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
      board.wait(100, function() {
        vino.stop().off();
      });
    });
socket.on('piazzolla', function(data) {
      tango.on();
      board.wait(100, function() {
        tango.stop().off();
      });
socket.on('gol', function(data) {
      messi.on();
      board.wait(100, function() {
        messi.stop().off();
      });
socket.on('corazon', function(data) {
      masche.on();
      board.wait(100, function() {
        masche.stop().off();
      });
    });
    });
    });

  });

  this.repl.inject({
      led: vino
  });
});
http.listen(3000, function(){
  console.log('listening on *:3000');
});
