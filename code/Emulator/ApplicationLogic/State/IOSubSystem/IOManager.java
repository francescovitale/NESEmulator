package Emulator.ApplicationLogic.State.IOSubSystem;

import Emulator.ApplicationLogic.ByteManager;
import Emulator.ApplicationLogic.State.Bus;
import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.OAM;
import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.PPU;

public class IOManager {
	
	//MACRO
	private static final int INCREMENT_MODE = 2;
	private static final int CONTROL_NAMETABLE_X = 0;
	private static final int CONTROL_NAMETABLE_Y = 1;
	private static final int LOOPY_NAMETABLE_X = 10;
	private static final int LOOPY_NAMETABLE_Y = 11;
	private static final int VERTICAL_BLANK = 7;
	
	private volatile static IOManager IOManager = null;
	
	private Byte PPUStatus;
	private Byte PPUMask;
	private Byte PPUControl;
	private Byte PPUAddress;
	private Byte PPUData;
	
	private Bus CpuBus;
	private PPU PictureProcessingUnit;
	private Joypad Pad;
	private DMA Dma;
	 
	private IOManager() {
		
		PPUStatus = 0x00;
		PPUMask = 0x00;
		PPUControl = 0x00;
		PPUAddress = 0x00;
		PPUData = 0x00;
		
		CpuBus = Bus.getInstance();	
		PictureProcessingUnit = PPU.getInstance(); 
		Dma = DMA.getInstance();
	}

	//Punto di ingresso globale all'istanza
	
	public static IOManager getInstance() {
		if(IOManager==null) {
			synchronized(IOManager.class) {
				if(IOManager==null) {
					IOManager = new IOManager();
				}
			}
		}
		return IOManager;
	}
	
	
	public Byte read(char address, Boolean rdonly) {
		
		Byte data = 0x00;
		
		//SPAZIO DI INDIRIZZAMENTO PER LA PERIFERICA PICTURE PROCESSING UNIT

		if(address >= 0x2000 && address <= 0x3FFF) {

			address = (char)(address & 0x0007); //Mirroring
			char vram_addr;
			Byte vram_data;

			switch (address)
			{
			case 0x0000: // Control
				//Not readable
				break;
			case 0x0001: // Mask
				//Not readable
				break;
			case 0x0002: // Status
				// Leggere dallo status register ha una funzione di reset; solo i tre bit
				// pi� significativi contengono informazioni,
				data = (byte)((Byte.toUnsignedInt(PPUStatus) & 0xE0) | (Byte.toUnsignedInt(PPUData) & 0x1F));
				
				// Bisogna resettare il bit del vertical_blank
				PPUStatus = ByteManager.setBit(VERTICAL_BLANK, 0, PPUStatus); 

				// Resetta l'address_latch nella ppu
				PictureProcessingUnit.setAddress_latch(0);

				break;
			case 0x0003: // OAM Address
				break;
			case 0x0004: // OAM Data
				Byte OAMaddr = PictureProcessingUnit.getOAMaddr();
				data = PictureProcessingUnit.readOAM((char)Byte.toUnsignedInt(OAMaddr));
				break;
			case 0x0005: // Scroll
				//Not Readable
				break;
			case 0x0006: //PPU Address
				//Not Readable
				break;
			case 0x0007: // PPU Data

				//Le letture dalla Nametable, ovvero la VRAM; sono rallentate di un ciclo di clock,
				//dunque il registro PPUData conterr� le informazioni del dato della precedente richiesta
				data = PPUData;
				vram_addr = PictureProcessingUnit.getVram_addr();
				
				//una volta letto il dato, esso va aggiornato per la prossima richiesta
				PPUData = PictureProcessingUnit.PPURead(vram_addr);
				
				// Tuttavia se la lettura corrisponde al range delle palette (3f00), allora leggilo immediatamente
				if(vram_addr >= 0x3F00) data = PPUData;
				
				// Tutte le letture sulla PPU incrementano il valore della Vram, e tale incremento dipende da quanto specificato nel registro control
				// se il l'increment mode � su vertical (ovvero bit alto), l'incremento � di 32, ovvero si deve andare alla riga successiva,
				// altrimenti � necessario incrementare solo di un bit, e quindi di 1.
				
				if(ByteManager.extractBit(INCREMENT_MODE, PPUControl) == 1) {
					vram_addr = (char)((int)vram_addr + 32);
				}
				else
				{
					vram_addr = (char)((int)vram_addr + 1);
				}
			
				PictureProcessingUnit.setVram_addr(vram_addr);
				
				break;
			}
		
		}
		else if(address == 0x4016) {
			Pad = Joypad.getInstance();
			
			if((Pad.getController_state() & 0x80) != 0) {
				data = 0x01;
			}
			else data = 0x00;
			Pad.setController_state((byte)(Pad.getController_state() << 1));
		}
		
		return data;
		
	}
	 
