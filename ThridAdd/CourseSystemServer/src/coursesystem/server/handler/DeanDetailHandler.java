package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.DeanDetailReply;
import coursesystem.request.DeanDetailRequest;
import coursesystem.server.database.Database;

public class DeanDetailHandler 
{
	
	public static void onHandle(DeanDetailRequest p_request)
	{
		DeanDetailReply t_reply=new DeanDetailReply();
		try
		{
			t_reply.m_dean=Database.getDean(p_request.m_id);
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
