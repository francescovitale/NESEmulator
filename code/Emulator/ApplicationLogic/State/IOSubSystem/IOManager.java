package Emulator.ApplicationLogic.State.IOSubSystem;

import Emulator.ApplicationLogic.ByteManager;
import Emulator.ApplicationLogic.State.Bus;
import Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem.PPU;

public class IOManager {
	
	private volatile static IOManager IOManager = null;
	
	private Byte PPUStatus;
	private Byte PPUMask;
	private Byte PPUControl;
	private Byte PPUAddress;
	private Byte PPUData;
	
	private Bus CpuBus;
	private PPU PictureProcessingUnit;
	
	private IOManager() {
		
		PPUStatus = 0x00;
		PPUMask = 0x00;
		PPUControl = 0x00;
		PPUAddress = 0x00;
		PPUData = 0x00;
		
		CpuBus = Bus.getInstance();	
		PictureProcessingUnit = PPU.getInstance(); 
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
				// più significativi contengono informazioni,
				data = (byte)((PPUStatus & 0xE0) | (PPUData & 0x1F));
				
				// Bisogna resettare il bit del vertical_blank
				PPUStatus = ByteManager.setBit(7, 0, PPUStatus); 
				
				// Resetta l'address_latch nella ppu
				PictureProcessingUnit.setAddress_latch(0);

				break;
			case 0x0003: // OAM Address
				break;
			case 0x0004: // OAM Data
				break;
			case 0x0005: // Scroll
				//Not Readable
				break;
			case 0x0006: //PPU Address
				//Not Readable
				break;
			case 0x0007: // PPU Data

				//Le letture dalla Nametable, ovvero la VRAM; sono rallentate di un ciclo di clock,
				//dunque il registro PPUData conterrà le informazioni del dato della precedente richiesta
				
				data = PPUData;
				vram_addr = PictureProcessingUnit.getVram_addr();
				
				//una volta letto il dato, esso va aggiornato per la prossima richiesta
				
				PPUData = PictureProcessingUnit.PPURead(vram_addr);
				
				// Tuttavia se la lettura corrisponde al range delle palette (3f00), allora leggilo immediatamente
				
				if(vram_addr >= 0x3F00) data = PPUData;
				
				// Tutte le letture sulla PPU incrementano il valore della Vram, e tale incremento dipende da quanto specificato nel registro control
				// se il l'increment mode è su vertical (ovvero bit alto), l'incremento è di 32, ovvero si deve andare alla riga successiva,
				// altrimenti è necessario incrementare solo di un bit, e quindi di 1.
				
				if(ByteManager.extractBit(2, PPUControl) == 1) {
					vram_addr += 32;
				}
				else
				{
					vram_addr += 1;
				}
			
				PictureProcessingUnit.setVram_addr(vram_addr);
				
				break;
			}
		
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
				
				nametable_x = ByteManager.extractBit(0, PPUControl); //BIT nametable_x
				nametable_y = ByteManager.extractBit(1, PPUControl); //BIT nametable_y
				
				tram_addr = ByteManager.setCharBit(10,nametable_x,tram_addr);
				tram_addr = ByteManager.setCharBit(11,nametable_y,tram_addr);
				
				PictureProcessingUnit.setTram_addr(tram_addr);
				
				break;
			case 0x0001: // Mask
				PPUMask = data;
				break;
			case 0x0002: // Status
				break;
			case 0x0003: // OAM Address
				break;
			case 0x0004: // OAM Data
				break;
			case 0x0005: // Scroll
				
				if(PictureProcessingUnit.getAddress_latch() == 0) {
					
					// nello scroll legister, se siamo abilitati a farlo, gestiamo i valori di fine e coarse x
					
					temp_fine_x = PictureProcessingUnit.getFine_x();
					temp_fine_x = (byte)(data & 0x07);
					
					PictureProcessingUnit.setFine_x(temp_fine_x);
					
					tram_addr = PictureProcessingUnit.getTram_addr();
					data = (byte)((data >> 3) & 0x1F);					 //riporto il dato ai primi 5 bit meno significativi
					tram_addr = (char)((tram_addr & 0xFFE0) | data);	 //Setta i 5 bit di coarse_x del loopy register al valore di data shiftato.
					
					PictureProcessingUnit.setTram_addr(tram_addr);
					PictureProcessingUnit.setAddress_latch(1);
					
				}else {
					
					// nello scroll legister, se siamo abilitati a farlo, gestiamo i valori di fine e coarse y
					tram_addr = PictureProcessingUnit.getTram_addr(); //ricordiamo che fine_y è presente nel loopy_register tram, nei bit 14,13,12
					tram_addr = (char)(tram_addr);

					tram_addr = (char)((char)(tram_addr & 0x8FFF) | (char)((char)(data & 0x07) << 12)); //setto i bit 14,13,12 ai 3 bit meno significativi di data (setto fine_y)
					data = (byte)((data >> 3) & 0x1F);
					tram_addr = (char)((tram_addr & 0xFC1F) | (char)((int)data << 5)); //setto coarse_y in tram
					
					PictureProcessingUnit.setTram_addr(tram_addr);
					PictureProcessingUnit.setAddress_latch(0);
					
				}
				
				break;
			case 0x0006: // PPU Address
				if (PictureProcessingUnit.getAddress_latch() == 0)
				{			
					tram_addr = PictureProcessingUnit.getTram_addr();
					tram_addr = (char)((char)((char)(data & 0x3F) << 8) | (tram_addr & 0x00FF));
					PictureProcessingUnit.setTram_addr(tram_addr);
					PictureProcessingUnit.setAddress_latch(1);
				}
				else
				{
					//Quando una intera scrittura è stata realizzata, il registro di vram_addr è aggiornato, the internal vram address al valore di tram
							
					tram_addr = PictureProcessingUnit.getTram_addr();
					tram_addr = (char)((tram_addr & 0xFF00) | data);
					PictureProcessingUnit.setTram_addr(tram_addr);
					PictureProcessingUnit.setVram_addr(tram_addr);
					PictureProcessingUnit.setAddress_latch(0);
				}
				break;
			case 0x0007: // PPU Data
				vram_addr = PictureProcessingUnit.getVram_addr();
				PictureProcessingUnit.PPUWrite(PictureProcessingUnit.getVram_addr(), data);
				// Tutte le scritture sulla PPU incrementano il nametable, e tale incremento dipende da quanto specificato nel registro control
				// se il l'increment mode è su vertical (ovvero bit alto), l'incremento è di 32, ovvero si deve andare alla riga successiva,
				// altrimenti è necessario incrementare solo di un bit, e quindi di 1.
				
				if(ByteManager.extractBit(2, PPUControl) == 1) {
					vram_addr += 32;
				}
				else
				{
					vram_addr += 1;
				}
				
				PictureProcessingUnit.setVram_addr(vram_addr);
				
				break;
			}
		}
	}

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
