package ApplicationLogic.Interpreter;

public abstract class ControlUnitState {

	protected abstract void changeState(ControlUnit CU, ControlUnitState NewState);

	public abstract void execCycle();

}