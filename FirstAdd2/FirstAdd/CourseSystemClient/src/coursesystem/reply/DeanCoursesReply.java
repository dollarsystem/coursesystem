package coursesystem.reply;

import java.util.ArrayList;
import java.util.List;
import coursesystem.reply.superclass.Reply;
import coursesystem.unit.Course;
import coursesystem.unit.Teacher;

public class DeanCoursesReply extends Reply
{

	private static final long serialVersionUID=0;
	
	public List<Course> m_compuled_courses=new ArrayList<Course>();
	public List<Teacher> m_compuled_course_teachers=new ArrayList<Teacher>();
	public List<Course> m_uncompuled_courses=new ArrayList<Course>();
	public List<Teacher> m_uncompuled_course_teachers=new ArrayList<Teacher>();

}
