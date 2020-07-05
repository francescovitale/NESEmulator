package Emulator;


import Emulator.Control.Controller;

public class Main {
	public static void main(String args[]) {
		
		Controller C = new Controller();
		String Nome = "GIOCO";

		C.executeProgram("MarioBros", 0, "C:\\Users\\Daniele\\eclipse-workspace\\NES_new\\src\\Emulator\\programmi di prova\\Baseball (USA, Europe).ines");
		//C.DumpCartridge();
		//C.DumpMemory();

	}
}
