package biblio.gui;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/*
 * ceci est un bouchon pour lancer gui directement
 * normallement gui se lance à partir de control
 * */

import javax.swing.SwingUtilities;

import biblio.business.BiblioException;

public class BiblioMainEntry {
	static JOptionPane jop = new JOptionPane();
	static boolean debug = true;
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->
				{
					if (debug || biblio.control.EmprunterCtl.getAutorisation()) {
						try {
							new BiblioMainFrame2(biblio.control.EmprunterCtl.getAllUtilisateurs(),
												biblio.control.EmprunterCtl.getAllExemplaires(),
												biblio.control.EmprunterCtl.listAllEmpruntEnCoursDB());
						} catch (IOException | SQLException | BiblioException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						jop.showMessageDialog(jop, "Autentification requise", 
								"Info", jop.INFORMATION_MESSAGE);
					}
					
				});
	}
}
