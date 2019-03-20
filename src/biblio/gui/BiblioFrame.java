package biblio.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class BiblioFrame {
	public JLabel jLabel;
	public JTextArea textArea;
	public BiblioFrame() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame jFrame = new JFrame("Biblio application");
		jFrame.setSize(dimension.width - 400, 800);
		jFrame.setLocation(dimension.width/2 - jFrame.getWidth()/2, dimension.height/2 - jFrame.getHeight()/2);
		jFrame.setLayout(new FlowLayout());
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		jLabel = new JLabel("Autentification");
		jFrame.add(jLabel);
		
		//textArea = new JTextArea("Autentification", 2, 100);
		jFrame.setVisible(true);
		
	}
}
