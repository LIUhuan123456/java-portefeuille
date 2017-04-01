package projet_info_TEAM_CHAT;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.commons.lang.NumberUtils;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@SuppressWarnings("deprecation")
public class Fenetre_porteFeuille implements ActionListener, MouseListener{

	private PorteFeuille pf;
	private int len;
	private JFrame jf=new JFrame("Bienvenue sur votre Portefeuille ");
	private StandardButton jb_achat=new StandardButton("Acheter");
	private StandardButton jb_vendre=new StandardButton("Vendre");
	private JTextField jt_symbol=new JTextField(10);
	private JTextField jt_quantite=new JTextField(10);
	private JLabel jl_capitale=new JLabel();
	private JLabel jl_etat=new JLabel();
	private JTable table=new JTable();
	
	private ColoredJPanel jp_right=new ColoredJPanel(new Color(80,80,80));
	private JTabbedPane tabbedPane = new JTabbedPane();
	private ColoredJPanel card_actions=new ColoredJPanel(new Color(160,160,160));
	private ColoredJPanel card_news=new ColoredJPanel(new Color(160,160,160));
	private ColoredJPanel card_graph=new ColoredJPanel(new Color(160,160,160));
	private ColoredJPanel card_rechercher=new ColoredJPanel(new Color(160,160,160));
	private String[] columnNames={"Nom",
            "Alias",
            "Quantité",
            "Valeur Initiale",
            "Valeur Actuelle",
            "Alerte"};
	
	public void setJt_symbol(String s){
		this.jt_symbol.setText(s);
	}
	
	
	
	public PorteFeuille getPf() {
		return pf;
	}



	public void setPf(PorteFeuille pf) {
		this.pf = pf;
	}


	public JTable getTable() {
		return table;
	}



	public void setTable(JTable table) {
		this.table = table;
	}


	
	
	public void ajouterColumn(String nom,String symbol){
		DefaultTableModel model =(DefaultTableModel) this.table.getModel();
		model.addRow(new Object[]{nom,symbol,0,0.0,0.0,""});
		this.table.setModel(model);
	}
	
	public void supprimerColumn(String symbol){
		int k=this.table.getColumnCount();
		for(int i=0;i<k+1;i++){
		    if(symbol.equalsIgnoreCase((String)this.table.getValueAt(i, 1))){
		    	DefaultTableModel model =(DefaultTableModel) this.table.getModel();
		    	model.removeRow(i);
		    	this.table.setModel(model);
		    	break;
		    	}			   
		}
	}
	
	
	

