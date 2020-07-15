package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;

public class Sprite {
	private Byte y;				//Coordinata y
	private Byte id;			//ID del tile dalla palette memory
	private Byte attribute;		//flag che mi indica come renderizzare lo sprite
	private Byte x;				//Coordinata x

	public Sprite(Byte y, Byte id, Byte attribute, Byte x) {
		this.y = y;
		this.id = id;
		this.attribute = attribute;
		this.x = x;
	}
	
	public Sprite() {
		y = 0x00;
		id = 0x00;
		attribute = 0x00;
		x = 0x00;
	}
	public Sprite(Sprite S) {
		y = S.y;
		id = S.id;
		attribute = S.attribute;
		x = S.x;
	}
}
