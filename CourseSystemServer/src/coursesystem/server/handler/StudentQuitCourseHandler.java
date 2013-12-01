package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.StudentQuitCourseReply;
import coursesystem.request.StudentQuitCourseRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Student;

public class StudentQuitCourseHandler
{

	public static void onHandle(StudentQuitCourseRequest p_request)
	{
		StudentQuitCourseReply t_reply=new StudentQuitCourseReply();
		try
		{
			Student t_student=Database.getStudent(p_request.m_student_id);
			String[] t_course_ids=t_student.m_course_ids.split(",");
			String[] t_course_scores=t_student.m_course_scores.split(",");
			StringBuilder t_sb_course_ids=new StringBuilder();
			StringBuilder t_sb_course_scores=new StringBuilder();
			for(int t_i=0;t_i<t_course_ids.length;t_i++)
				if(!t_course_ids[t_i].equals(p_request.m_course_id))
				{
					t_sb_course_ids.append(t_course_ids[t_i]).append(",");
					t_sb_course_scores.append(t_course_scores[t_i]).append(",");
				}
			t_student.m_course_ids=t_sb_course_ids.toString();
			t_student.m_course_scores=t_sb_course_scores.toString();
			Database.setStudent(t_student);
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
