package Emulator.ApplicationLogic.State;

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

	public void loadData(ArrayList<Byte> ROMData) {
		
		ArrayList<Byte> Bank = new ArrayList<Byte>();
		Bank = ROMData;
		Cartridge.getIstance().loadData(ROMData); //Carico il banco dalla cartuccia
		for(int i = 0; i < Bank.size(); i++)
		{
		    System.out.println(Bank.get(i));
		}
		Type = Cartridge.getIstance().selectMappingMode(); //Inizializzo il tipo
		Ram.getIstance().loadBank(Bank); //Carico il banco in RAM
		
	}

}