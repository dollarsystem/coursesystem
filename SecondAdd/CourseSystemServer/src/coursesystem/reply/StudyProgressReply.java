package coursesystem.reply;

import java.util.ArrayList;
import java.util.List;

import coursesystem.reply.superclass.Reply;
import coursesystem.unit.Course;
import coursesystem.unit.Plan;

public class StudyProgressReply extends Reply
{

	private static final long serialVersionUID=0;
	
	public Plan m_plan;
	public List<Course> m_courses=new ArrayList<Course>();

}
