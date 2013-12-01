package coursesystem.reply;

import java.util.List;
import coursesystem.reply.superclass.Reply;
import coursesystem.unit.Course;
import coursesystem.unit.Faculty;
import coursesystem.unit.Teacher;

public class AllCoursesReply extends Reply
{
	
	private static final long serialVersionUID=0;
	
	public List<Course> m_courses;
	public List<Teacher> m_teachers;
	public List<Faculty> m_facultys;
	
}
