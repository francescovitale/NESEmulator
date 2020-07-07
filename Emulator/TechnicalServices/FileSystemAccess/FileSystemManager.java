package Emulator.TechnicalServices.FileSystemAccess;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import Emulator.ApplicationLogic.Program;

public class FileSystemManager {
	
	private String Path;

	private volatile static FileSystemManager FileSystemManager = null;			//Singleton

	protected FileSystemManager() {
		
	}

	public ArrayList<Program> getProgram(String SelectedPath, int FileType){
		setPath(SelectedPath);
		ArrayList<Program> ReturnedProgram = new ArrayList<Program>();
		
		if(FileType == 0) {
			ArrayList<String> ConvertedData;
			String WholeConvertedData ="";
			ArrayList<Byte> FetchedByteArrayList = new ArrayList<Byte>(getByteData());
			ConvertedData = new ArrayList<String>(ByteToStringConverter(FetchedByteArrayList));
			for(int i=0; i<ConvertedData.size(); i++)
				WholeConvertedData = WholeConvertedData + ConvertedData.get(i);
			ReturnedProgram.add(new Program(0,"",WholeConvertedData));
		}
		else {
			String WholeData="";
			ArrayList<String> FetchedStringArrayList = new ArrayList<String>(getStringData());
			
			/*ArrayList<Byte> ConvertedData;
			ConvertedData = new ArrayList<Byte>(StringToByteConverter(FetchedStringArrayList));
			writeByteData(ConvertedData);
			ArrayList<Byte> FetchedByteArrayList  = new ArrayList<Byte>(getByteData());
			FetchedStringArrayList = new ArrayList<String>(ByteToStringConverter(FetchedByteArrayList));*/
			
			for(int i=0; i<FetchedStringArrayList.size(); i++)
				WholeData = WholeData + FetchedStringArrayList.get(i);
			ReturnedProgram.add(new Program(0,"",WholeData));
		}
		return ReturnedProgram;
		
	}
	
	public static FileSystemManager getInstance(){

		if(FileSystemManager==null) {

			synchronized(FileSystemManager.class) {

				if(FileSystemManager==null) {

					FileSystemManager = new FileSystemManager();

				}

			}

		}

		return FileSystemManager;

	}
	private ArrayList<String> getStringData(){
		ArrayList<String> ReturnedStringArrayList = new ArrayList<String>();
		try {
			Character[] ReadChars = new Character[2];
			FileInputStream FIS = new FileInputStream(Path);
			DataInputStream DIS = new DataInputStream(FIS);
			while(DIS.available()!=0) {
				ReadChars[0] = (char) DIS.read();
				ReadChars[1] = (char) DIS.read();
				ReturnedStringArrayList.add(ReadChars[0].toString()+ReadChars[1].toString());
			}
			DIS.close();
		}
		catch(Exception e) {
			System.out.println("errore");
		}
		return ReturnedStringArrayList;
	}
	private void writeStringData(ArrayList<String> StringVector) {
		try {
			
			FileOutputStream FOS = new FileOutputStream(Path);
			DataOutputStream DOS = new DataOutputStream(FOS);
			for(int i=0; i<StringVector.size(); i++)
			{
				DOS.writeBytes(StringVector.get(i));
			}
			DOS.close();
		}
		catch(Exception e) {
			
		}
	}
	private ArrayList<Byte> getByteData(){
		ArrayList<Byte> ReturnedByteArrayList = new ArrayList<Byte>();
		try {
			Byte ReadByte;
			FileInputStream FIS = new FileInputStream(Path);
			DataInputStream DIS = new DataInputStream(FIS);
			while(DIS.available()!=0)
			{
				ReadByte = (byte) DIS.readUnsignedByte();
				ReturnedByteArrayList.add(ReadByte);
			}
			
			DIS.close();
		}
		catch(Exception e) {
			
		}
		return ReturnedByteArrayList;
	}
	private void writeByteData(ArrayList<Byte> ByteVector) {
		Integer temp;
		try {
			FileOutputStream FOS = new FileOutputStream(Path);
			DataOutputStream DOS = new DataOutputStream(FOS);
			for(int i=0; i<ByteVector.size(); i++)
			{
				temp = Byte.toUnsignedInt(ByteVector.get(i));
				DOS.write(temp);
			}
			DOS.close();
		}
		catch(Exception e) {
			
		}
	}
	private ArrayList<Byte> StringToByteConverter(ArrayList<String> StringVector) {
		ArrayList<Byte> ReturnedByteArrayList = new ArrayList<Byte>();
		Integer temp;
		Byte bytetemp;
		for(int i=0; i<StringVector.size(); i=i+1)
		{
			temp = Integer.parseInt(StringVector.get(i),16);
			if(temp > 127)
				temp = temp - 256;
			bytetemp = (byte)(int)temp;
			ReturnedByteArrayList.add(bytetemp);
		}
		return ReturnedByteArrayList;
		
		
	}
	private ArrayList<String> ByteToStringConverter(ArrayList<Byte> ByteVector) {
		ArrayList<String> ReturnedStringArrayList = new ArrayList<String>();
		Integer temp;
		String stringtemp;
		for(int i=0; i<ByteVector.size(); i++)
		{
			temp = Byte.toUnsignedInt(ByteVector.get(i));
			stringtemp = Integer.toHexString(temp);
			switch(stringtemp) {
				case "0": {
					stringtemp = "00";
					break;
				}
				case "1": {
					stringtemp = "01";
					break;
				}
				case "2": {
					stringtemp = "02";
					break;
				}
				case "3": {
					stringtemp = "03";
					break;
				}
				case "4": {
					stringtemp = "04";
					break;
				}
				case "5": {
					stringtemp = "05";
					break;
				}
				case "6": {
					stringtemp = "06";
					break;
				}
				case "7": {
					stringtemp = "07";
					break;
				}
				case "8": {
					stringtemp = "08";
					break;
				}
				case "9": {
					stringtemp = "09";
					break;
				}
				case "a":{
					stringtemp = "0a";
					break;
				}
				case "b":{
					stringtemp = "0b";
					break;
				}
				case "c":{
					stringtemp = "0c";
					break;
				}
				case "d":{
					stringtemp = "0d";
					break;
				}
				case "e":{
					stringtemp = "0e";
					break;
				}
				case "f":{
					stringtemp = "0f";
					break;	
				}
			}
			ReturnedStringArrayList.add(stringtemp);
		}
		return ReturnedStringArrayList;
	}
	private void setPath(String SelectedPath) {
		Path = SelectedPath;
	}
}
