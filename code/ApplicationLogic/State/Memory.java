package ApplicationLogic.State;

import java.util.*;

public class Memory {

	Bus Bus;
	Mapper Mapper;
	private ArrayList<Byte> Word;
	

	/**
	 * 
	 * @param Address
	 */
	public Byte read(Byte Address) {
		// TODO - implement Memory.read
		
		Byte data = null;
		
		Word.add ( (byte) 100);
		Word.add ( (byte) 101);
		Word.add ( (byte) 102);
		Word.add ( (byte) 103);
		Word.add ( (byte) 104);
		Word.add ( (byte) 105);
		Word.add ( (byte) 106);
		Word.add ( (byte) 107);
		
		
		
		if( (Address >= 0x0000) &&  (Address <= 0xFFFF)) {
		
			data=   Word.get(Address);
		
		}
		
		return data;
		 
	}

	/**
	 * 
	 * @param Bank
	 */
	public void loadBank(ArrayList<Byte> Bank) {
		// TODO - implement Memory.loadBank
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Address
	 * @param Data
	 */
	public void write(Byte Address, Byte Data) {
		// TODO - implement Memory.write
		
		if( (Address >= 0x0000) &&  (Address <= 0xFFFF)) {
			
			Word.add(Address, Data);
		}
		
	}

}