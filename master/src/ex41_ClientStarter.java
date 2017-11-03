import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.ltree.crs516.tasks.Client;


/**
 * Creates client that in turn submits task to task engine.
 * 
 * @author crs516 development team
 * 
 */
public final class ClientStarter {

	public static void main(String[] args) {
		configureLogging();

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
//TODO 1: Create an instance of Client and invoke the 
//prepareAndSubmitCommand() method you just wrote.
		
@		Client client = new Client();
@		client.prepareAndSubmitCommand();
$
$
%TODO 1:<br/>&#160;&#160;Client client = new Client();<br/>&#160;&#160;client.prepareAndSubmitCommand();<br/><br/>

	}

	/**
	 * Configures the logging.
	 */
	private static void configureLogging(){
		 try {
			InputStream inStream = ClientStarter.class.getClassLoader().getResourceAsStream("log4j.properties");
			 Properties props = new Properties();
			props.load(inStream);
			 PropertyConfigurator.configure(props);
		} catch (IOException e) {
			e.printStackTrace();
		}      
	}

}

