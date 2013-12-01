package coursesystem.client.dialog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import coursesystem.client.handler.StudentDetailHandler;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class StudentDetailDialog {

	private XMLContainer m_gui;
	//private XMLContainer m_father;
	private String m_id;
	
	public StudentDetailDialog(String p_id) throws InstantiationException, IllegalAccessException, FileNotFoundException, ParserConfigurationException, SAXException, IOException {
		//m_father=p_gui;
		m_gui=SmartGuis.create(new FileInputStream("res/dialog/StudentDetailDialog.xml"));
		m_id=p_id;
		//assign();
		StudentDetailHandler.onHandle(m_gui,m_id);
	}
	

}
