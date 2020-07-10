package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;

import java.util.ArrayList;

public class VRAMMemory {
	
	private volatile static VRAMMemory VRAMMemory = null;
	private ArrayList<Byte> NameTable1;
	private ArrayList<Byte> NameTable2;
	
	protected VRAMMemory() {
		NameTable1 = new ArrayList<Byte>();
		NameTable2 = new ArrayList<Byte>();
	};
	
	//Punto di ingresso globale all'istanza
	public static VRAMMemory getInstance(){
		if(VRAMMemory==null) {
			synchronized(VRAMMemory.class) {
				if(VRAMMemory==null) {
					VRAMMemory= new VRAMMemory();
				}
			}
		}
		return VRAMMemory;
	}
	
	public void read(char addr, Byte data)
	{
	}

	//Per Connettere la PPU sul BUS principale
	public void write(char addr, Byte data)
	{

	}

}
