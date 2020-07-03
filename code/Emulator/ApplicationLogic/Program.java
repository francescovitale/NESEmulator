package Emulator.ApplicationLogic;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Program {
	@Id
	private Integer ID;
	@Column
	private String Name;
	@Column
	private String ROMData;
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
	public String getROMData() {
		return ROMData;
	}
	public void setROMData(String rOMData) {
		ROMData = rOMData;
	}
	public ArrayList<Byte> getParsedROMData(){
		ArrayList<Byte> ReturnedByteList = new ArrayList<Byte>();
		Byte temp;
		String[] parts = ROMData.split(" ");
		    
		    
		for(int i = 0; i<parts.length; i++)
		{
		    temp = parseUnsignedByte(parts[i]);
		    ReturnedByteList.add(temp);
		}
		
		return ReturnedByteList;
	}
	
	protected Byte parseUnsignedByte(String StringByte)
	{
		Integer MyInteger = Integer.parseInt(StringByte,16);
		if(MyInteger > 127)
			MyInteger = MyInteger - 256;
		Byte MyByte = (byte)(int)MyInteger;
		return MyByte;
	}
	
	public Program(Integer iD, String name, String rOMData)
	{
		ID = iD;
		Name = name;
		ROMData = rOMData;
	}
	public Program() {
	}

}
