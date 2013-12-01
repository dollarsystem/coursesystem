package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.LoginReply;
import coursesystem.request.LoginRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Dean;
import coursesystem.unit.Manager;
import coursesystem.unit.Provest;
import coursesystem.unit.Student;
import coursesystem.unit.Teacher;

public class LoginHandler
{

	public static void onHandle(LoginRequest p_request)
	{
		LoginReply t_reply=new LoginReply();
		String t_password;
		try
		{
			if(("学生").equals(p_request.m_type))
			{
				Student t_student=Database.getStudent(p_request.m_id);
				t_password=t_student.m_password;
			}
			else if(("教师").equals(p_request.m_type))
			{
				Teacher t_teacher=Database.getTeacher(p_request.m_id);
				t_password=t_teacher.m_password;
			}
			else if(("院系教务员").equals(p_request.m_type))
			{
				Dean t_dean=Database.getDean(p_request.m_id);
				t_password=t_dean.m_password;
			}
			else if(("教务处老师").equals(p_request.m_type))
			{
				Provest t_provest=Database.getProvest(p_request.m_id);
				t_password=t_provest.m_password;
			}
			else if(("系统管理员").equals(p_request.m_type))
			{
				Manager t_manager=Database.getManager(p_request.m_id);
				t_password=t_manager.m_password;
			}
			else
				throw new IllegalArgumentException();
			if(t_password.equals(p_request.m_password))
				t_reply.m_done=true;
			else
			{
				t_reply.m_done=false;
				t_reply.m_message="密码错误！";
			}
		}
		catch(NullPointerException t_exp)
		{
			t_reply.m_done=false;
			t_reply.m_message="帐号不存在！";
		}
		catch(IllegalArgumentException t_exp)
		{
			t_reply.m_done=false;
			t_reply.m_message="没有这种角色！";
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
