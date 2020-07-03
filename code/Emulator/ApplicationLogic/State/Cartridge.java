package Emulator.ApplicationLogic.State;

import java.util.ArrayList;

import Emulator.TechnicalServices.TechnicalServicesFacade;

public class Cartridge {

	private ArrayList<Byte> Bank;
	private TechnicalServicesFacade Tsf;
	private volatile static Cartridge Cart = null;			//Singleton

	//Costruttore privato
	
	private Cartridge() {
		Bank = new ArrayList<Byte>();
	}

	//Punto di ingresso globale all'istanza
	
	public static Cartridge getIstance() {
		if(Cart==null) {
			synchronized(Cartridge.class) {
				if(Cart==null) {
					Cart = new Cartridge();
				}
			}
		}
		return Cart;
	}


	public Integer selectMappingMode() {
		
		//TORNERà IL TIPO DI MAPPING LEGGENDO DALLA ROM. COME LO FACCIA NON è DATO ANCORA SAPERLO
		
		return 0;
	}

	public ArrayList<Byte> loadData(String Nome, Integer ID, String SelectedPath) {
		
		Tsf = new TechnicalServicesFacade();
		ArrayList<Byte> ROM;
		
		ROM = Tsf.loadCartridgeData(Nome, ID, SelectedPath);
		
		return ROM;
		
	}

	//FUNZIONI DI UTILITA'
	
	public void dumpCartridge() {
		for(int i = 0; i < Bank.size(); i++) {
			System.out.print(Bank.get(i) + " ");
		}
		System.out.print("\n");
	}

}