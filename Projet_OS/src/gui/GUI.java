package gui;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;

import org.jfree.chart.ChartPanel;

import data.peripheral.*;
import data.processus.Processus;
import data.processus.Processuslist;
import data.drivers.*;
import process.rrobin.OperationExec;
import process.rrobin.Rrobin;
import process.traduction.*;

/**
 * work in progress:
 * mouse can be improved,
 * other keys to be added to the keyboard
 * 
 * v2 uses gridbaglayout to get more room for the virtual screen.
 * 
 * edit 2.1: panel for disk+ new keyboard w/ gridbaglayout
 * 
 * @author redouane
 * 
 *@version 2.1
 */

public class GUI extends JFrame implements Runnable{

	/*
	 * attributes
	 */
	
	
	private static final long serialVersionUID = 1L;
	
	//----------------------
	//peripherals and drivers
	//----------------------
	
	// Instance of the GUI 
	private GUI instance = this;
	
	//keyboard
	private Interaction authKbd = new Interaction();
	private Keyboard keyboard = new Keyboard("kbd1");
	private KeyboardDriver keyboardDriver = new KeyboardDriver("kbdDriver", authKbd, keyboard);
	
	//mouse
	private Interaction authMouse = new Interaction();
	private Mouse mouse = new Mouse();
	MouseDriver mouseDriver = new MouseDriver("mouseDriver", authMouse, mouse);
	
	//Screen
	private Interaction authScreen = new Interaction();
	private Screen screen= new Screen("screen");
	private ScreenDriver screenDriver = new ScreenDriver("screenDriver", authScreen, screen);
	
	// Arraylist of Processus
	Processuslist plist = new Processuslist();
	// Round robin
	Rrobin roundrobin = new Rrobin(plist, screenDriver);
	
	// Transcriptor for keyboard imput 
	Primitivetranscriptor traductor = new Primitivetranscriptor( roundrobin);
	
	//-------------------------
	//five major parts of the GUI
	//--------------------------
	private JPanel panel = new JPanel();
	private JPanel panprocess = new JPanel();
	private JPanel pandisk = new JPanel();
	private MouseGUI mousegui = new MouseGUI();
	private KeyboardGUI keyboardgui = new KeyboardGUI();
	
	//elements to display info on screen and for processes/hdd display (top of the gridlayout)
	
	private JTextArea affichecran = new JTextArea();
	private JTextArea invitecomm = new JTextArea();
	private JEditorPane affichprocess= new JEditorPane();
	private JTextArea affichdisk= new JTextArea();

