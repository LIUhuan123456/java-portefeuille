
package projet_info_TEAM_CHAT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang.NumberUtils;

@SuppressWarnings("deprecation")
public class Fenetre_setCoutAlerte implements ActionListener{

	private JFrame jf=new JFrame("Créer une alerte");
	private Fenetre_porteFeuille fenp;
	private Entreprise E;
	
	private StandardButton jb_valider=new StandardButton("Valider");
	private StandardButton jb_return=new StandardButton("Retour");
	private JTextField jt_min=new JTextField(5);
	private JTextField jt_max=new JTextField(5);

	Fenetre_setCoutAlerte(Fenetre_porteFeuille p,Entreprise e){
		    this.fenp=p;
		    this.E=e;
		    ColoredJPanel jp_min=new ColoredJPanel(new Color(160,160,160));
		    ColoredJPanel jp_max=new ColoredJPanel(new Color(160,160,160));
		    ColoredJPanel jp_button=new ColoredJPanel(new Color(160,160,160));
		    ColoredJPanel jp_all=new ColoredJPanel(new Color(160,160,160));
	        
	        JLabel jl_min_avant=new JLabel("Si le prix est inférieur à ");
	        JLabel jl_min_apres=new JLabel("m'alerter que le prix unitaire est trop bas!");
	        JLabel jl_max_avant=new JLabel("Si le prix est supérieur à ");
	        JLabel jl_max_apres=new JLabel("m'alerter que le prix unitaire est trop haut!");
	        
	        jp_min.add(jl_min_avant,BorderLayout.LINE_START);
	        jp_min.add(this.jt_min,BorderLayout.CENTER);
	        jp_min.add(jl_min_apres,BorderLayout.LINE_END);
	        
	        jp_max.add(jl_max_avant,BorderLayout.LINE_START);
	        jp_max.add(this.jt_max,BorderLayout.CENTER);
	        jp_max.add(jl_max_apres,BorderLayout.LINE_END);
	        
	        jp_button.add(this.jb_valider,BorderLayout.PAGE_START);
	        jp_button.add(this.jb_return,BorderLayout.PAGE_END);
	        this.jb_return.addActionListener(this);
	        this.jb_valider.addActionListener(this);
	        jp_all.add(jp_min);
	        jp_all.add(jp_max);
	        jp_all.add(jp_button);
	        jp_all.setLayout(new BoxLayout(jp_all, BoxLayout.Y_AXIS));
	        this.jf.add(jp_all);
	        this.jf.setSize(500, 200);
	        this.jf.setLocationRelativeTo(null);
	        this.jf.setVisible(true);
	        
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.jb_return){this.jf.dispose();}
		if(e.getSource()==this.jb_valider){
			
			String minp=this.jt_min.getText();
			String maxp=this.jt_max.getText();
			double minprix;
			double maxprix;
			
			if(NumberUtils.isNumber(minp) && NumberUtils.isNumber(maxp)){
			     minprix=Double.parseDouble(minp);
			     maxprix=Double.parseDouble(maxp);
			     if((minprix>=0) && (maxprix>=0)){
			        try {
						this.fenp.getPf().donnerCout_Alerte_PourEntreprise(this.E.getS().getSymbol(), minprix, maxprix);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			     }
			     else{new Text_input_erreur("La valeur doit être positive!");}
			}
			
			
			else{new Text_input_erreur("La valeur doit être entier");}
			this.jf.dispose();
			
		}
	}
	
	
}
