package ApplicationLogic.Interpreter;

import ApplicationLogic.State.*;

public class ControlUnitFetch extends ControlUnitState {

	OperativeUnit OU;
	
	private static ControlUnitFetch Instance;
	
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	protected ControlUnitFetch() {
	}
	
	public static ControlUnitFetch getInstance() {
		if(Instance == null) {
			Instance = new ControlUnitFetch();
			return Instance;
		}
		else
			return Instance;
	}
	

	public void execCycle() {
		ControlUnit.getInstance().InstructionRegister = (byte)0xD;
		changeState(ControlUnit.getInstance(), ControlUnitDecode.getInstance());
	}

}