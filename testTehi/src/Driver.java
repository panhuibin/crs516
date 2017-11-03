import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.ltree.crs516.*;

public class Driver {
	public static void main(String[] args) {
		ApplicationContext context 
		= new ClassPathXmlApplicationContext("guiConfig.xml");
//		MainFrame mainFrame = (MainFrame)context.getBean("mainFrame");
//		new MainFrame();
	}
}
