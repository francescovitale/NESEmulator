package Emulator.Control;

import java.util.ArrayList;

import Emulator.ApplicationLogic.State.OAMState;
import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.OAM;

public class ReturnedOAMState {
	//Attributi
	private volatile static ReturnedOAMState ReturnedOAMState = null;			//Singleton
	private ArrayList<Byte> Sprites;
	
	//Costruttore privato
	private ReturnedOAMState() {
		Sprites = new ArrayList<Byte>();
		for(int i = 0; i < 256; i++)
			Sprites.add((byte)0x0);
	}

	//Punto di ingresso globale all'istanza
	public static ReturnedOAMState getInstance() {
		if(ReturnedOAMState==null) {
			synchronized(ReturnedOAMState.class) {
				if(ReturnedOAMState==null) {
					ReturnedOAMState = new ReturnedOAMState();
				}
			}
		}
		return ReturnedOAMState;
	}

	public ArrayList<Byte> getSprites() {
		return Sprites;
	}

	public void setSprites(ArrayList<Byte> sprites) {
		Sprites = sprites;
	}
	
	
}
