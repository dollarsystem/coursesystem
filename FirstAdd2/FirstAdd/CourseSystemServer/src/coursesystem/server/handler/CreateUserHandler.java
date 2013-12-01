/**
 * 
 */
package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.CreateUserReply;
import coursesystem.request.CreateUserRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Dean;
import coursesystem.unit.Provest;
import coursesystem.unit.Student;
import coursesystem.unit.Teacher;

/**
 * @author Rillke
 * @create 20131130
 */
public class CreateUserHandler {
	public static void onHandle(CreateUserRequest p_request)
	{
		
		CreateUserReply t_reply=new CreateUserReply();
		try
		{
			if(p_request.m_type==CreateUserRequest.STUDENT)
			{
				Student u=new Student();
				u.m_id=p_request.m_student.m_id;
				u.m_name=p_request.m_student.m_name;
				u.m_sex=p_request.m_student.m_sex;
				u.m_faculty_id=p_request.m_student.m_faculty_id;
				u.m_grade=p_request.m_student.m_grade;
				u.m_mailbox=p_request.m_student.m_mailbox;
				u.m_password=p_request.m_student.m_password;
				u.fillNull();
				Database.setStudent(u);
				t_reply.m_done=true;
			}
			else if(p_request.m_type==CreateUserRequest.TEACHER)
			{
				Teacher u=new Teacher();
				u.m_id=p_request.m_teacher.m_id;
				u.m_name=p_request.m_teacher.m_name;
				u.m_sex=p_request.m_teacher.m_sex;
				u.m_faculty_id=p_request.m_teacher.m_faculty_id;
				u.m_mailbox=p_request.m_teacher.m_mailbox;
				u.m_password=p_request.m_teacher.m_password;
				u.fillNull();
				Database.setTeacher(u);
				t_reply.m_done=true;
			}
			else if(p_request.m_type==CreateUserRequest.DEAN)
			{
				Dean u=new Dean();
				u.m_id=p_request.m_dean.m_id;
				u.m_name=p_request.m_dean.m_name;
				u.m_sex=p_request.m_dean.m_sex;
				u.m_faculty_id=p_request.m_dean.m_faculty_id;
				u.m_mailbox=p_request.m_dean.m_mailbox;
				u.m_password=p_request.m_dean.m_password;
				u.fillNull();
				Database.setDean(u);
				t_reply.m_done=true;
			}
			else if(p_request.m_type==CreateUserRequest.PROVEST)
			{
				Provest u=new Provest();
				u.m_id=p_request.m_provest.m_id;
				u.m_name=p_request.m_provest.m_name;
				u.m_sex=p_request.m_provest.m_sex;
				u.m_mailbox=p_request.m_provest.m_mailbox;
				u.m_password=p_request.m_provest.m_password;
				u.fillNull();
				Database.setProvest(u);
				t_reply.m_done=true;
			}
			
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
