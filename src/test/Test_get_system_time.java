
package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test_get_system_time {

	public static void main(String[] args){
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		System.out.println(timeStamp );
	}
}