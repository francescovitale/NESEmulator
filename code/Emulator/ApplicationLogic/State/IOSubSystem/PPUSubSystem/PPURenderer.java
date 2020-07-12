package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;

import Emulator.ApplicationLogic.ByteManager;
import Emulator.ApplicationLogic.State.ClockManager;
import Emulator.ApplicationLogic.State.IOSubSystem.IOManager;


public class PPURenderer {
	
	//MACRO
	private static final int RENDER_BACKGROUND = 3;
	private static final int RENDER_SPRITES = 4;
	private static final int NAMETABLE_X = 10;
	private static final int NAMETABLE_Y = 11;
	private static final int VERTICAL_BLANK = 7;
	private static final int PATTERN_BACKGROUND = 4;
	
	private volatile static PPURenderer PPURenderer = null;
	
	private PPU P;
	private PPUBus Bus;
	private IOManager IOM;
	
	protected PPURenderer() {
		//P = PPU.getInstance();
		//IOM = IOManager.getInstance();
		Bus = PPUBus.getInstance();
	};
	
	//Punto di ingresso globale all'istanza
	public static PPURenderer getInstance(){
		if(PPURenderer==null) {
			synchronized(PPURenderer.class) {
				if(PPURenderer==null) {
					PPURenderer= new PPURenderer();
				}
			}
		}
		return PPURenderer;
	}
	
	
	public void incrementScrollX() {
		//Collego al IOManager e alla PPU
		IOM = IOManager.getInstance();
		P = PPU.getInstance();
		
		//Verifico se devo renderizzare
		Byte mask = IOM.getPPUMask();
		int render_background = ByteManager.extractBit(RENDER_BACKGROUND, mask);
		int render_sprites = ByteManager.extractBit(RENDER_SPRITES, mask);
		if(render_background == 1 || render_sprites == 1) {
			
			char vram = P.getVram_addr();											//Prelevo vram_addr
			char coarse_x = (char)(vram & 0x001F);									//coarse_x sono i primi 5 bit di vram
			
			if(coarse_x == 31) {
				vram = (char)(vram & 0xFFE0);  									    //vram.coarse_x = 0
				int nametable_x = ByteManager.extractCharBit(NAMETABLE_X, vram);	//prendo nametable_x
				nametable_x = ((nametable_x == 0) ? 1 : 0);							//Inverto nametable_x
				ByteManager.setCharBit(NAMETABLE_X, nametable_x, vram);				//reinserisco nametable_x
			}
			else {
				coarse_x++;															//Incremento coarse_x
				vram = (char)((vram & 0xFFE0) | coarse_x);							//Inserisco il nuovo coarse_x
			}
			
			P.setVram_addr(vram);													//Modifico vram_addr con le modifiche fatte
		}
		
	}
	
	public void incrementScrollY() {
		//Collego al IOManager e alla PPU
		IOM = IOManager.getInstance();
		P = PPU.getInstance();
		
		//Verifico se devo renderizzare
		Byte mask = IOM.getPPUMask();
		int render_background = ByteManager.extractBit(RENDER_BACKGROUND, mask);
		int render_sprites = ByteManager.extractBit(RENDER_SPRITES, mask);
		if(render_background == 1 || render_sprites == 1) {
			
			char vram = P.getVram_addr();											//Prelevo vram_addr
			char fine_y = (char)((vram & 0x7000) >> 12);							//Estraggo fine_y
			
			if(fine_y < 7) {
				fine_y ++;															//Incremento fine_y
				vram = (char)((vram & 0x8FFF) | (fine_y << 12));					//Riassegno il nuovo valore di fine_y in vram
			}
			else {
				vram = (char)(vram & 0x8FFF);										//fine_y = 0
				char coarse_y = (char)((vram & 0x03E0) >> 5);						//Prendo il valore di coarse_y
				
				if(coarse_y == 29) {
					
					vram = (char)(vram & 0xFC1F);										//coarse_y = 0
					int nametable_y = ByteManager.extractCharBit(NAMETABLE_Y, vram);	//prendo nametable_y
					nametable_y = ((nametable_y == 0) ? 1 : 0);							//Inverto nametable_y
					ByteManager.setCharBit(NAMETABLE_Y, nametable_y, vram);				//reinserisco nametable_y
					
				}
				else if(coarse_y == 31) {
					vram = (char)(vram & 0xFC1F);									//coarse_y = 0
				}
				else {
					coarse_y++;														//incremento coarse_y
					vram = (char)((vram & 0xFC1F) | (coarse_y << 5));				//Riassegno il nuovo valore di coarse_y in vram
				}
			}
			
			P.setVram_addr(vram);													//Modifico vram_addr con le modifiche fatte
		}		
	}
	
