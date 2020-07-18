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
import java.util.ArrayList;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EmulatorBoundary extends JFrame {

	private Controller Controller;
	private UIConfiguration Configuration;
	private SwingWorker<Void,Void> Logic;
	private SwingWorker<Void,ReturnedState> Polling;
	private SwingWorker<Void,Void> UserInput;
	private DisplayBoundary Display;
	
	private JPanel contentPane;
	private JList<String> ProgramList;
	private Panel MenuPanel;
	private Panel SelectProgramPanel;
	private Panel InsertListPanel;
	private Panel RemoveListPanel;
	private Panel StartProgramPanel;
	private JLabel ModeLabel;
	private JFileChooser fileChooser;
	private ArrayList<ReturnedProgram> LocalList = new ArrayList<ReturnedProgram>();
	
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
					Configuration.setProgramID(-1);
					
					AvviaProgramma();
					
					//Controller.executeProgram(FileName,-1,Path);
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
					String Name = (fileChooser.getSelectedFile().getName());
					String Path = (fileChooser.getSelectedFile().getPath());
					Controller.InsertProgram(Path, "", -1);
					
					ReturnedProgram temp = new ReturnedProgram();
					temp.setName(Name.replace(".ines",""));
					if(LocalList.size() > 0 ) temp.setID(LocalList.get(LocalList.size()-1).getID() + 1 );
					else temp.setID(1);
					LocalList.add(temp);
					RefreshList();
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
					
					Controller.DeleteProgram(ID, ProgramName);
					ReturnedProgram temp = new ReturnedProgram();
					temp.setName(ProgramName);
					temp.setID(ID);
					LocalList.remove(ProgramList.getSelectedIndex());
					RefreshList();
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

	protected void RefreshList() {
		ProgramList.setModel(new AbstractListModel() {
			ArrayList<ReturnedProgram> value = new ArrayList<ReturnedProgram>(LocalList);
			public int getSize() {
				return value.size();
			}
			public Object getElementAt(int index) {
				if(value.get(index).getID() < 10)
					return "0"+value.get(index).getID() + " - " + value.get(index).getName();
				else return value.get(index).getID() + " - " + value.get(index).getName();
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
		LocalList = Controller.getProgramList();
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
		
		RefreshList();

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
		CreateUserInput();
		
		Logic.execute();
		Polling.execute();
		UserInput.execute();
	}
	
	public void CreatePollingEntity() {
			Polling = new SwingWorker<Void,ReturnedState>(){
			@Override
			protected Void doInBackground() throws Exception {
				int i = 0;
				int j = 0;
				
				
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
						
						/*while(j < 2) {
							j++;
							Display.getKey();	
						}*/
				}
				return null;
			}
 
			@Override
			protected void process(List<ReturnedState> chunks) {
					Display.UpdateDisplayBoundary(Controller.getReturnedState());
			}
						
		};
	}
	
	public void CreateUserInput() {
		UserInput = new SwingWorker<Void,Void>(){
		@Override
		protected Void doInBackground() throws Exception {
			Display.getKey();	

		return null;

		}
					
	};
}

	public void CreateApplicationLogic() {
		    Logic = new SwingWorker<Void,Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				
			Controller.executeProgram(Configuration.getProgramName(), Configuration.getProgramID(), Configuration.getPath(), Configuration.getMode());

			return null;

			}
						
		};
	}
}
