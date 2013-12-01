package coursesystem.client.dialog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import coursesystem.client.handler.StudyProgressHandler;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class StudyProgressDialog
{

	private XMLContainer m_gui;
	private String m_student_id;
	
	public StudyProgressDialog(String p_student_id) throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException
	{
		m_gui=SmartGuis.create(new FileInputStream("res/dialog/StudyProgressDialog.xml"));
		m_student_id=p_student_id;
		StudyProgressHandler.onHandle(m_gui,m_student_id);
	}
	
}
