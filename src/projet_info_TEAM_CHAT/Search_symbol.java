
package projet_info_TEAM_CHAT;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Search_symbol implements ActionListener,MouseListener{

	private String nom;
	private JTable table=new JTable();
	JFrame jf=new JFrame();
	private JTextField jt=new JTextField(20);
	private StandardButton jb=new StandardButton("Recherche");
	private Fenetre_porteFeuille fenp;
	
	Search_symbol(String n,Fenetre_porteFeuille f) throws IOException{
		this.nom=n;
		this.fenp=f;
	}
	
	
	public void creatPanel(JPanel jp) throws IOException{
		jp.removeAll();
		
		ColoredJPanel jp_search=new ColoredJPanel(new Color(80,80,80));
		jp_search.add(this.jt,BorderLayout.PAGE_START);
		jp_search.add(this.jb,BorderLayout.PAGE_END);
		jb.addActionListener(this);
		jp.add(jp_search);
		
		String[] columnNames={"Alias","Nom de l'entreprise"};
		Object[][] data=this.getData();
		
		if(data.length==0){
			JLabel jl=new JLabel("Il n'y a pas des entreprises qui correspondent à votre recherche.");
			jp.add(jl);
		}
		else{	
		  this.table=new JTable(data,columnNames);
		  this.table.addMouseListener(this);
		  table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	      JScrollPane scrollPane = new JScrollPane(table);
	      table.setFillsViewportHeight(true);
		  jp.add(scrollPane);
		}
		
	    jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
	}
	
	
	
	
	public HashMap<String,String> surInternet() throws IOException {
		// Make a URL to the web page
		String s="http://finance.yahoo.com/lookup/stocks;_ylt=ArdVdUftEzhHrsMsGAHjaMHxVax_;_ylu=X3oDMTFiM3RzMzF1BHBvcwMyBHNlYwN5ZmlTeW1ib2xMb29rdXBSZXN1bHRzBHNsawNzdG9ja3M-?s=";
		s=s+this.nom+"&t=S&m=ALL&r=";
        URL url = new URL(s);
        // Get the input stream through URL Connection
        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();

        // Once you have the Input Stream, it's just plain old Java IO stuff.

        // For this case, since you are interested in getting plain-text web page
        // I'll use a reader and output the text content to System.out.

        // For binary content, it's better to directly read the bytes from stream and write
        // to the target file.


        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        String info=null;
        String line = null;

        // read each line and write to System.out
        while ((line = br.readLine()) != null) {
        	 info=info+line+"\n";    
        }
        
 
        String sub_result=null;
        int index_result=-1;
        index_result=info.indexOf("<div id=\"yfi_sym_results\"");
        if(index_result==-1){new Text_input_erreur("Cette entreprise n'existe pas dans votre portefeuille.");return new HashMap<String,String>();}
        else{
           sub_result=info.substring(index_result);
       
           String sub_body=null;
           int index_body_start=-1,index_body_end=-1;
           index_body_start=sub_result.indexOf("<tbody>");
           index_body_end=sub_result.indexOf("</tbody>",index_body_start);
           if((index_body_start>=index_body_end)||(index_body_start<0)||(index_body_end<0)){new Text_input_erreur("Pas de l'entreprise correct coresprendre");return new HashMap<String,String>();}
           else{
              sub_body=sub_result.substring(index_body_start,index_body_end);
        
              String sub_tr=null;
              int index_tr;
              index_tr=sub_body.indexOf("<tr");
              sub_tr=sub_body.substring(index_tr);
        

              HashMap<String,String> map=new HashMap<>();
        
              while(sub_tr.length()>15){
                  String key;
                  String value;
                  int index1;
                  index1=sub_tr.indexOf("<a");
                  sub_tr=sub_tr.substring(index1);
               
                  int index_key_start,index_key_end;
                  index_key_start=sub_tr.indexOf(">");
                  index_key_end=sub_tr.indexOf("<", index_key_start);
                  key=sub_tr.substring(index_key_start+1, index_key_end);
            
           
                  int index2;
                  index2=sub_tr.indexOf("<td");
                  sub_tr=sub_tr.substring(index2);
            
                  int index_value_start,index_value_end;
                  index_value_start=sub_tr.indexOf(">");
                  index_value_end=sub_tr.indexOf("<", index_value_start);
                  value=sub_tr.substring(index_value_start+1, index_value_end);
            
                  map.put(key, value);
            
                  int index3;
                  index3=sub_tr.indexOf("</tr");
                  sub_tr=sub_tr.substring(index3);
              
                }
              return map;
           }
        }
        
        
	}
	
	public Object[][] getData() throws IOException{
		HashMap<String,String> map=this.surInternet();
		Vector<String> list_key=new Vector<>();
		
		for ( String key : map.keySet() ) {
		    Collections.addAll(list_key, key);
		}
		
		int len=list_key.size();
		Object[][] obj=new Object[len][2];
		
		for(int i=0;i<len;i++){
			obj[i][0]=list_key.elementAt(i);
			obj[i][1]=map.get(list_key.elementAt(i));
		}
		
		return obj;
	}
	
	//test avec main
	/*
	public static void main(String[] args) throws IOException{
		new Search_symbol("samsung");
	}
    */

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.jb){
			try {
				this.fenp.updateNewsToSearchPage(this.jt.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row=table.rowAtPoint(e.getPoint());
		try {
			Object[][] ob=this.getData();
			String symbol=(String)ob[row][0];
			this.fenp.updateSearchPageToNews(symbol);
			this.fenp.setJt_symbol(symbol);
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
}

