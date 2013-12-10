/******************************************************/
/* tAMARINO v.0.1.0
/* Toolkit for Rapid Prototyping in Physical Computing Projects
/* based on:
/*   jQuery (http://jquery.com/)
/*   Breakout.js (http://breakoutjs.com)
/*   jsPlumb (http://jsplumb.org)
/*   jCanvas (http://calebevans.me/projects/jcanvas/)
/*   Board and Components Images by Fritzing (http://fritzing.org)
/*
/* @author - Ricardo Brazileiro
/* @since - 04/2013
/* rbrazileiro at gmail dot com
/******************************************************/

$(document).ready(function() {

/************************************************/
    /* VARIABLES                                    
    /* Declare these variables so you don't have to type the full namespace                              
    /* Breakout.js - http://breakoutjs.com                                  
    /* @author - Ricardo Brazileiro
    /************************************************/

    "use strict";

    var IOBoard = BO.IOBoard;
    var IOBoardEvent = BO.IOBoardEvent;
    var Pin = BO.Pin;
    var PinEvent = BO.PinEvent;
    var Potentiometer = BO.io.Potentiometer;
    var PotEvent = BO.io.PotEvent;
    var Scaler = BO.filters.Scaler;
    var Convolution = BO.filters.Convolution;
    var Oscillator = BO.generators.Oscillator;
    var Button = BO.io.Button;
    var ButtonEvent = BO.io.ButtonEvent;
    var Servo = BO.io.Servo;
    


    var LED = BO.io.LED;
    var leds = [];
    var portsLed = [];
    var led;

    var SPK = BO.io.LED;
    var spks = [];
    var speaker;
    var oscSquare;
    var currentOSC;
    var currentWave = Oscillator.SQUARE;
    var spkFreq;



    var pots = [];
    var pot;    
    var currentEq;
    var potMinValue = 0;
    var potMaxValue = 1;
    var potArray = [];
    var potArrayLength = 30;    
    var potLock = 0;
    var potPortValue;
    var scalerFilter;
    var smooth;

    var ldrs = [];
    var ldr;    
    var ldrCurrentEq;
    var ldrMinValue = 0;
    var ldrMaxValue = 1;
    var ldrArray = [];
    var ldrArrayLength = 30;    
    var ldrLock = 0;
    var ldrPortValue;
    var ldrScalerFilter;
    var ldrSmooth;

    var button;
    var btns = [];
    var portsBtn = [];

    var servo;
    var srvs = [];
    var srvAngle;

    var maxDropConnections = 1;


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
    /* Breakout.js - http://breakoutjs.com      
    /*                                   
    /************************************************/        
    
    
    function onReady(event) {

        arduino.removeEventListener(IOBoardEvent.READY, onReady);
               
        initGUILedListeners();
        initGUIPotListeners();
        initGUILdrListeners();
        initGUISpeakerListeners();
        initGUIButtonListeners(); // not used yet       
        
    }
    
    /************************************************/
    /* jQuery UI Functions and Binds                                  
    /* @author - Ricardo Brazileiro
    /* @since - 04/2013                      
    /*                                   
    /************************************************/    

    /* POTENTIOMETER */

    function initGUIPotListeners() {

        $('#potMinSlider').bind( "slide", function(evt, ui) {
          potMinValue = ui.value;
          pot.removeAllFilters();
          pot.addFilter(new Scaler(0, 1, potMinValue, potMaxValue, currentEq));          
        });

        $('#potMaxSlider').bind( "slide", function(evt, ui) {
          potMaxValue = ui.value;
          pot.removeAllFilters();
          pot.addFilter(new Scaler(0, 1, potMinValue, potMaxValue, currentEq));          
        });

    }

    function potActive(value) {        
        if (pots[0] === null) {
            pots.push(value);
            $("#ldrPortSelect option[value=" + value + "]").attr('disabled','disabled');            
        } else {
            pots.push(value);                            
            arduino.disableAnalogPin(pots[0]);
            $("#potPortSelect option[value=" + pots[0] + "]").removeAttr('disabled');
            $("#ldrPortSelect option[value=" + pots[0] + "]").removeAttr('disabled');                        
            pots[0] = pots[1];
            pots.length = 1;
            pot.clear();        
        }

        arduino.enableAnalogPin(value);
        pot = arduino.getAnalogPin(value);
        currentEq = Scaler.LINEAR;
        smooth = new Convolution(Convolution.MOVING_AVERAGE);        
        scalerFilter = new Scaler(0, 1, potMinValue, potMaxValue, Scaler.LINEAR);        
        pot.addFilter(smooth);
        pot.addFilter(scalerFilter);
        pot.clear();        
        pot.addEventListener(PinEvent.CHANGE, onPotChange);
    }

    function onPotChange(event) {
        var pin = event.target;
        var potLastVal = (potArray[potArrayLength-1]+50);        
        var potLastValLess = (potArray[potArrayLength-1]-50);

        if (potMaxValue === 1) {
            $('#potFilterValue').val(pin.value.toFixed(1)).trigger('change');
        } else if (potArray[potArrayLength-1] === null) {
            if(pin.value > 0) {                
                potLock = 1;
            }
        } else {
            if(pin.value > potLastVal || pin.value < potLastValLess) {                
                potArray.length = 0;
                potLock = 1;
            }            
        }               

        
                
        while (potLock === 1) {                    
            potArray.push(pin.value);                        
            if (potArray.length === potArrayLength) {
                var potVal = potArray[potArrayLength-1];                            
                $('#potFilterValue').val(potVal.toFixed(1)).trigger('change');                            
                potLock = 0;
                onPotChange();
            }
        }
    }

    function addFilter(type, label) {
        pot.addFilter(new Scaler(0, 1, potMinValue, potMaxValue, type));
        ldr.addFilter(new Scaler(0, 1, ldrMinValue, ldrMaxValue, type));
        currentEq = type;
    }

    /* LDR */

    function initGUILdrListeners() {
        $('#ldrMinSlider').bind( "slide", function(evt, ui) {
          ldrMinValue = ui.value;
          ldr.removeAllFilters();
          ldr.addFilter(new Scaler(0, 1, ldrMinValue, ldrMaxValue, ldrCurrentEq));          
        });

        $('#ldrMaxSlider').bind( "slide", function(evt, ui) {
          ldrMaxValue = ui.value;
          ldr.removeAllFilters();
          ldr.addFilter(new Scaler(0, 1, ldrMinValue, ldrMaxValue, ldrCurrentEq));          
        });
    } 

    function ldrActive(value) {        
        if (ldrs[0] === null) {
            ldrs.push(value);
            $("#potPortSelect option[value=" + value + "]").attr('disabled','disabled');                        
        } else {
            ldrs.push(value);
            arduino.disableAnalogPin(ldrs[0]);
            $("#potPortSelect option[value=" + ldrs[0] + "]").removeAttr('disabled');            
            ldrs[0] = ldrs[1];
            ldrs.length = 1;                                          
        }

        arduino.enableAnalogPin(value);
        ldr = arduino.getAnalogPin(value);                        
        ldrCurrentEq = Scaler.LINEAR;
        ldrSmooth = new Convolution(Convolution.MOVING_AVERAGE);        
        ldrScalerFilter = new Scaler(0, 1, ldrMinValue, ldrMaxValue, Scaler.LINEAR);        
        ldr.addFilter(ldrSmooth);
        ldr.addFilter(ldrScalerFilter);
        ldr.clear();        
        ldr.addEventListener(PinEvent.CHANGE, onLdrChange);
    }

    function onLdrChange(event) {
        var pin = event.target;
        var lastVal = (ldrArray[ldrArrayLength-1]+1);        
        var lastValLess = (ldrArray[ldrArrayLength-1]-1);

        if (potMaxValue === 1) {            
            $('#ldrFilterValue').val(pin.value.toFixed(1)).trigger('change');
        } else if (ldrArray[ldrArrayLength-1] === null) {
            if(pin.value > 0) {                
                ldrLock = 1;
            }
        } else {
            if(pin.value > lastVal || pin.value < lastValLess){                
                ldrArray.length = 0;
                ldrLock = 1;
            }            
        }               
                
        while (ldrLock === 1) {                    
            ldrArray.push(pin.value);                        
            if (ldrArray.length === ldrArrayLength) {
                var ldrVal = ldrArray[ldrArrayLength-1];                            
                $('#ldrFilterValue').val(ldrVal.toFixed(1)).trigger('change');                            
                ldrLock = 0;
                onLdrChange();
            }
        }
    }

    /* SPEAKER */


    function initGUISpeakerListeners() {

        spkFreq = 1.0; // Hz
        currentOSC = new Oscillator(currentWave, spkFreq);

        $("#spkOscSelect").change(function() {
            var spkFreq = 1.0;            
            currentOSC.stop();
            speaker.removeGenerator();
            switch(this.value) {
                case 'square':
                    currentWave = Oscillator.SQUARE;
                    break;
                case 'sin':
                    currentWave = Oscillator.SIN;
                    break;
                case 'saw':
                    currentWave = Oscillator.SAW;
                    break;
                case 'triangle':
                    currentWave = Oscillator.TRIANGLE;
                    break;
                case 'impulse':
                    currentWave = Oscillator.IMPULSE;
                    break;                          
            }
            addGenerator();
        });        

        $( "#spkFreqSlider" ).bind( "slide slidechange", function(event, ui) {
            spkFreq = ui.value;           
            speaker.removeGenerator();
            addGenerator();
        });
    }

    function addGenerator() {
        currentOSC = new Oscillator(currentWave, spkFreq);
        speaker.addGenerator(currentOSC);
        currentOSC.start();        
    }

    function setSpeakerPort(value) {

        if (spks[0] === null) {
            spks.push(value);            
            $("#ledPortSelect option[value=" + value + "]").attr('disabled','disabled');                        
            $("#srvPortSelect option[value=" + value + "]").attr('disabled','disabled');                        
            $("#btnPortSelect option[value=" + value + "]").attr('disabled','disabled');                        
        } else {
            spks.push(value);
            arduino.setDigitalPinMode(spks[0], Pin.DIN);
            arduino.setDigitalPinMode(spks[1], Pin.PWM);
            $("#ledPortSelect option[value=" + spks[0] + "]").removeAttr('disabled');            
            $("#srvPortSelect option[value=" + spks[0] + "]").removeAttr('disabled');            
            $("#btnPortSelect option[value=" + spks[0] + "]").removeAttr('disabled');            
            spks[0] = spks[1];
            spks.length = 1; 
        }        
        speaker = arduino.getDigitalPin(value);
        speaker.addGenerator(currentOSC);
        currentOSC.start();

    }

   

    /* LED */

    function initGUILedListeners() {
        for (var i = 3; i < 14; i++) {
          leds.push(new LED(arduino, arduino.getDigitalPin(i)));
        }

        $('#btnLedOn').on('click', turnLedOn);
        $('#btnLedOff').on('click', turnLedOff);


    }

    function turnLedOn(evt) {        
        led.stopBlinking();        
        led.on();
    }

    function turnLedOff(evt) {                       
        led.stopBlinking();
        led.off();  
    }

    function setLedPort(value) {

        if (portsLed[0] === null) {
            portsLed.push(value);            
            $("#spkPortSelect option[value=" + value + "]").attr('disabled','disabled');                        
            $("#srvPortSelect option[value=" + value + "]").attr('disabled','disabled');                        
            $("#btnPortSelect option[value=" + value + "]").attr('disabled','disabled');                        
        } else {
            portsLed.push(value);                                    
            $("#spkPortSelect option[value=" + portsLed[0] + "]").removeAttr('disabled');            
            $("#srvPortSelect option[value=" + portsLed[0] + "]").removeAttr('disabled');            
            $("#btnPortSelect option[value=" + portsLed[0] + "]").removeAttr('disabled');            
            portsLed[0] = portsLed[1];
            portsLed.length = 1; 
        }

        led = leds[value -3];
    }

    /* BUTTON */

    function initGUIButtonListeners() {        

    }

    function onBtnChange(evt) {
        var btn = evt.target;        
        switch(btn.value) {
            case 0:
                $('#btnStateIcon').css({
                    "box-shadow": "0px 5px 5px rgba(50, 50, 50, 0.75)",
                    "-moz-box-shadow": "0px 5px 5px rgba(50, 50, 50, 0.75)",
                    "-webkit-box-shadow": "0px 5px 5px rgba(50, 50, 50, 0.75)",
                    "top": "0px"
                });
                $('#btnStateValue').val(btn.value).trigger('change');
                break;
            case 1:
                $('#btnStateIcon').css({
                    "box-shadow": "none",
                    "-moz-box-shadow": "none",
                    "-webkit-box-shadow": "none",
                    "top": "3px"
                });
                $('#btnStateValue').val(btn.value).trigger('change');
            break;
        }        
    }   

    function setButtonPort(value) {

        if (portsBtn[0] === null) {
            portsBtn.push(value);
            arduino.setDigitalPinMode(portsBtn[0], Pin.DIN);            
            $("#ledPortSelect option[value=" + value + "]").attr('disabled','disabled');
            $("#spkPortSelect option[value=" + value + "]").attr('disabled','disabled');                        
            $("#srvPortSelect option[value=" + value + "]").attr('disabled','disabled');                        
        } else {
            portsBtn.push(value);
            arduino.setDigitalPinMode(portsBtn[0], Pin.DOUT);
            arduino.setDigitalPinMode(portsBtn[1], Pin.DIN);            
            $("#ledPortSelect option[value=" + portsBtn[0] + "]").removeAttr('disabled');            
            $("#spkPortSelect option[value=" + portsBtn[0] + "]").removeAttr('disabled');            
            $("#srvPortSelect option[value=" + portsBtn[0] + "]").removeAttr('disabled');            
            portsBtn[0] = portsBtn[1];
            portsBtn.length = 1; 
        }
        
        button = arduino.getDigitalPin(value);
        button.addEventListener(PinEvent.CHANGE, onBtnChange);       


    }

    /* SERVO */

    function setServoPort(value) {

        if (srvs[0] === null) {
            srvs.push(value);
            arduino.setDigitalPinMode(srvs[0], Pin.DIN);            
            $("#ledPortSelect option[value=" + value + "]").attr('disabled','disabled');
            $("#spkPortSelect option[value=" + value + "]").attr('disabled','disabled');                        
            $("#btnPortSelect option[value=" + value + "]").attr('disabled','disabled');
        } else {
            srvs.push(value);
            arduino.setDigitalPinMode(srvs[0], Pin.DOUT);            
            $("#ledPortSelect option[value=" + portsBtn[0] + "]").removeAttr('disabled');            
            $("#spkPortSelect option[value=" + portsBtn[0] + "]").removeAttr('disabled');            
            $("#btnPortSelect option[value=" + portsBtn[0] + "]").removeAttr('disabled');            
            portsBtn[0] = portsBtn[1];
            portsBtn.length = 1; 
        }

        servo = new Servo(arduino, arduino.getDigitalPin(value));
        servo.angle = 10;                
        
    } 

    /* POT */

    $("#potPortSpinner").spinner({
                min: 0,
                max: 5,
                step: 1,
                value: 0,
                stop: function( event, ui ) {
                    $(this).trigger("spinchange");
                }
    });

    $("#potMinSlider").slider({
                min: 0,
                max: 5000,
                step: 1,
                value: 0,
                slide: function( event, ui ) {
                    $( "#potMinSliderValue" ).val( ui.value );
                }
    });

    $( "#potMinSliderValue" ).val( $( "#potMinSlider" ).slider( "value" ) );                    
 
    $("#potMaxSlider").slider({
                min: 0,
                max: 5000,
                step: 1,
                value: 0,
                slide: function( event, ui ) {
                    $( "#potMaxSliderValue" ).val( ui.value );
                }

    });
    $( "#potMaxSliderValue" ).val( $( "#potMaxSlider" ).slider( "value" ) );        

    $( "#potPortSelect" ).change(function() {
        var value = $(this).val();         
        potActive(value);
        $("#ldrPortSelect option[value=" + value + "]").attr('disabled','disabled');
        $("#schema_tab").trigger('click');

    });
    
    /* LDR */

    $("#ldrPortSpinner").spinner({
                min: 0,
                max: 5,
                step: 1,
                value: 0,
                stop: function( event, ui ) {
                    $(this).trigger("spinchange");
                }
    });

    $("#ldrMinSlider").slider({
                min: 0,
                max: 5000,
                step: 1,
                value: 0,
                slide: function( event, ui ) {
                    $( "#ldrMinSliderValue" ).val( ui.value );
                }
    });

    $( "#ldrMinSliderValue" ).val( $( "#ldrMinSlider" ).slider( "value" ) );                    
 
    $("#ldrMaxSlider").slider({
                min: 0,
                max: 5000,
                step: 1,
                value: 0,
                slide: function( event, ui ) {
                    $( "#ldrMaxSliderValue" ).val( ui.value );
                }

    });

    $( "#ldrMaxSliderValue" ).val( $( "#ldrMaxSlider" ).slider( "value" ) );    

    $( "#ldrPortSpinner" ).bind( "spinchange", function() {
        ldrPortValue = $("#ldrPortSpinner").spinner("value");
        ldrActive(ldrPortValue);        
    });

    $( "#ldrPortSelect" ).change(function() {
        var value = $(this).val();
        ldrActive(value);
        $("#potPortSelect option[value=" + value + "]").attr('disabled','disabled');         
        $("#schema_tab").trigger('click');
        
        
    });

    /* LED */

    $("#ledFreqSlider").slider({
                min: 0,
                max: 5000,
                step: 1,
                value: 0,
                slide: function( event, ui ) {
                    $( "#ledFreqSliderValue" ).val( ui.value + " ms" );
                },
                change: function(event, ui) {
                    $( "#ledFreqSliderValue" ).val( ui.value + " ms");
                }
    });    

    $( "#ledFreqSliderValue" ).val( $( "#ledFreqSlider" ).slider( "value" ) + " ms");

    $("#ledIntSlider").slider({
                min: 0,
                max: 1,
                step: 0.01,
                value: 0,
                slide: function( event, ui ) {
                    $( "#ledIntSliderValue" ).val( ui.value + " ~" );
                },
                change: function(event, ui) {
                    $( "#ledIntSliderValue" ).val( ui.value  + " ~" );
                }
    });

    $( "#ledIntSliderValue" ).val( $( "#ledIntSlider" ).slider( "value" ) + " ~");

    $( "#ledPortSelect" ).change(function(){
        var value = $(this).val();
        setLedPort(value);
        $("#spkPortSelect option[value=" + value + "]").attr('disabled','disabled');
        $("#srvPortSelect option[value=" + value + "]").attr('disabled','disabled');
        $("#btnPortSelect option[value=" + value + "]").attr('disabled','disabled');
        $("#schema_tab").trigger('click');        
    });

    $( "#ledFreqSlider" ).bind( "slide slidechange", function(event, ui) {
        var ledFreqValue = ui.value;
        led.blink(ledFreqValue, 0);
    });    

    $( "#ledIntSlider" ).bind( "slide slidechange", function(event, ui) {
        var ledIntValue = ui.value;        
        led.fadeTo(ledIntValue, 100);
    });

    /* SPEAKER */

    $("#spkFreqSlider").slider({
                min: 0,
                max: 33.0,
                step: 0.1,
                value: 0,
                slide: function( event, ui ) {
                    $( "#spkFreqSliderValue" ).val( ui.value + " Hz" );
                },
                change: function(event, ui) {
                    $( "#spkFreqSliderValue" ).val( ui.value  + " Hz");
                }
    });    

    $( "#spkFreqSliderValue" ).val( $( "#spkFreqSlider" ).slider( "value" ) + " Hz");

    $( "#spkPortSelect" ).change(function(){
        var value = $(this).val();
        setSpeakerPort(value);
        $("#ledPortSelect option[value=" + value + "]").attr('disabled','disabled');        
        $("#srvPortSelect option[value=" + value + "]").attr('disabled','disabled');        
        $("#btnPortSelect option[value=" + value + "]").attr('disabled','disabled');
        $("#schema_tab").trigger('click');        
    });

    /* BUTTON */

    $( "#btnPortSelect" ).change(function(){
        var value = $(this).val();
        setButtonPort(value);
        $("#ledPortSelect option[value=" + value + "]").attr('disabled','disabled');        
        $("#spkPortSelect option[value=" + value + "]").attr('disabled','disabled');        
        $("#srvPortSelect option[value=" + value + "]").attr('disabled','disabled');
        $("#schema_tab").trigger('click');        
    });

    /* SERVO */

    $("#srvAngleValue").anglepicker({
        start: function(e, ui) {
        },
        change: function(e, ui) {
            $("#srvAngleLog").text(ui.value.toFixed(0) + " º");
            $("#srvAngleValue").val(ui.value).trigger("change");           
            
        },
        stop: function(e, ui) {
        },
        value: 90
    });

    $("#srvAngleValue").anglepicker("value", 10);

    $( "#srvPortSelect" ).change(function(){
        var value = $(this).val();
        setServoPort(value);
        $("#ledPortSelect option[value=" + value + "]").attr('disabled','disabled');        
        $("#spkPortSelect option[value=" + value + "]").attr('disabled','disabled');        
        $("#btnPortSelect option[value=" + value + "]").attr('disabled','disabled');
        $("#schema_tab").trigger('click');        
    });

    $("#srvAngleValue").change(function() {
        var angle = $(this).val();
        servo.angle = angle;
    });


    /* MENU */   

    $( "#input_control").click(function () {
        $(this).children('ul').slideToggle();
        }).mouseleave(function() {
            $(this).children('ul').slideUp();
    });

    $( "#output_control").click(function () {
        $(this).children('ul').slideToggle();
        }).mouseleave(function() {
            $(this).children('ul').slideUp();
    });    


    /************************************************/
    /* Modules Handler
    /* @author - Ricardo Brazileiro
    /* @since - 04/2013                              
    /*                                   
    /************************************************/  

    $( "#trigger-led" ).buttonset();
    $( ".module" ).hide();        

    $( ".modulePot").on("click", function (){        
        $(".pot").show();        
        jsPlumb.addEndpoint($(".potFilterValueModule") , { anchor:"RightMiddle", parameters:{potFilterValue:$('#potFilterValue')} }, potSourceEndpoint );                
        jsPlumb.animate($(".pot"), {"left": 100,"top": 150},{duration:"slow"});
        $("canvas#input-pot").setLayer("pot", {
                visible: true                
                }).drawLayers();    

        $(this).addClass("clicked");

    });

    $( ".moduleLdr").on("click", function (){        
        $(".ldr").show();
        // var butId = $(this).attr('class');                
        jsPlumb.addEndpoint($(".ldrFilterValueModule") , { anchor:"RightMiddle", parameters:{ldrFilterValue:$('#ldrFilterValue')} }, ldrSourceEndpoint );                
        jsPlumb.animate($(".ldr"), {"left": 70,"top": 350},{duration:"slow"});
        $("canvas#input-ldr").setLayer("ldr", {
                visible: true                
                }).drawLayers();    

        $(this).addClass("clicked");

    });

    $( ".moduleLed").on("click", function (){
        $(".led").show();
        jsPlumb.addEndpoint($(".ledFreqModule") , { anchor:[0, 0.65, 0, 1], parameters:{ledFreqModule:$("#ledFreqSlider") }}, ledFreqTargetEndpoint );
        jsPlumb.addEndpoint($(".ledIntModule") , { anchor:[0, 0.65, 0, 1], parameters:{ledIntModule:$("#ledIntSlider")} }, ledIntTargetEndpoint);
        jsPlumb.addEndpoint($(".stateSelect") , { anchor:[0, 0.5, 0, 1]}, ledStateTargetEndpoint);                
        jsPlumb.animate($(".led"), {"left": 650,"top": 250},{duration:"slow"});        
        $("canvas#output").setLayer("led", {
                visible: true                
                }).drawLayers();       
        $(this).addClass("clicked");
    });

    $( ".moduleSpk").on("click", function (){
        $(".spk").show();
        jsPlumb.addEndpoint($(".spkFreqModule") , { anchor:[0, 0.65, 0, 1], parameters:{spkFreqModule:$("#spkFreqSlider") }}, spkFreqTargetEndpoint );        
        jsPlumb.animate($(".spk"), {"left": 650,"top": 450},{duration:"slow"});        
        $("canvas#output-spk").setLayer("spk", {
                visible: true                
                }).drawLayers();       
        $(this).addClass("clicked");
    });

    $( ".moduleBtn").on("click", function (){
        $(".bttn").show();
        jsPlumb.addEndpoint($(".btnStateModule") , { anchor:"RightMiddle", parameters:{btnStateValue:$("#btnStateValue") }}, btnStateSourceEndpoint );        
        jsPlumb.animate($(".bttn"), {"left": 350,"top": 450},{duration:"slow"});        
        $("canvas#input-btn").setLayer("btn", {
                visible: true                
                }).drawLayers();       
        $(this).addClass("clicked");
    });

    $( ".moduleSrv").on("click", function (){
        $(".srv").show();
        // jsPlumb.addEndpoint($(".btnStateModule") , { anchor:"RightMiddle", parameters:{btnStateValue:$("#btnStateValue") }}, btnStateSourceEndpoint );        
        jsPlumb.addEndpoint($(".srvAngleValueModule") , { anchor:[0, 0.5, 0, 1], parameters:{srvAngleValueModule:$("#srvAngleValue") }}, srvAngleTargetEndpoint );        
        jsPlumb.animate($(".srv"), {"left": 350,"top": 250},{duration:"slow"});        
        $("canvas#output-srv").setLayer("srv", {
                visible: true                
                }).drawLayers();       
        $(this).addClass("clicked");
    });

    $('#trash').droppable({
        over: function(event, ui) {
            $(ui.draggable).hide();
            var element = $(ui.draggable);            
            var potEndPoint = $(ui.draggable).find('input').parent();
            var potEndPointClass = $(ui.draggable).find('input').parent().hasClass('potFilterValueModule');            
            var ldrEndPoint = $(ui.draggable).find('input').parent();
            var ldrEndPointClass = $(ui.draggable).find('input').parent().hasClass('ldrFilterValueModule');            
            var ledEndPoint = $(ui.draggable).find('div#ledFreqSlider').parent();
            var ledEndPointInt = $(ui.draggable).find('div#ledIntSlider').parent();
            var ledEndPointState = $(ui.draggable).find('div#trigger-led').parent();
            var ledEndPointClass = ledEndPoint.hasClass('ledFreqModule');            
            var spkEndPoint = $(ui.draggable).find('div#spkFreqSlider').parent();
            var spkEndPointClass = spkEndPoint.hasClass('spkFreqModule');          
            var btnEndPoint = $(ui.draggable).find('input').parent();
            var btnEndPointClass = btnEndPoint.hasClass('btnStateModule');
            var srvEndPoint = $(ui.draggable).find('div#srvAngleValue').parent();
            var srvEndPointClass = srvEndPoint.hasClass('srvAngleValueModule');

            if (potEndPointClass === true) {
                jsPlumb.selectEndpoints({element:potEndPoint}).setVisible(false);
                $("canvas#input-pot").setLayer("pot", {
                    visible: false                
                    }).drawLayers();
                $("canvas#schematico").animateLayer(3, {
                  l1: "0"
                }, 1000);
                $("canvas#schematico").animateLayer(4, {
                  l1: "0"
                }, 1000);
                $("canvas#schematico").animateLayer(5, {
                  l1: "0",
                  l2: "0",
                  l3: "0"      
                }, 1000);
                $(".modulePot").removeClass("clicked");
            } else if(ledEndPointClass === true) {
                jsPlumb.selectEndpoints({element:ledEndPoint}).setVisible(false);
                jsPlumb.selectEndpoints({element:ledEndPointInt}).setVisible(false);
                jsPlumb.selectEndpoints({element:ledEndPointState}).setVisible(false);
                $("canvas#output").setLayer("led", {
                    visible: false                
                    }).drawLayers();
                $("canvas#schematico").animateLayer(2, {
                  l1: "0"
                }, 1000);                
                $("canvas#schematico").animateLayer(6, {
                  l1: "0",
                  l2: "0",
                  l3: "0",
                  l4: "0"      
                }, 1000);
                $(".moduleLed").removeClass("clicked");
            } else if (ldrEndPointClass === true) {
                jsPlumb.selectEndpoints({element:ldrEndPoint}).setVisible(false);
                $("canvas#input-ldr").setLayer("ldr", {
                    visible: false                
                    }).drawLayers();
                $("canvas#schematico").animateLayer(7, {
                  l1: "0"
                }, 1000);
                $("canvas#schematico").animateLayer(8, {
                  l1: "0"
                }, 1000);
                $("canvas#schematico").animateLayer(9, {
                  l1: "0",
                  l2: "0",
                  l3: "0"      
                }, 1000);
                $(".moduleLdr").removeClass("clicked");
            } else if (spkEndPointClass === true) {
                jsPlumb.selectEndpoints({element:spkEndPoint}).setVisible(false);
                $("canvas#output-spk").setLayer("spk", {
                    visible: false                
                    }).drawLayers();
                $("canvas#schematico").animateLayer(10, {
                  l1: "0"
                }, 1000);
                $("canvas#schematico").animateLayer(11, {
                  l1: "0",
                  l2: "0",
                  l3: "0",
                  l4: "0",
                  l5: "0"
                }, 1000);                
                $(".moduleSpk").removeClass("clicked");
                currentOSC.stop();

            } else if (btnEndPointClass === true) {
                jsPlumb.selectEndpoints({element:btnEndPoint}).setVisible(false);
                $("canvas#input-btn").setLayer("btn", {
                    visible: false                
                    }).drawLayers();
                $("canvas#schematico").animateLayer(12, {
                  l1: "0"
                }, 1000);
                $("canvas#schematico").animateLayer(13, {
                  l1: "0"
                }, 1000);
                $("canvas#schematico").animateLayer(14, {
                  l1: "0",
                  l2: "0",
                  l3: "0",
                  l4: "0",
                  l5: "0"
                }, 1000);                
                $(".moduleBtn").removeClass("clicked");

            } else if (srvEndPointClass === true) {
                jsPlumb.selectEndpoints({element:srvEndPoint}).setVisible(false);
                $("canvas#output-srv").setLayer("srv", {
                    visible: false                
                    }).drawLayers();
                $("canvas#schematico").animateLayer(15, {
                  l1: "0"
                }, 1000);
                $("canvas#schematico").animateLayer(16, {
                  l1: "0"
                }, 1000);
                $("canvas#schematico").animateLayer(17, {
                  l1: "0",
                  l2: "0",
                  l3: "0",
                  l4: "0"                  
                }, 1000);                
                $(".moduleSrv").removeClass("clicked");
            }           

            
        }
    });


    /************************************************/
    /* Board and Schematic Handler
    /*                               
    /*                                   
    /************************************************/  

    var opened = false;
    $("#schema_content").hide();
    $("#schema_button, #schematic_control, #schema_tab").click(function(){
        if(opened){
            $("#schema_tab").animate({"min-height": "2%"});            
            $("#schema_content").hide("slow");
            
        }else{
            
            $("#schema_tab").animate({"min-height": "85%"});                                    
            $("#schema_content").show();                        

            $("canvas#schematico").animateLayer(0, {
                l1: "20",
                l2: "221",
                l3: "85"
                }, 3000);
            $("canvas#schematico").animateLayer(1, {
                l1: "27",
                l2: "224",
                l3: "71"
                }, 3000);

            if ($(".modulePot").hasClass("clicked")) {

                $("canvas#schematico").animateLayer(3, {
                    l1: "30"
                    }, 4000);
                $("canvas#schematico").animateLayer(4, {
                    l1: "35"
                }, 4000);

                if ($("#potPortSelect").val() == 0 && pots[0] !== null) {
                    $("canvas#schematico").animateLayer(5, {
                        x: "420",                                            
                        l1: "40",
                        l2: "123",
                        l3: "92"      
                        }, 4000);
                } else if ($("#potPortSelect").val() === 1) {
                    $("canvas#schematico").animateLayer(5, {
                        x: "430",                                            
                        l1: "40",
                        l2: "133",
                        l3: "92"      
                        }, 4000);
                } else if ($("#potPortSelect").val() === 2) {
                    $("canvas#schematico").animateLayer(5, {
                        x: "438",                                            
                        l1: "40",
                        l2: "141",
                        l3: "92"      
                        }, 4000);
                } else if ($("#potPortSelect").val() === 3) {
                    $("canvas#schematico").animateLayer(5, {
                        x: "448",                                            
                        l1: "40",
                        l2: "151",
                        l3: "92"      
                        }, 4000);
                } else if ($("#potPortSelect").val() === 4) {
                    $("canvas#schematico").animateLayer(5, {
                        x: "458",                                            
                        l1: "40",
                        l2: "161",
                        l3: "92"      
                        }, 4000);
                } else if ($("#potPortSelect").val() === 5) {
                    $("canvas#schematico").animateLayer(5, {
                        x: "467",                                            
                        l1: "40",
                        l2: "170",
                        l3: "92"      
                        }, 4000);
                }                
            }


            if ($(".moduleLdr").hasClass("clicked")) {                
                $("canvas#schematico").animateLayer(7, {
                    l1: "35"
                }, 4000);

                $("canvas#schematico").animateLayer(8, {
                    l1: "30"
                }, 4000);

                if ($("#ldrPortSelect").val() == 0 && ldrs[0] !== null) {
                    $("canvas#schematico").animateLayer(9, {                        
                        x: 420,
                        l1: "50",
                        l2: "58",
                        l3: "82"      
                        }, 4000);
                } else if ($("#ldrPortSelect").val() === 1) {
                    $("canvas#schematico").animateLayer(9, {                        
                        x: 430,
                        l1: "50",
                        l2: "68",
                        l3: "82"      
                        }, 4000);
                } else if ($("#ldrPortSelect").val() === 2) {
                    $("canvas#schematico").animateLayer(9, {
                        x: 440,                        
                        l1: "50",
                        l2: "78",
                        l3: "82"      
                        }, 4000);
                } else if ($("#ldrPortSelect").val() === 3) {
                    $("canvas#schematico").animateLayer(9, {
                        x: 448,                        
                        l1: "50",
                        l2: "86",
                        l3: "82"      
                        }, 4000);
                } else if ($("#ldrPortSelect").val() === 4) {
                    $("canvas#schematico").animateLayer(9, {
                        x: 456,                        
                        l1: "50",
                        l2: "94",
                        l3: "82"      
                        }, 4000);
                } else if ($("#ldrPortSelect").val() === 5) {
                    $("canvas#schematico").animateLayer(9, {
                        x: 466,                        
                        l1: "50",
                        l2: "104",
                        l3: "82"      
                        }, 4000);
                }
            }

            if ($(".moduleLed").hasClass("clicked")) {
                $("canvas#schematico").animateLayer(2, {
                  l1: "30"
                }, 4000);

                if ($("#ledPortSelect").val() === 3) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "439",
                        l1: "40",
                        l2: "102",
                        l3: "358",
                        l4: "64"      
                        }, 4000);
                } else if ($("#ledPortSelect").val() === 4) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "430",
                        l1: "40",
                        l2: "110",
                        l3: "358",
                        l4: "64"      
                        }, 4000);
                } else if ($("#ledPortSelect").val() === 5) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "421",
                        l1: "40",
                        l2: "120",
                        l3: "358",
                        l4: "64"      
                        }, 4000);
                } else if ($("#ledPortSelect").val() === 6) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "412",
                        l1: "40",
                        l2: "120",
                        l3: "358",
                        l4: "56"      
                        }, 4000);
                } else if ($("#ledPortSelect").val() === 7) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "403",
                        l1: "40",
                        l2: "130",
                        l3: "358",
                        l4: "56"      
                        }, 4000);
                } else if ($("#ledPortSelect").val() === 8) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "385",
                        l1: "40",
                        l2: "150",
                        l3: "358",
                        l4: "56"      
                        }, 4000);
                } else if ($("#ledPortSelect").val() === 9) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "375",
                        l1: "40",
                        l2: "150",
                        l3: "358",
                        l4: "46"      
                        }, 4000);
                } else if ($("#ledPortSelect").val() === 10) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "367",
                        l1: "40",
                        l2: "150",
                        l3: "358",
                        l4: "38"      
                        }, 2000);
                } else if ($("#ledPortSelect").val() === 11) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "357",
                        l1: "40",
                        l2: "150",
                        l3: "358",
                        l4: "28"      
                        }, 2000);
                } else if ($("#ledPortSelect").val() === 12) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "347",
                        l1: "40",
                        l2: "150",
                        l3: "358",
                        l4: "18"      
                        }, 2000);
                } else if ($("#ledPortSelect").val() === 13) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "338",
                        l1: "40",
                        l2: "160",
                        l3: "358",
                        l4: "19"      
                        }, 2000);
                }
            }

            if ($(".moduleSpk").hasClass("clicked")) {
                $("canvas#schematico").animateLayer(10, {
                  l1: "80"
                }, 4000);
                
                if ($("#spkPortSelect").val() === 3) {
                    $("canvas#schematico").animateLayer(11, {
                        x: "439",
                        l1: "30",
                        l2: "73",
                        l3: "263",
                        l4: "99",
                        l5: "120"      
                        }, 4000);
                } else if ($("#spkPortSelect").val() === 5) {
                    $("canvas#schematico").animateLayer(11, {
                        x: "421",
                        l1: "30",
                        l2: "91",
                        l3: "263",
                        l4: "99",
                        l5: "120"      
                        }, 4000);
                } else if ($("#spkPortSelect").val() === 6) {
                    $("canvas#schematico").animateLayer(11, {
                        x: "412",
                        l1: "30",
                        l2: "100",
                        l3: "263",
                        l4: "99",
                        l5: "120"      
                        }, 4000);
                } else if ($("#spkPortSelect").val() === 9) {
                    $("canvas#schematico").animateLayer(11, {
                        x: "375",
                        l1: "30",
                        l2: "137",
                        l3: "263",
                        l4: "99",
                        l5: "120"      
                        }, 4000);
                } else if ($("#spkPortSelect").val() === 10) {
                    $("canvas#schematico").animateLayer(11, {
                        x: "367",
                        l1: "30",
                        l2: "145",
                        l3: "263",
                        l4: "99",
                        l5: "120"
                        }, 4000);
                } else if ($("#spkPortSelect").val() === 11) {
                    $("canvas#schematico").animateLayer(11, {
                        x: "357",
                        l1: "30",
                        l2: "155",
                        l3: "263",
                        l4: "99",
                        l5: "120"     
                        }, 4000);
                }
            }

            if ($(".moduleBtn").hasClass("clicked")) {
                $("canvas#schematico").animateLayer(12, {
                  l1: "30"
                }, 4000);
                $("canvas#schematico").animateLayer(13, {
                  l1: "30"
                }, 4000);
                
                if ($("#btnPortSelect").val() === '2') {
                    $("canvas#schematico").animateLayer(14, {
                        x: "448",
                        l1: "50",
                        l2: "200",
                        l3: "520",
                        l4: "423",
                        l5: "108"      
                        }, 4000);
                } else if ($("#btnPortSelect").val() === 3) {
                    $("canvas#schematico").animateLayer(14, {
                        x: "439",
                        l1: "50",
                        l2: "209",
                        l3: "520",
                        l4: "423",
                        l5: "108"      
                        }, 4000);
                } else if ($("#btnPortSelect").val() === 4) {
                    $("canvas#schematico").animateLayer(14, {
                        x: "430",
                        l1: "50",
                        l2: "218",
                        l3: "520",
                        l4: "423",
                        l5: "108"      
                        }, 4000);
                } else if ($("#btnPortSelect").val() === 5) {
                    $("canvas#schematico").animateLayer(14, {
                        x: "421",
                        l1: "50",
                        l2: "227",
                        l3: "520",
                        l4: "423",
                        l5: "108"       
                        }, 4000);
                } else if ($("#btnPortSelect").val() === 6) {
                    $("canvas#schematico").animateLayer(14, {
                        x: "412",
                        l1: "50",
                        l2: "236",
                        l3: "520",
                        l4: "423",
                        l5: "108"       
                        }, 4000);
                } else if ($("#btnPortSelect").val() === 7) {
                    $("canvas#schematico").animateLayer(14, {
                        x: "403",
                        l1: "50",
                        l2: "245",
                        l3: "520",
                        l4: "423",
                        l5: "108"       
                        }, 4000);
                } else if ($("#btnPortSelect").val() === 9) {
                    $("canvas#schematico").animateLayer(14, {
                        x: "375",
                        l1: "30",
                        l2: "137",
                        l3: "263",
                        l4: "99",
                        l5: "120"      
                        }, 4000);
                } else if ($("#btnPortSelect").val() === 10) {
                    $("canvas#schematico").animateLayer(14, {
                        x: "367",
                        l1: "30",
                        l2: "145",
                        l3: "263",
                        l4: "99",
                        l5: "120"
                        }, 4000);
                } else if ($("#btnPortSelect").val() === 11) {
                    $("canvas#schematico").animateLayer(14, {
                        x: "357",
                        l1: "30",
                        l2: "155",
                        l3: "263",
                        l4: "99",
                        l5: "120"     
                        }, 4000);
                }
            }

            if ($(".moduleSrv").hasClass("clicked")) {
                $("canvas#schematico").animateLayer(15, {
                  l1: "30"
                }, 4000);
                $("canvas#schematico").animateLayer(16, {
                  l1: "37"
                }, 4000);
                
                if ($("#srvPortSelect").val() === 3) {
                    $("canvas#schematico").animateLayer(17, {
                        x: "439",
                        l1: "20",
                        l2: "110",
                        l3: "330",                              
                        }, 4000);
                } else if ($("#srvPortSelect").val() === 5) {
                    $("canvas#schematico").animateLayer(17, {
                        x: "421",
                        l1: "20",
                        l2: "128",
                        l3: "330",                             
                        }, 4000);
                } else if ($("#srvPortSelect").val() === 6) {
                    $("canvas#schematico").animateLayer(17, {
                        x: "412",
                        l1: "20",
                        l2: "137",
                        l3: "330",                             
                        }, 4000);
                } else if ($("#srvPortSelect").val() === 9) {
                    $("canvas#schematico").animateLayer(17, {
                        x: "375",
                        l1: "20",
                        l2: "174",
                        l3: "330",                             
                        }, 4000);
                } else if ($("#srvPortSelect").val() === 10) {
                    $("canvas#schematico").animateLayer(17, {
                        x: "367",
                        l1: "20",
                        l2: "182",
                        l3: "330",                        
                        }, 4000);
                } else if ($("#srvPortSelect").val() === 11) {
                    $("canvas#schematico").animateLayer(17, {
                        x: "357",
                        l1: "20",
                        l2: "192",
                        l3: "330",                             
                        }, 4000);
                }
            }
        
        }
        opened = opened ? false : true;
    });        

    /************************************************/
    /* Dataflow Module Handler
    /* jsPlumb - http://jsplumb.org                              
    /* @author - Ricardo Brazileiro
    /* @since - 04/2013                           
    /************************************************/  

    jsPlumb.importDefaults({
            Container:$("#modules")
    });
    
    var targetDropOptions = {
            tolerance:'touch',
            hoverClass:'dropHover',
            activeClass:'dragActive'
    };


    
    var sourceColor = "#E46B00";
    var targetColor = "#573816";

    var potSourceEndpoint = {
       endpoint:["Dot", { radius:12 }],
       paintStyle:{ fillStyle:sourceColor},
       isSource:true,
       scope:"test",
       connectorStyle:{ strokeStyle:sourceColor, lineWidth:12 },
       connector: ["Bezier", { curviness:60 } ],
       maxConnections: maxDropConnections
    };

    var ldrSourceEndpoint = {
       endpoint:["Dot", { radius:12 }],
       paintStyle:{ fillStyle:sourceColor},
       isSource:true,
       scope:"test",
       connectorStyle:{ strokeStyle:sourceColor, lineWidth:12 },
       connector: ["Bezier", { curviness:60 } ],
       maxConnections: maxDropConnections
    };

    var btnStateSourceEndpoint = {
       endpoint:["Dot", { radius:12 }],
       paintStyle:{ fillStyle:sourceColor},
       isSource:true,
       scope:"test",
       connectorStyle:{ strokeStyle:sourceColor, lineWidth:12 },
       connector: ["Bezier", { curviness:60 } ],
       maxConnections: maxDropConnections
    };

    var ledIntTargetEndpoint = {
       endpoint:["Dot", { radius:12 }],
       paintStyle:{ fillStyle:targetColor},    
       scope:"test",
       connectorStyle:{ strokeStyle:targetColor, lineWidth:12 },
       connector: ["Bezier", { curviness:63 } ],
       maxConnections:1,
       isTarget:true,
       dropOptions : targetDropOptions

    };

    var ledFreqTargetColor = "#ff0000";

    var ledFreqTargetEndpoint = {
       endpoint:["Dot", { radius:12 }],
       paintStyle:{ fillStyle:targetColor},       
       scope:"test",
       connectorStyle:{ strokeStyle:targetColor, lineWidth:12 },
       connector: ["Bezier", { curviness:63 } ],
       maxConnections:1,
       isTarget:true,
       dropOptions : targetDropOptions

    };

    var ledStateTargetEndpoint = {
       endpoint:["Dot", { radius:12 }],
       paintStyle:{ fillStyle:targetColor},
       //isSource:true,
       scope:"test",
       connectorStyle:{ strokeStyle:targetColor, lineWidth:12 },
       connector: ["Bezier", { curviness:63 } ],
       maxConnections:1,
       isTarget:true,
       dropOptions : targetDropOptions

    };    

    var spkFreqTargetEndpoint = {
       endpoint:["Dot", { radius:12 }],
       paintStyle:{ fillStyle:targetColor},
       //isSource:true,
       scope:"test",
       connectorStyle:{ strokeStyle:targetColor, lineWidth:12 },
       connector: ["Bezier", { curviness:63 } ],
       maxConnections:1,
       isTarget:true,
       dropOptions : targetDropOptions
    };

    var srvAngleTargetEndpoint = {
       endpoint:["Dot", { radius:12 }],
       paintStyle:{ fillStyle:targetColor},
       //isSource:true,
       scope:"test",
       connectorStyle:{ strokeStyle:targetColor, lineWidth:12 },
       connector: ["Bezier", { curviness:63 } ],
       maxConnections:1,
       isTarget:true,
       dropOptions : targetDropOptions
    };
    
    jsPlumb.draggable($(".module"));
    jsPlumb.draggable($("#trash"));
    jsPlumb.draggable($("#schema_content"));    
     
    jsPlumb.bind("jsPlumbConnection", function(info) {
        var whatTarget = info.targetId;
        var whatSource = info.sourceId;
        // $("#log").append("<li> target "+whatTarget+"</li>");
        // $("#log").append("<li> source "+whatSource+"</li>");
        var potSourceParameter = info.sourceEndpoint.getParameter("potFilterValue");        
        var ldrSourceParameter = info.sourceEndpoint.getParameter("ldrFilterValue");
        var btnSourceParameter = info.sourceEndpoint.getParameter("btnStateValue");        

        if (whatTarget === 'ledFreqModule') {
            if (whatSource === 'potFilterValueModule') {                                 
                potSourceParameter.on('change', function() {                                
                    var sourceValue = $(this).val();       
                    info.targetEndpoint.getParameter('ledFreqModule').slider('value', sourceValue);                
                });
            } else if (whatSource === 'ldrFilterValueModule') {                                 
                ldrSourceParameter.on('change', function() {                                
                    var sourceValue = $(this).val();       
                    info.targetEndpoint.getParameter('ledFreqModule').slider('value', sourceValue);                
                });
            }

        } else if (whatTarget === 'ledIntModule') {
            if (whatSource === 'potFilterValueModule') {                                 
                potSourceParameter.on('change', function() {                                
                    var sourceValue = $(this).val();       
                    var finalSourceValue = (sourceValue / potMaxValue);
                    info.targetEndpoint.getParameter('ledIntModule').slider('value', finalSourceValue);                                
                    
                });
            } else if (whatSource === 'ldrFilterValueModule') {                                 
                ldrSourceParameter.on('change', function() {                                
                    var sourceValue = $(this).val();       
                    var finalSourceValue = (sourceValue / ldrMaxValue);
                    info.targetEndpoint.getParameter('ledIntModule').slider('value', finalSourceValue);                                
                });
            } else if (whatSource === 'btnStateModule') {                                 
                btnSourceParameter.on('change', function() {                                
                    var sourceValue = $(this).val();                           
                    info.targetEndpoint.getParameter('ledIntModule').slider('value', sourceValue);                                
                });
            }
        } else if (whatTarget === 'stateSelect') {
            if (whatSource === 'btnStateModule') {                                 
                btnSourceParameter.on('change', function() {                                
                    var sourceValue = $(this).val();                           
                    if (sourceValue == 0) {
                        $('#btnLedOff').trigger("click");
                    } else {
                        $('#btnLedOn').trigger("click");
                    }
                });            
            }
        } else if (whatTarget === 'spkFreqModule') {            
            if (whatSource === 'potFilterValueModule') {                                 
                potSourceParameter.on('change', function() {                                
                    var sourceValue = $(this).val();       
                    if (sourceValue > 1) {
                        var swapSourceValue = (sourceValue / potMaxValue);                    
                        var finalSourceValue = (swapSourceValue * 33);
                        info.targetEndpoint.getParameter('spkFreqModule').slider('value', finalSourceValue);                                
                    }
                });
            } else if (whatSource === 'ldrFilterValueModule') {                                 
                ldrSourceParameter.on('change', function() {                                
                    var sourceValue = $(this).val();       
                    var swapSourceValue = (sourceValue / ldrMaxValue);
                    var finalSourceValue = (swapSourceValue * 33);
                    info.targetEndpoint.getParameter('spkFreqModule').slider('value', finalSourceValue);                                 
                });
            } else if (whatSource === 'btnStateModule') {                                 
                btnSourceParameter.on('change', function() {                                
                    var sourceValue = $(this).val();                           
                    var sourceValueRandom = Math.floor(Math.random() * 33) + 1
                    info.targetEndpoint.getParameter('spkFreqModule').slider('value', sourceValueRandom);                    
                });
            }            
        } else if (whatTarget === 'srvAngleValueModule') {
            if (whatSource === 'potFilterValueModule') {                                 
                potSourceParameter.on('change', function() {                                
                    var sourceValue = $(this).val();       
                    var finalSourceValue = (sourceValue / potMaxValue) * 180;
                    info.targetEndpoint.getParameter('srvAngleValueModule').anglepicker("value", finalSourceValue);                                
                    
                });
            } else if (whatSource === 'ldrFilterValueModule') {                                 
                ldrSourceParameter.on('change', function() {                                
                    var sourceValue = $(this).val();       
                    var finalSourceValue = (sourceValue / ldrMaxValue) * 180;
                    info.targetEndpoint.getParameter('srvAngleValueModule').anglepicker("value", finalSourceValue);                                
                });
            } else if (whatSource === 'btnStateModule') {                                 
                btnSourceParameter.on('change', function() {                                
                    var sourceValue = $(this).val();
                    var sourceValueRandom = Math.floor(Math.random() * 180) + 1                           
                    info.targetEndpoint.getParameter('srvAngleValueModule').anglepicker("value", sourceValueRandom);                                
                });
            }
        }

        $("#schema_content").trigger("click");                        
    });

    jsPlumb.bind("jsPlumbConnectionDetached", function(info) {
        var whatTarget = info.targetId;
        var whatSource = info.sourceId;
        var potSourceParameter = info.sourceEndpoint.getParameter("potFilterValue");       
        var ldrSourceParameter = info.sourceEndpoint.getParameter("ldrFilterValue");
        var btnSourceParameter = info.sourceEndpoint.getParameter("btnStateValue");
        if (whatTarget === "ledFreqModule" || whatTarget === "ledIntModule" || whatTarget === "stateSelect") {
            if (whatSource == "potFilterValueModule") {
                    led.stopBlinking();            
                    potSourceParameter.off();            
                    jsPlumb.detachAll(whatTarget);
            } else if (whatSource == "ldrFilterValueModule") {
                    led.stopBlinking();            
                    ldrSourceParameter.off();            
                    jsPlumb.detachAll(whatTarget);
            } else if (whatSource == "btnStateModule") {
                    led.stopBlinking();            
                    btnSourceParameter.off();            
                    jsPlumb.detachAll(whatTarget);
            }           
        } else if (whatTarget === "spkFreqModule") {
            if (whatSource == "potFilterValueModule") {
                    currentOSC.stop();                    
                    potSourceParameter.off();            
                    jsPlumb.detachAll(whatTarget);
            } else if (whatSource == "ldrFilterValueModule") {                    
                    currentOSC.stop();
                    ldrSourceParameter.off();            
                    jsPlumb.detachAll(whatTarget);
            }  else if (whatSource == "btnStateModule") {                    
                    currentOSC.stop();
                    btnSourceParameter.off();                    
                    jsPlumb.detachAll(whatTarget);
            }
        } else if (whatTarget === "srvAngleValueModule") {
            if (whatSource == "potFilterValueModule") {                    
                    potSourceParameter.off();            
                    jsPlumb.detachAll(whatTarget);
            } else if (whatSource == "ldrFilterValueModule") {                                        
                    ldrSourceParameter.off();            
                    jsPlumb.detachAll(whatTarget);
            }  else if (whatSource == "btnStateModule") {                    
                    btnSourceParameter.off();
                    jsPlumb.detachAll(whatTarget);
            }
        }
    });


    /************************************************/
    /* Canvas Setup
    /* jCanvas - http://calebevans.me/projects/jcanvas/                              
    /* @author - Ricardo Brazileiro
    /* @since - 04/2013
    /************************************************/  

    $("canvas#boards").addLayer({
            method: "drawImage",
        source: "assets/img/canvas/arduino_uno.png",
        x: 350, y: 150,
        // layer: true,
        visible: true,
        name: "controller"      
        }).addLayer({
            method: "drawImage",
            source: "assets/img/canvas/breadboard.png",
            x: 361.6, y: 400,
            // layer: true,
            visible: true,
            name: "breadboard"
    });
    $("canvas#output").addLayer({
        method: "drawImage",
        source: "assets/img/canvas/led_3mm.png",
        x: 472, y: 350,
        // layer: true,
        visible: true,
        name: "led"
      }).addLayer({
      method: "drawImage",
        source: "assets/img/canvas/resistor_220.png",
        x: 450, y: 375,
        // layer: true,
        visible: true,
        name: "resistor_22k"
      });
    $("canvas#output-spk").addLayer({
        method: "drawImage",
        source: "assets/img/canvas/piezo.png",
        rotate: 180,
        x: 416, y: 480,
        // layer: true,
        visible: true,
        name: "spk"
    });


    $("canvas#input-pot").addLayer({
        method: "drawImage",
        source: "assets/img/canvas/pot_small.png",
        rotate: 180,
        x: 297.5, y: 405,
        // layer: true,
        visible: true,
        name: "pot"
    });
    $("canvas#input-ldr").addLayer({
        method: "drawImage",
        source: "assets/img/canvas/ldr.png",
        rotate: 180,
        x: 348, y: 389,
        // layer: true,
        visible: true,
        name: "ldr"
    }).addLayer({
        method: "drawImage",
        source: "assets/img/canvas/resistor_220.png",
        x: 315, y: 403,
        // layer: true,
        visible: true,
        name: "resistor_22k"
    });

    $("canvas#input-btn").addLayer({
        method: "drawImage",
        source: "assets/img/canvas/button_off.png",
        rotate: 180,
        x: 214.5, y: 398,
        // layer: true,
        visible: true,
        name: "btn"
    }).addLayer({
      method: "drawImage",
        source: "assets/img/canvas/resistor_1k.png",
        x: 185, y: 421,
        // layer: true,
        visible: true,
        name: "resistor_22k"
      });

    $("canvas#output-srv").addLayer({
        method: "drawImage",
        source: "assets/img/canvas/servo.png",
        rotate: 180,
        x: 588, y: 430,
        // layer: true,
        visible: true,
        name: "srv"
    });

    $("canvas#schematico").addLayer({
        method: "drawVector",
        strokeStyle: "#ff0000",
        strokeWidth: 3,
        rounded: true,      
        // layer: true,
        visible: true,
        name: "board_vcc",
        bringToFront: true,        
        x: 375, y: 235,
        a1: 180, l1: 0,
        a2: -90, l2: 0,
        a3: 180, l3: 0
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#000",
            strokeWidth: 3,
            rounded: true,
            // layer: true,
            visible: true,
            name: "board_gnd",            
            bringToFront: true,
            x: 385, y: 235,
            a1: 180, l1: 0,
            a2: -90, l2: 0,
            a3: 180, l3: 0
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#000",
            strokeWidth: 3,
            rounded: true,
        // layer: true,
            visible: true,
            name: "led_gnd",
            bringToFront: true,
            x: 433, y: 331,
            a1: 180, l1: 0
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#000",
            strokeWidth: 3,
            rounded: true,
            // layer: true,
            visible: true,
            name: "pot_gnd",
            bringToFront: true,
            x: 304, y: 331,
            a1: 180, l1: 0
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#FF0000",
            strokeWidth: 3,
            rounded: true,
            // layer: true,
            visible: true,
            name: "pot_vcc",
            bringToFront: true,
            x: 290, y: 340,
            a1: 180, l1: 0
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#0000FF",
            strokeWidth: 3,
            rounded: true,
            // layer: true,
            visible: true,
            name: "pot_port",
            bringToFront: true,
            x: 420, y: 235,
            a1: 180, l1: 0,
            a2: 270, l2: 0,
            a3: 180, l3: 0        
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#0000FF",
            strokeWidth: 3,
            rounded: true,
            // layer: true,
            visible: true,
            name: "led_port",
            bringToFront: true,
            x: 375, y: 65,
            a1: 0, l1: 0,
            a2: 90, l2: 0,
            a3: 180, l3: 0,
            a4: 270, l4: 0
            // a2: 270, l2: 123,
            // a3: 180, l3: 57        
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#FF0000",
            strokeWidth: 3,
            rounded: true,
            // layer: true,
            visible: true,
            name: "ldr_vcc",
            bringToFront: true,
            x: 333, y: 340,
            a1: 180, l1: 0
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#000",
            strokeWidth: 3,
            rounded: true,
            // layer: true,
            visible: true,
            name: "ldr_gnd",
            bringToFront: true,
            x: 398, y: 331,
            a1: 180, l1: 0
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#0000FF",
            strokeWidth: 3,
            rounded: true,
            // layer: true,
            visible: true,
            name: "ldr_port",
            bringToFront: true,
            x: 430, y: 235,
            a1: 180, l1: 0,
            a2: 270, l2: 0,
            a3: 180, l3: 0        
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#000",
            strokeWidth: 3,
            rounded: true,
        // layer: true,
            visible: true,
            name: "spk_gnd",
            bringToFront: true,
            x: 420, y: 331,
            a1: 180, l1: 0
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#0000FF",
            strokeWidth: 3,
            rounded: true,
            // layer: true,
            visible: true,
            name: "spk_port",
            bringToFront: true,
            x: 375, y: 65,
            a1: 0, l1: 0,
            a2: 90, l2: 0,
            a3: 180, l3: 0,
            a4: 270, l4: 0,
            a5: 180, l5: 0            
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#000",
            strokeWidth: 3,
            rounded: true,
            // layer: true,
            visible: true,
            name: "btn_gnd",
            bringToFront: true,
            x: 262, y: 331,
            a1: 180, l1: 0
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#FF0000",
            strokeWidth: 3,
            rounded: true,
            // layer: true,
            visible: true,
            name: "btn_vcc",
            bringToFront: true,
            x: 204, y: 340,
            a1: 180, l1: 0
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#0000FF",
            strokeWidth: 3,
            rounded: true,
            // layer: true,
            visible: true,
            name: "btn_port",
            bringToFront: true,
            x: 330, y: 65,
            a1: 0, l1: 0,
            a2: 90, l2: 0,
            a3: 180, l3: 0,
            a4: 270, l4: 0,
            a5: 0, l5: 0
            // a2: 270, l2: 123,
            // a3: 180, l3: 57        
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#000",
            strokeWidth: 3,
            rounded: true,
        // layer: true,
            visible: true,
            name: "srv_gnd",
            bringToFront: true,
            x: 563, y: 331,
            a1: 180, l1: 0
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#FF0000",
            strokeWidth: 3,
            rounded: true,
        // layer: true,
            visible: true,
            name: "srv_vcc",
            bringToFront: true,
            x: 556, y: 331,
            a1: 180, l1: 0
        }).addLayer({
            method: "drawVector",
            strokeStyle: "#0000FF",
            strokeWidth: 3,
            rounded: true,
            // layer: true,
            visible: true,
            name: "srv_port",
            bringToFront: true,
            x: 367, y: 65,
            a1: 0, l1: 0,
            a2: 90, l2: 0,
            a3: 180, l3: 0    
        });        
    $("canvas#boards").drawLayers();    
});