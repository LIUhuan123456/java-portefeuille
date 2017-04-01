
package test;

import java.text.NumberFormat;

public class Test_output_form {

	
	public static void main(String[] args){
		NumberFormat fo=NumberFormat.getNumberInstance();
		fo.setMaximumFractionDigits(2);
		System.out.println(fo.format(3.1514926)+"--");
	}
}