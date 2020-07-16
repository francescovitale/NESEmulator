package Emulator.Control;

import java.util.ArrayList;

import Emulator.ApplicationLogic.State.VramMemoryState;
import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.VRAMMemory;

public class ReturnedVramMemoryState {
	//Attributi
	private volatile static ReturnedVramMemoryState ReturnedVramMemoryState = null;
	private ArrayList<Byte> NameTable1;
	private ArrayList<Byte> NameTable2;
	
	//Costruttore privato
	private ReturnedVramMemoryState() {
		NameTable1 = new ArrayList<Byte>();
		for(int i = 0; i < (int)0x400; i++)
			NameTable1.add((byte)0x0);
		NameTable2 = new ArrayList<Byte>();
		for(int i = 0; i < (int)0x400; i++)
			NameTable2.add((byte)0x0);
	}

	//Punto di ingresso globale all'istanza
	public static ReturnedVramMemoryState getInstance() {
		if(ReturnedVramMemoryState==null) {
			synchronized(ReturnedVramMemoryState.class) {
				if(ReturnedVramMemoryState==null) {
					ReturnedVramMemoryState = new ReturnedVramMemoryState();
				}
			}
		}
		return ReturnedVramMemoryState;
	}
 
	public ArrayList<Byte> getNameTable1() {
		return NameTable1;
	}

	public void setNameTable1(ArrayList<Byte> nameTable1) {
		NameTable1 = nameTable1;
	}

	public ArrayList<Byte> getNameTable2() {
		return NameTable2;
	}

	public void setNameTable2(ArrayList<Byte> nameTable2) {
		NameTable2 = nameTable2;
	}
	

}
