package Emulator.ApplicationLogic.State;

import Emulator.ApplicationLogic.ByteManager;
import Emulator.ApplicationLogic.Interpreter.ControlUnit;
import Emulator.TechnicalServices.FileSystemAccess.FileSystemManager;

public class OperativeUnit {
	private volatile static OperativeUnit UO = null;				//Singleton
	private static final Instruction[] MicroRom = new Instruction[256];
	private Bus BusOU;
	
	//DEBUG
	FileSystemManager FSM = FileSystemManager.getInstance();
	
	
	//REGISTRI INTERNI AL PROCESSORE
	private Byte A_register;
	private Byte X_register;
	private Byte Y_register;
	private Byte Stack_pointer;
	
	private char PC_register;
	private Byte Status_register; 
	
	//RICHIESTE DI INTERRUPT
	private Boolean NMIRequest = false;
	private Boolean IRQRequest = false;
	
	//ATTRIBUTI INTERNI DI COMODO
	private Byte fetched ;									   //Dati che memorizzo	
	private char addr_abs;   								   //Locazione assoluta di memoria
	private char addr_rel;									   // Indirizzo relativo
	
	//Costruttore privato
	private OperativeUnit() {
		
		//DEBUG
		FSM.setPath("C:\\Users\\Daniele\\eclipse-workspace\\NES\\src\\Emulator\\Log\\log.txt");
		
		//Collegamento con il Bus
		BusOU = Bus.getInstance();
		
		//INIZIALIZZAZIONE REGISTRI
		
		A_register = 0x00;
		X_register = 0x00;
		Y_register = 0x00;
		PC_register = 0x0000;
		Status_register = 0x00;	
		Stack_pointer= (byte) 0xFD;	
		fetched = 0x00;
		addr_abs = 0x0000;
		addr_rel = 0x00;
		
		//INIZIALIZZAZIONE MICROROM
		MicroRom[0] = new Instruction( "BRK", "IMM", 7 );
		MicroRom[1] = new Instruction( "ORA", "IZX", 6 );
		MicroRom[2] = new Instruction( "???", "IMP", 2 );
		MicroRom[3] = new Instruction( "???", "IMP", 8 );
		MicroRom[4] = new Instruction( "NOP", "IMP", 3 );
		MicroRom[5] = new Instruction( "ORA", "ZP0", 3 );
		MicroRom[6] = new Instruction( "ASL", "ZP0", 5 );
		MicroRom[7] = new Instruction( "???", "IMP", 5 );
		MicroRom[8] = new Instruction( "PHP", "IMP", 3 );
		MicroRom[9] = new Instruction( "ORA", "IMM", 2 );
		MicroRom[10] = new Instruction( "ASL", "IMP", 2 );
		MicroRom[11] = new Instruction( "???", "IMP", 2 );
		MicroRom[12] = new Instruction( "NOP", "IMP", 4 );
		MicroRom[13] = new Instruction( "ORA", "ABS", 4 );
		MicroRom[14] = new Instruction( "ASL", "ABS", 6 );
		MicroRom[15] = new Instruction( "???", "IMP", 6 );
		MicroRom[16] = new Instruction( "BPL", "REL", 2 );
		MicroRom[17] = new Instruction( "ORA", "IZY", 5 );
		MicroRom[18] = new Instruction( "???", "IMP", 2 );
		MicroRom[19] = new Instruction( "???", "IMP", 8 );
		MicroRom[20] = new Instruction( "NOP", "IMP", 4 );
		MicroRom[21] = new Instruction( "ORA", "ZPX", 4 );
		MicroRom[22] = new Instruction( "ASL", "ZPX", 6 );
		MicroRom[23] = new Instruction( "???", "IMP", 6 );
		MicroRom[24] = new Instruction( "CLC", "IMP", 2 );
		MicroRom[25] = new Instruction( "ORA", "ABY", 4 );
		MicroRom[26] = new Instruction( "NOP", "IMP", 2 );
		MicroRom[27] = new Instruction( "???", "IMP", 7 );
		MicroRom[28] = new Instruction( "NOP", "IMP", 4 );
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
		MicroRom[52] = new Instruction( "NOP", "IMP", 4 );
		MicroRom[53] = new Instruction( "AND", "ZPX", 4 );
		MicroRom[54] = new Instruction( "ROL", "ZPX", 6 );
		MicroRom[55] = new Instruction( "???", "IMP", 6 );
		MicroRom[56] = new Instruction( "SEC", "IMP", 2 );
		MicroRom[57] = new Instruction( "AND", "ABY", 4 );
		MicroRom[58] = new Instruction( "NOP", "IMP", 2 );
		MicroRom[59] = new Instruction( "???", "IMP", 7 );
		MicroRom[60] = new Instruction( "NOP", "IMP", 4 );
		MicroRom[61] = new Instruction( "AND", "ABX", 4 );
		MicroRom[62] = new Instruction( "ROL", "ABX", 7 );
		MicroRom[63] = new Instruction( "???", "IMP", 7 );
		MicroRom[64] = new Instruction( "RTI", "IMP", 6 );
		MicroRom[65] = new Instruction( "EOR", "IZX", 6 );
		MicroRom[66] = new Instruction( "???", "IMP", 2 );
		MicroRom[67] = new Instruction( "???", "IMP", 8 );
		MicroRom[68] = new Instruction( "NOP", "IMP", 3 );
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
		MicroRom[84] = new Instruction( "NOP", "IMP", 4 );
		MicroRom[85] = new Instruction( "EOR", "ZPX", 4 );
		MicroRom[86] = new Instruction( "LSR", "ZPX", 6 );
		MicroRom[87] = new Instruction( "???", "IMP", 6 );
		MicroRom[88] = new Instruction( "CLI", "IMP", 2 );
		MicroRom[89] = new Instruction( "EOR", "ABY", 4 );
		MicroRom[90] = new Instruction( "NOP", "IMP", 2 );
		MicroRom[91] = new Instruction( "???", "IMP", 7 );
		MicroRom[92] = new Instruction( "NOP", "IMP", 4 );
		MicroRom[93] = new Instruction( "EOR", "ABX", 4 );
		MicroRom[94] = new Instruction( "LSR", "ABX", 7 );
		MicroRom[95] = new Instruction( "???", "IMP", 7 );
		MicroRom[96] = new Instruction( "RTS", "IMP", 6 );
		MicroRom[97] = new Instruction( "ADC", "IZX", 6 );
		MicroRom[98] = new Instruction( "???", "IMP", 2 );
		MicroRom[99] = new Instruction( "???", "IMP", 8 );
		MicroRom[100] = new Instruction( "NOP", "IMP", 3 );
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
		MicroRom[116] = new Instruction( "NOP", "IMP", 4 );
		MicroRom[117] = new Instruction( "ADC", "ZPX", 4 );
		MicroRom[118] = new Instruction( "ROR", "ZPX", 6 );
		MicroRom[119] = new Instruction( "???", "IMP", 6 );
		MicroRom[120] = new Instruction( "SEI", "IMP", 2 );
		MicroRom[121] = new Instruction( "ADC", "ABY", 4 );
		MicroRom[122] = new Instruction( "NOP", "IMP", 2 );
		MicroRom[123] = new Instruction( "???", "IMP", 7 );
		MicroRom[124] = new Instruction( "NOP", "IMP", 4 );
		MicroRom[125] = new Instruction( "ADC", "ABX", 4 );
		MicroRom[126] = new Instruction( "ROR", "ABX", 7 );
		MicroRom[127] = new Instruction( "???", "IMP", 7 );
		MicroRom[128] = new Instruction( "NOP", "IMP", 2 );
		MicroRom[129] = new Instruction( "STA", "IZX", 6 );
		MicroRom[130] = new Instruction( "NOP", "IMP", 2 );
		MicroRom[131] = new Instruction( "???", "IMP", 6 );
		MicroRom[132] = new Instruction( "STY", "ZP0", 3 );
		MicroRom[133] = new Instruction( "STA", "ZP0", 3 );
		MicroRom[134] = new Instruction( "STX", "ZP0", 3 );
		MicroRom[135] = new Instruction( "???", "IMP", 3 );
		MicroRom[136] = new Instruction( "DEY", "IMP", 2 );
		MicroRom[137] = new Instruction( "NOP", "IMP", 2 );
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
		MicroRom[156] = new Instruction( "NOP", "IMP", 5 );
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
		MicroRom[194] = new Instruction( "NOP", "IMP", 2 );
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
		MicroRom[212] = new Instruction( "NOP", "IMP", 4 );
		MicroRom[213] = new Instruction( "CMP", "ZPX", 4 );
		MicroRom[214] = new Instruction( "DEC", "ZPX", 6 );
		MicroRom[215] = new Instruction( "???", "IMP", 6 );
		MicroRom[216] = new Instruction( "CLD", "IMP", 2 );
		MicroRom[217] = new Instruction( "CMP", "ABY", 4 );
		MicroRom[218] = new Instruction( "NOP", "IMP", 2 );
		MicroRom[219] = new Instruction( "???", "IMP", 7 );
		MicroRom[220] = new Instruction( "NOP", "IMP", 4 );
		MicroRom[221] = new Instruction( "CMP", "ABX", 4 );
		MicroRom[222] = new Instruction( "DEC", "ABX", 7 );
		MicroRom[223] = new Instruction( "???", "IMP", 7 );
		MicroRom[224] = new Instruction( "CPX", "IMM", 2 );
		MicroRom[225] = new Instruction( "SBC", "IZX", 6 );
		MicroRom[226] = new Instruction( "NOP", "IMP", 2 );
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
		MicroRom[244] = new Instruction( "NOP", "IMP", 4 );
		MicroRom[245] = new Instruction( "SBC", "ZPX", 4 );
		MicroRom[246] = new Instruction( "INC", "ZPX", 6 );
		MicroRom[247] = new Instruction( "???", "IMP", 6 );
		MicroRom[248] = new Instruction( "SED", "IMP", 2 );
		MicroRom[249] = new Instruction( "SBC", "ABY", 4 );
		MicroRom[250] = new Instruction( "NOP", "IMP", 2 );
		MicroRom[251] = new Instruction( "???", "IMP", 7 );
		MicroRom[252] = new Instruction( "NOP", "IMP", 4 );
		MicroRom[253] = new Instruction( "SBC", "ABX", 4 );
		MicroRom[254] = new Instruction( "INC", "ABX", 7 );
		MicroRom[255] = new Instruction( "???", "IMP", 7 );

	}

