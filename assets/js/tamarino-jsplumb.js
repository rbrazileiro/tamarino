$(document).ready(function() {
    // FRONT-END jQUERY UI
    $("#potMinSlider").slider({
                min: 0,
                max: 255,
                step: 1,
                value: 0,
                slide: function( event, ui ) {
                    $( "#potMinSliderValue" ).val( ui.value );
                }
    });
    $( "#potMinSliderValue" ).val( $( "#potMinSlider" ).slider( "value" ) );                    
 
    $("#potMaxSlider").slider({
                min: 0,
                max: 255,
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
                }
    });
    $( "#ledFreqSliderValue" ).val( $( "#ledFreqSlider" ).slider( "value" ) + " ms");

    $("#ledIntSlider").slider({
                min: 0,
                max: 255,
                step: 1,
                value: 0,
                slide: function( event, ui ) {
                    $( "#ledIntSliderValue" ).val( ui.value + " ~" );
                }
    });
    $( "#ledIntSliderValue" ).val( $( "#ledIntSlider" ).slider( "value" ) + " ~");

    $("#potPortSpinner").spinner({
                min: 0,
                max: 5,
                step: 1,
                value: 0
    });

    $("#ledPortSpinner").spinner({
                min: 0,
                max: 5,
                step: 1,
                value: 0
    });

    $( "#radio" ).buttonset();


// BACK-END jSPLUMB DATAFLOW UI
    // var a = $("#a");
    // var b = $("#b");
    jsPlumb.importDefaults({
            Container:$("body")
    });
    
    //Setting up drop options
    var targetDropOptions = {
            tolerance:'touch',
            hoverClass:'dropHover',
            activeClass:'dragActive'
    };


    
    //Setting up a Source endPoint for Potentiometer
    var sourceColor = "#e49956";
    var targetColor = "#411411";

    var potSourceEndpoint = {
       endpoint:["Dot", { radius:16 }],
       paintStyle:{ fillStyle:sourceColor},
       isSource:true,
       scope:"test",
       connectorStyle:{ strokeStyle:sourceColor, lineWidth:16 },
       connector: ["Bezier", { curviness:60 } ],
       maxConnections:-1
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

    
    jsPlumb.addEndpoint($(".potFilterValueModule") , { anchor:"RightMiddle", parameters:{potFilterValue:$('#potFilterValue')} }, potSourceEndpoint );
    jsPlumb.addEndpoint($(".ledFreqModule") , { anchor:[0, 0.65, 0, 1], parameters:{ledFreqModule:$("#ledFreqSlider") }}, ledFreqTargetEndpoint );
    jsPlumb.addEndpoint($(".ledIntModule") , { anchor:[0, 0.65, 0, 1], parameters:{ledIntModule:$("#ledIntSlider")} }, ledIntTargetEndpoint);

    jsPlumb.draggable($(".module"));
    jsPlumb.animate($("#led"), {"left": 100,"top": 150},{duration:"slow"});       
     
    jsPlumb.bind("jsPlumbConnection", function(info) {       
        var sourceValue = info.sourceEndpoint.getParameter("potFilterValue").val();
        var whatTarget = info.targetId;       
        if (whatTarget === "ledFreqModule") {
            info.targetEndpoint.getParameter("ledFreqModule").slider("value", sourceValue);
        }else{
            info.targetEndpoint.getParameter("ledIntModule").slider("value", sourceValue);
        } 
                
    });

    jsPlumb.bind("jsPlumbConnectionDetached", function(info) {
        var whatTarget = info.targetId;       
        if (whatTarget === "ledFreqModule") {
            jsPlumb.detachAll("ledFreqModule");
        }else{
            jsPlumb.detachAll("ledIntModule");
        } 
    });
    
    
});