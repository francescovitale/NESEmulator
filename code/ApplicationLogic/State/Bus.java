package ApplicationLogic.State;

public class Bus {

	Memory Ram;

	/**
	 * 
	 * @param Address
	 */
	public Byte readRam(Byte Address) {
		// TODO - implement Bus.readRam
		
		Byte data= null;
		
		data= Ram.read(Address);
		
		return data;
	}

	/**
	 * 
	 * @param Address
	 * @param Data
	 */
	public void writeRam(Byte Address, Byte Data) {
		// TODO - implement Bus.writeRam
		
		Ram.write(Address, Data);
		
	}

}