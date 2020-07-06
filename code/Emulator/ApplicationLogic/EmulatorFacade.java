package Emulator.ApplicationLogic;

import java.util.ArrayList;

import Emulator.ApplicationLogic.Interpreter.*;
import Emulator.ApplicationLogic.State.*;
import Emulator.Control.ReturnedState;
import Emulator.TechnicalServices.TechnicalServicesFacade;

public class EmulatorFacade {

	InterpreterFacade IF;
	StateFacade SF;
	TechnicalServicesFacade Tsf;
	ArrayList<Program> Programs;
	
	ArrayList<Byte> ROMData;
	
	public EmulatorFacade() {
		IF = new InterpreterFacade();
		SF = new StateFacade();
	}
	
	public Integer selectProgram(String ROMName, Integer ID)
	{
		Boolean trovato = false;
		Integer i = 0;
		while(!trovato && i<Programs.size()) {
			if(Programs.get(i).getName().equals(ROMName) && Programs.get(i).getID().equals(ID))
			{
				trovato = true;
			}
			else
				i++;
		}
		return i;
	}
	
	//Inizializzo il programma in memoria
	public Boolean initProgram(String ROMName, Integer ID, String SelectedPath) {
		Tsf = new TechnicalServicesFacade();
		Programs = new ArrayList<Program>(Tsf.loadCartridgeData(SelectedPath));
		int index;
		
		if(SelectedPath.isEmpty()) {
			index = selectProgram(ROMName, ID);
			return SF.loadData(Programs.get(index).getParsedROMData());
		}
		else {
			return SF.loadData(Programs.get(0).getParsedROMData());
		}
			
	}

	//Aziono il ciclo del processore
	public Boolean startCycle() {
		return IF.startCycle();
	}

	public State getState() {
		return SF.getState();
	}

	public void resetStateTaken() {
		SF.resetStateTaken();
		
	}

	public StateFacade getSF() {
		return SF;
	}

	
	//Recupero lo stato attuale dell'architettura
	/*public State getState() {
		
	}*/

}