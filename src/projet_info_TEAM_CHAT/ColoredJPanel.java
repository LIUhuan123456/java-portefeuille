package projet_info_TEAM_CHAT;

import java.awt.Color;

import javax.swing.JPanel;

public class ColoredJPanel extends JPanel {
	
	public ColoredJPanel(Color c){
		super();
		this.setOpaque(true);
		this.setBackground(c); 
	}
	

}