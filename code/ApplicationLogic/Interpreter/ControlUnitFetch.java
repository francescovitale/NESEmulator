package ApplicationLogic.Interpreter;

import ApplicationLogic.State.*;

public class ControlUnitFetch extends ControlUnitState {

	OperativeUnit OU;
	
	private volatile static ControlUnitFetch ControlUnitFetch = null;
	
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	protected ControlUnitFetch() {
	}
	
	//Punto di ingresso globale all'istanza
	public static ControlUnitFetch getInstance(){
		if(ControlUnitFetch==null) {
			synchronized(ControlUnitFetch.class) {
				if(ControlUnitFetch==null) {
					ControlUnitFetch= new ControlUnitFetch();
				}
			}
		}
		return ControlUnitFetch;
	}
	

	public void execCycle() {
		ControlUnit.getInstance().setInstructionRegister((byte)0xD);
		changeState(ControlUnit.getInstance(), ControlUnitDecode.getInstance());
	}

}