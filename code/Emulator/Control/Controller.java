package Emulator.Control;
import Emulator.ApplicationLogic.EmulatorFacade;
import Emulator.ApplicationLogic.State.*;

public class Controller {
	
	public void executeProgram(String Nome, Integer ID, String SelectedPath) {
		
		EmulatorFacade Emu = new EmulatorFacade();
		Emu.initProgram(Nome, ID, SelectedPath);
		Emu.startCycle();

	}
	
	//FUNZIONI AUSILIARIE DI STAMPA E DEBUG
	
	public void DumpMemory() {
		Memory.getIstance().dumpMemory();
	}
	public void DumpCartridge() {
		
		Cartridge.getIstance().dumpCartridge();
	}

}