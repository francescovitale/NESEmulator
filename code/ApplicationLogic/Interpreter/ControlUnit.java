package ApplicationLogic.Interpreter;

public class ControlUnit {

	Byte InstructionRegister;
	private ControlUnitState State;
	void changeState(ControlUnitState NextState) {
		State = NextState;
	};
	
	private static ControlUnit Instance;
	
	protected ControlUnit() {
		InstructionRegister = 0x0;
		State = ControlUnitIdle.getInstance();
	};
	
	public void execCycle() {
		while(InstructionRegister != 0xF)
		{
			State.execCycle();
		}
	}
	
	public static ControlUnit getInstance(){
		if(Instance == null) {
			Instance = new ControlUnit();
			return Instance;
		}
		else
			return Instance;
	}

	
	public static void main(String[] args) {
		ControlUnit UC = ControlUnit.getInstance();
		UC.execCycle();
		System.out.println(UC.State);
		System.out.println(UC.InstructionRegister);
	}

}