package Emulator.Control;

import java.util.ArrayList;

import Emulator.ApplicationLogic.State.PaletteMemoryState;
import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.PaletteMemory;

public class ReturnedPaletteMemoryState {
	
	//Attributi
	private volatile static ReturnedPaletteMemoryState ReturnedPaletteMemoryState = null;			//Singleton
	private ArrayList<Byte> Palette;
	
	//Costruttore privato
	private ReturnedPaletteMemoryState() {
		Palette = new ArrayList<Byte>();
		for(int i = 0; i < (int)0x20; i++)
			Palette.add((byte)0x0);
	}

	//Punto di ingresso globale all'istanza
	public static ReturnedPaletteMemoryState getInstance() {
		if(ReturnedPaletteMemoryState==null) {
			synchronized(ReturnedPaletteMemoryState.class) {
				if(ReturnedPaletteMemoryState==null) {
					ReturnedPaletteMemoryState = new ReturnedPaletteMemoryState();
				}
			}
		}
		return ReturnedPaletteMemoryState;
	}

	public ArrayList<Byte> getPalette() {
		return Palette;
	}

	public void setPalette(ArrayList<Byte> palette) {
		Palette = palette;
	}
}
