package Emulator.ApplicationLogic.State;

import java.util.ArrayList;

import Emulator.ApplicationLogic.Program;

public class StateFacade {
	
	OperativeUnit OU;		//Collegamento con l'unità operativa
	Cartridge Crtg;			//Collegamento con il Cartridge
	State S;	 			//Collegamento con lo State
	
	public StateFacade() {
		OU = OperativeUnit.getIstance();	//Recupero l'istanza dell'unità operativa
		Crtg = Cartridge.getIstance();		//Recupero l'istanza della Cartridge
		S = State.getIstance();				//Recupero l'istenza di State
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
		synchronized(this.S.getIstance()){
			while(S.getTaken() == true) {
				try {
					//System.out.println("P) Mi blocco su "  + S.getIstance().toString());
					this.S.getIstance().wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			S.refreshState();
			this.S.getIstance().notify();
			//System.out.println("P) Mi sblocco" + S.getIstance().toString());
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