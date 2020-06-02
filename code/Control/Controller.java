package Control;
import ApplicationLogic.EmulatorFacade;
import ApplicationLogic.State.*;

public class Controller {
	
	public void executeProgram(String Nome, Integer ID) {
		
		EmulatorFacade Emu = new EmulatorFacade();
		Emu.initProgram(Nome, ID);
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