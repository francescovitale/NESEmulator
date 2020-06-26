package ApplicationLogic.State;

public class OperativeUnit {

	private volatile static OperativeUnit UO = null;//Singleton
	private static final Instruction[] MicroRom = new Instruction[256];
	
	//REGISTRI INTERNI AL PROCESSORE
	private static Byte A_register;
	private static Byte X_register;
	private static Byte Y_register;
	
	private static char PC_register;
	private static Byte Status_register;
	
	//ATTRIBUTI INTERNI DI COMODO
	private static Instruction CurrentInstruction = new Instruction(); //Operazione in esecuzione in un dato ciclo del processore
	private static Byte fetched ;
	
	//Costruttore privato
	private OperativeUnit() {
		
		//INIZIALIZZAZIONE REGISTRI
		
		A_register = 0x00;
		X_register = 0x00;
		Y_register = 0x00;
		PC_register = 0x0000;
		Status_register = 0x00;	
		
		//INIZIALIZZAZIONE MICROROM
		MicroRom[0] = new Instruction( "BRK", "IMM", 7 );
		MicroRom[1] = new Instruction( "ORA", "IZX", 6 );
		MicroRom[2] = new Instruction( "???", "IMP", 2 );
		MicroRom[3] = new Instruction( "???", "IMP", 8 );
		MicroRom[4] = new Instruction( "???", "IMP", 3 );
		MicroRom[5] = new Instruction( "ORA", "ZP0", 3 );
		MicroRom[6] = new Instruction( "ASL", "ZP0", 5 );
		MicroRom[7] = new Instruction( "???", "IMP", 5 );
		MicroRom[8] = new Instruction( "PHP", "IMP", 3 );
		MicroRom[9] = new Instruction( "ORA", "IMM", 2 );
		MicroRom[10] = new Instruction( "ASL", "IMP", 2 );
		MicroRom[11] = new Instruction( "???", "IMP", 2 );
		MicroRom[12] = new Instruction( "???", "IMP", 4 );
		MicroRom[13] = new Instruction( "ORA", "ABS", 4 );
		MicroRom[14] = new Instruction( "ASL", "ABS", 6 );
		MicroRom[15] = new Instruction( "???", "IMP", 6 );
		MicroRom[16] = new Instruction( "BPL", "REL", 2 );
		MicroRom[17] = new Instruction( "ORA", "IZY", 5 );
		MicroRom[18] = new Instruction( "???", "IMP", 2 );
		MicroRom[19] = new Instruction( "???", "IMP", 8 );
		MicroRom[20] = new Instruction( "???", "IMP", 4 );
		MicroRom[21] = new Instruction( "ORA", "ZPX", 4 );
		MicroRom[22] = new Instruction( "ASL", "ZPX", 6 );
		MicroRom[23] = new Instruction( "???", "IMP", 6 );
		MicroRom[24] = new Instruction( "CLC", "IMP", 2 );
		MicroRom[25] = new Instruction( "ORA", "ABY", 4 );
		MicroRom[26] = new Instruction( "???", "IMP", 2 );
		MicroRom[27] = new Instruction( "???", "IMP", 7 );
		MicroRom[28] = new Instruction( "???", "IMP", 4 );
		MicroRom[29] = new Instruction( "ORA", "ABX", 4 );
		MicroRom[30] = new Instruction( "ASL", "ABX", 7 );
		MicroRom[31] = new Instruction( "???", "IMP", 7 );
		MicroRom[32] = new Instruction( "JSR", "ABS", 6 );
		MicroRom[33] = new Instruction( "AND", "IZX", 6 );
		MicroRom[34] = new Instruction( "???", "IMP", 2 );
		MicroRom[35] = new Instruction( "???", "IMP", 8 );
		MicroRom[36] = new Instruction( "BIT", "ZP0", 3 );
		MicroRom[37] = new Instruction( "AND", "ZP0", 3 );
		MicroRom[38] = new Instruction( "ROL", "ZP0", 5 );
		MicroRom[39] = new Instruction( "???", "IMP", 5 );
		MicroRom[40] = new Instruction( "PLP", "IMP", 4 );
		MicroRom[41] = new Instruction( "AND", "IMM", 2 );
		MicroRom[42] = new Instruction( "ROL", "IMP", 2 );
		MicroRom[43] = new Instruction( "???", "IMP", 2 );
		MicroRom[44] = new Instruction( "BIT", "ABS", 4 );
		MicroRom[45] = new Instruction( "AND", "ABS", 4 );
		MicroRom[46] = new Instruction( "ROL", "ABS", 6 );
		MicroRom[47] = new Instruction( "???", "IMP", 6 );
		MicroRom[48] = new Instruction( "BMI", "REL", 2 );
		MicroRom[49] = new Instruction( "AND", "IZY", 5 );
		MicroRom[50] = new Instruction( "???", "IMP", 2 );
		MicroRom[51] = new Instruction( "???", "IMP", 8 );
		MicroRom[52] = new Instruction( "???", "IMP", 4 );
		MicroRom[53] = new Instruction( "AND", "ZPX", 4 );
		MicroRom[54] = new Instruction( "ROL", "ZPX", 6 );
		MicroRom[55] = new Instruction( "???", "IMP", 6 );
		MicroRom[56] = new Instruction( "SEC", "IMP", 2 );
		MicroRom[57] = new Instruction( "AND", "ABY", 4 );
		MicroRom[58] = new Instruction( "???", "IMP", 2 );
		MicroRom[59] = new Instruction( "???", "IMP", 7 );
		MicroRom[60] = new Instruction( "???", "IMP", 4 );
		MicroRom[61] = new Instruction( "AND", "ABX", 4 );
		MicroRom[62] = new Instruction( "ROL", "ABX", 7 );
		MicroRom[63] = new Instruction( "???", "IMP", 7 );
		MicroRom[64] = new Instruction( "RTI", "IMP", 6 );
		MicroRom[65] = new Instruction( "EOR", "IZX", 6 );
		MicroRom[66] = new Instruction( "???", "IMP", 2 );
		MicroRom[67] = new Instruction( "???", "IMP", 8 );
		MicroRom[68] = new Instruction( "???", "IMP", 3 );
		MicroRom[69] = new Instruction( "EOR", "ZP0", 3 );
		MicroRom[70] = new Instruction( "LSR", "ZP0", 5 );
		MicroRom[71] = new Instruction( "???", "IMP", 5 );
		MicroRom[72] = new Instruction( "PHA", "IMP", 3 );
		MicroRom[73] = new Instruction( "EOR", "IMM", 2 );
		MicroRom[74] = new Instruction( "LSR", "IMP", 2 );
		MicroRom[75] = new Instruction( "???", "IMP", 2 );
		MicroRom[76] = new Instruction( "JMP", "ABS", 3 );
		MicroRom[77] = new Instruction( "EOR", "ABS", 4 );
		MicroRom[78] = new Instruction( "LSR", "ABS", 6 );
		MicroRom[79] = new Instruction( "???", "IMP", 6 );
		MicroRom[80] = new Instruction( "BVC", "REL", 2 );
		MicroRom[81] = new Instruction( "EOR", "IZY", 5 );
		MicroRom[82] = new Instruction( "???", "IMP", 2 );
		MicroRom[83] = new Instruction( "???", "IMP", 8 );
		MicroRom[84] = new Instruction( "???", "IMP", 4 );
		MicroRom[85] = new Instruction( "EOR", "ZPX", 4 );
		MicroRom[86] = new Instruction( "LSR", "ZPX", 6 );
		MicroRom[87] = new Instruction( "???", "IMP", 6 );
		MicroRom[88] = new Instruction( "CLI", "IMP", 2 );
		MicroRom[89] = new Instruction( "EOR", "ABY", 4 );
		MicroRom[90] = new Instruction( "???", "IMP", 2 );
		MicroRom[91] = new Instruction( "???", "IMP", 7 );
		MicroRom[92] = new Instruction( "???", "IMP", 4 );
		MicroRom[93] = new Instruction( "EOR", "ABX", 4 );
		MicroRom[94] = new Instruction( "LSR", "ABX", 7 );
		MicroRom[95] = new Instruction( "???", "IMP", 7 );
		MicroRom[96] = new Instruction( "RTS", "IMP", 6 );
		MicroRom[97] = new Instruction( "ADC", "IZX", 6 );
		MicroRom[98] = new Instruction( "???", "IMP", 2 );
		MicroRom[99] = new Instruction( "???", "IMP", 8 );
		MicroRom[100] = new Instruction( "???", "IMP", 3 );
		MicroRom[101] = new Instruction( "ADC", "ZP0", 3 );
		MicroRom[102] = new Instruction( "ROR", "ZP0", 5 );
		MicroRom[103] = new Instruction( "???", "IMP", 5 );
		MicroRom[104] = new Instruction( "PLA", "IMP", 4 );
		MicroRom[105] = new Instruction( "ADC", "IMM", 2 );
		MicroRom[106] = new Instruction( "ROR", "IMP", 2 );
		MicroRom[107] = new Instruction( "???", "IMP", 2 );
		MicroRom[108] = new Instruction( "JMP", "IND", 5 );
		MicroRom[109] = new Instruction( "ADC", "ABS", 4 );
		MicroRom[110] = new Instruction( "ROR", "ABS", 6 );
		MicroRom[111] = new Instruction( "???", "IMP", 6 );
		MicroRom[112] = new Instruction( "BVS", "REL", 2 );
		MicroRom[113] = new Instruction( "ADC", "IZY", 5 );
		MicroRom[114] = new Instruction( "???", "IMP", 2 );
		MicroRom[115] = new Instruction( "???", "IMP", 8 );
		MicroRom[116] = new Instruction( "???", "IMP", 4 );
		MicroRom[117] = new Instruction( "ADC", "ZPX", 4 );
		MicroRom[118] = new Instruction( "ROR", "ZPX", 6 );
		MicroRom[119] = new Instruction( "???", "IMP", 6 );
		MicroRom[120] = new Instruction( "SEI", "IMP", 2 );
		MicroRom[121] = new Instruction( "ADC", "ABY", 4 );
		MicroRom[122] = new Instruction( "???", "IMP", 2 );
		MicroRom[123] = new Instruction( "???", "IMP", 7 );
		MicroRom[124] = new Instruction( "???", "IMP", 4 );
		MicroRom[125] = new Instruction( "ADC", "ABX", 4 );
		MicroRom[126] = new Instruction( "ROR", "ABX", 7 );
		MicroRom[127] = new Instruction( "???", "IMP", 7 );
		MicroRom[128] = new Instruction( "???", "IMP", 2 );
		MicroRom[129] = new Instruction( "STA", "IZX", 6 );
		MicroRom[130] = new Instruction( "???", "IMP", 2 );
		MicroRom[131] = new Instruction( "???", "IMP", 6 );
		MicroRom[132] = new Instruction( "STY", "ZP0", 3 );
		MicroRom[133] = new Instruction( "STA", "ZP0", 3 );
		MicroRom[134] = new Instruction( "STX", "ZP0", 3 );
		MicroRom[135] = new Instruction( "???", "IMP", 3 );
		MicroRom[136] = new Instruction( "DEY", "IMP", 2 );
		MicroRom[137] = new Instruction( "???", "IMP", 2 );
		MicroRom[138] = new Instruction( "TXA", "IMP", 2 );
		MicroRom[139] = new Instruction( "???", "IMP", 2 );
		MicroRom[140] = new Instruction( "STY", "ABS", 4 );
		MicroRom[141] = new Instruction( "STA", "ABS", 4 );
		MicroRom[142] = new Instruction( "STX", "ABS", 4 );
		MicroRom[143] = new Instruction( "???", "IMP", 4 );
		MicroRom[144] = new Instruction( "BCC", "REL", 2 );
		MicroRom[145] = new Instruction( "STA", "IZY", 6 );
		MicroRom[146] = new Instruction( "???", "IMP", 2 );
		MicroRom[147] = new Instruction( "???", "IMP", 6 );
		MicroRom[148] = new Instruction( "STY", "ZPX", 4 );
		MicroRom[149] = new Instruction( "STA", "ZPX", 4 );
		MicroRom[150] = new Instruction( "STX", "ZPY", 4 );
		MicroRom[151] = new Instruction( "???", "IMP", 4 );
		MicroRom[152] = new Instruction( "TYA", "IMP", 2 );
		MicroRom[153] = new Instruction( "STA", "ABY", 5 );
		MicroRom[154] = new Instruction( "TXS", "IMP", 2 );
		MicroRom[155] = new Instruction( "???", "IMP", 5 );
		MicroRom[156] = new Instruction( "???", "IMP", 5 );
		MicroRom[157] = new Instruction( "STA", "ABX", 5 );
		MicroRom[158] = new Instruction( "???", "IMP", 5 );
		MicroRom[159] = new Instruction( "???", "IMP", 5 );
		MicroRom[160] = new Instruction( "LDY", "IMM", 2 );
		MicroRom[161] = new Instruction( "LDA", "IZX", 6 );
		MicroRom[162] = new Instruction( "LDX", "IMM", 2 );
		MicroRom[163] = new Instruction( "???", "IMP", 6 );
		MicroRom[164] = new Instruction( "LDY", "ZP0", 3 );
		MicroRom[165] = new Instruction( "LDA", "ZP0", 3 );
		MicroRom[166] = new Instruction( "LDX", "ZP0", 3 );
		MicroRom[167] = new Instruction( "???", "IMP", 3 );
		MicroRom[168] = new Instruction( "TAY", "IMP", 2 );
		MicroRom[169] = new Instruction( "LDA", "IMM", 2 );
		MicroRom[170] = new Instruction( "TAX", "IMP", 2 );
		MicroRom[171] = new Instruction( "???", "IMP", 2 );
		MicroRom[172] = new Instruction( "LDY", "ABS", 4 );
		MicroRom[173] = new Instruction( "LDA", "ABS", 4 );
		MicroRom[174] = new Instruction( "LDX", "ABS", 4 );
		MicroRom[175] = new Instruction( "???", "IMP", 4 );
		MicroRom[176] = new Instruction( "BCS", "REL", 2 );
		MicroRom[177] = new Instruction( "LDA", "IZY", 5 );
		MicroRom[178] = new Instruction( "???", "IMP", 2 );
		MicroRom[179] = new Instruction( "???", "IMP", 5 );
		MicroRom[180] = new Instruction( "LDY", "ZPX", 4 );
		MicroRom[181] = new Instruction( "LDA", "ZPX", 4 );
		MicroRom[182] = new Instruction( "LDX", "ZPY", 4 );
		MicroRom[183] = new Instruction( "???", "IMP", 4 );
		MicroRom[184] = new Instruction( "CLV", "IMP", 2 );
		MicroRom[185] = new Instruction( "LDA", "ABY", 4 );
		MicroRom[186] = new Instruction( "TSX", "IMP", 2 );
		MicroRom[187] = new Instruction( "???", "IMP", 4 );
		MicroRom[188] = new Instruction( "LDY", "ABX", 4 );
		MicroRom[189] = new Instruction( "LDA", "ABX", 4 );
		MicroRom[190] = new Instruction( "LDX", "ABY", 4 );
		MicroRom[191] = new Instruction( "???", "IMP", 4 );
		MicroRom[192] = new Instruction( "CPY", "IMM", 2 );
		MicroRom[193] = new Instruction( "CMP", "IZX", 6 );
		MicroRom[194] = new Instruction( "???", "IMP", 2 );
		MicroRom[195] = new Instruction( "???", "IMP", 8 );
		MicroRom[196] = new Instruction( "CPY", "ZP0", 3 );
		MicroRom[197] = new Instruction( "CMP", "ZP0", 3 );
		MicroRom[198] = new Instruction( "DEC", "ZP0", 5 );
		MicroRom[199] = new Instruction( "???", "IMP", 5 );
		MicroRom[200] = new Instruction( "INY", "IMP", 2 );
		MicroRom[201] = new Instruction( "CMP", "IMM", 2 );
		MicroRom[202] = new Instruction( "DEX", "IMP", 2 );
		MicroRom[203] = new Instruction( "???", "IMP", 2 );
		MicroRom[204] = new Instruction( "CPY", "ABS", 4 );
		MicroRom[205] = new Instruction( "CMP", "ABS", 4 );
		MicroRom[206] = new Instruction( "DEC", "ABS", 6 );
		MicroRom[207] = new Instruction( "???", "IMP", 6 );
		MicroRom[208] = new Instruction( "BNE", "REL", 2 );
		MicroRom[209] = new Instruction( "CMP", "IZY", 5 );
		MicroRom[210] = new Instruction( "???", "IMP", 2 );
		MicroRom[211] = new Instruction( "???", "IMP", 8 );
		MicroRom[212] = new Instruction( "???", "IMP", 4 );
		MicroRom[213] = new Instruction( "CMP", "ZPX", 4 );
		MicroRom[214] = new Instruction( "DEC", "ZPX", 6 );
		MicroRom[215] = new Instruction( "???", "IMP", 6 );
		MicroRom[216] = new Instruction( "CLD", "IMP", 2 );
		MicroRom[217] = new Instruction( "CMP", "ABY", 4 );
		MicroRom[218] = new Instruction( "NOP", "IMP", 2 );
		MicroRom[219] = new Instruction( "???", "IMP", 7 );
		MicroRom[220] = new Instruction( "???", "IMP", 4 );
		MicroRom[221] = new Instruction( "CMP", "ABX", 4 );
		MicroRom[222] = new Instruction( "DEC", "ABX", 7 );
		MicroRom[223] = new Instruction( "???", "IMP", 7 );
		MicroRom[224] = new Instruction( "CPX", "IMM", 2 );
		MicroRom[225] = new Instruction( "SBC", "IZX", 6 );
		MicroRom[226] = new Instruction( "???", "IMP", 2 );
		MicroRom[227] = new Instruction( "???", "IMP", 8 );
		MicroRom[228] = new Instruction( "CPX", "ZP0", 3 );
		MicroRom[229] = new Instruction( "SBC", "ZP0", 3 );
		MicroRom[230] = new Instruction( "INC", "ZP0", 5 );
		MicroRom[231] = new Instruction( "???", "IMP", 5 );
		MicroRom[232] = new Instruction( "INX", "IMP", 2 );
		MicroRom[233] = new Instruction( "SBC", "IMM", 2 );
		MicroRom[234] = new Instruction( "NOP", "IMP", 2 );
		MicroRom[235] = new Instruction( "???", "IMP", 2 );
		MicroRom[236] = new Instruction( "CPX", "ABS", 4 );
		MicroRom[237] = new Instruction( "SBC", "ABS", 4 );
		MicroRom[238] = new Instruction( "INC", "ABS", 6 );
		MicroRom[239] = new Instruction( "???", "IMP", 6 );
		MicroRom[240] = new Instruction( "BEQ", "REL", 2 );
		MicroRom[241] = new Instruction( "SBC", "IZY", 5 );
		MicroRom[242] = new Instruction( "???", "IMP", 2 );
		MicroRom[243] = new Instruction( "???", "IMP", 8 );
		MicroRom[244] = new Instruction( "???", "IMP", 4 );
		MicroRom[245] = new Instruction( "SBC", "ZPX", 4 );
		MicroRom[246] = new Instruction( "INC", "ZPX", 6 );
		MicroRom[247] = new Instruction( "???", "IMP", 6 );
		MicroRom[248] = new Instruction( "SED", "IMP", 2 );
		MicroRom[249] = new Instruction( "SBC", "ABY", 4 );
		MicroRom[250] = new Instruction( "NOP", "IMP", 2 );
		MicroRom[251] = new Instruction( "???", "IMP", 7 );
		MicroRom[252] = new Instruction( "???", "IMP", 4 );
		MicroRom[253] = new Instruction( "SBC", "ABX", 4 );
		MicroRom[254] = new Instruction( "INC", "ABX", 7 );
		MicroRom[255] = new Instruction( "???", "IMP", 7 );

	}

