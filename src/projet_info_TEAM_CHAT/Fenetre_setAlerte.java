
package projet_info_TEAM_CHAT;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Fenetre_setAlerte implements ActionListener{

	private JFrame jf=new JFrame("Créer une Alerte");
	private StandardButton jb_valider=new StandardButton("Valider");
	private StandardButton jb_return=new StandardButton("Retour");
	private JTextField jt=new JTextField(20); 
	String Path;
	String Nom;
	String Symbol_entreprise;
	Fenetre_setAlerte(String nom,String path,String symbol_entreprise){
		    this.Path=path;
		    this.Nom=nom;
		    this.Symbol_entreprise=symbol_entreprise;
	        ColoredJPanel jp_button=new ColoredJPanel(new Color(80,80,80));
	        ColoredJPanel jp_all=new ColoredJPanel(new Color(80,80,80));
	        ColoredJPanel jp_text=new ColoredJPanel(new Color(80,80,80));
	        jp_text.add(this.jt);
	        jp_button.add(this.jb_valider,BorderLayout.PAGE_START);
	        jp_button.add(this.jb_return,BorderLayout.PAGE_END);
	        this.jb_return.addActionListener(this);
	        this.jb_valider.addActionListener(this);
	        jp_all.add(jp_text);
	        jp_all.add(jp_button);
	        jp_all.setLayout(new BoxLayout(jp_all, BoxLayout.Y_AXIS));
	        this.jf.add(jp_all);
	        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.jf.setSize(300, 300);
	        this.jf.setVisible(true);
	        
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.jb_return){this.jf.dispose();}
		if(e.getSource()==this.jb_valider){
			try {
				PorteFeuille pf=new PorteFeuille(this.Nom,this.Path);
				pf.changer_etat_alterte(this.Symbol_entreprise, jt.getText());
				jf.dispose();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		new Fenetre_setAlerte("FAN","fiche_porte_feuille/FAN.txt","GOOG");
	}
}