	//Punto di ingresso globale all'istanza
	public static OperativeUnit getInstance() {
		if(UO==null) {
			synchronized(OperativeUnit.class) {
				if(UO==null) {
					UO = new OperativeUnit();
				}
			}
		}
		return UO;
	}
	
	//Fetch dell'opcode 
	public Byte fetch_pc() {
		//Leggo tramite BUS il valore nell'indirizzo indicato dal PC
		return BusOU.read(PC_register);
	}
	
	private Byte fetch() {
		//Leggo tramite BUS il valore nell'indirizzo indicato dalll'inidirizzo assoluto
		if(ControlUnit.getInstance().getCurrentInstruction().addressing_mode != "IMP")
			fetched = BusOU.read(addr_abs);
		return fetched;
	}

	//Scelta modo di indirizzamento
	public Boolean addressingMode(String AddrMode) {
		switch(AddrMode) {
		case "IMP":
			return this.IMP();
		
		case "IMM":
			return this.IMM();
			
		case "ZP0":
			return this.ZP0();
			
		case "ZPX":
			return this.ZPX();
			
		case "ZPY":
			return this.ZPY();
		
		case "REL":
			return this.REL();
		
		case "ABS":
			return this.ABS();
			
		case "ABX":
			return this.ABX();
			
		case "ABY":
			return this.ABY();
			
		case "IND":
			return this.IND();
			
		case "IZX":
			return this.IZX();
			
		case "IZY":
			return this.IZY();
			
		default: 
			System.out.println("Code ??? not recognised");
			break;
		}
		
		return true;
	}

	//Scelta codice operativo
	public Boolean Execute(String Opcode) {
		
		switch(Opcode) {
		
		case "ADC":
			return this.ADC();
			
		case "SBC":
			return this.SBC();
			
		case "AND":
			return this.AND();
			
		case "ASL":
			return this.ASL();
			
		case "BCC":
			return this.BCC();
			
		case "BCS":
			return this.BCS();
			
		case "BEQ":
			return this.BEQ();
			
		case "BIT":
			return this.BIT();
			
		case "BMI":
			return this.BMI();
			
		case "BNE":
			return this.BNE();
			
		case "BPL":
			return this.BPL();
			
		case "BRK":
			return this.BRK();
			
		case "BVC":
			return this.BVC();

		case "BVS":
			return this.BVS();
			
		case "CLC":
			return this.CLC();
			
		case "CLD":
			return this.CLD();

		case "CLI":
			return this.CLI();
			
		case "CLV":
			return this.CLV();
			
		case "CMP":
			return this.CMP();

		case "CPX":
			return this.CPX();
			
		case "CPY":
			return this.CPY();
			
		case "DEC":
			return this.DEC();

		case "DEX":
			return this.DEX();
			
		case "DEY":
			return this.DEY();
			
		case "EOR":
			return this.EOR();

		case "INC":
			return this.INC();
			
		case "INX":
			return this.INX();
			
		case "INY":
			return this.INY();
			
		case "JMP":
			return this.JMP();
			
		case "JSR":
			return this.JSR();

		case "LDA":
			return this.LDA();
			
		case "LDX":
			return this.LDX();
			
		case "LDY":
			return this.LDY();

		case "LSR":
			return this.LSR();
			
		case "NOP":
			return this.NOP();
			
		case "ORA":
			return this.ORA();

		case "PHA":
			return this.PHA();
			
		case "PHP":
			return this.PHP();
			
		case "PLA":
			return this.PLA();

		case "PLP":
			return this.PLP();
			
		case "ROL":
			return this.ROL();
			
		case "ROR":
			return this.ROR();
			
		case "RTI":
			return this.RTI();
			
		case "RTS":
			return this.RTS();

		case "SEC":
			return this.SEC();
			
		case "SED":
			return this.SED();
			
		case "SEI":
			return this.SEI();

		case "STA":
			return this.STA();
			
		case "STX":
			return this.STX();
			
		case "STY":
			return this.STY();

		case "TAX":
			return this.TAX();
			
		case "TAY":
			return this.TAY();
			
		case "TSX":
			return this.TSX();

		case "TXA":
			return this.TXA();
			
		case "TXS":
			return this.TXS();
			
		case "TYA":
			return this.TYA();
			
		case "???":
			return this.XXX();
			
		case "NMI":
			this.nmi();
			return false;
			
		case "IRQ":
			this.irq();
			return false;
			
		default: 
			System.out.println("Code ??? not recognised");
			break;
		}
		
		return true;
	}
	
