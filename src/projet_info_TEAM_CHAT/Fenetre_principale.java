
package projet_info_TEAM_CHAT;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class Fenetre_principale implements ActionListener{
  
	private JFrame jf=new JFrame("Identification");
	private StandardButton jb_login=new StandardButton("Se connecter");
	private StandardButton jb_inscrire=new StandardButton("S'inscrire");
	private JTextField jt_id=new JTextField(10);
	private JPasswordField jt_mp=new JPasswordField(10);
	/*private JTextField jt_boss=new JTextField(10);
	private StandardButton jb_boss_login=new StandardButton("Login");
	private String Path_boss="fiche_boss/password.txt"; */
	
	
    public Fenetre_principale(){
    	JLabel jl_id=new JLabel("Identifiant   ");
    	jl_id.setForeground(Color.WHITE);
		jl_id.setFont(new Font("Serif", Font.BOLD, 14 ));
		JLabel jl_mp=new JLabel("Mot de passe");
		jl_mp.setForeground(Color.WHITE);
		jl_mp.setFont(new Font("Serif", Font.BOLD, 14 ));
		/*JLabel jl_boss=new JLabel("I am the boss");
		jl_boss.setForeground(Color.WHITE);
		jl_boss.setFont(new Font("Serif", Font.BOLD, 14 ));
		ColoredJPanel jp_boss=new ColoredJPanel(new Color(80,80,80));*/
		ColoredJPanel jp_id=new ColoredJPanel(new Color(80,80,80));
		ColoredJPanel jp_mp=new ColoredJPanel(new Color(80,80,80));
		ColoredJPanel jp_button=new ColoredJPanel(new Color(80,80,80));
		ColoredJPanel jp_all=new ColoredJPanel(new Color(80,80,80));
		/*jp_boss.add(jl_boss,BorderLayout.PAGE_START);
		jp_boss.add(jt_boss,BorderLayout.PAGE_END);
		jp_boss.add(this.jb_boss_login,BorderLayout.AFTER_LINE_ENDS);*/
		jp_id.add(jl_id,BorderLayout.PAGE_START);
		jp_id.add(this.jt_id,BorderLayout.PAGE_END);
		jp_mp.add(jl_mp,BorderLayout.PAGE_START);
		jp_mp.add(this.jt_mp,BorderLayout.PAGE_END);
		jp_button.add(this.jb_login,BorderLayout.PAGE_START);
		jp_button.add(this.jb_inscrire,BorderLayout.PAGE_END);
		this.jb_login.addActionListener(this);
		this.jb_inscrire.addActionListener(this);
		/*this.jb_boss_login.addActionListener(this);
		jp_all.add(jp_boss);*/
		jp_all.add(jp_id);
		jp_all.add(jp_mp);
		jp_all.add(jp_button);
		jp_all.setLayout(new BoxLayout(jp_all,BoxLayout.Y_AXIS));
		
		jf.add(jp_all);
		jf.setSize(300, 300);
		this.jf.setLocationRelativeTo(null);
		jf.setVisible(true);
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//if(e.getSource()==this.jb_boss_login){} //pour le chef login
		
		 if(e.getSource()==this.jb_login){
			try {
			       Login lgi=new Login(this.jt_id.getText(),this.jt_mp.getText());
			       if(lgi.verifier(this.jt_id.getText(), this.jt_mp.getText())){this.jf.dispose();}
		     } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }
	     }
		else if(e.getSource()==this.jb_inscrire){
			new Inscription();
		}
	}
	
	public static void main(String[] args){
		new Fenetre_principale();
		
	}
	
}
