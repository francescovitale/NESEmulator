package ApplicationLogic.Interpreter;

import ApplicationLogic.State.Instruction;
import ApplicationLogic.State.OperativeUnit;

public class ControlUnit {

	private Byte InstructionRegister;									//Istruction Register contenente l'attuale istruzione letta in memoria
	private Integer cycles;												//Numero di cicli dell'attuale ciclo
	private static Instruction CurrentInstruction = new Instruction();  //Operazione in esecuzione in un dato ciclo del processore
 	private ControlUnitState State;										//Stato corrente 
 	private static Boolean bool_opcode;									//verifica sul ciclo aggiuntivo
 	private static Boolean bool_addr;									//verifica sul ciclo aggiuntivo
	void changeState(ControlUnitState NextState) {
		State = NextState;
	};
	
	
	private volatile static ControlUnit ControlUnit = null;			//Singleton
	
	protected ControlUnit() {
		InstructionRegister = 0x0;
		cycles = 0;
		setBool_opcode(false);
		setBool_addr(false);
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

	public static Instruction getCurrentInstruction() {
		return CurrentInstruction;
	}

	public static void setCurrentInstruction(Instruction currentInstruction) {
		CurrentInstruction = currentInstruction;
	}
	
	public static Boolean getBool_opcode() {
		return bool_opcode;
	}

	public static void setBool_opcode(Boolean i) {
		bool_opcode = i;
	}

	public static Boolean getBool_addr() {
		return bool_addr;
	}

	public static void setBool_addr(Boolean boolean1) {
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