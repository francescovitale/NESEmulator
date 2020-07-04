package Emulator.TechnicalServices;

import java.util.ArrayList;

import Emulator.ApplicationLogic.Program;
import Emulator.TechnicalServices.FileSystemAccess.FileSystemManager;

public class AccessProgramFS extends AccessProgram{
	
	FileSystemManager Fsm;
	
	public AccessProgramFS() {};
	
	public ArrayList<Program> loadProgram(String SelectedPath) {
		ArrayList<Program> Prog = new ArrayList<Program>();
		Fsm = FileSystemManager.getInstance();
		Fsm.setDefaultPath(SelectedPath);
		Prog.add(new Program(-1,"",Fsm.getData()));
		return Prog;
	};

}
