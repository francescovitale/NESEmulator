package Emulator.ApplicationLogic.State.IOSubSystem;

import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.PPU;

public class Joypad {
	
	private volatile static Joypad PAD = null;		
	Byte controller;
	Byte controller_state;
	
	private Joypad() {
		controller = 0x00;
		controller_state = 0x00;
	}
	
	public static Joypad getInstance(){
		if(PAD==null) {
			synchronized(Joypad.class) {
				if(PAD==null) {
					PAD= new Joypad();
				}
			}
		}
		return PAD;
	}

	public Byte getController() {
		return controller;
	}
 
	public void setController(Byte controller) {
		//System.out.println("Controller: " + Integer.toBinaryString(controller));
		this.controller = controller;
	}
 
	public Byte getController_state() {
		
		return controller_state;
	}

	public void setController_state(Byte controller_state) {
		//System.out.println("Controller_state: " + Integer.toBinaryString(controller_state));
		this.controller_state = controller_state;
	}

}
