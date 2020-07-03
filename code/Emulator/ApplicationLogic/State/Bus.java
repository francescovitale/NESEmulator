package Emulator.ApplicationLogic.State;

public class Bus {

	PPU P;											//Collegamento con la PPU
	Memory Ram;										//Collegamento con RAM
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

	
	
	
	public Byte read(char Address) {
		Byte data= null;
		P = PPU.getInstance();
		
		//Se l'address è compreso tra 0x0000 e 0x1FFF voglio leggere dalla RAM
		if( (Address >= 0x0000) &&  (Address <= 0x1FFF)) {
			data= Ram.read((char)(Address & 0x07FF));		//Faccio il mirroring della RAM (& 0x07FF)
		}
		//Se l'address è compreso tra 0x2000 e 0x3FFF voglio leggere dalla PPU
		else if((Address >= 0x2000) && (Address <= 0x3FFF)) {
			data = P.Read((char)(Address & 0x0007), false);	//Faccio il mirroring della PPU (& 0x0007)
		}
		else {
			data= Ram.read((char)(Address & 0x07FF));	//DEBUG
		}
		return data;
	}

	public void write(char Address, Byte Data) {
		P = PPU.getInstance();
		//Se l'address è compreso tra 0x0000 e 0x1FFF voglio scrivere in RAM
		if( (Address >= 0x0000) &&  (Address <= 0x1FFF)) {
			Ram.write((char)(Address & 0x07FF), Data);  //Faccio il mirroring della RAM (& 0x07FF)
		}
		//Se l'address è compreso tra 0x2000 e 0x3FFF voglio scrivere sulla PPU
		else if((Address >= 0x2000) && (Address <= 0x3FFF)) {
			P.Write((char)(Address & 0x0007), Data);	//Faccio il mirroring della PPU (& 0x0007)
		}
		else {
			Ram.write((char)(Address & 0x07FF), Data);  //DEBUG
		}
	}


}