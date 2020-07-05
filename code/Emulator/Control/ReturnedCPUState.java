package Emulator.Control;

public class ReturnedCPUState {
	//Attributi
	private volatile static ReturnedCPUState CPUState = null;			//Singleton
	private Byte A;
	private Byte X;
	private Byte Y;
	private Byte SP;	//STACK POINTER
	private char PC;	//PROGRAM COUNTER
	private Byte SR;	//STATUS REGISTER
	
	//Costruttore privato
	private ReturnedCPUState() {
		
		//Inizializzo variabili
		A = 0x00;
		X = 0x00;
		Y = 0x00;
		SP= (byte) 0xFD;
		PC = 0x0000;
		SR = 0x00;		
	}

	//Punto di ingresso globale all'istanza
	public static ReturnedCPUState getIstance() {
		if(CPUState==null) {
			synchronized(ReturnedCPUState.class) {
				if(CPUState==null) {
					CPUState = new ReturnedCPUState();
				}
			}
		}
		return CPUState;
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
