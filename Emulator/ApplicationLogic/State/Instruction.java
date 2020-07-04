package Emulator.ApplicationLogic.State;

public class Instruction {
	public String opcode;
	public String addressing_mode;
	public Integer cycles;
	
	public Instruction() {
		opcode = "???";
		addressing_mode = "???";
		cycles = 0;
	}
	
	public Instruction(String op , String ad,Integer c) {
		opcode = op;
		addressing_mode = ad;
		cycles = c;
	}

	@Override
	public String toString() {
		return "Instruction [opcode=" + opcode + ", addressing_mode=" + addressing_mode + ", cycles=" + cycles + "]";
	}

	// copy constructor 
	public Instruction(Instruction i) { 
        //System.out.println("Copy constructor called"); 
        opcode = i.opcode; 
        addressing_mode = i.addressing_mode;
        cycles = i.cycles;
    }
}

