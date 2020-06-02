package ApplicationLogic.Interpreter;

import ApplicationLogic.State.*;

public class ControlUnitExecute extends ControlUnitState {

	OperativeUnit OU;

	private static ControlUnitExecute Instance;
	
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	protected ControlUnitExecute() {
	}
	
	public static ControlUnitExecute getInstance() {
		if(Instance == null) {
			Instance = new ControlUnitExecute();
			return Instance;
		}
		else
			return Instance;
	}
	/**
	 * 
	 * @param OpMacro
	 */
	public void execCycle() {
		if(ControlUnit.getInstance().InstructionRegister == 0xD)
			ControlUnit.getInstance().InstructionRegister = (byte)0xF;
	}

}