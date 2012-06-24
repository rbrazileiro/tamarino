##
# Data de criação: 23/06/2012
# Autor: Giovane Boaviagem
#
# Script auxiliar de compilação e linkagem do código fonte do projeto arduino. 
##
#-Wl,--gc-sections

# Constantes
DIR="/home/giovane/workspace/java/tamarino/cpp"
GCC_INCLUDES="-I'$DIR/arduino/core/src' -I'$DIR/arduino/core/src/libraries/Servo'"
FLAGS="-Wall -Os -ffunction-sections -fdata-sections -fno-exceptions"
FLAGS_CPU="-mmcu=atmega168 -DF_CPU=16000000UL"

GCC_BIN=tools/linux64bits/avr/bin/avr-g++

compile(){	
	echo "$GCC_BIN $GCC_INCLUDES $FLAGS $FLAGS_CPU"
	$GCC_BIN $GCC_INCLUDES $FLAGS $FLAGS_CPU -MMD -MP -MF'../temp/main.d' -MT'../temp/main.d' -c -o '../temp/main.o' 'arduino/core/src/main.cpp'
}

compile