
package projet_info_TEAM_CHAT;

public class Sub_string_not_found_exception extends Exception{

	private static final long serialVersionUID = 1L;
    
	public Sub_string_not_found_exception() { super(); }
	  public Sub_string_not_found_exception(String message) { super(message); }
	  public Sub_string_not_found_exception(String message, Throwable cause) { super(message, cause); }
	  public Sub_string_not_found_exception(Throwable cause){ super(cause); }
}