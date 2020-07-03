package Emulator.TechnicalServices;

import java.util.ArrayList;

import Emulator.ApplicationLogic.Program;

public class AccessProgramProxy extends AccessProgram{
	
	private AccessProgramFS LPFS;
	private AccessProgramDB LPDB;

	public AccessProgramProxy() {}
	public ArrayList<Program> loadProgram(String SelectedPath){
		ArrayList<Program> Program;
		
		if(SelectedPath.isEmpty()) {
			LPDB = new AccessProgramDB();
			Program = LPDB.loadProgram(SelectedPath);}
		else {
			LPFS = new AccessProgramFS();
			Program = LPFS.loadProgram(SelectedPath);
		}
		return Program;
	};
	
}
