package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.NoticesReply;
import coursesystem.request.NoticesRequest;
import coursesystem.server.database.Database;

public class NoticesHandler
{

	public static void onHandle(NoticesRequest p_request)
	{
		NoticesReply t_reply=new NoticesReply();
		try
		{
			t_reply.m_notices=Database.getNoticeTo(p_request.m_type,p_request.m_id);
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
