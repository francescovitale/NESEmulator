package Emulator.ApplicationLogic.Interpreter;

public class InterpreterFacade {
	
	ControlUnit ControlUnit;	//Collegamento con la ControlUnit
	
	public InterpreterFacade() {}

	//Aziono il ciclo del processore
	public Boolean startCycle() {
		Emulator.ApplicationLogic.Interpreter.ControlUnit.getInstance().execCycle();
		return true;
	}
}
