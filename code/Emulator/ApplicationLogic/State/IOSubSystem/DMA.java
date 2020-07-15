package Emulator.ApplicationLogic.State.IOSubSystem;

import Emulator.ApplicationLogic.State.Bus;
import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.OAM;

public class DMA {
	private volatile static DMA  DirectMemory = null;		
	
	private OAM Oam;
	private Bus BUS;

	//Dati indirizzi e pagina da caricara dalla CPU alla OAM
	private Byte dma_data;
	private Byte dma_addr;
	private Byte dma_page;
	
	//Flag che permette di sincronizzare l'uso del DMA con i clock della CPU
	private Boolean dma_dummy;
	//Flag che indica che sta avvenendo il trasferimento dei dati tramite DMA
	private Boolean dma_transfer;
	 
	private DMA() {
		dma_data = 0X00;
		dma_addr = 0X00;
		dma_page = 0X00;
		
		dma_dummy = true;
		dma_transfer = false;
	
		Oam = OAM.getInstance();
		BUS = Bus.getInstance();
	}
	
	public static DMA getInstance(){
		if(DirectMemory==null) {
			synchronized(DMA.class) {
				if(DirectMemory==null) {
					DirectMemory= new DMA();
				}
			}
		}
		return DirectMemory;
	}
	
	//CLOCK
	public void clock(Integer ElapsedClockTicks) {
		if (dma_dummy){
			//Devo aspettare prima di iniziare il trasferimento
			if (ElapsedClockTicks % 2 == 1){
				dma_dummy = false;
			}
		}
		else {
			//Eseguo il trasferimento di dati
			if (ElapsedClockTicks % 2 == 0)
			{
				// Ad ogni ciclo leggo dal BUS
				dma_data = BUS.read((char)(Byte.toUnsignedInt(dma_page) << 8 | Byte.toUnsignedInt(dma_addr)));
			}
			else{
				// Nel ciclo dispari scrivo in  OAM
				writeOAM((char)(Byte.toUnsignedInt(dma_addr)), dma_data);
				
				// incremento il Byte meno significativo dell'address
				dma_addr = (byte)(Byte.toUnsignedInt(dma_addr) + 1);
				
				//Dopo aver scritto 256 volte l'address torna a 0 e mi posso fermare
				if (dma_addr == 0x00){
					
					//DEBUG
					//dumpOAM();
					
					dma_transfer = false;
					dma_dummy = true;
				}	
			}
		}
	}
	
	//GESTIONE OAM 
	public Byte readOAM(char addr) {
		return Oam.read(addr);
	}
	
	public void writeOAM(char addr, Byte data) {
		Oam.write(addr,data);
	}
	
	//DEBUG
	public void dumpOAM() {
		Oam.dumpOAM();
	}
	
	//GETTER AND SETTER
	public Byte getDma_data() {
		return dma_data;
	}

	public void setDma_data(Byte dma_data) {
		this.dma_data = dma_data;
	}

	public Byte getDma_addr() {
		return dma_addr;
	}

	public void setDma_addr(Byte dma_addr) {
		this.dma_addr = dma_addr;
	}

	public Byte getDma_page() {
		return dma_page;
	}

	public void setDma_page(Byte dma_page) {
		this.dma_page = dma_page;
	}

	public Boolean getDma_dummy() {
		return dma_dummy;
	}

	public void setDma_dummy(Boolean dma_dummy) {
		this.dma_dummy = dma_dummy;
	}

	public Boolean getDma_transfer() {
		return dma_transfer;
	}

	public void setDma_transfer(Boolean dma_transfer) {
		this.dma_transfer = dma_transfer;
	}

}
