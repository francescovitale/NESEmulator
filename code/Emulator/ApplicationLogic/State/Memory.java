package Emulator.ApplicationLogic.State;

import java.util.*;

public class Memory {

	Bus Bus;
	private ArrayList<Byte> Word;
	private volatile static Memory Mem = null;			//Singleton
	
	//Costruttore privato
	private Memory() {
		Word = new ArrayList<Byte>();
		for(int i = 0; i < (int)0xFFFF; i++)
			Word.add((byte)0x0);
		

	}

	//Punto di ingresso globale all'istanza
	public static Memory getInstance() {
		if(Mem==null) {
			synchronized(Memory.class) {
				if(Mem==null) {
					Mem = new Memory();
				}
			}
		}
		return Mem;
	}
	

	public Byte read(char Address) {
		
		Byte data = null;


			data = Word.get(Address);     
			

		
		return data;
	}

	public void write(char Address, Byte Data) {


			Word.set(Address, Data);		

		
	}

	
	//GETTER
	public ArrayList<Byte> getWord() {
		return Word;
	}
	
	//FUNZIONI DI UTILITA'
	
	public void dumpMemory() {
		
	}

}