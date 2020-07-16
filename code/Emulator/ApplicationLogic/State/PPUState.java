package Emulator.ApplicationLogic.State;

import java.util.ArrayList;

import Emulator.ApplicationLogic.State.IOSubSystem.IOManager;
import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.PPU;
import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.Pixel;

public class PPUState {
	
	private volatile static PPUState PPUState = null;			//Singleton
	private ArrayList<Pixel> returnedPixels;
 	private char vram_addr;
 	private char tram_addr;
 	private Byte fine_x; 	
 	private Integer address_latch;
 	private Integer scanline;
 	private Integer cycles; 
	private Byte PPUStatus;
	private Byte PPUMask;
	private Byte PPUControl;
	private Byte PPUAddress;
	private Byte PPUData;
 	
 	private PPU P;
 	private IOManager IOM;
 	
	//Costruttore privato
	private PPUState() {
		P = PPU.getInstance();
		IOM = IOManager.getInstance();
		
		returnedPixels = new ArrayList<Pixel>();
		//returnedPixels.add(new Pixel());
		
		vram_addr = 0x0000;
	 	tram_addr = 0x0000;
	 	fine_x = 0x00;
	 	address_latch = 0;
	 	scanline = 0;
		cycles = 0;
		PPUStatus = 0x00;
		PPUMask = 0x00;
		PPUControl = 0x00;
		PPUAddress = 0x00;
		PPUData = 0x00;
	}

	//Punto di ingresso globale all'istanza
	public static PPUState getInstance() {
		if(PPUState==null) {
			synchronized(PPUState.class) {
				if(PPUState==null) {
					PPUState = new PPUState();
				}
			}
		}
		return PPUState;
	}

	
	public void refreshPPUStateRegisters() {
		vram_addr = P.getVram_addr();
		tram_addr = P.getTram_addr();
		fine_x = P.getFine_x();
		address_latch = P.getAddress_latch();
		scanline = P.getScanline();
		cycles = P.getCycles();
		PPUStatus = IOM.getPPUStatus();
		PPUMask = IOM.getPPUMask();
		PPUControl = IOM.getPPUControl();
		PPUAddress = IOM.getPPUAddress();
		PPUData = IOM.getPPUData();
	}
	
	//Aggiorno lo stato della PPU
	public void refreshPPUState(ArrayList<Pixel> refreshedPixels) {
		returnedPixels = new ArrayList<Pixel>(refreshedPixels);
	}

	public ArrayList<Pixel> getReturnedPixels() {
		return returnedPixels;
	}

	public void setReturnedPixels(ArrayList<Pixel> returnedPixels) {
		this.returnedPixels = returnedPixels;
	}
	
	public char getVram_addr() {
		return vram_addr;
	}

	public void setVram_addr(char vram_addr) {
		this.vram_addr = vram_addr;
	}

	public char getTram_addr() {
		return tram_addr;
	}

	public void setTram_addr(char tram_addr) {
		this.tram_addr = tram_addr;
	}

	public Byte getFine_x() {
		return fine_x;
	}

	public void setFine_x(Byte fine_x) {
		this.fine_x = fine_x;
	}

	public Integer getAddress_latch() {
		return address_latch;
	}

	public void setAddress_latch(Integer address_latch) {
		this.address_latch = address_latch;
	}

	public Integer getScanline() {
		return scanline;
	}

	public void setScanline(Integer scanline) {
		this.scanline = scanline;
	}

	public Integer getCycles() {
		return cycles;
	}

	public void setCycles(Integer cycles) {
		this.cycles = cycles;
	}

	public Byte getPPUStatus() {
		return PPUStatus;
	}

	public void setPPUStatus(Byte pPUStatus) {
		PPUStatus = pPUStatus;
	}

	public Byte getPPUMask() {
		return PPUMask;
	}

	public void setPPUMask(Byte pPUMask) {
		PPUMask = pPUMask;
	}

	public Byte getPPUControl() {
		return PPUControl;
	}

	public void setPPUControl(Byte pPUControl) {
		PPUControl = pPUControl;
	}

	public Byte getPPUAddress() {
		return PPUAddress;
	}

	public void setPPUAddress(Byte pPUAddress) {
		PPUAddress = pPUAddress;
	}

	public Byte getPPUData() {
		return PPUData;
	}

	public void setPPUData(Byte pPUData) {
		PPUData = pPUData;
	}
	
	
}
