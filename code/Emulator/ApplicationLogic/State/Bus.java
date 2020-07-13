package Emulator.ApplicationLogic.State;

import Emulator.ApplicationLogic.State.IOSubSystem.IOManager;
import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.PPU;

public class Bus {

	Memory Ram;										//Collegamento con RAM
	Cartridge Crtg;									//Collegamento con il Cartridge
	IOManager IOM;
	private volatile static Bus BUS = null;			//Singleton

	//Costruttore privato
	
	private Bus() {
		//Collegamento con RAM
		Ram = Memory.getInstance();
		//Collegamento con la Cartridge
		Crtg = Cartridge.getInstance();
		 
		//IOM = IOManager.getInstance();
	}

	//Punto di ingresso globale all'istanza
	
	public static Bus getInstance() {
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
		Byte data = null;
		IOM = IOManager.getInstance();
		
		if (Crtg.Read(Address))
		{

			data = Crtg.getData();

			/*System.out.println("Add read C: " + Integer.toHexString(Address));
			System.out.println("Data read C: " + Integer.toHexString(data));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		//Se l'address è compreso tra 0x0000 e 0x1FFF voglio leggere dalla RAM
		else if( (Address >= 0x0000) &&  (Address <= 0x1FFF)) {
			data= Ram.read((char)(Address & 0x07FF));		//Faccio il mirroring della RAM (& 0x07FF)
			/*System.out.println("Add read M: " + Integer.toHexString(Address));
			System.out.println("Data read M: " + Integer.toHexString(data));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		//Se l'address è compreso tra 0x2000 e 0x3FFF voglio leggere dalla PPU
		else {
			data = IOM.read((char)(Address),false);	//Faccio il mirroring della PPU (& 0x0007)
			/*System.out.println("Add read M: " + Integer.toHexString(Address));
			System.out.println("Data read M: " + Integer.toHexString(data));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		/*else {
			data= Ram.read((char)(Address & 0x07FF));	//DEBUG
		}*/
		/*DEBUG*/
		//data= Ram.read((char)(Address));
		return data;
	}

	public void write(char Address, Byte Data) {
		//Se l'address è compreso tra 0x0000 e 0x1FFF voglio scrivere in RAM
		if (Crtg.Write(Address, Data))
		{
			/*System.out.println("Add C: " + Integer.toHexString(Address));
			System.out.println("Data C: " + Integer.toHexString(Data));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			// Cartridge Address Range
		}
		else if( (Address >= 0x0000) &&  (Address <= 0x1FFF)) {
			/*System.out.println("Add M: " + Integer.toHexString(Address));
			System.out.println("Data M: " + Integer.toHexString(Data));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			Ram.write((char)(Address & 0x07FF), Data);  //Faccio il mirroring della RAM (& 0x07FF)
		}
		//Se l'address è compreso tra 0x2000 e 0x3FFF voglio scrivere sulla PPU
		else{
			/*System.out.println("Add P: " + Integer.toHexString(Address));
			System.out.println("Data P: " + Integer.toHexString(Data));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			IOM.write((char)(Address), Data);	//Faccio il mirroring della PPU (& 0x0007)
		}
		/*else {
			Ram.write((char)(Address & 0x07FF), Data);  //DEBUG
		}*/
		/*DEBUG*/
		//Ram.write((char)(Address), Data);
	}

 
	public void reset(){}	//Da implementare
	
	public void clock(){} 	//Da implementare
}