package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.StudentInfosReply;
import coursesystem.request.StudentInfosRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Faculty;
import coursesystem.unit.Student;

public class StudentInfosHandler
{

	public static void onHandle(StudentInfosRequest p_request)
	{
		StudentInfosReply t_reply=new StudentInfosReply();
		try
		{
			Student t_student=Database.getStudent(p_request.m_student_id);
			Faculty t_faculty=Database.getFaculty(t_student.m_faculty_id);
			t_reply.m_student=t_student;
			t_reply.m_faculty=t_faculty;
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
