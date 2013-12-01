package coursesystem.client;

import javax.swing.UIManager;
import zjs.smartevents.SmartEvents;
import zjs.smartevents.events.UnSharedEvent;
import coursesystem.client.event.StudentQuitCourseEvent;
import coursesystem.client.frame.LoginFrame;
import coursesystem.client.frame.StudentFrame;
import coursesystem.unit.Student;

public class Main
{

	public static void main(String[] args) throws Exception
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		SmartEvents.bind(StudentQuitCourseEvent.class,StudentFrame.class,"onQuitCourse");
		SmartEvents.shareWith("localhost",5000);
		//LoginFrame.show();
		StudentFrame.show("121250224");
	}
	
	/*
	public static void onClose(UnSharedEvent p)
	{
		System.out.println("close");
	}
*/
}
