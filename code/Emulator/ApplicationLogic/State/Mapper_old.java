package Emulator.ApplicationLogic.State;

import java.util.ArrayList;


public class Mapper_old {

	Memory Ram;
	Cartridge Crtg;
	private Integer Type;								//NMapperId
	private Integer nPRGBanks;								//Numero di banchi program
	private Integer nCHRBanks;								//Numero di banchi Character
	private ArrayList<Byte> Bank;
	private volatile static Mapper_old Map = null;			//Singleton

	//Costruttore privato
	private Mapper_old() {
		Type = 0;
		nPRGBanks = 0;
		nCHRBanks = 0;
		Crtg = Cartridge.getIstance();
	}

	//Punto di ingresso globale all'istanza
	public static Mapper_old getIstance() {
		if(Map==null) {
			synchronized(Mapper_old.class) {
				if(Map==null) {
					Map = new Mapper_old();
				}
			}
		}
		return Map;
	}

}