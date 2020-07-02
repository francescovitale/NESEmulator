package ApplicationLogic.Interpreter;

public class InterpreterFacade {
	
	ControlUnit ControlUnit;	//Collegamento con la ControlUnit
	
	public InterpreterFacade() {}

	//Aziono il ciclo del processore
	public Boolean startCycle() {
		ControlUnit.getInstance().execCycle();
		return true;
	}
}
