package ApplicationLogic.State;

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
	
}

