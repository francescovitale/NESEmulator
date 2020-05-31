package ApplicationLogic.State;

import java.util.*;

public class Memory {

	Bus Bus;
	Mapper Mapper;
	private Collection<ArrayList<Byte>> Word;

	/**
	 * 
	 * @param Address
	 */
	public Byte read(Byte Address) {
		// TODO - implement Memory.read
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

}