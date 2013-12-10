/******************************************************/
/* tAMARINO v.0.0.7b
/* Toolkit for Rapid Prototyping in Physical Computing Projects
/* based on Breakoutjs (http://breakoutjs.com)
/* based on jsPlumb (http://jsplumb.org)


/* ricardo brazileiro aka rbrz
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
    var Potentiometer = BO.io.Potentiometer;
    var PotEvent = BO.io.PotEvent;
    var Scaler = BO.filters.Scaler;
    var Convolution = BO.filters.Convolution;
    var TriggerPoint = BO.filters.TriggerPoint;


    var LED = BO.io.LED;
    var leds = [];
    var led;
    
    var pots = [];
    var pot;    
    var currentEq;
    var potMinValue = 0;
    var potMaxValue = 1;
    var potArray = [];
    var potArrayLength = 30;    
    var potLock = 0;
    var potPortValue = 0;

    var scalerFilter;
    var smooth;


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

        led = new LED(arduino, arduino.getDigitalPin(9));
        //arduino.setDigitalPinMode(9, Pin.PWM);

        $('#btnLedOn').on('click', turnLedOn);
        $('#btnLedOff').on('click', turnLedOff);
        
        potActive(0);
        
        initGUIPotListeners();        
        
        
    }
    
    /************************************************/
    /* FUNCTIONS                                    
    /* BINDS                              
    /*                                   
    /************************************************/    

    /* POTENTIOMETER */

    function potActive(value) {        
        if (pots[0] == null) {
            pots.push(value);            
        } else {
            pots.push(value);                            
            if (pots[0] < pots[1]) {                
                arduino.disableAnalogPin(pots[0]);
                pots[0] = pots[1];
                pots.length = 1;
            } else {
                arduino.disableAnalogPin(pots[0]);                
                arduino.disableAnalogPin(pots[1]);
                pots[0] = pots[1];
                pots.length = 1;
            }                    
        }

        arduino.enableAnalogPin(value);
        pot = arduino.getAnalogPin(value);
        pot.clear();                
        currentEq = Scaler.LINEAR;
        smooth = new Convolution(Convolution.MOVING_AVERAGE);
        triggerPoint = new TriggerPoint();
        scalerFilter = new Scaler(0, 1, potMinValue, potMaxValue, Scaler.LINEAR);        
        pot.addFilter(smooth);
        pot.addFilter(scalerFilter);        
        pot.addEventListener(PinEvent.CHANGE, onPotChange);
    }

    function onPotChange(event) {
        var pin = event.target;
        var potLastVal = (potArray[potArrayLength-1]+50);        
        var potLastValLess = (potArray[potArrayLength-1]-50);

        if (potArray[potArrayLength-1] == null) {
            if(pin.value > 0) {                
                potLock = 1;
            }
        } else {
            if(pin.value > potLastVal || pin.value < potLastValLess){                
                potArray.length = 0;
                potLock = 1;
            }            
        }               
                
        while (potLock == 1) {                    
            potArray.push(pin.value);                        
            if (potArray.length === potArrayLength) {
                var potVal = potArray[potArrayLength-1];                            
                $('#potFilterValue').val(potVal.toFixed(1)).trigger('change');                            
                potLock = 0;
                onPotChange();
            }
        }
    }

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

    function addFilter(type, label) {
        pot.addFilter(new Scaler(0, 1, potMinValue, potMaxValue, type));
        currentEq = type;
    }

    function turnLedOn(evt) {
        // Turn the LED on
        led.stopBlinking();        
        led.on();
    }

    function turnLedOff(evt) {
        // Turn the LED off                
        led.stopBlinking();
        led.off();  
    }


    $( "#ledPortSpinner" ).bind( "spinchange", function() {
        var ledPortValue = $("#ledPortSpinner").spinner("value");        
        led = leds[ledPortValue -3];
    });

    $( "#ledFreqSlider" ).bind( "slide slidechange", function(event, ui) {
        var ledFreqValue = ui.value;
        led.blink(ledFreqValue, 0);
    });    

    $( "#ledIntSlider" ).bind( "slide slidechange", function(event, ui) {
        var ledIntValue = ui.value;        
        led.fadeTo(ledIntValue, 100);
    });

    $( "#potPortSpinner" ).bind( "spinchange", function() {
        potPortValue = $("#potPortSpinner").spinner("value");
        potActive(potPortValue);        
    });


   
