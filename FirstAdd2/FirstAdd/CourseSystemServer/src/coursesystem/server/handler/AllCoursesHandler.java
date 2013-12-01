package coursesystem.server.handler;

import java.util.ArrayList;
import java.util.List;
import zjs.smartevents.SmartEvents;
import coursesystem.reply.AllCoursesReply;
import coursesystem.request.AllCoursesRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Course;
import coursesystem.unit.Faculty;
import coursesystem.unit.Teacher;

public class AllCoursesHandler
{

	public static void onHandle(AllCoursesRequest p_request)
	{
		AllCoursesReply t_reply=new AllCoursesReply();
		try
		{
			List<Course> t_courses=Database.getCoursesOf(p_request.m_term,p_request.m_faculty_id);
			List<Faculty> t_facultys=new ArrayList<Faculty>();
			List<Teacher> t_teachers=new ArrayList<Teacher>();
			for(int t_i=0;t_i<t_courses.size();t_i++)
			{
				Course t_course=t_courses.get(t_i);
				t_facultys.add(Database.getFaculty(t_course.m_faculty_id));
				t_teachers.add(Database.getTeacher(t_course.m_teacher_id));
			}
			t_reply.m_courses=t_courses;
			t_reply.m_facultys=t_facultys;
			t_reply.m_teachers=t_teachers;
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
