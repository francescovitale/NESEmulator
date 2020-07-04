package Emulator.ApplicationLogic.State;

import java.util.ArrayList;

import Emulator.ApplicationLogic.Program;
import Emulator.TechnicalServices.TechnicalServicesFacade;

public class Cartridge {
	private volatile static Cartridge Cart = null;			//Singleton
	
	private Integer nMapperID;
	private Integer nPRGBanks;
	private Integer nCHRBanks;
	private boolean bImageValid;
	
	//La memoria è divisa in program e character entrambi di grandezza che dipende dalla specifica Cartridge
	private ArrayList<Byte> vPRGMemory;				//Program
	private ArrayList<Byte> vCHRMemory;				//Character
	private ArrayList<Byte> ROM;					//ROM completa
	
	Mapper pMapper;
	
	public enum MIRROR
	{
		HORIZONTAL,
		VERTICAL,
		ONESCREEN_LO,
		ONESCREEN_HI
	}
	public MIRROR mirror;
	
	//Costruttore privato
	private Cartridge() {
		vPRGMemory = new ArrayList<Byte>();
		vCHRMemory = new ArrayList<Byte>();
		ROM = new ArrayList<Byte>();
		nMapperID = 0;
		nPRGBanks = 0;
		nCHRBanks = 0;
		mirror = MIRROR.HORIZONTAL;
		bImageValid = false;
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


	public boolean ImageValid()
	{
		return bImageValid;
	}
	
	public Integer selectMappingMode() {
		
		//TORNERà IL TIPO DI MAPPING LEGGENDO DALLA ROM. COME LO FACCIA NON è DATO ANCORA SAPERLO
		
		return 0;
	}

	//DA MODIFICARE
	public boolean loadData(ArrayList<Byte> Programma) {
		//Prendo la ROM della cartuccia inserita
		ROM = new ArrayList<Byte>(Programma);
		
		// iNES Format Header
		char[] name = new char[4];
		Byte prg_rom_chunks;
		Byte chr_rom_chunks;
		Byte mapper1;
		Byte mapper2;
		Byte prg_ram_size;
		Byte tv_system1;
		Byte tv_system2;
		char[] unused = new char[5];
		
		//Variabili di appoggio
		Integer indice;
		Integer ind0;
		
		//Inizializzo i valori dell'Header
		ind0 = 0;
		for(int i= 0; i<8; i=i+2) {
			name[ind0] = (char)((ROM.get(i+1) << 8) | ROM.get(i));
			ind0++;
		}
		prg_rom_chunks = ROM.get(8);
		chr_rom_chunks = ROM.get(9);
		mapper1 = ROM.get(10);
		mapper2 = ROM.get(11);
		prg_ram_size = ROM.get(12);
		tv_system1 = ROM.get(13);
		tv_system2 = ROM.get(14);
		ind0 = 0;
		for(int i= 15; i<25; i=i+2) {
			unused[ind0] = (char)((ROM.get(i+1) << 8) | ROM.get(i));
			ind0++;
		}
		
		if ((mapper1 & 0x04) != 0x00) {
			//Vado avanti di 512 byte
			indice = 512 * 8;
		}
		else {
			indice = 26;	//Da verificare
		}
		// Determino il Mapper ID
		nMapperID = ((mapper2 >> 4) << 4) | (mapper1 >> 4);
		mirror = (mapper1 & 0x01)!=0 ? MIRROR.VERTICAL : MIRROR.HORIZONTAL;
		
		// "Discover" del File Format
		Byte nFileType = 1;
		

		if (nFileType == 0)
		{

		}
		if (nFileType == 1)
		{
			//Prelevo dalla ROM tutti i banks "program"
			nPRGBanks = (int)prg_rom_chunks;
			vPRGMemory.ensureCapacity(nPRGBanks * 16384);
			ind0 = 0;
			while (ind0 < nPRGBanks * 16384 & (indice<ROM.size())) {
				vPRGMemory.set(ind0, ROM.get(indice));
				indice++;
				ind0++;
			}
			//Prelevo dalla ROM tutti i banks "character"
			nCHRBanks = (int)chr_rom_chunks;
			vCHRMemory.ensureCapacity(nCHRBanks * 8192);
			ind0 = 0;
			while (ind0 < nCHRBanks * 8192 & (indice<ROM.size())) {
				vCHRMemory.set(ind0, ROM.get(indice));
				indice++;
				ind0++;
			}
		}
		if (nFileType == 2)
		{

		}
		// Load appropriate mapper
		switch (nMapperID)
		{
			case 0: 
				pMapper = Mapper_000.getIstance(); 
				pMapper.setAttributes(nPRGBanks, nCHRBanks);
				break;
		}
		
		
		bImageValid = true;
		
		return true;
		
	}
	
	//Per Connettere la Cartridge sul BUS principale
	public boolean Read(char addr, Byte data)
	{
		Character mapped_addr = 0x0000;
		if (pMapper.mapRead(addr, mapped_addr))
		{
			data = vPRGMemory.get((int)mapped_addr);
			return true;
		}
		else
			return false;
		
	}

	//Per Connettere la Cartridge sul BUS principale
	public boolean Write(char addr, Byte data)
	{
		Character mapped_addr = 0x0000;
		if (pMapper.mapWrite(addr, mapped_addr))
		{
			vPRGMemory.set(mapped_addr, data);
			return true;
		}
		else
			return false;
	}

	//Per Connettere la Cartridge sul BUS della PPU
	public boolean ppuRead(char addr, Byte data)
	{
		Character mapped_addr = 0x0000;
		if (pMapper.ppuMapRead(addr, mapped_addr))
		{
			data = vCHRMemory.get(mapped_addr);
			return true;
		}
		else
			return false;
	}

	//Per Connettere la Cartridge sul BUS della PPU
	public boolean ppuWrite(char addr, Byte data)
	{
		Character mapped_addr = 0x0000;
		if (pMapper.ppuMapWrite(addr, mapped_addr))
		{
			vCHRMemory.set(mapped_addr, data);
			return true;
		}
		else
			return false;
	}

	//FUNZIONI DI UTILITA'
	
	public void dumpCartridge() {
		for(int i = 0; i < vPRGMemory.size(); i++) {
			System.out.print(vPRGMemory.get(i) + " ");
		}
		System.out.print("\n");
		for(int i = 0; i < vCHRMemory.size(); i++) {
			System.out.print(vCHRMemory.get(i) + " ");
		}
		System.out.print("\n");
	}

}