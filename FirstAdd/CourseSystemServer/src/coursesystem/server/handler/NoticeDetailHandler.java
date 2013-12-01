package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.NoticeDetailReply;
import coursesystem.request.NoticeDetailRequest;
import coursesystem.server.database.Database;

public class NoticeDetailHandler
{

	public static void onHandle(NoticeDetailRequest p_request)
	{
		NoticeDetailReply t_reply=new NoticeDetailReply();
		try
		{
			t_reply.m_notice=Database.getNotice(p_request.m_notice_id);
			t_reply.m_done=true;
		} 
		catch (Exception t_exp)
		{
			t_exp.printStackTrace();
			t_reply.m_done=false;
			t_reply.m_message="服务器故障：\n"+t_exp.getMessage();
		}
		SmartEvents.happen(t_reply,true,p_request.getFriend());
	}
	
}
