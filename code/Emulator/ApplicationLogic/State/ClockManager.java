package Emulator.ApplicationLogic.State;

import Emulator.ApplicationLogic.Interpreter.ControlUnit;
import Emulator.ApplicationLogic.State.IOSubSystem.DMA;
import Emulator.ApplicationLogic.State.IOSubSystem.IOManager;
import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.PPU;

public class ClockManager {

	Integer ElapsedClockTicks;
	
	
 	private volatile static ClockManager CM = null;				//Singleton
 	private IOManager IO;
 	
 	private ClockManager() {
 		ElapsedClockTicks = 0;
 		IO = IOManager.getInstance();
 	}
 	
 	//Punto di ingresso globale all'istanza
 	public static ClockManager getInstance(){
 		if(CM==null) {
 			synchronized(ClockManager.class) {
 				if(CM==null) {
 					CM = new ClockManager();
 				}
 			}
 		}
 		return CM;
 	}
 	  
 	public Boolean clock() {
 		
 		//Clock della PPU
 		IO.PPUclock();
 		
 		//Se sono passati tre clock, toccherà alla CPU, a meno che non bisogni usare il DMA
 		if(ElapsedClockTicks % 3 == 0)
 		{
 			if(IO.getDma_transfer()) {
 				//Eseguo il Trasferimento tramite DMA
 				IO.DMAclock(ElapsedClockTicks);
 				ElapsedClockTicks++;
 				return false;
 			}
 			else {
 				//Faccio eseguire la CPU
 				ElapsedClockTicks++;
 				return true;
 			}
 		}
 		else {
 			ElapsedClockTicks++;
 			return false;
 		}
 		
 	}
 		
}
