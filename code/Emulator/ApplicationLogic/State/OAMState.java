package Emulator.ApplicationLogic.State;

import java.util.ArrayList;

import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.OAM;

public class OAMState {
	//Attributi
	private volatile static OAMState OAMState = null;			//Singleton
	private ArrayList<Byte> Sprites;
	private OAM Oam;
	
	//Costruttore privato
	private OAMState() {
		Oam = OAM.getInstance();
		Sprites = new ArrayList<Byte>();
		for(int i = 0; i < 256; i++)
			Sprites.add((byte)0x0);
	}

	//Punto di ingresso globale all'istanza
	public static OAMState getInstance() {
		if(OAMState==null) {
			synchronized(OAMState.class) {
				if(OAMState==null) {
					OAMState = new OAMState();
				}
			}
		}
		return OAMState;
	}
	 
	//Aggiorno lo stato della memoria 
	public void refreshOAMState() {
		Sprites = Oam.getSprite_bytes();
	}

	public ArrayList<Byte> getSprites() {
		return Sprites;
	}

	public void setSprites(ArrayList<Byte> sprites) {
		Sprites = sprites;
	}
	
}
