package Emulator.ApplicationLogic.State;

import java.util.ArrayList;

import Emulator.ApplicationLogic.Program;

public class StateFacade {
	
	OperativeUnit OU;		//Collegamento con l'unit� operativa
	Cartridge Crtg;			//Collegamento con il Cartridge
	State S;	 			//Collegamento con lo State
	
	public StateFacade() {
		OU = OperativeUnit.getInstance();	//Recupero l'istanza dell'unit� operativa
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
	
	//Modalit� di indirizzamento
	public Boolean addressingMode(String AddrMode) {
		return OU.addressingMode(AddrMode);
	}
	
	//Esecuzione di un opcode
	public Boolean Execute(String Opcode) {
		return OU.Execute(Opcode);
	}
	
	//Aggiorno lo stato 
	public void refreshState() {
		synchronized(this.S.getInstance()){
			while(S.getTaken() == true) {
				try {
					//System.out.println("P) Mi blocco su "  + S.getInstance().toString());
					this.S.getInstance().wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			S.refreshState();
			this.S.getInstance().notify();
			//System.out.println("P) Mi sblocco" + S.getInstance().toString());
		}


	}
	
	//Recupero lo Stato
	public State getState(){
		return S;
	}
	
	//Resetto 
	public void reset() {
		OU.reset();
	}

	public void resetStateTaken() {
		S.setTaken(false);
		
	}

}