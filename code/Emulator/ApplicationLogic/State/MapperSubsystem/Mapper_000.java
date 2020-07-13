package Emulator.ApplicationLogic.State.MapperSubsystem;

import java.util.ArrayList;

public class Mapper_000 extends Mapper{

	//private volatile static Mapper_000 Map = null;			//Singleton
	private int nPRGBanks;
	private int nCHRBanks;
	
	//Variabile di appoggio
	private char mapped_addr;
	
	protected Mapper_000() {}
	

	public void setAttributes(Integer prgBanks, Integer chrBanks) {
		nPRGBanks = (int)prgBanks;
		nCHRBanks = (int)chrBanks;
	}

	@Override
	public boolean mapRead(char addr) {
		// se il programma � 16KB
		//     CPU Address Bus          PRG ROM
		//     0x8000 -> 0xBFFF: Map    0x0000 -> 0x3FFF
		//     0xC000 -> 0xFFFF: Mirror 0x0000 -> 0x3FFF
		// se il programma � 16KB
		//     CPU Address Bus          PRG ROM
		//     0x8000 -> 0xFFFF: Map    0x0000 -> 0x7FFF	
		if (addr >= 0x8000 && addr <= 0xFFFF)
		{
			mapped_addr = (char)(addr & (nPRGBanks > 1 ? 0x7FFF : 0x3FFF));
			return true;
		}
		return false;
	}

	@Override
	public boolean mapWrite(char addr) {
		if (addr >= 0x8000 && addr <= 0xFFFF)
		{
			mapped_addr = (char)(addr & (nPRGBanks > 1 ? 0x7FFF : 0x3FFF));
			return true;
		}

		return false;
	}

	@Override
	public boolean ppuMapRead(char addr) {
		// Non c'� bisogna del mapping per la PPU
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
	public boolean ppuMapWrite(char addr) {
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

	//GETTER AND SETTER
	public char getMapped_addr() {
		return mapped_addr;
	}
}