// FRONT-END UI
    // var potPortSchematic;
    // if (potPortValue == 0) {
    //     potPortSchematic = 420;
    // }
    // if (potPortValue == 1) {
    //     potPortSchematic =430;
    // }
    // if (potPortValue == 2) {
    //     potPortSchematic =440;
    // }

// SCHEMATIC AREA
    var opened = false;
    $("#schema_content").hide();
    $("#schema_button, #schematic_control").click(function(){
        if(opened){
            // $("#schema_tab").animate({"bottom": "-=20%"}, "slow");
            // $("#schema_tab").animate({"bottom": "-=70%"});            
            $("#schema_tab").animate({"min-height": "3%"});            
            $("#schema_content").hide("slow");
            // $("#modules").show("slow");

            // $("#trash").show();            
        }else{
            // $("#schema_tab").animate({"bottom": "+=70%"});                    
            $("#schema_tab").animate({"min-height": "80%"});                                    
            $("#schema_content").show();            
            // $("#modules").hide("slow");

            $("canvas#schematico").animateLayer(0, {
                l1: "107"
                }, 3000);
            $("canvas#schematico").animateLayer(1, {
                l1: "98"
                }, 3000);

            if ($(".modulePot").hasClass("clicked")) {
                $("canvas#schematico").animateLayer(3, {
                    l1: "30"
                    }, 4000);
                $("canvas#schematico").animateLayer(4, {
                    l1: "35"
                }, 4000);

                if ($("#potPortSpinner").spinner("value") == 0) {
                    $("canvas#schematico").animateLayer(5, {                                            
                        l1: "75",
                        l2: "123",
                        l3: "57"      
                        }, 4000);
                } else if ($("#potPortSpinner").spinner("value") == 1) {
                    $("canvas#schematico").animateLayer(5, {
                        x: 430,                                            
                        l1: "75",
                        l2: "133",
                        l3: "57"      
                        }, 4000);
                } else if ($("#potPortSpinner").spinner("value") == 2) {
                    $("canvas#schematico").animateLayer(5, {
                        x: 438,                                            
                        l1: "75",
                        l2: "141",
                        l3: "57"      
                        }, 4000);
                } else if ($("#potPortSpinner").spinner("value") == 3) {
                    $("canvas#schematico").animateLayer(5, {
                        x: 448,                                            
                        l1: "75",
                        l2: "151",
                        l3: "57"      
                        }, 4000);
                } else if ($("#potPortSpinner").spinner("value") == 4) {
                    $("canvas#schematico").animateLayer(5, {
                        x: 458,                                            
                        l1: "75",
                        l2: "161",
                        l3: "57"      
                        }, 4000);
                } else if ($("#potPortSpinner").spinner("value") == 5) {
                    $("canvas#schematico").animateLayer(5, {
                        x: 467,                                            
                        l1: "75",
                        l2: "170",
                        l3: "57"      
                        }, 4000);
                }                
            }

            if ($(".moduleLed").hasClass("clicked")) {
                $("canvas#schematico").animateLayer(2, {
                  l1: "30"
                }, 4000);                
                // $("canvas#schematico").animateLayer(6, {
                //   l1: "30",
                //   l2: "150",
                //   l3: "348",
                //   l4: "46"      
                // }, 4000);

                
                if ($("#ledPortSpinner").spinner("value") == 3) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "439",
                        l1: "30",
                        l2: "102",
                        l3: "348",
                        l4: "64"      
                        }, 4000);
                } else if ($("#ledPortSpinner").spinner("value") == 4) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "430",
                        l1: "30",
                        l2: "110",
                        l3: "348",
                        l4: "64"      
                        }, 4000);
                } else if ($("#ledPortSpinner").spinner("value") == 5) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "421",
                        l1: "30",
                        l2: "120",
                        l3: "348",
                        l4: "64"      
                        }, 4000);
                } else if ($("#ledPortSpinner").spinner("value") == 6) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "412",
                        l1: "30",
                        l2: "120",
                        l3: "348",
                        l4: "56"      
                        }, 4000);
                } else if ($("#ledPortSpinner").spinner("value") == 7) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "403",
                        l1: "30",
                        l2: "130",
                        l3: "348",
                        l4: "56"      
                        }, 4000);
                } else if ($("#ledPortSpinner").spinner("value") == 8) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "385",
                        l1: "30",
                        l2: "150",
                        l3: "348",
                        l4: "56"      
                        }, 4000);
                } else if ($("#ledPortSpinner").spinner("value") == 9) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "375",
                        l1: "30",
                        l2: "150",
                        l3: "348",
                        l4: "46"      
                        }, 4000);
                } else if ($("#ledPortSpinner").spinner("value") == 10) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "367",
                        l1: "30",
                        l2: "150",
                        l3: "348",
                        l4: "38"      
                        }, 2000);
                } else if ($("#ledPortSpinner").spinner("value") == 11) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "357",
                        l1: "30",
                        l2: "150",
                        l3: "348",
                        l4: "28"      
                        }, 2000);
                } else if ($("#ledPortSpinner").spinner("value") == 12) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "347",
                        l1: "30",
                        l2: "150",
                        l3: "348",
                        l4: "18"      
                        }, 2000);
                } else if ($("#ledPortSpinner").spinner("value") == 13) {
                    $("canvas#schematico").animateLayer(6, {
                        x: "338",
                        l1: "30",
                        l2: "160",
                        l3: "348",
                        l4: "19"      
                        }, 2000);
                }
            }


            // jsPlumb.addEndpoint($("#breadboard") , { anchor:"LeftMiddle", container:"#schema_content"}, breadboardSource);
            // $("#trash").hide();           
        }
        opened = opened ? false : true;
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

    $("#ledFreqSlider").slider({
                min: 0,
                max: 5000,
                step: 1,
                value: 0,
                slide: function( event, ui ) {
                    $( "#ledFreqSliderValue" ).val( ui.value + " ms" );
                },
                change: function(event, ui) {
                    $( "#ledFreqSliderValue" ).val( ui.value);
                }
    });

    $( "#ledFreqSliderValue" ).val( $( "#ledFreqSlider" ).slider( "value" ) + " ms");

    $("#ledIntSlider").slider({
                min: 0,
                max: 1,
                step: 0.1,
                value: 0,
                slide: function( event, ui ) {
                    $( "#ledIntSliderValue" ).val( ui.value + " ~" );
                },
                change: function(event, ui) {
                    $( "#ledIntSliderValue" ).val( ui.value);
                }
    });

    $( "#ledIntSliderValue" ).val( $( "#ledIntSlider" ).slider( "value" ) + " ~");

    $("#potPortSpinner").spinner({
                min: 0,
                max: 5,
                step: 1,
                value: 0,
                stop: function( event, ui ) {
                    $(this).trigger("spinchange");
                }
    });

    $("#ledPortSpinner").spinner({
                min: 0,
                max: 13,
                step: 1,
                value: 9,
                stop: function( event, ui ) {
                    $(this).trigger("spinchange");
                }
    });

    $( "#radio" ).buttonset();

    $( ".module" ).hide();    

    $( "#input_control").mouseenter(function () {
        $(this).children('ul').slideToggle();
    }).mouseleave(function() {
        $(this).children('ul').slideToggle();
    });

    $( "#output_control").mouseenter(function () {
        $(this).children('ul').slideToggle();
    }).mouseleave(function() {
        $(this).children('ul').slideToggle();
    });    

    $( ".modulePot").on("click", function (){        
        $(".pot").show();
        // var butId = $(this).attr('class');                
        jsPlumb.addEndpoint($(".potFilterValueModule") , { anchor:"RightMiddle", parameters:{potFilterValue:$('#potFilterValue')} }, potSourceEndpoint );                
        jsPlumb.animate($(".pot"), {"left": 100,"top": 150},{duration:"slow"});
        $("canvas#input").setLayer("pot", {
                visible: true                
                }).drawLayers();    

        $(this).addClass("clicked");

    });

    $( ".moduleLed").on("click", function (){
        $(".led").show();
        jsPlumb.addEndpoint($(".ledFreqModule") , { anchor:[0, 0.65, 0, 1], parameters:{ledFreqModule:$("#ledFreqSlider") }}, ledFreqTargetEndpoint );
        jsPlumb.addEndpoint($(".ledIntModule") , { anchor:[0, 0.65, 0, 1], parameters:{ledIntModule:$("#ledIntSlider")} }, ledIntTargetEndpoint);        
        jsPlumb.animate($(".led"), {"left": 650,"top": 250},{duration:"slow"});        
        $("canvas#output").setLayer("led", {
                visible: true                
                }).drawLayers();       
        $(this).addClass("clicked");
    });

    $('#trash').droppable({
        over: function(event, ui) {
            $(ui.draggable).hide();
            var element = $(ui.draggable);            
            var potEndPoint = $(ui.draggable).find('input').parent();
            var potEndPointClass = $(ui.draggable).find('input').parent().hasClass('potFilterValueModule');;            
            var ledEndPoint = $(ui.draggable).find('div#ledFreqSlider').parent();
            var ledEndPointInt = $(ui.draggable).find('div#ledIntSlider').parent();
            var ledEndPointClass = ledEndPoint.hasClass('ledFreqModule');            
            // $("#log").append("<li>"+potEndPoint+"</li>");
            // $("#log").append("<li>"+ledEndPoint+"</li>");

            if (potEndPointClass == true) {
                //$("#log").append("<li> sim </li>");                
                //jsPlumb.removeAllEndpoints(potSourceEndpoint);
                //potSourceEndpoint.setVisible(false);
                jsPlumb.selectEndpoints({element:potEndPoint}).setVisible(false);
                $("canvas#input").setLayer("pot", {
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
                //jsPlumb.removeEndpoint("potEndPointt", potSourceEndpoint);
                //jsPlumb.removeAllEndpoints(potEndPointt);
            } else {
                jsPlumb.selectEndpoints({element:ledEndPoint}).setVisible(false);
                jsPlumb.selectEndpoints({element:ledEndPointInt}).setVisible(false);
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
                //jsPlumb.removeEndpoint("ledEndPoint", ledIntTargetEndpoint);
                //jsPlumb.removeEndpoint("ledEndPoint", ledFreqTargetEndpoint);
            }
            

            //
        }
    });

// CANVAS

    $("canvas#boards").addLayer({
            method: "drawImage",
        source: "libs/canvas/arduino_uno.png",
        x: 350, y: 150,
        // layer: true,
      visible: true,
        name: "controller"      
        }).addLayer({
            method: "drawImage",
            source: "libs/canvas/breadboard.png",
            x: 361.6, y: 400,
            // layer: true,
        visible: true,
            name: "breadboard"
    });
    $("canvas#output").addLayer({
        method: "drawImage",
        source: "libs/canvas/led_3mm.png",
        x: 472, y: 350,
        // layer: true,
        visible: true,
        name: "led"
      }).addLayer({
      method: "drawImage",
        source: "libs/canvas/resistor_220.png",
        x: 450, y: 375,
        // layer: true,
        visible: true,
        name: "resistor_22k"
      });
      $("canvas#input").addLayer({
        method: "drawImage",
        source: "libs/canvas/pot_small.png",
        rotate: 180,
        x: 297.5, y: 405,
        // layer: true,
        visible: true,
        name: "pot"
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
        a1: 180, l1: 0
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
            a1: 180, l1: 0
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
        
      });

        //$("#schematico").drawLayers();
    $("canvas#boards").drawLayers();
    // $("canvas#schematico").drawLayer("board_vcc");
    // $("canvas#schematico").drawLayer("board_gnd");
    // $("canvas#schematico").drawLayer("led_gnd");
    // $("canvas#schematico").drawLayer("pot_gnd");
    // $("canvas#schematico").drawLayer("pot_vcc");
    // $("canvas#schematico").drawLayer("pot_port");
    // $("canvas#schematico").drawLayer("led_port");       

    // $("canvas#schematico").animateLayer(0, {
    //   l1: "107"
    // }, 1000);
    // $("canvas#schematico").animateLayer(1, {
    //   l1: "98"
    // }, 1000);
    // $("canvas#schematico").animateLayer(2, {
    //   l1: "30"
    // }, 1000);
    // $("canvas#schematico").animateLayer(3, {
    //   l1: "30"
    // }, 1000);
    // $("canvas#schematico").animateLayer(4, {
    //   l1: "35"
    // }, 1000);
    // $("canvas#schematico").animateLayer(5, {
    //   l1: "75",
    //   l2: "123",
    //   l3: "57"      
    // }, 1000);

    // $("canvas#schematico").animateLayer(6, {
    //   l1: "30",
    //   l2: "150",
    //   l3: "348",
    //   l4: "46"      
    // }, 1000);


// BACK-END jSPLUMB DATAFLOW UI

    jsPlumb.importDefaults({
            Container:$("#modules")
    });
    
    //Setting up drop options
    var targetDropOptions = {
            tolerance:'touch',
            hoverClass:'dropHover',
            activeClass:'dragActive'
    };


    
    //Setting up a Source endPoint for Potentiometer
    var sourceColor = "#E46B00";
    var targetColor = "#573816";

    var potSourceEndpoint = {
       endpoint:["Dot", { radius:16 }],
       paintStyle:{ fillStyle:sourceColor},
       isSource:true,
       scope:"test",
       connectorStyle:{ strokeStyle:sourceColor, lineWidth:16 },
       connector: ["Bezier", { curviness:60 } ],
       maxConnections:1
    };
    
    //Setting up a Target endPoint for Led Intensity
    

    var ledIntTargetEndpoint = {
       endpoint:["Dot", { radius:16 }],
       paintStyle:{ fillStyle:targetColor},
       //isSource:true,
       scope:"test",
       connectorStyle:{ strokeStyle:targetColor, lineWidth:16 },
       connector: ["Bezier", { curviness:63 } ],
       maxConnections:1,
       isTarget:true,
       dropOptions : targetDropOptions

    };

    //Setting up a Target endPoint for Led Frequency
    var ledFreqTargetColor = "#ff0000";

    var ledFreqTargetEndpoint = {
       endpoint:["Dot", { radius:16 }],
       paintStyle:{ fillStyle:targetColor},
       //isSource:true,
       scope:"test",
       connectorStyle:{ strokeStyle:targetColor, lineWidth:16 },
       connector: ["Bezier", { curviness:63 } ],
       maxConnections:1,
       isTarget:true,
       dropOptions : targetDropOptions

    };

    
    // jsPlumb.addEndpoint($(".potFilterValueModule") , { anchor:"RightMiddle", parameters:{potFilterValue:$('#potFilterValue')} }, potSourceEndpoint );
    // jsPlumb.addEndpoint($(".ledFreqModule") , { anchor:[0, 0.65, 0, 1], parameters:{ledFreqModule:$("#ledFreqSlider") }}, ledFreqTargetEndpoint );
    // jsPlumb.addEndpoint($(".ledIntModule") , { anchor:[0, 0.65, 0, 1], parameters:{ledIntModule:$("#ledIntSlider")} }, ledIntTargetEndpoint);

    jsPlumb.draggable($(".module"));
    jsPlumb.draggable($("#trash"));

    // jsPlumb.selectEndpoints({scope:"test"}).each(function(endpoint) {
    //     endpoint.setVisible(false);       
    // });

    //jsPlumb.hide("potSourceEndpoint", true);
    //jsPlumb.hide($(".potFilterValueModule"));
    //jsPlumb.recalculateOffsets(".module");
    //jsPlumb.animate($("#led"), {"left": 100,"top": 150},{duration:"slow"});       
     
    jsPlumb.bind("jsPlumbConnection", function(info) {
        var whatTarget = info.targetId;
        var whatSource = info.sourceId;
        var sourceParameter = info.sourceEndpoint.getParameter("potFilterValue");        

        if (whatTarget === 'ledFreqModule') {                                 
            sourceParameter.on('change', function() {                                
                var sourceValue = $(this).val();       
                info.targetEndpoint.getParameter('ledFreqModule').slider('value', sourceValue);                
            });

        } else {
            sourceParameter.on('change', function() {                
                var sourceValue = $(this).val();
                var finalSourceValue = (sourceValue / potMaxValue);
                info.targetEndpoint.getParameter('ledIntModule').slider('value', finalSourceValue);                                
            });            

        }
        $("#schema_button").trigger("click");
                        
    });

    jsPlumb.bind("jsPlumbConnectionDetached", function(info) {
        var whatTarget = info.targetId;
        var sourceParameter = info.sourceEndpoint.getParameter("potFilterValue");       
        if (whatTarget === "ledFreqModule") {
            led.stopBlinking();            
            sourceParameter.off();
            jsPlumb.detachAll(whatTarget);
        } else {
            led.stopBlinking();
            sourceParameter.off();
            jsPlumb.detachAll(whatTarget);
        } 
    });
    
});

//$("#log").append("<li>"+sourceValue+"</li>");