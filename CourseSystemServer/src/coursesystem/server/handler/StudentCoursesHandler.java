package coursesystem.server.handler;

import coursesystem.reply.StudentCoursesReply;
import coursesystem.request.StudentCoursesRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Course;
import coursesystem.unit.Student;
import zjs.smartevents.SmartEvents;

public class StudentCoursesHandler
{
	
	public static void onHandle(StudentCoursesRequest p_request)
	{
		StudentCoursesReply t_reply=new StudentCoursesReply();
		try
		{
			Student t_student=Database.getStudent(p_request.m_student_id);
			if(!t_student.m_course_ids.equals(""))
			{
				String[] t_course_ids=t_student.m_course_ids.split(",");
				for(String t_course_id:t_course_ids)
				{
					Course t_course=Database.getCourse(t_course_id);
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
