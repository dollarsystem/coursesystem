package coursesystem.client.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import coursesystem.client.handler.SendNoticeHandler;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class NewNoticeDialog
{
	
	private XMLContainer m_gui;
	private String m_type;
	private String m_id;
	private String m_name;

	public NewNoticeDialog(String p_type,String p_id,String p_name,String p_receiver_ids) throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException
	{
		m_gui=SmartGuis.create(new FileInputStream("res/dialog/NewNoticeDialog.xml"));
		m_type=p_type;
		m_id=p_id;
		m_name=p_name;
		SmartGuis.set(SmartGuis.find(m_gui,"receiver_ids"),"text",p_receiver_ids);
		assign();
	}
	
	private void assign()
	{
		JButton t_send=(JButton)SmartGuis.find(m_gui,"send");
		t_send.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				SendNoticeHandler.onHandle(m_gui,m_type,m_id,m_name);
			}
		});
	}
	
}
