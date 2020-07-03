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
	
	public Instruction(Instruction I2)
	{
		opcode = I2.opcode;
		addressing_mode = I2.addressing_mode;
		cycles = I2.cycles;
		
	}

	@Override
	public String toString() {
		return "Instruction [opcode=" + opcode + ", addressing_mode=" + addressing_mode + ", cycles=" + cycles + "]";
	}
	
}

