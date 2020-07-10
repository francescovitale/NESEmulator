package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;

import java.util.ArrayList;

public class PaletteMemory {
	
	private volatile static PaletteMemory PaletteMemory = null;
	
	private ArrayList<Byte> PaletteTable;
	
	protected PaletteMemory() {
		
		PaletteTable = new ArrayList<Byte>();
	};
	
	//Punto di ingresso globale all'istanza
	public static PaletteMemory getInstance(){
		if(PaletteMemory==null) {
			synchronized(PaletteMemory.class) {
				if(PaletteMemory==null) {
					PaletteMemory= new PaletteMemory();
				}
			}
		}
		return PaletteMemory;
	}
	
	public void read(char addr, Byte data)
	{
	}

	//Per Connettere la PPU sul BUS principale
	public void write(char addr, Byte data)
	{

	}

}
