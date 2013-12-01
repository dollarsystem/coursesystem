package coursesystem.server.handler;

import java.util.List;
import zjs.smartevents.SmartEvents;
import coursesystem.reply.DeanCompulsoryReply;
import coursesystem.request.DeanCompulsoryRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Course;
import coursesystem.unit.Dean;
import coursesystem.unit.Student;

public class DeanCompulsoryHandler
{

	public static void onHandle(DeanCompulsoryRequest p_request)
	{
		DeanCompulsoryReply t_reply=new DeanCompulsoryReply();
		try
		{
			Dean t_dean=Database.getDean(p_request.m_dean_id);
			List<Student> t_students=Database.getStudentOf(t_dean.m_faculty_id,p_request.m_grade);
			Course t_course=Database.getCourse(p_request.m_course_id);
			if(p_request.m_compulsory_uncompulsory)
				for(Student t_student:t_students)
				{
					if(t_student.m_course_ids.contains(t_course.m_id))
						t_student.m_course_ids=t_student.m_course_ids.replace(Course.toUnCompuledFormat(t_course.m_id),Course.toCompuledFormat(t_course.m_id));
					else
					{
						t_student.m_course_ids+=Course.toCompuledFormat(t_course.m_id)+",";
						t_student.m_course_scores+="-1,";
						t_course.m_student_ids+=t_student.m_id+",";
					}
					Database.setStudent(t_student);
				}
			else
				for(Student t_student:t_students)
				{
					String[] t_course_ids=t_student.m_course_ids.split(",");
					String[] t_course_scores=t_student.m_course_scores.split(",");
					StringBuilder t_sb_course_ids=new StringBuilder();
					StringBuilder t_sb_course_scores=new StringBuilder();
					for(int t_i=0;t_i<t_course_ids.length;t_i++)
						if(!t_course_ids[t_i].endsWith(p_request.m_course_id))
						{
							t_sb_course_ids.append(t_course_ids[t_i]).append(",");
							t_sb_course_scores.append(t_course_scores[t_i]).append(",");
						}
					t_student.m_course_ids=t_sb_course_ids.toString();
					t_student.m_course_scores=t_sb_course_scores.toString();
					t_course.m_student_ids=t_course.m_student_ids.replace(t_student.m_id+",","");
					Database.setStudent(t_student);
				}
			Database.setCourse(t_course);
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
