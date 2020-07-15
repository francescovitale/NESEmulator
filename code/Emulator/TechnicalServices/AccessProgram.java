package Emulator.TechnicalServices;

import java.util.ArrayList;

import Emulator.ApplicationLogic.Program;

public abstract class AccessProgram {
	
	public abstract ArrayList<Program> loadProgram(String SelectedPath);

	public abstract void InsertProgram(String ROMData, String SelectedPath);
	
	public abstract void DeleteProgram(Program P);
	
	public abstract void UpdateProgram(Program P);

	
	
	
	
	
	
}




