package Emulator.ApplicationLogic.Interpreter;

import Emulator.ApplicationLogic.State.*;
import Emulator.TechnicalServices.FileSystemAccess.FileSystemManager;

public class ControlUnitDecode extends ControlUnitState {

	StateFacade SF;
	ControlUnit CU;
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	protected ControlUnitDecode() {
		//Collegamento con il facade dello STATE
		SF = new StateFacade();
	}
	
	
	/**
	 * 
	 * @param OpCode
	 */
	public void execCycle() {
		CU = ControlUnit.getInstance();
		Byte opcode = CU.getInstructionRegister();											//prendo il codice operativo per capire la modalità di indirizzamento 
		
		CU.setCurrentInstruction(SF.getMicrorom(Byte.toUnsignedInt(opcode)));  				//Prelevo l'istruzione dalla microRom e la salvo in una variabile della ControlUnit
		CU.setCycles(CU.getCurrentInstruction().cycles);									//Assegno il numero di cicli dell'istruzione 

		
		CU.setBool_addr(SF.addressingMode(CU.getCurrentInstruction().addressing_mode));		//Prelevo la macro dalla microROM in corrispondenza dell'opcode e comando l'unità operativa di applicare una modalità di indirizzamento
		

		
		changeState(CU, ControlUnitState.getInstance("Execute"));
		
		/*
		//DEBUG					** SCRITTURA LOG DI ESECUZIONE **
		FileSystemManager FSM = FileSystemManager.getInstance();
		FSM.setPath("C:\\Users\\Daniele\\eclipse-workspace\\NES\\src\\Emulator\\Log\\log.txt");
		FSM.writeLogData(CU.getCurrentInstruction().opcode + " " + CU.getCurrentInstruction().addressing_mode + " CYC: " + CU.CYC + "\n");
		*/
	}

}