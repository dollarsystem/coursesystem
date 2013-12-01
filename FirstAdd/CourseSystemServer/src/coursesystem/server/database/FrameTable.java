package coursesystem.server.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import coursesystem.unit.Frame;

class FrameTable
{

	public static void init() throws SQLException
	{
		Database.execute("create table if not exists Frame(id,modules);");
	}

	public static Frame getFrame(String p_frame_id) throws SQLException
	{
		String t_sql="select * from Frame where id='"+p_frame_id+"';";
		ResultSet t_records=Database.execute(t_sql);
		if(!t_records.next())
			return null;
		Frame t_frame=new Frame();
		t_frame.m_id=p_frame_id;
		t_frame.m_modules=t_records.getString("modules");
		t_frame.fillNull();
		return t_frame;
	}
	
	public static void setFrame(Frame p_frame) throws SQLException
	{
		String t_sql="delete from Frame where id='"+p_frame.m_id+"';";
		Database.execute(t_sql);
		StringBuilder t_sb=new StringBuilder("insert into Frame values('");
		t_sb.append(p_frame.m_id).append("','")
			.append(p_frame.m_modules).append("');");
		Database.execute(t_sb.toString());
	}
	
	public static void removeFrame(String p_frame_id) throws SQLException
	{
		String t_sql="delete from Frame where id='"+p_frame_id+"';";
		Database.execute(t_sql);
	}
	
}
