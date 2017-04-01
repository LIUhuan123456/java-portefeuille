
package test;

import org.apache.commons.lang.NumberUtils;

@SuppressWarnings("deprecation")
public class Test_string {
        public static void main(String[] args){
        	String s1="4";
        	String s3="adf";
        	System.out.println(NumberUtils.isDigits(s1));
        	System.out.println(NumberUtils.isDigits(s3));
        }
}
