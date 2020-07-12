package Emulator.ApplicationLogic.State.IOSubSystem.PPUSubSystem;


public class Pixel {
	private int x_coord;
	private int y_coord;
	private String NES_hex_code;
	private String rgb_info;

	public Pixel(int x_coord_in, int y_coord_in, String hex_code_in, String rgb_info_in) {
		x_coord = x_coord_in;
		y_coord = y_coord_in;
		NES_hex_code = hex_code_in;
		rgb_info = rgb_info_in;
	}
	
	public Pixel() {
		x_coord = 0;
		y_coord = 0;
		NES_hex_code = "";
		rgb_info = "";
	}
	public Pixel(Pixel P) {
		x_coord = P.x_coord;
		y_coord = P.y_coord;
		NES_hex_code = P.NES_hex_code;
		rgb_info = P.rgb_info;
	}
}

