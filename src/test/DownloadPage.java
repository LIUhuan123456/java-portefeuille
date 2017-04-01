
package test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;


public class DownloadPage {

    public static void main(String[] args) throws IOException {

        // Make a URL to the web page
        URL url = new URL("http://finance.yahoo.com/lookup/stocks;_ylt=ArdVdUftEzhHrsMsGAHjaMHxVax_;_ylu=X3oDMTFiM3RzMzF1BHBvcwMyBHNlYwN5ZmlTeW1ib2xMb29rdXBSZXN1bHRzBHNsawNzdG9ja3M-?s=fan&t=S&m=ALL&r=");

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
        int index_result;
        index_result=info.indexOf("<div id=\"yfi_sym_results\"");
        sub_result=info.substring(index_result);
        
        
        

        String sub_body=null;
        int index_body_start,index_body_end;
        index_body_start=sub_result.indexOf("<tbody>");
        index_body_end=sub_result.indexOf("</tbody>",index_body_start);
        sub_body=sub_result.substring(index_body_start,index_body_end);
        
        
        String sub_tr=null;
        int index_tr;
        index_tr=sub_body.indexOf("<tr");
        sub_tr=sub_body.substring(index_tr);
        
      
        HashMap<String,String> map=new HashMap<>();
        
        while(sub_tr.length()>10){
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
            index3=sub_tr.indexOf("</tr>");     
            sub_tr=sub_tr.substring(index3);
            System.out.println(sub_tr.length());
          }
        
        System.out.println(map);
    }
}