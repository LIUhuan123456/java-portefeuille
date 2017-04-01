
package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Test_write_a_filed {
     
	
	public static void main(String[] args) throws IOException{
          File f= new File("../file_test/test.txt"); 
    	  // f.createNewFile();
          //write into the file
          try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f.getPath(), true)))) {
        	    out.println("the text\n");
        	    //more code
        	    out.println("more text\n");
        	    //more code
        	    out.close();
        	}catch (IOException e) {
        	    //exception handling left as an exercise for the reader
        	}
          //
          System.out.println(f.exists());
      }
}
