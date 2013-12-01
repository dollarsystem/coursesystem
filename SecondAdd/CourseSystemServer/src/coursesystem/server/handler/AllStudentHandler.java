package coursesystem.server.handler;

import java.util.List;
import zjs.smartevents.SmartEvents;
import coursesystem.reply.AllStudentReply;
import coursesystem.request.AllStudentRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Student;

public class AllStudentHandler 
{
	
	public static void onHandle(AllStudentRequest p_request)
	{
		AllStudentReply t_reply=new AllStudentReply();
		try
		{
			List<Student> t_student=Database.getStudentOf(p_request.m_faculty_id,p_request.m_grade);
			t_reply.m_students=t_student;
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