	public void TransferAddressX() {
		//Collego al IOManager e alla PPU
		IOM = IOManager.getInstance();
		P = PPU.getInstance();
		
		//Verifico se devo renderizzare
		Byte mask = IOM.getPPUMask();
		int render_background = ByteManager.extractBit(RENDER_BACKGROUND, mask);
		int render_sprites = ByteManager.extractBit(RENDER_SPRITES, mask);
		if(render_background == 1 || render_sprites == 1) {
			char vram = P.getVram_addr();											//Prelevo vram_addr
			char tram = P.getTram_addr();											//Prelevo tram_addr
			
			int nametable_x = ByteManager.extractCharBit(NAMETABLE_X, tram);		//prendo nametable_x da tram
			ByteManager.setCharBit(NAMETABLE_X, nametable_x, vram);					//Inserisco nametable_x in vram
			
			char coarse_x = (char)(tram & 0x001F);									//prelevo coarse_x dalla tram
			vram = (char)((vram & 0xFFE0) | coarse_x);								//Inserisco il nuovo coarse_x in vram
			
			P.setVram_addr(vram);													//Modifico vram_addr con le modifiche fatte
		}
	}
	
	public void TransferAddressY() {
		//Collego al IOManager e alla PPU
		IOM = IOManager.getInstance();
		P = PPU.getInstance();
		
		//Verifico se devo renderizzare
		Byte mask = IOM.getPPUMask();
		int render_background = ByteManager.extractBit(RENDER_BACKGROUND, mask);
		int render_sprites = ByteManager.extractBit(RENDER_SPRITES, mask);
		if(render_background == 1 || render_sprites == 1) {
			char vram = P.getVram_addr();											//Prelevo vram_addr
			char tram = P.getTram_addr();											//Prelevo tram_addr
			
			char fine_y = (char)((tram & 0x7000) >> 12);							//Estraggo fine_y dalla tram
			vram = (char)((vram & 0x8FFF) | (fine_y << 12));						//Riassegno il nuovo valore di fine_y in vram
			
			int nametable_y = ByteManager.extractCharBit(NAMETABLE_Y, tram);		//prendo nametable_y dalla tram
			ByteManager.setCharBit(NAMETABLE_Y, nametable_y, vram);					//reinserisco nametable_y in vram
			
			char coarse_y = (char)((tram & 0x03E0) >> 5);							//Prelevo coarse_y dalla tram
			vram = (char)((vram & 0xFC1F) | (coarse_y << 5));						//Riassegno il nuovo valore di coarse_y in vram
			
			P.setVram_addr(vram);													//Modifico vram_addr con le modifiche fatte
		}		
	}
	
