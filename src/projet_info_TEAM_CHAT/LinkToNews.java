
package projet_info_TEAM_CHAT;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;

public class LinkToNews {

	private String Url;

	public LinkToNews(String url) throws URISyntaxException {
		super();
		Url = url;
		 try {
				Desktop.getDesktop().browse(new java.net.URI(Url));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
}
