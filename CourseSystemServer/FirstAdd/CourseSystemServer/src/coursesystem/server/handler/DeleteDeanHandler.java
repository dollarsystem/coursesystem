package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.DeleteDeanReply;
import coursesystem.request.DeleteDeanRequest;
import coursesystem.server.database.Database;

public class DeleteDeanHandler 
{
	
	public static void onHandle(DeleteDeanRequest p_request)
	{
		DeleteDeanReply t_reply=new DeleteDeanReply();
		try
		{
			Database.removeDean(p_request.m_dean_id);
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
