package coursesystem.server.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import coursesystem.unit.Course;

class CourseTable
{
	
	public static void init() throws SQLException
	{
		Database.execute("create table if not exists Course(id,name,type,marks,faculty_id,teacher_id,classes,description,student_ids);");
	}

	public static Course getCourse(String p_course_id) throws SQLException
	{
		String t_sql="select * from Course where id='"+p_course_id+"';";
		ResultSet t_records=Database.execute(t_sql);
		if(!t_records.next())
			return null;
		Course t_course=new Course();
		t_course.m_id=p_course_id;
		t_course.m_name=t_records.getString("name");
		t_course.m_type=t_records.getString("type");
		t_course.m_marks=t_records.getString("marks");
		t_course.m_faculty_id=t_records.getString("faculty_id");
		t_course.m_teacher_id=t_records.getString("teacher_id");
		t_course.m_classes=t_records.getString("classes");
		t_course.m_description=t_records.getString("description");
		t_course.m_student_ids=t_records.getString("student_ids");
		return t_course;
	}

	public static void setCourse(Course p_course) throws SQLException
	{
		String t_sql="delete from Course where id='"+p_course.m_id+"';";
		Database.execute(t_sql);
		StringBuilder t_sb=new StringBuilder("insert into Course values('");
		t_sb.append(p_course.m_id).append("','")
			.append(p_course.m_name).append("','")
			.append(p_course.m_type).append("','")
			.append(p_course.m_marks).append("','")
			.append(p_course.m_faculty_id).append("','")
			.append(p_course.m_teacher_id).append("','")
			.append(p_course.m_classes).append("','")
			.append(p_course.m_description).append("','")
			.append(p_course.m_student_ids).append("');");
		Database.execute(t_sb.toString());
	}
	
	public static void removeCourse(String p_course_id) throws SQLException
	{
		String t_sql="delete from Course where id='"+p_course_id+"';";
		Database.execute(t_sql);
	}
	
}
