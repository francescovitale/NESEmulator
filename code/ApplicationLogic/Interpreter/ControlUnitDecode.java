package ApplicationLogic.Interpreter;

import ApplicationLogic.State.*;

public class ControlUnitDecode extends ControlUnitState {

	OperativeUnit OU;

	private volatile static ControlUnitDecode ControlUnitDecode = null;			//Singleton
	
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	protected ControlUnitDecode() {
	}
	
	//Punto di ingresso globale all'istanza
	public static ControlUnitDecode getInstance(){
		if(ControlUnitDecode==null) {
			synchronized(ControlUnitDecode.class) {
				if(ControlUnitDecode==null) {
					ControlUnitDecode= new ControlUnitDecode();
				}
			}
		}
		return ControlUnitDecode;
	}
	
	/**
	 * 
	 * @param OpCode
	 */
	public void execCycle() {
		Byte opcode = ControlUnit.getInstance().getInstructionRegister();									//prendo il codice operativo per capire la modalità di indirizzamento 
		
		ControlUnit.setCurrentInstruction(OU.getIstance().getMicrorom(Byte.toUnsignedInt(opcode)));  		//Prelevo l'istruzione dalla microRom e la salvo in una variabile della ControlUnit
		ControlUnit.getInstance().setCycles(ControlUnit.getCurrentInstruction().cycles);					//Assegno il numero di cicli dell'istruzione 
		
		/*DEBUG*/
		System.out.println("ISTRUZIONE:");
		System.out.println(ControlUnit.getCurrentInstruction().cycles);
		
		ControlUnit.setBool_addr(OU.getIstance().addressingMode(ControlUnit.getCurrentInstruction().addressing_mode));		//Prelevo la macro dalla microROM in corrispondenza dell'opcode e comando l'unità operativa di applicare una modalità di indirizzamento
		
		/*DEBUG*/
		System.out.println(ControlUnit.getCurrentInstruction().addressing_mode);
		//if(opcode == 0xD) //Debug
		
		changeState(ControlUnit.getInstance(), ControlUnitExecute.getInstance());
	}

}