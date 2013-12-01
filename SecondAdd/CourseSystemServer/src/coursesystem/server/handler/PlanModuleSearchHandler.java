package coursesystem.server.handler;

import java.util.List;
import zjs.smartevents.SmartEvents;
import coursesystem.reply.PlanModuleSearchReply;
import coursesystem.request.PlanModuleSearchRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Course;
import coursesystem.unit.Dean;
import coursesystem.unit.Plan;
import coursesystem.unit.Teacher;

public class PlanModuleSearchHandler
{

	public static void onHandle(PlanModuleSearchRequest p_request)
	{
		PlanModuleSearchReply t_reply=new PlanModuleSearchReply();
		try
		{
			List<Course> t_courses=Database.getCoursesOfType(p_request.m_type);
			Dean t_dean=Database.getDean(p_request.m_dean_id);
			Plan t_plan=Database.getPlan(t_dean.m_faculty_id);
			String t_courselist=null;
			for(String t_module:t_plan.m_modules.split(";"))
			{
				int t_index_1=t_module.indexOf(":");
				String t_type=t_module.substring(0,t_index_1);
				if(t_type.equals(p_request.m_type))
				{
					int t_index_2=t_module.indexOf("#");
					t_courselist=t_module.substring(t_index_2+1);
					break;
				}
			}
			for(Course t_course:t_courses)
			{
				Teacher t_teacher=Database.getTeacher(t_course.m_teacher_id);
				if(t_courselist!=null&&t_courselist.contains(t_course.m_id))
				{
					t_reply.m_added.add(t_course);
					t_reply.m_added_teachers.add(t_teacher);
				}
				else
				{
					t_reply.m_unadded.add(t_course);
					t_reply.m_unadded_teachers.add(t_teacher);
				}
			}
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