	//ADDRESSING MODE
	
	//Implied
	private Boolean IMP() {
		
		
		//Lavoro direttamente sull'accumulatore
		fetched = A_register;
		return false;
	}
	
	//Immediate
	private Boolean IMM() {
		//Andrò a leggere il prossimo byte		
		addr_abs = PC_register++;
		
		
		return false;
	}	
	
	//Zero Page
	private Boolean ZP0() {
		//Leggo l'offset del program counter nella pagina 0
		addr_abs = (char)Byte.toUnsignedInt(BusOU.read(PC_register));		//Vado a leggere il contenuto in memoria nella locazione indicata dal PC
		PC_register++;														//Incremento il PC
		addr_abs &= 0x00FF;													//L'Indirizzo a cui dovrò leggere è nella pagina 0
		return false;
	}	
	
	//Zero Page with X Offset
	private Boolean ZPX() {
		//Leggo l'offset del program counter + x nella pagina 0
		addr_abs = (char)(Byte.toUnsignedInt(BusOU.read((char)(PC_register)).byteValue()) + Byte.toUnsignedInt(X_register));   //Vado a leggere il contenuto in memoria nella locazione PC+X
		PC_register++;															    											//Incremento il PC
		addr_abs &= 0x00FF;																										//L'Indirizzo a cui dovrò leggere è nella pagina 0
		return false;	
	}	
	
	//Zero Page with Y Offset
	private Boolean ZPY() {
		//Leggo l'offset del program counter +y nella pagina 0
		addr_abs = (char)(Byte.toUnsignedInt(BusOU.read((char)(PC_register)).byteValue()) + Byte.toUnsignedInt(Y_register));  //Vado a leggere il contenuto in memoria nella locazione PC+Y
		PC_register++;															 					   						  //Incremento il PC
		addr_abs &= 0x00FF;															  										  //L'Indirizzo a cui dovrò leggere è nella pagina 0
				
		return false;
	}	

	//Relative
	private Boolean REL() {
		addr_rel = (char)BusOU.read(PC_register).byteValue();							//Leggo il primo byte in memoria	
		PC_register++;
		if ((addr_rel & 0x0080) != 0)													//Se il valore del byte supera 0x80 
			addr_rel |= 0xFF00;															//Diventa negativo così da far rimanere il valore tra -128 e 127
		return false;
	}
	
	//Absolute 
	private Boolean ABS() {
		Integer lo = Byte.toUnsignedInt(BusOU.read(PC_register));			//Leggo il primo byte in memoria	
		PC_register++;														//Incremento il PC
		Integer hi = Byte.toUnsignedInt(BusOU.read(PC_register));			//Leggo il secondo byte in memoria
		PC_register++;														//Incremento il PC
		
		addr_abs = (char)((hi << 8) | lo );									//L'indirizzo assoluto è composto dal primo e dal secondo byte letti
		return false;
	}	
	
	//Absolute with X Offset
	private Boolean ABX() {
		Integer lo = Byte.toUnsignedInt(BusOU.read(PC_register));				//Leggo il primo byte in memoria	
		PC_register++;															//Incremento il PC
		Integer hi = Byte.toUnsignedInt(BusOU.read(PC_register));				//Leggo il secondo byte in memoria
		PC_register++;															//Incremento il PC
	
		addr_abs = (char)((hi << 8) | lo);										//L'indirizzo assoluto è composto dal primo e dal secondo byte letti
		addr_abs += Byte.toUnsignedInt(X_register);								//Sommo il contenuto del registro x all'indirizzo

		if ((addr_abs & 0xFF00) != (char)(hi << 8))								//Se ho cambiato pagina allora ci metterò più tempo ad eseguire l'istruzione
			return true;
		else
			return false;
	}	
	
	//Absolute with Y Offset
	private Boolean ABY() {
		Integer lo = Byte.toUnsignedInt(BusOU.read(PC_register));				//Leggo il primo byte in memoria	
		PC_register++;															//Incremento il PC
		Integer hi = Byte.toUnsignedInt(BusOU.read(PC_register));				//Leggo il secondo byte in memoria
		PC_register++;															//Incremento il PC
	
		addr_abs = (char)((hi << 8) | lo);										//L'indirizzo assoluto è composto dal primo e dal secondo byte letti
		addr_abs += Byte.toUnsignedInt(Y_register);								//Sommo il contenuto del registro y all'indirizzo

		if ((addr_abs & 0xFF00) != (char)(hi << 8))								//Se ho cambiato pagina allora ci metterò più tempo ad eseguire l'istruzione
			return true;
		else
			return false;
	}	
	
	//Indirect
	private Boolean IND() {
		
		Integer PC = (int)PC_register;
		
		Integer ptr_lo = Byte.toUnsignedInt(BusOU.read((char)(int)PC));				//Leggo il primo byte in memoria	
		PC++;																		//Incremento il PC
		Integer ptr_hi = Byte.toUnsignedInt(BusOU.read((char)(int)PC));				//Leggo il secondo byte in memoria
		PC++;																		//Incremento il PC


		Integer ptr = (ptr_hi << 8) | ptr_lo;									//Costruisco un puntatore per andare a prendere il vero e proprio indirizzo

		if (ptr_lo == 0x00FF) 													// Simula un bug hardware (Se il +1 causa un cambiamento di pagina in realtà la pagina non viene cambiata)
		{
			//ptr = (char)((ptr_hi << 8) | 0x00);	
			Integer hi = Byte.toUnsignedInt(BusOU.read((char)(int)(ptr & 0xFF00)).byteValue());
			Integer lo = Byte.toUnsignedInt(BusOU.read((char)(int)(ptr)).byteValue());
			addr_abs = (char)((hi << 8) | lo);
		}
		else 				 													//Comportamento normale 
		{
			//char ptr1 = (char)((ptr_hi << 8) | (ptr_lo +1));
			Integer hi = Byte.toUnsignedInt(BusOU.read((char)(int)(ptr+1)).byteValue());
			Integer lo = Byte.toUnsignedInt(BusOU.read((char)(int)(ptr)).byteValue());
			addr_abs = (char)((hi << 8) | lo);
		}
		
		PC_register = (char)(int)PC;
		
		return false;
	}	
	
