package Emulator.ApplicationLogic.State;

import java.util.ArrayList;

import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.Pixel;

public class PPUState {
	
	private volatile static PPUState PPUState = null;			//Singleton
	private ArrayList<Pixel> returnedPixels;

	//Costruttore privato
	private PPUState() {
		returnedPixels = new ArrayList<Pixel>();
		returnedPixels.add(new Pixel());
	}

	//Punto di ingresso globale all'istanza
	public static PPUState getInstance() {
		if(PPUState==null) {
			synchronized(PPUState.class) {
				if(PPUState==null) {
					PPUState = new PPUState();
				}
			}
		}
		return PPUState;
	}

	//Aggiorno lo stato della PPU
	public void refreshPPUState(ArrayList<Pixel> refreshedPixels) {
		returnedPixels = new ArrayList<Pixel>(refreshedPixels);
	}

	public ArrayList<Pixel> getReturnedPixels() {
		return returnedPixels;
	}

	public void setReturnedPixels(ArrayList<Pixel> returnedPixels) {
		this.returnedPixels = returnedPixels;
	}
	
	
}
