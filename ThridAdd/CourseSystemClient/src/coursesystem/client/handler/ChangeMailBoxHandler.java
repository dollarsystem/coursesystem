package coursesystem.client.handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import coursesystem.reply.ChangeMailBoxReply;
import coursesystem.request.ChangeMailBoxRequest;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class ChangeMailBoxHandler
{

	public static void onHandle(XMLContainer p_gui,String p_type,String p_id)
	{
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		String t_mailbox=JOptionPane.showInputDialog(t_frame,"请输入新邮箱：","修改邮箱",JOptionPane.INFORMATION_MESSAGE);
		if(t_mailbox==null)
			return;
		if(t_mailbox.equals(""))
		{
			JOptionPane.showMessageDialog(t_frame,"邮箱不能为空！","错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		ChangeMailBoxRequest t_request=new ChangeMailBoxRequest();
		t_request.m_type=p_type;
		t_request.m_id=p_id;
		t_request.m_new_mailbox=t_mailbox;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		ChangeMailBoxReply t_reply=(ChangeMailBoxReply)SmartEvents.expect(ChangeMailBoxReply.class);
		if(!t_reply.m_done)
		{
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			SmartGuis.find(p_gui,"info_mailbox").set("text",t_mailbox);
			SmartGuis.set(SmartGuis.find(p_gui,"last_opt"),"text","修改邮箱："+t_mailbox);
			JOptionPane.showMessageDialog(t_frame,"邮箱修改成功！","提示",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
}
