package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;

import java.util.ArrayList;

public class PaletteMemory {
	
	private volatile static PaletteMemory PaletteMemory = null;
	
	private ArrayList<Byte> PaletteTable;
	
	protected PaletteMemory() {
		
		PaletteTable = new ArrayList<Byte>();
		for(int i = 0; i < (int)0x20; i++)
			PaletteTable.add((byte)0x00);
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
	
	public Byte read(char addr)
	{
		return PaletteTable.get(addr);
	}

	//Per Connettere la PPU sul BUS principale
	public void write(char addr, Byte data)
	{
		PaletteTable.set(addr, data);
	}

}
