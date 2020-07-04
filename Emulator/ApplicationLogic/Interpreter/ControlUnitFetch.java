package Emulator.ApplicationLogic.Interpreter;

import Emulator.ApplicationLogic.State.*;

public class ControlUnitFetch extends ControlUnitState {

	ControlUnit CU;
	StateFacade SF;
	private volatile static ControlUnitFetch ControlUnitFetch = null;		//Singleton
	
	
	protected ControlUnitFetch() {
		//Collegamento con il facade dello STATE
		SF = new StateFacade();
	}
	
	//Punto di ingresso globale all'istanza
	public static ControlUnitFetch getInstance(){
		if(ControlUnitFetch==null) {
			synchronized(ControlUnitFetch.class) {
				if(ControlUnitFetch==null) {
					ControlUnitFetch= new ControlUnitFetch();
				}
			}
		}
		return ControlUnitFetch;
	}
	

	protected void changeState(ControlUnit CU, ControlUnitState NewState) {
		CU.changeState(NewState);
	};
	
	public void execCycle() {
		CU = ControlUnit.getInstance();
		
		//Aggiorno lo stato da far visualizzare
		SF.refreshState();
		
		//Decremento i cicli finché non arrivo a 0
		boolean stop=false;
		while (stop == false)																	
			stop = clock();							
		
		/*try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		if(CU.getInstructionRegister().byteValue() == (byte)0xFF)								//Condizione di terminazione
			CU.setInstructionRegister((byte)0xF);
		else {	
			CU.setInstructionRegister(SF.fetch().byteValue());				//Fetcho l'istruzione e la salvo nell'IR
			SF.increasePC();
			/*DEBUG*/
			//System.out.println(Byte.toUnsignedInt(OU.getIstance().fetch()));									//Stampa DEBUG del codice operativo
			//ControlUnit.getInstance().setInstructionRegister((byte)0xD);
			
			changeState(CU, ControlUnitDecode.getInstance());
		}
	}
	
	private Boolean clock() {
		if (CU.getCycles()==0)					//Se ho finito i cicli
			return true;										
		else {
			CU.decreaseCycles();				//Altrimenti decrementa il numero di cicli
			return true;
		}
	}

}