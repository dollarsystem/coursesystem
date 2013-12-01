package coursesystem.client.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import coursesystem.client.handler.NoticeDetailHandler;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class NoticeDetailDialog
{
	
	private XMLContainer m_gui;
	private String m_type;
	private String m_id;
	private String m_name;
	private String m_notice_id;
	private String m_sender_type;
	private String m_sender_id;

	public NoticeDetailDialog(String p_type,String p_id,String p_name,String p_notice_id,String p_sender_type,String p_sender_id) throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException
	{
		m_gui=SmartGuis.create(new FileInputStream("res/dialog/NoticeDetailDialog.xml"));
		m_type=p_type;
		m_id=p_id;
		m_name=p_name;
		m_notice_id=p_notice_id;
		m_sender_type=p_sender_type;
		m_sender_id=p_sender_id;
		assign();
		NoticeDetailHandler.onHandle(m_gui,m_notice_id);
	}
	
	private void assign()
	{
		JButton t_reply=(JButton)SmartGuis.find(m_gui,"reply");
		t_reply.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				try
				{
					new NewNoticeDialog(m_type,m_id,m_name,m_sender_type+"#"+m_sender_id);
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
	}
	
}