	public Fenetre_porteFeuille(PorteFeuille p) throws IOException {
		this.pf = p;
		this.len=this.pf.getListEntreprise().size();
		Object[][] data=this.getData();
		//DefaultTableModel model = new DefaultTableModel(data,this.columnNames);
		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(data,this.columnNames) {
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};

		this.table=new JTable(model);
        this.table.addMouseListener(this);	
		
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scrollPane = new JScrollPane(this.table);
		this.table.setFillsViewportHeight(true);
		
		ColoredJPanel jp_table=new ColoredJPanel(new Color(80,80,80));
		jp_table.add(scrollPane);

		String s="Bienvenue ";
		s=s+this.pf.getNomEmploye();
		JLabel jl=new JLabel(s,JLabel.RIGHT);
		ColoredJPanel jp_label=new ColoredJPanel(new Color(80,80,80));
		jp_label.add(jl);
		jl.setForeground(Color.WHITE);
		jl.setFont(new Font("Serif", Font.BOLD, 25 ));
		
		String capitale="Votre capital:  "+this.pf.getValeurReel()+" euros";
		this.jl_capitale.setText(capitale);
		ColoredJPanel jp_capitale=new ColoredJPanel(new Color(80,80,80));
		jp_capitale.add(jl_capitale);
		jl_capitale.setForeground(Color.WHITE);
		jl_capitale.setFont(new Font("Serif", Font.BOLD, 18 ));
		
		String etat="L'état de votre portefeuille:  "+this.pf.getValeurTotalActuel()+" euros";
		this.jl_etat.setText(etat);
		ColoredJPanel jp_etat=new ColoredJPanel(new Color(80,80,80));
		jp_etat.add(jl_etat);
		jl_etat.setForeground(Color.WHITE);
		jl_etat.setFont(new Font("Serif", Font.BOLD, 18 ));
		
		JLabel jl_symbol=new JLabel("Alias      ");
		jl_symbol.setForeground(Color.WHITE);
		jl_symbol.setFont(new Font("Serif", Font.BOLD, 14 ));
		ColoredJPanel jp_symbol=new ColoredJPanel(new Color(80,80,80));
		jp_symbol.add(jl_symbol,BorderLayout.PAGE_START);
		jp_symbol.add(jt_symbol,BorderLayout.PAGE_END);
		
		JLabel jl_quantite=new JLabel("Quantite");
		jl_quantite.setForeground(Color.WHITE);
		jl_quantite.setFont(new Font("Serif", Font.BOLD, 14 ));
		ColoredJPanel jp_quantite=new ColoredJPanel(new Color(80,80,80));
		jp_quantite.add(jl_quantite,BorderLayout.PAGE_START);
		jp_quantite.add(jt_quantite,BorderLayout.PAGE_END);
		
		
		jb_achat.addActionListener(this);
		jb_vendre.addActionListener(this);
		ColoredJPanel jp_achat=new ColoredJPanel(new Color(80,80,80));
		jp_achat.add(jb_achat,BorderLayout.LINE_START);
		jp_achat.add(jb_vendre,BorderLayout.LINE_END);
		
		ColoredJPanel jp_left=new ColoredJPanel(new Color(80,80,80));
		
		
		jp_left.add(jp_capitale);
		jp_left.add(jp_table);
		jp_left.add(jp_etat);
		jp_left.add(jp_symbol);
		jp_left.add(jp_quantite);
		jp_left.add(jp_achat);
		jp_left.setLayout(new BoxLayout(jp_left,BoxLayout.Y_AXIS));
		
		tabbedPane.addTab("Historique", card_actions);
        tabbedPane.addTab("Evolution", card_graph);
        tabbedPane.addTab("News", card_news);
        tabbedPane.addTab("Recherche", card_rechercher);
        tabbedPane.setBackground(new Color(113, 172, 217));
        jp_right.add(tabbedPane, BorderLayout.CENTER);
        jp_right.setVisible(true);
        
        
        ColoredJPanel jp_top=new ColoredJPanel(new Color(80,80,80));
        jp_top.add(jp_label);
        
        ColoredJPanel jp_bottom=new ColoredJPanel(new Color(80,80,80));
        jp_bottom.setLayout(new GridLayout(1,2));
        jp_bottom.add(jp_left);
        jp_bottom.add(jp_right);
        
        ColoredJPanel jp_all=new ColoredJPanel(new Color(80,80,80));
        jp_all.setLayout(new BoxLayout(jp_all, BoxLayout.Y_AXIS));
        jp_all.add(jp_top);
        jp_all.add(jp_bottom);
        
		jf.add(jp_all);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(1280,720);
		this.jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	
		
		
		Fenetre_Recherche fr= new Fenetre_Recherche("google","",this);
	    fr.creatPanel(this.card_rechercher);
	    this.card_rechercher.revalidate();
	    this.card_rechercher.repaint();
		
		
		
	}

	public Object[][] getData(){
		  
		this.len=this.pf.getListEntreprise().size();
		Object data[][]=new Object[len][6];
		int i=0;
		@SuppressWarnings("rawtypes")
		Iterator it;
		it=this.pf.getListEntreprise().iterator();
		   while(it.hasNext()){
				Entreprise e=new Entreprise((Entreprise) it.next());
				data[i][0]=e.getS().getName();
				data[i][1]=e.getS().getSymbol();
				data[i][2]=e.getSumQuantite();
				data[i][3]=e.getSumValeurInitial();
				data[i][4]=e.getSumValeurActuel();
	            String alerte="";
				if(alerte.equalsIgnoreCase(e.getAlerte())){data[i][5]="";}
				else{data[i][5]=e.getAlerte();}
				i++;
			}
		   return data;
	}
	
