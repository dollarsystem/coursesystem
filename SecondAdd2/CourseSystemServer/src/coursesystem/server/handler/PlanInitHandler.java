package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.PlanInitReply;
import coursesystem.request.PlanInitRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Frame;

public class PlanInitHandler
{

	public static void onHandle(PlanInitRequest p_request)
	{
		PlanInitReply t_reply=new PlanInitReply();
		try
		{
			Frame t_frame=Database.getLastFrame();
			t_reply.m_frame=t_frame;
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
