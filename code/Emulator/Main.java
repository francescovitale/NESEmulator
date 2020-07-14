package Emulator;


import Emulator.Control.Controller;
import Emulator.TechnicalServices.FileSystemAccess.FileSystemManager;

public class Main {
	public static void main(String args[]) {
		
		Controller C = new Controller();
		String Nome = "GIOCO";

		
		//C.executeProgram("MarioBros", 0, "C:\\Users\\Daniele\\eclipse-workspace\\NES\\src\\Emulator\\programmi di prova\\DonkeyKong.ines");
	
		/*FileSystemManager FSM = FileSystemManager.getInstance();
		FSM.deleteFile("C:\\Users\\aceep\\eclipse-workspace\\NES\\src\\Emulator\\Log\\log.txt");*/
		
		//C.DumpCartridge();
		//C.DumpMemory();
		Byte data = (byte)0x80;
		System.out.println(Integer.toBinaryString(data));
		data = (byte)((data >> 3) & 0x1F);	
		System.out.println(Integer.toBinaryString(data));
		
		
	}
}
