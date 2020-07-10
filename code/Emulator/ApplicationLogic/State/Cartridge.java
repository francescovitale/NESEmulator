package Emulator.ApplicationLogic.State;

import java.util.ArrayList;

import Emulator.ApplicationLogic.Program;
import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.PPUBus;
import Emulator.ApplicationLogic.State.MapperSubsystem.Mapper;
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
	
	private PPUBus PBus;
	
	private Mapper pMapper;
	
	public enum MIRROR
	{
		HORIZONTAL,
		VERTICAL,
		ONESCREEN_LO,
		ONESCREEN_HI
	}
	public MIRROR mirror;
	
	//Varibili di comodo
	Byte data;
	
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
		//PBus = PPUBus.getInstance();
	}

	//Punto di ingresso globale all'istanza
	
	public static Cartridge getInstance() {
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
		char[] name = new char[2];
		Byte prg_rom_chunks;
		Byte chr_rom_chunks;
		Byte mapper1;
		Byte mapper2;
		Byte prg_ram_size;
		Byte tv_system1;
		Byte tv_system2;
		Byte []unused = new Byte[5];
		
		//Variabili di appoggio
		Integer indice;
		Integer ind0;
		
		//Inizializzo i valori dell'Header
		ind0 = 0;
		for(int i= 0; i<4; i=i+2) {
			name[ind0] = (char)((ROM.get(i) << 8) | ROM.get(i+1));
			ind0++;
		}
		prg_rom_chunks = ROM.get(4);
		chr_rom_chunks = ROM.get(5);
		mapper1 = ROM.get(6);
		mapper2 = ROM.get(7);
		prg_ram_size = ROM.get(8);
		tv_system1 = ROM.get(9);
		tv_system2 = ROM.get(10);
		
		ind0 = 0;
		for(int i= 11; i<16; i++) {
			unused[ind0] = ROM.get(i);
			ind0++;
		}
		
		if ((mapper1 & 0x04) != 0x00) {
			//Vado avanti di 512 byte
			indice = 512 * 8;
		}
		else {
			indice = 16;	//Da verificare
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
			while (ind0 < nPRGBanks * 16384 && (indice<ROM.size())) {
				vPRGMemory.add( ROM.get(indice));
				indice++;
				ind0++;
			}
			//Prelevo dalla ROM tutti i banks "character"
			nCHRBanks = (int)chr_rom_chunks;
			vCHRMemory.ensureCapacity(nCHRBanks * 8192);
			ind0 = 0;
			while (ind0 < nCHRBanks * 8192 && (indice<ROM.size())) {
				vCHRMemory.add(ROM.get(indice));
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
				pMapper = Mapper.getInstance("Mapper_000"); 
				pMapper.setAttributes(nPRGBanks, nCHRBanks);
				//System.out.println("\n" + pMapper.getClass().getName());
				break;
		}
		
		
		bImageValid = true;
		
		return true;
		
	}
	
	//Per Connettere la Cartridge sul BUS principale
	public boolean Read(char addr)
	{
		char mapped_addr = 0x0000;
		if (pMapper.mapRead(addr))
		{
			mapped_addr = pMapper.getMapped_addr();
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
		if (pMapper.mapWrite(addr))
		{
			mapped_addr = pMapper.getMapped_addr();
			vPRGMemory.set(mapped_addr, data);
			return true;
		}
		else
			return false;
	}

	//Per Connettere la Cartridge sul BUS della PPU
	public boolean ppuRead(char addr)
	{
		Character mapped_addr = 0x0000;
		if (pMapper.ppuMapRead(addr))
		{
			mapped_addr = pMapper.getMapped_addr();
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
		if (pMapper.ppuMapWrite(addr))
		{
			mapped_addr = pMapper.getMapped_addr();
			vCHRMemory.set(mapped_addr, data);
			return true;
		}
		else
			return false;
	}

	//FUNZIONI DI UTILITA'
	
	//Getter and setter
	public Byte getData() {
		return data;
	}

	public void dumpCartridge() {
		
		for(int i = 0; i < ROM.size(); i++) {
			System.out.print(Integer.toHexString(ROM.get(i)) + " ");
		}
		System.out.println("\n");
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