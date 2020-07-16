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
		Programs = new ArrayList<Program>(Tsf.loadProgramData(SelectedPath));
		int index;
		
		if(SelectedPath.isEmpty()) {
			index = selectProgram(ROMName, ID);
			return SF.loadData(Programs.get(index).getParsedROMData());
		}
		else {
			return SF.loadData(Programs.get(0).getParsedROMData());
			
		}
		
		/*System.out.println(Programs.get(0).getROMData());
		return true;	*/
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

	
	public void setkeys(Byte keys) {
		SF.setkeys(keys);
	}

	

	public ArrayList<Program> getProgramList() {
		
		Tsf= new TechnicalServicesFacade();
		
		return Tsf.loadProgramData("");
	}
	
	
	
	public void DeleteProgram(Integer ID, String ROMName) {
		

		Tsf= new TechnicalServicesFacade();
		Programs= new ArrayList<Program>(Tsf.loadProgramData(""));
		Tsf.DeleteProgram(Programs.get(selectProgram(ROMName, ID)));
		
		
	}
	
	public void InsertProgram(String SelectedPath, String ROMName, Integer ID) {
		
		Tsf= new TechnicalServicesFacade();
		if(ROMName.isEmpty() && ID == -1)
			Tsf.InsertProgram(SelectedPath, "");
		else {
			Programs = new ArrayList<Program>(Tsf.loadProgramData(""));
			Tsf.InsertProgram(SelectedPath, Programs.get(selectProgram(ROMName,ID)).getROMData());
		}
		
		
	}
	
	
	public void UpdateProgram(String ROMName, String OLDName, Integer ID) {
		
		Program P;
		Tsf= new TechnicalServicesFacade();
		Programs= new ArrayList<Program>(Tsf.loadProgramData(""));
		P= new Program(Programs.get(selectProgram(OLDName, ID)));
		P.setName(ROMName);
		Tsf.UpdateProgram(P);
		
	
		
	}
	
}