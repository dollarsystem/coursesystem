package coursesystem.server.handler;

import java.util.List;
import zjs.smartevents.SmartEvents;
import coursesystem.reply.DeanCoursesReply;
import coursesystem.request.DeanCoursesRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Course;
import coursesystem.unit.Dean;
import coursesystem.unit.Student;
import coursesystem.unit.Teacher;

public class DeanCoursesHandler
{
	
	public static void onHandle(DeanCoursesRequest p_request)
	{
		DeanCoursesReply t_reply=new DeanCoursesReply();
		try
		{
			Dean t_dean=Database.getDean(p_request.m_dean_id);
			List<Course> t_all_courses=Database.getCourseOf(t_dean.m_faculty_id);
			Student t_student=Database.getStudentDemo(t_dean.m_faculty_id,p_request.m_grade);
			if(t_student==null)
				throw new IllegalArgumentException();
			String t_course_ids=t_student.m_course_ids;
			for(Course t_course:t_all_courses)
			{
				Teacher t_teacher=Database.getTeacher(t_course.m_teacher_id);
				if(t_course_ids.contains(Course.toCompuledFormat(t_course.m_id)+","))
				{
					t_reply.m_compuled_courses.add(t_course);
					t_reply.m_compuled_course_teachers.add(t_teacher);
				}
				else
				{
					t_reply.m_uncompuled_courses.add(t_course);
					t_reply.m_uncompuled_course_teachers.add(t_teacher);
				}
			}
			t_reply.m_done=true;
		}
		catch(IllegalArgumentException t_exp)
		{
			t_reply.m_done=false;
			t_reply.m_message="该年级暂无学生信息！\n";
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
