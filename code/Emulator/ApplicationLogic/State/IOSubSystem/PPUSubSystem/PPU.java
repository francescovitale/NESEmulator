package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;
import Emulator.ApplicationLogic.State.PPUState;

import java.util.ArrayList;
import java.util.Random;

import Emulator.ApplicationLogic.ByteManager;
import Emulator.ApplicationLogic.State.OperativeUnit;
import Emulator.ApplicationLogic.State.IOSubSystem.IOManager;



public class PPU {
	//MACRO
	private static final int VERTICAL_BLANK = 7;
	private static final int ENABLE_NMI = 7;
	private static final int RENDER_BACKGROUND = 3;
	private static final int RENDER_SPRITES = 4;
	private static final int RENDER_BACKGROUND_LEFT = 1;
	private static final int RENDER_SPRITES_LEFT = 2;
	private static final int SPRITE_ZERO_HIT = 6;
	private static final int SPRITE_OVERFLOW = 5;
	
	//MACRO foreground
	private static final int Y = 0;
	private static final int ID = 1;
	private static final int ATTRIBUTE = 2;
	private static final int X = 3;
	
	private ArrayList<String> NESPalette;
	private ArrayList<Pixel> returnedPixels;
	

 	private volatile static PPU PPU = null;				//Singleton							//Bus

 	private char vram_addr;
 	private char tram_addr;
 	
 	//Background
 	private char bg_shifter_pattern_lo;
	private char bg_shifter_pattern_hi;
 	private char bg_shifter_attrib_lo;
 	private char bg_shifter_attrib_hi;
 	private Byte fine_x;
 	
 	private Byte bg_next_tile_id;
 	private Byte bg_next_tile_attr;
 	private Byte bg_next_tile_lsb;
 	private Byte bg_next_tile_msb;
 	
 	//Foreground
	private ArrayList <Byte> spriteScanline;	
	private Integer sprite_count;
	private ArrayList <Byte> sprite_shifter_pattern_lo; 
	private ArrayList <Byte> sprite_shifter_pattern_hi; 
	// Sprite Zero Collision Flags
	private Boolean bSpriteZeroHitPossible;
	private Boolean bSpriteZeroBeingRendered;
 	
 	private Integer address_latch;
 	private Integer scanline;
 	private Integer cycles;
	
 	private IOManager IOM;
 	private PPURenderer PPUR;
 	private OAM Oam;
 	
 	private Byte OAMaddr;
 	
