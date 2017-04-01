package projet_info_TEAM_CHAT;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class Entreprise {

	
	private String Path;
	private String Alerte="";
	private String Alerte2="";
	private Stock S;
	private float min_alerte;
	private float max_alerte;
	private float min_acheter_vendre;
	private float max_acheter_vendre;
	private float PrixUnitair;
	private int SumQuantite;
	private float SumValeurInitial;
	private float SumValeurActuel;

	private int regle;
	private int autoExchangeQuantite;
	private Collection<Action> ListAction=new Vector<>();
	private PorteFeuille pf;

	public Entreprise() {
		super();
	}
	public Entreprise(Entreprise a) {
		this.S=a.getS();
		this.Path=a.getPath();
		this.PrixUnitair=a.getPrixUnitair();
		this.SumValeurActuel=a.getSumValeurActuel();
		this.SumValeurInitial=a.getSumValeurInitial();
		this.SumQuantite=a.getSumQuantite();
		this.ListAction=a.getListAction();
		this.Alerte=a.getAlerte();
		this.max_acheter_vendre=a.getMax_acheter_vendre();
		this.max_alerte=a.getMax_alerte();
		this.min_acheter_vendre=a.getMin_acheter_vendre();
		this.min_alerte=a.getMin_alerte();
		this.regle=a.getRegle();
		this.autoExchangeQuantite=a.getAutoExchangeQuantite();
		this.Alerte2=a.getAlerte2();
	}	
	
	public Entreprise(Entreprise a,PorteFeuille p) {
		this.S=a.getS();
		this.Path=a.getPath();
		this.PrixUnitair=a.getPrixUnitair();
		this.SumValeurActuel=a.getSumValeurActuel();
		this.SumValeurInitial=a.getSumValeurInitial();
		this.SumQuantite=a.getSumQuantite();
		this.ListAction=a.getListAction();
		this.Alerte=a.getAlerte();
		this.max_acheter_vendre=a.getMax_acheter_vendre();
		this.max_alerte=a.getMax_alerte();
		this.min_acheter_vendre=a.getMin_acheter_vendre();
		this.min_alerte=a.getMin_alerte();
		this.regle=a.getRegle();
		this.autoExchangeQuantite=a.getAutoExchangeQuantite();
		this.Alerte2=a.getAlerte2();
		this.pf=p;
	}
	
	public Entreprise(String symble,String path) throws IOException {
		super();
		this.Path=path;
	    S=YahooFinance.get(symble);
        this.PrixUnitair=(float) this.S.getQuote().getPrice().doubleValue();

	    this.chargerList();
	    this.calculerSumQuantite();
        this.calculerSumValeurInitial();
		this.calculerSumValeurActuel();

	}
	
	public void ajourterAction(Action a){
		Collections.addAll(this.ListAction,a);
	}
	
	public void chargerList() throws IOException{
		this.ListAction.removeAll(this.ListAction);
		FileInputStream f=null;
		try{
		f=new FileInputStream(this.Path);
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.out.println("Ce fichier n'existe pas!");
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
		
		f.close();
		@SuppressWarnings("rawtypes")
		Iterator it;
		it=Ta.iterator();
		while(it.hasNext()){
			String so=(String) it.next();
		    int index0,index1;
		    int end=so.length();
		    index0=so.indexOf(" ");
		    index1=so.indexOf(" ", index0+1);
		   
		    String sub0,sub1,sub2;
		    sub0=so.substring(0,index0);
		    sub1=so.substring(index0+1, index1);
		    sub2=so.substring(index1+1,end);
		    
		    String date=sub0;

		    float pua=this.PrixUnitair;
		    float pui=Float.parseFloat(sub1);

		    int quantite=Integer.parseInt(sub2);
		    Action a=new Action(pua,pui,quantite,date);
		    this.ajourterAction(a);  
		}
	}
	
	public void calculerSumQuantite(){
		@SuppressWarnings("rawtypes")
		Iterator it;
		it=this.ListAction.iterator();
		while(it.hasNext()){
			Action a=new Action((Action) it.next());
				
			this.SumQuantite= this.SumQuantite+a.getQuantite();
			
		}
	}
	
	public void calculerSumValeurInitial(){
		@SuppressWarnings("rawtypes")
		Iterator it;
		it=this.ListAction.iterator();
		while(it.hasNext()){
			Action a=new Action((Action) it.next());

			this.SumValeurInitial= (float) (this.SumValeurInitial+a.getPrixInitial()*a.getQuantite());

			
		}
	}
	
	public void calculerSumValeurActuel(){
		this.SumValeurActuel=this.PrixUnitair*this.SumQuantite;
	}
	
	public void afficher(){
	
		System.out.println("                                           "+this.S.getName());
		System.out.println("                                                "+this.S.getSymbol());
		System.out.println("----------------------------------------------------------------------------------------------------------");

		System.out.println("Date         Quantité   Prix Actuel   Valeur Actuelle   Prix Initial   Valeur Initialle   Valeurlatente");

		@SuppressWarnings("rawtypes")
		Iterator it;
		it=this.ListAction.iterator();
		while(it.hasNext()){
			Action a=new Action((Action) it.next());
			a.afficher();
		}
		System.out.println("----------------------------------------------------------------------------------------------------------");
	}
	//
	
	
	
	public String getPath() {
		return Path;
	}
	public String getAlerte2() {
		return Alerte2;
	}
	public void setAlerte2(String alerte2) {
		Alerte2 = alerte2;
	}
	public int getRegle() {
		return regle;
	}
	public void setRegle(int regle) {
		this.regle = regle;
	}
	public int getAutoExchangeQuantite() {
		return autoExchangeQuantite;
	}
	public void setAutoExchangeQuantite(int autoExchangeQuantite) {
		this.autoExchangeQuantite = autoExchangeQuantite;
	}
	public void setPath(String path) {
		Path = path;
	}
	public Stock getS() {
		return S;
	}
	public void setS(Stock s) {
		S = s;
	}

	public float getPrixUnitair() {
		return PrixUnitair;
	}
	public void setPrixUnitair(float prixUnitair) {

		PrixUnitair = prixUnitair;
	}
	public int getSumQuantite() {
		return SumQuantite;
	}

	public void setSumQuantite(float sumQuantite) {
		SumQuantite =  (int) sumQuantite;
	}
	public float getSumValeurInitial() {
		return SumValeurInitial;
	}
	public void setSumValeurInitial(float sumValeurInitial) {
		SumValeurInitial = sumValeurInitial;
	}
	public float getSumValeurActuel() {
		return SumValeurActuel;
	}
	public void setSumValeurActuel(float sumValeurActuel) {

		SumValeurActuel = sumValeurActuel;
	}
	public Collection<Action> getListAction() {
		return this.ListAction;
	}
	public void setListAction(Collection<Action> listAction) {
		ListAction = listAction;
	}
	public String getAlerte() {
		return Alerte;
	}
	public void setAlerte(String alerte) {
		this.Alerte = alerte;
	}

	public float getMin_alerte() {
		return min_alerte;
	}
	public void setMin_alerte(float min_alerte) {
		this.min_alerte = min_alerte;
	}
	public float getMax_alerte() {
		return max_alerte;
	}
	public void setMax_alerte(float max_alerte) {
		this.max_alerte = max_alerte;
	}
	public float getMin_acheter_vendre() {
		return min_acheter_vendre;
	}
	public void setMin_acheter_vendre(float min_acheter_vendre) {
		this.min_acheter_vendre = min_acheter_vendre;
	}
	public float getMax_acheter_vendre() {
		return max_acheter_vendre;
	}
	public void setMax_acheter_vendre(float max_acheter_vendre) {

		this.max_acheter_vendre = max_acheter_vendre;
	}
	//--------------------------------------------------
	
    public void autoSetAlerte() throws IOException{

    	if(this.PrixUnitair>this.max_alerte){this.pf.changer_etat_alterte2(this.S.getSymbol(), "P.U HAUT");this.setAlerte("P.U HAUT!");}
    	else if(this.PrixUnitair<this.min_alerte){this.pf.changer_etat_alterte2(this.S.getSymbol(), "P.U BAS");this.setAlerte("P.U BAS!");}

    }
    
	//problem verifier capacite!!!!!!!comme fenetre_porteFeuille action Vendre
	public void regleAcheterVendre() throws IOException //A:acheter,V:vendre || k=1 A<min,A>max; k=2 A<min,V>max; k=3 V<min,V>max; k=4 V<min,A>max
	{
		String path="fiche_entreprise/"+this.pf.getNomEmploye();
		if(this.regle==1){
			if(this.PrixUnitair<this.min_acheter_vendre || this.PrixUnitair>this.max_acheter_vendre){this.pf.acheter(this.S.getSymbol(), this.autoExchangeQuantite, path);}
		}
		
		if(this.regle==2){
			if(this.PrixUnitair<this.min_acheter_vendre){this.pf.acheter(this.S.getSymbol(), this.autoExchangeQuantite, path);}
			else if(this.PrixUnitair>this.max_acheter_vendre){this.pf.acheter(this.S.getSymbol(), -this.autoExchangeQuantite, path);}
		}
		
		if(this.regle==3){
			if(this.PrixUnitair<this.min_acheter_vendre || this.PrixUnitair>this.max_acheter_vendre){this.pf.acheter(this.S.getSymbol(),-this.autoExchangeQuantite, path);}
		}
		
		if(this.regle==4){
			if(this.PrixUnitair<this.min_acheter_vendre){this.pf.acheter(this.S.getSymbol(), -this.autoExchangeQuantite, path);}
			else if(this.PrixUnitair>this.max_acheter_vendre){this.pf.acheter(this.S.getSymbol(), this.autoExchangeQuantite, path);}
		}
		
	}
	


	// TEST

	//---------------------------------------------------
	//test avec main 

	public static void main(String[] args) throws IOException{
		Entreprise e=new Entreprise("GOOG","fiche_entreprise/Alice/GOOG.txt");
		e.afficher();
		Entreprise e1=new Entreprise("INTC","fiche_entreprise/Alice/INTC.txt");
		e1.afficher();
	}
	
	
}