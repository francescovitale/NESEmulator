package Emulator.UI.DisplayBoundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Emulator.Control.ReturnedCPUState;
import java.awt.Panel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Font;

public class CPUFrame extends JFrame {

	//private ReturnedCPUState State;
	private JPanel contentPane;
	
	public Label RegALabel;
	public Label RegXLabel;
	public Label RegYLabel;
	public Label RegPCLabel;
	public Label RegSSPLabel;
	public Label RegSRLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CPUFrame frame = new CPUFrame();
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
	public CPUFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 484);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Panel RegAPanel = new Panel();
		RegAPanel.setBackground(new Color(64, 64, 64));
		RegAPanel.setBounds(10, 41, 304, 51);
		contentPane.add(RegAPanel);
		RegAPanel.setLayout(null);
		
		RegALabel = new Label("Reg A : ");
		RegALabel.setBackground(Color.GRAY);
		RegALabel.setForeground(Color.WHITE);
		RegALabel.setFont(new Font("OCR A Extended", Font.BOLD, 16));
		RegALabel.setBounds(0, 0, 304, 51);
		RegAPanel.add(RegALabel);
		
		Panel RegXPanel = new Panel();
		RegXPanel.setBackground(new Color(64, 64, 64));
		RegXPanel.setBounds(10, 109, 304, 51);
		contentPane.add(RegXPanel);
		RegXPanel.setLayout(null);
		
		RegXLabel = new Label("Reg X : ");
		RegXLabel.setBackground(Color.GRAY);
		RegXLabel.setForeground(Color.WHITE);
		RegXLabel.setFont(new Font("OCR A Extended", Font.BOLD, 16));
		RegXLabel.setBounds(0, 0, 304, 51);
		RegXPanel.add(RegXLabel);
		
		Panel RegYPanel = new Panel();
		RegYPanel.setBackground(Color.DARK_GRAY);
		RegYPanel.setBounds(10, 176, 304, 51);
		contentPane.add(RegYPanel);
		RegYPanel.setLayout(null);
		
		RegYLabel = new Label("Reg Y : ");
		RegYLabel.setBackground(Color.GRAY);
		RegYLabel.setForeground(Color.WHITE);
		RegYLabel.setFont(new Font("OCR A Extended", Font.BOLD, 16));
		RegYLabel.setBounds(0, 0, 304, 51);
		RegYPanel.add(RegYLabel);
		
		Panel RegPCPanel = new Panel();
		RegPCPanel.setBackground(Color.DARK_GRAY);
		RegPCPanel.setBounds(10, 243, 304, 51);
		contentPane.add(RegPCPanel);
		RegPCPanel.setLayout(null);
		
		RegPCLabel = new Label("Reg PC : ");
		RegPCLabel.setBackground(Color.GRAY);
		RegPCLabel.setForeground(Color.WHITE);
		RegPCLabel.setFont(new Font("OCR A Extended", Font.BOLD, 16));
		RegPCLabel.setBounds(0, 0, 304, 51);
		RegPCPanel.add(RegPCLabel);
		
		Panel RegSSPPanel = new Panel();
		RegSSPPanel.setBackground(Color.DARK_GRAY);
		RegSSPPanel.setBounds(10, 316, 304, 51);
		contentPane.add(RegSSPPanel);
		RegSSPPanel.setLayout(null);
		
		RegSSPLabel = new Label("Reg SP : ");
		RegSSPLabel.setBackground(Color.GRAY);
		RegSSPLabel.setBounds(0, 0, 304, 51);
		RegSSPPanel.add(RegSSPLabel);
		RegSSPLabel.setForeground(Color.WHITE);
		RegSSPLabel.setFont(new Font("OCR A Extended", Font.BOLD, 16));
		
		Panel RegSRPanel = new Panel();
		RegSRPanel.setBackground(Color.DARK_GRAY);
		RegSRPanel.setBounds(10, 384, 304, 51);
		contentPane.add(RegSRPanel);
		RegSRPanel.setLayout(null);
		
		RegSRLabel = new Label("Reg SR : ");
		RegSRLabel.setBackground(Color.GRAY);
		RegSRLabel.setForeground(Color.WHITE);
		RegSRLabel.setFont(new Font("OCR A Extended", Font.BOLD, 16));
		RegSRLabel.setBounds(0, 0, 304, 51);
		RegSRPanel.add(RegSRLabel);
		
		Label Title = new Label("CPU State");
		Title.setAlignment(Label.CENTER);
		Title.setForeground(Color.WHITE);
		Title.setFont(new Font("OCR A Extended", Font.BOLD, 16));
		Title.setBounds(10, 0, 304, 40);
		contentPane.add(Title);
	}
}
