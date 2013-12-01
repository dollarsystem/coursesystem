package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.StudentScoresReply;
import coursesystem.request.StudentScoresRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Student;

public class StudentScoresHandler
{

	public static void onHandle(StudentScoresRequest p_request)
	{
		StudentScoresReply t_reply=new StudentScoresReply();
		try
		{
			Student t_student=Database.getStudent(p_request.m_student_id);
			t_reply.m_student=t_student;
			if(!t_student.m_history_course_ids.equals(""))
				for(String t_course_id:t_student.m_history_course_ids.split(","))
					t_reply.m_courses.add(Database.getCourse(t_course_id));
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
