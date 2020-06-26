package ApplicationLogic;

import ApplicationLogic.Interpreter.*;
import ApplicationLogic.State.*;

public class EmulatorFacade {

	ControlUnit ControlUnit;
	Mapper Mapper;
	
	
	public EmulatorFacade() {}
	
	public Boolean initProgram(String ROMName, Integer ID) {

		Mapper.getIstance().loadData(ROMName, ID);
		
		return true;
	}

	public Boolean startCycle() {

		ControlUnit.getInstance().execCycle();
		return true;
	}

}