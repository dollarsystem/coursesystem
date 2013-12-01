/**
 * 
 */
package coursesystem.client.dialog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;
import coursesystem.client.handler.DeanDetailHandler;

/**
 * @author Rillke
 * @create 20131130
 */
public class DeanDetailDialog {
	private XMLContainer m_gui;
	//private XMLContainer m_father;
	private String m_id;
	
	public DeanDetailDialog(String p_id) throws InstantiationException, IllegalAccessException, FileNotFoundException, ParserConfigurationException, SAXException, IOException {
		//m_father=p_gui;
		m_gui=SmartGuis.create(new FileInputStream("res/dialog/DeanDetailDialog.xml"));
		m_id=p_id;
		//assign();
		DeanDetailHandler.onHandle(m_gui,m_id);
	}
}
