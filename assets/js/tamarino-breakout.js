/******************************************************/
/* tAMARINO v.0.0.1
/* Toolkit for Rapid Prototyping in Physical Computing Projects
/* based on Breakoutjs (http://breakoutjs.com)


/* ricardo brazileiro aka sudo halt
/* rbrazileiro at gmail dot com
/******************************************************/



$(document).ready(function() {

    
    /************************************************/
    /* VARIABLES                                    
    /* Declare these variables so you don't have to type the full namespace                              
    /*                                   
    /************************************************/

    var IOBoard = BO.IOBoard;
    var IOBoardEvent = BO.IOBoardEvent;
    var Pin = BO.Pin;
    var PinEvent = BO.PinEvent;
    var Scaler = BO.filters.Scaler;


    var LED = BO.io.LED;
    var leds = [];
    var led;

    var pot;
    var currentEq;
    var potMinValue = 0;
    var potMaxValue = 1;


    /************************************************/
    /* DEBUGGING                                    
    /*                               
    /*                                   
    /************************************************/    
    
    BO.enableDebugging = false; 

    /************************************************/
    /* BOARD                                    
    /* If you are not serving this file from the same computer
    /* that the Arduino board is connected to, replace
    /* location.hostname with the IP address or hostname
    /* of the computer that the Arduino board is connected to.
    /*                                   
    /************************************************/
    
    var arduino = new IOBoard(location.hostname, 8887);

    /************************************************/
    /* LISTENING                                    
    /* Listen for the IOBoard READY event which indicates the IOBoard                              
    /* is ready to send and receive data                                  
    /************************************************/     
    
    arduino.addEventListener(IOBoardEvent.READY, onReady);

    /************************************************/
    /* FUNCTION onReady();                                    
    /* 
    /* When the onReady method is called, the first thing
    /* you should always do is remove the event listener
    /* for the READY event.
    /*       
    /*                                   
    /************************************************/        
    
    
    function onReady(event) {

        arduino.removeEventListener(IOBoardEvent.READY, onReady);
        
        // assuming you have leds wired to digital pins 2 - 8
        for (var i = 3; i < 14; i++) {
            leds.push(new LED(arduino, arduino.getDigitalPin(i)));
        }

        // POTENTIOMETER MODULE

        arduino.enableAnalogPin(0);
        pot = arduino.getAnalogPin(0);
        pot.addFilter(new Scaler(0, 1, potMinValue, potMaxValue, Scaler.LINEAR));
        currentEq = Scaler.LINEAR;
        pot.addEventListener(PinEvent.CHANGE, onPotChange);

        initGUIPotListeners();
        

        //led = new LED(arduino, arduino.getDigitalPin(ledPort));
        
        $('#btnLedOn').on('click', turnLedOn);
        $('#btnLedOff').on('click', turnLedOff);

        
        //$('#btnStop').click(function() { changeLed(0); });
        //$('#btnFull').click(function() { changeLed(2000); });
        //$('#btnHalf').click(function() { changeLed(1000); });

        // $("#ledBlinkSlider").slider({
        //     step: 1,
        //     min : 0,
        //     max : 2000
        //   });
          
      
        // $( "#ledPwmSlider" ).slider({
        //     step: 0.05,
        //     min : 0.0,
        //     max : 1.0      
        //   });
        
        // $('#spinner').spinner({
        //     min: 3,
        //     max: 13,
        //     step: 1
        // });
        
        // $('#potSpinner').spinner({
        //     min: 0,
        //     max: 5,
        //     step: 1
        // });

        // $('#potMinSpinner').spinner({
        //     min: 0,
        //     max: 10000,
        //     step: 10
        // });

        // $('#potMaxSpinner').spinner({
        //     min: 0,
        //     max: 10000,
        //     step: 10
        // });

        //$('#value').text("Value: 0.0"); 


        //for (var i = 0; i < 6; i++) {
        //    pots.push(new POT(arduino, arduino.getAnalogPin(i)));
        //}
        //pot = new POT(arduino, arduino.getAnalogPin(potPort));
        //pot.addEventListener(PotEvent.CHANGE, onPotChange);
        
        
    }
    
    /************************************************/
    /* FUNCTIONS                                    
    /*                               
    /*                                   
    /************************************************/    
    

    /* LED */

    $( "#ledBlinkSlider" ).bind( "slide", function(event, ui) {
        var value = ui.value;
            led.blink(ui.value, 0);
    });
    
    $( "#ledPwmSlider" ).bind( "slide", function(event, ui) {
        var value = ui.value;
           //$('#value').text("Value: " + value.toFixed(2));
          //led.value = value;
          led.fadeTo(value, 500);
    });
    
    // https://github.com/soundanalogous/Breakout/issues/35
    $( "#spinner" ).bind( "spinchange", function() {
        var spinnerValue = $("#spinner").spinner("value");
        //led = new LED(arduino, arduino.getDigitalPin(spinnerValue));
        led = leds[spinnerValue -3];       
        
    });    

    $( "#potSpinner" ).bind( "spinchange", function() {
        var spinnerValue = $("#potSpinner").spinner("value");
        //pot = new POT(arduino, arduino.getAnalogPin(spinnerValue));
        sensor0 = arduino.getAnalogPin(spinnerValue);
        //pot = pots[spinnerValue -3];                      
        
    });    

    $( "#potMinSpinner" ).bind( "spinchange", function() {
        var spinnerValue = $("#potMinSpinner").spinner("value");
        pot.setRange(spinnerValue, $("#potMaxSpinner").spinner("value"));        
    });

    $( "#potMaxSpinner" ).bind( "spinchange", function() {
        var spinnerValue = $("#potMaxSpinner").spinner("value");
        pot.setRange($("#potMinSpinner").spinner("value"), spinnerValue);        
    });    
    
      
    //function changeLed(delay) {
    //    led.blink(delay, 0);
    //    $("#ledBlinkSlider").slider( "option", "value", delay);
    //    $('#value').text("Value: " + delay);
    //} 
    
    function turnLedOn(evt) {
        // Turn the LED on
        led.on();
    }

    function turnLedOff(evt) {
        // Turn the LED off
        led.off();  
    }

    /* POTENTIOMETER */

    function onPotChange(event) {
        var pin = event.target;
        $('#potFilterValue').val(pin.value.toFixed(0));

        // $('#scope1Value').text('Pre-filtered value: ' + pin.preFilterValue.toFixed(3));
        // $('#scope2Value').text('Filtered value: ' + pin.value.toFixed(3));
    }

    function initGUIPotListeners() {
        $('select').on('change', function(evt) {
            pot.removeAllFilters();
            switch(this.value) {
                case 'cube':
                    addFilter(Scaler.CUBE, "CUBE");
                    break;
                case 'cube_root':
                    addFilter(Scaler.CUBE_ROOT, "CUBE_ROOT");
                    break;
                case 'square':
                    addFilter(Scaler.SQUARE, "SQUARE");
                    break;
                case 'square_root':
                    addFilter(Scaler.SQUARE_ROOT, "SQUARE_ROOT");
                    break;
                case 'linear':
                    addFilter(Scaler.LINEAR, "LINEAR");
                    break;
            }
        });

        $('#potMinSlider').on('change', function(evt) {
            potMinValue = this.value;
            pot.removeAllFilters();
            pot.addFilter(new Scaler(0, 1, potMinValue, potMaxValue, currentEq));
            // scope2 = null;
            // scope2 = new SignalScope("scope2", 200, 100, 0, maxVal);            
        });
        $('#potMaxSlider').on('change', function(evt) {
            potMaxValue = this.value;
            pot.removeAllFilters();
            pot.addFilter(new Scaler(0, 1, potMinValue, potMaxValue, currentEq));
            // scope2 = null;
            // scope2 = new SignalScope("scope2", 200, 100, 0, maxVal);            
        });
    }

    function addFilter(type, label) {
        pot.addFilter(new Scaler(0, 1, potMinValue, potMaxValue, type));
        // $('#scope2Label').text(label);
        currentEq = type;
    }   

    // function onChange(evt) {
    //     // The potentiometer gives back a value between 0 and 1.0
    //     var valueIn = evt.target.value;
    //     var value = valueIn * 1000;

    //     $('#potValue').text(value.toFixed(0));
    // //    $("#progressbar").progressbar({ value: value });
    // }

    

    //function onPotChange(evt) {
    //    //console.log("value = " + event.target.value);  
    //    var valueIn = evt.target.value;
    //    var value = valueIn * 1000;
    //    $('#value').text("Value = " + value.toFixed(0));
    //}

});