package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.DeleteProvestReply;
import coursesystem.request.DeleteProvestRequest;
import coursesystem.server.database.Database;

public class DeleteProvestHandler 
{
	
	public static void onHandle(DeleteProvestRequest p_request)
	{
		DeleteProvestReply t_reply=new DeleteProvestReply();
		try
		{
			Database.removeProvest(p_request.m_provest_id);
			t_reply.m_done=true;
		}
		catch(Exception t_exp)
		{
			t_exp.printStackTrace();
			t_reply.m_done=false;
			t_reply.m_message="服务器故障：\n"+t_exp.getMessage();
		}
		SmartEvents.happen(t_reply,true,p_request.getFriend());
	}
	
}
