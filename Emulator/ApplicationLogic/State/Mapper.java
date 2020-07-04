package Emulator.ApplicationLogic.State;

public abstract class Mapper {
	
	
	public abstract boolean mapRead(char addr, Character mapped_addr);
	
	public abstract boolean mapWrite(char addr, Character mapped_addr);
	
	public abstract boolean ppuMapRead(char addr, Character mapped_addr);
	
	public abstract boolean ppuMapWrite(char addr, Character mapped_addr);

	protected abstract void setAttributes(Integer nPRGBanks, Integer nCHRBanks);
	
}
