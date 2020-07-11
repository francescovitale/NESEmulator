package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;

import java.util.ArrayList;

import Emulator.ApplicationLogic.State.Bus;
import Emulator.ApplicationLogic.State.ClockManager;
import Emulator.ApplicationLogic.State.IOSubSystem.IOManager;

public class PPU {

 	private volatile static PPU PPU = null;				//Singleton							//Bus

 	private char vram_addr;
 	private char tram_addr;
 	private char bg_shifter_pattern_lo;
	private char bg_shifter_pattern_hi;
 	private char bg_shifter_attrib_lo;
 	private char bg_shifter_attrib_hi;
	
 	private Byte PPU_data_buffer;
 	private Byte fine_x;
 	
 	private Byte bg_next_tile_id;
 	private Byte bg_next_tile_attr;
 	private Byte bg_next_tile_lsb;
 	private Byte bg_next_tile_msb;
 	
 	private Integer address_latch;
 	private Integer scanline;
 	private Integer cycles;
	
 	private IOManager IOM;
 	private ClockManager CM;
 	private PPURenderer PPUR;
 	
	protected PPU() {
		
		//IOM = IOManager.getInstance();
		//CM = ClockManager.getInstance();
		PPUR = PPURenderer.getInstance();
		
	 	vram_addr = 0x0000;
	 	tram_addr = 0x0000;
	 	bg_shifter_pattern_lo = 0x0000;
		bg_shifter_pattern_hi = 0x0000;
		bg_shifter_attrib_lo = 0x0000;
		bg_shifter_attrib_hi = 0x0000;
		
		PPU_data_buffer = 0x00;
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
		
	}
	
	public Byte PPURead(char addr)
	{
		return null;
	}

	//Per Connettere la PPU sul BUS principale
	public void PPUWrite(char addr, Byte data)
	{

	}

	public void clock() {}

	public void reset() {

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
