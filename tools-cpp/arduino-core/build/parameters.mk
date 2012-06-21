#parameters.mk

# Created on: 19/06/2012
#     Author: giovane

##############
# PARAMETROS #
##############

# Tipo da placa. Valores validos:
# - UNO
# - DIECIMILA
TYPE=DIECIMILA

ifeq ($(TYPE),UNO)
	FLAGS_CPU=-mmcu=atmega328p -DF_CPU=16000000L
	PINS_ARDUINO_FOLDER=standard
endif
ifeq ($(TYPE),DIECIMILA)
	FLAGS_CPU=-mmcu=atmega168 -DF_CPU=16000000L
	PINS_ARDUINO_FOLDER=standard
endif


# CONSTANTES #
CC=avr-gcc
CXX=avr-g++
VARIANTS_ORIGIN=src/variants
FLAGS_INCLUDES=-I"/mnt/dados/workspace/cpp/arduino-core/src"
COMMON_FLAGS=-Wall -Os -ffunction-sections -fdata-sections -fno-exceptions -MMD -MP
OUTPUT=../bin/$(TYPE)
OUTPUT_OBJECT=$(OUTPUT)/objects
OUTPUT_DEPENDENCIES=$(OUTPUT)/dependencies
INPUT=../src
FILE=libarduino-core