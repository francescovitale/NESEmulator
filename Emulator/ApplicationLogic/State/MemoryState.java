package Emulator.ApplicationLogic.State;

import java.util.ArrayList;

public class MemoryState {
	
	//Attributi
	private volatile static MemoryState MemoryState = null;			//Singleton
	private ArrayList<Byte> RAM;
	Memory Mem;
	
	//Costruttore privato
	private MemoryState() {
		Mem = Memory.getInstance();
		RAM = new ArrayList<Byte>();
		for(int i = 0; i < (int)0xFFFF; i++)
			RAM.add((byte)0x0);
	}

	//Punto di ingresso globale all'istanza
	public static MemoryState getInstance() {
		if(MemoryState==null) {
			synchronized(MemoryState.class) {
				if(MemoryState==null) {
					MemoryState = new MemoryState();
				}
			}
		}
		return MemoryState;
	}
	
	//Aggiorno lo stato della memoria 
	public void refreshMemoryState() {
		RAM = Mem.getWord();
	}

	public ArrayList<Byte> getRAM() {
		return RAM;
	}

	public void setRAM(ArrayList<Byte> rAM) {
		RAM = rAM;
	}

	public Memory getMem() {
		return Mem;
	}

	public void setMem(Memory mem) {
		Mem = mem;
	}
	
	

}