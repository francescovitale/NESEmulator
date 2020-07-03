package Emulator.TechnicalServices;

import java.util.ArrayList;

import Emulator.TechnicalServices.FileSystemAccess.FileSystemManager;

public class TechnicalServicesFacade {

	AccessProgram LP;
	public TechnicalServicesFacade(){};
	
	public ArrayList<Byte> loadCartridgeData(String Nome, Integer ID, String SelectedPath){
		LP = new AccessProgramProxy();
		return LP.loadProgram(Nome, ID, SelectedPath);
	}
	
}
