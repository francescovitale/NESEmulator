package ApplicationLogic.Interpreter;

import ApplicationLogic.State.*;

public class ControlUnitDecode extends ControlUnitState {

	OperativeUnit OU;

	private volatile static ControlUnitDecode ControlUnitDecode = null;			//Singleton
	
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	protected ControlUnitDecode() {
	}
	
	//Punto di ingresso globale all'istanza
	public static ControlUnitDecode getInstance(){
		if(ControlUnitDecode==null) {
			synchronized(ControlUnitDecode.class) {
				if(ControlUnitDecode==null) {
					ControlUnitDecode= new ControlUnitDecode();
				}
			}
		}
		return ControlUnitDecode;
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