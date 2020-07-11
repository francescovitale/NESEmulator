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
		//Collego al IOManager e alla PPU
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
		
	}
	
	public Byte PPURead(char addr)
	{
		return Bus.PPURead(addr);
	}

	public void PPUWrite(char addr, Byte data)
	{
		Bus.PPUWrite(addr, data);
	}
}


