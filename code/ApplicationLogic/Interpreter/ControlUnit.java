package ApplicationLogic.Interpreter;

import ApplicationLogic.State.OperativeUnit;

public class ControlUnit {

	Byte InstructionRegister;
	private ControlUnitState State;
	void changeState(ControlUnitState NextState) {
		State = NextState;
	};
	
	
	private volatile static ControlUnit ControlUnit = null;			//Singleton
	
	protected ControlUnit() {
		InstructionRegister = 0x0;
		State = ControlUnitFetch.getInstance();
	};
	
	//Punto di ingresso globale all'istanza
	public static ControlUnit getInstance(){
		if(ControlUnit==null) {
			synchronized(ControlUnit.class) {
				if(ControlUnit==null) {
					ControlUnit= new ControlUnit();
				}
			}
		}
		return ControlUnit;
	}
	
	public void execCycle() {
		while(InstructionRegister != 0xF)
		{
			State.execCycle();
		}
	}

	
	public static void main(String[] args) {
		ControlUnit UC = ControlUnit.getInstance();
		UC.execCycle();
		System.out.println(UC.State);
		System.out.println(UC.InstructionRegister);
	}

}