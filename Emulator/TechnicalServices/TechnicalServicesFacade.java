package Emulator.TechnicalServices;

import java.util.ArrayList;

import Emulator.ApplicationLogic.Program;
import Emulator.TechnicalServices.FileSystemAccess.FileSystemManager;

public class TechnicalServicesFacade {

	AccessProgram LP;
	public TechnicalServicesFacade(){};
	
	public ArrayList<Program> loadProgramData(String SelectedPath){
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
	

	public void InsertProgram(String SelectedPath) {
		
		LP= new AccessProgramFS();
		Program P= new Program(LP.loadProgram(SelectedPath).get(0));
		LP= new AccessProgramDB();
		LP.InsertProgram(P.getROMData(), SelectedPath);
		
		
	}
	
	public void DeleteProgram(Program P) {
		

		
		LP= new AccessProgramDB();
		LP.DeleteProgram(P);
		
		
	}
	
	public void UpdateProgram(Program P) {
		
		LP= new AccessProgramDB();
		LP.UpdateProgram(P);
		
		
	}
	
}