	protected PPU() {
		
		
		PPUR = PPURenderer.getInstance();
		Oam = OAM.getInstance();
		
		//Loopy registers
	 	vram_addr = 0x0000;
	 	tram_addr = 0x0000;
	 	//shifter register per il rendering background
	 	bg_shifter_pattern_lo = 0x0000;
		bg_shifter_pattern_hi = 0x0000;
		bg_shifter_attrib_lo = 0x0000;
		bg_shifter_attrib_hi = 0x0000;
		
		//scrolling fluido
	 	fine_x = 0x00;
	 	
	 	//Dati dei singoli Tile
		bg_next_tile_id = 0x00;
	 	bg_next_tile_attr = 0x00;
	 	bg_next_tile_lsb = 0x00;
	 	bg_next_tile_msb = 0x00;
	 	
	 	//Varibile di comodo
	 	address_latch = 0;
	 	
		//Posizione nello schermo
	 	scanline = 0;
		cycles = 0;
		
		//Indirizzo OAM
		OAMaddr = 0x00;
		
	 	//Foreground
		spriteScanline = new ArrayList <Byte>();
		for(int i = 0; i < 32; i++) {
			//Contiene gli 8 sprite visualizzabili a schermo
			spriteScanline.add((byte)0xFF);
		}
		sprite_count= 0;
		//shifter register per il rendering foreground
		sprite_shifter_pattern_lo = new ArrayList <Byte>();
		sprite_shifter_pattern_hi = new ArrayList <Byte>();
		for(int i = 0; i < 8; i++) {
			sprite_shifter_pattern_lo.add((byte)0x00);
			sprite_shifter_pattern_hi.add((byte)0x00);
		}
		// Sprite Zero Collision Flags
		bSpriteZeroHitPossible = false;
		bSpriteZeroBeingRendered = false;
		
		initializeNESPalette();
		returnedPixels = new ArrayList<Pixel>();
	};
	  
	
	private void initializeNESPalette() {
		NESPalette = new ArrayList<String>();
		for(int i = 0; i <= (int)0x003F; i++)
			NESPalette.add("");
		NESPalette.set(0x00,"#545454");
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
		
		//Foreground
		for(int i = 0; i < 32; i++) {
			//Contiene gli 8 sprite visualizzabili a schermo
			spriteScanline.set(i,(byte)0xFF);
		}
		sprite_count= 0;
		//shifter register per il rendering foreground
		for(int i = 0; i < 8; i++) {
			sprite_shifter_pattern_lo.set(i,(byte)0x00);
			sprite_shifter_pattern_hi.set(i,(byte)0x00);
		}
		// Sprite Zero Collision Flags
		bSpriteZeroHitPossible = false;
		bSpriteZeroBeingRendered = false;
		
		
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
		if(scanline >= -1 && scanline < 240) {
			//Vertical blank period - Render dello schermo
			PPUR.Render(scanline, cycles);
		}
			
		if(scanline == 240) {
			// Post Render Scanline, non bisogna fare nulla
		}
			
		if(scanline >= 241 && scanline < 261) {
			//Superato lo schermo
			if(scanline == 241 && cycles == 1) {
				//Setto ad 1 il vertical blank
				Byte status = IOM.getPPUStatus();
				status = ByteManager.setBit(VERTICAL_BLANK,1,status);
				IOM.setPPUStatus(status);
				
				Byte control = IOM.getPPUControl();
				int enable_nmi = ByteManager.extractBit(ENABLE_NMI,control);
				OperativeUnit OU;
				if(enable_nmi == 1) {
					// Bisogna impostare una richiesta di interruzione. E' necessaria la chiamata alla CPU.
					OU = OperativeUnit.getInstance();
					OU.setNMIRequest(true);
				}
			}
		}
		
		//Estraggo il pixel da stampare a schermo 
		Pixel temp = extractPixel();
		
		if(scanline == 0) {
            if(cycles % 3 != 0)
            {
            	temp.setX_coord(temp.getX_coord()-1);
                returnedPixels.add(temp);
                //DEBUG
                //System.out.println("Coord:" + returnedPixels.get(returnedPixels.size()-1).getX_coord() + " " + returnedPixels.get(returnedPixels.size()-1).getY_coord());
                
            }
            else {
                PPUState PState = PPUState.getInstance();
                temp.setX_coord(temp.getX_coord()-1);
                returnedPixels.add(temp);
                PState.refreshPPUState(returnedPixels);
                //DEBUG
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
		Byte bg_pattern_info = 0x00; //2-BIT per il pixel da renderizzare
		Byte bg_palette_info = 0x00; //3-BIT per il colore della palette
		Byte hex_color = 0x00;
		
		Byte mask = IOM.getPPUMask();
		int render_background = ByteManager.extractBit(RENDER_BACKGROUND,mask);
		// Background =============================================================
		if (render_background == 1)
		{
			/* bit_mux consentira' di estrarre solamente il singolo pixel da renderizzare */
			char bit_mux = (char)(0x8000 >> Byte.toUnsignedInt(fine_x));
			Byte p0_pattern_info; // LSB
			Byte p1_pattern_info; // MSB
			boolean temp = (bg_shifter_pattern_lo & bit_mux) > 0;
			if(temp == true)
				p0_pattern_info = 0x01;
			else
				p0_pattern_info = 0x00;
			
			//DEBUG
			//System.out.println("p0_pattern_info: "+p0_pattern_info);
			
			temp = (bg_shifter_pattern_hi & bit_mux) > 0;
			if(temp == true)
				p1_pattern_info = 0x01;
			else
				p1_pattern_info = 0x00;
			
			//DEBUG
			//System.out.println("p1_pattern_info: "+p1_pattern_info);

			// Combine to form pixel index
			bg_pattern_info = (byte) ((Byte.toUnsignedInt(p1_pattern_info) << 1) | Byte.toUnsignedInt(p0_pattern_info));
			
			//DEBUG
			//System.out.println("bg_pattern_info: "+bg_pattern_info);
			

			Byte p0_palette_info; // LSB
			Byte p1_palette_info; // MSB
			temp = (bg_shifter_attrib_lo & bit_mux) > 0;
			if(temp == true)
				p0_palette_info = 0x01;
			else
				p0_palette_info = 0x00;
			
			//DEBUG
			//System.out.println("p0_palette_info: "+p0_palette_info);
			
			temp = (bg_shifter_attrib_hi & bit_mux) > 0;
			if(temp == true)
				p1_palette_info = 0x01;
			else
				p1_palette_info = 0x00;
			
			//DEBUG
			//System.out.println("p1_palette_info: "+p1_palette_info);
			
			bg_palette_info = (byte) ((Byte.toUnsignedInt(p1_palette_info)<< 1) | Byte.toUnsignedInt(p0_palette_info));
			
			//DEBUG
			/*System.out.println("bg_palette_info: "+bg_palette_info);
			System.out.println("bg_palette_info << 2: "+ (bg_palette_info << 2));
			System.out.println("(bg_palette_info << 2)+bg_pattern_info: "+((bg_palette_info << 2)+bg_pattern_info));
			System.out.println("0x3F00 + (bg_palette_info << 2) + bg_pattern_info: "+ (0x3F00+(bg_palette_info << 2)+bg_pattern_info));*/
		}
		

		// Foreground =============================================================
		Byte fg_pattern = 0x00;  		 //2-BIT per il pixel da renderizzare
		Byte fg_palette = 0x00; 	 //3-BIT per il colore della palette
		Byte fg_priority = 0x00;	 //gestione della Priorità
		
		int render_sprites = ByteManager.extractBit(RENDER_SPRITES,mask);
		if (render_sprites == 1)
		{
			bSpriteZeroBeingRendered = false;
			
			//debug
			//System.out.println("Sprite count:" + sprite_count);
			
			for (int i = 0; i < sprite_count; i+=4)
			{
				// Se lo sprite collide con lo scanline
				if (Byte.toUnsignedInt(spriteScanline.get(i + X)) == 0) 
				{
					Byte fg_pattern_lo;
					Byte fg_pattern_hi;
					// Determino il valore dei pixel
					if((Byte.toUnsignedInt(sprite_shifter_pattern_lo.get(i/4)) & 0x80) != 0) {
						fg_pattern_lo = 0x01;
					}
					else {
						fg_pattern_lo = 0x00;
					}
					
					if((Byte.toUnsignedInt(sprite_shifter_pattern_hi.get(i/4)) & 0x80) != 0) {
						fg_pattern_hi = 0x01;
					}
					else {
						fg_pattern_hi = 0x00;
					}
					
					fg_pattern = (byte) ((Byte.toUnsignedInt(fg_pattern_hi)<< 1) | Byte.toUnsignedInt(fg_pattern_lo));

					// Estraggo la palette dai primi 2 bits.
					fg_palette = (byte) ((Byte.toUnsignedInt(spriteScanline.get(i + ATTRIBUTE)) & 0x03) + 0x04);
					if((Byte.toUnsignedInt(spriteScanline.get(i + ATTRIBUTE)) & 0x20) == 0) {
						fg_priority = 0x01;
					}
					else {
						fg_priority = 0x00;
					}
					

					// Se il pattern non è trasparente lo renderizziamo 
					if (fg_pattern != 0)
					{
						if (i == 0) // Is this sprite zero?
						{
							bSpriteZeroBeingRendered = true;
						}
						break;
					}				
				}
			}		
		}

		// Dobbiamo combinare le informazioni del background con quelle del foreground.
		Byte pattern = 0x00;    // The FINAL Pattern...
		Byte palette = 0x00; 	// The FINAL Palette...
		
		//DEBUG
		//System.out.println("bg_pattern: " + Byte.toUnsignedInt(bg_pattern_info) + "fg_pattern: " + Byte.toUnsignedInt(fg_pattern));
		
		if (Byte.toUnsignedInt(bg_pattern_info) == 0 && Byte.toUnsignedInt(fg_pattern) == 0)
		{
			// The background pixel is transparent
			// The foreground pixel is transparent
			// No winner, draw "background" colour
			pattern = 0x00;
			palette = 0x00;
		}
		else if (Byte.toUnsignedInt(bg_pattern_info) == 0 && Byte.toUnsignedInt(fg_pattern) > 0)
		{
			// The background pixel is transparent
			// The foreground pixel is visible
			// Foreground wins!
			pattern = fg_pattern;
			palette = fg_palette;
		}
		else if (Byte.toUnsignedInt(bg_pattern_info) > 0 && Byte.toUnsignedInt(fg_pattern) == 0)
		{
			// The background pixel is visible
			// The foreground pixel is transparent
			// Background wins!
			pattern = bg_pattern_info;
			palette = bg_palette_info;
		}
		else if (Byte.toUnsignedInt(bg_pattern_info) > 0 && Byte.toUnsignedInt(fg_pattern) > 0)
		{
			// The background pixel is visible
			// The foreground pixel is visible
			// Hmmm...
			if (Byte.toUnsignedInt(fg_priority) == 1)
			{
				// Foreground cheats its way to victory!
				pattern = fg_pattern;
				palette = fg_palette;
			}
			else
			{
				// Background is considered more important!
				pattern = bg_pattern_info;
				palette = bg_palette_info;
			}

			// Sprite Zero Hit detection
			if (bSpriteZeroHitPossible && bSpriteZeroBeingRendered)
			{
				// Sprite zero is a collision between foreground and background
				// so they must both be enabled
				if ((render_background & render_sprites)==1)
				{
					// The left edge of the screen has specific switches to control
					// its appearance. This is used to smooth inconsistencies when
					// scrolling (since sprites x coord must be >= 0)
					int render_background_left = ByteManager.extractBit(RENDER_BACKGROUND_LEFT,mask); 
					int render_sprites_left = ByteManager.extractBit(RENDER_SPRITES_LEFT,mask); 
					
					if ((render_background_left | render_sprites_left) == 0)
					{
						if (cycles >= 9 && cycles < 258)
						{
							IOM.setPPUStatus(ByteManager.setBit(SPRITE_ZERO_HIT, 1, IOM.getPPUStatus()));
						}
					}
					else
					{
						if (cycles >= 1 && cycles < 258)
						{
							IOM.setPPUStatus(ByteManager.setBit(SPRITE_ZERO_HIT, 1, IOM.getPPUStatus()));
						}
					}
				}
			}
		}
		
		hex_color = getColourFromPaletteRam(palette, pattern); // Il colore tratto servirà a indirizzare NESPalette
		Integer intTemp = Byte.toUnsignedInt(hex_color);
		String stringTemp = Integer.toHexString(intTemp);
		
		//DEBUG
		//System.out.println("Colore palette: "+stringTemp);
		//System.out.println("Colore in esadecimale: "+ NESPalette.get(hex_color));
		//System.out.println("Colore palette: "+hex_color);
		
		return new Pixel(cycles,scanline,stringTemp,NESPalette.get(hex_color));
	}
	
	
	Byte getColourFromPaletteRam(Byte Palette, Byte Pixel) {
		return PPURead((char)(0x3F00 + ((Byte.toUnsignedInt(Palette) << 2) + Byte.toUnsignedInt(Pixel))));
		
		//DEBUG
		//return (byte)0x2c; // STUB
	}
	
	//GESTIONE OAM 
	public Byte readOAM(char addr) {
		return Oam.read(addr);
	}
	
	public void writeOAM(char addr, Byte data) {
		Oam.write(addr,data);
	}
	
	public Byte getOAMaddr() {
		return OAMaddr;
	}

	public void setOAMaddr(Byte oAMaddr) {
		OAMaddr = oAMaddr;
	}
	
	//Foreground
	public void decrease_sprite_scanline_x(int i) {
		spriteScanline.set(i + X, (byte)(Byte.toUnsignedInt(spriteScanline.get(i + X)) - 1));
	}
	
	public void shift_sprite_shifters(int i) {
		Byte value_lo = (byte) (Byte.toUnsignedInt(sprite_shifter_pattern_lo.get(i)) << 1);
		sprite_shifter_pattern_lo.set(i, value_lo);
		Byte value_hi = (byte) (Byte.toUnsignedInt(sprite_shifter_pattern_hi.get(i)) << 1);
		sprite_shifter_pattern_hi.set(i, value_hi);
	}
	
	public void clearSprite() {
		IOM.setPPUStatus(ByteManager.setBit(SPRITE_OVERFLOW, 0, IOM.getPPUStatus()));
		IOM.setPPUStatus(ByteManager.setBit(SPRITE_ZERO_HIT, 0, IOM.getPPUStatus()));
		for(int i = 0; i < 8; i++) {
			sprite_shifter_pattern_lo.set(i,(byte)0x00);
			sprite_shifter_pattern_hi.set(i,(byte)0x00);
		}
	}
	
	//GETTER AND SETTER 
	
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
	
	public ArrayList<Byte> getSpriteScanline() {
		return spriteScanline;
	}

	public void setSpriteScanline(ArrayList<Byte> spriteScanline) {
		this.spriteScanline = spriteScanline;
	}

	public Integer getSprite_count() {
		return sprite_count;
	}

	public void setSprite_count(Integer sprite_count) {
		this.sprite_count = sprite_count;
	}

	public ArrayList<Byte> getSprite_shifter_pattern_lo() {
		return sprite_shifter_pattern_lo;
	}

	public void setSprite_shifter_pattern_lo(ArrayList<Byte> sprite_shifter_pattern_lo) {
		this.sprite_shifter_pattern_lo = sprite_shifter_pattern_lo;
	}

	public ArrayList<Byte> getSprite_shifter_pattern_hi() {
		return sprite_shifter_pattern_hi;
	}

	public void setSprite_shifter_pattern_hi(ArrayList<Byte> sprite_shifter_pattern_hi) {
		this.sprite_shifter_pattern_hi = sprite_shifter_pattern_hi;
	}

	public Boolean getbSpriteZeroHitPossible() {
		return bSpriteZeroHitPossible;
	}

	public void setbSpriteZeroHitPossible(Boolean bSpriteZeroHitPossible) {
		this.bSpriteZeroHitPossible = bSpriteZeroHitPossible;
	}

	public Boolean getbSpriteZeroBeingRendered() {
		return bSpriteZeroBeingRendered;
	}

	public void setbSpriteZeroBeingRendered(Boolean bSpriteZeroBeingRendered) {
		this.bSpriteZeroBeingRendered = bSpriteZeroBeingRendered;
	}

}
