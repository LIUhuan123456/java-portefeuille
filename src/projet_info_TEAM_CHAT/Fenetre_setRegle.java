
package projet_info_TEAM_CHAT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang.NumberUtils;

public class Fenetre_setRegle implements ActionListener{

	private Fenetre_porteFeuille fenp;
	private Entreprise E;
	
	private JFrame jf=new JFrame("Créer une régle d'Achat/vente");
	
	private JComboBox jcb_min =new JComboBox();
	private JComboBox jcb_max=new JComboBox();
	private String a="Acheter";
	private String v="Vendre";
	private int rmin=3,rmax=3;
	
	private StandardButton jb_valider=new StandardButton("Valider");
	private StandardButton jb_return=new StandardButton("Retour");
	private JTextField jt_min=new JTextField(5);
	private JTextField jt_max=new JTextField(5);
	private JTextField jt_quantite=new JTextField(5);

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	Fenetre_setRegle(Fenetre_porteFeuille p,Entreprise e){
		this.fenp=p;
	    this.E=e;
	    ColoredJPanel jp_min=new ColoredJPanel(new Color(160,160,160));
	    ColoredJPanel jp_max=new ColoredJPanel(new Color(160,160,160));
	    ColoredJPanel jp_button=new ColoredJPanel(new Color(160,160,160));
	    ColoredJPanel jp_all=new ColoredJPanel(new Color(160,160,160));
	    ColoredJPanel jp_quantite=new ColoredJPanel(new Color(160,160,160));
	    ColoredJPanel jp_regle=new ColoredJPanel(new Color(160,160,160));
	    ColoredJPanel jp_text=new ColoredJPanel(new Color(160,160,160));
        
        JLabel jl_min_avant=new JLabel("Si le prix est inférieur");
        JLabel jl_min_centre=new JLabel("je souhaite");
        JLabel jl_max_avant=new JLabel("Si le prix est supérieur");
        JLabel jl_max_centre=new JLabel("je souhaite");
        
        JLabel jl_quantite=new JLabel("Action(s)");
        
        jp_quantite.add(this.jt_quantite);
        jp_quantite.add(jl_quantite);
        jp_quantite.setLayout(new FlowLayout());
        
        
        
        DefaultComboBoxModel comboModel_min = new DefaultComboBoxModel();
        comboModel_min.addElement(this.a);
        comboModel_min.addElement(this.v);
        this.jcb_min.setModel(comboModel_min);
        
        DefaultComboBoxModel comboModel_max= new DefaultComboBoxModel();
        comboModel_max.addElement(this.a);
        comboModel_max.addElement(this.v);
        this.jcb_max.setModel(comboModel_max);
        
        jp_min.add(jl_min_avant);
        jp_min.add(this.jt_min);
        jp_min.add(jl_min_centre);
        jp_min.add(this.jcb_min);
        jp_min.setLayout(new FlowLayout());
        
        jp_max.add(jl_max_avant);
        jp_max.add(this.jt_max);
        jp_max.add(jl_max_centre);
        jp_max.add(this.jcb_max);
        jp_max.setLayout(new FlowLayout());
        
        jp_regle.add(jp_min);
        jp_regle.add(jp_max);
        jp_regle.setLayout(new BoxLayout(jp_regle, BoxLayout.Y_AXIS));
        
        jp_text.add(jp_regle);
        jp_text.add(jp_quantite);
        jp_text.setLayout(new FlowLayout());
        
       
        
        jp_button.add(this.jb_valider,BorderLayout.PAGE_START);
        jp_button.add(this.jb_return,BorderLayout.PAGE_END);
        this.jb_return.addActionListener(this);
        this.jb_valider.addActionListener(this);
        
        
        jp_all.add(jp_text);
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
			
			String a="Acheter";
			String v="Vendre";
			if(a.equalsIgnoreCase((String)this.jcb_max.getSelectedItem())){this.rmax=0;}
			if(v.equalsIgnoreCase((String)this.jcb_max.getSelectedItem())){this.rmax=1;}
			if(a.equalsIgnoreCase((String)this.jcb_min.getSelectedItem())){this.rmin=0;}
			if(v.equalsIgnoreCase((String)this.jcb_min.getSelectedItem())){this.rmin=1;}
			
			String minp=this.jt_min.getText();
			String maxp=this.jt_max.getText();
			String quantite=this.jt_quantite.getText();
			
			double minprix;
			double maxprix;
			int q;

			if(NumberUtils.isNumber(minp) && NumberUtils.isNumber(maxp) && NumberUtils.isDigits(quantite) ){
			     minprix=Double.parseDouble(minp);
			     maxprix=Double.parseDouble(maxp);
			     q=Integer.parseInt(quantite);
			     
			     int regle=0;//A:acheter,V:vendre || k=1 A<min,A>max; k=2 A<min,V>max; k=3 V<min,V>max; k=4 V<min,A>max
                 if((this.rmin==0) && (this.rmax==0)){regle=1;}
                 if((this.rmin==0) && (this.rmax==1)){regle=2;}
                 if((this.rmin==1) && (this.rmax==1)){regle=3;}
                 if((this.rmin==1) && (this.rmax==0)){regle=4;}
			     
			     if((minprix>=0) && (maxprix>=0) && ((q>=0))){
			    	
			        try {
						this.fenp.getPf().donnerRegleAutoAcheter_Vendre(this.E.getS().getSymbol(), regle, q);
						this.fenp.getPf().donnerCout_Acheter_Vendre_PourEntreprise(this.E.getS().getSymbol(), minprix, maxprix);
						new Text_input_erreur("Le changement a été prix en compte et sera exécuté la prochaine fois ! ");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			     }
			     
			     else{new Text_input_erreur("La valeur doit être positive !");}
			}
			
			
			else{new Text_input_erreur("Le prix doit être un nombre ET la quantitée doit être un entier!");}
			this.jf.dispose();
			
		}
	}
	
	
}
