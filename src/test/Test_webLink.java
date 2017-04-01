
package test;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;

public class Test_webLink {

      public static void main(String[] args) throws URISyntaxException
      {
    	  try {
			Desktop.getDesktop().browse(new java.net.URI("www.google.com"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
}
