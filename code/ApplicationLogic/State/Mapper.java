package ApplicationLogic.State;

import java.util.ArrayList;

public class Mapper {

	Memory Ram;
	Cartridge Cartridge;
	private Integer Type;
	private ArrayList<Byte> Bank;
	private volatile static Mapper Map = null;			//Singleton

	//Costruttore privato
	private Mapper() {}

	//Punto di ingresso globale all'istanza
	public static Mapper getIstance() {
		if(Map==null) {
			synchronized(Mapper.class) {
				if(Map==null) {
					Map = new Mapper();
				}
			}
		}
		return Map;
	}

	public void loadData(String ROMName, Integer ID) {
		
		ArrayList<Byte> Bank = new ArrayList<Byte>();
		
		Bank = Cartridge.getIstance().loadData(ROMName, ID); //Carico il banco dalla cartuccia
		Type = Cartridge.getIstance().selectMappingMode(); //Inizializzo il tipo
		Ram.getIstance().loadBank(Bank); //Carico il banco in RAM
		
	}

}