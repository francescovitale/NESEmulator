package ApplicationLogic.State;

import java.util.*;

public class Memory {

	Bus Bus;
	Mapper Mapper;
	private ArrayList<Byte> Word;
	private volatile static Memory Mem = null;			//Singleton
	
	//Costruttore privato
	private Memory() {
		Word = new ArrayList<Byte>();
		for(int i = 0; i < (int)0xFFFF; i++)
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

		if( (Address >= 0x0000) &&  (Address <= 0xFFFF)) {
		
			data = Word.get(Address);
		
		}
		
		return data;
	}


	public void loadBank(ArrayList<Byte> Bank) {
		
		//I BANCHI SONO TEORICAMENTE CARICATI IN LOCAZIONI SPECIFICHE DI MEMORIA, TUTTAVIA PER IL MOMENTO LI CARICHEREMO NELLE PRIME LOCAZIONI.
		for(int i = 0; i < Bank.size(); i++) write((char) i, Bank.get(i));
		
	}


	public void write(char Address, Byte Data) {
		
		if( (Address >= 0x0000) &&  (Address <= 0xFFFF)) {
			
			Word.set(Address, Data);
		}
		
	}
	
	//FUNZIONI DI UTILITA'
	
	public void dumpMemory() {
		
	}

}