	//Indirect X
	private Boolean IZX() {
		Integer t = Byte.toUnsignedInt(BusOU.read(PC_register));	//Leggo il primo byte in memoria	

		Integer PC = (int)PC_register;
		PC++;
		
		Integer lo = Byte.toUnsignedInt(BusOU.read((char)( (t + Byte.toUnsignedInt(X_register)) & 0x00FF )));			//Leggo il primo byte in pagina 0 sommando x al valore t preso in memoria
		Integer hi = Byte.toUnsignedInt(BusOU.read((char)( (t + Byte.toUnsignedInt(X_register) + 1) & 0x00FF )));		//Leggo il secondo byte in pagina 0 sommando x e 1 al valore t preso in memoria

		addr_abs = (char)((hi << 8) | lo);
		
		PC_register = (char)(int)PC;
		
		
		
		return false;
	}	
	
	//Indirect Y
	private Boolean IZY() {
		Integer t = Byte.toUnsignedInt(BusOU.read(PC_register));	//Leggo il primo byte in memoria	

		Integer PC = (int)PC_register;
		PC++;
		
		Integer lo = Byte.toUnsignedInt(BusOU.read((char)( t  & 0x00FF )));				//Leggo il primo byte in pagina 0 sommando x al valore t preso in memoria
		Integer hi = Byte.toUnsignedInt(BusOU.read((char)( (t  + 1) & 0x00FF )));		//Leggo il secondo byte in pagina 0 sommando x e 1 al valore t preso in memoria

		addr_abs = (char)((hi << 8) | lo);
		
		addr_abs += Byte.toUnsignedInt(Y_register);			
		
		PC_register = (char)(int)PC;
		if ((addr_abs & 0xFF00) != (char)(hi << 8))										//Se cambio pagina potrei metterci più tempo a eseguire l'istruzione
			return true;
		else
			return false;
	}
	


	//OPCODES
	
	//Add
	private boolean ADC() {
		// Prelevo i dati da aggiungere all'accumulatore
		fetch();
		
		//Accumulatore + valore prelevato + Carry(se alto)
		char temp;
		if(getFlag("C"))
			temp = (char)(Byte.toUnsignedInt(A_register) + Byte.toUnsignedInt(fetched) + 1);
		else
			temp = (char)(Byte.toUnsignedInt(A_register) + Byte.toUnsignedInt(fetched));
		
		// Se il valore di temp è maggiore di 255 avrò un carry
		setFlag("C", temp > 255);
		
		// Setto il flag Z a 1 se il risultato è 0
		setFlag("Z", (int)(temp & 0x00FF) == 0);
		
		int A = Byte.toUnsignedInt(A_register);
		int f = Byte.toUnsignedInt(fetched);
		
		//Setto il flag di overflow
		setFlag("V",(((~(A ^ f) & (A ^ temp))& 0x0080)) != 0x00);			
		
		// Il flag negativo è settato al valore del bit più significativo
		setFlag("N", (temp & 0x80) != 0);
		
		// Carico il risultato nell'accumulatore (passa ad 8-bit)
		A_register = (byte) (temp & 0x00FF);	
		
		// l'istruzione potrebbe richiedere un ulteriore ciclo di clock
		return true;
	}
	

	//Sub
	private boolean SBC() {
		fetch();
		
		
		// Inverto i primi 8 bit con una xor
		char value = (char)((char)fetched.byteValue() ^ 0x00FF);
		
		
		// Da qui è esattamente come la addizione
		char temp;
		if(getFlag("C"))
			temp = (char)(Byte.toUnsignedInt(A_register) + Byte.toUnsignedInt((byte) value) + 1);
		else
			temp = (char)(Byte.toUnsignedInt(A_register) + Byte.toUnsignedInt((byte) value));
		

		setFlag("C", (temp & 0xFF00) != 0);
		setFlag("Z", (temp & 0x00FF) == 0);
		
		int A = Byte.toUnsignedInt(A_register);
		int t = (int)(temp & 0xFFFF);
		
		setFlag("V",((((t ^ A) & (t ^ value)) & 0x0080)) != 0x00);
		setFlag("N", (temp & 0x80) != 0);
		
		A_register = (byte) (temp & 0x00FF);

		return true;
	}
	
	//Logic AND
	private boolean AND() {
		
		fetch();

		A_register = (byte)(A_register & fetched);

		setFlag("Z",A_register == 0x00);		 //Se la AND ha dato risultato 0x00, abilita il registro 0
		setFlag("N",0 != (A_register & 0x80));   //Se il bit più significativo del registro A è alto, setta il flag Negative.
		
		return true;	
	}
	
	//Arithmetic Shift Left
	private boolean ASL() {
		
		fetch();
		 
		char temp = (char)((char)fetched.byteValue() & 0x00FF); 		   	//Shifta a sinistra il valore fetchato
		temp = (char)(temp << 1);											//Shifta a sinistra il valore fetchato
		setFlag("C", (temp & 0xFF00) > 0); 									//se tale shift rende un valore maggiore di 256, allora alza il carry flag
		setFlag("Z", (temp & 0x00FF) == 0x0000); 							//se lo shift porta i primi 8 bit ad essere nulli, alza il flag zero
		setFlag("N", 0x0000 != (temp & 0x0080)); 							//se lo shift porta ad avere il bit alto alla posizione 8, abilita il flag Negative
		
		if (ControlUnit.getInstance().getCurrentInstruction().addressing_mode == "IMP") //se l'address mode è implied, scrivi in A, altrimenti in memoria
			A_register = (byte)(temp & 0x00FF);
		else
			BusOU.write(addr_abs, (byte)(temp & 0x00FF));
		return false;
		
	}

	//Branch if Carry Clear
	private boolean BCC() {
		
		if (getFlag("C")== false) {
			
			ControlUnit.getInstance().increaseCycles();
			addr_abs= (char) (PC_register + addr_rel);
			
			if ((char)(addr_abs & 0xFF00) != (char)(PC_register & 0xFF00)) 
				ControlUnit.getInstance().increaseCycles();
			
			PC_register= addr_abs;
			
		}			
		
		return false;	
		
	}
		
