package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;

import java.util.ArrayList;

public class OAM {
	
	private volatile static OAM OAM_memory = null;
	
	private ArrayList<Byte> Sprite_bytes;
	
	protected OAM() {
		
		Sprite_bytes = new ArrayList<Byte>();
		for(int i = 0; i < 256; i++)
			Sprite_bytes.add((byte)0x00);
	};
	
	//Punto di ingresso globale all'istanza
	public static OAM getInstance(){
		if(OAM_memory==null) {
			synchronized(OAM.class) {
				if(OAM_memory==null) {
					OAM_memory= new OAM();
				}
			}
		}
		return OAM_memory;
	}
	
	public Byte read(char addr)
	{
		return Sprite_bytes.get(addr);
	}

	//Per Connettere la PPU sul BUS principale
	public void write(char addr, Byte data)
	{
		Sprite_bytes.set(addr, data);
	}
	
	public void dumpOAM() {
		
		System.out.println("\n");
		for(int i = 0; i < Sprite_bytes.size(); i++) {
			System.out.print(Integer.toHexString(Sprite_bytes.get(i)) + " ");
		}
		System.out.print("\n");
	}
}

