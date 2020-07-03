package Emulator.TechnicalServices;

import java.util.ArrayList;

import Emulator.ApplicationLogic.Program;
import Emulator.TechnicalServices.FileSystemAccess.FileSystemManager;

public class TechnicalServicesFacade {

	AccessProgram LP;
	public TechnicalServicesFacade(){};
	
	public ArrayList<Program> loadCartridgeData(String SelectedPath){
		ArrayList<Program> Program;
		
		if(SelectedPath.isEmpty()) {
			LP = new AccessProgramDB();
			Program = new ArrayList<Program>(LP.loadProgram(SelectedPath));}
		else {
			LP = new AccessProgramFS();
			Program = new ArrayList<Program>(LP.loadProgram(SelectedPath));
		}
		return Program;
	}
	
}
