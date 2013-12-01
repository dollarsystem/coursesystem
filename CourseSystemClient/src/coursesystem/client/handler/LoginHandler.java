package coursesystem.client.handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import coursesystem.client.frame.StudentFrame;
import coursesystem.reply.LoginReply;
import coursesystem.request.LoginRequest;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class LoginHandler
{
	
	public static void onHandle(XMLContainer p_gui,String p_id,String p_password,String p_type)
	{
		LoginRequest t_request=new LoginRequest();
		t_request.m_id=p_id;
		t_request.m_password=p_password;
		t_request.m_type=p_type;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		LoginReply t_reply=(LoginReply)SmartEvents.expect(LoginReply.class);
		if(!t_reply.m_done)
		{
			JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
			t_frame.dispose();
			try
			{
				if(p_type.equals("学生"))
					StudentFrame.show(p_id);
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
