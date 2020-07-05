package Emulator.UI.EmulatorBoundary;

import Emulator.ApplicationLogic.State.Bus;

public class UIConfiguration {

	private volatile static UIConfiguration Configuration = null;
	private Boolean Mode;
	private String ProgramName;
	private String Path;
	private Integer ProgramID;
	
	private UIConfiguration(){
		Mode = false;
		ProgramName = "";
		Path = "";
		ProgramID = -1;
	}
	
	public static UIConfiguration getIstance() {
		if(Configuration==null) {
			synchronized(UIConfiguration.class) {
				if(Configuration==null) {
					Configuration = new UIConfiguration();
				}
			}
		}
		return Configuration;
	}

	public Boolean getMode() {
		return Mode;
	}

	public void setMode(Boolean mode) {
		Mode = mode;
	}

	public String getProgramName() {
		return ProgramName;
	}

	public void setProgramName(String programName) {
		ProgramName = programName;
	}

	public String getPath() {
		return Path;
	}

	public void setPath(String path) {
		Path = path;
	}

	public Integer getProgramID() {
		return ProgramID;
	}

	public void setProgramID(Integer programID) {
		ProgramID = programID;
	}
	
	
}
