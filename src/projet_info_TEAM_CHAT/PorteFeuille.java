package projet_info_TEAM_CHAT;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.io.FileUtils;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class PorteFeuille {

	private String NomEmploye;
	private float ValeurTotal=1000000;   //initial capacite
	private float ValeurTotalActuel;     //l'argent total(les stocks+ValeurReel) ETAT
	private float ValeurReel;           //capacite reel
	private String Path;
	private Collection<Entreprise> ListEntreprise=new Vector<>();
	
    
    
    public PorteFeuille() {
		// TODO Auto-generated constructor stub
	}
	public PorteFeuille(String nom,String path) throws IOException{
		this.NomEmploye=nom;
		this.Path=path;
		this.chargerList();
		this.calculerValeurReel();
		this.calculerValeurTotalVirtuel();
	}
	public PorteFeuille(PorteFeuille p) {
		this.NomEmploye=p.getNomEmploye();
		this.ValeurTotal=(float) p.getValeurTotal();
		this.ValeurTotalActuel=(float) p.getValeurTotalActuel();
		this.ValeurReel=(float) p.getValeurReel();
		this.Path=p.getPath();
		this.ListEntreprise=p.getListEntreprise();
	}
	//constructeur~	
	//get set
	public String getNomEmploye() {
		return NomEmploye;
	}
	public void setNomEmploye(String nomEmploye) {
		NomEmploye = nomEmploye;
	}
	public float getValeurTotal() {
		return ValeurTotal;
	}
	public void setValeurTotal(float valeurTotal) {
		ValeurTotal =  valeurTotal;
	}
	public float getValeurTotalActuel() {
		return ValeurTotalActuel;
	}
	public void setValeurTotalActuel(float valeurTotalActuel) {
		ValeurTotalActuel=  valeurTotalActuel;
	}
	public float getValeurReel() {
		return ValeurReel;
	}
	public void setValeurReel(float valeurReel) {
		ValeurReel = valeurReel;
	}
	public String getPath() {
		return Path;
	}
	public void setPath(String path) {
		Path = path;
	}
	public Collection<Entreprise> getListEntreprise() {
		return ListEntreprise;
	}
	public void setListEntreprise(Collection<Entreprise> listEntreprise) {
		ListEntreprise = listEntreprise;
	}
	//other methodes
	public void ajouterEntreprise(Entreprise a){
		Collections.addAll(ListEntreprise, a);
	}
	
	public void supprimerEntreprise(String symbol){
		@SuppressWarnings("rawtypes")
		Iterator it= this.ListEntreprise.iterator();
		while(it.hasNext()){
			Entreprise e=new Entreprise((Entreprise) it.next());
			if(symbol.equalsIgnoreCase(e.getS().getSymbol())){
				this.ListEntreprise.remove(e);
			}
		}
	}
	
	public void changer_etat_alterte(String symbol,String l) throws IOException{
		
		 FileInputStream f1=null;
			try{
			f1=new FileInputStream(this.Path);
			}catch(FileNotFoundException e){
				e.printStackTrace();
				System.out.println("Ce fichier n'existe pas.");
			}
			//read the file in Ta 
			BufferedReader dr=new BufferedReader(new InputStreamReader(f1));
			Vector<String> Ta=new Vector<>();
			String s;
			try {
				while ((s=dr.readLine())!=null){
					Collections.addAll(Ta,s);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			f1.close();
			
			String line_recuperer="";
			int i=0;
			boolean touver_entreprise=false;
			@SuppressWarnings("rawtypes")
			Iterator it;
			it=Ta.iterator();
			while(it.hasNext()){
				String so=(String) it.next();
			    int index;
			    @SuppressWarnings("unused")
				int end=so.length();
			    index=so.indexOf("~");
			   
			    String alias;
			    alias=so.substring(0,index);
			    
			    if(alias.equalsIgnoreCase(symbol)){
			    	   Ta.removeElementAt(i); 
			    	   int index_separer;
			    	   index_separer=so.indexOf("~", index+1);
			    	   line_recuperer=so.substring(index_separer,so.length());
			    	   touver_entreprise=true;
			    	   break;
			    	} 
			    else{i++;}
			}
			
			if(!touver_entreprise){new Text_input_erreur("Cette entreprise n'existe pas dans votre portefeuille.");}
			else{
				  String n=symbol+"~"+l+line_recuperer;
				  Collections.addAll(Ta, n);
				  
				  File f2= new File(this.Path);
				   FileUtils.write(f2, "");
				   
				   @SuppressWarnings("rawtypes")
				Iterator it2;
					it2=Ta.iterator();
					while(it2.hasNext()){
						String so=(String) it2.next();
					    
						if(f2.exists()){
							try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f2.getPath(), true)))) {
				        	    out.println(so);
				        	    out.close();   
				        	}catch (IOException e) {
				        	    //exception handling left as an exercise for the reader
				        	}
					     }
				    }
			}
			
		
	}
	
    public void donnerCout_Alerte_PourEntreprise(String symbol,double min,double max) throws IOException{
    	
		 FileInputStream f1=null;
			try{
			f1=new FileInputStream(this.Path);
			}catch(FileNotFoundException e){
				e.printStackTrace();
				System.out.println("Ce fichier n'existe pas.");
			}
			//read the file in Ta 
			BufferedReader dr=new BufferedReader(new InputStreamReader(f1));
			Vector<String> Ta=new Vector<>();
			String s;
			try {
				while ((s=dr.readLine())!=null){
					Collections.addAll(Ta,s);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			f1.close();
			
			String line_recuperer="";
			String alerte_recuperer="";
			int i=0;
			boolean touver_entreprise=false;
			@SuppressWarnings("rawtypes")
			Iterator it;
			it=Ta.iterator();
			while(it.hasNext()){
				String so=(String) it.next();
			    int index;
			    @SuppressWarnings("unused")
				int end=so.length();
			    index=so.indexOf("~");
			   
			    String alias;
			    alias=so.substring(0,index);
			    
			    int index_alerte;
			    index_alerte=so.indexOf("~", index+1);
			    alerte_recuperer=so.substring(index+1, index_alerte);
			    
			    
			    if(alias.equalsIgnoreCase(symbol)){
			    	   Ta.removeElementAt(i); 
			    	   int index_separer;
			    	   index_separer=so.indexOf("~", index_alerte+1);
			    	   index_separer=so.indexOf("~", index_separer+1);
			    	   line_recuperer=so.substring(index_separer,so.length());
			    	   touver_entreprise=true;
			    	   break;
			    	} 
			    else{i++;}
			}
			
			if(!touver_entreprise){new Text_input_erreur("Cette entreprise n'existe pas dans votre portefeuille.");}
			else{
				  String n=symbol+"~"+alerte_recuperer+"~"+min+"~"+max+line_recuperer;
				  Collections.addAll(Ta, n);
				  
				  File f2= new File(this.Path);
				   FileUtils.write(f2, "");
				   
				   @SuppressWarnings("rawtypes")
				Iterator it2;
					it2=Ta.iterator();
					while(it2.hasNext()){
						String so=(String) it2.next();
					    
						if(f2.exists()){
							try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f2.getPath(), true)))) {
				        	    out.println(so);
				        	    out.close();   
				        	}catch (IOException e) {
				        	    //exception handling left as an exercise for the reader
				        	}
					     }
				    }
			}
			
    }
	
    
    public void donnerCout_Acheter_Vendre_PourEntreprise(String symbol,double min, double max) throws IOException{
    	
    	
    	FileInputStream f1=null;
		try{
		f1=new FileInputStream(this.Path);
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.out.println("Ce fichier n'existe pas.");
		}
		//read the file in Ta 
		BufferedReader dr=new BufferedReader(new InputStreamReader(f1));
		Vector<String> Ta=new Vector<>();
		String s;
		try {
			while ((s=dr.readLine())!=null){
				Collections.addAll(Ta,s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		f1.close();
		
		String line_recuperer="",line_recuperer2="";
		int i=0;
		boolean touver_entreprise=false;
		@SuppressWarnings("rawtypes")
		Iterator it;
		it=Ta.iterator();
		while(it.hasNext()){
			String so=(String) it.next();
		    int index;
		    @SuppressWarnings("unused")
			int end=so.length();
		    index=so.indexOf("~");
		   
		    String alias;
		    alias=so.substring(0,index);
		    
		    if(alias.equalsIgnoreCase(symbol)){
		    	   Ta.removeElementAt(i); 
		    	   int index_separer,index_separer2;
		    	   index_separer=so.indexOf("~", index+1);
		    	   index_separer=so.indexOf("~", index_separer+1);
		    	   index_separer=so.indexOf("~", index_separer+1);
		    	   index_separer2=so.indexOf("~", index_separer+1);
		    	   index_separer2=so.indexOf("~", index_separer2+1);
		    	   line_recuperer2=so.substring(index_separer2, so.length());
		    	   line_recuperer=so.substring(0,index_separer);
		    	   touver_entreprise=true;
		    	   break;
		    	} 
		    else{i++;}
		}
		
		if(!touver_entreprise){new Text_input_erreur("Cette entreprise n'existe pas dans votre portefeuille.");}
		else{
			  String n=line_recuperer+"~"+min+"~"+max+line_recuperer2;
			  Collections.addAll(Ta, n);
			  
			  File f2= new File(this.Path);
			   FileUtils.write(f2, "");
			   
			   @SuppressWarnings("rawtypes")
			Iterator it2;
				it2=Ta.iterator();
				while(it2.hasNext()){
					String so=(String) it2.next();
				    
					if(f2.exists()){
						try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f2.getPath(), true)))) {
			        	    out.println(so);
			        	    out.close();   
			        	}catch (IOException e) {
			        	    //exception handling left as an exercise for the reader
			        	}
				     }
			    }
		}
    }
    
    
    public void donnerRegleAutoAcheter_Vendre(String symbol,int regle,int autoQuantite) throws IOException{
    	
    	
    	FileInputStream f1=null;
		try{
		f1=new FileInputStream(this.Path);
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.out.println("Ce fichier n'existe pas.");
		}
		//read the file in Ta 
		BufferedReader dr=new BufferedReader(new InputStreamReader(f1));
		Vector<String> Ta=new Vector<>();
		String s;
		try {
			while ((s=dr.readLine())!=null){
				Collections.addAll(Ta,s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		f1.close();
		
		String line_recuperer="",line_recuperer2="";
		int i=0;
		boolean touver_entreprise=false;
		@SuppressWarnings("rawtypes")
		Iterator it;
		it=Ta.iterator();
		while(it.hasNext()){
			String so=(String) it.next();
		    int index;
		    @SuppressWarnings("unused")
			int end=so.length();
		    index=so.indexOf("~");
		   
		    String alias;
		    alias=so.substring(0,index);
		    
		    if(alias.equalsIgnoreCase(symbol)){
		    	   Ta.removeElementAt(i); 
		    	   int index_separer,index_separer2;
		    	   index_separer=so.indexOf("~", index+1);
		    	   index_separer=so.indexOf("~", index_separer+1);
		    	   index_separer=so.indexOf("~", index_separer+1);
		    	   index_separer=so.indexOf("~", index_separer+1);
		    	   index_separer=so.indexOf("~", index_separer+1);
		    	   index_separer2=so.indexOf("~",index_separer+1);
		    	   index_separer2=so.indexOf("~",index_separer2+1);
		    	   
		    	   line_recuperer2=so.substring(index_separer2, so.length());
		    	   line_recuperer=so.substring(0,index_separer);
		    	   touver_entreprise=true;
		    	   break;
		    	} 
		    else{i++;}
		}
		
		if(!touver_entreprise){new Text_input_erreur("Cette entreprise n'existe pas dans votre portefeuille.");}
		else{
			  String n=line_recuperer+"~"+regle+"~"+autoQuantite+line_recuperer2;
			  Collections.addAll(Ta, n);
			  
			  File f2= new File(this.Path);
			   FileUtils.write(f2, "");
			   
			   @SuppressWarnings("rawtypes")
			Iterator it2;
				it2=Ta.iterator();
				while(it2.hasNext()){
					String so=(String) it2.next();
				    
					if(f2.exists()){
						try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f2.getPath(), true)))) {
			        	    out.println(so);
			        	    out.close();   
			        	}catch (IOException e) {
			        	    //exception handling left as an exercise for the reader
			        	}
				     }
			    }
		}
    	
    }
    
    public void changer_etat_alterte2(String symbol,String l) throws IOException{
    	

    	FileInputStream f1=null;
		try{
		f1=new FileInputStream(this.Path);
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.out.println("Ce fichier n'existe pas.");
		}
		//read the file in Ta 
		BufferedReader dr=new BufferedReader(new InputStreamReader(f1));
		Vector<String> Ta=new Vector<>();
		String s;
		try {
			while ((s=dr.readLine())!=null){
				Collections.addAll(Ta,s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		f1.close();
		
		String line_recuperer="";
		int i=0;
		boolean touver_entreprise=false;
		@SuppressWarnings("rawtypes")
		Iterator it;
		it=Ta.iterator();
		while(it.hasNext()){
			String so=(String) it.next();
		    int index;
		    @SuppressWarnings("unused")
			int end=so.length();
		    index=so.indexOf("~");
		   
		    String alias;
		    alias=so.substring(0,index);
		    
		    if(alias.equalsIgnoreCase(symbol)){
		    	   Ta.removeElementAt(i); 
		    	   int index_separer;
		    	   index_separer=so.indexOf("~", index+1);
		    	   index_separer=so.indexOf("~", index_separer+1);
		    	   index_separer=so.indexOf("~", index_separer+1);
		    	   index_separer=so.indexOf("~", index_separer+1);
		    	   index_separer=so.indexOf("~", index_separer+1);
		    	   index_separer=so.indexOf("~",index_separer+1);
		    	   index_separer=so.indexOf("~",index_separer+1);
		    	   
		    	   line_recuperer=so.substring(0,index_separer);
		    	   touver_entreprise=true;
		    	   break;
		    	} 
		    else{i++;}
		}
		
		if(!touver_entreprise){new Text_input_erreur("Cette entreprise n'existe pas dans votre portefeuille.");}
		else{
			  String n=line_recuperer+"~"+l;
			  Collections.addAll(Ta, n);
			  
			  File f2= new File(this.Path);
			   FileUtils.write(f2, "");
			   
			   @SuppressWarnings("rawtypes")
			Iterator it2;
				it2=Ta.iterator();
				while(it2.hasNext()){
					String so=(String) it2.next();
				    
					if(f2.exists()){
						try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f2.getPath(), true)))) {
			        	    out.println(so);
			        	    out.close();   
			        	}catch (IOException e) {
			        	    //exception handling left as an exercise for the reader
			        	}
				     }
			    }
		}
    }
    
    
	public void supprimerEntreprise(String symbol,String path_entreprise) throws IOException{
		   path_entreprise=path_entreprise+"/"+symbol+".txt";
		   Files.delete(Paths.get(path_entreprise));
		  /*
		   File f= new File(path_entreprise); 
		   if(!f.exists()){new Text_input_erreur("NOT EXISTS");}
		   else{f.delete();}
		  */ 

		   FileInputStream f1=null;
			try{
			f1=new FileInputStream(this.Path);
			}catch(FileNotFoundException e){
				e.printStackTrace();
				System.out.println("Ce fichier n'existe pas.");
			}
			//read the file in Ta 
			BufferedReader dr=new BufferedReader(new InputStreamReader(f1));
			Vector<String> Ta=new Vector<>();
			String s;
			try {
				while ((s=dr.readLine())!=null){
					Collections.addAll(Ta,s);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			f1.close();
			int i=0;
			boolean touver_entreprise=false;
			@SuppressWarnings("rawtypes")
			Iterator it;
			it=Ta.iterator();
			while(it.hasNext()){
				String so=(String) it.next();
			    int index;
			    @SuppressWarnings("unused")
				int end=so.length();
			    index=so.indexOf("~");
			   
			    String alias;
			    alias=so.substring(0,index);
			    
			    if(alias.equalsIgnoreCase(symbol)){Ta.removeElementAt(i);touver_entreprise=true;break;} 
			    else{i++;}
			}
			
			if(!touver_entreprise){new Text_input_erreur("Cette entreprise n'existe pas dans votre portefeuille.");}
			else{
				   File f2= new File(this.Path);
				   FileUtils.write(f2, "");
				   
				   @SuppressWarnings("rawtypes")
				Iterator it2;
					it2=Ta.iterator();
					while(it2.hasNext()){
						String so=(String) it2.next();
					    
						if(f2.exists()){
							try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f2.getPath(), true)))) {
				        	    out.println(so);
				        	    out.close();   
				        	}catch (IOException e) {
				        	    //exception handling left as an exercise for the reader
				        	}
					     }
				    }
					new Text_input_erreur("Suppression réussie !");	
			}
	}
	
	public void chargerList() throws IOException{      
		
		this.ListEntreprise.removeAll(this.ListEntreprise);
		FileInputStream f=null;
		try{
		f=new FileInputStream(this.Path);
		}catch(FileNotFoundException e){
			e.printStackTrace();
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
		//get subString from a String seperate as "~"
		
		f.close();
		@SuppressWarnings("rawtypes")
		Iterator it;
		it=Ta.iterator();
		while(it.hasNext()){
			
			String so=(String) it.next();
			
			String sub[]=new String[9];
			for(int i=0;i<8;i++){
				int index;
				index=so.indexOf("~");
				sub[i]=so.substring(0, index);
				so=so.substring(index+1);
			}
			sub[8]=so;
			
			String symbol=sub[0];
			String alerte=sub[1];
			float min_alerte=Float.parseFloat(sub[2]);
			float max_alerte=Float.parseFloat(sub[3]);
			float min_acheter_vendre=Float.parseFloat(sub[4]);
			float max_acheter_vendre=Float.parseFloat(sub[5]);
			int regle=Integer.parseInt(sub[6]);
			int autoExchangeQuantite=Integer.parseInt(sub[7]);
			String alerte2=sub[8];
			
			
			String path="fiche_entreprise/";
			path=path+this.NomEmploye+"/"+symbol+".txt";
			Entreprise e=new Entreprise(symbol,path);
			e.setAlerte(alerte);
			e.setMax_acheter_vendre(max_acheter_vendre);
			e.setMax_alerte(max_alerte);
			e.setMin_acheter_vendre(min_acheter_vendre);
			e.setMin_alerte(min_alerte);
			e.setRegle(regle);
			e.setAutoExchangeQuantite(autoExchangeQuantite);
			e.setAlerte2(alerte2);
			this.ajouterEntreprise(e);  
		}	
	}
	
	public void calculerValeurReel(){
		float vs=0;
		@SuppressWarnings("rawtypes")
		Iterator it;
		it=this.ListEntreprise.iterator();
		while(it.hasNext()){
			Entreprise e=new Entreprise((Entreprise) it.next());
			vs= e.getSumValeurInitial()+vs;
		}
		
		this.ValeurReel=(float) (this.ValeurTotal-vs);
	}
	
	public void calculerValeurTotalVirtuel(){
		float vs=0;
		@SuppressWarnings("rawtypes")
		Iterator it;
		it=this.ListEntreprise.iterator();
		while(it.hasNext()){
			Entreprise e=new Entreprise((Entreprise) it.next());
			vs= e.getSumValeurActuel()+vs;
		}
		
		this.ValeurTotalActuel=(float) vs;
	}
	
	public void afficherNonTrier(){

		@SuppressWarnings("rawtypes")
		Iterator it;
		it=ListEntreprise.iterator();
		System.out.println("Nom                  Alias        Nb_Quantite   Valeur_Initial   Valeur_Actuel");
		System.out.println("==============================================================================");
		while(it.hasNext()){
			Entreprise e=new Entreprise((Entreprise) it.next());
			System.out.println(e.getS().getName()+"         "+e.getS().getSymbol()+"          "+e.getSumQuantite()+"             "+e.getSumValeurInitial()+"         "+e.getSumValeurActuel());
		}
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("ETAT                                                            "+this.ValeurTotalActuel);
		System.out.println("==============================================================================");
	}
    public int acheter(String symbol,int quantite,String path) throws IOException{    //problem pour initial min_alerte min_acheter_vendre...
	    
    	Stock s=YahooFinance.get(symbol);
    	float v=(float) s.getQuote().getPrice().doubleValue();
    	
    	if((quantite*v)>200000){new Text_input_erreur("Chaque investissement doit être inferieur à 200000");return 0;}	//limeter les investissements
	    else{
    	   
		   if(s.getName().equalsIgnoreCase("N/A")){
			   new Text_input_erreur("Votre saisie est incorrecte, veuillez vérifier votre 'Alias'");
			   return 0;
		   }
		   else{
			   
			   String date= new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		   	   float vt=v*quantite;
			
			   if(this.verifierCapacite(vt)){
				   String tofile=date+" "+v+" "+quantite;
				   path=path+"/"+symbol+".txt";
				   File f= new File(path); 
				   if(f.exists()){
					   try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f.getPath(), true)))) {
		        	       out.println(tofile);
		        	       out.close();
		        	       this.ValeurReel=(float) (this.ValeurReel-vt);
		        	       this.ValeurTotalActuel=(float) (this.ValeurTotalActuel + vt);
		        	   }catch (IOException e) {
		        	       //exception handling left as an exercise for the reader
		        	   }
					   return 1;
				   }
				   else{
					   f.createNewFile();
					
					   try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f.getPath(), true)))) {
		        	       out.println(tofile);
		        	       out.close();
		        	       this.ValeurReel=(float) (this.ValeurReel-vt);
		        	   }catch (IOException e) {
		        	       //exception handling left as an exercise for the reader
		           	   }	
					
					   String tof=symbol+"~Normal~0~10000~0~10000~0~0~Normal";
					   File fl=new File(this.Path);
					   try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fl.getPath(), true)))) {
		        	       out.println(tof);
		        	       out.close();  
		        	   }catch (IOException e) {
		        	       //exception handling left as an exercise for the reader
		        	   }
				
				   }
			       this.chargerList();
				   return 1;	
			   }
			
			   else{new Text_input_erreur("Vous n'avez pas assez d'argent!");return 0;}			
		   }
	    }
	}

    public void ajouterEntreprise(String symbol,String path){       //problem pour initial min_alerte min_acheter_vendre...
    	   path=path+"/"+symbol+".txt";
		   File f= new File(path); 
		   if(f.exists()){ new Text_input_erreur("Cette entreprise existe déjà dans votre portefeuille.");}
		   else{
			   try {
			    f.createNewFile();
		       } catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
		       }
		    }
		   
		   String tofield=symbol+"~Normal~0~10000~0~10000~0~0~Normal";
		   File fl=new File(this.Path);
		   try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fl.getPath(), true)))) {
       	     out.println(tofield);
       	     out.close();  
       	    }catch (IOException e) {
       	    //exception handling left as an exercise for the reader
         	}
		   
		   new Text_input_erreur("Ajout d'entreprise réussi.");
    }

    public Entreprise chercherEntreprise(String symbol){
    	   
    	    @SuppressWarnings("rawtypes")
			Iterator it=this.ListEntreprise.iterator();
    	    while(it.hasNext()){
    	    	
    	    	Entreprise en=new Entreprise((Entreprise) it.next());
    	    	if(symbol.equalsIgnoreCase((en.getS()).getSymbol())){
    	    		return en;
    	    	}
    	    }
    	    return null;
    }
   
    
	public boolean verifierCapacite(float m){
	        if(this.ValeurReel>=m){return true;}
	        else{return false;}
	}
	
	public boolean verifierQuatite(String symbol,int q) throws IOException{
		   String path="fiche_entreprise/";
		   path=path+this.NomEmploye+"/"+symbol+".txt";
		   Entreprise e=new Entreprise(symbol,path);
		   if(e.getSumQuantite()>=q){return true;}
		   else{return false;}
	}
	
	//test avec main
   
	public static void main(String[] args) throws IOException{
		
		PorteFeuille alice=new PorteFeuille("FAN","fiche_porte_feuille/FAN.txt");
		alice.donnerCout_Acheter_Vendre_PourEntreprise("AIR.PA", 5.67, 9.86);
	}
}
