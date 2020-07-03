package Emulator.TechnicalServices.FileSystemAccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

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
	
	public String getData() {
		String ReturnedString = "";
		try (InputStream in = Files.newInputStream(ParsedPath);
			    BufferedReader reader =new BufferedReader(new InputStreamReader(in))) {
			    String ReadLine = reader.readLine();
			    while(ReadLine != null) {
			    	ReturnedString = ReturnedString + ReadLine + " ";
				    ReadLine = reader.readLine();
			    }
			} catch (IOException x) {
			    System.err.println(x);
			}
		return ReturnedString;
	}
	
	public void setDefaultPath(String path) {
		DefaultPath = path;
		ParsedPath = FS.getPath(DefaultPath);
	}

}
