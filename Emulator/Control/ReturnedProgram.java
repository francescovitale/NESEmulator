package Emulator.Control;

public class ReturnedProgram {
	
	private Integer ID;
	private String Name;

	
	
	public ReturnedProgram() {
		
		ID=0;
		Name= "";
		
		
	}
	
	
	public ReturnedProgram(ReturnedProgram RP) {
		
		
		ID= RP.ID;
		Name= RP.Name;
		
	}
	
	
	public ReturnedProgram(Integer id, String name) {
		
		
		ID= id;
		Name= name;
		
	}


	public Integer getID() {
		return ID;
	}


	public void setID(Integer iD) {
		ID = iD;
	}


	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}

}
