package ApplicationLogic.Interpreter;

import ApplicationLogic.State.StateFacade;

public class ControlUnitReset extends ControlUnitState {

	ControlUnit CU;
	StateFacade SF;
	private volatile static ControlUnitReset ControlUnitReset = null;		//Singleton
	
	
	protected ControlUnitReset() {
		//Collegamento con il facade dello STATE
		SF = new StateFacade();
	}
	
	//Punto di ingresso globale all'istanza
	public static ControlUnitReset getInstance(){
		if(ControlUnitReset==null) {
			synchronized(ControlUnitReset.class) {
				if(ControlUnitReset==null) {
					ControlUnitReset= new ControlUnitReset();
				}
			}
		}
		return ControlUnitReset;
	}
	
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	public void execCycle() {
		CU = ControlUnit.getInstance();
		//Resetto
		SF.reset();
		changeState(CU, ControlUnitFetch.getInstance());
	}

}
