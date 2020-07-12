package Emulator.ApplicationLogic.State;

import java.util.ArrayList;

import Emulator.ApplicationLogic.Program;
import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.PPU;

public class StateFacade {
	
	OperativeUnit OU;		//Collegamento con l'unità operativa
	Cartridge Crtg;			//Collegamento con il Cartridge
	State S;	 			//Collegamento con lo State
	PPU P;
	
	public StateFacade() {
		P = PPU.getInstance();
		OU = OperativeUnit.getInstance();	//Recupero l'istanza dell'unità operativa
		Crtg = Cartridge.getInstance();		//Recupero l'istanza della Cartridge
		S = State.getInstance();				//Recupero l'istenza di State
	}
	
	//Inizializzo il programma in memoria
	public Boolean loadData(ArrayList<Byte> Programma) {
		return Crtg.loadData(Programma);
	}

	//Recupero l'istruzione i_esima dalla microRom
	public Instruction getMicrorom(int i) {
		return OU.getMicrorom(i);
	}
	
	//Fetch dell'opcode 
	public Byte fetch() {
		return OU.fetch();
	}
	
	//Incrementa il PC
	public void increasePC() {
		OU.increasePC();
	}
	
	//Modalità di indirizzamento
	public Boolean addressingMode(String AddrMode) {
		return OU.addressingMode(AddrMode);
	}
	
	//Esecuzione di un opcode
	public Boolean Execute(String Opcode) {
		return OU.Execute(Opcode);
	}
	
	//Aggiorno lo stato 
	public void refreshState() {
		S.refreshState();
	}
	
	//Recupero lo Stato
	public State getState(){
		return S;
	}
	
	//Resetto 
	public void reset() {
		OU.reset();
		P.reset();
	}

	public void resetStateTaken() {
		S.setTaken(false);
		
	}
	
	public Boolean clock() {
		ClockManager CM = ClockManager.getInstance();
		return CM.clock();
		
	}
	public Boolean getIRQRequest() {
		return OU.getIRQRequest();
	}
	public Boolean getNMIRequest() {
		return OU.getNMIRequest();
	}
	public void setNMIRequest(Boolean NMI) {
		OU.setNMIRequest(NMI);
	}
	public void setIRQRequest(Boolean IRQ) {
		OU.setNMIRequest(IRQ);
	}
	
	public void setFlag(String bit, boolean value) {
		OU.setFlag(bit, value);
	}
}