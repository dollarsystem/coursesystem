package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.ChangePasswordReply;
import coursesystem.request.ChangePasswordRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Dean;
import coursesystem.unit.Manager;
import coursesystem.unit.Student;
import coursesystem.unit.Teacher;

public class ChangePasswordHandler
{

	public static void onHandle(ChangePasswordRequest p_request)
	{
		ChangePasswordReply t_reply=new ChangePasswordReply();
		try
		{
			if(p_request.m_type.equals("学生"))
			{
				Student t_student=Database.getStudent(p_request.m_id);
				t_student.m_password=p_request.m_new_password;
				Database.setStudent(t_student);
			}
			else if(p_request.m_type.equals("教师"))
			{
				Teacher t_teacher=Database.getTeacher(p_request.m_id);
				t_teacher.m_password=p_request.m_new_password;
				Database.setTeacher(t_teacher);
			}
			else if(p_request.m_type.equals("院系教务员"))
			{
				Dean t_dean=Database.getDean(p_request.m_id);
				t_dean.m_password=p_request.m_new_password;
				Database.setDean(t_dean);
			}
			else if(p_request.m_type.equals("系统管理员"))
			{
				Manager t_manager=Database.getManager(p_request.m_id);
				t_manager.m_password=p_request.m_new_password;
				Database.setManager(t_manager);
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
