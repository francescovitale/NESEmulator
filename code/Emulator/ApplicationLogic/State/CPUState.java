package Emulator.ApplicationLogic.State;

public class CPUState {
	//Attributi
	private volatile static CPUState CPUState = null;			//Singleton
	private Byte A;		//registro ACCUMULATORE
	private Byte X;		//X REGISTER
	private Byte Y;		//Y REGISTER
	private Byte SP;	//STACK POINTER
	private char PC;	//PROGRAM COUNTER
	private Byte SR;	//STATUS REGISTER
	OperativeUnit OU;
	
	//Costruttore privato
	private CPUState() {
		OU = OperativeUnit.getInstance();	//Collegamento con l'unit� operativa
		
		//Inizializzo variabili
		A = 0x00;
		X = 0x00;
		Y = 0x00;
		SP= (byte) 0xFD;
		PC = 0x0000;
		SR = 0x00;		
	}

	//Punto di ingresso globale all'istanza
	public static CPUState getInstance() {
		if(CPUState==null) {
			synchronized(State.class) {
				if(CPUState==null) {
					CPUState = new CPUState();
				}
			}
		}
		return CPUState;
	}

	public void refreshCPUState() {
		A = OU.getA_register();
		X = OU.getX_register();
		Y = OU.getY_register();
		SP = OU.getStack_pointer();
		PC = OU.getPC_register();
		SR = OU.getStatus_register();
		

	}

	public Byte getA() {
		return A;
	}

	public void setA(Byte a) {
		A = a;
	}

	public Byte getX() {
		return X;
	}

	public void setX(Byte x) {
		X = x;
	}

	public Byte getY() {
		return Y;
	}

	public void setY(Byte y) {
		Y = y;
	}

	public Byte getSP() {
		return SP;
	}

	public void setSP(Byte sP) {
		SP = sP;
	}

	public char getPC() {
		return PC;
	}

	public void setPC(char pC) {
		PC = pC;
	}

	public Byte getSR() {
		return SR;
	}

	public void setSR(Byte sR) {
		SR = sR;
	}
	
	
}
