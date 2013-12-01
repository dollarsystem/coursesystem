package coursesystem.server.handler;

import java.util.List;
import zjs.smartevents.SmartEvents;
import coursesystem.reply.AllCoursesInitReply;
import coursesystem.request.AllCoursesInitRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Faculty;

public class AllCoursesInitHandler
{

	public static void onHandle(AllCoursesInitRequest p_request)
	{
		AllCoursesInitReply t_reply=new AllCoursesInitReply();
		try
		{
			List<Faculty> t_facultys=Database.getAllFacultys();
			t_reply.m_facultys=t_facultys;
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
