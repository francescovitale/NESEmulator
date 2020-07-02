package ApplicationLogic.Interpreter;

import ApplicationLogic.State.Instruction;
import ApplicationLogic.State.OperativeUnit;

public class ControlUnit {

	private Byte InstructionRegister;									//Istruction Register contenente l'attuale istruzione letta in memoria
	private Integer cycles;												//Numero di cicli dell'attuale ciclo
	private Instruction CurrentInstruction = new Instruction();  		//Operazione in esecuzione in un dato ciclo del processore
 	private ControlUnitState State;										//Stato corrente 
 	private Boolean bool_opcode;										//verifica sul ciclo aggiuntivo
 	private Boolean bool_addr;											//verifica sul ciclo aggiuntivo
 	private volatile static ControlUnit ControlUnit = null;				//Singleton
 		
	
	protected ControlUnit() {
		InstructionRegister = 0x0;
		cycles = 0;
		setBool_opcode(false);
		setBool_addr(false);
		State = ControlUnitReset.getInstance();
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
	
 	void changeState(ControlUnitState NextState) {
		State = NextState;
	};
	
	public void execCycle() {
		while(InstructionRegister != 0xF)
		{
			State.execCycle();
		}
	}

	
	/*public static void main(String[] args) {
		ControlUnit UC = ControlUnit.getInstance();
		UC.execCycle();
		System.out.println(UC.State);
		System.out.println(UC.InstructionRegister);
	}*/
	
	//SETTER AND GETTER
	public Byte getInstructionRegister() {
		return InstructionRegister;
	}

	public void setInstructionRegister(byte b) {
		InstructionRegister = b;		
	}

	public Instruction getCurrentInstruction() {
		return CurrentInstruction;
	}

	public void setCurrentInstruction(Instruction currentInstruction) {
		CurrentInstruction = currentInstruction;
	}
	
	public Boolean getBool_opcode() {
		return bool_opcode;
	}

	public void setBool_opcode(Boolean i) {
		bool_opcode = i;
	}

	public Boolean getBool_addr() {
		return bool_addr;
	}

	public void setBool_addr(Boolean boolean1) {
		bool_addr = boolean1;
	}
	
	
	//Gestione cicli di un istruzione
	public Integer getCycles() {
		return cycles;
	}

	public void setCycles(Integer b) {
		cycles = b;		
	}
	
	public void increaseCycles() {
		cycles ++;
	}
	
	public void decreaseCycles() {
		cycles --;
	}

}