package Emulator.Control;

public class ReturnedPPUState {
	
	private volatile static ReturnedPPUState PPUState = null;			//Singleton

	//Costruttore privato
	private ReturnedPPUState() {}

	//Punto di ingresso globale all'istanza
	public static ReturnedPPUState getIstance() {
		if(PPUState==null) {
			synchronized(ReturnedPPUState.class) {
				if(PPUState==null) {
					PPUState = new ReturnedPPUState();
				}
			}
		}
		return PPUState;
	}
	
}
