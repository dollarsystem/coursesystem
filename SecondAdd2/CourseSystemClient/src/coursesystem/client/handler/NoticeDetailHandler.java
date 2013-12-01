package coursesystem.client.handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import coursesystem.reply.NoticeDetailReply;
import coursesystem.request.NoticeDetailRequest;
import coursesystem.unit.Notice;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class NoticeDetailHandler
{

	public static void onHandle(XMLContainer p_gui,String p_notice_id)
	{
		NoticeDetailRequest t_request=new NoticeDetailRequest();
		t_request.m_notice_id=p_notice_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		NoticeDetailReply t_reply=(NoticeDetailReply)SmartEvents.expect(NoticeDetailReply.class);
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		if(!t_reply.m_done)
		{
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		SmartGuis.set(SmartGuis.find(p_gui,"date"),"text",t_reply.m_notice.m_date);
		SmartGuis.set(SmartGuis.find(p_gui,"sender"),"text",Notice.getSenderName(t_reply.m_notice.m_sender));
		SmartGuis.set(SmartGuis.find(p_gui,"content"),"text",t_reply.m_notice.m_content);
		t_frame.pack();
	}
	
}
