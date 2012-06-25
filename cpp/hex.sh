##
# Data de criação: 23/06/2012
# Autor: Giovane Boaviagem
#
# Script auxiliar de compilação e linkagem do código fonte do projeto arduino. 
##
#

GCC_BIN=tools/linux64bits/avr/bin/avr-g++

##
#
##
compileDiecimila(){	
	#$GCC_BIN $GCC_INCLUDES $FLAGS $FLAGS_CPU -MMD -MP -MF"$DIR/temp/main.d" -MT"$DIR/temp/main.d" -c -o "$DIR/temp/main.o" "$DIR/cpp/arduino/core/src/main.cpp"
	$GCC_BIN \
	-I"arduino/core/src" \
	-Wall \
	-Os \
	-ffunction-sections \
	-fdata-sections \
	-fno-exceptions \
	"-Wl,--gc-sections" \
	-mmcu=atmega168 \
	-DF_CPU=16000000UL \
	-MMD \
	-MP \
	-MF"../temp/main.d" \
	-MT"../temp/main.d" \
	-c -o "../temp/main.o" "../temp/main.cpp" 
}

compileDiecimila