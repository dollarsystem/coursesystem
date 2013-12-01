package coursesystem.server.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import coursesystem.unit.Dean;

class DeanTable
{

	public static void init() throws SQLException
	{
		Database.execute("create table if not exists Dean(id,name,sex,faculty_id,mailbox,password);");
	}

	public static Dean getDean(String p_dean_id) throws SQLException
	{
		String t_sql="select * from Dean where id='"+p_dean_id+"';";
		ResultSet t_records=Database.execute(t_sql);
		if(!t_records.next())
			return null;
		Dean t_dean=new Dean();
		t_dean.m_id=p_dean_id;
		t_dean.m_name=t_records.getString("name");
		t_dean.m_sex=t_records.getString("sex");
		t_dean.m_faculty_id=t_records.getString("faculty_id");
		t_dean.m_mailbox=t_records.getString("mailbox");
		t_dean.m_password=t_records.getString("password");
		t_dean.fillNull();
		return t_dean;
	}

	public static void setDean(Dean p_dean) throws SQLException
	{
		String t_sql="delete from Dean where id='"+p_dean.m_id+"';";
		Database.execute(t_sql);
		StringBuilder t_sb=new StringBuilder("insert into Dean values('");
		t_sb.append(p_dean.m_id).append("','")
			.append(p_dean.m_name).append("','")
			.append(p_dean.m_sex).append("','")
			.append(p_dean.m_faculty_id).append("','")
			.append(p_dean.m_mailbox).append("','")
			.append(p_dean.m_password).append("');");
		Database.execute(t_sb.toString());
	}
	
	public static void removeDean(String p_dean_id) throws SQLException
	{
		String t_sql="delete from Dean where id='"+p_dean_id+"';";
		Database.execute(t_sql);
	}
	
}
