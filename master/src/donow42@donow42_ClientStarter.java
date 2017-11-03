import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.ltree.crs516.taskengine.TaskEngine;
import com.ltree.crs516.tasks.Client;
import com.ltree.crs516.tasks.TaskEngineLocator;
import com.ltree.crs516.tasks.Client.EngineNotAvailableException;
import com.ltree.crs516.tasks.Client.TaskEngineFactory;


/**
 * Creates client that in turn submits task to task engine.
 * 
 * @author crs516 development team
 * 
 */
public final class ClientStarter {

	public static void main(String[] args) throws EngineNotAvailableException, 
	RemoteException, NotBoundException {
		configureLogging();
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		Client client = new Client();
		//Give the client the TaskEngineFactory
		TaskEngineLocator locator = TaskEngineLocator.INSTANCE;
		TaskEngine engine = locator.getTaskEngine();
		TaskEngineFactory factory = new TaskEngineFactory();
		factory.setEngine(engine);
		client.setTaskEngineFactory(factory);
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