	//Branch if Carry Set
	private boolean BCS() {		
		
		if (getFlag("C")== true) {
		
			ControlUnit.getInstance().increaseCycles();
			addr_abs= (char) (PC_register + addr_rel);
		
			if ((char)(addr_abs & 0xFF00) != (char)(PC_register & 0xFF00)) 
				ControlUnit.getInstance().increaseCycles();
		
			PC_register= addr_abs;
		
		}
	
		return false;	
	
	}
	
	//Branch if Equal
	private boolean BEQ() {	
		
		if (getFlag("Z")== true) {
		
			ControlUnit.getInstance().increaseCycles();
			addr_abs= (char) (PC_register + addr_rel);
		
			if ((char)(addr_abs & 0xFF00) != (char)(PC_register & 0xFF00)) 
				ControlUnit.getInstance().increaseCycles();
		
			PC_register= addr_abs;
		
		}
		return false;
	
	}
	
	//BIT
	private boolean BIT() {
		
		fetch();
		char temp = (char) (A_register & fetched); 	// AND tra valore fetchato d il registro A
		setFlag("Z", (temp & 0x00FF) == 0x0000); 	//se la AND porta i primi 8 bit ad essere nulli, alza il flag zero
		setFlag("N", (fetched & (1<<7))!=0);		// se il settimo bit di Fetched è 1 alza il flag N
		setFlag("V", (fetched & (1<<6))!=0);		// se il sesto bit di Fetched è 1 alza il flag V
		
		return false;
		
	}
	
	//Branch if Negative
	private boolean BMI() {	
		
		if (getFlag("N")== true) {
		
			ControlUnit.getInstance().increaseCycles();
			addr_abs= (char) (PC_register + addr_rel);
		
			if ((char)(addr_abs & 0xFF00) != (char)(PC_register & 0xFF00)) 
				ControlUnit.getInstance().increaseCycles();
		
			PC_register= addr_abs;
		
		}
		
		return false;
	}
	
	//Branch if Not Equal
	private boolean BNE() {	
		
		if (getFlag("Z")== false) {
		
			ControlUnit.getInstance().increaseCycles();
			addr_abs= (char) (PC_register + addr_rel);
		
			if ((char)(addr_abs & 0xFF00) != (char)(PC_register & 0xFF00)) 
				ControlUnit.getInstance().increaseCycles();
		
			PC_register= addr_abs;
		
		}
		return false;
	}
	
	//Branch if Positive
	private boolean BPL() {	
		
		if (getFlag("N")== false) {
		
			ControlUnit.getInstance().increaseCycles();
			addr_abs= (char) (PC_register + addr_rel);
		
			if ((char)(addr_abs & 0xFF00) != (char)(PC_register & 0xFF00)) 
				ControlUnit.getInstance().increaseCycles();
		
			PC_register= addr_abs;
		
		}
		return false;
	}
	
	//Break
	private boolean BRK() {
		
		PC_register++;
		
		setFlag("I", true);
		BusOU.write((char)(0x0100 + Byte.toUnsignedInt(Stack_pointer)), (byte)((PC_register>>8) & 0x00FF));
		
		Stack_pointer= (byte) (Stack_pointer-1);
		BusOU.write((char)(0x0100 + Byte.toUnsignedInt(Stack_pointer)), (byte)(PC_register & 0x00FF));
		Stack_pointer= (byte)(Stack_pointer-1);
		
		setFlag("B",true);
		BusOU.write((char)(0x0100 + Byte.toUnsignedInt(Stack_pointer)), (byte)Status_register);
		Stack_pointer= (byte)(Stack_pointer-1);
		
		setFlag("B",false);
		
		Integer lo = Byte.toUnsignedInt(BusOU.read((char) 0xFFFF));
		Integer hi = Byte.toUnsignedInt(BusOU.read((char) 0xFFFE));
		
		PC_register= (char) ( hi | (lo << 8));
			
			
		return false;
	}
	
	//Branch if Overflow Clear
	private boolean BVC() {	
		
		if (getFlag("V")== false) {
		
			ControlUnit.getInstance().increaseCycles();
			addr_abs= (char) (PC_register + addr_rel);
		
			if ((char)(addr_abs & 0xFF00) != (char)(PC_register & 0xFF00)) 
				ControlUnit.getInstance().increaseCycles();;
		
			PC_register= addr_abs;
		
		}
		return false;
	}
	
	//Branch if Overflow Set
	private boolean BVS() {	
		
		if (getFlag("V")== true) {
		
			ControlUnit.getInstance().increaseCycles();
			addr_abs= (char) (PC_register + addr_rel);
		
			if ((char)(addr_abs & 0xFF00) != (char)(PC_register & 0xFF00)) 
				ControlUnit.getInstance().increaseCycles();
		
			PC_register= addr_abs;
		
		}
		return false;
	}
	
	//Clear
	private boolean CLC() {
		
		setFlag("C", false);
		return false;
	}
	
	//Clear Decimal Flag
	private boolean CLD() {
		
		setFlag("D", false);
		return false;
	}
	
	//Disable Interrupts / Clear Interrupt Flag
	private boolean CLI() {
		
		setFlag("I", false);
		return false;
	}
		
	//Clear Overflow Flag
	// Function:    V = 0
	private boolean CLV() {
		
		setFlag("V", false);
		return false;
	}

	//Compare Accumulator
	// Function:    C <- A >= M      Z <- (A - M) == 0
	// Flags Out:   N, C, Z
	private boolean CMP() {
		
		fetch();
		
		char temp= (char)(Byte.toUnsignedInt(A_register)- Byte.toUnsignedInt(fetched));
		setFlag("C", Byte.toUnsignedInt(A_register) >= Byte.toUnsignedInt(fetched));
		setFlag("Z", (temp & 0x00FF) == 0x0000);
		setFlag("N", 0 != (temp & 0x0080));
		
		return true;
	}
	
	//Compare X Register
	// Function:    C <- X >= M      Z <- (X - M) == 0
	// Flags Out:   N, C, Z
	private boolean CPX() {
		
		fetch();
		
		char temp= (char)(Byte.toUnsignedInt(X_register) - Byte.toUnsignedInt(fetched));
		setFlag("C", Byte.toUnsignedInt(X_register) >= Byte.toUnsignedInt(fetched));
		setFlag("Z", (temp & 0x00FF) == 0x0000);
		setFlag("N", 0 != (temp & 0x0080));
		
		return false;
		
		
	}
	
	//Compare Y Register
	// Function:    C <- Y >= M      Z <- (Y - M) == 0
	// Flags Out:   N, C, Z
	private boolean CPY() {
		
		fetch();
		
		char temp= (char)(Byte.toUnsignedInt(Y_register)- Byte.toUnsignedInt(fetched));
		setFlag("C", Byte.toUnsignedInt(Y_register) >= Byte.toUnsignedInt(fetched));
		setFlag("Z", (temp & 0x00FF) == 0x0000);
		setFlag("N", 0 != (temp & 0x0080));
		
		return false;
		
	}
	
