package Emulator;


import Emulator.Control.Controller;

public class Main {
	public static void main(String args[]) {
		
		Controller C = new Controller();
		String Nome = "GIOCO";

		C.executeProgram("MarioBros", 0, "C:\\Users\\aceep\\eclipse-workspace\\NES\\src\\Emulator\\programmi di prova\\Balloon Fight (USA).ines");
		//C.DumpCartridge();
		//C.DumpMemory();

	}
}