	//Punto di ingresso globale all'istanza
	public static OperativeUnit getIstance() {
		if(UO==null) {
			synchronized(OperativeUnit.class) {
				if(UO==null) {
					UO = new OperativeUnit();
				}
			}
		}
		return UO;
	}
	
	public Byte fetch() {
		// TODO - implement OperativeUnit.fetch
		throw new UnsupportedOperationException();
	}

	public Boolean addressingMode() {
		// TODO - implement OperativeUnit.addressingMode
		throw new UnsupportedOperationException();
	}

	public Boolean Execute(String Opcode) {
		
		switch(Opcode) {
		case "BRK":
			break;
			
		default: 
			System.out.println("Code ??? not recognised");
			break;
		}
		
		return true;
	}

	//OPCODES
	
	//Add
	private void ADC() {}
	//Sub
	private void SBC() {}
	//Logic AND
	private void AND() {
		
		//Byte fetched = fetch();

		A_register = (byte)(A_register & fetched);

		setFlag("Z",A_register == 0x00); //Se la AND ha dato risultato 0x00, abilita il registro 0
		setFlag("N",0x00 != (A_register & 0x80));  //Se il bit più significativo del registro A è alto, setta il flag Negative.
		
		//return 1?	
	}
	//Arithmetic Shift Left
	private void ASL() {
		
		//Byte fetched = fetch();
		
		char temp = (char)(fetched << 1); //Shifta a sinistra il valore fetchato
		setFlag("C", (temp & 0xFF00) > 0); //se tale shift rende un valore maggiore di 256, allora alza il carry flag
		setFlag("Z", (temp & 0x00FF) == 0x00); //se lo shift porta i primi 8 bit ad essere nulli, alza il flag zero
		setFlag("N", 0x00 != (temp & 0x80)); //se lo shift porta ad avere il bit alto alla posizione 8, abilita il flag Negative
		
		if (CurrentInstruction.addressing_mode == "IMP") //se l'address mode è implied, scrivi in A, altrimenti in memoria
			A_register = (byte)(temp & 0x00FF);
		/*else
			Bus.getIstance().writeRam(Address, (byte)(temp & 0x00FF));
		return 0;*/
		
	}
	//Branch if Carry Clear
	private void BCC() {}
	//Branch if Carry Set
	private void BCS() {}
	//Branch if Equal
	private void BEQ() {}
	//
	private void BIT() {}
	//Branch if Negative
	private void BMI() {}
	//Branch if Not Equal
	private void BNE() {}
	//Branch if Positive
	private void BPL() {}
	//Break
	private void BRK() {}
	//Branch if Overflow Clear
	private void BVC() {}
	//Branch if Overflow Set
	private void BVS() {}
	//Clear
	private void CLC() {}
	//Clear Decimal Flag
	private void CLD() {}
	//Disable Interrupts / Clear Interrupt Flag
	private void CLI() {}
	//Clear Overflow Flag
	private void CLV() {}
	//Compare Accumulator
	private void CMP() {}
	//Compare X Register
	private void CPX() {}
	//Compare Y Register
	private void CLY() {}
	//Decrement Value at Memory Location
	private void DEC() {}
	//Decrement X Register
	private void DEX() {}
	//Decrement Y Register
	private void DEY() {}
	//Bitwise Logic XOR
	private void EOR() {}
	//Increment Value at Memory Location
	private void INC() {}
	//Increment X Register
	private void INX() {}
	//Increment Y Register
	private void INY() {}
	//Jump To Location
	private void JMP() {}
	//Jump To Sub-Routine
	private void JSR() {}
	//Load The Accumulator
	private void LDA() {}
	//Load The X Register
	private void LDX() {}
	//Load The Y Register
	private void LDY() {}
	
