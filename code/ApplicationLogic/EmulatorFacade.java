package ApplicationLogic;

import ApplicationLogic.Interpreter.*;
import ApplicationLogic.State.*;

public class EmulatorFacade {

	InterpreterFacade IF;
	StateFacade SF;
	
	public EmulatorFacade() {
		IF = new InterpreterFacade();
		SF = new StateFacade();
	}
	
	//Inizializzo il programma in memoria
	public Boolean initProgram(String ROMName, Integer ID) {
		return SF.loadData(ROMName, ID);
	}

	//Aziono il ciclo del processore
	public Boolean startCycle() {
		return IF.startCycle();
	}
	
	//Recupero lo stato attuale dell'architettura
	/*public State getState() {
		
	}*/

}