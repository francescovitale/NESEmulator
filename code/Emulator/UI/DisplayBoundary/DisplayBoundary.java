package Emulator.UI.DisplayBoundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Emulator.Control.Controller;
import Emulator.Control.ReturnedState;
import Emulator.UI.EmulatorBoundary.UIConfiguration;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingConstants;
import java.awt.Panel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DisplayBoundary extends JFrame {

	private CPUFrame CPUFrame;
	private MemoryFrame MemFrame;
	private PPUFrame PPUFrame;
	
	private JPanel contentPane;
	public Screen NESDisplay;
	private UIConfiguration Configuration;
	private Controller controller;
	private Byte keys;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					DisplayBoundary frame = new DisplayBoundary(UIConfiguration.getIstance());
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public DisplayBoundary(UIConfiguration Conf) {
		setResizable(false);
		
		initComponents();
		
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		
		Configuration = Conf;
		System.out.println(Configuration.getMode());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//if(Configuration.getMode() == true) 
			setBounds(100, 100, 715, 644);
		//else setBounds(100, 100, 615, 405);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setOpaque(false);

		NESDisplay = new Screen();
		NESDisplay.setLocation(101, 11);
		contentPane.add(NESDisplay);
		
		JButton CPUButton = new JButton("CPU");
		CPUButton.setBounds(282, 542, 125, 28);
		contentPane.add(CPUButton);
		CPUButton.setForeground(Color.RED);
		CPUButton.setFont(new Font("OCR A Extended", Font.BOLD, 11));
		CPUButton.setBackground(UIManager.getColor("Button.background"));
		
		JButton PPUButton = new JButton("PPU");
		PPUButton.setBounds(169, 542, 103, 28);
		contentPane.add(PPUButton);
		PPUButton.setFont(new Font("OCR A Extended", Font.BOLD, 11));
		PPUButton.setForeground(Color.RED);
		PPUButton.setBackground(UIManager.getColor("Button.background"));
		
		JButton MemButton = new JButton("Memory");
		MemButton.setBounds(417, 542, 125, 28);
		contentPane.add(MemButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(155, 517, 425, 70);
		panel.setOpaque(true);
		contentPane.add(panel);
		MemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemFrame.setVisible(true);
			}
		});
		CPUButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CPUFrame.setVisible(true);
				
			}
		});
			
	}

	private void initComponents() {
		CPUFrame = new CPUFrame();
		PPUFrame = new PPUFrame();
		MemFrame = new MemoryFrame();
		controller = new Controller();
		
	}

	public CPUFrame getCPUFrame() {
		return CPUFrame;
	}

	public void setCPUFrame(CPUFrame cPUFrame) {
		CPUFrame = cPUFrame;
	}

	public MemoryFrame getMemFrame() {
		return MemFrame;
	}

	public void setMemFrame(MemoryFrame memFrame) {
		MemFrame = memFrame;
	}

	public PPUFrame getPPUFrame() {
		return PPUFrame;
	}

	public void setPPUFrame(PPUFrame pPUFrame) {
		PPUFrame = pPUFrame;
	}

	public void UpdateDisplayBoundary(ReturnedState instance) {
		UpdateCPUState(instance);
		UpdateMemState(instance);
		UpdatePPUState(instance);
		
	}
	
	public void UpdateDisplayScreen(ReturnedState instance) {
		NESDisplay.RenderScreen(instance);

	}

	private void UpdateCPUState(ReturnedState instance) {
		getCPUFrame().RegXLabel.setText("Reg X : " + instance.getCS().getX());
		getCPUFrame().RegALabel.setText("Reg A : " + instance.getCS().getA());
		getCPUFrame().RegYLabel.setText("Reg Y : " + instance.getCS().getY());
		getCPUFrame().RegSSPLabel.setText("Reg SP : " +instance.getCS().getSP());
		getCPUFrame().RegPCLabel.setText("Reg PC : " + Integer.toHexString(instance.getCS().getPC()));
		getCPUFrame().RegSRLabel.setText("Reg SR : " + instance.getCS().getSR());
		
	}
	
	public void getKey() {
		this.setFocusable(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case 88 : 
					keys = (byte)0x80;
					//System.out.println("X");
					break;
				case 90 : 
					keys = (byte)0x40;
					//System.out.println("Z");
					break;
				case 65 : 
					keys = (byte)0x20;
					//System.out.println("A");
					break;
				case 83 : 
					keys = (byte)0x10;
					//System.out.println("S");
					break;
				case 38 : 
					keys = (byte)0x08;
					//System.out.println("UP");
					break;
				case 40 : 
					keys = (byte)0x04;
					//System.out.println("DOWN");
					break;
				case 37 : 
					keys = (byte)0x02;
					//System.out.println("LEFT");
					break;
				case 39 : 
					keys = (byte)0x01;
					//System.out.println("RIGHT");
					break;
				default:
					keys = (byte)0x00;
					break;
				}
				
				controller.setkeys(keys);	
			}	
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				controller.setkeys((byte)0x00);	
			}	
		});
	}
	
	public void UpdateMemState(ReturnedState instance) {
		
	}
	
	public void UpdatePPUState(ReturnedState instance) {
		
	}
}
