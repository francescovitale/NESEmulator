package Emulator.ApplicationLogic.State;

import java.util.ArrayList;

public class Mapper_000 extends Mapper{

	private volatile static Mapper_000 Map = null;			//Singleton
	private int nPRGBanks;
	private int nCHRBanks;
	
	
	private Mapper_000() {}
	

	//Punto di ingresso globale all'istanza
	public static Mapper_000 getIstance() {
		if(Map==null) {
			synchronized(Mapper_000.class) {
				if(Map==null) {
					Map = new Mapper_000();
				}
			}
		}
		return Map;
	}

	public void setAttributes(Integer prgBanks, Integer chrBanks) {
		nPRGBanks = (int)prgBanks;
		nCHRBanks = (int)chrBanks;
	}

	@Override
	public boolean mapRead(char addr, Character mapped_addr) {
		// se il programma è 16KB
		//     CPU Address Bus          PRG ROM
		//     0x8000 -> 0xBFFF: Map    0x0000 -> 0x3FFF
		//     0xC000 -> 0xFFFF: Mirror 0x0000 -> 0x3FFF
		// se il programma è 16KB
		//     CPU Address Bus          PRG ROM
		//     0x8000 -> 0xFFFF: Map    0x0000 -> 0x7FFF	
		if (addr >= 0x8000 && addr <= 0xFFFF)
		{
			if(nPRGBanks > 1)
				mapped_addr = (char)(addr & (0x7FFF));
			else
				mapped_addr = (char)(addr & (0x3FFF));
			return true;
		}
		return false;
	}

	@Override
	public boolean mapWrite(char addr, Character mapped_addr) {
		if (addr >= 0x8000 && addr <= 0xFFFF)
		{
			mapped_addr = (char)(addr & (nPRGBanks > 1 ? 0x7FFF : 0x3FFF));
			return true;
		}

		return false;
	}

	@Override
	public boolean ppuMapRead(char addr, Character mapped_addr) {
		// Non c'è bisogna del mapping per la PPU
		// PPU Address Bus          CHR ROM
		// 0x0000 -> 0x1FFF: Map    0x0000 -> 0x1FFF
		if (addr >= 0x0000 && addr <= 0x1FFF)
		{
			mapped_addr = addr;
			return true;
		}

		return false;
	}

	@Override
	public boolean ppuMapWrite(char addr, Character mapped_addr) {
		if (addr >= 0x0000 && addr <= 0x1FFF)
		{
			if (nCHRBanks == 0)
			{
				//funziona come una RAM
				mapped_addr = addr;
				return true;
			}
		}
		return false;
	}
	
}
