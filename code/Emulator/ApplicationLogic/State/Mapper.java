package Emulator.ApplicationLogic.State;

public abstract class Mapper {
	
	protected Integer nPRGBanks;
	protected Integer nCHRBanks;
	
	public Mapper(Integer prgBanks, Integer chrBanks) {
		nPRGBanks = prgBanks;
		nCHRBanks = chrBanks;
	}
	
	public abstract boolean mapRead(char addr, Character mapped_addr);
	
	public abstract boolean mapWrite(char addr, Character mapped_addr);
	
	public abstract boolean ppuMapRead(char addr, Character mapped_addr);
	
	public abstract boolean ppuMapWrite(char addr, Character mapped_addr);
	
}