	//Decrement Value at Memory Location
	// Function:    M = M - 1
	// Flags Out:   N, Z
	private boolean DEC() {
		
		fetch();
		
		char temp= (char)(Byte.toUnsignedInt(fetched)-1);
		
		BusOU.write(addr_abs, (byte)(temp & 0x00FF));
		setFlag("Z", (temp & 0x00FF) == 0x00);
		setFlag("N", 0 != (temp & 0x0080));
		
		return false;
		
	}
	
	//Decrement X Register
	// Function:    X = X - 1
	// Flags Out:   N, Z
	private boolean DEX() {
		
		X_register= (byte)(X_register-1);
	    setFlag("Z", X_register == 0x00);
		setFlag("N", 0 != (X_register & 0x80));
		return false;

	}
	
	//Decrement Y Register
	// Function:    Y = Y - 1
	// Flags Out:   N, Z
	private boolean DEY() {
		
		Y_register= (byte)(Y_register-1);
	    setFlag("Z", Y_register == 0x00);
		setFlag("N", 0 != (Y_register & 0x80));
		
		return false;
		
	}
	
	//Bitwise Logic XOR
	// Function:    A = A xor M
	// Flags Out:   N, Z
	private boolean EOR() {
		
		fetch();
		A_register= (byte)(A_register^fetched);
		setFlag("Z", A_register == 0x00);
		setFlag("N", 0 != (A_register & 0x80));
		
		return true;
		
		
	}
	
	//Increment Value at Memory Location
	// Function:    M = M + 1
	// Flags Out:   N, Z
	private boolean INC() {	
		
		fetch();
	
		char temp= (char)(fetched+1);
	
		BusOU.write(addr_abs, (byte) (temp & 0x00FF));
		setFlag("Z", (temp & 0x00FF) == 0x0000);
		setFlag("N", 0 != (temp & 0x0080));
	
		return false;
	
	}
	
	//Increment X Register
	// Function:    X = X + 1
	// Flags Out:   N, Z
	private boolean INX() {
		
		X_register= (byte)(X_register+1);
	    setFlag("Z", X_register == 0x00);
		setFlag("N", 0 != (X_register & 0x80));
		return false;
		
	}
	
	//Increment Y Register
	// Function:    Y = Y + 1
	// Flags Out:   N, Z
	private boolean INY() {
		

		Y_register= (byte)(Y_register+1);
	    setFlag("Z", Y_register == 0x00);
		setFlag("N", 0 != (Y_register & 0x80));
		return false;
		
	}
	
	//Jump To Location
	// Function:    pc = address
	private boolean JMP() {
		
		PC_register= addr_abs;
		
		return false;
		
		
	}
	
	//Jump To Sub-Routine
	// Function:    Push current pc to stack, pc = address
	private boolean JSR() {
		
		Integer PC = (int)PC_register;
		Integer SP = (int)Byte.toUnsignedInt(Stack_pointer);
		
		PC--;
		
		BusOU.write((char)(0x0100 + SP), (byte)((PC >> 8) & 0x00FF));
		SP = SP-1;
		
		BusOU.write((char)(0x0100 + SP), (byte)(PC & 0x00FF));
		SP = SP-1;
		
		
		
		PC_register = (char)(int)PC;
		Stack_pointer = (byte)(int)SP;
		
		PC_register= addr_abs;
		
		return false;
		
	}
	
	//Load The Accumulator
	// Function:    A = M
	// Flags Out:   N, Z
	private boolean LDA() {
		
		fetch();
		A_register = fetched;
		
	    setFlag("Z", A_register == 0x00);
		setFlag("N", 0 != (A_register & 0x80));
		
		return true;
		
	}
	
	
	//Load The X Register
	// Function:    X = M
	// Flags Out:   N, Z
	private boolean LDX() {
		
		fetch();
		X_register= fetched;
	    setFlag("Z", X_register == 0x00);
		setFlag("N", 0 != (X_register & 0x80));
		
		return true;
		
		
	} 
	
	
	//Load The Y Register
	// Function:    Y = M
	// Flags Out:   N, Z
	private boolean LDY() {
		
		fetch();
		Y_register= fetched;
	    setFlag("Z", Y_register == 0x00);
		setFlag("N", 0 != (Y_register & 0x80));
		
		return true;
		
		
	}
	
	private boolean LSR() {
		
		fetch();
		setFlag("C", 0 !=(fetched & 0x01) );
		Integer temp= (Byte.toUnsignedInt(fetched) >> 1);
		setFlag("Z", (temp & 0x00FF) == 0);
		setFlag("N", (temp & 0x0080) != 0);
		
		if (ControlUnit.getInstance().getCurrentInstruction().addressing_mode == "IMP")
			A_register = (byte)(temp & 0x00FF);
		else 
			BusOU.write(addr_abs, (byte)(temp & 0x00FF));
		
		return false;
	}
		
	//No Operation
	private boolean NOP() {

		Instruction CurrentInstruction= ControlUnit.getInstance().getCurrentInstruction();
		Instruction NoOp1= MicroRom[28];
		Instruction NoOp2= MicroRom[60];
		Instruction NoOp3= MicroRom[92];
		Instruction NoOp4= MicroRom[220];
		Instruction NoOp5= MicroRom[252];
		Instruction NoOp6 = MicroRom[137];

		if ( CurrentInstruction == NoOp1 ||CurrentInstruction== NoOp2 ||CurrentInstruction== NoOp3 ||CurrentInstruction== NoOp4 ||CurrentInstruction== NoOp5 )
			return true;
		else 
			return false;
	}
	
	//Bitwise Logic OR
	// Function:    A = A | M
	// Flags Out:   N, Z
	private boolean ORA() {
		
		fetch();
		A_register= (byte)(A_register | fetched);
		setFlag("Z", A_register == 0x00);
		setFlag("N", 0 != (A_register & 0x80));
		
		return true;
	}
	
	//Push Accumulator to Stack
	// Function:    A -> stack
	private boolean PHA() {
		
		Integer SP = Byte.toUnsignedInt(Stack_pointer);
		
		
		BusOU.write( (char)(0x0100+ SP), A_register);
		SP--;
		
		Stack_pointer = (byte)(int)SP;
		return false;
		
	}
	
	//Push Status Register to Stack
	// Function:    status -> stack
	// Note:        Break flag is set to 1 before push
	private boolean PHP() {
		
		Byte B,U;
		
		B= (byte)(0x10);
		
		U= (byte)(0x20);
		
		BusOU.write((char)(0x0100 + Byte.toUnsignedInt(Stack_pointer)), (byte)((byte)(Status_register | B) | U));
		
		setFlag("B", false);
		setFlag("U", false);
		Stack_pointer= (byte)(Stack_pointer-1);
		
		return false;
		
	}
	
