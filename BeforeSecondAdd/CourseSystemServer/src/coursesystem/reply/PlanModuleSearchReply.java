package coursesystem.reply;

import java.util.ArrayList;
import java.util.List;
import coursesystem.reply.superclass.Reply;
import coursesystem.unit.Course;
import coursesystem.unit.Teacher;

public class PlanModuleSearchReply extends Reply
{

	private static final long serialVersionUID=0;
	
	public List<Course> m_added=new ArrayList<Course>();
	public List<Teacher> m_added_teachers=new ArrayList<Teacher>();
	public List<Course> m_unadded=new ArrayList<Course>();
	public List<Teacher> m_unadded_teachers=new ArrayList<Teacher>();

}
