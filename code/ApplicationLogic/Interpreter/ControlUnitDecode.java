package ApplicationLogic.Interpreter;

import ApplicationLogic.State.*;

public class ControlUnitDecode extends ControlUnitState {

	StateFacade SF;
	ControlUnit CU;

	private volatile static ControlUnitDecode ControlUnitDecode = null;			//Singleton
	
	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	protected ControlUnitDecode() {
		//Collegamento con il facade dello STATE
		SF = new StateFacade();
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
		Byte opcode = CU.getInstructionRegister();									//prendo il codice operativo per capire la modalità di indirizzamento 
		
		CU.setCurrentInstruction(SF.getMicrorom(Byte.toUnsignedInt(opcode)));  		//Prelevo l'istruzione dalla microRom e la salvo in una variabile della ControlUnit
		CU.setCycles(CU.getCurrentInstruction().cycles);		//Assegno il numero di cicli dell'istruzione 
		
		/*DEBUG*/
		System.out.println("ISTRUZIONE:");
		System.out.println(CU.getCurrentInstruction().cycles);
		
		CU.setBool_addr(SF.addressingMode(CU.getCurrentInstruction().addressing_mode));		//Prelevo la macro dalla microROM in corrispondenza dell'opcode e comando l'unità operativa di applicare una modalità di indirizzamento
		
		/*DEBUG*/
		System.out.println(CU.getCurrentInstruction().addressing_mode);
		//if(opcode == 0xD) //Debug
		
		changeState(CU, ControlUnitExecute.getInstance());
	}

}