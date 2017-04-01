
package projet_info_TEAM_CHAT;
import java.text.NumberFormat;

public class Action {

	private double PrixActuel;
	private double PrixInitial;
	private int Quantite;
	private String Date;
	
	
	//constructeur
	public Action(Action a){
		this.Date=a.Date;
		this.PrixActuel=a.PrixActuel;
		this.PrixInitial=a.PrixInitial;
		this.Quantite=a.Quantite;
	}
	public Action() {
		super();
	}
	public Action(double prixInitial, int quantite, String date) {
		super();
		PrixInitial = prixInitial;
		Quantite = quantite;
		Date = date;
	}
	public Action(double prixActuel, double prixInitial, int quantite, String date) {
		super();
		this.PrixActuel = prixActuel;
		this.PrixInitial = prixInitial;
		this.Quantite = quantite;
		this.Date = date;
	}
    //get set
	public double getPrixActuel() {
		return PrixActuel;
	}
	public void setPrixActuel(double prixActuel) {
		PrixActuel = prixActuel;
	}
	public double getPrixInitial() {
		return PrixInitial;
	}
	public void setPrixInitial(double prixInitial) {
		PrixInitial = prixInitial;
	}
	public int getQuantite() {
		return Quantite;
	}
	public void setQuantite(int quantite) {
		Quantite = quantite;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
    //
    public void afficher(){
    	double va,vi,vl;
		va=this.PrixActuel*this.Quantite;
		vi=this.PrixInitial*this.Quantite;
		vl=va-vi;
		NumberFormat fo=NumberFormat.getNumberInstance();
		fo.setMaximumFractionDigits(2);
		String sva=fo.format(va);
		String svi=fo.format(vi);
		String svl=fo.format(vl);
    	System.out.println(this.Date+"   "+this.Quantite+"                "+this.PrixActuel+"         "+sva+"          "+this.PrixInitial+"           "+svi+"           "+svl);
    }
	
	//test
    /*
	public static void main(String[] args){
	     Action ac=new Action(500,100,4,"2015-10-29");
	     ac.afficher();
	}*/
}

