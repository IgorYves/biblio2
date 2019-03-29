package biblio.gui;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import biblio.business.BiblioException;
import biblio.business.Exemplaire;
import biblio.business.Utilisateur;
import biblio.control.EmprunterCtl;
import biblio.dao.EmpruntEnCoursDB;

public class BiblioMainFrame2 extends JFrame {

	JLabel jLabel11;
	JToggleButton jToggleButton;
	JCheckBox jCheckBox4;
	JPopupMenu jPopupMenu;
	JOptionPane jop = new JOptionPane();
	EmprunterCtl emprunterCtl = new EmprunterCtl();
	JPanel jPanelInt7;
	JTable jTable2;
	List<Utilisateur> utilisateurs;
	List<Exemplaire> exemplaires;
	List<EmpruntEnCoursDB> empruntsEnCoursDB;
	
	public BiblioMainFrame2() {
		this(null, null, null);
	}
	
	public BiblioMainFrame2(List<Utilisateur> utilisateurs, 
							List<Exemplaire> exemplaires, 
							List<EmpruntEnCoursDB> empruntsEnCoursDB) {
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		this.setDefaultLookAndFeelDecorated(true);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(dimension.width - 400, 800);
		//this.setLocation(dimension.width/2 - this.getWidth()/2, dimension.height/2 - this.getHeight()/2);
		this.setLocationRelativeTo(null);
		this.setTitle("Yves HUGOT swing application");
		this.setResizable(true);
		this.setAlwaysOnTop(false);
		this.setUndecorated(false);
		//this.setBackground(new Color(0, 150, 150));
		this.setIconImage(new ImageIcon("imgs/useradd.png").getImage());
		this.setForeground(new Color(200, 50, 50));
		
		HYJPanel jPanel = new HYJPanel();
		this.add(jPanel);
		
		this.setVisible(true);
		
//----------------------------------------------------------
		
		JMenuBar jMenuBar = new JMenuBar();
		
		JMenu jMFile = new JMenu("File");
			jMFile.setMnemonic(KeyEvent.VK_F);
			
			JMenuItem jMFileNew = new JMenuItem("New", new ImageIcon("imgs/file.png"));
				jMFileNew.setMnemonic(KeyEvent.VK_N);
				jMFileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
				jMFileNew.addActionListener((actionEvent)-> 
										{jop.showMessageDialog(jop, "Nothing here", 
												"Info", jop.INFORMATION_MESSAGE);repaint();});
			jMFile.add(jMFileNew);
				
			JMenuItem jMFileOpen = new JMenuItem("Open", KeyEvent.VK_O);
				jMFileOpen.setIcon(new ImageIcon("imgs/reglages.png"));
				jMFileOpen.setToolTipText("ouvrir fichier existant");
				jMFileOpen.addActionListener((actionEvent)-> 
										{FileDialog fd = new FileDialog(this, "ceci est un FileDialog");
										fd.setVisible(true);
										repaint();});
			jMFile.add(jMFileOpen);
				
			JMenuItem jMFileClose = new JMenuItem("Close", KeyEvent.VK_C);
			jMFile.add(jMFileClose);
			
			jMFile.addSeparator();
			
			JMenuItem jMFileSave = new JMenuItem("Save", KeyEvent.VK_S);
			jMFile.add(jMFileSave);
				
			JMenuItem jMFileSaveAs = new JMenuItem("SaveAs");
				jMFileSaveAs.setToolTipText("Save avec autre nom de fichier");
			jMFile.add(jMFileSaveAs);
				
			jMFile.addSeparator();
			
			JMenuItem jMFileQuit = new JMenuItem("Quit", KeyEvent.VK_Q);
				jMFileQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
				jMFileQuit.addActionListener((actionEvent)-> {System.exit(0);});
			jMFile.add(jMFileQuit);
				
		jMenuBar.add(jMFile);
		
		JMenu jMEdit = new JMenu("Edit");
			jMEdit.setMnemonic(KeyEvent.VK_D);
			
			JMenu jMEditOptions = new JMenu("Options");
				jMEditOptions.setMnemonic(KeyEvent.VK_O);
				
				JMenu jMEditOptionsColors = new JMenu("Colors");
					jMEditOptionsColors.setMnemonic(KeyEvent.VK_C);
				
					JMenuItem jMEditOptionsColorsRed = new JMenuItem("Red");
						jMEditOptionsColorsRed.addActionListener((actionEvent)-> 
												{jop.showMessageDialog(jop, "Nothing here", 
														"Info", jop.INFORMATION_MESSAGE);repaint();});
					jMEditOptionsColors.add(jMEditOptionsColorsRed);
					
					JMenuItem jMEditOptionsColorsBlue = new JMenuItem("Blue");
						jMEditOptionsColorsBlue.addActionListener((actionEvent)-> 
													{jop.showMessageDialog(jop, "Nothing here", 
															"Info", jop.INFORMATION_MESSAGE);repaint();});
					jMEditOptionsColors.add(jMEditOptionsColorsBlue);
			
				jMEditOptions.add(jMEditOptionsColors);
			
				JMenu jMEditOptionsPriority = new JMenu("Priority");
					jMEditOptionsPriority.setMnemonic(KeyEvent.VK_P);
				
					JMenuItem jMEditOptionsPriorityHigh = new JMenuItem("High", KeyEvent.VK_H);
						jMEditOptionsPriorityHigh.addActionListener((actionEvent)-> 
													{jop.showMessageDialog(jop, "Nothing here", 
															"Info", jop.INFORMATION_MESSAGE);repaint();});
					jMEditOptionsPriority.add(jMEditOptionsPriorityHigh);
					
					JMenuItem jMEditOptionsPriorityLow = new JMenuItem("Low", KeyEvent.VK_L);
						jMEditOptionsPriorityLow.addActionListener((actionEvent)-> 
													{jop.showMessageDialog(jop, "Nothing here", 
															"Info", jop.INFORMATION_MESSAGE);repaint();});
					jMEditOptionsPriority.add(jMEditOptionsPriorityLow);
					
				jMEditOptions.add(jMEditOptionsPriority);
			jMEdit.add(jMEditOptions);
			
			JMenuItem jMEdit2 = new JMenuItem("Edit2");
			jMEdit2.setEnabled(false);
			jMEdit.add(jMEdit2);
			
		jMenuBar.add(jMEdit);
		
		JMenu jMAffichage = new JMenu("Affichage");
			jMAffichage.setMnemonic(KeyEvent.VK_A);
			
			JCheckBoxMenuItem  JCBMAffichageOption1 = new JCheckBoxMenuItem("Option1");
			jMAffichage.add(JCBMAffichageOption1);
			
			jMAffichage.addSeparator();
			JCheckBoxMenuItem  JCBMAffichageOption2 = new JCheckBoxMenuItem("Option2", true);
			jMAffichage.add(JCBMAffichageOption2);
			
			jMAffichage.addSeparator();
			JCheckBoxMenuItem  JCBMAffichageOption3 = new JCheckBoxMenuItem("Option3");
				JCBMAffichageOption3.setSelected(true);
			jMAffichage.add(JCBMAffichageOption3);
			
			jMAffichage.addSeparator();
			JRadioButtonMenuItem JRMAffichageOption4 = new JRadioButtonMenuItem("Option4");
				JRMAffichageOption4.setSelected(true);
			jMAffichage.add(JRMAffichageOption4);
			
			jMAffichage.addSeparator();
			JRadioButtonMenuItem JRMAffichageOption5 = new JRadioButtonMenuItem("Option5", true);
			jMAffichage.add(JRMAffichageOption5);
			
			JRadioButtonMenuItem JRMAffichageOption6 = new JRadioButtonMenuItem("Option6");
			jMAffichage.add(JRMAffichageOption6);
			
			JRadioButtonMenuItem JRMAffichageOption7 = new JRadioButtonMenuItem("Option7");
			jMAffichage.add(JRMAffichageOption7);
			
			ButtonGroup bGroup1 = new ButtonGroup();
			bGroup1.add(JRMAffichageOption5);
			bGroup1.add(JRMAffichageOption6);
			bGroup1.add(JRMAffichageOption7);
			
		jMenuBar.add(jMAffichage);
		
//---------------------------------------------------------
		JMenu jMBiblio = new JMenu("Bibliotheque");
		jMBiblio.setMnemonic(KeyEvent.VK_B);
			JMenuItem jMBiblioEnregistrer = new JMenuItem("Enregistrement Emprunt", new ImageIcon("imgs/book2.png"));
			jMBiblioEnregistrer.setMnemonic(KeyEvent.VK_E);
			jMBiblioEnregistrer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
			jMBiblioEnregistrer.addActionListener((actionEvent)-> {try {emprunterCtl.enregistrerEmprunt();} 
									catch (IOException | SQLException | BiblioException e) {};});
			jMBiblio.add(jMBiblioEnregistrer);
			
			JMenuItem jMBiblioRetourner = new JMenuItem("Enregistrement Retour", new ImageIcon("imgs/books2.png"));
			jMBiblioRetourner.setMnemonic(KeyEvent.VK_R);
			jMBiblioRetourner.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
			jMBiblioRetourner.addActionListener((actionEvent)-> {try {emprunterCtl.enregistrerRetour();} 
									catch (IOException | SQLException | BiblioException e) {};});
			jMBiblio.add(jMBiblioRetourner);
		
		jMenuBar.add(jMBiblio);
//---------------------------------------------------------
		
		JMenu jMOutils = new JMenu("Outils");
			jMOutils.setMnemonic(KeyEvent.VK_O);
		jMenuBar.add(jMOutils);
		
		JMenu jMWindow = new JMenu("Window");
			jMWindow.setMnemonic(KeyEvent.VK_W);
		jMenuBar.add(jMWindow);
		
		JMenu jMHelp = new JMenu("Help");
			jMHelp.setMnemonic(KeyEvent.VK_H);
			JMenuItem jMHelpTopics = new JMenuItem("Help Topics");
				jMHelpTopics.addActionListener((actionEvent)-> {jop.showMessageDialog(jop, "Nothing here", 
						"Info", jop.INFORMATION_MESSAGE);repaint();});
				jMHelp.add(jMHelpTopics);
			
			JMenuItem jMHelpAbout = new JMenuItem("About");
				jMHelpAbout.addActionListener((actionEvent)-> {jop.showMessageDialog(jop, "Igor was here", 
						"Info", jop.INFORMATION_MESSAGE);repaint();});
				jMHelp.add(jMHelpAbout);
		
		jMenuBar.add(jMHelp);
		
		this.setJMenuBar(jMenuBar);
		
//----------------------------------------------------------------------------------
		JScrollPane jScrollPane = new JScrollPane(jPanel);
		jScrollPane.setOpaque(false);
		this.add(jScrollPane, BorderLayout.CENTER);
		this.setContentPane(jScrollPane);
		JScrollPane contentPane = (JScrollPane) this.getContentPane();
//-----------------------------------------------------------------
		jPanel.setLayout(new BorderLayout());
		jPanel.setOpaque(false);
		
		JPanel jPanelTop = new JPanel();
		jPanelTop.setLayout(new FlowLayout());
		jPanelTop.setOpaque(false);
		
		JButton boutonEnregistrer = new JButton("Enregistrer un Emprunt");
		boutonEnregistrer.setIcon(new ImageIcon("imgs/book2.png"));
		boutonEnregistrer.setRolloverIcon(new ImageIcon("imgs/obook2.png"));
		boutonEnregistrer.addActionListener((actionEvent)-> {try {emprunterCtl.enregistrerEmprunt();} 
									catch (IOException | SQLException | BiblioException e) 
							{e.printStackTrace();};});
		jPanelTop.add(boutonEnregistrer);
		
		JButton boutonRetour = new JButton("Enregistrer un Retour");
		boutonRetour.setIcon(new ImageIcon("imgs/books2.png"));
		boutonRetour.setRolloverIcon(new ImageIcon("imgs/hcbook.png"));
		boutonRetour.addActionListener((actionEvent)-> {try {emprunterCtl.enregistrerRetour();} 
					catch (IOException | SQLException | BiblioException e) 
						{e.printStackTrace();};repaint();});
		jPanelTop.add(boutonRetour);
		
		jPanel.add(jPanelTop, BorderLayout.NORTH);
//---------------------------------------------------
		JPanel jPanelCenter = new JPanel();
		jPanelCenter.setLayout(new FlowLayout());
		jPanelCenter.setOpaque(false);
		
		
		JTabbedPane jTabbedPane = new JTabbedPane();
		jTabbedPane.setForeground(Color.BLACK);
		jTabbedPane.setBackground(Color.LIGHT_GRAY);
		jTabbedPane.setOpaque(false);
		jTabbedPane.setFont(new Font("Ariel",Font.PLAIN,20));
		
		JPanel jPanelTabbedPane1 = new JPanel();
		jPanelTabbedPane1.setOpaque(false);
		jPanelTabbedPane1.setBackground(new Color(200,200,250));


		JPanel panel = new JPanel();
		panel.setOpaque(false);
		jPanelTabbedPane1.add(panel);
		
		GridBagLayout gbl_panel = new GridBagLayout();
		panel.setLayout(gbl_panel);
		gbl_panel.columnWidths = new int[]{0, 0, 0, 250, 0};

		JLabel userIdLabel = new JLabel("ID utilisateur : ");
		userIdLabel.setFont(new Font("Ariel",Font.PLAIN,18));
		userIdLabel.setHorizontalAlignment(SwingConstants.LEFT);
		userIdLabel.setOpaque(false);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		panel.add(userIdLabel, gbc_lblNewLabel);

		JTextField userIdText = new JTextField();
		userIdText.setFont(new Font("Ariel",Font.PLAIN,18));
		userIdText.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		gbc_textField.anchor = GridBagConstraints.EAST;
		panel.add(userIdText, gbc_textField);
		userIdText.setColumns(10);

		JLabel exemplaireIdLabel = new JLabel("ID exemplaire : ");
		exemplaireIdLabel.setFont(new Font("Ariel",Font.PLAIN,18));
		exemplaireIdLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 3;
		panel.add(exemplaireIdLabel, gbc_lblNewLabel_1);

		JTextField exemplaireIdText = new JTextField();
		exemplaireIdText.setFont(new Font("Ariel",Font.PLAIN,18));
		exemplaireIdText.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 3;
		gbc_textField_1.anchor = GridBagConstraints.EAST;
		panel.add(exemplaireIdText, gbc_textField_1);
		exemplaireIdText.setColumns(10);
		
		JButton btnRegisterEmprunt = new JButton("Enregistrer Emprunt");
		btnRegisterEmprunt.setFont(new Font("Ariel",Font.PLAIN,18));
		btnRegisterEmprunt.setHorizontalAlignment(SwingConstants.LEFT);
		btnRegisterEmprunt.addActionListener((actionEvent)-> 
			{try {emprunterCtl.enregistrerEmprunt(Integer.parseInt(userIdText.getText()), 
											Integer.parseInt(exemplaireIdText.getText()));} 
			catch (IOException | SQLException | BiblioException e) {e.printStackTrace();};});
		GridBagConstraints gbc_btn = new GridBagConstraints();
		gbc_btn.gridx = 1;
		gbc_btn.gridy = 5;
		gbc_btn.gridwidth = 3;
		gbc_btn.anchor = GridBagConstraints.EAST;
		panel.add(btnRegisterEmprunt, gbc_btn);
		
		
		
		jTabbedPane.add("Enregistrer un Emprunt", jPanelTabbedPane1);
		
		JPanel jPanelTabbedPane2 = new JPanel();
		jPanelTabbedPane2.setOpaque(false);
		jPanelTabbedPane2.add(new JButton("jPanelTabbedPane2 - 1"));
		jPanelTabbedPane2.add(new JButton("jPanelTabbedPane2 - 2"));
		jPanelTabbedPane2.add(new JButton("jPanelTabbedPane2 - 3"));
		jTabbedPane.add("Enregistrer un Retour", jPanelTabbedPane2);
		
		JPanel jPanelTabbedPane3 = new JPanel();
		jPanelTabbedPane3.setOpaque(false);
		jPanelTabbedPane3.add(new JButton("bouton1"));
		jPanelTabbedPane3.add(new JButton("bouton2"));
		jPanelTabbedPane3.add(new JButton("bouton3"));
		jTabbedPane.add("Consulter", jPanelTabbedPane3);
		
				
		jPanel.add(jTabbedPane, BorderLayout.CENTER);
		
	}

	private void reloadExemplaires() throws IOException, SQLException {
		exemplaires = emprunterCtl.getAllExemplaires();
	}
}

class HYJPanel extends JPanel {
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		this.setBackground(new Color(100, 150, 150));
		this.setVisible(true);
		this.setOpaque(true);
		
		graphics.setColor(new Color(100, 160, 160));
		graphics.setFont(new Font("Ariel",Font.PLAIN,500));
		((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D) graphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f ));
		graphics.drawString("AFPA", 50, this.getHeight()/2+200);
		((Graphics2D) graphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f ));
	}
}