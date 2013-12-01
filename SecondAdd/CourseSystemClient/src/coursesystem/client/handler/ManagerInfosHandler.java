/**
 * 
 */
package coursesystem.client.handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import coursesystem.reply.ManagerInfosReply;
import coursesystem.request.ManagerInfosRequest;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

/**
 * @author Rillke
 * @20131201
 */
public class ManagerInfosHandler 
{
	public static void onHandle(XMLContainer p_gui,String p_manager_id) 
	{
		ManagerInfosRequest t_request=new ManagerInfosRequest();
		t_request.m_manager_id=p_manager_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		ManagerInfosReply t_reply=(ManagerInfosReply)SmartEvents.expect(ManagerInfosReply.class);
		if(!t_reply.m_done)
		{
			JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		SmartGuis.find(p_gui,"info_id").set("text",t_reply.m_manager.m_id);
		SmartGuis.find(p_gui,"info_name").set("text",t_reply.m_manager.m_name);
		SmartGuis.find(p_gui,"info_sex").set("text",t_reply.m_manager.m_sex);
		SmartGuis.find(p_gui,"info_mailbox").set("text",t_reply.m_manager.m_mailbox);
		SmartGuis.find(p_gui,"info_password").set("text",ChangePasswordHandler.getVisiblePassword(t_reply.m_manager.m_password));
	}
	
}
