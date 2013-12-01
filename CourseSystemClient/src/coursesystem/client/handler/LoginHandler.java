package coursesystem.client.handler;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import coursesystem.client.frame.StudentFrame;
import coursesystem.reply.LoginReply;
import coursesystem.request.LoginRequest;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class LoginHandler
{
	
	public static void onHandle(XMLContainer p_gui)
	{
		JTextField t_id_field=(JTextField)SmartGuis.find(p_gui,"id");
		String t_id=t_id_field.getText();
		JPasswordField t_psd_field=(JPasswordField)SmartGuis.find(p_gui,"password");
		String t_password=new String(t_psd_field.getPassword());
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		if(("").equals(t_id)||("").equals(t_password))
		{
			JOptionPane.showMessageDialog(t_frame,"帐号和密码不能为空！","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		@SuppressWarnings("rawtypes")
		JComboBox t_type_combo=(JComboBox)SmartGuis.find(p_gui,"type");
		String t_type=t_type_combo.getSelectedItem().toString();
		LoginRequest t_request=new LoginRequest();
		t_request.m_id=t_id;
		t_request.m_password=t_password;
		t_request.m_type=t_type;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		LoginReply t_reply=(LoginReply)SmartEvents.expect(LoginReply.class);
		if(!t_reply.m_done)
		{
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			t_frame.dispose();
			try
			{
				if(t_type.equals("学生"))
					new StudentFrame(t_id);
				/*
				else if(p_type.equals("teacher"))
					new TeacherFrame((Teacher)t_reply.m_self);
				else if(p_type.equals("dean"))
					new DeanFrame((Dean)t_reply.m_self);
				else if(p_type.equals("provest"))
					new ProvestFrame((Provest)t_reply.m_self);
				else if(p_type.equals("manager"))
					new ManagerFrame((Manager)t_reply.m_self);
					*/
			}
			catch(Exception t_exp)
			{
				t_exp.printStackTrace();
			}
		}
	}

}