	//Pop Accumulator off Stack
	// Function:    A <- stack
	// Flags Out:   N, Z
	private boolean PLA() {
		
		Integer SP = Byte.toUnsignedInt(Stack_pointer);
		
		SP = SP + 1;	
		A_register = BusOU.read((char)(0x0100 + SP));
		
		
		setFlag("Z", A_register == 0x00);
		setFlag("N", 0 != (A_register & 0x80));
		
		Stack_pointer = (byte)(int)SP;
		return false;
		
	}
	
	//Pop Status Register off Stack
	// Function:    Status <- stack
	private boolean PLP() {
		
		Integer SP = Byte.toUnsignedInt(Stack_pointer);
		Integer SR = Byte.toUnsignedInt(Status_register);
		
		//System.out.println("SP : " + Integer.toBinaryString(SP));
		//System.out.println("Status : " + Integer.toBinaryString(SR));
		SP++;
		SR= Byte.toUnsignedInt(BusOU.read((char) (0x0100 + SP)));
		setFlag("U", true);
		
		//System.out.println("SP : " + Integer.toBinaryString(SP));
		//System.out.println("Status : " + Integer.toBinaryString(SR));
		
		Stack_pointer = (byte)(int)SP;
		Status_register = (byte)(int)SR;
		
		return false;
	}
	
	
    private boolean ROL() {
        
        fetch();
        char C;
       
        if(getFlag("C")==true)
            C=0x0001;
        else
            C=0x0000;
       
        char temp= (char)((char)(fetched<<1)| C); // conversione da byte in intero per avere il valore unsigned, la AND con 0xff serve a tagliare le cifre aggiunte con il cast
        setFlag("C", 0x0000 != (temp & 0xFF00));
        setFlag("Z", 0x0000 == (temp & 0x00FF));
        setFlag("N", 0x0000 != (temp & 0x0080));
        
       
        if (ControlUnit.getInstance().getCurrentInstruction().addressing_mode == "IMP")
            A_register= (byte)(temp & 0x00FF);
       
        else
            BusOU.write(addr_abs, (byte)(temp & 0x00FF));
       
        return false;
    }
   
    private boolean ROR() {
       
        fetch();
        Integer C;
       
        if(getFlag("C"))
            C=1;
        else
            C=0;
       
        char temp= (char)((C<<7) | (fetched>>1)); // conversione da byte in intero per avere il valore unsigned, la AND con 0xff serve a tagliare le cifre aggiunte con il cast
        setFlag("C", 0 != (fetched & 0x01));
        setFlag("Z", 0x0000 == (temp & 0x00FF));
        setFlag("N", 0x0000 != (temp & 0x0080));
       
        if (ControlUnit.getInstance().getCurrentInstruction().addressing_mode == "IMP")
            A_register= (byte)(temp & 0x00FF);
       
        else
            BusOU.write(addr_abs, (byte)(temp & 0x00FF));
       
        return false;
       
    }
	
	//Return from interrupt
	private boolean RTI() {
		
		
		Integer SP = Byte.toUnsignedInt(Stack_pointer);
		Integer PC = (int)PC_register;
		
		SP = SP+1;
		
		Status_register = BusOU.read((char) (0x0100+ SP));
		
		Integer B = 0;
		Integer U = 0;

		
		Status_register = ByteManager.setBit(4,B, Status_register);
		Status_register = ByteManager.setBit(5,U, Status_register);
		
		SP = SP+1;
		PC = Byte.toUnsignedInt(BusOU.read((char)(0x0100 + SP)));
		SP = SP+1;
		
		Integer temp =(BusOU.read((char)(0x0100 + SP))<<8);
		PC = (PC | temp);
		
		Stack_pointer = (byte)(int)SP;
		PC_register = (char)(int)PC;
		
		return false;
		
	
	}
	
	//Return from subroutine
	private boolean RTS() {
		
		/*stkp++;
		pc = (uint16_t)read(0x0100 + stkp);
		stkp++;
		pc |= (uint16_t)read(0x0100 + stkp) << 8;*/
		
		Integer SP = Byte.toUnsignedInt(Stack_pointer);
		Integer PC = (int)(PC_register);
		SP= (SP+1);
		PC = Byte.toUnsignedInt(BusOU.read((char)(0x0100 + SP)));
		SP= (SP+1);
		
		Integer temp =(BusOU.read((char)(0x0100 + SP))<<8);
		
		PC =  (PC | temp);
		
		PC_register = (char)PC.intValue();
		Stack_pointer = (byte)SP.intValue();

		PC_register++;
		
		return false;
	}
	
	//Set Carry Flag
	// Function:    C = 1
	private boolean SEC() {
		
		setFlag("C", true);
		return false;
		
	}
	
	
	//Set Decimal Flag
	// Function:    D = 1
	private boolean SED() {
		
		setFlag("D", true);
		return false;
	}
	
	//et Interrupt Flag / Enable Interrupts
	// Function:    I = 1
	private boolean SEI() {
		
		setFlag("I", true);
		return false;
	}
	
	//Store Accumulator at Address
	// Function:    M = A
	private boolean STA() {
		BusOU.write(addr_abs, A_register);
		return false;
	}
	
	 
	//Store X Register at Address
	// Function:    M = X
	private boolean STX() {
		BusOU.write(addr_abs, X_register);
		return false;
	}
	
	
	//Store Y Register at Address
	// Function:    M = Y
	private boolean STY() {
		
		BusOU.write(addr_abs, Y_register);
		return false;
	}
	
	//Transfer Accumulator to X Register
	// Function:    X = A
	// Flags Out:   N, Z
	private boolean TAX() {
		
		X_register= A_register;
		setFlag("Z",   (0x00 ==X_register));
		setFlag("N", 0x00 != (X_register & 0x80));
		return false;
	}
	
	//Transfer Accumulator to Y Register
	// Function:    Y = A
	// Flags Out:   N, Z
	private boolean TAY() {
		
		Y_register= A_register;
		setFlag("Z", (0x00 ==Y_register));
		setFlag("N", 0x00 != (Y_register & 0x80));
		return false;
	
		
	}
	
	//Transfer Stack Pointer to X Register
	// Function:    X = stack pointer
	// Flags Out:   N, Z
	private boolean TSX() {
		
		X_register= Stack_pointer;
		setFlag("Z",  (0x00 == X_register));
		setFlag("N", 0x00 != (X_register & 0x80));
		return false;
	
		
	}
	
	
	//Transfer X Register to Accumulator
	// Function:    A = X
	// Flags Out:   N, Z
	private boolean TXA() {
		
		A_register= X_register;
		setFlag("Z", (0x00 ==A_register));
		setFlag("N", 0x00 != (A_register & 0x80));
		return false;
	
		
		
	}
	
	//Transfer X Register to Stack Pointer
	// Function:    stack pointer = X
	private boolean TXS() {
		
		Stack_pointer=X_register;
		
		return false;
		
		
	}
	
