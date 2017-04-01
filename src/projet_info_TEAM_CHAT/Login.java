
package projet_info_TEAM_CHAT;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.Vector;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class Login {

	private String Path="fiche_identifiant/id.txt";
	private HashMap<String,String> List_id=new HashMap<>();
	private Collection<String> Ids=new Vector<>();
	
	

	public String getPath() {
		return Path;
	}

	public void setPath(String path) {
		Path = path;
	}

	public HashMap<String, String> getList_id() {
		return List_id;
	}

	public void setList_id(HashMap<String, String> list_id) {
		List_id = list_id;
	}
	public Collection<String> getIds() {
		return Ids;
	}

	public void setIds(Collection<String> ids) {
		Ids = ids;
	}

	
	
	
	
	
	public Login(String id,String pass_word) throws IOException{
		
		
		this.chargerList_id();
		if(this.verifier(id, pass_word)){
			    String path="fiche_porte_feuille/"+id+".txt";
				final PorteFeuille p=new PorteFeuille(id,path);
				
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
							p.chargerList();
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
		             	   double v=e.getSumValeurActuel();
		             	   int k= fp.getTable().getRowCount();
		             	   System.out.println(k);
		             	   for(int i=0;i<k;i++){
		             	        	if(e.getS().getSymbol().equals(fp.getTable().getValueAt(i, 1))){
		             			       fp.getTable().setValueAt(v, i,4);
		             			      fp.getTable().setValueAt(quantite, i,2);
		             			      fp.getTable().setValueAt(e.getAlerte(), i,5);
		             			       System.out.println("Raffraichissement réalisé numéro "+i);  
		             		       }
		             	    }
		                }
		            }
		        }; 
				
				new java.util.Timer().schedule( ttk,0,500);
				
		}
		else{new Text_input_erreur("L'identifiant ou le mot de passe est incorrect.");}
	}
	
	
	public boolean verifier(String id,String pass_word){
		if(this.Ids.contains(id)){
			if(this.List_id.get(id).equalsIgnoreCase(pass_word)){
				return true;
			}
			else{return false;}
		}
		
		else{return false;}
	}
	
	
	
	public void chargerList_id(){
		//this.List_id.removeAll(this.List_id);
		FileInputStream f=null;
		try{
		f=new FileInputStream(this.Path);
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.out.println("Ce fichier n'existe pas.");
		}
		//read the file in Ta 
		BufferedReader dr=new BufferedReader(new InputStreamReader(f));
		Collection<String> Ta=new Vector<>();
		String s;
		try {
			while ((s=dr.readLine())!=null){
				Collections.addAll(Ta,s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//get subString from a String seperate as " "
				@SuppressWarnings("rawtypes")
				Iterator it;
				it=Ta.iterator();
				while(it.hasNext()){
					String so=(String) it.next();
				    int index;
				    int end=so.length();
				    index=so.indexOf(" ");
				   
				    String id,mp;
				    id=so.substring(0,index);
				    mp=so.substring(index+1,end);
				    
				    this.List_id.put(id, mp); 
				    Collections.addAll(this.Ids,id);
				}			
	}
	
	
	// TEST
	public static void main(String[] args) throws IOException{
		 new Login("Alice","123456");
	}
	
}
