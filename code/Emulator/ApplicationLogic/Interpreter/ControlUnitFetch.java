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
		//Variabili per la gestione delle interrupt
		Boolean NMIRequest_temp;
		Boolean IRQRequest_temp;
		
		//Aggiorno lo stato da far visualizzare
		SF.refreshState();
		
		CU = ControlUnit.getInstance();
		SF.setFlag("U", true);
		
		boolean stop=false;
		boolean CPUTurn;
		//Finché la CPU continua ad avere cicli 
		while (stop == false) {	
			//Clock della PPU e verifico se sono passati 3 clock della PPU
			CPUTurn = SF.clock();
			
			//Se sono passati tre clock ne faccio passare uno della CPU
			if(CPUTurn == true) {
				stop = clock();	
			}
			
			//Se la CPU non ha eseguito bisogna verificare comunque se si è verificata una richiesta di interrupt
			if(stop == false) {
				NMIRequest_temp = SF.getNMIRequest();
				IRQRequest_temp = SF.getIRQRequest();
				
				if(NMIRequest_temp == true || IRQRequest_temp == true) {
					/*DEBUG*/
					//System.out.println("Servo la richiesta di interruzione");
					changeState(CU, ControlUnitState.getInstance("Interrupt"));
				}
			}
		}
		//DEBUG	/
		//Possibile Condizione di terminazione
		//if(CU.getInstructionRegister().byteValue() == (byte)0xFF)								
			//CU.setInstructionRegister((byte)0xF);
		//else {	
		
			/* DEBUG */
			FileSystemManager FSM = FileSystemManager.getInstance();
			FSM.setPath("C:\\Users\\Daniele\\eclipse-workspace\\NES\\src\\Emulator\\Log\\log.txt");
			OperativeUnit OU = OperativeUnit.getInstance();
			
			Integer PC = (int)OU.getPC_register();
			Integer A = Byte.toUnsignedInt(OU.getA_register());
			Integer X = Byte.toUnsignedInt(OU.getX_register());
			Integer Y = Byte.toUnsignedInt(OU.getY_register());
			Integer SP = Byte.toUnsignedInt(OU.getStack_pointer());
			Integer SR = Byte.toUnsignedInt(OU.getStatus_register());
			
			Byte opfetched = SF.fetch().byteValue();
			/*
			//DEBUG					** SCRITTURA LOG DI ESECUZIONE **
			FSM.writeLogData(Integer.toHexString(Byte.toUnsignedInt(opfetched)) + " "+Integer.toHexString(PC)+ " " + " A: "+ Integer.toHexString(A) + " X: "+ Integer.toHexString(X) + " Y: "+ Integer.toHexString(Y) + 
			" SP: "+ Integer.toHexString(SP) + " " + "SR: " + Integer.toHexString(SR) + " ");
			*/

			CU.setInstructionRegister(opfetched);				//Fetcho l'istruzione e la salvo nell'IR
			SF.increasePC();									//Incremento il PC
			
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