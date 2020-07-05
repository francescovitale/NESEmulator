package Emulator.ApplicationLogic.Interpreter;

import Emulator.ApplicationLogic.State.*;

public class ControlUnitExecute extends ControlUnitState {

	StateFacade SF;
	ControlUnit CU;
	
	protected ControlUnitExecute() {
		//Collegamento con il facade dello STATE
		SF = new StateFacade();
	}
	
	
	
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};

	public void execCycle() {
		CU = ControlUnit.getInstance();
		CU.setBool_opcode(SF.Execute(CU.getCurrentInstruction().opcode));						//Eseguo l'operazione dell'istruzione corrente
		
		/*DEBUG*/
		System.out.println(CU.getCurrentInstruction().opcode);
		
		if(CU.getBool_addr() & CU.getBool_opcode())												//Se entrambi i bool sono veri
			CU.increaseCycles();																//Va aggiunto un ciclo per l'istruzione corrente
		
		//if(CU.getInstructionRegister().byteValue() == (byte)0xD0)								//Condizione di terminazione
			CU.setInstructionRegister((byte)0xF);
		//else 
		//	changeState(CU, ControlUnitState.getInstance("Fetch"));
	}

}