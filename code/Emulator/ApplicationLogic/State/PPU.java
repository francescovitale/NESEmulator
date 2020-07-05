package Emulator.ApplicationLogic.State;

import java.util.ArrayList;

public class PPU {

 	private volatile static PPU PPU = null;				//Singleton
 	private Bus BusPPU;									//Bus
	
 	//Collegati alla PPU vi sono delle memorie
 	private ArrayList<Byte> tblNAME1;					//VRAM (prima full_name_table da 1 kb)
 	private ArrayList<Byte> tblNAME2;					//VRAM (seconda full_name_table da 1 kb)
 	private ArrayList<Byte> tblPalette;					//small RAM (Palette)
 	
	protected PPU() {
		//Collegamento con il Bus
		BusPPU = Bus.getInstance();	
		
		//VRAM 
		tblNAME1 = new ArrayList<Byte>();
		for(int i = 0; i < 1024; i++)
			tblNAME1.add((byte)0x0);
		tblNAME2 = new ArrayList<Byte>();
		for(int i = 0; i < 1024; i++)
			tblNAME2.add((byte)0x0);
		
		//Palette 
		tblPalette = new ArrayList<Byte>();
		for(int i = 0; i < 32; i++)
			tblPalette.add((byte)0x0);
		
	};
	
	//Punto di ingresso globale all'istanza
	public static PPU getInstance(){
		if(PPU==null) {
			synchronized(PPU.class) {
				if(PPU==null) {
					PPU= new PPU();
				}
			}
		}
		return PPU;
	}
	
	//Per Connettere la PPU sul BUS principale
	public byte Read(char addr, boolean rdonly)
	{
		byte data = 0x00;

		switch (addr)
		{
		case 0x0000: // Control
			break;
		case 0x0001: // Mask
			break;
		case 0x0002: // Status
			break;
		case 0x0003: // OAM Address
			break;
		case 0x0004: // OAM Data
			break;
		case 0x0005: // Scroll
			break;
		case 0x0006: // PPU Address
			break;
		case 0x0007: // PPU Data
			break;
		}

		return data;
	}

	//Per Connettere la PPU sul BUS principale
	public void Write(char addr, byte data)
	{
		switch (addr)
		{
		case 0x0000: // Control
			break;
		case 0x0001: // Mask
			break;
		case 0x0002: // Status
			break;
		case 0x0003: // OAM Address
			break;
		case 0x0004: // OAM Data
			break;
		case 0x0005: // Scroll
			break;
		case 0x0006: // PPU Address
			break;
		case 0x0007: // PPU Data
			break;
		}
	}

	//Read della PPU al suo BUS personale
	public byte ppuRead(char addr, boolean rdonly)
	{
		byte data = 0x00;
		addr &= 0x3FFF;

		/*if (cart->ppuRead(addr, data))
		{

		}*/

		return data;
	}

	//Write della PPU al suo BUS personale
	public void ppuWrite(char addr, byte data)
	{
		addr &= 0x3FFF;

		/*if (cart->ppuWrite(addr, data))
		{

		}*/
	}
	
	//Clock della PPU (Dovrebbe essere 3 volte più veloce di quello della CPU)
	public void clock() {}

}
