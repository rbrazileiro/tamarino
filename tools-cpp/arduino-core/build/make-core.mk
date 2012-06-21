#make-core.mk

# Created on: 19/06/2012
# Author: giovane

CDC.o:
	@echo "* Building file: $(INPUT)/CDC.cpp"
	@$(CXX) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -MF"$(OUTPUT_DEPENDENCIES)/CDC.d" -MT"$(OUTPUT_DEPENDENCIES)/CDC.d" -c -o "$(OUTPUT_OBJECT)/CDC.o" "$(INPUT)/CDC.cpp"

HID.o: CDC.o
	@echo "* Building file: $(INPUT)/HID.cpp"
	@$(CXX) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -MF"$(OUTPUT_DEPENDENCIES)/HID.d" -MT"$(OUTPUT_DEPENDENCIES)/HID.d" -c -o "$(OUTPUT_OBJECT)/HID.o" "$(INPUT)/HID.cpp"

HardwareSerial.o: HID.o
	@echo "* Building file: $(INPUT)/HardwareSerial.cpp"
	@$(CXX) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -MF"$(OUTPUT_DEPENDENCIES)/HardwareSerial.d" -MT"$(OUTPUT_DEPENDENCIES)/HardwareSerial.d" -c -o "$(OUTPUT_OBJECT)/HardwareSerial.o" "$(INPUT)/HardwareSerial.cpp"

main.o: HardwareSerial.o
	@echo "* Building file: $(INPUT)/main.cpp"
	@$(CXX) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -MF"$(OUTPUT_DEPENDENCIES)/main.d" -MT"$(OUTPUT_DEPENDENCIES)/main.d" -c -o "$(OUTPUT_OBJECT)/main.o" "$(INPUT)/main.cpp"

new.o: main.o
	@echo "* Building file: $(INPUT)/new.cpp"
	@$(CXX) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -MF"$(OUTPUT_DEPENDENCIES)/new.d" -MT"$(OUTPUT_DEPENDENCIES)/new.d" -c -o "$(OUTPUT_OBJECT)/new.o" "$(INPUT)/new.cpp"

IPAddress.o: HardwareSerial.o
	@echo "* Building file: $(INPUT)/IPAddress.cpp"
	@$(CXX) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -MF"$(OUTPUT_DEPENDENCIES)/IPAddress.d" -MT"$(OUTPUT_DEPENDENCIES)/IPAddress.d" -c -o "$(OUTPUT_OBJECT)/IPAddress.o" "$(INPUT)/IPAddress.cpp"

Print.o: IPAddress.o
	@echo "* Building file: $(INPUT)/Print.cpp"
	@$(CXX) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -MF"$(OUTPUT_DEPENDENCIES)/Print.d" -MT"$(OUTPUT_DEPENDENCIES)/Print.d" -c -o "$(OUTPUT_OBJECT)/Print.o" "$(INPUT)/Print.cpp"

Stream.o: Print.o
	@echo "* Building file: $(INPUT)/Stream.cpp"
	@$(CXX) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -MF"$(OUTPUT_DEPENDENCIES)/Stream.d" -MT"$(OUTPUT_DEPENDENCIES)/Stream.d" -c -o "$(OUTPUT_OBJECT)/Stream.o" "$(INPUT)/Stream.cpp"

Tone.o: Stream.o
	@echo "* Building file: $(INPUT)/Tone.cpp"
	@$(CXX) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -MF"$(OUTPUT_DEPENDENCIES)/Tone.d" -MT"$(OUTPUT_DEPENDENCIES)/Tone.d" -c -o "$(OUTPUT_OBJECT)/Tone.o" "$(INPUT)/Tone.cpp"

USBCore.o: Tone.o
	@echo "* Building file: $(INPUT)/USBCore.cpp"
	@$(CXX) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -MF"$(OUTPUT_DEPENDENCIES)/USBCore.d" -MT"$(OUTPUT_DEPENDENCIES)/USBCore.d" -c -o "$(OUTPUT_OBJECT)/USBCore.o" "$(INPUT)/USBCore.cpp"

WInterrupts.o: USBCore.o
	@echo "* Building file: $(INPUT)/WInterrupts.c"
	@$(CC) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -std=gnu99 -MF"$(OUTPUT_DEPENDENCIES)/WInterrupts.d" -MT"$(OUTPUT_DEPENDENCIES)/WInterrupts.d" -c -o "$(OUTPUT_OBJECT)/WInterrupts.o" "$(INPUT)/WInterrupts.c"

wiring.o: WInterrupts.o
	@echo "* Building file: $(INPUT)/wiring.c"
	@$(CC) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -std=gnu99 -MF"$(OUTPUT_DEPENDENCIES)/wiring.d" -MT"$(OUTPUT_DEPENDENCIES)/wiring.d" -c -o "$(OUTPUT_OBJECT)/wiring.o" "$(INPUT)/wiring.c"

wiring_analog.o: wiring.o
	@echo "* Building file: $(INPUT)/wiring_analog.c"
	@$(CC) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -std=gnu99 -MF"$(OUTPUT_DEPENDENCIES)/wiring_analog.d" -MT"$(OUTPUT_DEPENDENCIES)/wiring_analog.d" -c -o "$(OUTPUT_OBJECT)/wiring_analog.o" "$(INPUT)/wiring_analog.c"

wiring_digital.o: wiring_analog.o
	@echo "* Building file: $(INPUT)/wiring_digital.c"
	@$(CC) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -std=gnu99 -MF"$(OUTPUT_DEPENDENCIES)/wiring_digital.d" -MT"$(OUTPUT_DEPENDENCIES)/wiring_digital.d" -c -o "$(OUTPUT_OBJECT)/wiring_digital.o" "$(INPUT)/wiring_digital.c"

wiring_pulse.o: wiring_digital.o
	@echo "* Building file: $(INPUT)/wiring_pulse.c"
	@$(CC) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -std=gnu99 -MF"$(OUTPUT_DEPENDENCIES)/wiring_pulse.d" -MT"$(OUTPUT_DEPENDENCIES)/wiring_pulse.d" -c -o "$(OUTPUT_OBJECT)/wiring_pulse.o" "$(INPUT)/wiring_pulse.c"

wiring_shift.o: wiring_pulse.o
	@echo "* Building file: $(INPUT)/wiring_shift.c"
	@$(CC) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -std=gnu99 -MF"$(OUTPUT_DEPENDENCIES)/wiring_shift.d" -MT"$(OUTPUT_DEPENDENCIES)/wiring_shift.d" -c -o "$(OUTPUT_OBJECT)/wiring_shift.o" "$(INPUT)/wiring_shift.c"

WMath.o: wiring_shift.o
	@echo "* Building file: $(INPUT)/WMath.cpp"
	@$(CXX) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -MF"$(OUTPUT_DEPENDENCIES)/WMath.d" -MT"$(OUTPUT_DEPENDENCIES)/WMath.d" -c -o "$(OUTPUT_OBJECT)/WMath.o" "$(INPUT)/WMath.cpp"

WString.o: WMath.o
	@echo "* Building file: $(INPUT)/WString.cpp"
	@$(CXX) $(FLAGS_INCLUDES) $(COMMON_FLAGS) $(FLAGS_CPU) -MF"$(OUTPUT_DEPENDENCIES)/WString.d" -MT"$(OUTPUT_DEPENDENCIES)/WString.d" -c -o "$(OUTPUT_OBJECT)/WString.o" "$(INPUT)/WString.cpp"

make-core: WString.o