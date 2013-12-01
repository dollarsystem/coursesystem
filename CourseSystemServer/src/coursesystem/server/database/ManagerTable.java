package coursesystem.server.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import coursesystem.unit.Manager;

class ManagerTable
{

	public static void init() throws SQLException
	{
		Database.execute("create table if not exists Manager(id,name,sex,mailbox,password);");
	}

	public static Manager getManager(String p_manager_id) throws SQLException
	{
		String t_sql="select * from Manager where id='"+p_manager_id+"';";
		ResultSet t_records=Database.execute(t_sql);
		if(!t_records.next())
			return null;
		Manager t_manager=new Manager();
		t_manager.m_id=p_manager_id;
		t_manager.m_name=t_records.getString("name");
		t_manager.m_sex=t_records.getString("sex");
		t_manager.m_mailbox=t_records.getString("mailbox");
		t_manager.m_password=t_records.getString("password");
		return t_manager;
	}

	public static void setManager(Manager p_manager) throws SQLException
	{
		String t_sql="delete from Manager where id='"+p_manager.m_id+"';";
		Database.execute(t_sql);
		StringBuilder t_sb=new StringBuilder("insert into Manager values('");
		t_sb.append(p_manager.m_id).append("','")
			.append(p_manager.m_name).append("','")
			.append(p_manager.m_sex).append("','")
			.append(p_manager.m_mailbox).append("','")
			.append(p_manager.m_password).append("');");
		Database.execute(t_sb.toString());
	}
	
	public static void removeManager(String p_manager_id) throws SQLException
	{
		String t_sql="delete from Manager where id='"+p_manager_id+"';";
		Database.execute(t_sql);
	}
	
}
