package Emulator.TechnicalServices;

import java.util.ArrayList;

import Emulator.ApplicationLogic.Program;
import Emulator.TechnicalServices.DataBaseAccess.ProgramDAO;
import Emulator.TechnicalServices.FileSystemAccess.FileSystemManager;

public class AccessProgramFS extends AccessProgram{
	
	FileSystemManager Fsm;
	
	public AccessProgramFS() {};
	
	public ArrayList<Program> loadProgram(String SelectedPath) {
		Fsm = FileSystemManager.getInstance();
		String TempFileType = SelectedPath.substring(SelectedPath.length()-4,SelectedPath.length());
		if(TempFileType.equals("ines") || TempFileType.equals("nes"))
			return new ArrayList<Program>(Fsm.getProgram(SelectedPath, 0));
		else
			return new ArrayList<Program>(Fsm.getProgram(SelectedPath, 1));
		
	};
	
	

	public void InsertProgram(String ROMData, String SelectedPath) {
	}
	
	public void DeleteProgram(Program P) {
	}
	
	
	
	public void UpdateProgram(Program P) {
		
		
		
	}

}
 