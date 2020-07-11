package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;


import Emulator.ApplicationLogic.ByteManager;
import Emulator.ApplicationLogic.State.OperativeUnit;
import Emulator.ApplicationLogic.State.IOSubSystem.IOManager;

public class PPU {

 	private volatile static PPU PPU = null;				//Singleton							//Bus

 	private char vram_addr;
 	private char tram_addr;
 	private char bg_shifter_pattern_lo;
	private char bg_shifter_pattern_hi;
 	private char bg_shifter_attrib_lo;
 	private char bg_shifter_attrib_hi;
 	private Byte fine_x;
 	
 	private Byte bg_next_tile_id;
 	private Byte bg_next_tile_attr;
 	private Byte bg_next_tile_lsb;
 	private Byte bg_next_tile_msb;
 	
 	private Integer address_latch;
 	private Integer scanline;
 	private Integer cycles;
	
 	private IOManager IOM;
 	private PPURenderer PPUR;
 	
	protected PPU() {
		
		
		PPUR = PPURenderer.getInstance();
		
	 	vram_addr = 0x0000;
	 	tram_addr = 0x0000;
	 	bg_shifter_pattern_lo = 0x0000;
		bg_shifter_pattern_hi = 0x0000;
		bg_shifter_attrib_lo = 0x0000;
		bg_shifter_attrib_hi = 0x0000;
		
	 	fine_x = 0x00;
	 	
		bg_next_tile_id = 0x00;
	 	bg_next_tile_attr = 0x00;
	 	bg_next_tile_lsb = 0x00;
	 	bg_next_tile_msb = 0x00;
	 	
	 	address_latch = 0;
		scanline = 0;
		cycles = 0;
	};
	
	//Punto di ingresso globale all'istanza
	public static PPU getInstance(){
		if(PPU==null) {
			synchronized(PPU.class) {
				if(PPU==null) {
					PPU= new PPU();
				}
			}
		}
		return PPU;
	}
	
	Integer getColourFromPaletteRam(Byte Palette, Byte Pixel) {
		return 0;
	}
	
	void refresh_vram() {
		vram_addr = tram_addr;
	}
	
	public void reset() {
		IOM = IOManager.getInstance();
		
		/* cicli e scanline azzerati */
		scanline = 0;
		cycles = 0;
		
		/* Variabili di comodo per immagazzinare i tile da renderizzare successivamente */
		bg_next_tile_id = 0x00;
		bg_next_tile_attr = 0x00;
		bg_next_tile_lsb = 0x00;
		bg_next_tile_msb = 0x00;
		
		/* Gli shift registers vanno azzerati */
		bg_shifter_pattern_lo = 0x0000;
		bg_shifter_pattern_hi = 0x0000;
		bg_shifter_attrib_lo = 0x0000;
		bg_shifter_attrib_hi = 0x0000;
		
		/* È necessario resettare i registri di comunicazione verso il processore */
		IOM.setPPUData((byte)0x00);
		IOM.setPPUStatus((byte)0x00);
		IOM.setPPUMask((byte)0x00);
		IOM.setPPUControl((byte)0x00);
		
		/* Reset dei loopy registers */
		vram_addr = 0x0000;
		tram_addr = 0x0000;
		fine_x = 0x00;
		address_latch = 0x00;
		/*
		System.out.println(scanline);
		System.out.println(cycles);
		System.out.println(bg_next_tile_id);
		System.out.println(bg_next_tile_attr);
		System.out.println(bg_next_tile_lsb);
		System.out.println(bg_next_tile_msb);
		System.out.println(bg_shifter_pattern_lo);
		System.out.println(bg_shifter_pattern_hi);
		System.out.println(bg_shifter_attrib_lo);
		System.out.println(bg_shifter_attrib_hi);
		System.out.println(IOM.getPPUData());
		System.out.println(IOM.getPPUStatus());
		System.out.println(IOM.getPPUMask());
		System.out.println(IOM.getPPUControl());
		System.out.println(vram_addr);
		System.out.println(tram_addr);
		System.out.println(fine_x);
		System.out.println(address_latch);
		*/
	}
	
	public Byte PPURead(char addr)
	{
		/* La lettura verso la PPU può portare a leggere verso la pattern memory, la VRAM, oppure la palette memory */
		return PPUR.PPURead(addr);
	}

	//Per Connettere la PPU sul BUS principale
	public void PPUWrite(char addr, Byte data)
	{
		PPUR.PPUWrite(addr, data);
	}

	public void clock() {
		if(scanline >= -1 && scanline <= 239) {
			PPUR.Render(scanline, cycles);
		}
			
		if(scanline == 240) {}
			
		if(scanline >= 241 && scanline <= 261) {
			if(scanline == 241 && cycles == 1) {
				Byte status = IOM.getPPUStatus();
				status = ByteManager.setBit(7,1,status);
				IOM.setPPUStatus(status);
				
				Byte control = IOM.getPPUControl();
				int enable_nmi = ByteManager.extractBit(7,control);
				OperativeUnit OU;
				if(enable_nmi == 1) {
					// Bisogna impostare una richiesta di interruzione. È necessaria la chiamata alla CPU.
					OU = OperativeUnit.getInstance();
					OU.setNMIRequest(true);
				}
			}
		}
			
		//extractPixel();
		
		cycles++;
		if(cycles >= 341) {
			cycles = 0;
			if(scanline >= 261)
				scanline = -1;
		}
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

	public Integer getCycles() {
		return cycles;
	}

	public void setCycles(Integer cycles) {
		this.cycles = cycles;
	}
	
	public char getBg_shifter_pattern_lo() {
		return bg_shifter_pattern_lo;
	}

	public void setBg_shifter_pattern_lo(char bg_shifter_pattern_lo) {
		this.bg_shifter_pattern_lo = bg_shifter_pattern_lo;
	}

	public char getBg_shifter_pattern_hi() {
		return bg_shifter_pattern_hi;
	}

	public void setBg_shifter_pattern_hi(char bg_shifter_pattern_hi) {
		this.bg_shifter_pattern_hi = bg_shifter_pattern_hi;
	}

	public char getBg_shifter_attrib_lo() {
		return bg_shifter_attrib_lo;
	}

	public void setBg_shifter_attrib_lo(char bg_shifter_attrib_lo) {
		this.bg_shifter_attrib_lo = bg_shifter_attrib_lo;
	}

	public char getBg_shifter_attrib_hi() {
		return bg_shifter_attrib_hi;
	}

	public void setBg_shifter_attrib_hi(char bg_shifter_attrib_hi) {
		this.bg_shifter_attrib_hi = bg_shifter_attrib_hi;
	}

	public Byte getBg_next_tile_id() {
		return bg_next_tile_id;
	}

	public void setBg_next_tile_id(Byte bg_next_tile_id) {
		this.bg_next_tile_id = bg_next_tile_id;
	}

	public Byte getBg_next_tile_attr() {
		return bg_next_tile_attr;
	}

	public void setBg_next_tile_attr(Byte bg_next_tile_attr) {
		this.bg_next_tile_attr = bg_next_tile_attr;
	}

	public Byte getBg_next_tile_lsb() {
		return bg_next_tile_lsb;
	}

	public void setBg_next_tile_lsb(Byte bg_next_tile_lsb) {
		this.bg_next_tile_lsb = bg_next_tile_lsb;
	}

	public Byte getBg_next_tile_msb() {
		return bg_next_tile_msb;
	}

	public void setBg_next_tile_msb(Byte bg_next_tile_msb) {
		this.bg_next_tile_msb = bg_next_tile_msb;
	}
}
