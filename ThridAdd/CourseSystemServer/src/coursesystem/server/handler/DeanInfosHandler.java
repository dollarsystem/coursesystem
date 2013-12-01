package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.DeanInfosReply;
import coursesystem.request.DeanInfosRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Dean;
import coursesystem.unit.Faculty;

public class DeanInfosHandler
{

	public static void onHandle(DeanInfosRequest p_request)
	{
		DeanInfosReply t_reply=new DeanInfosReply();
		try
		{
			Dean t_dean=Database.getDean(p_request.m_dean_id);
			Faculty t_faculty=Database.getFaculty(t_dean.m_faculty_id);
			t_reply.m_dean=t_dean;
			t_reply.m_faculty=t_faculty;
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
