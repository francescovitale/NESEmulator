package Emulator.TechnicalServices;

import java.util.ArrayList;

import Emulator.ApplicationLogic.Program;
import Emulator.TechnicalServices.FileSystemAccess.FileSystemManager;

public class TechnicalServicesFacade {

	AccessProgram LP;
	public TechnicalServicesFacade(){};
	
	public ArrayList<Program> loadCartridgeData(String SelectedPath){
		LP = new AccessProgramProxy();
		return LP.loadProgram(SelectedPath);
	}
	
}
