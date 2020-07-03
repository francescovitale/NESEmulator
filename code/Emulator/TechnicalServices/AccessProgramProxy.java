package Emulator.TechnicalServices;

import java.util.ArrayList;

public class AccessProgramProxy extends AccessProgram{
	
	private AccessProgramFS LPFS;
	private AccessProgramDB LPDB;

	public AccessProgramProxy() {}
	public ArrayList<Byte> loadProgram(String Nome, Integer ID, String SelectedPath){
		ArrayList<Byte> Program;
		
		if(SelectedPath.isEmpty()) {
			LPDB = new AccessProgramDB();
			Program = LPDB.loadProgram(Nome, ID, SelectedPath);}
		else {
			LPFS = new AccessProgramFS();
			Program = LPFS.loadProgram(Nome, ID, SelectedPath);
		}
		return Program;
	};
	
}
