package Emulator.ApplicationLogic.State;

import java.util.*;

public class Memory {

	Bus Bus;
	private ArrayList<Byte> Word;
	private volatile static Memory Mem = null;			//Singleton
	
	//Costruttore privato
	private Memory() {
		Word = new ArrayList<Byte>();
		for(int i = 0; i < (int)0x1FFF; i++)
			Word.add((byte)0x0);

	}

	//Punto di ingresso globale all'istanza
	public static Memory getIstance() {
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

		//if( (Address >= 0x0000) &&  (Address <= 0x1FFF)) { il Controllo è fatto nel BUS.
		
			//data = Word.get(Address & 0x07FF);     //Faccio il mirroring della RAM (& 0x07FF) e leggo nella posizione corretta
			
			/*DEBUG*/
			data = (byte)0x69;
		//}
		
		return data;
	}

	public void write(char Address, Byte Data) {

		//if( (Address >= 0x0000) &&  (Address <= 0x1FFF)) {  il Controllo è fatto nel BUS.
			Word.set(Address, Data);		
		//}
		
	}

	
	//GETTER
		public ArrayList<Byte> getWord() {
			return Word;
		}
	
	//FUNZIONI DI UTILITA'
	
	public void dumpMemory() {
		
	}

}