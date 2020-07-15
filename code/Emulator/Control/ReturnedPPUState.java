package Emulator.Control;

import java.util.ArrayList;

import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.Pixel;

public class ReturnedPPUState {
	
	private volatile static ReturnedPPUState PPUState = null;			//Singleton
	private ArrayList<Pixel> returnedPixels;

	//Costruttore privato
	private ReturnedPPUState() {
		
		returnedPixels = new ArrayList<Pixel>();
	}

	//Punto di ingresso globale all'istanza
	public static ReturnedPPUState getIstance() {
		if(PPUState==null) {
			synchronized(ReturnedPPUState.class) {
				if(PPUState==null) {
					PPUState = new ReturnedPPUState();
				}
			}
		}
		return PPUState;
	} 

	public ArrayList<Pixel> getReturnedPixels() {
		return returnedPixels;
	}

	public void setReturnedPixels(ArrayList<Pixel> returnedPixels) {
		this.returnedPixels = new ArrayList<Pixel>(returnedPixels);
	}
	
	
	
}

