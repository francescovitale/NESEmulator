package Emulator.ApplicationLogic.Interpreter;

import Emulator.ApplicationLogic.State.StateFacade;

public class ControlUnitInterrupt extends ControlUnitState {
	
	private ControlUnit CU;
	
	protected ControlUnitInterrupt() {
		CU = ControlUnit.getInstance();
	}

	@Override
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
		
	}

	@Override
	public void execCycle() {
		//System.out.println("we");
		Boolean NMIRequest_temp;
		Boolean IRQRequest_temp;
		StateFacade Stf = new StateFacade();
		
		NMIRequest_temp = Stf.getNMIRequest();
		if(NMIRequest_temp == true)
		{
			Stf.setNMIRequest(false);
			Stf.Execute("NMI");
			changeState(CU,ControlUnitState.getInstance("Fetch"));
		}
		else {
			IRQRequest_temp = Stf.getIRQRequest();
			if(IRQRequest_temp == true) {
				Stf.setIRQRequest(false);
				Stf.Execute("IRQ");
			}
			changeState(CU,ControlUnitState.getInstance("Fetch"));
		}
		
	}

}
