#parameters.mk

# Created on: 19/06/2012
#     Author: giovane

##############
# PARAMETROS #
##############

# Tipo da placa. Valores validos:
# - UNO
# - MEGA
TYPE=UNO


# CONSTANTES #
CC=avr-gcc
CXX=avr-g++

FLAGS_INCLUDES=-I"/mnt/dados/workspace/cpp/arduino-core/src"
COMMON_FLAGS=-Wall -Os -ffunction-sections -fdata-sections -fno-exceptions -MMD -MP
OUTPUT=../bin
OUTPUT_OBJECT=$(OUTPUT)/objects
OUTPUT_DEPENDENCIES=$(OUTPUT)/dependencies
INPUT=../src
FILE=libarduino-core$(TYPE)