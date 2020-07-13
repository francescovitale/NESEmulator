package Emulator.ApplicationLogic.Interpreter;

import Emulator.ApplicationLogic.State.*;
import Emulator.TechnicalServices.FileSystemAccess.FileSystemManager;

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
		Boolean NMIRequest_temp;
		Boolean IRQRequest_temp;
		
		CU = ControlUnit.getInstance();
		CU.setBool_opcode(SF.Execute(CU.getCurrentInstruction().opcode));						//Eseguo l'operazione dell'istruzione corrente
		
		/*DEBUG*/
		//System.out.println(CU.getCurrentInstruction().opcode);

		
		if(CU.getBool_addr() & CU.getBool_opcode())												//Se entrambi i bool sono veri
			CU.increaseCycles();																//Va aggiunto un ciclo per l'istruzione corrente
		
		SF.setFlag("U", true);
		
		NMIRequest_temp = SF.getNMIRequest();
		IRQRequest_temp = SF.getIRQRequest();
		
		if(NMIRequest_temp == true || IRQRequest_temp == true) {
			/*DEBUG*/
			//System.out.println("Servo la richiesta di interruzione");
			//SF.Execute("NMI");
			changeState(CU, ControlUnitState.getInstance("Interrupt"));
		}
		else
			changeState(CU, ControlUnitState.getInstance("Fetch"));
		
	
		
		
	}

}