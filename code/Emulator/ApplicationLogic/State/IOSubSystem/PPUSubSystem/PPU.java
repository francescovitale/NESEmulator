package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;
import Emulator.ApplicationLogic.State.PPUState;

import java.util.ArrayList;
import java.util.Random;

import Emulator.ApplicationLogic.ByteManager;
import Emulator.ApplicationLogic.State.OperativeUnit;
import Emulator.ApplicationLogic.State.IOSubSystem.IOManager;



public class PPU {
	
	private ArrayList<String> NESPalette;
	private ArrayList<Pixel> returnedPixels;
	

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
		
		initializeNESPalette();
		returnedPixels = new ArrayList<Pixel>();
	};
	
	private void initializeNESPalette() {
		NESPalette = new ArrayList<String>();
		for(int i = 0; i <= (int)0x003F; i++)
			NESPalette.add("");
		NESPalette.set(0x00, "#545454");
		NESPalette.set(0x01,"#001e74");
		NESPalette.set(0x02,"#081090");
		NESPalette.set(0x03,"#300088");
		NESPalette.set(0x04,"#440064");
		NESPalette.set(0x05,"#5c0030");
		NESPalette.set(0x06,"#540400");
		NESPalette.set(0x07,"#3c1800");
		NESPalette.set(0x08,"#202a00");
		NESPalette.set(0x09,"#083a00");
		NESPalette.set(0x0A,"#004000");
		NESPalette.set(0x0B,"#003c00");
		NESPalette.set(0x0C,"#00323c");
		NESPalette.set(0x0D,"#000000");
		NESPalette.set(0x0E,"#000000");
		NESPalette.set(0x0F,"#000000");
		NESPalette.set(0x10,"#989698");
		NESPalette.set(0x11,"#084cc4");
		NESPalette.set(0x12,"#3032ec");
		NESPalette.set(0x13,"#5c1ee4");
		NESPalette.set(0x14,"#8814b0");
		NESPalette.set(0x15,"#a01464");
		NESPalette.set(0x16,"#982220");
		NESPalette.set(0x17,"#783c00");
		NESPalette.set(0x18,"#545a00");
		NESPalette.set(0x19,"#287200");
		NESPalette.set(0x1A,"#087c00");
		NESPalette.set(0x1B,"#007628");
		NESPalette.set(0x1C,"#006678");
		NESPalette.set(0x1D,"#000000");
		NESPalette.set(0x1E,"#000000");
		NESPalette.set(0x1F,"#000000");
		NESPalette.set(0x20,"#eceeec");
		NESPalette.set(0x21,"#4c9aec");
		NESPalette.set(0x22,"#787cec");
		NESPalette.set(0x23,"#b062ec");
		NESPalette.set(0x24,"#e454ec");
		NESPalette.set(0x25,"#ec58b4");
		NESPalette.set(0x26,"#ec6a64");
		NESPalette.set(0x27,"#d48820");
		NESPalette.set(0x28,"#a0aa00");
		NESPalette.set(0x29,"#74c400");
		NESPalette.set(0x2A,"#4cd020");
		NESPalette.set(0x2B,"#38cc6c");
		NESPalette.set(0x2C,"#38b4cc");
		NESPalette.set(0x2D,"#3c3c3c");
		NESPalette.set(0x2E,"#000000");
		NESPalette.set(0x2F,"#000000");
		NESPalette.set(0x30,"#eceeec");
		NESPalette.set(0x31,"#a8ccec");
		NESPalette.set(0x32,"#bcbcec");
		NESPalette.set(0x33,"#d4b2ec");
		NESPalette.set(0x34,"#ecaeec");
		NESPalette.set(0x35,"#ecaed4");
		NESPalette.set(0x36,"#ecb4b0");
		NESPalette.set(0x37,"#e4c490");
		NESPalette.set(0x38,"#ccd278");
		NESPalette.set(0x39,"#b4de78");
		NESPalette.set(0x3A,"#a8e290");
		NESPalette.set(0x3B,"#98e2b4");
		NESPalette.set(0x3C,"#a0d6e4");
		NESPalette.set(0x3D,"#a0a2a0");
		NESPalette.set(0x3E,"#000000");
		NESPalette.set(0x3F,"#000000");
	}

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
		
		/* Ãˆ necessario resettare i registri di comunicazione verso il processore */
		IOM.setPPUData((byte)0x00);
		IOM.setPPUAddress((byte)0x00);
		IOM.setPPUStatus((byte)0x00);
		IOM.setPPUMask((byte)0x00);
		IOM.setPPUControl((byte)0x00);
		
		/* Reset dei loopy registers */
		vram_addr = 0x0000;
		tram_addr = 0x0000;
		fine_x = 0x00;
		address_latch = 0x00;
		
		
	}
	
	public Byte PPURead(char addr)
	{
		/* La lettura verso la PPU puÃ² portare a leggere verso la pattern memory, la VRAM, oppure la palette memory */
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
					// Bisogna impostare una richiesta di interruzione. E' necessaria la chiamata alla CPU.
					OU = OperativeUnit.getInstance();
					OU.setNMIRequest(true);
				}
			}
		}
			
		Pixel temp = extractPixel();
		/*Random random = new Random();
		int value = random.nextInt();
		if(value % 2 == 0) temp.setRgb_info("#000000");*/
		
		if(scanline == 0) {
            if(cycles % 3 != 0)
            {
            	temp.setX_coord(temp.getX_coord()-1);
                returnedPixels.add(temp);
                //System.out.println("Coord:" + returnedPixels.get(returnedPixels.size()-1).getX_coord() + " " + returnedPixels.get(returnedPixels.size()-1).getY_coord());
                
            }
            else {
                PPUState PState = PPUState.getInstance();
                temp.setX_coord(temp.getX_coord()-1);
                returnedPixels.add(temp);
                PState.refreshPPUState(returnedPixels);
                //System.out.println("Coord:" + returnedPixels.get(returnedPixels.size()-1).getX_coord() + " " + returnedPixels.get(returnedPixels.size()-1).getY_coord());
                returnedPixels.clear();
            }
        }
        else
        {
            if((cycles+1) % 3 != 0)
            {
            	temp.setX_coord(temp.getX_coord()-1);
                returnedPixels.add(temp);
                
            }
            else {
                PPUState PState = PPUState.getInstance();
                temp.setX_coord(temp.getX_coord()-1);
                returnedPixels.add(temp);
                PState.refreshPPUState(returnedPixels);
               
                returnedPixels.clear();
            } // Potrebbe dipendere dalla velocità relativa... Concorrenza? Si, ma problemi di velocità se il thread in polling viene
            // sincronizzato..
        }
		
		cycles++;
		if(cycles >= 341) {
			cycles = 0;
			scanline++;
			if(scanline >= 261)
				scanline = -1;
		}
	}

	public char getVram_addr() {
		return vram_addr;
	}
	
	private Pixel extractPixel() {
		IOM = IOManager.getInstance();
		Byte bg_pattern_info = 0x00;  
		Byte bg_palette_info = 0x00; 
		Byte hex_color;
		
		Byte mask = IOM.getPPUMask();
		int render_background = ByteManager.extractBit(3,mask);
		
		if (render_background == 1)
		{
			/* bit_mux consentira' di estrarre solamente il singolo pixel da renderizzare */
			char bit_mux = (char)(0x8000 >> fine_x);

			Byte p0_pattern_info; // LSB
			Byte p1_pattern_info; // MSB
			boolean temp = (bg_shifter_pattern_lo & bit_mux) > 0;
			if(temp == true)
				p0_pattern_info = 0x01;
			else
				p0_pattern_info = 0x00;
			//System.out.println("p0_pattern_info: "+p0_pattern_info);
			temp = (bg_shifter_pattern_hi & bit_mux) > 0;
			if(temp == true)
				p1_pattern_info = 0x01;
			else
				p1_pattern_info = 0x00;
			//System.out.println("p1_pattern_info: "+p1_pattern_info);

			// Combine to form pixel index
			bg_pattern_info = (byte) ((p1_pattern_info << 1) | p0_pattern_info);
			//System.out.println("bg_pattern_info: "+bg_pattern_info);
			

			Byte p0_palette_info; // LSB
			Byte p1_palette_info; // MSB
			temp = (bg_shifter_attrib_lo & bit_mux) > 0;
			if(temp == true)
				p0_palette_info = 0x01;
			else
				p0_palette_info = 0x00;
			//System.out.println("p0_palette_info: "+p0_palette_info);
			temp = (bg_shifter_attrib_hi & bit_mux) > 0;
			if(temp == true)
				p1_palette_info = 0x01;
			else
				p1_palette_info = 0x00;
			//System.out.println("p1_palette_info: "+p1_palette_info);
			bg_palette_info = (byte) ((p1_palette_info << 1) | p0_palette_info);
			/*System.out.println("bg_palette_info: "+bg_palette_info);
			
			System.out.println("bg_palette_info << 2: "+ (bg_palette_info << 2));
			System.out.println("(bg_palette_info << 2)+bg_pattern_info: "+((bg_palette_info << 2)+bg_pattern_info));
			System.out.println("0x3F00 + (bg_palette_info << 2) + bg_pattern_info: "+ (0x3F00+(bg_palette_info << 2)+bg_pattern_info));*/
		}
		
		hex_color = getColourFromPaletteRam(bg_palette_info, bg_pattern_info); // Il colore tratto; servirà a indirizzare NESPalette
		Integer intTemp = Byte.toUnsignedInt(hex_color);
		String stringTemp = Integer.toHexString(intTemp);
		/*System.out.println("Colore palette: "+stringTemp);
		System.out.println("Colore in esadecimale: "+ NESPalette.get(hex_color));*/
		
		//System.out.println("Colore palette: "+hex_color);
		return new Pixel(cycles,scanline,stringTemp,NESPalette.get(hex_color));
	}
	
	
	Byte getColourFromPaletteRam(Byte Palette, Byte Pixel) {
		return PPURead((char) (0x3F00 + (Palette << 2) + Pixel));
		//return (byte)0x2c; // STUB
	}
	
	public Integer getScanline() {
		return scanline;
	}

	public void setScanline(Integer scanline) {
		this.scanline = scanline;
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
