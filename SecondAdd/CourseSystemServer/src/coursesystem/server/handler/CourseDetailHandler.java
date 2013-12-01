package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.CourseDetailReply;
import coursesystem.request.CourseDetailRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Course;
import coursesystem.unit.Faculty;
import coursesystem.unit.Teacher;

public class CourseDetailHandler
{
	
	public static void onHandle(CourseDetailRequest p_request)
	{
		CourseDetailReply t_reply=new CourseDetailReply();
		try
		{
			Course t_course=Database.getCourse(p_request.m_course_id);
			Teacher t_teacher=Database.getTeacher(t_course.m_teacher_id);
			Faculty t_faculty=Database.getFaculty(t_course.m_faculty_id);
			t_reply.m_course=t_course;
			t_reply.m_teacher=t_teacher;
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
