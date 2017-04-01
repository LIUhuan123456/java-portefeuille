
package projet_info_TEAM_CHAT;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Inscription implements ActionListener{
     
	private JFrame jf=new JFrame("Inscription");
	private StandardButton jb_valide=new StandardButton("S'inscrire");
	private StandardButton jb_annuler=new StandardButton("Annuler");
	private JTextField jt_id=new JTextField(10);
	private JPasswordField jt_mp=new JPasswordField(10);
	private String Path="fiche_identifiant/id.txt";
	private String Path_pf="fiche_porte_feuille/";
	private String Path_fiche_entreprise="fiche_entreprise/";
	private String Path_boss="fiche_boss/list_employes.txt";
	
	public Inscription(){
		JLabel jl_id=new JLabel("Identifiant      ");
		jl_id.setForeground(Color.WHITE);
		JLabel jl_mp=new JLabel("Mot de passe");
		jl_mp.setForeground(Color.WHITE);
		ColoredJPanel jp_id=new ColoredJPanel(new Color(80,80,80));
		ColoredJPanel jp_mp=new ColoredJPanel(new Color(80,80,80));
		ColoredJPanel jp_button=new ColoredJPanel(new Color(80,80,80));
		ColoredJPanel jp_all=new ColoredJPanel(new Color(80,80,80));
		jp_id.add(jl_id,BorderLayout.PAGE_START);
		jp_id.add(this.jt_id,BorderLayout.PAGE_END);
		jp_mp.add(jl_mp,BorderLayout.PAGE_START);
		jp_mp.add(this.jt_mp,BorderLayout.PAGE_END);
		jp_button.add(this.jb_valide,BorderLayout.PAGE_START);
		jp_button.add(this.jb_annuler,BorderLayout.PAGE_END);
		this.jb_valide.addActionListener(this);
		this.jb_annuler.addActionListener(this);
		jp_all.add(jp_id);
		jp_all.add(jp_mp);
		jp_all.add(jp_button);
		jp_all.setLayout(new BoxLayout(jp_all,BoxLayout.Y_AXIS));
		
		jf.add(jp_all);
		jf.setSize(300, 300);
		jf.setVisible(true);
	    jf.setLocationRelativeTo(null);
	     
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.jb_annuler){this.jf.dispose();}
		else{
			 String id=this.jt_id.getText();
			 String mp=this.jt_mp.getText();
			 String tofile=id+" "+mp;
			 File f= new File(this.Path);

			 if(f.exists()){
					try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f.getPath(), true)))) {
		        	    out.println(tofile);
		        	    out.close();
		        	    
		        	}catch (IOException e1) {
		        	    //exception handling left as an exercise for the reader
		        	}
				}
				else{
					try {
						f.createNewFile();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f.getPath(), true)))) {
		        	    out.println(tofile);
		        	    out.close();
		        	}catch (IOException e1) {
		        	    //exception handling left as an exercise for the reader
		        	}	
				}
			 
			 String newpath=this.Path_fiche_entreprise+id;
			 File f1= new File(newpath);
			 f1.mkdir();  
			 
			 String path_pf=this.Path_pf+id+".txt";
			 File f3=new File(path_pf);
			 try {
				f3.createNewFile();
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			 
			 
			 
			 File f2= new File(this.Path_boss);
             
			 if(f2.exists()){
					try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f2.getPath(), true)))) {
		        	    out.println(id);
		        	    out.close();
		        	    
		        	}catch (IOException e1) {
		        	    //exception handling left as an exercise for the reader
		        	}
				}
				else{
					try {
						f2.createNewFile();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f2.getPath(), true)))) {
		        	    out.println(id);
		        	    out.close();
		        	}catch (IOException e1) {
		        	    //exception handling left as an exercise for the reader
		        	}	
				}
			 
			 new Text_input_erreur("Félicitations, Votre profil a été créé !");
			 this.jf.dispose();
		}
	}
	
	public static void main(String[] args){
		new Inscription();
	} 
	
}
