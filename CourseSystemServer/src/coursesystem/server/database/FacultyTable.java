package coursesystem.server.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import coursesystem.unit.Faculty;

class FacultyTable
{
	
	public static void init() throws SQLException
	{
		Database.execute("create table if not exists Faculty(id,name,description);");
	}

	public static Faculty getFaculty(String p_faculty_id) throws SQLException
	{
		String t_sql="select * from Faculty where id='"+p_faculty_id+"';";
		ResultSet t_records=Database.execute(t_sql);
		if(!t_records.next())
			return null;
		Faculty t_faculty=new Faculty();
		t_faculty.m_id=p_faculty_id;
		t_faculty.m_name=t_records.getString("name");
		t_faculty.m_description=t_records.getString("description");
		return t_faculty;
	}
	
	public static void setFaculty(Faculty p_faculty) throws SQLException
	{
		String t_sql="delete from Faculty where id='"+p_faculty.m_id+"';";
		Database.execute(t_sql);
		StringBuilder t_sb=new StringBuilder("insert into Faculty values('");
		t_sb.append(p_faculty.m_id).append("','")
			.append(p_faculty.m_name).append("','")
			.append(p_faculty.m_description).append("');");
		Database.execute(t_sb.toString());
	}
	
	public static void removeFaculty(String p_faculty_id) throws SQLException
	{
		String t_sql="delete from Faculty where id='"+p_faculty_id+"';";
		Database.execute(t_sql);
	}
	
}
