package br.ufpe.cin.tamarino.arduinoGenerator.functions;

import br.ufpe.cin.tamarino.arduinoGenerator.AbstractScript;
import br.ufpe.cin.tamarino.arduinoGenerator.Global;

public class Servo {

	private String SERVO_VARIABLE = "myServo";
	
	public Servo(){
		super();
		Global.getInstance().addIncude("Servo");
		Global.getInstance().addVarDeclarations("Servo", SERVO_VARIABLE, null);
	}
	
	public String attachScript(int value){
		return SERVO_VARIABLE+".attach("+value+");\n";
	}
	
	public String detachScript(){
		return SERVO_VARIABLE+".detach();\n";
	}
	
	public String writeScript(int data){
		return SERVO_VARIABLE+".write("+data+");\n";
	}
	
	public String readScript(){
		return SERVO_VARIABLE+".read();\n";
	}

	public String getSERVO_VARIABLE() {
		return SERVO_VARIABLE;
	}

	public void setSERVO_VARIABLE(String sERVO_VARIABLE) {
		SERVO_VARIABLE = sERVO_VARIABLE;
	}

}
