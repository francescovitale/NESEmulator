package Emulator.ApplicationLogic.State;


public class State {
	

	//Attributi
	private Boolean Taken;
	private volatile static State State = null;			//Singleton
	CPUState CS;
	MemoryState MS;
	PPUState PS;
	PaletteMemoryState PM;
	VramMemoryState VM;
	OAMState OS;
	
	//Costruttore privato
	private State() {
		Taken = true;
		CS = CPUState.getInstance();
		MS = MemoryState.getInstance();
		PS = PPUState.getInstance();
		PM = PaletteMemoryState.getInstance();
		VM = VramMemoryState.getInstance();
		OS = OAMState.getInstance();
	}

	//Punto di ingresso globale all'istanza
	public static State getInstance() {
		if(State==null) {
			synchronized(State.class) {
				if(State==null) {
					State = new State();
				}
			}
		}
		return State;
	}
	
	//Aggiorna Stato
	public void refreshState() {
		CS.refreshCPUState();
		MS.refreshMemoryState();
		PS.refreshPPUStateRegisters();
		PM.refreshPaletteMemoryState();
		VM.refreshVRAMMemoryState();
		OS.refreshOAMState();
		Taken = true; //Lo stato è stato aggiornato
	}

	
	//Getter AND Setter
	public Boolean getTaken() {
		return Taken;
	}
	public void setTaken(Boolean taken) {
		Taken = taken;
	}

	public CPUState getCS() {
		return CS;
	}
 
	public void setCS(CPUState cS) {
		CS = cS;
	}

	public MemoryState getMS() {
		return MS;
	}

	public void setMS(MemoryState mS) {
		MS = mS;
	}

	public PPUState getPS() {
		return PS;
	}

	public void setPS(PPUState pS) {
		PS = pS;
	}

	public PaletteMemoryState getPM() {
		return PM;
	}

	public void setPM(PaletteMemoryState pM) {
		PM = pM;
	}

	public VramMemoryState getVM() {
		return VM;
	}

	public void setVM(VramMemoryState vM) {
		VM = vM;
	}

	public OAMState getOS() {
		return OS;
	}

	public void setOS(OAMState oS) {
		OS = oS;
	}

	
}
