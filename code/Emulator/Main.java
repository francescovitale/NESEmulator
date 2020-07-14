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
		Byte bg_next_tile_attrib = 0x04;
		bg_next_tile_attrib = (byte)(Byte.toUnsignedInt(bg_next_tile_attrib) >> 2);
		System.out.println(Integer.toBinaryString(bg_next_tile_attrib));
	}
}
