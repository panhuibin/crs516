import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
//		ApplicationContext context 
//		= new ClassPathXmlApplicationContext("file:resources/client.xml");
		
//		ApplicationContext context 
//		= new ClassPathXmlApplicationContext("resources\clientConfig.xml");

//	Client client = (Client)context.getBean("client");
	
		Client client = new Client();
		client.prepareAndSubmitCommand();
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

