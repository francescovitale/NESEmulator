package ApplicationLogic.Interpreter;

import ApplicationLogic.State.*;

public class ControlUnitDecode extends ControlUnitState {

	OperativeUnit OU;
	ControlUnit CU;

	private volatile static ControlUnitDecode ControlUnitDecode = null;			//Singleton
	
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	protected ControlUnitDecode() {
		//Collegamento con L'unit� operativa
		OU = OperativeUnit.getIstance();
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
		CU = ControlUnit.getInstance();
		Byte opcode = CU.getInstructionRegister();									//prendo il codice operativo per capire la modalit� di indirizzamento 
		
		CU.setCurrentInstruction(OU.getMicrorom(Byte.toUnsignedInt(opcode)));  		//Prelevo l'istruzione dalla microRom e la salvo in una variabile della ControlUnit
		CU.setCycles(CU.getCurrentInstruction().cycles);		//Assegno il numero di cicli dell'istruzione 
		
		/*DEBUG*/
		System.out.println("ISTRUZIONE:");
		System.out.println(CU.getCurrentInstruction().cycles);
		
		CU.setBool_addr(OU.addressingMode(CU.getCurrentInstruction().addressing_mode));		//Prelevo la macro dalla microROM in corrispondenza dell'opcode e comando l'unit� operativa di applicare una modalit� di indirizzamento
		
		/*DEBUG*/
		System.out.println(CU.getCurrentInstruction().addressing_mode);
		//if(opcode == 0xD) //Debug
		
		changeState(CU, ControlUnitExecute.getInstance());
	}

}