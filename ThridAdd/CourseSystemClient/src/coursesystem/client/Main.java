package coursesystem.client;

import javax.swing.UIManager;
import zjs.smartevents.SmartEvents;
import coursesystem.client.frame.LoginFrame;
import coursesystem.client.frame.TeacherFrame;

public class Main
{

	public static void main(String[] args) throws Exception
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		SmartEvents.shareWith("localhost",5000);
		new LoginFrame();
		//new TeacherFrame("rentw");
	}

}
