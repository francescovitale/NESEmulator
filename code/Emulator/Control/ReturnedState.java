package Emulator.Control;

import Emulator.ApplicationLogic.State.CPUState;
import Emulator.ApplicationLogic.State.MemoryState;
import Emulator.ApplicationLogic.State.OAMState;
import Emulator.ApplicationLogic.State.PPUState;
import Emulator.ApplicationLogic.State.PaletteMemoryState;
import Emulator.ApplicationLogic.State.VramMemoryState;

public class ReturnedState {
	

	//Attributi
	private Boolean Taken;
	private volatile static ReturnedState State = null;			//Singleton
	private ReturnedCPUState CS;
	private ReturnedMemoryState MS;
	private ReturnedPPUState PS;
	private ReturnedPaletteMemoryState PM;
	private ReturnedVramMemoryState VM;
	private ReturnedOAMState OS;
	
	//Costruttore privato
	private ReturnedState() {
		Taken = false;
		CS = ReturnedCPUState.getIstance();
		MS = ReturnedMemoryState.getIstance();
		PS = ReturnedPPUState.getIstance();
		PM = ReturnedPaletteMemoryState.getInstance();
		VM = ReturnedVramMemoryState.getInstance();
		OS = ReturnedOAMState.getInstance();
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

	public void setMS(MemoryState mS) {
		MS.setRAM(mS.getRAM());
	}

	public ReturnedPPUState getPS() {
		return PS;
	}

	public void setPS(PPUState pS) {
		PS.setReturnedPixels(pS.getReturnedPixels());
		PS.setVram_addr(pS.getVram_addr());
		PS.setTram_addr(pS.getTram_addr());
		PS.setAddress_latch(pS.getAddress_latch());
		PS.setFine_x(pS.getFine_x());
		PS.setScanline(pS.getScanline());
		PS.setCycles(pS.getCycles());
		PS.setPPUStatus(pS.getPPUStatus());
		PS.setPPUAddress(pS.getPPUAddress());
		PS.setPPUControl(pS.getPPUControl());
		PS.setPPUData(pS.getPPUData());
		PS.setPPUMask(pS.getPPUMask());
	}

	public ReturnedPaletteMemoryState getPM() {
		return PM;
	}

	public void setPM(PaletteMemoryState pM) {
		PM.setPalette(pM.getPalette());
	}

	public ReturnedVramMemoryState getVM() {
		return VM;
	}

	public void setVM(VramMemoryState vM) {
		VM.setNameTable1(vM.getNameTable1());
		VM.setNameTable2(vM.getNameTable2());
	}

	public ReturnedOAMState getOS() {
		return OS;
	}

	public void setOS(OAMState oamState) {
		OS.setSprites(oamState.getSprites());
	}

	
}
