package Emulator.ApplicationLogic.Interpreter;

import Emulator.ApplicationLogic.State.StateFacade;

public class ControlUnitReset extends ControlUnitState{
	ControlUnit CU;
	StateFacade SF;
	
	protected ControlUnitReset() {
		//Collegamento con il facade dello STATE
		SF = new StateFacade();
	}
	
	
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	public void execCycle() {
		CU = ControlUnit.getInstance();
		//Resetto
		SF.reset();
		changeState(CU, ControlUnitState.getInstance("Fetch"));
	}
}
