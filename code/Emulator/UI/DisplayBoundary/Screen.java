package Emulator.UI.DisplayBoundary;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import Emulator.Control.ReturnedState;

public class Screen extends JPanel {

	public Byte VRAM[][] = new Byte[256][240];
	public Integer current_x;
	public Integer current_y;
	private Integer width = 256;
	private Integer height = 240;
	private ArrayList<String> PaletteSet;
	
	public Screen() {
		
		setBackground(Color.DARK_GRAY);
		setBounds(173, 11, width , height);
		current_x = 0;
		current_y = 0;
		
		for(int i = 0; i < 256; i++) {
			for(int j = 0; j < 240; j++) {
				VRAM[i][j] = 0x00;
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);


		for(int i = 0; i < 256; i++) {
			for(int j = 0; j < 240; j++) {
				g.setColor(Color.decode(ParseColor(i,j)));
				g.fillRect(i, j ,1, 1);
			}
		}

		
	}

	private String ParseColor(int i, int j) {
		
		String hexcolor = new String();
		
		switch(VRAM[i][j]) {
		case 0:
			hexcolor = "#000000";
			break;
		case 1:
			hexcolor = "#000000";
			break;
		case 2:
			hexcolor = "#ffffff";
			break;
		case 3:
			hexcolor = "#f878f8";
			break;
		case 4:
			hexcolor = "#fcfcfc";
			break;	
		case 5:
			hexcolor = "#9878f8";
			break;
		case 6:
			hexcolor = "#0078f8";
			break;		
		case 7:
			hexcolor = "#f8b800";
			break;
			
		}
			
		return hexcolor;
	}

	public void RenderScreen(ReturnedState instance) {
		int R = (int)(Math.random()*256);
		int G = (int)(Math.random()*256);
		int B= (int)(Math.random()*256);
		Color color = new Color(R, G, B); //random color, but can be bright or dull

		//to get rainbow, pastel colors
		Random random = new Random();
		byte value = (byte)Math.abs((random.nextInt(3)));
		if(value == 0) value = 1;
		this.VRAM[current_x][current_y] = value;
		this.repaint();
		
		//System.out.println(current_x + " " + current_y);
		current_x+=1;
		if(current_x == 256) {
			current_x = 0;
			current_y += 1;
			if(current_y == 240) {
				current_y = 0;
			}
		}
		
	}


}
