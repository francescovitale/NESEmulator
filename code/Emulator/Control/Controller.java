package Emulator.Control;
import Emulator.ApplicationLogic.EmulatorFacade;
import Emulator.ApplicationLogic.State.*;

public class Controller {
	
	private EmulatorFacade Emu;
	private ReturnedState State;
	
	public Controller(){
		Emu = new EmulatorFacade();
		State = ReturnedState.getIstance();
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
			//State.setMS(Emu.getState().getMS());
			State.setPS(Emu.getState().getPS());
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

}