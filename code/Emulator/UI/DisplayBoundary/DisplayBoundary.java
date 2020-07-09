package Emulator.UI.DisplayBoundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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

public class DisplayBoundary extends JFrame {

	private CPUFrame CPUFrame;
	private MemoryFrame MemFrame;
	private PPUFrame PPUFrame;
	
	private JPanel contentPane;
	public Screen NESDisplay;
	private UIConfiguration Configuration;

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
}
