
package projet_info_TEAM_CHAT;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Text_input_erreur implements ActionListener{

	private JFrame jf=new JFrame();
	private StandardButton jb=new StandardButton("Fermer");
	
	public Text_input_erreur(String s) {
		
		jb.addActionListener(this);
		JLabel jl=new JLabel(s);
		jl.setForeground(Color.WHITE);
		ColoredJPanel jp=new ColoredJPanel(new Color(80,80,80));
		jp.add(jl);
		jp.add(jb);
		jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
		jf.add(jp);
		jf.setSize(500, 180);
		jf.setVisible(true);
		this.jf.setLocationRelativeTo(null);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb){jf.dispose();}
	}
	
	
	// TEST
	public static void main(String[] args){
		new Text_input_erreur("test");
	}
	
}


