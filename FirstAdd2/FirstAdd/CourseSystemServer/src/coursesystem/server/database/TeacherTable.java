package coursesystem.server.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import coursesystem.unit.Teacher;

final class TeacherTable
{
	
	public static void init() throws SQLException
	{
		Database.execute("create table if not exists Teacher(id,name,sex,faculty_id,mailbox,password,course_ids);");
	}

	public static Teacher getTeacher(String p_teacher_id) throws SQLException
	{
		String t_sql="select * from Teacher where id='"+p_teacher_id+"';";
		ResultSet t_results=Database.execute(t_sql);
		if(!t_results.next())
			return null;
		Teacher t_teacher=new Teacher();
		t_teacher.m_id=p_teacher_id;
		t_teacher.m_name=t_results.getString("name");
		t_teacher.m_sex=t_results.getString("sex");
		t_teacher.m_faculty_id=t_results.getString("faculty_id");
		t_teacher.m_mailbox=t_results.getString("mailbox");
		t_teacher.m_password=t_results.getString("password");
		t_teacher.m_course_ids=t_results.getString("course_ids");
		t_teacher.fillNull();
		return t_teacher;
	}
	
	public static List<Teacher> getTeacherOf(String p_faculty_id) throws SQLException
	{
		List<Teacher> t_teachers=new ArrayList<Teacher>();
		String t_sql="select * from Teacher where faculty_id='"+p_faculty_id+"';";
		ResultSet t_results=Database.execute(t_sql);
		while(t_results.next())
		{
			Teacher t_teacher=new Teacher();
			t_teacher.m_id=t_results.getString("id");
			t_teacher.m_name=t_results.getString("name");
			t_teacher.m_sex=t_results.getString("sex");
			t_teacher.m_faculty_id=t_results.getString("faculty_id");
			t_teacher.m_mailbox=t_results.getString("mailbox");
			t_teacher.m_password=t_results.getString("password");
			t_teacher.m_course_ids=t_results.getString("course_ids");
			t_teacher.fillNull();
			t_teachers.add(t_teacher);
		}
		return t_teachers;
	}
	
	public static void setTeacher(Teacher p_teacher) throws SQLException
	{
		String t_sql="delete from Teacher where id='"+p_teacher.m_id+"';";
		Database.execute(t_sql);
		StringBuilder t_sb=new StringBuilder("insert into Teacher values('");
		t_sb.append(p_teacher.m_id).append("','")
			.append(p_teacher.m_name).append("','")
			.append(p_teacher.m_sex).append("','")
			.append(p_teacher.m_faculty_id).append("','")
			.append(p_teacher.m_mailbox).append("','")
			.append(p_teacher.m_password).append("','")
			.append(p_teacher.m_course_ids).append("');");
		Database.execute(t_sb.toString());
	}
	
	public static void removeTeacher(String p_teacher_id) throws SQLException
	{
		String t_sql="delete from Teacher where id='"+p_teacher_id+"';";
		Database.execute(t_sql);
	}
	
}
