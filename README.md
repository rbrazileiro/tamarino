#tAMARINO v.0.1.0
A visual approach to rapid prototyping in physical computing

tAMARINO is a visual approach to rapid prototyping in physical computing. It proposes an unique and intuitive visual enviroment toolkit to accelerate physical computing prototypes. The evaluation reveal tAMARINO' success to straightforward quick development - even on first-time prototyping – further lowering the time-to-market. This first version is designed for Arduino microcontrollers, but is obviously extendable to many other boards.

## Video
* [Screencast](https://vimeo.com/65594452)
	about the features

## Requirements
* [Breakout.js](https://github.com/soundanalogous/Breakout)
	Javascript library and toolkit for connecting Arduino and other IOBoards to the web
* [jsPlumb](http://jsplumb.org)
	jsPlumb provides a way to connect elements in a UI.
* [jCanvas](http://jsplumb.org)
	 jQuery plugin that makes the HTML5 canvas easy to work with.
* [jQuery](http://jquery.com)
	 jQuery is a fast, small, and feature-rich JavaScript library.
* [Bootstrap](http://getbootstrap.com)
	 Sleek, intuitive, and powerful mobile first front-end framework for faster and easier web development.


## Build

Make the steps below to build tAMARINO:

* Start a Breakout.js Server. For linux, you need install:
	```
	sudo apt-get install librxtx-java
	```

and run
	```
	java -jar BreakoutServer.jar
	```

set the default folder to tAMARINO repository.

* Open a browser (Chrome, Firefox and Safari), type http://localhost:8887 and enjoy!

more about Breakout Server in [Breakoutjs Server Guide](http://breakoutjs.com/guides/using-breakout-server/)


## Visual Prototype
Boards and Components Images by [Fritzing](http://fritzing.org)

## License

The MIT License

Copyright (c) 2013 Ricardo Brazileiro, https://github.com/rbrazileiro/tamarino

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


