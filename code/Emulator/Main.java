package Emulator;


import Emulator.Control.Controller;

public class Main {
	public static void main(String args[]) {
		/*
		Controller C = new Controller();
		String Nome = "GIOCO";

		C.executeProgram("MarioBros", 0, "C:\\Users\\Daniele\\Desktop\\NESEmulator-ppu\\Programmi\\GIOCO2.ines");
		//C.DumpCartridge();
		//C.DumpMemory();
		 */
		Byte a = 0x05;
		char b = (char)((char)(Byte.toUnsignedInt((byte)(a<<1)) | 0x01));
		System.out.println(Integer.toHexString(b));
	}
}