	public int getRowofTable(String symbol,Object data_table[][]) {
    	
		for(int i=0;i< data_table.length;i++){
			if(symbol.equals(data_table[i][1])){return i;}
		}
		return -1;
	}
	
	public void updateAlerteAction(Fenetre_entreprise e){
		e.creatPanel(this.card_actions);
		this.card_actions.revalidate();
		this.card_actions.repaint();
	}
	
	public void updateNewsToSearchPage(String s) throws IOException{
		Search_symbol s_s= new Search_symbol(s,this);
	    s_s.creatPanel(this.card_rechercher);
	    this.card_rechercher.revalidate();
	    this.card_rechercher.repaint();
	}
	
	public void updateSearchPageToNews(String symbol) throws IOException{
		Fenetre_Recherche fn= new Fenetre_Recherche(symbol,this);
	    fn.creatPanel(this.card_rechercher);
	    this.card_rechercher.revalidate();
	    this.card_rechercher.repaint();
	}

	public void setAlerteDansTable(String Symbol){
		int k= this.getTable().getColumnCount();
  	   for(int i=0;i<k;i++){
  	        	if(Symbol.equals(this.getTable().getValueAt(i, 1))){
  			       this.getTable().setValueAt("A Risque", i,5);  
  			       break;
  		       }
  	   }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb_achat){
			String s=jt_quantite.getText();
			int k=0;
			   if(NumberUtils.isDigits(s))
			   {
				 int i;
			     i=Integer.parseInt(s);
			     String symbol=jt_symbol.getText();
			     String  path="fiche_entreprise/";
			     path=path+this.pf.getNomEmploye();
			     if(i>0){
			    	 try {
					      k=this.pf.acheter(symbol, i, path);
					      String etat="Etat:  "+this.pf.getValeurTotalActuel()+" euros";
					      
				      } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				      }
			       }
			     else{new Text_input_erreur("La Quantite doit être positive");}
			   }
			   else{new Text_input_erreur("La Quantite doit être un entier");}
			  
			   if(k==1){
			       String symbol=jt_symbol.getText();
			        try {
						this.pf.chargerList();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
			        int rows = this.table.getRowCount();
					Object data_table[][]=this.getData();
					int len_data=data_table.length;
			        int r=this.getRowofTable(symbol,data_table);
			        if(len_data>rows){
			        	DefaultTableModel model1 = (DefaultTableModel) this.table.getModel();
			            model1.addRow(new Object[]{data_table[len_data-1][0],data_table[len_data-1][1], data_table[len_data-1][2],data_table[len_data-1][3],data_table[len_data-1][4],data_table[len_data-1][5]});
			        }
			        else{
					   for(int i=0;i<6;i++){
					    table.setValueAt(data_table[r][i], r, i);
					    }
			        }
			     }  
			   String capitale="Capitale:  "+this.pf.getValeurReel()+" euros";
			   this.jl_capitale.setText(capitale);
			   String etat="Etat:  "+this.pf.getValeurTotalActuel()+" euros";
			   this.jl_etat.setText(etat);
			}
		