	private void LSR() {}
	//No Operation
	private void NOP() {}
	//Bitwise Logic OR
	private void ORA() {}
	//Push Accumulator to Stack
	private void PHA() {}
	//Push Status Register to Stack
	private void PHP() {}
	//Pop Accumulator off Stack
	private void PLA() {}
	//Pop Status Register off Stack
	private void PLP() {}
	
	private void ROL() {}
	
	private void ROR() {}
	//Return from interrupt
	private void RTI() {}
	//Return from subroutine
	private void RTS() {}
	//Set Carry Flag
	private void SEC() {}
	//Set Decimal Flag
	private void SED() {}
	//et Interrupt Flag / Enable Interrupts
	private void SEI() {}
	//Store Accumulator at Address
	private void STA() {}
	//Store X Register at Address
	private void STX() {}
	//Store Y Register at Address
	private void STY() {}
	//Transfer Accumulator to X Register
	private void TAX() {}
	//Transfer Accumulator to Y Register
	private void TAY() {}
	//Transfer Stack Pointer to X Register
	private void TSX() {}
	//Transfer X Register to Accumulator
	private void TXA() {}
	//Transfer X Register to Stack Pointer
	private void TXS() {}
	//Transfer Y Register to Accumulator
	private void TYA() {}
	//Illegal opcodes
	private void XXX() {}

