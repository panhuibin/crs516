import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.taskengine.TaskEngineImpl;

 @SuppressWarnings("serial")
public final class TaskEngineStarter extends JFrame {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final JButton startButton = new JButton("Start");
	private final JButton stopButton = new JButton("Stop");	
	private Registry registry;

	public TaskEngineStarter() {
		// Create GUI for control buttons.
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Task Engine Control");
		setLayout(new GridLayout(1, 2, 5, 5));
		startButton.addActionListener(new startButtonListener());
		add(startButton);
		stopButton.addActionListener(new stopButtonListener());
		addWindowListener(new WindowCloser());
		add(stopButton);
		startButton.setEnabled(true);
		stopButton.setEnabled(false);
		setLocation(300, 100);
		setSize(250, 100);
		setVisible(true);
	}

	private class WindowCloser extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			shutDown();
		}
	}

	private class startButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				registry = java.rmi.registry.LocateRegistry.createRegistry(1099);
				logger.info("RMI registry ready.");
				new TaskEngineImpl();
			} catch (IOException exc) {
				if(exc.getMessage().contains("Port already in use:")){
					logger.error("Failed to start engine because there is another taskengine running on that port");
					return;
				}
				logger.error("Failed to start engine", exc);
			}
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
		}
	}

	private class stopButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			startButton.setEnabled(false);
			shutDown();
		}
	}

	public void shutDown() {
		// Shut down the registry.
		try {
			UnicastRemoteObject.unexportObject(registry, true);
		} catch (NoSuchObjectException e) {
			logger.info("Registry is already closed");
		}
		logger.info("Registry is closed");
		startButton.setEnabled(true);
		stopButton.setEnabled(false);
	}

	public static void main(String[] args) {
		new TaskEngineStarter();
	}

}
