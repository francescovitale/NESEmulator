package Emulator;


import Emulator.Control.Controller;
import Emulator.TechnicalServices.FileSystemAccess.FileSystemManager;

public class Main {
	public static void main(String args[]) {
		
		Controller C = new Controller();
		String Nome = "GIOCO\\aaa\\bbb.qualcosa";

		
		//C.executeProgram("MarioBros", 0, "C:\\Users\\Daniele\\eclipse-workspace\\NES\\src\\Emulator\\programmi di prova\\DonkeyKong.ines");
	
		/*FileSystemManager FSM = FileSystemManager.getInstance();
		FSM.deleteFile("C:\\Users\\aceep\\eclipse-workspace\\NES\\src\\Emulator\\Log\\log.txt");*/
		
		//C.DumpCartridge();
		//C.DumpMemory();
		//Byte a = 0x10;
		//Byte b = 
		
		String sottostringa = Nome.substring(Nome.lastIndexOf(92)+1, Nome.lastIndexOf(46));
		System.out.println(sottostringa);
		
		
	}
}
