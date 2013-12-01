package coursesystem.client.handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import coursesystem.reply.ChangePasswordReply;
import coursesystem.request.ChangePasswordRequest;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class ChangePasswordHandler
{

	public static void onHandle(XMLContainer p_gui,String p_type,String p_id)
	{
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		String t_password=JOptionPane.showInputDialog(t_frame,"请输入新密码：","修改密码",JOptionPane.INFORMATION_MESSAGE);
		if(t_password==null)
			return;
		if(t_password.equals(""))
		{
			JOptionPane.showMessageDialog(t_frame,"密码不能为空！","错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(t_password.length()<6)
		{
			JOptionPane.showMessageDialog(t_frame,"密码长度不能低于6位！","错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		String p_password_again=JOptionPane.showInputDialog(t_frame,"请再次输入密码：","修改密码",JOptionPane.INFORMATION_MESSAGE);
		if(p_password_again==null)
			return;
		if(!t_password.endsWith(p_password_again))
		{
			JOptionPane.showMessageDialog(t_frame,"两次密码输入不一致！","错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		ChangePasswordRequest t_request=new ChangePasswordRequest();
		t_request.m_type=p_type;
		t_request.m_id=p_id;
		t_request.m_new_password=t_password;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		ChangePasswordReply t_reply=(ChangePasswordReply)SmartEvents.expect(ChangePasswordReply.class);
		if(!t_reply.m_done)
		{
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			SmartGuis.find(p_gui,"info_password").set("text",getVisiblePassword(t_password));
			SmartGuis.set(SmartGuis.find(p_gui,"last_opt"),"text","修改密码："+getVisiblePassword(t_password));
			JOptionPane.showMessageDialog(t_frame,"密码修改成功！","提示",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public static String getVisiblePassword(String p_password)
	{
		StringBuilder t_password=new StringBuilder(p_password);
		for(int t_i=t_password.length()-3;t_i>1;t_i--)
			t_password.setCharAt(t_i,'*');
		return t_password.toString();
	}
	
}
