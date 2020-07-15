package Emulator.ApplicationLogic;


public class ByteManager {
	
	ByteManager(){}
	
	public static Byte setBit(int pos, int val, Byte reg) {
		switch(pos) {
		case 0:
			if(val == 1)
				return (byte)(0x01 | reg);
			else
				return (byte)(0xFE & reg);
		case 1:
			if(val == 1)
				return (byte)(0x02 | reg);
			else
				return (byte) (0xFD & reg);
		case 2:
			if(val == 1)
				return (byte)(0x04 | reg);
			else
				return (byte)(0xFB & reg);
		case 3:
			if(val == 1)
				return (byte)(0x08 | reg);
			else
				return (byte)(0xF7 & reg);
		case 4:
			if(val == 1)
				return (byte)(0x10 | reg);
			else
				return (byte)(0xEF & reg);
		case 5:
			if(val == 1)
				return (byte)(0x20 | reg);
			else
				return (byte)(0xDF & reg);
		case 6:
			if(val == 1)
				return (byte)(0x40 | reg);
			else
				return (byte)(0xBF & reg);
		case 7:
			if(val == 1)
				return (byte)(0x80 | reg);
			else {
				return (byte)(0x7F & reg);
			}
				
		default:
			return 0;
		}
	}
	public static int extractBit(int pos, Byte reg) {
		switch(pos) {
		case 0:
			return (reg & 0x01);
		case 1:
			if((byte) (reg & 0x02) != 0)
				return 1;
			else
				return 0;
		case 2:
			if((byte) (reg & 0x04) != 0)
				return 1;
			else
				return 0;
		case 3:
			if((byte) (reg & 0x08) != 0)
				return 1;
			else
				return 0;
		case 4:
			if((byte) (reg & 0x10) != 0)
				return 1;
			else
				return 0;
		case 5:
			if((byte) (reg & 0x20) != 0)
				return 1;
			else
				return 0;
		case 6:
			if((byte) (reg & 0x40) != 0)
				return 1;
			else
				return 0;
		case 7:
			if((byte) (reg & 0x80) != 0)
				return 1;
			else
				return 0;
		default:
			return 0;
		}
		
	}
	public static char setCharBit(int pos, int val, char reg) {
		switch(pos) {
		case 0:
			if(val == 1)
				return (char)(0x0001 | reg);
			else
				return (char)(0xFFFE & reg);
		case 1:
			if(val == 1)
				return (char)(0x0002 | reg);
			else
				return (char)(0xFFFD & reg);
		case 2:
			if(val == 1)
				return (char)(0x0004 | reg);
			else
				return (char)(0xFFFB & reg);
		case 3:
			if(val == 1)
				return (char)(0x0008 | reg);
			else
				return (char)(0xFFF7 & reg);
		case 4:
			if(val == 1)
				return (char)(0x0010 | reg);
			else
				return (char)(0xFFEF & reg);
		case 5:
			if(val == 1)
				return (char)(0x0020 | reg);
			else
				return (char)(0xFFDF & reg);
		case 6:
			if(val == 1)
				return (char)(0x0040 | reg);
			else
				return (char)(0xFFBF & reg);
		case 7:
			if(val == 1)
				return (char)(0x0080 | reg);
			else
				return (char)(0xFF7F & reg);
		case 8:
			if(val == 1)
				return (char)(0x0100 | reg);
			else
				return (char)(0xFEFF & reg);
		case 9:
			if(val == 1)
				return (char)(0x0200 | reg);
			else
				return (char)(0xFDFF & reg);
		case 10:
			if(val == 1)
				return (char)(0x0400 | reg);
			else
				return (char)(0xFBFF & reg);
		case 11:
			if(val == 1)
				return (char)(0x0800 | reg);
			else
				return (char)(0xF7FF & reg);
		case 12:
			if(val == 1)
				return (char)(0x1000 | reg);
			else
				return (char)(0xEFFF & reg);
		case 13:
			if(val == 1)
				return (char)(0x2000 | reg);
			else
				return (char)(0xDFFF & reg);
		case 14:
			if(val == 1)
				return (char)(0x4000 | reg);
			else
				return (char)(0xBFFF & reg);
		case 15:
			if(val == 1)
				return (char)(0x8000 | reg);
			else
				return (char)(0x7FFF & reg);
		default:
			return 0;
		}
	}
	public static int extractCharBit(int pos, char reg) {
		switch(pos) {
		case 0:
			return (reg & 0x0001);
		case 1:
			if((char) (reg & 0x0002) != 0)
				return 1;
			else
				return 0;
		case 2:
			if((char) (reg & 0x0004) != 0)
				return 1;
			else
				return 0;
		case 3:
			if((char) (reg & 0x0008) != 0)
				return 1;
			else
				return 0;
		case 4:
			if((char) (reg & 0x0010) != 0)
				return 1;
			else
				return 0;
		case 5:
			if((char) (reg & 0x0020) != 0)
				return 1;
			else
				return 0;
		case 6:
			if((char) (reg & 0x0040) != 0)
				return 1;
			else
				return 0;
		case 7:
			if((char) (reg & 0x0080) != 0)
				return 1;
			else
				return 0;
		case 8:
			if((char) (reg & 0x0100) != 0)
				return 1;
			else
				return 0;
		case 9:
			if((char) (reg & 0x0200) != 0)
				return 1;
			else
				return 0;
		case 10:
			if((char) (reg & 0x0400) != 0)
				return 1;
			else
				return 0;
		case 11:
			if((char) (reg & 0x0800) != 0)
				return 1;
			else
				return 0;
		case 12:
			if((char) (reg & 0x1000) != 0)
				return 1;
			else
				return 0;
		case 13:
			if((char) (reg & 0x2000) != 0)
				return 1;
			else
				return 0;
		case 14:
			if((char) (reg & 0x4000) != 0)
				return 1;
			else
				return 0;
		case 15:
			if((char) (reg & 0x8000) != 0)
				return 1;
			else
				return 0;
		default:
			return 0;
		}
	}
}
