package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.DeleteStudentReply;
import coursesystem.request.DeleteStudentRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Course;
import coursesystem.unit.Student;

public class DeleteStudentHandler 
{
	
	public static void onHandle(DeleteStudentRequest p_request)
	{
		DeleteStudentReply t_reply=new DeleteStudentReply();
		try
		{
			Student t_student=Database.getStudent(p_request.m_student_id);
			if(!t_student.m_course_ids.equals(""))
			{
				String[] t_course_ids=t_student.m_course_ids.split(",");
				for(String t_course_id:t_course_ids)
				{
					Course t_course=Database.getCourse(Course.getSelectableCourseId(t_course_id));
					t_course.m_student_ids=t_course.m_student_ids.replace(t_student.m_id+",","");
					Database.setCourse(t_course);
				}
			}
			Database.removeStudent(t_student.m_id);
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
