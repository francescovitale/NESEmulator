package ApplicationLogic.Interpreter;

import ApplicationLogic.State.*;
import Control.Controller;

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

	public void execCycle() {
		
		ControlUnit.setBool_opcode(OU.getIstance().Execute(ControlUnit.getCurrentInstruction().opcode));			//Eseguo l'operazione dell'istruzione corrente
		
		/*DEBUG*/
		System.out.println(ControlUnit.getCurrentInstruction().opcode);
		
		if(ControlUnit.getInstance().getBool_addr() & ControlUnit.getInstance().getBool_opcode())					//Se entrambi i bool sono veri
			ControlUnit.getInstance().increaseCycles();																//Va aggiunto un ciclo per l'istruzione corrente
		
		boolean stop=false;
		while (stop == false)
			stop = clock();																							//Decremento i cicli finché non arrivo a 0
		
		if(ControlUnit.getInstance().getInstructionRegister().byteValue() == (byte)0xD0)							//Condizione di terminazione
			ControlUnit.getInstance().setInstructionRegister((byte)0xF);
	}

	
	private Boolean clock() {
		if (ControlUnit.getInstance().getCycles()==0)				//Se ho finito i cicli
			return true;										
		else {
			ControlUnit.getInstance().decreaseCycles();				//Altrimenti decrementa il numero di cicli
			return true;
		}
	}
}