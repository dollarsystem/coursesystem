package coursesystem.server.handler;

import java.util.List;
import zjs.smartevents.SmartEvents;
import coursesystem.reply.AllTeacherReply;
import coursesystem.request.AllTeacherRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Teacher;

public class AllTeacherHandler 
{
	
	public static void onHandle(AllTeacherRequest p_request)
	{
		AllTeacherReply t_reply=new AllTeacherReply();
		try
		{
			List<Teacher> t_teacher=Database.getTeacherOf(p_request.m_faculty_id);
			t_reply.m_teachers=t_teacher;
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
