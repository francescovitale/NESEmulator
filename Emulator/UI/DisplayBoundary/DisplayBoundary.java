package Emulator.UI.DisplayBoundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Emulator.UI.EmulatorBoundary.UIConfiguration;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingConstants;
import java.awt.Panel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DisplayBoundary extends JFrame {

	private CPUFrame CPUFrame;
	private MemoryFrame MemFrame;
	private PPUFrame PPUFrame;
	
	private JPanel contentPane;
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
		
		initComponents();
		
		Configuration = Conf;
		System.out.println(Configuration.getMode());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//if(Configuration.getMode() == true) 
			setBounds(100, 100, 615, 584);
		//else setBounds(100, 100, 615, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Panel panel = new Panel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(10, 10, 579, 347);
		contentPane.add(panel);
		
		JButton CPUButton = new JButton("CPU");
		CPUButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CPUFrame.setVisible(true);
			}
		});
		CPUButton.setBounds(10, 383, 173, 65);
		contentPane.add(CPUButton);
		
		JButton MemButton = new JButton("Memory");
		MemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemFrame.setVisible(true);
			}
		});
		MemButton.setBounds(193, 383, 173, 65);
		contentPane.add(MemButton);
		
		JButton PPUButton = new JButton("PPU");
		PPUButton.setBounds(376, 383, 173, 65);
		contentPane.add(PPUButton);
			
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
