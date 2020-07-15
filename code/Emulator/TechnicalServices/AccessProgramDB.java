package Emulator.TechnicalServices;

import java.util.ArrayList;

import Emulator.ApplicationLogic.Program;
import Emulator.TechnicalServices.DataBaseAccess.ProgramDAO;

public class AccessProgramDB extends AccessProgram{
	
	private ProgramDAO PDAO;
	public AccessProgramDB() {};
	public ArrayList<Program> loadProgram(String SelectedPath) {
		PDAO = new ProgramDAO();
		return PDAO.getPrograms();
	};
	
	
	

	public void InsertProgram(String ROMData, String SelectedPath) {
		
		PDAO= new ProgramDAO();
		String Name= SelectedPath.substring(SelectedPath.lastIndexOf(92)+1, SelectedPath.lastIndexOf(46));
		Program P= new Program(0, Name, ROMData);
		PDAO.InsertProgram(P);
		
	}
	
	public void DeleteProgram(Program P) {
		
		PDAO= new ProgramDAO();
		PDAO.DeleteProgram(P);
		
	}
	
	
	public void UpdateProgram(Program P) {
		
		PDAO= new ProgramDAO();
		PDAO.UpdateProgram(P);
		
		
	}

}
