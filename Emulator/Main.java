package Emulator;


import Emulator.Control.Controller;
import Emulator.TechnicalServices.FileSystemAccess.FileSystemManager;

public class Main {
	public static void main(String args[]) {
		
		Controller C = new Controller();
		String Nome = "GIOCO";

		
		C.UpdateProgram("Pippo","MarioBros", 3  );
	
		/*FileSystemManager FSM = FileSystemManager.getInstance();
		FSM.deleteFile("C:\\Users\\aceep\\eclipse-workspace\\NES\\src\\Emulator\\Log\\log.txt");*/
		
		//C.DumpCartridge();
		//C.DumpMemory();
		//Byte a = 0x10;
		//Byte b = 
		
		
	}
}