/**
 * 
 */
package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.StudentDetailReply;
import coursesystem.request.StudentDetailRequest;
import coursesystem.server.database.Database;

/**
 * @author Rillke
 * @create 20131130
 */
public class StudentDetailHandler {
	public static void onHandle(StudentDetailRequest p_request)
	{
		StudentDetailReply t_reply=new StudentDetailReply();
		try
		{
			t_reply.m_student=Database.getStudent(p_request.m_id);
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
