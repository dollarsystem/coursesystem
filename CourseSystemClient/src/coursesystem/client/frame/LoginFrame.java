package coursesystem.client.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import coursesystem.client.handler.LoginHandler;
import zjs.smartguis.XMLContainer;
import zjs.smartguis.SmartGuis;

public class LoginFrame
{
	
	private static XMLContainer sm_gui;

	public static void show() throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException
	{
		sm_gui=SmartGuis.create(new FileInputStream("res/frame/LoginFrame.xml"));
		JButton t_button=(JButton)SmartGuis.find(sm_gui,"login");
		t_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent p_exp)
			{
				JTextField t_id_field=(JTextField)SmartGuis.find(sm_gui,"id");
				String t_id=t_id_field.getText();
				JPasswordField t_psd_field=(JPasswordField)SmartGuis.find(sm_gui,"password");
				String t_password=new String(t_psd_field.getPassword());
				JFrame t_frame=(JFrame)SmartGuis.find(sm_gui,"frame");
				if(("").equals(t_id)||("").equals(t_password))
				{
					JOptionPane.showMessageDialog(t_frame,"帐号和密码不能为空！","提示",JOptionPane.WARNING_MESSAGE);
					return;
				}
				@SuppressWarnings("rawtypes")
				JComboBox t_type_combo=(JComboBox)SmartGuis.find(sm_gui,"type");
				String t_type=t_type_combo.getSelectedItem().toString();
				LoginHandler.onHandle(sm_gui,t_id,t_password,t_type);
			}
		});
	}
	
}
