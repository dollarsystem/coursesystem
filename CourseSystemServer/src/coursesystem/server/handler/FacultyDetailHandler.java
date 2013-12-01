package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.FacultyDetailReply;
import coursesystem.request.FacultyDetailRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Faculty;

public class FacultyDetailHandler
{

	public static void onHandle(FacultyDetailRequest p_request)
	{
		FacultyDetailReply t_reply=new FacultyDetailReply();
		try
		{
			Faculty t_faculty=Database.getFaculty(p_request.m_faculty_id);
			t_reply.m_faculty=t_faculty;
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
