package ApplicationLogic.State;

public class PPUState {
	
	private volatile static PPUState PPUState = null;			//Singleton

	//Costruttore privato
	private PPUState() {}

	//Punto di ingresso globale all'istanza
	public static PPUState getIstance() {
		if(PPUState==null) {
			synchronized(PPUState.class) {
				if(PPUState==null) {
					PPUState = new PPUState();
				}
			}
		}
		return PPUState;
	}

	//Aggiorno lo stato della PPU
	public void refreshPPUState() {
		//DA IMPLEMENTARE
	}
	
}
