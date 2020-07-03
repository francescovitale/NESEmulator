package ApplicationLogic.State;

public class State {
	

	//Attributi
	private Boolean Taken;
	private volatile static State State = null;			//Singleton
	CPUState CS;
	MemoryState MS;
	PPUState PS;
	
	//Costruttore privato
	private State() {
		Taken = false;
		CS = CPUState.getIstance();
		MS = MemoryState.getIstance();
		PS = PPUState.getIstance();
	}

	//Punto di ingresso globale all'istanza
	public static State getIstance() {
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
		PS.refreshPPUState();
		
		Taken = true; //Lo stato � stato aggiornato
	}

	
	//Getter AND Setter
	public Boolean getTaken() {
		return Taken;
	}
	public void setTaken(Boolean taken) {
		Taken = taken;
	}

	
}