		if(e.getSource()==jb_vendre){
			String s=jt_quantite.getText();
			int k=0;
			   if(NumberUtils.isDigits(s))
			   {
				 int i;
			     i=Integer.parseInt(s);
			     String symbol=jt_symbol.getText();
			     String  path="fiche_entreprise/";
			     path=path+this.pf.getNomEmploye();
			     if(i>0){
			    	 
			    	    try {
							  if(this.pf.verifierQuatite(symbol, i)){
								  try {
								        k=this.pf.acheter(symbol, (-i), path);
							        } catch (IOException e1) {
								        // TODO Auto-generated catch block
								        e1.printStackTrace();
							        }
							     }
							  else{new Text_input_erreur("Vous ne possédez pas cette quantité d'actions.");}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			    	 
			    	 
			       }
			     else{new Text_input_erreur("La quantite doit être positive");}
			   }
			   else{new Text_input_erreur("La quantite doit être un entier");}
			   
			   if(k==1){
				   String symbol=jt_symbol.getText();
			        try {
						this.pf.chargerList();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					Object data_table[][]=this.getData();
			        int r=this.getRowofTable(symbol,data_table);
					for(int i=0;i<6;i++){
					table.setValueAt(data_table[r][i], r, i);
					}
			      }
			   String capitale="Capitale:  "+this.pf.getValeurReel()+" euros";
			   this.jl_capitale.setText(capitale);
			   String etat="Etat:  "+this.pf.getValeurTotalActuel()+" euros";
			   this.jl_etat.setText(etat);
			}
	}
	

	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row=table.rowAtPoint(e.getPoint());
		//int col=table.columnAtPoint(e.getPoint());
		Object[][] ob=this.getData();
		
			String path="fiche_entreprise/";
			path=path+this.pf.getNomEmploye()+"/"+ob[row][1]+".txt";
			try {
				Entreprise en=new Entreprise(this.pf.chercherEntreprise((String)ob[row][1]));
			    Fenetre_entreprise fen=new Fenetre_entreprise(en,this);
			    fen.creatPanel(this.card_actions);
			    this.card_actions.revalidate();
			    this.card_actions.repaint();
			    Fenetre_news fn= new Fenetre_news((String) ob[row][1],(String) ob[row][0],this);
			    fn.creatPanel(this.card_news);
			    this.card_news.revalidate();
			    this.card_news.repaint();
			    Draw_graph_entreprise dg=new Draw_graph_entreprise(en);
			    dg.creatPanel(this.card_graph);
			    this.card_graph.revalidate();
			    this.card_graph.repaint();
			    
			    Fenetre_Recherche fr= new Fenetre_Recherche((String) ob[row][1],(String) ob[row][0],this);
			    fr.creatPanel(this.card_rechercher);
			    this.card_rechercher.revalidate();
			    this.card_rechercher.repaint();
			    
			    this.jp_right.revalidate();
			    this.jp_right.repaint();
			    this.jt_symbol.setText((String)ob[row][1]);
			    
			    
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	public static void main(String[] args) throws IOException{
		
		
		final PorteFeuille p=new PorteFeuille("Alice","fiche_porte_feuille/Alice.txt");
		final Fenetre_porteFeuille fp=new Fenetre_porteFeuille(p);
		@SuppressWarnings("rawtypes")
		Iterator it1=p.getListEntreprise().iterator();
        while(it1.hasNext()){
        	Entreprise e=new Entreprise((Entreprise) it1.next(),p);
        	e.regleAcheterVendre();
        	System.out.println("it1");
        }
		fp.getPf().chargerList();
		
		
		//refresh
		
		TimerTask ttk=new java.util.TimerTask() {
            @Override
            public void run() {
                // your code here
         	   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
         	   Date date = new Date();
         	   System.out.println(dateFormat.format(date));
         	    try {
					fp.getPf().chargerList();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
                Iterator it=fp.getPf().getListEntreprise().iterator();
                while(it.hasNext()){
             	   Entreprise e=new Entreprise((Entreprise) it.next(),p);

             	   try {
					   Stock s=YahooFinance.get(e.getS().getSymbol());
					   e.setPrixUnitair((float) s.getQuote().getPrice().doubleValue());
				   } catch (IOException e1) {
					   // TODO Auto-generated catch block
					   e1.printStackTrace();
				   }
             	   
             	   try {
					  e.autoSetAlerte();
				   } catch (IOException e1) {
					// TODO Auto-generated catch block
					  e1.printStackTrace();
				   }
             	   
             	   e.calculerSumValeurActuel();
             	   int quantite=e.getSumQuantite();
             	   float v=e.getSumValeurActuel();
             	   int k= fp.getTable().getRowCount();
             	   for(int i=0;i<k;i++){
             	        	if(e.getS().getSymbol().equals(fp.getTable().getValueAt(i, 1))){
             			       fp.getTable().setValueAt(v, i,4);
             			      fp.getTable().setValueAt(quantite, i,2);
             			      fp.getTable().setValueAt(e.getAlerte(), i,5);
             			       System.out.println("Raffraichissement de la page numéro "+i);  
             		       }
             	    }
                }
            }
        }; 
		
		new java.util.Timer().schedule( ttk,0,500);
		
	}
}
