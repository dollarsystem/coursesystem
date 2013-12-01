package coursesystem.client.dialog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;
import coursesystem.client.handler.CourseDetailHandler;

public class CourseDetailDialog
{

	private static XMLContainer sm_gui;
	private static String sm_course_id;
	
	public static void show(String p_course_id) throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException
	{
		sm_gui=SmartGuis.create(new FileInputStream("res/dialog/CourseDetailDialog.xml"));
		sm_course_id=p_course_id;
		CourseDetailHandler.onHandle(sm_gui,sm_course_id);
	}
	
}
