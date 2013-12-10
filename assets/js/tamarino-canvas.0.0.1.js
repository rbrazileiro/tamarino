// CANVAS SCHEMATIC PROTOTYPING
$(document).ready(function() {
    $("#boards").addLayer({
            method: "drawImage",
        source: "arduino_uno.png",
        x: 350, y: 150,
        // layer: true,
      visible: true,
        name: "controller"      
        }).addLayer({
            method: "drawImage",
            source: "breadboard.png",
            x: 361.6, y: 400,
            // layer: true,
        visible: true,
            name: "breadboard"
    });
    $("#output").addLayer({
        method: "drawImage",
        source: "led_3mm.png",
        x: 472, y: 350,
        // layer: true,
        visible: true,
        name: "led"
      }).addLayer({
      method: "drawImage",
        source: "resistor_220.png",
        x: 450, y: 375,
        // layer: true,
        visible: true,
        name: "resistor_22k"
    });
      $("#input").addLayer({
        method: "drawImage",
        source: "pot_small.png",
        rotate: 180,
        x: 297.5, y: 405,
        // layer: true,
        visible: true,
        name: "pot"
    });

    $("#schematico").addLayer({
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
    });

    $("#boards").drawLayers();    
    $("#output").drawLayers();
    $("#input").drawLayers();

});