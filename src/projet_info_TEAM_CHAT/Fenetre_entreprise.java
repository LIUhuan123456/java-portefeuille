
package projet_info_TEAM_CHAT;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Fenetre_entreprise implements ActionListener {

	private Entreprise En;
    private JFrame jf=new JFrame();
	private StandardButton jb_alerte=new StandardButton("A RISQUE!");
	private StandardButton jb_alerte2=new StandardButton("NON A RISQUE");
	private StandardButton jb_setRegle=new StandardButton("ACHAT/VENTE AUTOMATIQUES");
	private StandardButton jb_coutAlerte=new StandardButton("CREER UNE ALERTE ");
	private Fenetre_porteFeuille fenp;
    
	public Fenetre_entreprise(Entreprise e,Fenetre_porteFeuille f) {
		this.En=e;
		this.fenp=f;
	}

   @SuppressWarnings({ "rawtypes" })
   public String[][] getData(){
	   int len=this.En.getListAction().size();
	   String data[][] = new String[len][7];
	   int i=0;
	   Iterator it;
	   it=this.En.getListAction().iterator();
	   while(it.hasNext()){
			Action a=new Action((Action) it.next());
			data[i][0]=a.getDate();
			data[i][1]=String.valueOf(a.getQuantite());
			data[i][2]=String.valueOf(a.getPrixActuel());
			data[i][3]=String.valueOf(a.getPrixActuel()*Math.abs(a.getQuantite()));
			data[i][4]=String.valueOf(a.getPrixInitial());
			data[i][5]=String.valueOf(a.getPrixInitial()*Math.abs(a.getQuantite()));
			data[i][6]=String.valueOf(a.getPrixActuel()*a.getQuantite()-a.getPrixInitial()*a.getQuantite());
			i++;
		}
	   return data;
   }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.jb_alerte){
			try {
				this.fenp.getPf().changer_etat_alterte(this.En.getS().getSymbol(), "A risque!");
				this.fenp.setAlerteDansTable(this.En.getS().getSymbol());
				this.En.setAlerte("A risque!");
				this.fenp.updateAlerteAction(this);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		if(e.getSource()==this.jb_alerte2){
			try {
				this.fenp.getPf().changer_etat_alterte(this.En.getS().getSymbol(), " ");
				this.En.setAlerte("");
				this.fenp.updateAlerteAction(this);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		
	   if(e.getSource()==this.jb_setRegle){
		   new Fenetre_setRegle(this.fenp,this.En);    
	   }
	   
	   if(e.getSource()==this.jb_coutAlerte){
		   new Fenetre_setCoutAlerte(this.fenp,this.En);
	   }
	   
	}
	//--------------------------------------------------------------------

	public void creatPanel(JPanel jp) {
		jp.removeAll();
		
		String[] columnNames={"Date",
	              "Quantité",
	               "Prix Actuel",
	               "Valeur Actuelle",
	               "Prix Initial",
	               "Valeur Initiale",
	              "Valeur latente"};
        String[][] data=this.getData();

        JLabel jl_alerte=new JLabel();
        JLabel jl_alerte2=new JLabel();
        ColoredJPanel jp_labels=new ColoredJPanel(new Color(160,160,160));
        ColoredJPanel jp_modifier_alertes=new ColoredJPanel(new Color(160,160,160));
        ColoredJPanel jp_table=new ColoredJPanel(new Color(160,160,160));
        ColoredJPanel jp_title=new ColoredJPanel(new Color(160,160,160));
        ColoredJPanel jp_alerte=new ColoredJPanel(new Color(160,160,160));
        ColoredJPanel jp_setCout=new ColoredJPanel(new Color(160,160,160));
        ColoredJPanel jp_top=new ColoredJPanel(new Color(160,160,160));
        if(!this.En.getAlerte().equalsIgnoreCase(" ")){
        	jl_alerte.setText(this.En.getAlerte());
        	jl_alerte.setForeground(Color.red);
        }
        if(!this.En.getAlerte2().equalsIgnoreCase(" ")){
        	jl_alerte2.setText(this.En.getAlerte2());
        	jl_alerte2.setForeground(Color.red);
        }
        
        jp_labels.add(jl_alerte);
        jp_labels.add(jl_alerte2);
        jp_labels.setLayout(new BoxLayout(jp_labels,BoxLayout.Y_AXIS));
        
        jp_modifier_alertes.add(this.jb_alerte);
        jp_modifier_alertes.add(this.jb_alerte2);
        jp_modifier_alertes.setLayout(new BoxLayout(jp_modifier_alertes,BoxLayout.Y_AXIS));
        
        JTable table=new JTable(data,columnNames);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        jp.setSize(500, 500);
        jp.setVisible(true);	

        JLabel jl_name=new JLabel(this.En.getS().getName(),JLabel.CENTER);
        JLabel jl_symbol=new JLabel(this.En.getS().getSymbol(),JLabel.CENTER);
        jp_title.add(jl_name,BorderLayout.PAGE_START);
        jp_title.add(jl_symbol,BorderLayout.PAGE_END);
        jp_alerte.add(jp_labels,BorderLayout.PAGE_START);
        jp_setCout.add(this.jb_setRegle,BorderLayout.PAGE_START);
        jp_setCout.add(this.jb_coutAlerte,BorderLayout.PAGE_END);
        this.jb_alerte.addActionListener(this);
        this.jb_alerte2.addActionListener(this);
        this.jb_coutAlerte.addActionListener(this);
        this.jb_setRegle.addActionListener(this);
        jp_alerte.add(jp_modifier_alertes,BorderLayout.PAGE_END);
        jp_top.setLayout(new GridLayout(1,2));
        jp_top.add(jp_title);
        jp_top.add(jp_alerte);
        jp_table.add(scrollPane);
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        jp.add(jp_top);
        jp.add(jp_table);
        jp.add(jp_setCout);
        

	}
	
	//--------------------------------------------------------------------
	
}