	public void LoadBackgroundShifters(){
		//Collego alla PPU
		P = PPU.getInstance();
		
		//Prelevo i registri utili 
		char bg_shifter_pattern_lo = P.getBg_shifter_pattern_lo();
		char bg_shifter_pattern_hi = P.getBg_shifter_pattern_hi();
		Byte bg_next_tile_lsb = P.getBg_next_tile_lsb();
		Byte bg_next_tile_msb = P.getBg_next_tile_msb();
		//Modifico i valori		
		P.setBg_shifter_pattern_lo((char)((bg_shifter_pattern_lo & 0xFF00) | bg_next_tile_lsb));
		P.setBg_shifter_pattern_hi((char)((bg_shifter_pattern_hi & 0xFF00) | bg_next_tile_msb));
		
		//Prelevo i registri utili 
		char bg_shifter_attrib_lo = P.getBg_shifter_attrib_lo();
		char bg_shifter_attrib_hi = P.getBg_shifter_attrib_hi();
		Byte bg_next_tile_attr = P.getBg_next_tile_attr();
		//Modifico i valori		
		P.setBg_shifter_attrib_lo((char) ((bg_shifter_attrib_lo & 0xFF00) | ((bg_next_tile_attr & 0x01) != 0 ? 0xFF : 0x00)));
		P.setBg_shifter_attrib_hi((char) ((bg_shifter_attrib_hi & 0xFF00) | ((bg_next_tile_attr & 0x10) != 0 ? 0xFF : 0x00)));
	}
	
	public void UpdateShifters() {
		//Collego all'IOManager e alla PPU
		IOM = IOManager.getInstance();
		P = PPU.getInstance();
		
		//Verifico se devo renderizzare
		Byte mask = IOM.getPPUMask();
		int render_background = ByteManager.extractBit(RENDER_BACKGROUND, mask);
		if(render_background == 1) {
			//Shifto tile backgorund della riga
			char bg_shifter_pattern_lo = P.getBg_shifter_pattern_lo();				//Prelevo
			bg_shifter_pattern_lo <<= 1;											//shifto
			P.setBg_shifter_pattern_lo(bg_shifter_pattern_lo);						//Sostiruisco
			
			char bg_shifter_pattern_hi = P.getBg_shifter_pattern_hi();				//Prelevo
			bg_shifter_pattern_hi <<= 1;											//shifto
			P.setBg_shifter_pattern_hi(bg_shifter_pattern_hi);						//Sostiruisco
			
			//Shifto palette attributes 
			char bg_shifter_attrib_lo = P.getBg_shifter_attrib_lo();				//Prelevo
			bg_shifter_attrib_lo <<= 1;												//shifto
			P.setBg_shifter_attrib_lo(bg_shifter_attrib_lo);						//Sostiruisco
			
			char bg_shifter_attrib_hi = P.getBg_shifter_attrib_hi();				//Prelevo
			bg_shifter_attrib_hi <<= 1;												//shifto
			P.setBg_shifter_attrib_hi(bg_shifter_attrib_hi);						//Sostiruisco
		}
	}
	
