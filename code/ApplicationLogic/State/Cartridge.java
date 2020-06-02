package ApplicationLogic.State;

import java.util.ArrayList;

public class Cartridge {

	private ArrayList<Byte> Bank;
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

	public ArrayList<Byte> loadData(String Nome, Integer ID) {
		
		ArrayList<Byte> ROM = new ArrayList<Byte>();
		//CARICA DATI DALLA REPOSITORY
		
		//BLOCCO TRY CATCH PER EVENTUALI ECCEZIONI DA IMPLEMENTARE
		for(int i = 0; i < 10; i++) {
			ROM.add(i,(byte)0xFF);
			Bank.add(i,(byte)0xFF);
		}
		
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