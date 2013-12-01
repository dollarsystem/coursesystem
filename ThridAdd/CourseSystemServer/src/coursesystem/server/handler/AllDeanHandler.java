package coursesystem.server.handler;

import java.util.List;
import zjs.smartevents.SmartEvents;
import coursesystem.reply.AllDeanReply;
import coursesystem.request.AllDeanRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Dean;

public class AllDeanHandler 
{
	
	public static void onHandle(AllDeanRequest p_request)
	{
		AllDeanReply t_reply=new AllDeanReply();
		try
		{
			List<Dean> t_dean=Database.getDeanOf(p_request.m_faculty_id);
			t_reply.m_deans=t_dean;
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
