package biblio.gui;

/*
 * ceci est un bouchon pour lancer gui directement
 * normallement gui se lance à partir de control
 * */

import javax.swing.SwingUtilities;

public class BiblioMainEntry {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->new BiblioMainFrame());
	}
}