	//SET & GET
	//SETFLAG E GETFLAG DELLO STATUS REGISTER
	
	public static void setFlag(String bit, boolean value) {
			switch(bit) {
			
			case "C": //FLAG CARRY
				if(value) Status_register = (byte)(Status_register | (1 << 0));
				else Status_register = (byte)(Status_register &~ (1 << 0));			
				break;
				
			case "Z": //FLAG ZERO
				if(value) Status_register = (byte)(Status_register | (1 << 1));
				else Status_register = (byte)(Status_register &~ (1 << 1));
				break;
				
			case "I": //FLAG DISABLE INTERRUPT
				if(value) Status_register = (byte)(Status_register | (1 << 2));
				else Status_register = (byte)(Status_register &~ (1 << 2));			
				break;
				
			case "D": //FLAG DECIMAL MODE
				if(value) Status_register = (byte)(Status_register | (1 << 3));
				else Status_register = (byte)(Status_register &~ (1 << 3));			
				break;
				
			case "B": //FLAG BREAK
				if(value) Status_register = (byte)(Status_register | (1 << 4));
				else Status_register = (byte)(Status_register &~ (1 << 4));			
				break;
				
			case "U": //FLAG UNUSED
				if(value) Status_register = (byte)(Status_register | (1 << 5));
				else Status_register = (byte)(Status_register &~ (1 << 5));			
				break;
					
			case "V": //FLAG OVERFLOW
				if(value) Status_register = (byte)(Status_register | (1 << 6));
				else Status_register = (byte)(Status_register &~ (1 << 6));
				break;
				
			case "N": //FLAG NEGATIVE
				if(value) Status_register = (byte)(Status_register | (1 << 7));
				else Status_register = (byte)(Status_register &~ (1 << 7));
				break;
			}
		}
		
