package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.ChangeMailBoxReply;
import coursesystem.request.ChangeMailBoxRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Dean;
import coursesystem.unit.Student;

public class ChangeMailBoxHandler
{

	public static void onHandle(ChangeMailBoxRequest p_request)
	{
		ChangeMailBoxReply t_reply=new ChangeMailBoxReply();
		try
		{
			if(p_request.m_type.equals("学生"))
			{
				Student t_student=Database.getStudent(p_request.m_id);
				t_student.m_mailbox=p_request.m_new_mailbox;
				Database.setStudent(t_student);
			}
			else if(p_request.m_type.equals("院系教务员"))
			{
				Dean t_dean=Database.getDean(p_request.m_id);
				t_dean.m_mailbox=p_request.m_new_mailbox;
				Database.setDean(t_dean);
			}
			else
				throw new IllegalArgumentException("无此类型！");
			t_reply.m_done=true;
		}
		catch(IllegalArgumentException t_exp)
		{
			t_reply.m_done=false;
			t_reply.m_message=t_exp.getMessage();
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
