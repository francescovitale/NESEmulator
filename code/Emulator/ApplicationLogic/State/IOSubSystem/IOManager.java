package Emulator.ApplicationLogic.State.IOSubSystem;

import Emulator.ApplicationLogic.State.Bus;
import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.PPU;

public class IOManager {
	
	private volatile static IOManager IOManager = null;
	
	private Byte PPUStatus;
	private Byte PPUMask;
	private Byte PPUControl;
	private Byte PPUAddress;
	private Byte PPUData;
	
	private Bus CpuBus;
	private PPU PictureProcessingUnit;
	
	private IOManager() {
		
		PPUStatus = 0x00;
		PPUMask = 0x00;
		PPUControl = 0x00;
		PPUAddress = 0x00;
		PPUData = 0x00;
		
		CpuBus = Bus.getInstance();	
		PictureProcessingUnit = PPU.getInstance();
	}

	//Punto di ingresso globale all'istanza
	
	public static IOManager getInstance() {
		if(IOManager==null) {
			synchronized(IOManager.class) {
				if(IOManager==null) {
					IOManager = new IOManager();
				}
			}
		}
		return IOManager;
	}
	
	
	public Byte read(char address, Boolean rdonly) {
		
		Byte data = 0x00;

		switch (address)
		{
		case 0x0000: // Control
			break;
		case 0x0001: // Mask
			break;
		case 0x0002: // Status
			break;
		case 0x0003: // OAM Address
			break;
		case 0x0004: // OAM Data
			break;
		case 0x0005: // Scroll
			break;
		case 0x0006: // PPU Address
			break;
		case 0x0007: // PPU Data
			break;
		}

		return data;

	}
	
	public void write(char address, Byte data) {
		
		switch (address)
		{
		case 0x0000: // Control
			break;
		case 0x0001: // Mask
			break;
		case 0x0002: // Status
			break;
		case 0x0003: // OAM Address
			break;
		case 0x0004: // OAM Data
			break;
		case 0x0005: // Scroll
			break;
		case 0x0006: // PPU Address
			break;
		case 0x0007: // PPU Data
			break;
		}
	}
	
	public Byte getPPUStatus(){
		return null;
	}
	
	public void setPPUStatus(Byte Status) {
		
	}
	
	public Byte getPPUControl(){
		return null;
	}
	
	public void setPPUControl(Byte Control) {
		
	}
	
	public Byte getPPUMask(){
		return null;
	}
	
	public void setPPUMask(Byte Mask) {
		
	}

	
}
