package ApplicationLogic.Interpreter;

import ApplicationLogic.State.*;

public class ControlUnitExecute extends ControlUnitState {

	OperativeUnit OU;

	private volatile static ControlUnitExecute ControlUnitExecute = null;
	
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	protected ControlUnitExecute() {
	}
	
	//Punto di ingresso globale all'istanza
	public static ControlUnitExecute getInstance(){
		if(ControlUnitExecute==null) {
			synchronized(ControlUnitExecute.class) {
				if(ControlUnitExecute==null) {
					ControlUnitExecute= new ControlUnitExecute();
				}
			}
		}
		return ControlUnitExecute;
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