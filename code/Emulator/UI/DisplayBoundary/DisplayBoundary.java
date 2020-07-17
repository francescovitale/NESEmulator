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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.LineBorder;

import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.CardLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class DisplayBoundary extends JFrame {
	
	private JTextPane RegistersTextPane;
	private JTextPane MEMTextPane;
	
	private JPanel contentPane;
	public Screen NESDisplay;
	private UIConfiguration Configuration;
	private Controller controller;
	private Byte keys;
	private JTextField txtRange;
	private JComboBox MEMComboBox;
	private JPanel CPUPanel;
	private JPanel PPUPanel;
	
	private int RegShow = 0;

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
		setBackground(new Color(0, 0, 128));
		setResizable(false);
		
		initComponents();

		Configuration = Conf;
		System.out.println(Configuration.getMode());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//if(Configuration.getMode() == true) //MODE PROGRAMMER
			setBounds(100, 100, 1193, 760);
		//else setBounds(100, 100, 3*(240 + 21), 3*(252)); //MODE USER
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setOpaque(false);

		NESDisplay = new Screen();
		NESDisplay.setLocation(0, 0);
		contentPane.add(NESDisplay);
		
		Panel PlayPanel = new Panel();
		PlayPanel.setBounds(774, 10, 48, 42);
		contentPane.add(PlayPanel);
		
		Panel ResumePanel = new Panel();
		ResumePanel.setBounds(828, 10, 48, 42);
		contentPane.add(ResumePanel);
		
		Panel StopPanel = new Panel();
		StopPanel.setBounds(882, 10, 48, 42);
		contentPane.add(StopPanel);
		
		Panel ResetPanel = new Panel();
		ResetPanel.setBounds(936, 10, 48, 42);
		contentPane.add(ResetPanel);
		
		CPUPanel = new JPanel();
		CPUPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CPUPanel.setBackground(new Color(100, 149, 237));
				PPUPanel.setBackground(new Color(0, 0, 128));
				RegShow = 0;
			}
		});
		CPUPanel.setForeground(new Color(255, 255, 255));
		CPUPanel.setBackground(new Color(100, 149, 237));
		CPUPanel.setBounds(774, 58, 102, 42);
		contentPane.add(CPUPanel);
		CPUPanel.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		
		JLabel CPULabel = new JLabel("CPU");
		CPULabel.setHorizontalAlignment(SwingConstants.CENTER);
		CPULabel.setFont(new Font("OCR A Extended", Font.BOLD, 30));
		CPULabel.setForeground(new Color(255, 255, 255));
		CPUPanel.add(CPULabel);
		
		PPUPanel = new JPanel();
		PPUPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PPUPanel.setBackground(new Color(100, 149, 237));
				CPUPanel.setBackground(new Color(0, 0, 128));
				RegShow = 1;
			}
		});
		PPUPanel.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		PPUPanel.setBackground(new Color(0, 0, 128));
		PPUPanel.setBounds(882, 58, 102, 42);
		contentPane.add(PPUPanel);
		
		JLabel PPULabel = new JLabel("PPU");
		PPULabel.setHorizontalAlignment(SwingConstants.CENTER);
		PPULabel.setForeground(Color.WHITE);
		PPULabel.setFont(new Font("OCR A Extended", Font.BOLD, 30));
		PPUPanel.add(PPULabel);
		
		JPanel APUPanel = new JPanel();
		APUPanel.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		APUPanel.setBackground(new Color(0, 0, 128));
		APUPanel.setBounds(990, 58, 102, 42);
		contentPane.add(APUPanel);
		
		JLabel APULabel = new JLabel("APU");
		APULabel.setHorizontalAlignment(SwingConstants.CENTER);
		APULabel.setForeground(Color.WHITE);
		APULabel.setFont(new Font("OCR A Extended", Font.BOLD, 30));
		APUPanel.add(APULabel);
		
		JPanel RegistersPanel = new JPanel();
		RegistersPanel.setForeground(new Color(255, 255, 255));
		RegistersPanel.setBackground(new Color(0, 0, 128));
		RegistersPanel.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		RegistersPanel.setBounds(774, 106, 318, 139);
		contentPane.add(RegistersPanel);
		RegistersPanel.setLayout(null);
		
		JPanel MEMSelectorPanel = new JPanel();
		MEMSelectorPanel.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		MEMSelectorPanel.setBackground(new Color(0, 0, 128));
		MEMSelectorPanel.setBounds(774, 251, 156, 24);
		contentPane.add(MEMSelectorPanel);
		MEMSelectorPanel.setLayout(new CardLayout(0, 0));
		
		MEMComboBox = new JComboBox();
		MEMComboBox.setFocusable(false);
		MEMComboBox.setModel(new DefaultComboBoxModel(new String[] {"RAM", "VRAM", "Palette RAM", "OAM"}));
		MEMComboBox.setForeground(new Color(255, 255, 255));
		MEMComboBox.setBorder(null);
		MEMComboBox.setFont(new Font("OCR A Extended", Font.PLAIN, 11));
		MEMComboBox.setBackground(new Color(0, 0, 128));
		MEMSelectorPanel.add(MEMComboBox, "name_58506618704500");
		
		JPanel MEMPanel = new JPanel();
		MEMPanel.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		MEMPanel.setBackground(new Color(0, 0, 128));
		MEMPanel.setBounds(774, 281, 396, 439);
		contentPane.add(MEMPanel);
		MEMPanel.setLayout(null);
		
		MEMTextPane = new JTextPane();
		MEMTextPane.setBackground(new Color(0, 0, 128));
		MEMTextPane.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 15));
		MEMTextPane.setForeground(new Color(255, 255, 255));
		MEMTextPane.setBounds(10, 11, 376, 417);
		MEMPanel.add(MEMTextPane);
		
		RegistersTextPane = new JTextPane();
		RegistersTextPane.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 15));
		RegistersTextPane.setBackground(new Color(0, 0, 128));
		RegistersTextPane.setForeground(new Color(255, 255, 255));
		RegistersTextPane.setBounds(778, 106, 310, 139);
		contentPane.add(RegistersTextPane);
		
		JPanel MEMRangePanel = new JPanel();
		MEMRangePanel.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		MEMRangePanel.setBackground(new Color(0, 0, 128));
		MEMRangePanel.setBounds(932, 251, 156, 24);
		contentPane.add(MEMRangePanel);
		MEMRangePanel.setLayout(new CardLayout(0, 0));
		
		txtRange = new JTextField();
		txtRange.setHorizontalAlignment(SwingConstants.CENTER);
		txtRange.setFont(new Font("OCR A Extended", Font.PLAIN, 13));
		txtRange.setText("0x0000");
		txtRange.setBorder(null);
		txtRange.setForeground(new Color(255, 255, 255));
		txtRange.setBackground(new Color(0, 0, 128));
		MEMRangePanel.add(txtRange, "name_58465746839300");
		txtRange.setColumns(10);
			
	}

	private void initComponents() {
		controller = new Controller();
		
	}

	public void UpdateDisplayBoundary(ReturnedState instance) {
		if(RegShow == 0)
			UpdateCPUState(instance);
		else if(RegShow == 1)
			UpdatePPUState(instance);
		UpdateMemState(instance);

		
	}
	
	public void UpdateDisplayScreen(ReturnedState instance) {
		NESDisplay.RenderScreen(instance);

	}

	private void UpdateCPUState(ReturnedState instance) {
		
		String Flags = "                  " + "NVUBDIZC\n";
		String RegSR = "Status Register : " + String.format("%8s",Integer.toBinaryString(Byte.toUnsignedInt(instance.getCS().getSR()))).replace(" ","0") + "\n\n";

		String RegX = "Register X : $" + Integer.toHexString(Byte.toUnsignedInt(instance.getCS().getX())) + "\n";
		String RegY = "Register Y : $" + Integer.toHexString(Byte.toUnsignedInt(instance.getCS().getY())) + "\n";
		String RegA = "Register A : $" + Integer.toHexString(Byte.toUnsignedInt(instance.getCS().getA())) + "\n";
		String RegSP = "Stack Pointer : $" + Integer.toHexString(Byte.toUnsignedInt(instance.getCS().getSP())) + "\n";
		String RegPC = "Program Counter : $" + Integer.toHexString(instance.getCS().getPC()) + "\n";
		
		RegistersTextPane.setText(Flags + RegSR + RegX + RegY + RegA + RegSP + RegPC);
		
	}
	 
	private void UpdateRAM(ReturnedState instance)
	{

		int addr = Integer.decode(txtRange.getText());
		int max_addr = 0x1FFF;
		String Memory = "";
		
		for(int i = 0; i < 26; i++) {
			Memory += "0x"+String.format("%4s",Integer.toHexString((char)addr)).replace(" ","0") + " : ";
			for(int j = addr; j < addr + 8; j++) {
				if(addr < max_addr) Memory += String.format("%2s",Integer.toHexString(Byte.toUnsignedInt(instance.getMS().getRAM().get(j)))).replace(" ","0") +" ";
				else Memory += "xx" + " ";
			}
			Memory += "\n";
			addr += 8;
		}
		MEMTextPane.setText(Memory);
	}
	private void UpdateVRAM(ReturnedState instance)
	{
		int addr = Integer.decode(txtRange.getText());
		int max_addr = 0x0400;
		String Memory = "NAMETABLE 1\n";
		for(int i = 0; i < 12; i++) {
			Memory += "0x"+String.format("%4s",Integer.toHexString((char)addr)).replace(" ","0") + " : ";
			for(int j = addr; j < addr + 8; j++) {
				if(addr < max_addr) Memory += String.format("%2s",Integer.toHexString(Byte.toUnsignedInt(instance.getVM().getNameTable1().get(j)))).replace(" ","0") +" ";
				else Memory += "xx" + " ";
			}
			Memory += "\n";
			addr += 8;
		}

		max_addr = 0x0400;
		Memory += "NAMETABLE 2\n";
		for(int i = 0; i < 13; i++) {
			Memory += "0x"+String.format("%4s",Integer.toHexString((char)addr)).replace(" ","0") + " : ";
			for(int j = addr; j < addr + 8; j++) {
				if(addr < max_addr) Memory += String.format("%2s",Integer.toHexString(Byte.toUnsignedInt(instance.getVM().getNameTable2().get(j)))).replace(" ","0") +" ";
				else Memory += "xx" + " ";
			}
			Memory += "\n";
			addr += 8;
		}
		MEMTextPane.setText(Memory);
	}
	private void UpdatePRAM(ReturnedState instance)
	{
		int addr = Integer.decode(txtRange.getText());
		int max_addr = 0x20;
		String Memory = "";
		for(int i = 0; i < 26; i++) {
			Memory += "0x"+String.format("%4s",Integer.toHexString((char)addr)).replace(" ","0") + " : ";
			for(int j = addr; j < addr + 8; j++) {
				if(addr < max_addr) Memory += String.format("%2s",Integer.toHexString(Byte.toUnsignedInt(instance.getPM().getPalette().get(j)))).replace(" ","0") +" ";
				else Memory += "xx" + " ";
			}
			Memory += "\n";
			addr += 8;
		}
		MEMTextPane.setText(Memory);
		
	}
	private void UpdateOAM(ReturnedState instance)
	{
		int addr = Integer.decode(txtRange.getText());
		int max_addr = 0x0100;
		String Memory = "";
		
		for(int i = 0; i < 26; i++) {
			Memory += "0x"+String.format("%4s",Integer.toHexString((char)addr)).replace(" ","0") + " : ";
			for(int j = addr; j < addr + 8; j++) {
				if(addr < max_addr) Memory += String.format("%2s",Integer.toHexString(Byte.toUnsignedInt(instance.getOS().getSprites().get(j)))).replace(" ","0") +" ";
				else Memory += "xx" + " ";
			}
			Memory += "\n";
			addr += 8;
		}
		MEMTextPane.setText(Memory);
	}
	private void UpdateMemState(ReturnedState instance) {
		
		if(MEMComboBox.getSelectedItem().toString() == "RAM")
			UpdateRAM(instance);
		else if(MEMComboBox.getSelectedItem().toString() == "VRAM")
			UpdateVRAM(instance);
		else if(MEMComboBox.getSelectedItem().toString() == "OAM")
			UpdateOAM(instance);
		else if(MEMComboBox.getSelectedItem().toString() == "Palette RAM")
			UpdatePRAM(instance);
	}
	
	private void UpdatePPUState(ReturnedState instance) {
		String Status =  "Status Register  : " + String.format("%8s",Integer.toBinaryString(Byte.toUnsignedInt(instance.getPS().getPPUStatus()))).replace(" ","0") + "\n";
		String Mask =    "Mask Register    : " + String.format("%8s",Integer.toBinaryString(Byte.toUnsignedInt(instance.getPS().getPPUMask()))).replace(" ","0") + "\n";
		String Control = "Control Register : " + String.format("%8s",Integer.toBinaryString(Byte.toUnsignedInt(instance.getPS().getPPUControl()))).replace(" ","0") + "\n";
		String Address = "Address Register : " + String.format("%8s",Integer.toBinaryString(Byte.toUnsignedInt(instance.getPS().getPPUAddress()))).replace(" ","0") + "\n";
		String Data =    "Data Register    : " + String.format("%8s",Integer.toBinaryString(Byte.toUnsignedInt(instance.getPS().getPPUData()))).replace(" ","0") + "\n";
		String RegV = "Vram_addr : $" + Integer.toHexString(instance.getPS().getVram_addr()) + "\n";
		String Scan = "Scanline : " + (instance.getPS().getScanline()) + "\n";
		String Cycle ="Cycle    : " + (instance.getPS().getCycles()) + "\n";
		
		RegistersTextPane.setText(Status + Mask + Control + Address + Data + RegV + Scan + Cycle);
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
}
