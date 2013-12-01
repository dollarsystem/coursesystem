package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.SelectCourseReply;
import coursesystem.request.SelectCourseRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Course;
import coursesystem.unit.Student;

public class SelectCourseHandler
{

	public static void onHandle(SelectCourseRequest p_request)
	{
		SelectCourseReply t_reply=new SelectCourseReply();
		try
		{
			Student t_student=Database.getStudent(p_request.m_student_id);
			Course t_course=Database.getCourse(p_request.m_course_id);
			if(t_student.m_course_ids.contains(t_course.m_id))
				throw new IllegalArgumentException("该课程已在课程表中！");
			t_student.m_course_ids+=Course.toUnCompuledFormat(t_course.m_id)+",";
			t_student.m_course_scores+="-1,";
			t_course.m_student_ids+=t_student.m_id+",";
			Database.setStudent(t_student);
			Database.setCourse(t_course);
			t_reply.m_done=true;
		}
		catch(IllegalArgumentException t_exp)
		{
			t_reply.m_done=false;
			t_reply.m_message=t_exp.getMessage();
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
