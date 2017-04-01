
package test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Test_map_get_all_keys {

	public static void main(String[] args){
		Map<String,String> m=new HashMap<>();
		m.put("ty","my");
		m.put("kj","your");
		Collection<String> s=new Vector<>();
		for ( String key : m.keySet() ) {
		    Collections.addAll(s, key);
		}
		System.out.println(s);
	}
}