	public void Render(Integer Scanline, Integer Cycle) {
		//Collego all'IOManager e alla PPU
		IOM = IOManager.getInstance();
		P = PPU.getInstance();
		
		Integer cycle = P.getCycles();
		Integer scanline = P.getScanline();
		
		if(cycle == 0 && scanline == 0) {
			//"Odd frame" scarto il cycle
			P.setCycles(1);
		}
		
		if(cycle == 1 && scanline == 1) {
			// Inizia effettivamente un nuovo ciclo quindi pulisco il vertical blank flag
			Byte status = IOM.getPPUStatus();
			status = ByteManager.setBit(VERTICAL_BLANK, 0, status);
			IOM.setPPUStatus(status);
		}
		
		if ((cycle >= 2 && cycle < 258) || (cycle >= 321 && cycle < 338)){
			UpdateShifters();														//Aggiorno gli shift register
			char vram = P.getVram_addr();											//Prelevo la vram
			
			switch ((cycle - 1) % 8){ 
			case 0:{
				LoadBackgroundShifters();											//Carico i tile del background sugli shifter 
				Byte bg_next_tile_id = P.PPURead((char)(0x2000 | (vram & 0x0FFF)));	//Fetch del prossimo background tile ID
				P.setBg_next_tile_id(bg_next_tile_id);								//Lo inserisco nella PPU
				break; 
			}
			case 2:{
				int nametable_y = ByteManager.extractCharBit(NAMETABLE_Y, vram);			//prendo nametable_y 
				int nametable_x = ByteManager.extractCharBit(NAMETABLE_X, vram);			//prendo nametable_x
				char coarse_y = (char)((vram & 0x03E0) >> 5);								//Prelevo coarse_y 
				char coarse_x = (char)(vram & 0x001F);										//Prelevo coarse_x
				
				byte bg_next_tile_attrib = P.PPURead((char) (0x23C0 | ((char)(nametable_y) << 11)	//Fetch del prossimo background tile attribute
																	| ((char)(nametable_x) << 10) 
																	| ((coarse_y >> 2) << 3)
																	| (coarse_x >> 2))); 
				
				if ((coarse_y & 0x02) != 0)
					bg_next_tile_attrib = (byte)((char)bg_next_tile_attrib >> 4);
				if ((coarse_x & 0x02) != 0) 
					bg_next_tile_attrib = (byte)((char)bg_next_tile_attrib >> 2);
				
				bg_next_tile_attrib &= 0x03;
				
				P.setBg_next_tile_attr(bg_next_tile_attrib);									//Lo inserisco nella PPU
				break;
			}
			case 4:{
				Byte control = IOM.getPPUControl();												//Prelevo il control register
				byte ppu_bg_next_tile_id = P.getBg_next_tile_id();								//Prelevo il bg_next_tile_id dalla PPU
				int pattern_background = ByteManager.extractBit(PATTERN_BACKGROUND, control);	//Prelevo il bit PATTERN_BACKGROUND dal registro control
				char fine_y = (char)((vram & 0x7000) >> 12);									//Prelev fine_y 
				
				Byte bg_next_tile_lsb = P.PPURead((char)((pattern_background << 12) 			// Fetch del prossimo background tile LSB bit plane dalla pattern memory
	                       						+ ((char)(ppu_bg_next_tile_id << 4)) 
	                       						+ (fine_y) + 0));
				
				P.setBg_next_tile_lsb(bg_next_tile_lsb);										//Lo inserisco nella PPU
				break;
			}
			case 6:{
				Byte control = IOM.getPPUControl();												//Prelevo il control register
				byte ppu_bg_next_tile_id = P.getBg_next_tile_id();								//Prelevo il bg_next_tile_id dalla PPU
				int pattern_background = ByteManager.extractBit(PATTERN_BACKGROUND, control);	//Prelevo il bit PATTERN_BACKGROUND dal registro control
				char fine_y = (char)((vram & 0x7000) >> 12);									//Prelev fine_y 
				
				Byte bg_next_tile_msb = P.PPURead((char)((pattern_background << 12) 			// Fetch del prossimo background tile MSB bit plane dalla pattern memory
												+ ((char)(ppu_bg_next_tile_id << 4)) 
												+ (fine_y) + 8));
				
				P.setBg_next_tile_msb(bg_next_tile_msb);										//Lo inserisco nella PPU
				break;
			}
			case 7:{
				incrementScrollX();																// Incremento il "puntatore" background tile al prossimo tile orizzontale nella nametable memory.									
				break; 
			}
			}
			
		}
		if(cycle == 256){
			// End of a visible scanline, so increment downwards...
			incrementScrollY();
		}
		
		if(cycle == 257){
			//...and reset the x position
			LoadBackgroundShifters();
			TransferAddressX();			
		}
		
		if (cycle == 338 || cycle == 340)
		{
			// Superfluous reads of tile id at end of scanline
			Byte bg_next_tile_id = P.getBg_next_tile_id();							//Prelevo bg_next_tile_id
			char vram = P.getVram_addr();											//Prelevo la vram
			bg_next_tile_id = P.PPURead((char) (0x2000 | (vram & 0x0FFF)));		
			P.setBg_next_tile_id(bg_next_tile_id);
		}

		if (scanline == -1 && cycle >= 280 && cycle < 305)
		{
			// End of vertical blank period so reset the Y address ready for rendering
			TransferAddressY();
		}
	}
	
	public Byte PPURead(char addr)
	{
		//return Bus.PPURead(addr); 
		return (byte)0x00; // STUB
	}

	public void PPUWrite(char addr, Byte data)
	{
		//Bus.PPUWrite(addr, data); 
	}
}