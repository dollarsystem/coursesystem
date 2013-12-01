package coursesystem.server.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import coursesystem.unit.Provest;

class ProvestTable
{

	public static void init() throws SQLException
	{
		Database.execute("create table if not exists Provest(id,name,sex,mailbox,password);");
	}

	public static Provest getProvest(String p_provest_id) throws SQLException
	{
		String t_sql="select * from Provest where id='"+p_provest_id+"';";
		ResultSet t_records=Database.execute(t_sql);
		if(!t_records.next())
			return null;
		Provest t_provest=new Provest();
		t_provest.m_id=p_provest_id;
		t_provest.m_name=t_records.getString("name");
		t_provest.m_sex=t_records.getString("sex");
		t_provest.m_mailbox=t_records.getString("mailbox");
		t_provest.m_password=t_records.getString("password");
		t_provest.fillNull();
		return t_provest;
	}

	public static void setProvest(Provest p_provest) throws SQLException
	{
		String t_sql="delete from Provest where id='"+p_provest.m_id+"';";
		Database.execute(t_sql);
		StringBuilder t_sb=new StringBuilder("insert into Provest values('");
		t_sb.append(p_provest.m_id).append("','")
			.append(p_provest.m_name).append("','")
			.append(p_provest.m_sex).append("','")
			.append(p_provest.m_mailbox).append("','")
			.append(p_provest.m_password).append("');");
		Database.execute(t_sb.toString());
	}
	
	public static void removeProvest(String p_provest_id) throws SQLException
	{
		String t_sql="delete from Provest where id='"+p_provest_id+"';";
		Database.execute(t_sql);
	}
	
}
