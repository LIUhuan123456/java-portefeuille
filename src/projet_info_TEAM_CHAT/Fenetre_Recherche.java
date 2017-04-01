package projet_info_TEAM_CHAT;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.plaf.metal.MetalBorders;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class Fenetre_Recherche implements ActionListener{
     
	private URL Url;
	private String Nom;
	private String Symbol;
	private HashMap<String,String> M;
	private JFrame jf=new JFrame();
	private Vector<JButton> List_jb=new Vector<>();

	private StandardButton jb_return=new StandardButton("Retour");
	private Vector<String> List_key=new Vector<>();
	private JTextField jt=new JTextField(20);
	private StandardButton jb=new StandardButton("Rechercher");

	private Fenetre_porteFeuille fenp;
	private StandardButton jb_supprimer=new StandardButton("Supprimer");
	private StandardButton jb_ajouter=new StandardButton("Ajouter");
	
	
	
	
	
	public String getSymbol() {
		return Symbol;
	}

	public void setSymbol(String symbol) {
		Symbol = symbol;
	}

	public Fenetre_Recherche(String symbol,Fenetre_porteFeuille f) throws IOException {
		super();
		this.Symbol=symbol;
		String url="http://finance.yahoo.com/q?s="+symbol;
		Url =new URL(url);
		Stock s=YahooFinance.get(symbol);
		this.Nom=s.getName();
		this.fenp=f;
		this.M=this.saisirMap();
	}
	
	public Fenetre_Recherche(String symbol,String nom,Fenetre_porteFeuille f) throws IOException {
		super();
		this.Symbol=symbol;
		String url="http://finance.yahoo.com/q?s="+symbol;
		Url =new URL(url);
		this.Nom=nom;
		this.fenp=f;
		this.M=this.saisirMap();

	}

	public void creatPanel(JPanel jp){
		
		jp.removeAll();
		ColoredJPanel jp_search=new ColoredJPanel(new Color(160,160,160));
		jp_search.add(this.jt,BorderLayout.PAGE_START);
		jp_search.add(this.jb,BorderLayout.PAGE_END);
		jb.addActionListener(this);
		jp.add(jp_search);
     
		ColoredJPanel jp_ajouter_supprimer=new ColoredJPanel(new Color(160,160,160));
		jp_ajouter_supprimer.add(this.jb_ajouter,BorderLayout.PAGE_START);
		jp_ajouter_supprimer.add(this.jb_supprimer,BorderLayout.PAGE_END);
		this.jb_ajouter.addActionListener(this);
		this.jb_supprimer.addActionListener(this);
		
		JLabel jl_nom=new JLabel(this.Nom);
		
		jl_nom.setForeground(Color.white);

		jl_nom.setFont(new Font("Calibri", Font.BOLD, 16 ));
		
		 jp.add(jl_nom);

		
		for ( String key : M.keySet() ) {
		    Collections.addAll(this.List_key, key);
		    String jkey=key;
		    if(jkey.length()>55){
		    	jkey=jkey.substring(0, 55);
		    	jkey=jkey+"...";
		    }
		    JButton jb=new JButton(jkey);
		    jb.setBackground(Color.white);
		    jb.addActionListener(this);
		    Collections.addAll(this.List_jb, jb);
		    jp.add(jb);
		}
		
		jp.add(jp_ajouter_supprimer);

		jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.jb){
			String s=this.jt.getText();
			try {
				this.fenp.updateNewsToSearchPage(s);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if(e.getSource()==this.jb_ajouter){
			int f=0;
			@SuppressWarnings("rawtypes")
			Iterator it=this.fenp.getPf().getListEntreprise().iterator();
			while(it.hasNext()){
				Entreprise e2=new Entreprise((Entreprise) it.next());
				if(this.Symbol.equalsIgnoreCase(e2.getS().getSymbol())){f=1;break;}
			}

			if(f==1){new Text_input_erreur("Cette entreprise existe déj?");}

			else{
				   String path="fiche_entreprise/";
				   path=path+this.fenp.getPf().getNomEmploye();
				   this.fenp.getPf().ajouterEntreprise(this.Symbol, path);
				   String nom="Erreur";
				   
				   try {
					   Stock s=YahooFinance.get(this.Symbol);
					   nom=s.getName();
				    } catch (IOException e2) {
					// TODO Auto-generated catch block
					   e2.printStackTrace();
				    }
				   
				   this.fenp.ajouterColumn(nom,this.Symbol);  
				   
				   try {
					   this.fenp.getPf().chargerList();
				    } catch (IOException e1) {
				    	// TODO Auto-generated catch block
					   e1.printStackTrace();
				    }
			    }
			
		}
		
		if(e.getSource()==this.jb_supprimer){
			@SuppressWarnings("rawtypes")
			Iterator it=this.fenp.getPf().getListEntreprise().iterator();
			while(it.hasNext()){
				Entreprise e2=new Entreprise((Entreprise) it.next());
				if(this.Symbol.equalsIgnoreCase(e2.getS().getSymbol())){
					if(e2.getSumQuantite()==0){
						   String path="fiche_entreprise/";
						   path=path+this.fenp.getPf().getNomEmploye();
						   try {
							  this.fenp.getPf().supprimerEntreprise(this.Symbol, path);
						   } catch (IOException e1) {
							// TODO Auto-generated catch block
						    	e1.printStackTrace();
						   }
						   
						   this.fenp.supprimerColumn(this.Symbol);
					}

					else{new Text_input_erreur("Vous possédez toujours des actions pour cette entreprise.");}

				}
			}
			
			try {
				   this.fenp.getPf().chargerList();
			    } catch (IOException e1) {
			    	// TODO Auto-generated catch block
				   e1.printStackTrace();
			    }
			   
		}
		
		int len=this.List_jb.size();
		if(e.getSource()==this.jb_return){jf.dispose();}
		else{
			for(int i=0;i<len;i++){
				if(e.getSource()==this.List_jb.elementAt(i)){
					String key=this.List_key.elementAt(i);
					try {
						new LinkToNews(M.get(key));
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		
	}
	


	public HashMap<String,String> saisirMap() throws IOException{
		// Make a URL to the web page

        // Get the input stream through URL Connection
        URLConnection con = this.Url.openConnection();
        InputStream is =con.getInputStream();

        // Once you have the Input Stream, it's just plain old Java IO stuff.

        // For this case, since you are interested in getting plain-text web page
        // I'll use a reader and output the text content to System.out.

        // For binary content, it's better to directly read the bytes from stream and write
        // to the target file.


        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        String info=null;
        String line = null;


        // Lire chaque ligne et l'écrire.

        while ((line = br.readLine()) != null) {
        	 info=info+line+"\n";    
        }
        
 
        String sub_headline=null;
        int index_headline=-1;
        index_headline=info.indexOf("<div id=\"yfi_headlines\" class=\"yfi_quote_headline\">");

        if(index_headline==-1){ new Text_input_erreur("Il n'y a pas de news pour cette entreprise."); return new HashMap<String,String>();}

        else{
           sub_headline=info.substring(index_headline);
        
           String sub_div_menu=null;
           int index_div_menu_start,index_div_menu_end;
           index_div_menu_start=sub_headline.indexOf("<div class=\"bd\">");
           index_div_menu_end=sub_headline.indexOf("</div>",index_div_menu_start);
           sub_div_menu=sub_headline.substring(index_div_menu_start,index_div_menu_end);
           HashMap<String,String> map=new HashMap<>();
        
           while(!sub_div_menu.equalsIgnoreCase("</li></ul>")){
               String key;
               String value;
               String sub_li=null;
               int index_li_start=-1,index_li_end=-1;
               index_li_start=sub_div_menu.indexOf("<li>");
               index_li_end=sub_div_menu.indexOf("</li>",index_li_start);
               if((index_li_start>=index_li_end)||(index_li_start==-1)||(index_li_end==-1))

               { new Text_input_erreur("Il n'y a pas de news pour cette entreprise.");break;}

               else{
                  sub_li=sub_div_menu.substring(index_li_start, index_li_end);
                  int index_value_start,index_value_end;
                  index_value_start=sub_li.indexOf("http:");
                  index_value_end=sub_li.indexOf("\"",index_value_start);
                  value=sub_li.substring(index_value_start, index_value_end);
        
                  int index_key_start,index_key_end;
                  index_key_start=sub_li.indexOf(">",index_value_end);
                  index_key_end=sub_li.indexOf("<",index_key_start);
                  key=sub_li.substring(index_key_start+1, index_key_end);
                  map.put(key, value);
        
                  sub_div_menu=sub_div_menu.substring(index_li_end);
               }
             }
        
           return map;
        }
	}
	
	
}

