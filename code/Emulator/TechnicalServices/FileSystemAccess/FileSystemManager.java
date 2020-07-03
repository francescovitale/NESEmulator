package Emulator.TechnicalServices.FileSystemAccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileSystemManager {
	private volatile static FileSystemManager FileSystemManager = null;			//Singleton
	
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
	
	String DefaultPath = "";
	Path ParsedPath;
	FileSystem FS;
	protected FileSystemManager(){
		FS = FileSystems.getDefault();
	}
	
	protected Byte parseUnsignedByte(String StringByte)
	{
		Integer MyInteger = Integer.parseInt(StringByte,16);
		if(MyInteger > 127)
			MyInteger = MyInteger - 256;
		Byte MyByte = (byte)(int)MyInteger;
		return MyByte;
	}
	
	public ArrayList<Byte> getData() {
		
		ArrayList<Byte> ReturnedByteList = new ArrayList<Byte>();
		Byte temp;
		
		try (InputStream in = Files.newInputStream(ParsedPath);
			    BufferedReader reader =new BufferedReader(new InputStreamReader(in))) {
			    String ReadLine = reader.readLine();
			    while(ReadLine != null) {
				    String[] parts = ReadLine.split(" ");
				    
				    
				    for(int i = 0; i<parts.length; i++)
				    {
				    	temp = parseUnsignedByte(parts[i]);
				    	ReturnedByteList.add(temp);
				    }
				    
				    ReadLine = reader.readLine();
			    }
			} catch (IOException x) {
			    System.err.println(x);
			}
		return ReturnedByteList;
		
	}
	
	public void setDefaultPath(String path) {
		DefaultPath = path;
		ParsedPath = FS.getPath(DefaultPath);
	}

}
