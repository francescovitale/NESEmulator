package Emulator.ApplicationLogic.State;

import java.util.ArrayList;

import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.PaletteMemory;

public class PaletteMemoryState {
	//Attributi
	private volatile static PaletteMemoryState PaletteMemoryState = null;			//Singleton
	private ArrayList<Byte> Palette;
	private PaletteMemory P;
	//Costruttore privato
	private PaletteMemoryState() {
		P = PaletteMemory.getInstance();
		Palette = new ArrayList<Byte>();
		for(int i = 0; i < (int)0x20; i++)
			Palette.add((byte)0x0);
	}

	//Punto di ingresso globale all'istanza
	public static PaletteMemoryState getInstance() {
		if(PaletteMemoryState==null) {
			synchronized(PaletteMemoryState.class) {
				if(PaletteMemoryState==null) {
					PaletteMemoryState = new PaletteMemoryState();
				}
			}
		}
		return PaletteMemoryState;
	}
	
	//Aggiorno lo stato della memoria 
	public void refreshPaletteMemoryState() {
		Palette = P.getPaletteTable();
	}

	public ArrayList<Byte> getPalette() {
		return Palette;
	}

	public void setPalette(ArrayList<Byte> palette) {
		Palette = palette;
	}

	
}
