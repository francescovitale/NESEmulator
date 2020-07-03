package Emulator.TechnicalServices;

import java.util.ArrayList;

import Emulator.TechnicalServices.FileSystemAccess.FileSystemManager;

public class AccessProgramFS extends AccessProgram{
	
	FileSystemManager Fsm;
	
	public AccessProgramFS() {};
	
	public ArrayList<Byte> loadProgram(String Nome, Integer ID, String SelectedPath) {
		ArrayList<Byte> Program;
		Fsm = FileSystemManager.getInstance();
		Fsm.setDefaultPath(SelectedPath);
		Program = Fsm.getData();
		return Program;
		
	};

}
