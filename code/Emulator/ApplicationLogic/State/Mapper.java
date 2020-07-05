package Emulator.ApplicationLogic.State;

public abstract class Mapper {
	

	private volatile static Mapper_000 Map = null;			//Singleton
	
	public static Mapper_000 getInstance(String SelectedMapper) {
		if(SelectedMapper.equals("Mapper_000"))
		if(Map==null) {
			synchronized(Mapper_000.class) {
				if(Map==null) {
					Map = new Mapper_000();
				}
			}
		}
		return Map;
	}
	
	public abstract boolean mapRead(char addr, Character mapped_addr);
	
	public abstract boolean mapWrite(char addr, Character mapped_addr);
	
	public abstract boolean ppuMapRead(char addr, Character mapped_addr);
	
	public abstract boolean ppuMapWrite(char addr, Character mapped_addr);
	
}
