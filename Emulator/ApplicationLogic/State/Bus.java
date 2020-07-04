package Emulator.ApplicationLogic.State;

public class Bus {

	PPU P;											//Collegamento con la PPU
	Memory Ram;										//Collegamento con RAM
	Cartridge Crtg;									//Collegamento con il Cartridge
	private volatile static Bus BUS = null;			//Singleton

	//Costruttore privato
	
	private Bus() {
		//Collegamento con RAM
		Ram = Memory.getIstance();
		//Collegamento con la Cartridge
		Crtg = Cartridge.getIstance();
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

	
	
	
	public Byte read(char Address) {
		Byte data= null;
		P = PPU.getInstance();
		
		/*if (Crtg.Read(Address, data))
		{
		        // The cartridge "sees all" and has the facility to veto
		        // the propagation of the bus transaction if it requires.
		        // This allows the cartridge to map any address to some
		        // other data, including the facility to divert transactions
		        // with other physical devices. The NES does not do this
		        // but I figured it might be quite a flexible way of adding
		        // "custom" hardware to the NES in the future!
		}
		//Se l'address � compreso tra 0x0000 e 0x1FFF voglio leggere dalla RAM
		if( (Address >= 0x0000) &&  (Address <= 0x1FFF)) {
			data= Ram.read((char)(Address & 0x07FF));		//Faccio il mirroring della RAM (& 0x07FF)
		}
		//Se l'address � compreso tra 0x2000 e 0x3FFF voglio leggere dalla PPU
		else if((Address >= 0x2000) && (Address <= 0x3FFF)) {
			data = P.Read((char)(Address & 0x0007), false);	//Faccio il mirroring della PPU (& 0x0007)
		}
		else {
			data= Ram.read((char)(Address & 0x07FF));	//DEBUG
		}*/
		/*DEBUG*/
		data= Ram.read((char)(Address));
		return data;
	}

	public void write(char Address, Byte Data) {
		P = PPU.getInstance();
		//Se l'address � compreso tra 0x0000 e 0x1FFF voglio scrivere in RAM
		/*Byte data = 0x00;
		if (Crtg.Write(Address, data))
		{
			// Cartridge Address Range
		}
		if( (Address >= 0x0000) &&  (Address <= 0x1FFF)) {
			Ram.write((char)(Address & 0x07FF), Data);  //Faccio il mirroring della RAM (& 0x07FF)
		}
		//Se l'address � compreso tra 0x2000 e 0x3FFF voglio scrivere sulla PPU
		else if((Address >= 0x2000) && (Address <= 0x3FFF)) {
			P.Write((char)(Address & 0x0007), Data);	//Faccio il mirroring della PPU (& 0x0007)
		}
		else {
			Ram.write((char)(Address & 0x07FF), Data);  //DEBUG
		}*/
		Ram.write((char)(Address), Data);
	}


	public void reset(){}	//Da implementare
	
	public void clock(){} //Da implementare
}