	public static boolean getFlag(String bit) {
			
			boolean flagval = false;
			
			switch(bit) {
			
			case "C": //FLAG CARRY
				flagval = !(0x0 == (Status_register & 0x1));
				break;
				
			case "Z": //FLAG ZERO
				flagval = !(0x0 == (Status_register & (0<<1)));
				break;
				
			case "I": //FLAG DISABLE INTERRUPT
				flagval = !(0x0 == (Status_register & (0<<2)));		
				break;
				
			case "D": //FLAG DECIMAL MODE
				flagval = !(0x0 == (Status_register & (0<<3)));		
				break;
				
			case "B": //FLAG BREAK
				flagval = !(0x0 == (Status_register & (0<<4)));			
				break;
				
			case "U": //FLAG UNUSED
				flagval = !(0x0 == (Status_register & (0<<5)));		
				break;
					
			case "V": //FLAG OVERFLOW
				flagval = !(0x0 == (Status_register & (0<<6)));
				break;
				
			case "N": //FLAG NEGATIVE
				flagval = !(0x0 == (Status_register & (0<<7)));
				break;
			}
			
			return flagval;
		}
		
	//GETTER & SETTERS
	public static Instruction getMicrorom(int i) {
		return MicroRom[i];
	}
	
	public static Instruction getCurrentInstruction() {
		return CurrentInstruction;
	}

	public static void setCurrentInstruction(Instruction currentInstruction) {
		CurrentInstruction = currentInstruction;
	}
	
	
}