	//Transfer Y Register to Accumulator
	// Function:    A = Y
	// Flags Out:   N, Z
	private boolean TYA() {
		
		A_register= Y_register;
		setFlag("Z",  (0x00 ==A_register));
		setFlag("N", 0x00 != (A_register & 0x80));
		return false;
		
	}

	//Illegal opcodes
	private boolean XXX() {
		return false;
	}
	
	//SET & GET
	//SETFLAG E GETFLAG DELLO STATUS REGISTER
	
	public void setFlag(String bit, boolean value) {
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
		
	public boolean getFlag(String bit) {
			
			boolean flagval = false;
			
			switch(bit) {
			
			case "C": //FLAG CARRY
				flagval = !(0x0 == (Status_register & 0x1));
				break;
				
			case "Z": //FLAG ZERO
				flagval = !(0x0 == (Status_register & (1<<1)));
				break;
				
			case "I": //FLAG DISABLE INTERRUPT
				flagval = !(0x0 == (Status_register & (1<<2)));		
				break;
				
			case "D": //FLAG DECIMAL MODE
				flagval = !(0x0 == (Status_register & (1<<3)));		
				break;
				
			case "B": //FLAG BREAK
				flagval = !(0x0 == (Status_register & (1<<4)));			
				break;
				
			case "U": //FLAG UNUSED
				flagval = !(0x0 == (Status_register & (1<<5)));		
				break;
					
			case "V": //FLAG OVERFLOW
				flagval = !(0x0 == (Status_register & (1<<6)));
				break;
				
			case "N": //FLAG NEGATIVE
				flagval = !(0x0 == (Status_register & (1<<7)));
				break;
			}
			
			return flagval;
		}
	
	//Resetto
	public void reset() {
		//Prelevo un valore da un indirizzo prestabilito
		addr_abs = 0xFFFC;
		Integer lo = Byte.toUnsignedInt(BusOU.read(addr_abs));
		Integer hi = Byte.toUnsignedInt(BusOU.read((char)(addr_abs+1)));
		
		//Salvo il valore prelevato come Program Counter
		PC_register = (char)((hi << 8) | lo);
		
		// Resetto i registri interni e i registri di utilità
		A_register = 0x00;
		X_register = 0x00;
		Y_register = 0x00;

		Status_register = (byte)(0x20);	
		Stack_pointer= (byte) 0x00FD;	
		fetched = 0x00;
		addr_abs = 0x0000;
		addr_rel = 0x00;
		
		NMIRequest = false;
		IRQRequest = false;

		// Resetto i cycle a 8
		ControlUnit.getInstance().setCycles(8);
	}
	
	public void increasePC() {
		PC_register++;
	}
	
	//GETTER & SETTERS
	public Instruction getMicrorom(int i) {
		return MicroRom[i];
	}
	public Byte getA_register() {
		return A_register;
	}
	public Byte getX_register() {
		return X_register;
	}
	public Byte getY_register() {
		return Y_register;
	}
	public Byte getStack_pointer() {
		return Stack_pointer;
	}
	public char getPC_register() {
		return PC_register;
	}
	public Byte getStatus_register() {
		return Status_register;
	}
	
	//GESTIONE DELLE INTERRUZIONI
	// Interrupt requests are a complex operation and only happen if the
	// "disable interrupt" flag is 0. IRQs can happen at any time, but
	// you dont want them to be destructive to the operation of the running 
	// program. Therefore the current instruction is allowed to finish
	// (which I facilitate by doing the whole thing when cycles == 0) and 
	// then the current program counter is stored on the stack. Then the
	// current status register is stored on the stack. When the routine
	// that services the interrupt has finished, the status register
	// and program counter can be restored to how they where before it 
	// occurred. This is impemented by the "RTI" instruction. Once the IRQ
	// has happened, in a similar way to a reset, a programmable address
	// is read form hard coded location 0xFFFE, which is subsequently
	// set to the program counter.
	private void irq()
	{
		// If interrupts are allowed
		if (getFlag("I") == false)
		{
			// Push the program counter to the stack. It's 16-bits dont
			// forget so that takes two pushes
			BusOU.write((char)(0x0100 + Byte.toUnsignedInt(Stack_pointer)), (byte)((PC_register >> 8) & 0x00FF));
			Stack_pointer--;
			BusOU.write((char)(0x0100 + Byte.toUnsignedInt(Stack_pointer)), (byte)(PC_register & 0x00FF));
			Stack_pointer--;

			// Then Push the status register to the stack
			setFlag("B", false);
			setFlag("U", true);
			setFlag("I", true);
			BusOU.write((char)(0x0100 + Byte.toUnsignedInt(Stack_pointer)), Status_register);
			Stack_pointer--;

			// Read new program counter location from fixed address
			addr_abs = 0xFFFE;
			Integer lo = Byte.toUnsignedInt(BusOU.read(addr_abs));
			Integer hi = Byte.toUnsignedInt(BusOU.read((char)(addr_abs+1)));
			PC_register = (char)((hi << 8) | lo);
			
			// IRQs take time
			//Integer temp_cycle = ControlUnit.getInstance().getCycles();
			ControlUnit.getInstance().setCycles(7); // + temp_cycle);
		}
	}


	// A Non-Maskable Interrupt cannot be ignored. It behaves in exactly the
	// same way as a regular IRQ, but reads the new program counter address
	// form location 0xFFFA.
	private void nmi()
	{
		// Push the program counter to the stack. It's 16-bits dont
		// forget so that takes two pushes
		BusOU.write((char)(0x0100 + Byte.toUnsignedInt(Stack_pointer)), (byte)((PC_register >> 8) & 0x00FF));
		Stack_pointer--;
		BusOU.write((char)(0x0100 + Byte.toUnsignedInt(Stack_pointer)), (byte)(PC_register & 0x00FF));
		Stack_pointer--;

		// Then Push the status register to the stack
		setFlag("B", false);
		setFlag("U", true);
		setFlag("I", true);
		BusOU.write((char)(0x0100 + Byte.toUnsignedInt(Stack_pointer)), Status_register);
		Stack_pointer--;

		// Read new program counter location from fixed address
		addr_abs = 0xFFFA;
		Integer lo = Byte.toUnsignedInt(BusOU.read(addr_abs));
		Integer hi = Byte.toUnsignedInt(BusOU.read((char)(addr_abs+1)));
		PC_register = (char)((hi << 8) | lo);
					
		// IRQs take time
		//Integer temp_cycle = ControlUnit.getInstance().getCycles();
		ControlUnit.getInstance().setCycles(8); // + temp_cycle);
	}

	public Boolean getIRQRequest() {
		return IRQRequest;
	}
	public Boolean getNMIRequest() {
		return NMIRequest;
	}
	public void setIRQRequest(Boolean IRQ) {
		IRQRequest = IRQ;
	}
	public void setNMIRequest(Boolean NMI) {
		NMIRequest = NMI;
	}
}
