package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;

import Emulator.ApplicationLogic.State.Cartridge;

public class PPUBus {
	
	private volatile static PPUBus PPUBus = null;
	private Cartridge Cart;
	private PaletteMemory Palette;
	private VRAMMemory VRAM;

	protected PPUBus() {
		Cart = Cartridge.getInstance();
		Palette = PaletteMemory.getInstance();
		VRAM = VRAMMemory.getInstance();
	};
	
	//Punto di ingresso globale all'istanza
	public static PPUBus getInstance(){
		if(PPUBus==null) {
			synchronized(PPUBus.class) {
				if(PPUBus==null) {
					PPUBus= new PPUBus();
				}
			}
		}
		return PPUBus;
	}
	
	public Byte PPURead(char addr)
	{
		return null;
	}

	//Per Connettere la PPU sul BUS principale
	public void PPUWrite(char addr, Byte data)
	{

	}
}
