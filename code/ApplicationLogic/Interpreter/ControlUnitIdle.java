package ApplicationLogic.Interpreter;

public class ControlUnitIdle extends ControlUnitState {
	
	private static ControlUnitIdle Instance;
	
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	protected ControlUnitIdle() {
	}
	
	public static ControlUnitIdle getInstance() {
		if(Instance == null) {
			Instance = new ControlUnitIdle();
			return Instance;
		}
		else
			return Instance;
	}
	
	public void execCycle() {
		changeState(ControlUnit.getInstance(), ControlUnitFetch.getInstance());
	}

}