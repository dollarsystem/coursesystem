package coursesystem.reply;

import java.util.ArrayList;
import java.util.List;
import coursesystem.reply.superclass.Reply;
import coursesystem.unit.Course;
import coursesystem.unit.Student;

public class StudentScoresReply extends Reply
{

	private static final long serialVersionUID=0;
	
	public Student m_student;
	public List<Course> m_courses=new ArrayList<Course>();;

}
