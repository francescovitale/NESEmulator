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
		
		
		/*DEBUG*/
		Word.set(0xFFFC, (byte)0x00);
		Word.set(0xFFFD, (byte)0x80);
		Word.set(0x8000, (byte)0xA2);
		Word.set(0xFFFA, (byte)0x00);
		Word.set(0xFFFB, (byte)0x10);
		Word.set(0x1000, (byte)0xAA);
		Word.set(0x1001, (byte)0xAB);
		
		/*Word.set(0xFFFC, (byte)0x00);
		Word.set(0xFFFD, (byte)0x80);
		Word.set(0x8000, (byte)0xA2);
		Word.set(0x8001, (byte)0x0A);
		Word.set(0x8002, (byte)0x8E);
		Word.set(0x8003, (byte)0x00);
		Word.set(0x8004, (byte)0x00);
		Word.set(0x8005, (byte)0xA2);
		Word.set(0x8006, (byte)0x03);
		Word.set(0x8007, (byte)0x8E);
		Word.set(0x8008, (byte)0x01);
		Word.set(0x8009, (byte)0x00);
		Word.set(0x800A, (byte)0xAC);
		Word.set(0x800B, (byte)0x00);
		Word.set(0x800C, (byte)0x00);
		Word.set(0x800D, (byte)0xA9);
		Word.set(0x800E, (byte)0x00);
		Word.set(0x800F, (byte)0x18);
		Word.set(0x8010, (byte)0x6D);
		Word.set(0x8011, (byte)0x01);
		Word.set(0x8012, (byte)0x00);
		Word.set(0x8013, (byte)0x88);
		Word.set(0x8014, (byte)0xD0);
		Word.set(0x8015, (byte)0xFA);
		Word.set(0x8016, (byte)0x8D);
		Word.set(0x8017, (byte)0x02);
		Word.set(0x8018, (byte)0x00);
		Word.set(0x8019, (byte)0x00);
		Word.set(0x801A, (byte)0x00);
		Word.set(0x801B, (byte)0x00);
		Word.set(0x801C, (byte)0x00);
		Word.set(0x801D, (byte)0x00);
		Word.set(0x801E, (byte)0x00);
		Word.set(0x801F, (byte)0x00);
		Word.set(0x8020, (byte)0x00);
		Word.set(0x8021, (byte)0x00);
		Word.set(0x8022, (byte)0x00);
		Word.set(0x8023, (byte)0x00);
		Word.set(0x8024, (byte)0x00);
		Word.set(0x8025, (byte)0x00);
		Word.set(0x8019, (byte)0xFF);*/
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

		//if( (Address >= 0x0000) &&  (Address <= 0x1FFF)) { il Controllo è fatto nel BUS.
			//System.out.println("Leggo all'indirizzo: ");
			//System.out.println((byte)Address);
			//System.out.println((byte)(Address>>8));
			data = Word.get(Address);     
			
			/*DEBUG*/
			//data = (byte)0x69;
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