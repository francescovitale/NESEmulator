package Emulator.ApplicationLogic.State;

import Emulator.ApplicationLogic.Interpreter.ControlUnit;

public class ClockManager {

	Integer ElapsedClockTicks;
	
 	private volatile static ClockManager CM = null;				//Singleton
 	private PPU P;
 	
 	private ClockManager() {
 		ElapsedClockTicks = 0;
 		P = PPU.getInstance();
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
 		P.clock();
 		if(ElapsedClockTicks % 3 == 0)
 		{
 			
 			ElapsedClockTicks++;
 			return true;
 		}
 		else {
 			ElapsedClockTicks++;
 			return false;
 		}
 		
 	}
 		
}
