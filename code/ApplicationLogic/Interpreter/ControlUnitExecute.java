package ApplicationLogic.Interpreter;

import ApplicationLogic.State.*;
import Control.Controller;

public class ControlUnitExecute extends ControlUnitState {

	OperativeUnit OU;

	private volatile static ControlUnitExecute ControlUnitExecute = null;
	
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	protected ControlUnitExecute() {
	}
	
	//Punto di ingresso globale all'istanza
	public static ControlUnitExecute getInstance(){
		if(ControlUnitExecute==null) {
			synchronized(ControlUnitExecute.class) {
				if(ControlUnitExecute==null) {
					ControlUnitExecute= new ControlUnitExecute();
				}
			}
		}
		return ControlUnitExecute;
	}

	public void execCycle() {
		
		Byte Opcode = ControlUnit.getInstance().getInstructionRegister();
		String MACRO_Opcode = getOpcode(Opcode); //Estrae il codice operativo

		OU.getIstance().setCurrentInstruction(OU.getIstance().getMicrorom(Opcode));  //Imposta l'istruzione appena prelevata nella UO
		OU.getIstance().Execute(MACRO_Opcode);
		
		if(ControlUnit.getInstance().getInstructionRegister() == 0xD)
			ControlUnit.getInstance().setInstructionRegister((byte)0xF);
	}

	private String getOpcode(Byte Opcode) {
		String Operation = "";	
		Operation = OU.getIstance().getMicrorom(Opcode).opcode;

		return Operation;
	}
	



}