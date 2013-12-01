package coursesystem.client.handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import coursesystem.reply.ModifyDescriptionReply;
import coursesystem.request.ModifyDescriptionRequest;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class ModifyDescriptionHandler
{

	public static void onHandle(XMLContainer p_gui,String p_course_id)
	{
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		String t_description=JOptionPane.showInputDialog(t_frame,"请输入新描述：","新描述",JOptionPane.INFORMATION_MESSAGE);
		if(t_description==null)
			return;
		ModifyDescriptionRequest t_request=new ModifyDescriptionRequest();
		t_request.m_course_id=p_course_id;
		t_request.m_description=t_description;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		ModifyDescriptionReply t_reply=(ModifyDescriptionReply)SmartEvents.expect(ModifyDescriptionReply.class);
		if(!t_reply.m_done)
		{
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			SmartGuis.set(SmartGuis.find(p_gui,"last_opt"),"text","修改描述："+t_description);
			JOptionPane.showMessageDialog(t_frame,"描述修改成功！","提示",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
}
