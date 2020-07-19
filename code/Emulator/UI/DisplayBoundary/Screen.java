package Emulator.UI.DisplayBoundary;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import Emulator.Control.ReturnedState;

public class Screen extends JPanel {

	public String VRAM[][] = new String[256][240];
	public Integer current_x;
	public Integer current_y;
	private Integer width = 256;
	private Integer height = 240;
	private Integer resize ;
	private ArrayList<String> PaletteSet;
	
	public Screen() {
		
		resize = 3;
		setBackground(Color.DARK_GRAY);
		setBounds(173, 11, width*resize, height*resize);
		current_x = 0;
		current_y = 0;
		
		for(int i = 0; i < 256; i++) {
			for(int j = 0; j < 240; j++) {
				VRAM[i][j] =  "#ffffff";
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);


		for(int i = 0; i < 256; i++) {
			for(int j = 0; j < 240; j++) {
				g.setColor(Color.decode(VRAM[i][j]));
				g.fillRect(resize*i, resize*j ,resize, resize);
			}
		}

		
	}

	private String ParseColor(int i, int j) {
		
		String hexcolor = new String();
		

			
		return hexcolor;
	}

	public void RenderScreen(ReturnedState instance) {


		for(int i = 0; i < instance.getPS().getReturnedPixels().size() && instance.getPS().getReturnedPixels().size() <= 3; i++) {	
			Integer x = instance.getPS().getReturnedPixels().get(i).getX_coord();
			Integer y = instance.getPS().getReturnedPixels().get(i).getY_coord();


			if(x >= 0 && x < 256) {
				if(y >= 0 && y < 240) {	
					String color = instance.getPS().getReturnedPixels().get(i).getRgb_info();
					if(color != "") {
				
						VRAM[x][y] = color;
						this.repaint();
					}
				}
			}
		}

		
	}


}
