package coursesystem.server.handler;

import java.util.List;
import zjs.smartevents.SmartEvents;
import coursesystem.reply.AllProvestReply;
import coursesystem.request.AllProvestRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Provest;

public class AllProvestHandler 
{
	
	public static void onHandle(AllProvestRequest p_request)
	{
		AllProvestReply t_reply=new AllProvestReply();
		try
		{
			List<Provest> t_provest=Database.getAllProvest();
			t_reply.m_provests=t_provest;
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
