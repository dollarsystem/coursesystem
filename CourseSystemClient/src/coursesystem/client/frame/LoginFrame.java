package coursesystem.client.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import coursesystem.client.handler.LoginHandler;
import zjs.smartguis.XMLContainer;
import zjs.smartguis.SmartGuis;

public class LoginFrame
{
	
	private XMLContainer m_gui;

	public LoginFrame() throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException
	{
		m_gui=SmartGuis.create(new FileInputStream("res/frame/LoginFrame.xml"));
		assign();
	}
	
	private void assign()
	{
		JButton t_button=(JButton)SmartGuis.find(m_gui,"login");
		t_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent p_exp)
			{
				LoginHandler.onHandle(m_gui);
			}
		});
	}
	
}
