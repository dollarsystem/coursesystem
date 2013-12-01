package coursesystem.reply;

import java.util.List;
import coursesystem.reply.superclass.Reply;
import coursesystem.unit.Course;
import coursesystem.unit.Teacher;

public class PlanModuleSearchReply extends Reply
{

	private static final long serialVersionUID=0;
	
	public List<Course> m_added;
	public List<Teacher> m_added_teachers;
	public List<Course> m_unadded;
	public List<Teacher> m_unadded_teachers;

}
