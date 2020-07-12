package Emulator.UI.EmulatorBoundary;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Panel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

import javax.swing.JList;
import javax.swing.border.LineBorder;
import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.ListSelectionModel;

import Emulator.Control.*;
import Emulator.UI.DisplayBoundary.*;

public class EmulatorBoundary extends JFrame {

	private Controller Controller;
	private UIConfiguration Configuration;
	private SwingWorker<Void,Void> Logic;
	private SwingWorker<Void,ReturnedState> Polling;
	private DisplayBoundary Display;
	
	private JPanel contentPane;
	private JList ProgramList;
	private Panel MenuPanel;
	private Panel SelectProgramPanel;
	private Panel InsertListPanel;
	private Panel RemoveListPanel;
	private Panel StartProgramPanel;
	private JLabel ModeLabel;
	private JFileChooser fileChooser;
	
	private Boolean Mode;
	private JLabel IconLabel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmulatorBoundary frame = new EmulatorBoundary();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public EmulatorBoundary() {
		
		initComponents();
		initList();
		initMenu();

	}
	
	private void initMenu() {
		
		Panel ModePanel = new Panel();
		
		MenuPanel.setBackground(Color.GRAY);
		MenuPanel.setBounds(0, 0, 183, 408);
		contentPane.add(MenuPanel);
		MenuPanel.setLayout(null);
		
		ModePanel.setBackground(Color.LIGHT_GRAY);
		ModePanel.setBounds(0, 113, 183, 38);
		MenuPanel.add(ModePanel);
		ModePanel.setLayout(null);
		
		ModeLabel.setBounds(10, 11, 163, 22);
		ModeLabel.setForeground(Color.GRAY);
		ModeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ModeLabel.setFont(new Font("OCR A Extended", Font.BOLD, 14));
		ModePanel.add(ModeLabel);
		
		SelectProgramPanel.setBackground(Color.LIGHT_GRAY);
		SelectProgramPanel.setBounds(0, 153, 183, 38);
		MenuPanel.add(SelectProgramPanel);
		SelectProgramPanel.setLayout(null);
		
		JLabel SelectProgramLabel = new JLabel("Seleziona Programma");
		SelectProgramLabel.setHorizontalAlignment(SwingConstants.CENTER);
		SelectProgramLabel.setForeground(Color.GRAY);
		SelectProgramLabel.setFont(new Font("OCR A Extended", Font.BOLD, 14));
		SelectProgramLabel.setBounds(10, 11, 163, 22);
		SelectProgramPanel.add(SelectProgramLabel);
		
		InsertListPanel.setBackground(Color.LIGHT_GRAY);
		InsertListPanel.setBounds(0, 193, 183, 38);
		MenuPanel.add(InsertListPanel);
		InsertListPanel.setLayout(null);
		
		JLabel InsertListLabel = new JLabel("Inserisci Programma");
		InsertListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		InsertListLabel.setForeground(Color.GRAY);
		InsertListLabel.setFont(new Font("OCR A Extended", Font.BOLD, 14));
		InsertListLabel.setBounds(10, 11, 163, 22);
		InsertListPanel.add(InsertListLabel);
		
		RemoveListPanel.setBackground(Color.LIGHT_GRAY);
		RemoveListPanel.setBounds(0, 233, 183, 38);
		MenuPanel.add(RemoveListPanel);
		RemoveListPanel.setLayout(null);
		
		JLabel RemoveListLabel = new JLabel("Elimina Programma");
		RemoveListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RemoveListLabel.setForeground(Color.GRAY);
		RemoveListLabel.setFont(new Font("OCR A Extended", Font.BOLD, 14));
		RemoveListLabel.setBounds(10, 11, 163, 22);
		RemoveListPanel.add(RemoveListLabel);
		
		StartProgramPanel.setLayout(null);
		StartProgramPanel.setBackground(Color.LIGHT_GRAY);
		StartProgramPanel.setBounds(0, 272, 183, 38);
		MenuPanel.add(StartProgramPanel);
		
		JLabel StartProgramLabel = new JLabel("Avvia Programma");
		StartProgramLabel.setHorizontalAlignment(SwingConstants.CENTER);
		StartProgramLabel.setForeground(Color.GRAY);
		StartProgramLabel.setFont(new Font("OCR A Extended", Font.BOLD, 14));
		StartProgramLabel.setBounds(10, 11, 163, 22);
		StartProgramPanel.add(StartProgramLabel);
		
		IconLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/Emulator/nes_icon.jpg")).getImage();
		Image newimg = img.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);
		IconLabel.setIcon(new ImageIcon(newimg));
		IconLabel.setBounds(32, 0, 120, 107);
		MenuPanel.add(IconLabel);
		
		ModePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ModePanel.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				ModePanel.setBackground(Color.lightGray);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(Mode == false) {
					Mode = true;
					ModeLabel.setText("Mode : Programmatore");
				}
				else{
					Mode = false;
					ModeLabel.setText("Mode : Utente");
				}
				
			}
		});
		
		SelectProgramPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				SelectProgramPanel.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				SelectProgramPanel.setBackground(Color.lightGray);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				int option = fileChooser.showOpenDialog(EmulatorBoundary.this);
				if(option == JFileChooser.APPROVE_OPTION ) {
					String FileName = (fileChooser.getSelectedFile().getName());
					String Path = (fileChooser.getSelectedFile().getPath());
					
					Configuration.setProgramName(FileName);
					Configuration.setPath(Path);
					Configuration.setMode(Mode);
					
					Controller.executeProgram(FileName,-1,Path);
				}
			}
		});
		
		InsertListPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				InsertListPanel.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				InsertListPanel.setBackground(Color.lightGray);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				int option = fileChooser.showOpenDialog(EmulatorBoundary.this);
				if(option == JFileChooser.APPROVE_OPTION ) {
					String FileName = (fileChooser.getSelectedFile().getName());
					String Path = (fileChooser.getSelectedFile().getPath());
					Controller.LocalRepositoryRequest(FileName,0,0,Path);
				}
			}
		});
		
		RemoveListPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				RemoveListPanel.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				RemoveListPanel.setBackground(Color.lightGray);
			}
			public void mouseClicked(MouseEvent e) {
				
				if(ProgramList.getSelectedIndex() != -1) {
					String ProgramName = ProgramList.getSelectedValue().toString().substring(5,ProgramList.getSelectedValue().toString().length());
					Integer ID = Integer.parseInt(ProgramList.getSelectedValue().toString().substring(0, 2));
					
					Controller.LocalRepositoryRequest(ProgramName,ID,1,"");
				}
			}
		});
		
		StartProgramPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				StartProgramPanel.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				StartProgramPanel.setBackground(Color.lightGray);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(ProgramList.getSelectedIndex() != -1) {
					String ProgramName = ProgramList.getSelectedValue().toString().substring(5,ProgramList.getSelectedValue().toString().length());
					Integer ID = Integer.parseInt(ProgramList.getSelectedValue().toString().substring(0, 2));
					
					Configuration.setProgramName(ProgramName);
					Configuration.setProgramID(ID);
					Configuration.setMode(Mode);
					AvviaProgramma();
				}
			}
		});
		
	}

	private void initComponents() {
		Controller = new Controller();
		fileChooser = new JFileChooser();
		Configuration = UIConfiguration.getIstance();
		ProgramList = new JList();
		MenuPanel = new Panel();
		ModeLabel = new JLabel("Mode : Utente");
		SelectProgramPanel = new Panel();
		InsertListPanel = new Panel();
		RemoveListPanel = new Panel();
		StartProgramPanel = new Panel();
		
	}

	public void initList() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 559, 447);
		Mode = false; //Utente
		//setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ProgramList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ProgramList.setForeground(Color.WHITE);
		ProgramList.setFont(new Font("OCR A Extended", Font.BOLD, 18));
		ProgramList.setModel(new AbstractListModel() {
			String[] values = new String[] {"01 - Mario Bros.iNES", "02 - Carmine Marra.iNES", "03 - Add6502.iNES"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		ProgramList.setBorder(new LineBorder(Color.GRAY, 2));
		ProgramList.setBackground(Color.DARK_GRAY);
		ProgramList.setBounds(189, 11, 344, 300);
		contentPane.add(ProgramList);
	}
	
	public void AvviaProgramma() {
		Display = new DisplayBoundary(Configuration);
		Display.setVisible(true);
		
		CreateApplicationLogic();
		CreatePollingEntity();
		
		Logic.execute();
		Polling.execute();
	}
	
	public void CreatePollingEntity() {
			Polling = new SwingWorker<Void,ReturnedState>(){
			@Override
			protected Void doInBackground() throws Exception {
				int i = 0;
				while(i == 0) {
					/*synchronized(Controller.getEmulatorFacade().getSF().getState().getInstance()) {
						while(Controller.getState() == false) {
							//System.out.println("C) Mi blocco su " + Controller.getEmulatorFacade().getSF().getState().getIstance().toString());
							Controller.getEmulatorFacade().getSF().getState().getInstance().wait();
						}
						//System.out.println("C) Mi sblocco su " + Controller.getEmulatorFacade().getSF().getState().getIstance().toString());
						publish((Controller.getReturnedState()));
						Controller.getEmulatorFacade().getSF().getState().getInstance().notify();
					}*/
					
						Controller.getState();
						Display.UpdateDisplayScreen(Controller.getReturnedState());
						publish((Controller.getReturnedState()));

				}
				return null;
			}

			@Override
			protected void process(List<ReturnedState> chunks) {
					Display.UpdateDisplayBoundary(Controller.getReturnedState());
			}
						
		};
	}
	
	public void CreateApplicationLogic() {
		    Logic = new SwingWorker<Void,Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				
			//Controller.executeProgram(Configuration.getProgramName(), Configuration.getProgramID(), Configuration.getPath());
			Controller.executeProgram(Configuration.getProgramName(), Configuration.getProgramID(), "C:\\Users\\aceep\\eclipse-workspace\\NES\\src\\Emulator\\programmi di prova\\Balloon Fight (USA).ines");
			
			return null;

			}
						
		};
	}
}
