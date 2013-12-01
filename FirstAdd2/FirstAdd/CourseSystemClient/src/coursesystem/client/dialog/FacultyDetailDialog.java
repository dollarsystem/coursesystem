package coursesystem.client.dialog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;
import coursesystem.client.handler.FacultyDetailHandler;

public class FacultyDetailDialog
{
	
	private XMLContainer m_gui;
	private String m_faculty_id;
	
	public FacultyDetailDialog(String p_faculty_id) throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException
	{
		m_gui=SmartGuis.create(new FileInputStream("res/dialog/FacultyDetailDialog.xml"));
		m_faculty_id=p_faculty_id;
		FacultyDetailHandler.onHandle(m_gui,m_faculty_id);
	}

}
