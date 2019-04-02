package biblio.gui;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import biblio.business.Adherent;
import biblio.business.BiblioException;
import biblio.business.Employe;
import biblio.business.Exemplaire;
import biblio.business.Utilisateur;
import biblio.control.EmprunterCtl;
import biblio.dao.EmpruntEnCoursDB;

public class BiblioMainFrame extends JFrame {

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
	
	public BiblioMainFrame() {
		this(null, null, null);
	}
	
	public BiblioMainFrame(List<Utilisateur> utilisateurs, 
							List<Exemplaire> exemplaires, 
							HashMap<Integer, String> empruntsEnCoursDB) {
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
									catch (IOException | SQLException | BiblioException e) {};jTable2.repaint();});
			jMBiblio.add(jMBiblioEnregistrer);
			
			JMenuItem jMBiblioRetourner = new JMenuItem("Enregistrement Retour", new ImageIcon("imgs/books2.png"));
			jMBiblioRetourner.setMnemonic(KeyEvent.VK_R);
			jMBiblioRetourner.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
			jMBiblioRetourner.addActionListener((actionEvent)-> {try {emprunterCtl.enregistrerRetour();} 
									catch (IOException | SQLException | BiblioException e) {};repaint();});
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
						"Info", jop.INFORMATION_MESSAGE);});
				jMHelp.add(jMHelpTopics);
			
			JMenuItem jMHelpAbout = new JMenuItem("About");
				jMHelpAbout.addActionListener((actionEvent)-> {jop.showMessageDialog(jop, "Igor was here", 
						"Info", jop.INFORMATION_MESSAGE);});
				jMHelp.add(jMHelpAbout);
		
		jMenuBar.add(jMHelp);
		
		this.setJMenuBar(jMenuBar);
		
//----------------------------------------------------------------------------------
		JScrollPane jScrollPane = new JScrollPane(jPanel);
		jScrollPane.setOpaque(false);
		this.add(jScrollPane, BorderLayout.CENTER);
		this.setContentPane(jScrollPane);
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		jPanel.setLayout(gridBagLayout);
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.ipadx = 100;
		gridBagConstraints.insets = new Insets(10, 10, 10, 10);
		//gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
//------------------------------------------------------------------------------------
		JPanel jPanelInt1 = new JPanel();
		//jPanelInt1.setBackground(new Color(130, 150, 150));
		jPanelInt1.setOpaque(false);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagLayout.setConstraints(jPanelInt1, gridBagConstraints);
		jPanel.add(jPanelInt1);
		
		
//--------------------------------------------------------------------------------
		JPanel jPanelInt2 = new JPanel();
		//jPanelInt2.setBackground(new Color(130, 150, 150));
		jPanelInt2.setOpaque(false);
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagLayout.setConstraints(jPanelInt2, gridBagConstraints);
		jPanel.add(jPanelInt2);
		
		JButton bouton = new JButton("Enregistrer un Emprunt");
		bouton.setIcon(new ImageIcon("imgs/book2.png"));
		bouton.setRolloverIcon(new ImageIcon("imgs/obook2.png"));
		bouton.addActionListener((actionEvent)-> {try {emprunterCtl.enregistrerEmprunt();
							reloadExemplaires();} 
						catch (IOException | SQLException | BiblioException e) {};
						});
		jPanelInt2.add(bouton);
		JButton bouton2 = new JButton("Enregistrer un Retour");
		bouton2.setIcon(new ImageIcon("imgs/books2.png"));
		bouton2.setRolloverIcon(new ImageIcon("imgs/hcbook.png"));
		bouton2.addActionListener((actionEvent)-> {try {emprunterCtl.enregistrerRetour();} 
		catch (IOException | SQLException | BiblioException e) {};});
		jPanelInt2.add(bouton2);
		this.getRootPane().setDefaultButton(bouton2);
		
//-------------------------------------------------------------------------
		JPanel jPanelInt3 = new JPanel();
		//jPanelInt3.setBackground(new Color(130, 150, 150));
		jPanelInt3.setOpaque(false);
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagLayout.setConstraints(jPanelInt3, gridBagConstraints);
		jPanel.add(jPanelInt3);
		

//--------------------------------------------------------------------
		
		JPanel jPanelInt4 = new JPanel();
		//jPanelInt4.setBackground(new Color(130, 150, 150));
		jPanelInt4.setOpaque(false);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagLayout.setConstraints(jPanelInt4, gridBagConstraints);
		
		if (utilisateurs != null) {
			int cols = 5;
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
			
			JTable jTable1 = new JTable(data, headers) {
				@Override
				public boolean getScrollableTracksViewportWidth() {
					return super.getScrollableTracksViewportWidth()
							&& getPreferredSize().width < getParent().getWidth();
				}
			};
			//jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//jTable1.setFillsViewportHeight(true);
			
			jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			for(int i=0; i<jTable1.getColumnCount(); i++) {
				jTable1.getColumnModel().getColumn(i).setPreferredWidth(140);
			}
			JScrollPane tableJScrolPane = new JScrollPane(jTable1);
			tableJScrolPane.setPreferredSize(new Dimension(600, 200));
			jPanelInt4.add(tableJScrolPane);
		}
		
		jPanel.add(jPanelInt4);
		
//---------------------------------------------------
		
		jPanelInt7 = new JPanel();
		//jPanelInt7.setBackground(new Color(130, 150, 150));
		jPanelInt7.setOpaque(false);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagLayout.setConstraints(jPanelInt7, gridBagConstraints);
		
		if (exemplaires != null) {
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
			tableJScrolPane.setPreferredSize(new Dimension(600, 200));
			jPanelInt7.add(tableJScrolPane);
		}
		
		jPanel.add(jPanelInt7);
		
//---------------------------------------------------
		
		JPanel jPanelInt10 = new JPanel();
		//jPanelInt10.setBackground(new Color(130, 150, 150));
		jPanelInt10.setOpaque(false);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagLayout.setConstraints(jPanelInt10, gridBagConstraints);
		
		if (empruntsEnCoursDB != null) {
			int cols = 2;
			int rows = empruntsEnCoursDB.size();
			String[] headers = {"id Exemplaire", "id Utilisateur, Date Emprunt"};
			Object[][] data = new Object[rows][cols];
			
			Set<Integer> empruntEnCoursKeys = empruntsEnCoursDB.keySet();
			Iterator iterator = empruntEnCoursKeys.iterator();
			Integer current = null;
			int i = 0;
			while (iterator.hasNext()) {
				current = (Integer) iterator.next();
				data[i][0] = current;
				data[i][1] = empruntsEnCoursDB.get(current);
				i++;
			}
			
			JTable jTable3 = new JTable(data, headers) {
				@Override
				public boolean getScrollableTracksViewportWidth() {
					return super.getScrollableTracksViewportWidth()
							&& getPreferredSize().width < getParent().getWidth();
				}
			};
			//jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//jTable3.setFillsViewportHeight(true);
			
			jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			for(int i2=0; i2<jTable3.getColumnCount(); i2++) {
				jTable3.getColumnModel().getColumn(i2).setPreferredWidth(140);
			}
			JScrollPane tableJScrolPane = new JScrollPane(jTable3);
			tableJScrolPane.setPreferredSize(new Dimension(600, 200));
			jPanelInt10.add(tableJScrolPane);
		}
		
		jPanel.add(jPanelInt10);
		
//---------------------------------------------------
		
		
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