	//scroll for the process display
	private JScrollPane scrollprocess = new JScrollPane(affichprocess,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	//scroll for the disk
	private JScrollPane scrolldisk = new JScrollPane(affichdisk,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	//scroll for the screen
	private JScrollPane scroll = new JScrollPane(affichecran,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	//charts
	PieChart pchart = new PieChart();
	
	
	// HTML generator for the processus table
	ProcTable ptable = new ProcTable();


	/*
	 * methods
	 */
	
	public GUI() {
		this.setTitle("OS Simulation");
		init();
	}
	
	
	// -------------
	// init method 
	// -----------
	
	public void init() {
		
		// Main window
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		contentPane.add(panel );
		panel.setPreferredSize(new Dimension(800,450));
		contentPane.add(pandisk);
		pandisk.setPreferredSize(new Dimension(200,450));
		contentPane.add(panprocess);
		panprocess.setPreferredSize(new Dimension(200,450));
		affichprocess.setContentType("text/html"); ;  

		keyboardgui.getPankeybrd().setPreferredSize(new Dimension(800, 200));
		contentPane.add(keyboardgui.getPankeybrd());
		
		mousegui.getPanel().setPreferredSize(new Dimension(400, 200));
		contentPane.add(mousegui.getPanel());
		
	
		
		
		/*contentPane.setLayout(new GridBagLayout());
		GridBagConstraints gridcons = new GridBagConstraints();
		gridcons.fill=GridBagConstraints.BOTH;
		gridcons.insets = new Insets(5, 5, 5, 5);

		
		//adding the five parts to the frame
		
		//screen
		gridcons.weightx=2;
		gridcons.weighty=2;
		gridcons.gridx=0;
		gridcons.gridy=0;
		gridcons.gridheight=4;
		gridcons.gridwidth=2;
		contentPane.add(panel, gridcons);
		
		//disk
		gridcons.weightx=1;
		gridcons.weighty=1;
		gridcons.gridx=3;
		gridcons.gridy=0;
		gridcons.gridheight=4;
		gridcons.gridwidth=1;
		contentPane.add(pandisk, gridcons);
		
		//processes
		gridcons.gridx=4;
		gridcons.gridy=0;
		gridcons.gridheight=4;
		gridcons.gridwidth=1;
		contentPane.add(panprocess, gridcons);
		
		//keyboard
		gridcons.weightx=0;
		gridcons.weighty=0;
		gridcons.gridx=0;
		gridcons.gridy=4;
		gridcons.gridheight=2;
		gridcons.gridwidth=2;
		contentPane.add(keyboardgui.getPankeybrd(), gridcons);
		
		//mouse
		gridcons.gridx=3;
		gridcons.gridy=4;
		gridcons.gridheight=2;
		gridcons.gridwidth=2;
		gridcons.weightx=0;
		gridcons.weighty=0;
		contentPane.add(mousegui.getPanel(), gridcons);
		*/
		
		//adjusting the panels of each five parts:
		
		//screen
		affichecran.setEditable(false);
		//affichprocess.setEditable(false);
		scroll.setAutoscrolls(true);
		
		
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gcscreen= new GridBagConstraints();
		
		
		panel.setBorder(BorderFactory.createTitledBorder("Screen"));
		invitecomm.setBorder(BorderFactory.createTitledBorder("type here"));

		gcscreen.fill=GridBagConstraints.BOTH;
		gcscreen.gridx=0;
		gcscreen.gridy=0;
		gcscreen.weighty=1;
		gcscreen.weightx=1;
		gcscreen.gridheight=10;
		gcscreen.gridwidth=10;
		panel.add(scroll, gcscreen);
		gcscreen.weighty=0;
		gcscreen.gridx=0;
		gcscreen.gridy=10;
		gcscreen.gridheight=GridBagConstraints.REMAINDER;
		gcscreen.gridwidth=10;
		panel.add(invitecomm, gcscreen);
		
		panel.setBackground(Color.BLACK);
		scroll.setVisible(false);
		invitecomm.setVisible(false);
		
		//pan process
		
		//affichprocess.setEditable(false);
		panprocess.add(scrollprocess);
		panprocess.setBorder(BorderFactory.createTitledBorder("Processus"));
		panprocess.setLayout(new GridLayout(1,1));
		
		//pandisk
		
		affichdisk.setEditable(false);
		
		pandisk.setBorder(BorderFactory.createTitledBorder("Instruments "));
		pandisk.setLayout(new BorderLayout());
		ChartPanel chartpan1= new ChartPanel(pchart.getCpuPie(), 200, 225, 150,150, 200, 200, true, true, false, false, false, true);
		ChartPanel chartpan2= new ChartPanel(pchart.getSlotPie(), 200, 225, 150,150, 200, 200, true, true, false, false, false, true);
		pandisk.add(chartpan1, BorderLayout.NORTH );
		pandisk.add(chartpan2, BorderLayout.SOUTH );
		
		
		DefaultCaret caret = (DefaultCaret)affichecran.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		//pan keyboard (version 2, uses gridbaglayout)
		//and pan Mouse are now handled in keyboardgui class
		
		
		//action listeners
		invitecomm.addKeyListener(new EnterKeyAction());
		
		//for the keyboard
		mousegui.getEnter().addActionListener(new EnterAction());
		keyboardgui.getSpace().addActionListener(new KeyLetter());
		keyboardgui.getMultiplyKey().addActionListener(new KeyLetter());
		keyboardgui.getAddKey().addActionListener(new KeyLetter());
		keyboardgui.getSubstractKey().addActionListener(new KeyLetter());
		keyboardgui.getDivideKey().addActionListener(new KeyLetter());
		keyboardgui.getEqualSign().addActionListener(new KeyLetter());
		keyboardgui.getParenthesisOpen().addActionListener(new KeyLetter());
		keyboardgui.getParenthesisClose().addActionListener(new KeyLetter());
		
		
		for(int i=0; i<4;i++) {
			for(int j=0; j<10;j++) {
				JButtonKey[][] temp = keyboardgui.getRows();
				temp[i][j].addActionListener(new KeyLetter());
			}
		
		
		
			//for the mouse and the turn on button of the screen
			mousegui.getStartstop().addActionListener(new StartandStopAction());
			
			
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		Updatevalues();
		setVisible(true);
	
	}
	
	
	public void Updatevalues() {
		affichecran.setText(" ");
		affichecran.setText(screenDriver.toString());
		int activeprocposition = roundrobin.getActiveprocposition();
		Processuslist plist = roundrobin.getPlist();
		ptable.refreshProcTable(plist , activeprocposition);
		try {
			affichprocess.setPage("file:./tab.html");
			Document doc = affichprocess.getDocument();
			doc.putProperty(Document.StreamDescriptionProperty, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		do {
			roundrobin.RrobinUnit();
			Updatevalues();
		}while(roundrobin.getPlist().getProcessuslist().size() > 0);
	}
	
	
	private class EnterAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			//affichecran.setText(affichecran.getText() + "\n" + invitecomm.getText());
			//System.out.println(invitecomm.getText());
			
			//screenDriver.resetScreen();
			
			traductor.transcriptor(invitecomm.getText(), screenDriver);
			keyboard.resetContent();
			invitecomm.setText(null);

			if(roundrobin.getBuffer().getProcessuslist().size() > 0) {
				Thread th = new Thread(instance);
				if(!th.isAlive()) {
					th.start();
				}
			}
			else {
				Updatevalues();
			}
			
			/*
			keyboard.resetContent();
			invitecomm.setText(null);
			affichecran.setText(" ");
			affichecran.setText(screenDriver.toString());
			*/
			
		}
	}

	private class KeyLetter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String tmp =((JButtonKey)e.getSource()).getNum();
			keyboard.keyinput(tmp);
			invitecomm.setText(keyboardDriver.translate());
			
		}
	}
	
	private class StartandStopAction implements ActionListener {
		private boolean status = screen.getState();
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (!status) {
				mousegui.getStartstop().setText("Stop");
				panel.setBackground(Color.WHITE);
				scroll.setVisible(true);
				invitecomm.setVisible(true);
				
			}else {
				
				mousegui.getStartstop().setText("Start");
				panel.setBackground(Color.BLACK);
				scroll.setVisible(false);
				invitecomm.setVisible(false);
				
			}
			screen.setState(!screen.getState());
			status=!status;
		}
	}

	private class EnterKeyAction implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
		    if (e.getKeyCode()==KeyEvent.VK_ENTER){
		    	traductor.transcriptor(invitecomm.getText(), screenDriver);
				keyboard.resetContent();
				invitecomm.setText(null);

				if(roundrobin.getBuffer().getProcessuslist().size() > 0) {
					Thread th = new Thread(instance);
					if(!th.isAlive()) {
						th.start();
					}
				}
				else {
					Updatevalues();
				}
		    }

		}

		@Override
		public void keyReleased(KeyEvent arg0) {

		}

		@Override
		public void keyTyped(KeyEvent arg0) {

		}
	}
	

	

}

