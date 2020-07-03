package Emulator;


import Emulator.Control.Controller;

public class Main {
	public static void main(String args[]) {
		
		Controller C = new Controller();
		String Nome = "GIOCO";

		C.executeProgram("", 0, "C:\\Users\\aceep\\Desktop\\Prova\\GIOCO.ines");
		//C.DumpCartridge();
		//C.DumpMemory();

	}
}
