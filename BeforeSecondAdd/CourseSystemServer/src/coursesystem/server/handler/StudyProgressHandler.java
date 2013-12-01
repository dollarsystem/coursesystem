package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.StudyProgressReply;
import coursesystem.request.StudyProgressRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Course;
import coursesystem.unit.Student;

public class StudyProgressHandler
{

	public static void onHandle(StudyProgressRequest p_request)
	{
		StudyProgressReply t_reply=new StudyProgressReply();
		try
		{
			Student t_student=Database.getStudent(p_request.m_student_id);
			t_reply.m_plan=Database.getPlan(t_student.m_faculty_id);
			if(!t_student.m_history_course_ids.equals(""))
			{
				String[] t_course_ids=t_student.m_history_course_ids.split(",");
				for(String t_course_id:t_course_ids)
				{
					Course t_course=Database.getCourse(t_course_id);
					t_reply.m_courses.add(t_course);
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
