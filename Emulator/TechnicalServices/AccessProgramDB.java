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

}
