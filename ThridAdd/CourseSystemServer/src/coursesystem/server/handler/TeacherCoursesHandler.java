package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.TeacherCoursesReply;
import coursesystem.request.TeacherCoursesRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Course;
import coursesystem.unit.Teacher;

public class TeacherCoursesHandler 
{
	
	public static void onHandle(TeacherCoursesRequest p_request) 
	{
		TeacherCoursesReply t_reply = new TeacherCoursesReply();
		try
		{
			Teacher t_teacher=Database.getTeacher(p_request.m_teacher_id);
			if(!t_teacher.m_course_ids.equals(""))
			{
				String[] t_course_ids=t_teacher.m_course_ids.split(",");
				for(String t_course_id:t_course_ids)
				{
					Course t_course=Database.getCourse(t_course_id);
					t_course.m_id=t_course_id;
					t_reply.m_courses.add(t_course);
				}
			}
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
