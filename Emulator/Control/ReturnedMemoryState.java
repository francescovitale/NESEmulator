package Emulator.Control;

import java.util.ArrayList;

public class ReturnedMemoryState {
	
	//Attributi
	private volatile static ReturnedMemoryState MemoryState = null;			//Singleton
	private ArrayList<Byte> RAM;
	
	//Costruttore privato
	private ReturnedMemoryState() {
		RAM = new ArrayList<Byte>();
		for(int i = 0; i < (int)0xFFFF; i++)
			RAM.add((byte)0x0);
	}

	//Punto di ingresso globale all'istanza
	public static ReturnedMemoryState getIstance() {
		if(MemoryState==null) {
			synchronized(ReturnedMemoryState.class) {
				if(MemoryState==null) {
					MemoryState = new ReturnedMemoryState();
				}
			}
		}
		return MemoryState;
	}
	
}
