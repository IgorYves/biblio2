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
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
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

import biblio.business.Adherent;
import biblio.business.BiblioException;
import biblio.business.Employe;
import biblio.business.Exemplaire;
import biblio.business.Utilisateur;
import biblio.control.EmprunterCtl;
import biblio.dao.BiblioDaoException;
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
	HashMap<Integer, String> empruntsEnCoursDbList;
	private JTable jTable1;
	private JScrollPane tableJScrolPane;
	private JPanel jPanelInt4;
	private JPanel jPanelCenter;
	private JTable jTable3;
	private JScrollPane tableJScrolPane2;
	private JPanel jPanelInt10;
	private JPanel listUtilisateursPanel;
	private JPanel listExemplairesPanel;
	private JPanel listEmpruntsEnCoursDbPanel;
	private JPanel jPanelInt8;
	private JPanel userPanel;
	
	public BiblioMainFrame2() {
		this(null, null, null);
	}
	
	public BiblioMainFrame2(List<Utilisateur> utilisateurs, 
							List<Exemplaire> exemplaires, 
							HashMap<Integer, String> empruntsEnCoursDbList) {
		makeMainFrame();
		HYJPanel jPanel = new HYJPanel();
		this.add(jPanel);
		
//----------------------------------------------------------------------------------
		this.setJMenuBar(makeMenus());
//----------------------------------------------------------------------------------
		JScrollPane jScrollPane = new JScrollPane(jPanel);
		jScrollPane.setOpaque(false);
		this.add(jScrollPane, BorderLayout.CENTER);
		this.setContentPane(jScrollPane);
		JScrollPane contentPane = (JScrollPane) this.getContentPane();
//-------------------------------------------------------------------------------------
		jPanel.setLayout(new BorderLayout());
		jPanel.setOpaque(false);
//******
		JPanel jPanelTop = new JPanel();
		jPanelTop.setLayout(new FlowLayout());
		jPanelTop.setOpaque(false);
//******
		jPanelTop.add(makeButtonEnregistrer());
		jPanelTop.add(makeButtonRetour());
		jPanelTop.add(makeButtonRefresh());
		jPanelTop.add(makeButtonUser());
//******
		jPanel.add(jPanelTop, BorderLayout.PAGE_START);
//--------------------------------------------------------------------
		JTabbedPane jTabbedPane = new JTabbedPane();
		jTabbedPane.setForeground(Color.BLACK);
		jTabbedPane.setBackground(new Color(200, 160, 160));
		jTabbedPane.setOpaque(false);
		jTabbedPane.setFont(new Font("Ariel",Font.PLAIN,14));
//******		
		jTabbedPane.add("Enregistrer un Emprunt", makePaneEmprunt());
//******		
		jTabbedPane.add("Enregistrer un Retour", makePaneRetour());
//******
		jTabbedPane.setBackgroundAt(0, new Color(130, 160, 160));
		jTabbedPane.getComponentAt(0).setBackground(new Color(100, 160, 160));
		jTabbedPane.setBackgroundAt(1, new Color(130, 160, 160));
		jTabbedPane.getComponentAt(1).setBackground(new Color(100, 160, 160));
		jPanel.add(jTabbedPane, BorderLayout.LINE_START);
		
		jPanelCenter = new JPanel();
		jPanelCenter.setOpaque(false);
		jPanelCenter.setLayout(new BoxLayout(jPanelCenter, BoxLayout.PAGE_AXIS));
		
		if (utilisateurs != null) {
			jPanelCenter.add(makeUtilisateurs(utilisateurs));
		}
		if (exemplaires != null) {
			jPanelCenter.add(makeExemplaires(exemplaires));
		}
		if (empruntsEnCoursDbList != null) {
			jPanelCenter.add(makeEmpruntsEnCoursDB(empruntsEnCoursDbList));
		}

		jPanel.add(jPanelCenter, BorderLayout.CENTER);
		
	}//fin constructeur
