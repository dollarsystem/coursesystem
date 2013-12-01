package coursesystem.server.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import coursesystem.unit.Student;

class StudentTable
{
	
	public static void init() throws SQLException
	{
		Database.execute("create table if not exists Student(id,name,sex,faculty_id,grade,mailbox,password,course_ids,course_scores,history_course_ids,history_course_scores);");
	}

	public static Student getStudent(String p_student_id) throws SQLException
	{
		String t_sql="select * from Student where id='"+p_student_id+"';";
		ResultSet t_results=Database.execute(t_sql);
		if(!t_results.next())
			return null;
		Student t_student=new Student();
		t_student.m_id=p_student_id;
		t_student.m_name=t_results.getString("name");
		t_student.m_sex=t_results.getString("sex");
		t_student.m_faculty_id=t_results.getString("faculty_id");
		t_student.m_grade=t_results.getString("grade");
		t_student.m_mailbox=t_results.getString("mailbox");
		t_student.m_password=t_results.getString("password");
		t_student.m_course_ids=t_results.getString("course_ids");
		t_student.m_course_scores=t_results.getString("course_scores");
		t_student.m_history_course_ids=t_results.getString("history_course_ids");
		t_student.m_history_course_scores=t_results.getString("history_course_scores");
		t_student.fillNull();
		return t_student;
	}
	
	public static List<Student> getStudentOf(String p_faculty_id,String p_grade) throws SQLException
	{
		List<Student> t_students=new ArrayList<Student>();
		String t_sql="select * from Student where faculty_id='"+p_faculty_id+"' and grade='"+p_grade+"';";
		ResultSet t_results=Database.execute(t_sql);
		while(t_results.next())
		{
			Student t_student=new Student();
			t_student.m_id=t_results.getString("id");
			t_student.m_name=t_results.getString("name");
			t_student.m_sex=t_results.getString("sex");
			t_student.m_faculty_id=t_results.getString("faculty_id");
			t_student.m_grade=t_results.getString("grade");
			t_student.m_mailbox=t_results.getString("mailbox");
			t_student.m_password=t_results.getString("password");
			t_student.m_course_ids=t_results.getString("course_ids");
			t_student.m_course_scores=t_results.getString("course_scores");
			t_student.m_history_course_ids=t_results.getString("history_course_ids");
			t_student.m_history_course_scores=t_results.getString("history_course_scores");
			t_student.fillNull();
			t_students.add(t_student);
		}
		return t_students;
	}
	
	public static Student getStudentDemo(String p_faculty_id,String p_grade) throws SQLException
	{
		String t_sql="select * from Student where faculty_id='"+p_faculty_id+"' and grade='"+p_grade+"';";
		ResultSet t_results=Database.execute(t_sql);
		if(!t_results.next())
			return null;
		Student t_student=new Student();
		t_student.m_id=t_results.getString("id");
		t_student.m_name=t_results.getString("name");
		t_student.m_sex=t_results.getString("sex");
		t_student.m_faculty_id=t_results.getString("faculty_id");
		t_student.m_grade=t_results.getString("grade");
		t_student.m_mailbox=t_results.getString("mailbox");
		t_student.m_password=t_results.getString("password");
		t_student.m_course_ids=t_results.getString("course_ids");
		t_student.m_course_scores=t_results.getString("course_scores");
		t_student.m_history_course_ids=t_results.getString("history_course_ids");
		t_student.m_history_course_scores=t_results.getString("history_course_scores");
		t_student.fillNull();
		return t_student;
	}
	
	public static void setStudent(Student p_student) throws SQLException
	{
		String t_sql="delete from Student where id='"+p_student.m_id+"';";
		Database.execute(t_sql);
		StringBuilder t_sb=new StringBuilder("insert into Student values('");
		t_sb.append(p_student.m_id).append("','")
			.append(p_student.m_name).append("','")
			.append(p_student.m_sex).append("','")
			.append(p_student.m_faculty_id).append("','")
			.append(p_student.m_grade).append("','")
			.append(p_student.m_mailbox).append("','")
			.append(p_student.m_password).append("','")
			.append(p_student.m_course_ids).append("','")	
			.append(p_student.m_course_scores).append("','")
			.append(p_student.m_history_course_ids).append("','")	
			.append(p_student.m_history_course_scores).append("');");
		Database.execute(t_sb.toString());
	}
	
	public static void removeStudent(String p_student_id) throws SQLException
	{
		String t_sql="delete from Student where id='"+p_student_id+"';";
		Database.execute(t_sql);
	}
	
}
