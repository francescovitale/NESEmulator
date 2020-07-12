package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;

import java.util.ArrayList;

public class VRAMMemory {
	
	private volatile static VRAMMemory VRAMMemory = null;
	private ArrayList<Byte> NameTable1;
	private ArrayList<Byte> NameTable2;
	
	protected VRAMMemory() {
		NameTable1 = new ArrayList<Byte>();
		for(int i = 0; i < (int)0x400; i++)
			NameTable1.add((byte)0x00);
		NameTable2 = new ArrayList<Byte>();
		for(int i = 0; i < (int)0x400; i++)
			NameTable2.add((byte)0x00);
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
	
	public Byte read(char addr, int ChosenTable)
	{
		if(ChosenTable == 1)
			return NameTable1.get(addr);
		else
			return NameTable2.get(addr);
	}

	//Per Connettere la PPU sul BUS principale
	public void write(char addr, Byte data, int ChosenTable)
	{
		if(ChosenTable == 1)
			NameTable1.set(addr,data);
		else
			NameTable2.set(addr,data);
	}

}