///////////////////////////////////////////////////////////////////////////////////////////	
	
	private void makeMainFrame() {
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
		this.setVisible(true);
	}
	
	private JMenuBar makeMenus() {
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
									catch (IOException | SQLException | BiblioException e) {} catch (HeadlessException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (BiblioDaoException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									};});
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
		
		return jMenuBar;
	}

	private JButton makeButtonEnregistrer() {
		JButton boutonEnregistrer = new JButton("Enregistrer un Emprunt");
		boutonEnregistrer.setIcon(new ImageIcon("imgs/book2.png"));
		boutonEnregistrer.setRolloverIcon(new ImageIcon("imgs/obook2.png"));
		boutonEnregistrer.addActionListener((actionEvent)-> {try {emprunterCtl.enregistrerEmprunt();} 
									catch (IOException | SQLException | BiblioException e) 
							{e.printStackTrace();};});
		return boutonEnregistrer;
	}
	
	private JButton makeButtonRetour() {
		JButton boutonRetour = new JButton("Enregistrer un Retour");
		boutonRetour.setIcon(new ImageIcon("imgs/books2.png"));
		boutonRetour.setRolloverIcon(new ImageIcon("imgs/hcbook.png"));
		boutonRetour.addActionListener((actionEvent)-> {try {emprunterCtl.enregistrerRetour();} 
					catch (IOException | SQLException | BiblioException | HeadlessException | BiblioDaoException e) 
						{e.printStackTrace();};repaint();});
		return boutonRetour;
	}
	
	private JButton makeButtonRefresh() {
		JButton boutonRefresh = new JButton("Régénérer l'affichage");
		boutonRefresh.setIcon(new ImageIcon("imgs/books2.png"));
		boutonRefresh.setRolloverIcon(new ImageIcon("imgs/hcbook.png"));
		boutonRefresh.addActionListener((actionEvent)-> {
			try {
				this.utilisateurs = biblio.control.EmprunterCtl.getAllUtilisateurs();
				this.exemplaires = biblio.control.EmprunterCtl.getAllExemplaires();
				this.empruntsEnCoursDbList = biblio.control.EmprunterCtl.listAllEmpruntEnCoursDB();
				
				this.jPanelCenter.removeAll();
				this.jPanelCenter.add(makeUtilisateurs(this.utilisateurs));
				this.jPanelCenter.add(makeExemplaires(this.exemplaires));
				this.jPanelCenter.add(makeEmpruntsEnCoursDB(this.empruntsEnCoursDbList));
				this.jPanelCenter.revalidate();
				this.jPanelCenter.repaint();
			} 
			catch (IOException | SQLException | BiblioException e) {
				e.printStackTrace();
			};
			repaint();
		});
		return boutonRefresh;
	}
	
	private JButton makeButtonUser() {
		JButton boutonUser = new JButton("Afficher utilisateur");
		boutonUser.setIcon(new ImageIcon("imgs/useradd.png"));
		boutonUser.addActionListener((actionEvent)-> {
			this.jPanelCenter.removeAll();
			try {
				this.jPanelCenter.add(makeUser());
			} catch (IOException | SQLException | BiblioException e) {
				e.printStackTrace();
			}
			this.jPanelCenter.revalidate();
			this.jPanelCenter.repaint();
			repaint();
		});
		
		
		return boutonUser;
	}
	
	private JPanel makePaneEmprunt() {
		JPanel jPanelTabbedPane1 = new JPanel();
		jPanelTabbedPane1.setOpaque(true);
		jPanelTabbedPane1.setBackground(new Color(100, 160, 160));

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		jPanelTabbedPane1.add(panel);
		
		GridBagLayout gbl_panel = new GridBagLayout();
		panel.setLayout(gbl_panel);
		gbl_panel.columnWidths = new int[]{0, 0, 0, 250, 0};

		JLabel userIdLabel = new JLabel("ID utilisateur : ");
		userIdLabel.setFont(new Font("Ariel",Font.PLAIN,16));
		userIdLabel.setHorizontalAlignment(SwingConstants.LEFT);
		userIdLabel.setOpaque(false);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		panel.add(userIdLabel, gbc_lblNewLabel);

		JTextField userIdText = new JTextField();
		userIdText.setFont(new Font("Ariel",Font.PLAIN,16));
		userIdText.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		gbc_textField.anchor = GridBagConstraints.EAST;
		panel.add(userIdText, gbc_textField);
		userIdText.setColumns(10);

		JLabel exemplaireIdLabel = new JLabel("ID exemplaire : ");
		exemplaireIdLabel.setFont(new Font("Ariel",Font.PLAIN,16));
		exemplaireIdLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 3;
		panel.add(exemplaireIdLabel, gbc_lblNewLabel_1);

		JTextField exemplaireIdText = new JTextField();
		exemplaireIdText.setFont(new Font("Ariel",Font.PLAIN,16));
		exemplaireIdText.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 3;
		gbc_textField_1.anchor = GridBagConstraints.EAST;
		panel.add(exemplaireIdText, gbc_textField_1);
		exemplaireIdText.setColumns(10);
		
		JButton btnRegisterEmprunt = new JButton("Enregistrer Emprunt");
		btnRegisterEmprunt.setFont(new Font("Ariel",Font.PLAIN,16));
		btnRegisterEmprunt.setHorizontalAlignment(SwingConstants.LEFT);
		btnRegisterEmprunt.addActionListener((actionEvent)-> {
				if (!userIdText.getText().equals("") && !exemplaireIdText.getText().equals("")) {
					int userId = Integer.parseInt(userIdText.getText());
					int exemplaireId = Integer.parseInt(exemplaireIdText.getText());
					try {
						emprunterCtl.enregistrerEmprunt(userId, exemplaireId);
					} 
					catch (IOException | SQLException | NumberFormatException e) {
						e.printStackTrace();
					} catch (BiblioException e) {
						jop.showMessageDialog(jop, "ERROR_MESSAGE : "
								+ "conditions de pret ne sont pas acceptées", 
								"Utilisateur", jop.ERROR_MESSAGE);
					} catch (BiblioDaoException e) {
						jop.showMessageDialog(jop, "ERROR_MESSAGE : biblio dao exception "
										+ e.getMessage(), 
								"Utilisateur", jop.ERROR_MESSAGE);
					};
				}
			});
		GridBagConstraints gbc_btn = new GridBagConstraints();
		gbc_btn.gridx = 1;
		gbc_btn.gridy = 5;
		gbc_btn.gridwidth = 3;
		gbc_btn.anchor = GridBagConstraints.EAST;
		panel.add(btnRegisterEmprunt, gbc_btn);
		
		return jPanelTabbedPane1;
	}
	
	private JPanel makePaneRetour() {

		JPanel jPanelTabbedPane2 = new JPanel();
		jPanelTabbedPane2.setOpaque(true);

		JPanel panel2 = new JPanel();
		panel2.setOpaque(false);
		jPanelTabbedPane2.add(panel2);
		
		GridBagLayout gbl_panel2 = new GridBagLayout();
		panel2.setLayout(gbl_panel2);
		gbl_panel2.columnWidths = new int[]{0, 0, 0, 250, 0};

		JLabel exemplaireIdLabel2 = new JLabel("ID exemplaire : ");
		exemplaireIdLabel2.setFont(new Font("Ariel",Font.PLAIN,16));
		exemplaireIdLabel2.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_12 = new GridBagConstraints();
		gbc_lblNewLabel_12.gridx = 1;
		gbc_lblNewLabel_12.gridy = 3;
		panel2.add(exemplaireIdLabel2, gbc_lblNewLabel_12);

		JTextField exemplaireIdText2 = new JTextField();
		exemplaireIdText2.setFont(new Font("Ariel",Font.PLAIN,16));
		exemplaireIdText2.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_textField_12 = new GridBagConstraints();
		gbc_textField_12.gridx = 3;
		gbc_textField_12.gridy = 3;
		gbc_textField_12.anchor = GridBagConstraints.EAST;
		panel2.add(exemplaireIdText2, gbc_textField_12);
		exemplaireIdText2.setColumns(10);
		
		JButton btnRegisterRetour = new JButton("Enregistrer Retour");
		btnRegisterRetour.setFont(new Font("Ariel",Font.PLAIN,16));
		btnRegisterRetour.setHorizontalAlignment(SwingConstants.LEFT);
		btnRegisterRetour.addActionListener((actionEvent)-> 
			{
				if (!exemplaireIdText2.getText().equals("")) {
					int exemplaireId = Integer.parseInt(exemplaireIdText2.getText());
					try {
						emprunterCtl.enregistrerRetour(exemplaireId);
					} 
					catch (IOException | SQLException | NumberFormatException | HeadlessException | BiblioDaoException e) {
						e.printStackTrace();
					};
				}
			});
		GridBagConstraints gbc_btn2 = new GridBagConstraints();
		gbc_btn2.gridx = 1;
		gbc_btn2.gridy = 5;
		gbc_btn2.gridwidth = 3;
		gbc_btn2.anchor = GridBagConstraints.EAST;
		panel2.add(btnRegisterRetour, gbc_btn2);
		
		return jPanelTabbedPane2;
	}
	
	private JPanel makeUtilisateurs(List<Utilisateur> utilisateurs) {
		int cols = 5;
		System.out.println(utilisateurs);
		int rows = utilisateurs.size();
		String[] headers = {"id", "Nom", "Prenom", "H/F", "Categorie"};
		Object[][] data = new Object[rows][cols];
		for (int i = 0; i < utilisateurs.size(); i++) {
			data[i][0] = utilisateurs.get(i).getIdUtilisateur();
			data[i][1] = utilisateurs.get(i).getNom();
			data[i][2] = utilisateurs.get(i).getPrenom();
			data[i][3] = utilisateurs.get(i).getSexe();
			String categorie = "";
			if (utilisateurs.get(i) instanceof Adherent) {
				categorie = "Adherent";
			}
			else if (utilisateurs.get(i) instanceof Employe) {
				categorie = "Employé";
			}
			data[i][4] = categorie;
		}
		
		jTable1 = new JTable(data, headers) {
			@Override
			public boolean getScrollableTracksViewportWidth() {
				return super.getScrollableTracksViewportWidth()
						&& getPreferredSize().width < getParent().getWidth();
			}
		};
		jTable1.setOpaque(false);
		
		jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		for(int i=0; i<jTable1.getColumnCount(); i++) {
			jTable1.getColumnModel().getColumn(i).setPreferredWidth(140);
		}
		tableJScrolPane = new JScrollPane(jTable1);
		tableJScrolPane.setPreferredSize(new Dimension(700, 200));
		tableJScrolPane.setOpaque(false);
		jPanelInt4 = new JPanel();
		jPanelInt4.setOpaque(false);
		jPanelInt4.add(tableJScrolPane);
		return jPanelInt4;
	}
	
	private JPanel makeExemplaires(List<Exemplaire> exemplaires) {
		jPanelInt7 = new JPanel();
		//jPanelInt7.setBackground(new Color(130, 150, 150));
		jPanelInt7.setOpaque(false);
	
	
		int cols = 4;
		int rows = exemplaires.size();
		String[] headers = {"id", "Date achat", "Status", "ISBN"};
		Object[][] data = new Object[rows][cols];
		for (int i = 0; i < exemplaires.size(); i++) {
			data[i][0] = exemplaires.get(i).getIdExemplaire();
			data[i][1] = exemplaires.get(i).getDateAchat();
			data[i][2] = exemplaires.get(i).getStatus();
			data[i][3] = exemplaires.get(i).getIsbn();
			
		}
		
		jTable2 = new JTable(data, headers) {
			@Override
			public boolean getScrollableTracksViewportWidth() {
				return super.getScrollableTracksViewportWidth()
						&& getPreferredSize().width < getParent().getWidth();
			}
		};
		//jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//jTable2.setFillsViewportHeight(true);
		
		jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		for(int i=0; i<jTable2.getColumnCount(); i++) {
			jTable2.getColumnModel().getColumn(i).setPreferredWidth(140);
		}
		JScrollPane tableJScrolPane = new JScrollPane(jTable2);
		tableJScrolPane.setPreferredSize(new Dimension(700, 200));
		jPanelInt7.add(tableJScrolPane);

		return jPanelInt7;
	}
	
	private JPanel makeEmpruntsEnCoursDB(HashMap<Integer, String> empruntsEnCoursDbList) {
		jPanelInt10 = new JPanel();
		//jPanelInt10.setBackground(new Color(130, 150, 150));
		jPanelInt10.setOpaque(false);
		
		int cols = 2;
		int rows = empruntsEnCoursDbList.size();
		String[] headers = {"id Exemplaire", "id Utilisateur, Date Emprunt"};
		Object[][] data = new Object[rows][cols];
		
		Set<Integer> empruntEnCoursKeys = empruntsEnCoursDbList.keySet();
		Iterator iterator = empruntEnCoursKeys.iterator();
		Integer current = null;
		int i = 0;
		while (iterator.hasNext()) {
			current = (Integer) iterator.next();
			data[i][0] = current;
			data[i][1] = empruntsEnCoursDbList.get(current);
			i++;
		}
		
		jTable3 = new JTable(data, headers) {
			@Override
			public boolean getScrollableTracksViewportWidth() {
				return super.getScrollableTracksViewportWidth()
						&& getPreferredSize().width < getParent().getWidth();
			}
		};
		
		jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		for(int i2=0; i2<jTable3.getColumnCount(); i2++) {
			jTable3.getColumnModel().getColumn(i2).setPreferredWidth(140);
		}
		tableJScrolPane2 = new JScrollPane(jTable3);
		tableJScrolPane2.setPreferredSize(new Dimension(700, 200));
		jPanelInt10.add(tableJScrolPane2);
		return jPanelInt10;
	}

	private JPanel makeUser() throws IOException, SQLException, BiblioException {
		jPanelInt8 = new JPanel();
		jPanelInt8.setOpaque(false);
		jPanelInt8.setLayout(new BoxLayout(jPanelInt8, BoxLayout.Y_AXIS));
		JPanel selectUser = new JPanel();
		
		HashMap<Integer, String> users = biblio.control.EmprunterCtl.listAllUtilisateurs();
		
		Integer[] combo2UsersId = new Integer[users.size()];
		Set<Integer> usersKeys = users.keySet();
		combo2UsersId = usersKeys.toArray(combo2UsersId);
		String[] combo = new String[users.size()];
		for (int i = 0; i < combo.length; i++) {
			combo[i] = combo2UsersId[i] + users.get(combo2UsersId[i]);
		}
		
		JComboBox<String> jComboBox1 = new JComboBox<>(combo);
		jComboBox1.addActionListener((actionEvent)->{
			JComboBox comboBox = (JComboBox)actionEvent.getSource();
	        String user = (String)comboBox.getSelectedItem();
	        int userId = Integer.parseInt(user.substring(0, 1));
	        System.out.println(user);
	        System.out.println(userId);
	        try {
				this.afficheUser(userId);
			} catch (SQLException | BiblioException | IOException e) {
				e.printStackTrace();
			}
			
		});
		
		selectUser.add(jComboBox1);
		jPanelInt8.add(selectUser);
		
		userPanel = new JPanel();
		
		jPanelInt8.add(userPanel);
		
		return jPanelInt8;
	}
	
	private void afficheUser(int userId) throws SQLException, BiblioException, IOException {
		this.userPanel.removeAll();
		this.userPanel.add(new JLabel(biblio.control.EmprunterCtl.getUserString(userId)));
		this.userPanel.revalidate();
		this.userPanel.repaint();
		repaint();
	}
	
	private void reloadUtilisateurs() 
			throws IOException, SQLException, BiblioException {
		utilisateurs = emprunterCtl.getAllUtilisateurs();
	}
	private void reloadExemplaires() 
			throws IOException, SQLException {
		exemplaires = emprunterCtl.getAllExemplaires();
	}
	private void reloadEmpruntsEnCoursDB() 
			throws IOException, SQLException, BiblioException {
		empruntsEnCoursDbList = emprunterCtl.listAllEmpruntEnCoursDB();
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