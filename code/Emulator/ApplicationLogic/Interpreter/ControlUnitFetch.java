package Emulator.ApplicationLogic.Interpreter;

import Emulator.ApplicationLogic.State.*;
import Emulator.TechnicalServices.FileSystemAccess.FileSystemManager;

public class ControlUnitFetch extends ControlUnitState {

	ControlUnit CU;
	StateFacade SF;
	
	
	
	protected ControlUnitFetch() {
		//Collegamento con il facade dello STATE
		SF = new StateFacade();
	}

	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	public void execCycle() {
		CU = ControlUnit.getInstance();
		//Aggiorno lo stato da far visualizzare
		SF.refreshState();
		SF.setFlag("U", true);
		//Decremento i cicli finché non arrivo a 0
		boolean stop=false;
		boolean CPUTurn; 
		while (stop == false) {																	
			CPUTurn = SF.clock();
			
			if(CPUTurn == true) {
				stop = clock();
				
			}
		}
		//if(CU.getInstructionRegister().byteValue() == (byte)0xFF)								//Condizione di terminazione
			//CU.setInstructionRegister((byte)0xF);
	//	else {	
		
			/* DEBUG */
			/*FileSystemManager FSM = FileSystemManager.getInstance();
			FSM.setPath("C:\\Users\\aceep\\eclipse-workspace\\NES\\src\\Emulator\\Log\\log.txt");
			OperativeUnit OU = OperativeUnit.getInstance();
			
			Integer PC = (int)OU.getPC_register();
			Integer A = Byte.toUnsignedInt(OU.getA_register());
			Integer X = Byte.toUnsignedInt(OU.getX_register());
			Integer Y = Byte.toUnsignedInt(OU.getY_register());
			Integer SP = Byte.toUnsignedInt(OU.getStack_pointer());
			Integer SR = Byte.toUnsignedInt(OU.getStatus_register());
			
			FSM.writeLogData(Integer.toHexString(PC)+ " " + " A: "+ Integer.toHexString(A) + " X: "+ Integer.toHexString(X) + " Y: "+ Integer.toHexString(Y) + 
			" SP: "+ Integer.toHexString(SP) + " " + "SR: " + Integer.toHexString(SR) + " ");
			
			CU.setInstructionRegister(SF.fetch().byteValue());				//Fetcho l'istruzione e la salvo nell'IR
			SF.increasePC();
			/*DEBUG*/
			//System.out.println(Byte.toUnsignedInt(OU.getInstance().fetch()));									//Stampa DEBUG del codice operativo
			//ControlUnit.getInstance().setInstructionRegister((byte)0xD);
			
			changeState(CU, ControlUnitState.getInstance("Decode"));
		//}	
			
			
			

		
	}
	
	private Boolean clock() {
		if (CU.getCycles()==0)					//Se ho finito i cicli
			return true;										
		else {
			CU.decreaseCycles();				//Altrimenti decrementa il numero di cicli
			CU.CYC++;
			return false;
		}
	}

}