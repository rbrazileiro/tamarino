#Makefile
# Created on: 18/06/2012
#     Author: giovane
# ver makepp.sourceforge.net/1.19/makepp_statements.html

-include parameters.mk

ifeq ($(TYPE),UNO)
	FLAGS_CPU=-mmcu=atmega328 -DF_CPU=16000000UL
	VARIANTS_ORIGIN=$(INPUT)/variants/standard
endif

all: build	

-include make-core.mk
-include make-libraries.mk

clean:
	@rm -rf $(OUTPUT)

init: clean
	@echo "***** Build of project arduino-core for $(TYPE) board *****"
	@echo
	@mkdir $(OUTPUT)
	@mkdir $(OUTPUT_DEPENDENCIES)
	@mkdir $(OUTPUT_OBJECT)
	@cp -v $(VARIANTS_ORIGIN)/pins_arduino.h $(INPUT)
	
libarduino-core.a: init make-core make-libraries
	@echo	
	@avr-ar -r "$(OUTPUT)/$(FILE).a" $(OUTPUT_OBJECT)/*.o
	@avr-objdump -h -S $(OUTPUT)/$(FILE).a  >"$(OUTPUT)/$(FILE).lss"
	
build: libarduino-core.a
	@echo 
	@echo "* Final Result:"
	avr-size --format=berkeley -t $(OUTPUT)/$(FILE).a
	@rm -rf $(OUTPUT_DEPENDENCIES)
	@rm -rf $(OUTPUT_OBJECT)
	@echo
		
	@echo "***** Build of project arduino-core for $(TYPE) complete!*****"	
