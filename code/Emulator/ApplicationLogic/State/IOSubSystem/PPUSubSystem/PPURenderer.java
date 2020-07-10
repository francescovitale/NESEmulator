package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;

import Emulator.ApplicationLogic.State.ClockManager;
import Emulator.ApplicationLogic.State.IOSubSystem.IOManager;

public class PPURenderer {

	private volatile static PPURenderer PPURenderer = null;
	
	private PPU P;
	private PPUBus Bus;
	
	protected PPURenderer() {
		//P = PPU.getInstance();
		Bus = PPUBus.getInstance();
	};
	
	//Punto di ingresso globale all'istanza
	public static PPURenderer getInstance(){
		if(PPURenderer==null) {
			synchronized(PPURenderer.class) {
				if(PPURenderer==null) {
					PPURenderer= new PPURenderer();
				}
			}
		}
		return PPURenderer;
	}
	
	
	public void incrementScrollX() {
		
	}
	
	public void incrementScrollY() {
		
	}
	
	public void TransferAddressX() {
		
	}
	
	public void TransferAddressY() {
		
	}
	
	public void LoadBackgroundShifters(){
		
	}
	
	public void UpdateShifters() {
		
	}
	
	public void Render(Integer Scanline, Integer Cycle) {
		
	}
	
	public Byte PPURead(char addr)
	{
		return null;
	}

	public void PPUWrite(char addr, Byte data)
	{

	}
}


