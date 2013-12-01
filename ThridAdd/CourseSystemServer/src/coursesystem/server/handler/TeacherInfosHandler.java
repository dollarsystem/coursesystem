package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.TeacherInfosReply;
import coursesystem.request.TeacherInfosRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Faculty;
import coursesystem.unit.Teacher;

public class TeacherInfosHandler 
{
	
	public static void onHandle(TeacherInfosRequest p_request) 
	{
		TeacherInfosReply t_reply=new TeacherInfosReply();
		try
		{
			Teacher t_teacher=Database.getTeacher(p_request.m_teacher_id);
			Faculty t_faculty=Database.getFaculty(t_teacher.m_faculty_id);
			t_reply.m_teacher=t_teacher;
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
