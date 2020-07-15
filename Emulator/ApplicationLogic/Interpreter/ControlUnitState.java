package Emulator.ApplicationLogic.Interpreter;

public abstract class ControlUnitState {

	private volatile static ControlUnitFetch CUF = null;		//Singleton
	private volatile static ControlUnitDecode CUD = null;			//Singleton
	private volatile static ControlUnitExecute CUE = null;	//Singleton
	private volatile static ControlUnitReset CUR = null;		//Singleton
	private volatile static ControlUnitInterrupt CUI = null;		//Singleton
	
	public static ControlUnitState getInstance(String SelectedState) {
		if(SelectedState.equals("Fetch"))
		{
			if(CUF==null) {
				synchronized(ControlUnitFetch.class) {
					if(CUF==null) {
						CUF = new ControlUnitFetch();
					}
				}
			}
			return CUF;
		}
		else if(SelectedState.equals("Decode")) {
			if(CUD==null) {
				synchronized(ControlUnitDecode.class) {
					if(CUD==null) {
						CUD= new ControlUnitDecode();
					}
				}
			}
			return CUD;
		}
		else if(SelectedState.equals("Execute")) {
			if(CUE==null) {
				synchronized(ControlUnitExecute.class) {
					if(CUE==null) {
						CUE= new ControlUnitExecute();
					}
				}
			}
			return CUE;
		}
		else if(SelectedState.equals("Interrupt")) {
			if(CUI==null) {
				synchronized(ControlUnitExecute.class) {
					if(CUI==null) {
						CUI= new ControlUnitInterrupt();
					}
				}
			}
			return CUI;
		}
		else {
			if(CUR==null) {
				synchronized(ControlUnitReset.class) {
					if(CUR==null) {
						CUR= new ControlUnitReset();
					}
				}
			}
			return CUR;
		}
	}
	
	protected abstract void changeState(ControlUnit CU, ControlUnitState NewState);

	public abstract void execCycle();

}