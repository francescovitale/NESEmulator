package Emulator.Control;
import java.util.ArrayList;

import Emulator.ApplicationLogic.EmulatorFacade;
import Emulator.ApplicationLogic.Program;
import Emulator.ApplicationLogic.State.*;

public class Controller {
	
	private EmulatorFacade Emu;
	private ReturnedState State;
	private ArrayList<ReturnedProgram> RP;
	
	
	public Controller(){
		Emu = new EmulatorFacade();
		State = ReturnedState.getIstance();
		
		RP= new ArrayList<ReturnedProgram>();
		
	}
	
	public void executeProgram(String Nome, Integer ID, String Path) {
		
		Emu.initProgram(Nome, ID, Path);
		//DumpCartridge();
		Emu.startCycle();

	}
	
	public void LocalRepositoryRequest(String fileName, Integer ID, Integer Op, String Path) {
		if(Op == 0) {
			//INVIA RICHIESTA DI INSERIMENTO IN LISTA DEL FILE 
		}
		else if(Op == 1) {
			//INVIA RICHIESTA DI ELIMINAZIONE DALLA LISTA DEL FILE CON DATO ID
		}
		
	}
	
	public ReturnedState getReturnedState() {
		return State.getIstance();
	}
	
	public Boolean getState() {
		 
		Boolean T = Emu.getState().getTaken();
		if(T == true) {
			State.setCS(Emu.getState().getCS());
			State.setMS(Emu.getState().getMS());
			State.setPS(Emu.getState().getPS());
			State.setPM(Emu.getState().getPM());
			State.setVM(Emu.getState().getVM());
			State.setOS(Emu.getState().getOS());
			resetStateTaken();
		}
		return T;
		
	}
	
	public void resetStateTaken() {
		
		Emu.resetStateTaken();
	}
	
	//FUNZIONI AUSILIARIE DI STAMPA E DEBUG
	
	public void DumpMemory() {
		Memory.getInstance().dumpMemory();
	}
	public void DumpCartridge() {
		
		Cartridge.getInstance().dumpCartridge();
	}
	
	public EmulatorFacade getEmulatorFacade() {
		return Emu;
	}

	public void setkeys(Byte keys) {
		Emu.setkeys(keys);
	}
	
	
	public ArrayList<ReturnedProgram> getProgramList (){
		
		ArrayList<Program> P= new ArrayList<Program>(Emu.getProgramList());
		
		for (int i=0; i< P.size(); i++) {
			
			RP.add(new ReturnedProgram(P.get(i).getID(), P.get(i).getName()));
			System.out.println(" Sono il programma: " + RP.get(i).getID() + RP.get(i).getName());
			
		}

		return RP;
	}
	
	
	public void InsertProgram(String SelectedPath, String ROMName, Integer ID) {
		
		Emu.InsertProgram(SelectedPath, ROMName, ID);
		
		
	}
	
	public void UpdateProgram(String ROMName, String OLDName, Integer ID) {
		
		Emu.UpdateProgram(ROMName, OLDName, ID);
		
		
	}
	
	public void DeleteProgram(Integer ID, String ROMName) {
		
		Emu.DeleteProgram(ID, ROMName);
	}
	

	
}