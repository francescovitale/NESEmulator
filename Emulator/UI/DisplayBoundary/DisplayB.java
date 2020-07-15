package Emulator.UI.DisplayBoundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Emulator.UI.EmulatorBoundary.UIConfiguration;

import java.awt.FlowLayout;
import java.util.Random;
import java.awt.Color;
import javax.swing.JButton;

public class DisplayB extends JFrame {

	private JPanel contentPane;
	private CPUFrame CPUFrame;
	private MemoryFrame MemFrame;
	private PPUFrame PPUFrame;
	private UIConfiguration Configuration;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DisplayB frame = new DisplayB(UIConfiguration.getIstance());
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
	public DisplayB(UIConfiguration Conf) {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 426);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel Display = new JPanel();
		Display.setBackground(Color.LIGHT_GRAY);
		Display.setBounds(10, 11, 414, 239);
		contentPane.add(Display);
		
			int R = (int)(Math.random()*256);
			int G = (int)(Math.random()*256);
			int B= (int)(Math.random()*256);
			Color color = new Color(R, G, B); //random color, but can be bright or dull

			//to get rainbow, pastel colors
			Random random = new Random();
			final float hue = random.nextFloat();
			final float saturation = 0.9f;//1.0 for brilliant, 0.0 for dull
			final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
			color = Color.getHSBColor(hue, saturation, luminance);
			
			Display.setBackground(color);
			
			JButton CPUStateButton = new JButton("CPU STATE");
			CPUStateButton.setBounds(10, 278, 130, 54);
			contentPane.add(CPUStateButton);
			
			JButton MemStateButton = new JButton("MEM STATE");
			MemStateButton.setBounds(160, 278, 130, 54);
			contentPane.add(MemStateButton);
			
			JButton btnPpuState = new JButton("PPU STATE");
			btnPpuState.setBounds(307, 278, 130, 54);
			contentPane.add(btnPpuState);
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
