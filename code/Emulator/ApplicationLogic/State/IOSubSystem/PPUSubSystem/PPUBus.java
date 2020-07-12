package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;

import Emulator.ApplicationLogic.ByteManager;
import Emulator.ApplicationLogic.State.Cartridge;
import Emulator.ApplicationLogic.State.Cartridge.MIRROR;
import Emulator.ApplicationLogic.State.IOSubSystem.IOManager;

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
		Byte data = 0x00;
		addr &= 0x3FFF;
		
		/* Da verificare */
		if (Cart.ppuRead(addr))
		{
			data = Cart.getData();
		}
		else if (addr >= 0x2000 && addr <= 0x3EFF)
		{
			addr &= 0x0FFF;

			if (Cart.mirror.equals(MIRROR.VERTICAL))
			{
				
				// Vertical
				if (addr >= 0x0000 && addr <= 0x03FF)
					data = VRAM.read(addr &= 0x03FF, 1);
				if (addr >= 0x0400 && addr <= 0x07FF)
					data = VRAM.read(addr &= 0x03FF, 2);
				if (addr >= 0x0800 && addr <= 0x0BFF)
					data = VRAM.read(addr &= 0x03FF,1);
				if (addr >= 0x0C00 && addr <= 0x0FFF)
					data = VRAM.read(addr &= 0x03FF,2);
			}
			else if (Cart.mirror.equals(MIRROR.HORIZONTAL))
			{
				// Horizontal
				if (addr >= 0x0000 && addr <= 0x03FF)
					data = VRAM.read(addr &= 0x03FF, 1);
				if (addr >= 0x0400 && addr <= 0x07FF)
					data = VRAM.read(addr &= 0x03FF,1);
				if (addr >= 0x0800 && addr <= 0x0BFF)
					data = VRAM.read(addr &= 0x03FF,2);
				if (addr >= 0x0C00 && addr <= 0x0FFF)
					data = VRAM.read(addr &= 0x03FF,2);
			}
		}
		else if (addr >= 0x3F00 && addr <= 0x3FFF)
		{
			addr &= 0x001F;
			if (addr == 0x0010) addr = 0x0000;
			if (addr == 0x0014) addr = 0x0004;
			if (addr == 0x0018) addr = 0x0008;
			if (addr == 0x001C) addr = 0x000C;
			//data = (byte)(Palette.read(addr) );
			// Chiamata di comodo verso IOManager
			Byte mask = IOManager.getInstance().getPPUMask();
			// È necessario estrarre il bit grayscale dal mask register
			int grayscale = ByteManager.extractBit(0,mask);
			if(grayscale == 0) 
				data = (byte)(Palette.read(addr) & 0x30);
			else 
				data = (byte)(Palette.read(addr) & 0x3F);
			
		}
		

		return data;
	}
	
	

	//Per Connettere la PPU sul BUS principale
	public void PPUWrite(char addr, byte data)
	{
		addr &= 0x3FFF;
		
		/* Da verificare */
		if (Cart.ppuWrite(addr, data))
		{
			
		}
		
		else if (addr >= 0x2000 && addr <= 0x3EFF)
		{
			addr &= 0x0FFF;

			if (Cart.mirror.equals(MIRROR.VERTICAL))
			{
				
				// Vertical
				if (addr >= 0x0000 && addr <= 0x03FF)
					VRAM.write(addr &= 0x03FF, data, 1);
				if (addr >= 0x0400 && addr <= 0x07FF)
					VRAM.write(addr &= 0x03FF, data, 2);
				if (addr >= 0x0800 && addr <= 0x0BFF)
					VRAM.write(addr &= 0x03FF, data, 1);
				if (addr >= 0x0C00 && addr <= 0x0FFF)
					VRAM.write(addr &= 0x03FF, data, 2);
			}
			else if (Cart.mirror.equals(MIRROR.HORIZONTAL))
			{
				// Horizontal
				if (addr >= 0x0000 && addr <= 0x03FF)
					VRAM.write(addr &= 0x03FF, data, 1);
				if (addr >= 0x0400 && addr <= 0x07FF)
					VRAM.write(addr &= 0x03FF, data, 1);
				if (addr >= 0x0800 && addr <= 0x0BFF)
					VRAM.write(addr &= 0x03FF, data, 2);
				if (addr >= 0x0C00 && addr <= 0x0FFF)
					VRAM.write(addr &= 0x03FF, data, 2);
			}
		}
		else if (addr >= 0x3F00 && addr <= 0x3FFF)
		{
			addr &= 0x001F;
			if (addr == 0x0010) addr = 0x0000;
			if (addr == 0x0014) addr = 0x0004;
			if (addr == 0x0018) addr = 0x0008;
			if (addr == 0x001C) addr = 0x000C;
			Palette.write(addr, data);
		}


	}
}
