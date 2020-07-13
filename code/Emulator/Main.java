package Emulator;


import Emulator.Control.Controller;
import Emulator.TechnicalServices.FileSystemAccess.FileSystemManager;

public class Main {
	public static void main(String args[]) {
		
		Controller C = new Controller();
		String Nome = "GIOCO";

		
		//C.executeProgram("MarioBros", 0, "C:\\Users\\Daniele\\eclipse-workspace\\NES\\src\\Emulator\\programmi di prova\\nestest.ines");
		char addr_rel = 0xFF01;
		char PC_register = 0x0000;
		char addr_abs= (char) (PC_register + addr_rel);
		System.out.println(Integer.toBinaryString(addr_abs));
		/*FileSystemManager FSM = FileSystemManager.getInstance();
		FSM.deleteFile("C:\\Users\\aceep\\eclipse-workspace\\NES\\src\\Emulator\\Log\\log.txt");*/
		
		//C.DumpCartridge();
		//C.DumpMemory();
		
	}
}
