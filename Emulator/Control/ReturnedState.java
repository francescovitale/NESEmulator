package Emulator.Control;

import Emulator.ApplicationLogic.State.CPUState;
import Emulator.ApplicationLogic.State.PPUState;

public class ReturnedState {
	

	//Attributi
	private Boolean Taken;
	private volatile static ReturnedState State = null;			//Singleton
	private ReturnedCPUState CS;
	private ReturnedMemoryState MS;
	private ReturnedPPUState PS;
	
	//Costruttore privato
	private ReturnedState() {
		Taken = false;
		CS = ReturnedCPUState.getIstance();
		MS = ReturnedMemoryState.getIstance();
		PS = ReturnedPPUState.getIstance();
	}

	//Punto di ingresso globale all'istanza
	public static ReturnedState getIstance() {
		if(State==null) {
			synchronized(ReturnedState.class) {
				if(State==null) {
					State = new ReturnedState();
				}
			}
		}
		return State;
	}

	
	//Getter AND Setter
	public Boolean getTaken() {
		return Taken;
	}
	public void setTaken(Boolean taken) {
		Taken = taken;
	}

	public void setCS(CPUState cs2) {
		
		CS.setA(cs2.getA());
		CS.setPC(cs2.getPC());
		CS.setSP(cs2.getSP());
		CS.setSR(cs2.getSR());
		CS.setX(cs2.getX());
		CS.setY(cs2.getY());
		
	}

	public ReturnedCPUState getCS() {
		return CS;
	}

	public void setCS(ReturnedCPUState cS) {
		CS = cS;
	}

	public ReturnedMemoryState getMS() {
		return MS;
	}

	public void setMS(ReturnedMemoryState mS) {
		MS = mS;
	}

	public ReturnedPPUState getPS() {
		return PS;
	}

	public void setPS(PPUState pS) {
		PS.setReturnedPixels(pS.getReturnedPixels());
	}

	
}
