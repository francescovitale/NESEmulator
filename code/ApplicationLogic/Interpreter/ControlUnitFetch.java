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
		ControlUnit.getInstance().setInstructionRegister(OU.getIstance().fetch().byteValue());				//Fetcho l'istruzione e la salvo nell'IR
		
		/*DEBUG*/
		//System.out.println(Byte.toUnsignedInt(OU.getIstance().fetch()));									//Stampa DEBUG del codice operativo
		//ControlUnit.getInstance().setInstructionRegister((byte)0xD);
		
		changeState(ControlUnit.getInstance(), ControlUnitDecode.getInstance());
	}

}