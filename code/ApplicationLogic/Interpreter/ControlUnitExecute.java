package ApplicationLogic.Interpreter;

import ApplicationLogic.State.*;
import Control.Controller;

public class ControlUnitExecute extends ControlUnitState {

	OperativeUnit OU;
	ControlUnit CU;

	private volatile static ControlUnitExecute ControlUnitExecute = null;
	
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	protected ControlUnitExecute() {
		//Collegamento con L'unità operativa
		OU = OperativeUnit.getIstance();
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
		CU = ControlUnit.getInstance();
		CU.setBool_opcode(OU.Execute(CU.getCurrentInstruction().opcode));						//Eseguo l'operazione dell'istruzione corrente
		
		/*DEBUG*/
		System.out.println(CU.getCurrentInstruction().opcode);
		
		if(CU.getBool_addr() & CU.getBool_opcode())												//Se entrambi i bool sono veri
			CU.increaseCycles();																//Va aggiunto un ciclo per l'istruzione corrente
		
		boolean stop=false;
		while (stop == false)
			stop = clock();																		//Decremento i cicli finché non arrivo a 0
		
		if(CU.getInstructionRegister().byteValue() == (byte)0xD0)								//Condizione di terminazione
			CU.setInstructionRegister((byte)0xF);
	}

	
	private Boolean clock() {
		if (CU.getCycles()==0)					//Se ho finito i cicli
			return true;										
		else {
			CU.decreaseCycles();				//Altrimenti decrementa il numero di cicli
			return true;
		}
	}
}