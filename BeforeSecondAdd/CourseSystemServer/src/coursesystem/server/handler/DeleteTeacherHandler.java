package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.DeleteTeacherReply;
import coursesystem.request.DeleteTeacherRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Course;
import coursesystem.unit.Teacher;

public class DeleteTeacherHandler 
{
	
	public static void onHandle(DeleteTeacherRequest p_request)
	{
		DeleteTeacherReply t_reply=new DeleteTeacherReply();
		try
		{
			Teacher t_teacher=Database.getTeacher(p_request.m_teacher_id);
			String[] t_course_ids=t_teacher.m_course_ids.split(",");
			for(String t_course_id:t_course_ids)
			{
				Course t_course=Database.getCourse(t_course_id);
				t_course.m_teacher_id="";
				Database.setCourse(t_course);
			}
			Database.removeTeacher(t_teacher.m_id);
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
