package Emulator.TechnicalServices;

import java.util.ArrayList;

import Emulator.ApplicationLogic.Program;

public abstract class AccessProgram {
	
	public abstract ArrayList<Program> loadProgram(String SelectedPath);

}
