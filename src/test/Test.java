
package test;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

public class Test {

	public static void main(String[] args){
		FileInputStream f=null;
		try{
		f=new FileInputStream("G:/projet info--fise2/projet info/TableauAction.txt");
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
		//get subString from a String seperate as " "
		Iterator it;
		it=Ta.iterator();
		while(it.hasNext()){
			String so=(String) it.next();
		    int index1,index2;
		    int end=so.length();
		    index1=so.indexOf(" ");
		    index2=so.indexOf(" ", index1+1);
		    String sub1,sub2,sub3;
		    sub1=so.substring(0,index1);
		    sub2=so.substring(index1+1, index2);
		    sub3=so.substring(index2, end);
		}
		
		
		
		//afficher 
		/*Iterator it;
		it=Ta.iterator();
		System.out.println("NomEntreprise     Etat     Porcentage\n");
		while(it.hasNext()){
			System.out.println(it.next());
		}*/
		
	}
}
