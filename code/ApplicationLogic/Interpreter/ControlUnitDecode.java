package ApplicationLogic.Interpreter;

import ApplicationLogic.State.*;

public class ControlUnitDecode extends ControlUnitState {

	OperativeUnit OU;

	private static ControlUnitDecode Instance;
	
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	protected ControlUnitDecode() {
	}
	
	public static ControlUnitDecode getInstance() {
		if(Instance == null) {
			Instance = new ControlUnitDecode();
			return Instance;
		}
		else
			return Instance;
	}
	
	/**
	 * 
	 * @param OpCode
	 */
	public void execCycle() {
		Byte opcode = ControlUnit.getInstance().InstructionRegister;
		if(opcode == 0xD)
			changeState(ControlUnit.getInstance(), ControlUnitExecute.getInstance());
	}

}