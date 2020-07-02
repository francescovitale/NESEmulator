package ApplicationLogic.State;

public class CPUState {
	//Attributi
	private volatile static CPUState CPUState = null;			//Singleton
	private Byte A;
	private Byte X;
	private Byte Y;
	private Byte SP;	//STACK POINTER
	private char PC;	//PROGRAM COUNTER
	private Byte SR;	//STATUS REGISTER
	OperativeUnit OU;
	
	//Costruttore privato
	private CPUState() {
		OU = OperativeUnit.getIstance();	//Collegamento con l'unità operativa
		
		//Inizializzo variabili
		A = 0x00;
		X = 0x00;
		Y = 0x00;
		SP= (byte) 0xFD;
		PC = 0x0000;
		SR = 0x00;		
	}

	//Punto di ingresso globale all'istanza
	public static CPUState getIstance() {
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
}
