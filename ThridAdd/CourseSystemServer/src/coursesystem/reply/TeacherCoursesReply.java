package coursesystem.reply;

import java.util.ArrayList;
import java.util.List;

import coursesystem.reply.superclass.Reply;
import coursesystem.unit.Course;

public class TeacherCoursesReply extends Reply 
{
	private static final long serialVersionUID = 0;
	
	public List<Course> m_courses=new ArrayList<Course>();

}