	public void write(char address, Byte data) {
		
		//SPAZIO DI INDIRIZZAMENTO PER LA PERIFERICA PICTURE PROCESSING UNITtry 

		
		if(address >= 0x2000 && address < 0x3FFF) {
			
			char tram_addr;
			char vram_addr;
			Byte temp_fine_x;
			Byte temp_fine_y = 0;
			int nametable_x;
			int nametable_y;
			
			address = (char)(address & 0x0007); //Mirroring
			switch (address)
			{
			case 0x0000: // Control
				PPUControl = data;
				
				tram_addr = PictureProcessingUnit.getTram_addr();
				
				nametable_x = ByteManager.extractBit(CONTROL_NAMETABLE_X, PPUControl);	 //Estraggo il BIT nametable_x dal registro control
				nametable_y = ByteManager.extractBit(CONTROL_NAMETABLE_Y, PPUControl);	 //Estraggo il BIT nametable_y dal registro control
				
				tram_addr = ByteManager.setCharBit(LOOPY_NAMETABLE_X,nametable_x,tram_addr);	//Modifico il BIT nametable_x del tram
				tram_addr = ByteManager.setCharBit(LOOPY_NAMETABLE_Y,nametable_y,tram_addr);	//Modifico il BIT nametable_y del tram
				
				PictureProcessingUnit.setTram_addr(tram_addr);
				
				break;
			case 0x0001: // Mask
				PPUMask = data;
				break;
			case 0x0002: // Status
				break;
			case 0x0003: // OAM Address
				PictureProcessingUnit.setOAMaddr(data);
				break;
			case 0x0004: // OAM Data
				Byte OAMaddr = PictureProcessingUnit.getOAMaddr();
				PictureProcessingUnit.writeOAM((char)Byte.toUnsignedInt(OAMaddr), data);
				break;
			case 0x0005: // Scroll
				
				if(PictureProcessingUnit.getAddress_latch() == 0) {
					
					// nello scroll legister, se siamo abilitati a farlo, gestiamo i valori di fine e coarse x

					temp_fine_x = (byte)(data & 0x07);
					
					PictureProcessingUnit.setFine_x(temp_fine_x);
					
					tram_addr = PictureProcessingUnit.getTram_addr();
					data = (byte)((data >> 3) & 0x1F);											 //riporto il dato ai primi 5 bit meno significativi
					tram_addr = (char)((int)(tram_addr & 0xFFE0) | Byte.toUnsignedInt(data));	 //Setta i 5 bit di coarse_x del loopy register al valore di data shiftato.
					
					PictureProcessingUnit.setTram_addr(tram_addr);
					PictureProcessingUnit.setAddress_latch(1);
					
				}else {
					
					// nello scroll legister, se siamo abilitati a farlo, gestiamo i valori di fine e coarse y
					tram_addr = PictureProcessingUnit.getTram_addr(); //ricordiamo che fine_y � presente nel loopy_register tram, nei bit 14,13,12


					tram_addr = (char)((char)(tram_addr & 0x8FFF) | (char)((char)(data & 0x07) << 12)); //setto i bit 14,13,12 ai 3 bit meno significativi di data (setto fine_y)
					data = (byte)((data >> 3) & 0x1F);
					tram_addr = (char)((tram_addr & 0xFC1F) | (char)((int)data << 5)); 					//setto coarse_y in tram
					
					PictureProcessingUnit.setTram_addr(tram_addr);
					PictureProcessingUnit.setAddress_latch(0);
					
				}
				
				break;
			case 0x0006: // PPU Address
				if (PictureProcessingUnit.getAddress_latch() == 0)
				{			
					tram_addr = PictureProcessingUnit.getTram_addr();
					tram_addr = (char)( ((Byte.toUnsignedInt(data) & 0x3F) << 8) | (int)(tram_addr & 0x00FF) );
					PictureProcessingUnit.setTram_addr(tram_addr);
					PictureProcessingUnit.setAddress_latch(1);
				}
				else
				{
					//Quando una intera scrittura � stata realizzata, il registro di vram_addr � aggiornato, the internal vram address al valore di tram
					tram_addr = PictureProcessingUnit.getTram_addr();
					tram_addr = (char)((tram_addr & 0xFF00) | Byte.toUnsignedInt(data));
					PictureProcessingUnit.setTram_addr(tram_addr);
					PictureProcessingUnit.setVram_addr(tram_addr);
					PictureProcessingUnit.setAddress_latch(0);
				}
				break;
			case 0x0007: // PPU Data
				vram_addr = PictureProcessingUnit.getVram_addr();
				PictureProcessingUnit.PPUWrite(vram_addr, data);
				// Tutte le scritture sulla PPU incrementano il nametable, e tale incremento dipende da quanto specificato nel registro control
				// se il l'increment mode � su vertical (ovvero bit alto), l'incremento � di 32, ovvero si deve andare alla riga successiva,
				// altrimenti � necessario incrementare solo di un bit, e quindi di 1.
				
				if(ByteManager.extractBit(2, PPUControl) == 1) {
					vram_addr = (char)((int)vram_addr + 32);
				}
				else
				{
					vram_addr = (char)((int)vram_addr + 1);
				}
				PictureProcessingUnit.setVram_addr(vram_addr);
				
				break;
			}
		}
		else if (address == 0x4014)
		{
			// Una scrittura a questo indirizzo inizializza il DMA 
			Dma.setDma_page(data);
			Dma.setDma_addr((byte)0x00);
			Dma.setDma_transfer(true);						
		}
		else if(address == 0x4016) {
			Pad = Joypad.getInstance();
		
			Pad.setController_state(Pad.getController());
		}
	}
	
	//PPU CLOCK
	public void PPUclock() {
		PictureProcessingUnit.clock();
	}

	//DMA CLOCK
	public void DMAclock(Integer ElapsedClockTicks) {
		Dma.clock(ElapsedClockTicks);
	}
	
	public Boolean getDma_transfer() {
		return Dma.getDma_transfer();
	}
	
	//GETTER AND SETTER
	public Byte getPPUStatus() {
		return PPUStatus;
	}

	public void setPPUStatus(Byte pPUStatus) {
		PPUStatus = pPUStatus;
	}

	public Byte getPPUMask(){
		return PPUMask;
	}
	
	public void setPPUMask(Byte Mask) {
		
		PPUMask= Mask;
		
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
