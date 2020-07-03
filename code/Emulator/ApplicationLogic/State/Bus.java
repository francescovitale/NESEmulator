package Emulator.ApplicationLogic.State;

public class Bus {

	Memory Ram;
	private volatile static Bus BUS = null;			//Singleton

	//Costruttore privato
	
	private Bus() {
		//Collegamento con RAM
		Ram = Memory.getIstance();
	}

	//Punto di ingresso globale all'istanza
	
	public static Bus getIstance() {
		if(BUS==null) {
			synchronized(Bus.class) {
				if(BUS==null) {
					BUS = new Bus();
				}
			}
		}
		return BUS;
	}

	
	public Byte readRam(char Address) {
		// TODO - implement Bus.readRam
		
		Byte data= null;
		
		data= Ram.read(Address);
		
		return data;
	}

	public void writeRam(char Address, Byte Data) {
		// TODO - implement Bus.writeRam
		
		Ram.write(Address, Data);
		
	}


}