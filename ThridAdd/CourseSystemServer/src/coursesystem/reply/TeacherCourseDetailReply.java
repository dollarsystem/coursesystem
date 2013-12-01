package coursesystem.reply;

import coursesystem.reply.superclass.Reply;
import coursesystem.unit.Course;
import coursesystem.unit.Faculty;
import coursesystem.unit.Teacher;

public class TeacherCourseDetailReply extends Reply {

	private static final long serialVersionUID = 1L;

	public Course m_course;
	public Faculty m_faculty;
	public Teacher m_teacher;
}
