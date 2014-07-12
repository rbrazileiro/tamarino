var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var userin = 0;
var five = require("johnny-five"),
    board = new five.Board();

board.on("ready", function() {
  // var servo = new five.Servo(process.argv[2] || 10);
  var servo = new five.Servo({
    id: "MyServo",     // User defined id
    pin: 10,           // Which pin is it attached to?
    type: "standard",  // Default: "standard". Use "continuous" for continuous rotation servos
    range: [0,180],    // Default: 0-180
    fps: 100,          // Used to calculate rate of movement between positions
    isInverted: false, // Invert all specified positions
    startAt: 90,       // Immediately move to a degree
    center: true,      // overrides startAt if true and moves the servo to the center of the range
    specs: {           // Is it running at 5V or 3.3V?
      speed: five.Servo.Continuous.speeds["@5.0V"] 
    }
  });
  this.repl.inject({
    servo: servo
  });

  app.get('/', function(req, res){
    res.sendfile('index.html');
  });

  io.on('connection', function(socket){
    console.log('a user connected');
    io.emit('in', userin++);
    servo.sweep();
    socket.on('disconnect', function(){
      io.emit('out', userin--);
      servo.stop();
      console.log('user disconnected');
    });    
  });
});
http.listen(3000, function(){
  console.log('listening on *:3000');
});