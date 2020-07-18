package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;

import java.util.ArrayList;

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
	private static final int PATTERN_SPRITE = 3;
	private static final int SPRITE_SIZE = 5;
	private static final int SPRITE_OVERFLOW = 5;
	
	//MACRO foreground
	private static final int Y = 0;
	private static final int ID = 1;
	private static final int ATTRIBUTE = 2;
	private static final int X = 3;
	
	private volatile static PPURenderer PPURenderer = null;
	
	private PPU P;
	private PPUBus Bus;
	private IOManager IOM;
	
	protected PPURenderer() {
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
				vram = ByteManager.setCharBit(NAMETABLE_X, nametable_x, vram);		//reinserisco nametable_x
			}
			else {
				coarse_x++;															//Incremento coarse_x
				vram = (char)((vram & 0xFFE0) | (int)coarse_x);						//Inserisco il nuovo coarse_x
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
					vram = ByteManager.setCharBit(NAMETABLE_Y, nametable_y, vram);		//reinserisco nametable_y
					
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
			vram = ByteManager.setCharBit(NAMETABLE_X, nametable_x, vram);			//Inserisco nametable_x in vram
			
			char coarse_x = (char)(tram & 0x001F);									//prelevo coarse_x dalla tram
			vram = (char)((vram & 0xFFE0) | (int)coarse_x);							//Inserisco il nuovo coarse_x in vram
			
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
			vram = ByteManager.setCharBit(NAMETABLE_Y, nametable_y, vram);			//reinserisco nametable_y in vram
			
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
		P.setBg_shifter_pattern_lo((char)((bg_shifter_pattern_lo & 0xFF00) | (Byte.toUnsignedInt(bg_next_tile_lsb) & 0x00FF)));
		P.setBg_shifter_pattern_hi((char)((bg_shifter_pattern_hi & 0xFF00) | (Byte.toUnsignedInt(bg_next_tile_msb) & 0x00FF)));
		
		//Prelevo i registri utili 
		char bg_shifter_attrib_lo = P.getBg_shifter_attrib_lo();
		char bg_shifter_attrib_hi = P.getBg_shifter_attrib_hi();
		Byte bg_next_tile_attr = P.getBg_next_tile_attr();
		//Modifico i valori		
		P.setBg_shifter_attrib_lo((char) ((bg_shifter_attrib_lo & 0xFF00) | ((Byte.toUnsignedInt(bg_next_tile_attr) & 0x0b01) != 0 ? 0x00FF : 0x0000)));
		P.setBg_shifter_attrib_hi((char) ((bg_shifter_attrib_hi & 0xFF00) | ((Byte.toUnsignedInt(bg_next_tile_attr) & 0x0b10) != 0 ? 0x00FF : 0x0000)));
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
		
		//Foreground
		int render_sprites = ByteManager.extractBit(RENDER_SPRITES, mask);
		if (render_sprites==1 && P.getCycles() >= 1 && P.getCycles() < 258)
		{
			for (int i = 0; i < P.getSprite_count(); i+=4)
			{
				if (Byte.toUnsignedInt(P.getSpriteScanline().get(i + X)) > 0)
				{
					P.decrease_sprite_scanline_x(i);
				}
				else
				{
					P.shift_sprite_shifters(i/4);
				}
			}
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
		
		if(cycle == 1 && scanline == -1) {
			// Inizia effettivamente un nuovo ciclo quindi pulisco il vertical blank flag
			Byte status = IOM.getPPUStatus();
			status = ByteManager.setBit(VERTICAL_BLANK, 0, status);
			IOM.setPPUStatus(status);
			P.clearSprite();
		}
		
		if ((cycle >= 2 && cycle < 258) || (cycle >= 321 && cycle < 338)){
			UpdateShifters();														//Aggiorno gli shift register
			char vram = P.getVram_addr();											//Prelevo la vram
			cycle = P.getCycles();
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
				
				byte bg_next_tile_attrib = P.PPURead((char) (0x23C0 | ((nametable_y) << 11)	//Fetch del prossimo background tile attribute
																	| ((nametable_x) << 10) 
																	| (((int)coarse_y >> 2) << 3)
																	| ((int)coarse_x >> 2))); 
				
				if (((int)coarse_y & 0x02) != 0)
					bg_next_tile_attrib = (byte)(Byte.toUnsignedInt(bg_next_tile_attrib) >> 4);
				if (((int)coarse_x & 0x02) != 0) 
					bg_next_tile_attrib = (byte)(Byte.toUnsignedInt(bg_next_tile_attrib) >> 2);
				
				bg_next_tile_attrib &= 0x03;
				
				P.setBg_next_tile_attr(bg_next_tile_attrib);									//Lo inserisco nella PPU
				break;
			}
			case 4:{
				Byte control = IOM.getPPUControl();												//Prelevo il control register
				byte bg_next_tile_id = P.getBg_next_tile_id();									//Prelevo il bg_next_tile_id dalla PPU
				int pattern_background = ByteManager.extractBit(PATTERN_BACKGROUND, control);	//Prelevo il bit PATTERN_BACKGROUND dal registro control
				char fine_y = (char)((vram & 0x7000) >> 12);									//Prelev fine_y 
				
				Byte bg_next_tile_lsb = P.PPURead((char)((pattern_background << 12) 			// Fetch del prossimo background tile LSB bit plane dalla pattern memory
	                       						+ (Byte.toUnsignedInt(bg_next_tile_id) << 4)
	                       						+ ((int)fine_y) + 0));
				
				P.setBg_next_tile_lsb(bg_next_tile_lsb);										//Lo inserisco nella PPU
				break;
			}
			case 6:{
				Byte control = IOM.getPPUControl();												//Prelevo il control register
				byte bg_next_tile_id = P.getBg_next_tile_id();									//Prelevo il bg_next_tile_id dalla PPU
				int pattern_background = ByteManager.extractBit(PATTERN_BACKGROUND, control);	//Prelevo il bit PATTERN_BACKGROUND dal registro control
				char fine_y = (char)((vram & 0x7000) >> 12);									//Prelev fine_y 
				
				Byte bg_next_tile_msb = P.PPURead((char)((pattern_background << 12) 			// Fetch del prossimo background tile MSB bit plane dalla pattern memory
												+ (Byte.toUnsignedInt(bg_next_tile_id) << 4) 
												+ (int)(fine_y) + 8));
				
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
		
		// Foreground Rendering ========================================================
	
		if (cycle == 257 && scanline >= 0)
		{
			// Pulisco la sprite memory
			ArrayList <Byte> spriteScanline = new ArrayList<>(P.getSpriteScanline());							//SpriteScanline locale
			ArrayList <Byte> sprite_shifter_pattern_lo = new ArrayList<>(P.getSprite_shifter_pattern_lo());		//sprite_shifter_pattern_lo locale
			ArrayList <Byte> sprite_shifter_pattern_hi = new ArrayList<>(P.getSprite_shifter_pattern_hi());		//sprite_shifter_pattern_hi locale
			
			//Pulisco tutte le informazioni
			for(int i = 0; i < 32; i++) {
				spriteScanline.set(i,(byte)0xFF);									//il valore 0xFF indica che tutti gli sprites sono fuori dallo schermo
			}
			for(int i = 0; i < 8; i++) {
				sprite_shifter_pattern_lo.set(i,(byte)0x00);
				sprite_shifter_pattern_hi.set(i,(byte)0x00);
			}
			
			P.setSprite_count(0);

			//Indice utilizzato per verificare nell'OAM se vi sono Sprite da visualizzare
			char nOAMEntry = 0x0000;

			//Nuovo set di sprites, lo Sprite zero potrebbe non esistere quindi va pulito
			P.setbSpriteZeroHitPossible(false);
			
			while ((int)nOAMEntry < 256 && P.getSprite_count() < 36)
			{
				// Note the conversion to signed numbers here
				int diff = (scanline - Byte.toUnsignedInt((P.readOAM((char)(nOAMEntry + Y))))); //DA CONTROLLARE SE UNSIGNED

				// Se la differenza è positiva ed è minore di 8 o 16 a seconda della modalità allora lo sprite è visibile
				Integer spritesize = ByteManager.extractBit(SPRITE_SIZE, IOM.getPPUControl());			//Estraggo il bit spritesize dal registro control
				if (diff >= 0 && diff < (spritesize==1 ? 16 : 8))
				{
					// lo Sprite è visible, quindi copio l'attribute nello Sprite scanline. (Se non ne ho già presi altri 8)
					if (P.getSprite_count() < 32)
					{
						// se è lo sprite zero
						if (nOAMEntry == 0)
						{
							P.setbSpriteZeroHitPossible(true);
						}
						spriteScanline.set(P.getSprite_count() + Y, P.readOAM((char)(nOAMEntry + Y)));
						spriteScanline.set(P.getSprite_count() + ID, P.readOAM((char)(nOAMEntry + ID)));
						spriteScanline.set(P.getSprite_count() + ATTRIBUTE, P.readOAM((char)(nOAMEntry + ATTRIBUTE)));
						spriteScanline.set(P.getSprite_count() + X, P.readOAM((char)(nOAMEntry + X)));
						
						P.setSprite_count(P.getSprite_count() + 4);
					}				
				}

				nOAMEntry = (char)((int)nOAMEntry + 4);
			} // Fine dello sprite evaluation 

			// Set dello sprite overflow flag se ho preso più di 8 sprites
			int val;
			if(P.getSprite_count() > 32) {
				val = 1;
			}
			else val = 0;
			IOM.setPPUStatus(ByteManager.setBit(SPRITE_OVERFLOW, val, IOM.getPPUStatus()));
			
			P.setSpriteScanline(spriteScanline);
			P.setSprite_shifter_pattern_lo(sprite_shifter_pattern_lo);
			P.setSprite_shifter_pattern_hi(sprite_shifter_pattern_hi);
		}

		if (cycle == 340)
		{
			ArrayList <Byte> spriteScanline = new ArrayList<>(P.getSpriteScanline());							//SpriteScanline locale
			ArrayList <Byte> sprite_shifter_pattern_lo = new ArrayList<>(P.getSprite_shifter_pattern_lo());		//sprite_shifter_pattern_lo locale
			ArrayList <Byte> sprite_shifter_pattern_hi = new ArrayList<>(P.getSprite_shifter_pattern_hi());		//sprite_shifter_pattern_hi locale
			// Preparo gli shifter register per il foreground
			for (int i = 0; i < P.getSprite_count(); i+=4)
			{

				Byte sprite_pattern_bits_lo;
				Byte sprite_pattern_bits_hi;
				char sprite_pattern_addr_lo;
				char sprite_pattern_addr_hi;

				Integer sprite_size = ByteManager.extractBit(SPRITE_SIZE, IOM.getPPUControl());			//Estraggo il bit spritesize dal registro control
				if (sprite_size == 0)
				{
					// 8x8 Sprite Mode 
					
					if ((Byte.toUnsignedInt(spriteScanline.get(i + ATTRIBUTE)) & 0x80) == 0)
					{
						//Lo Sprite non è specchiato verticalmente  
						sprite_pattern_addr_lo = (char) ((ByteManager.extractBit(PATTERN_SPRITE, IOM.getPPUControl()) << 12  ) 	 // Quale Pattern Table? 0KB o 4KB offset
														| (Byte.toUnsignedInt(spriteScanline.get(i + ID))   << 4   ) 			 // Quale Cella? Tile ID * 16 (16 bytes per tile)
														| (scanline - Byte.toUnsignedInt(spriteScanline.get(i + Y)))); 			 // Quale riga in cell? (0->7)
														
					}
					else
					{
						// Lo Sprite è specchiato verticalmente
						sprite_pattern_addr_lo = (char) ((ByteManager.extractBit(PATTERN_SPRITE, IOM.getPPUControl()) << 12  ) 	 // Quale Pattern Table? 0KB o 4KB offset
														| (Byte.toUnsignedInt(spriteScanline.get(i + ID))   << 4   ) 			 // Quale Cella? Tile ID * 16 (16 bytes per tile)
														| (7 - (scanline - Byte.toUnsignedInt(spriteScanline.get(i + Y))))); 	 // Quale riga in cell? (7->0)
					}

				}
				else
				{
					// 8x16 Sprite Mode 
					if ((Byte.toUnsignedInt(spriteScanline.get(i + ATTRIBUTE)) & 0x80) == 0)
					{
						//Lo Sprite non è specchiato verticalmente
						if (scanline - Byte.toUnsignedInt(spriteScanline.get(i + Y)) < 8)
						{
							// Reading Top half Tile
							sprite_pattern_addr_lo =(char)( ( (Byte.toUnsignedInt(spriteScanline.get(i + ID)) & 0x01) << 12  )        // Quale Pattern Table? 0KB or 4KB offset
															| ( (Byte.toUnsignedInt(spriteScanline.get(i + ID)) & 0xFE) << 4 )        // Quale Cell? Tile ID * 16 (16 bytes per tile)
															| ((scanline - Byte.toUnsignedInt(spriteScanline.get(i + Y))) & 0x07 ) ); // Quale riga in cell? (0->7)
						}
						else
						{
							// Reading Bottom Half Tile
							sprite_pattern_addr_lo =(char)( ( (Byte.toUnsignedInt(spriteScanline.get(i + ID)) & 0x01) << 12  )       	  // Quale Pattern Table? 0KB or 4KB offset
															| (((Byte.toUnsignedInt(spriteScanline.get(i + ID)) & 0xFE)+ 1) << 4 )        // Quale Cell? Tile ID * 16 (16 bytes per tile)
															| ((scanline - Byte.toUnsignedInt(spriteScanline.get(i + Y))) & 0x07 ) ); 	  // Quale riga in cell? (0->7)
						}
					}
					else
					{
						// Lo Sprite è specchiato verticalmente
						if (scanline - Byte.toUnsignedInt(spriteScanline.get(i + Y)) < 8)
						{
							// Reading Top half Tile
							sprite_pattern_addr_lo =(char)( ( (Byte.toUnsignedInt(spriteScanline.get(i + ID)) & 0x01) << 12  )       	        // Quale Pattern Table? 0KB or 4KB offset
															| (((Byte.toUnsignedInt(spriteScanline.get(i + ID)) & 0xFE)+ 1) << 4 )              // Quale Cell? Tile ID * 16 (16 bytes per tile)
															| ((7 - (scanline - Byte.toUnsignedInt(spriteScanline.get(i + Y)))) & 0x07 ) ); 	// Quale riga in cell? (0->7)
						}
						else
						{
							// Reading Bottom Half Tile
							sprite_pattern_addr_lo =(char)( ( (Byte.toUnsignedInt(spriteScanline.get(i + ID)) & 0x01) << 12  )       	        // Quale Pattern Table? 0KB or 4KB offset
															| ((Byte.toUnsignedInt(spriteScanline.get(i + ID)) & 0xFE) << 4 )            		// Quale Cell? Tile ID * 16 (16 bytes per tile)
															| ((7 - (scanline - Byte.toUnsignedInt(spriteScanline.get(i + Y)))) & 0x07 ) ); 	// Quale riga in cell? (0->7)
						}
					}
				}

				//Hi bit plane equivale al low sommato a 8
				sprite_pattern_addr_hi = (char) ((int)sprite_pattern_addr_lo + 8);

				//Ora che abbiamo gli indirizzi possiamo leggere gli sprite
				sprite_pattern_bits_lo = PPURead(sprite_pattern_addr_lo);
				sprite_pattern_bits_hi = PPURead(sprite_pattern_addr_hi);

				//Se lo sprite è capovolto bisogna capovolgere i Bytes del pattern
				if ((Byte.toUnsignedInt(spriteScanline.get(i + ATTRIBUTE)) & 0x40) != 0){

					// Capovolgo i Patterns orizzontalmente
					sprite_pattern_bits_lo = flipbyte(sprite_pattern_bits_lo);
					sprite_pattern_bits_hi = flipbyte(sprite_pattern_bits_hi);
				}

				// Carico gli Shift register
				sprite_shifter_pattern_lo.set(i/4, sprite_pattern_bits_lo);
				sprite_shifter_pattern_hi.set(i/4, sprite_pattern_bits_hi);
			}
			//Riaggiorno i valori in PPU
			P.setSpriteScanline(spriteScanline);
			P.setSprite_shifter_pattern_lo(sprite_shifter_pattern_lo);
			P.setSprite_shifter_pattern_hi(sprite_shifter_pattern_hi);
		}	
		
	}
	
	private Byte flipbyte(Byte b)
	{
		b = (byte)((Byte.toUnsignedInt((byte)(b & 0xF0)) >> 4) | (Byte.toUnsignedInt((byte)(b & 0x0F)) << 4));
		b = (byte)((Byte.toUnsignedInt((byte)(b & 0xCC)) >> 2) | (Byte.toUnsignedInt((byte)(b & 0x33)) << 2));
		b = (byte)((Byte.toUnsignedInt((byte)(b & 0xAA)) >> 1) | (Byte.toUnsignedInt((byte)(b & 0x55)) << 1));
		return b;
	};
	
	public Byte PPURead(char addr)
	{
		return Bus.PPURead(addr); 
	}

	public void PPUWrite(char addr, Byte data)
	{
		Bus.PPUWrite(addr, data); 
	}
}