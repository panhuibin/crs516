import java.awt.Dimension;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ltree.crs516.client.WOD;
import com.ltree.crs516.config.GuiConfig;

public final class Driver {
	
	private static final double GOLDEN_RATIO =(1 + Math.sqrt(5)) / 2; 
	private static final Dimension FRAME_SIZE 
				= new Dimension((int) (600 * GOLDEN_RATIO), 600);

	/**
	 * Starting point for the whole application. The input argument is not used.
	 */
	public static void main(String[] args) {
		
		

//TODO 1: (instructor demo later) Change the ApplicationContext implementation to
//AnnotationConfigApplicationContext and give it GuiConfig.class for the 
//configuration class.

//		ApplicationContext context
//			= new AnnotationConfigApplicationContext(GuiConfig.class);		

		ApplicationContext context 
			= new ClassPathXmlApplicationContext("guiConfig.xml");

		WOD wod = (WOD)context.getBean("wod");



		
		//{{Marker 1
		//}} end Marker 1
		
		wod.setSize(FRAME_SIZE);
		wod.setVisible(true);
	}
	
}
