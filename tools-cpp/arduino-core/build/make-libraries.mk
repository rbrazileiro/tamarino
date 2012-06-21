#make-libraries.mk

# Created on: 19/06/2012
#     Author: giovane

Servo.o:
	@echo "* Building file: $(INPUT)/libraries/Servo/Servo.cpp"
	@$(CXX) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -MF"$(OUTPUT_DEPENDENCIES)/Servo.d" -MT"$(OUTPUT_DEPENDENCIES)/Servo.d" -c -o "$(OUTPUT_OBJECT)/Servo.o" "$(INPUT)/libraries/Servo/Servo.cpp"
	
make-libraries: Servo.o