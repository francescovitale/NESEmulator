package Emulator.ApplicationLogic.State;

import java.util.ArrayList;

import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.PaletteMemory;
import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.VRAMMemory;

public class VramMemoryState {
	//Attributi
	private volatile static VramMemoryState VramMemoryState = null;
	private ArrayList<Byte> NameTable1;
	private ArrayList<Byte> NameTable2;

	private VRAMMemory Vram;
	//Costruttore privato
	private VramMemoryState() {
		Vram = VRAMMemory.getInstance();
		NameTable1 = new ArrayList<Byte>();
		for(int i = 0; i < (int)0x400; i++)
			NameTable1.add((byte)0x0);
		NameTable2 = new ArrayList<Byte>();
		for(int i = 0; i < (int)0x400; i++)
			NameTable2.add((byte)0x0);
	}

	//Punto di ingresso globale all'istanza
	public static VramMemoryState getInstance() {
		if(VramMemoryState==null) {
			synchronized(VramMemoryState.class) {
				if(VramMemoryState==null) {
					VramMemoryState = new VramMemoryState();
				}
			}
		}
		return VramMemoryState;
	}
	
	//Aggiorno lo stato della memoria 
	public void refreshVRAMMemoryState() {
		NameTable1 = Vram.getNameTable1();
		NameTable2 = Vram.getNameTable2();
	}
 
	public ArrayList<Byte> getNameTable1() {
		return NameTable1;
	}

	public void setNameTable1(ArrayList<Byte> nameTable1) {
		NameTable1 = nameTable1;
	}

	public ArrayList<Byte> getNameTable2() {
		return NameTable2;
	}

	public void setNameTable2(ArrayList<Byte> nameTable2) {
		NameTable2 = nameTable2;
	}


}
