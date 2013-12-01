package coursesystem.server.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import coursesystem.unit.Notice;

class NoticeTable
{

	public static void init() throws SQLException
	{
		Database.execute("create table if not exists Notice(id,sender,receiver_ids,content,date);");
	}

	public static Notice getNotice(String p_notice_id) throws SQLException
	{
		String t_sql="select * from Notice where id='"+p_notice_id+"';";
		ResultSet t_records=Database.execute(t_sql);
		if(!t_records.next())
			return null;
		Notice t_notice=new Notice();
		t_notice.m_id=p_notice_id;
		t_notice.m_sender=t_records.getString("sender");
		t_notice.m_receiver_ids=t_records.getString("receiver_ids");
		t_notice.m_content=t_records.getString("content");
		t_notice.m_date=t_records.getString("date");
		t_notice.fillNull();
		return t_notice;
	}
	
	public static List<Notice> getNoticeTo(String p_type,String p_id) throws SQLException
	{
		String t_sql="select * from Notice where receiver_ids like '%"+p_type+"_"+p_id+"%' or receiver_ids like '%"+p_type+"s%';";
		List<Notice> t_notices=new ArrayList<Notice>();
		ResultSet t_records=Database.execute(t_sql);
		while(t_records.next())
		{
			Notice t_notice=new Notice();
			t_notice.m_id=t_records.getString("id");
			t_notice.m_sender=t_records.getString("sender");
			t_notice.m_receiver_ids=t_records.getString("receiver_ids");
			t_notice.m_content=t_records.getString("content");
			t_notice.m_date=t_records.getString("date");
			t_notice.fillNull();
			t_notices.add(t_notice);
		}
		return t_notices;
	}
	
	public static void setNotice(Notice p_notice) throws SQLException
	{
		String t_sql="delete from Notice where id='"+p_notice.m_id+"';";
		Database.execute(t_sql);
		StringBuilder t_sb=new StringBuilder("insert into Notice values('");
		t_sb.append(p_notice.m_id).append("','")
			.append(p_notice.m_sender).append("','")
			.append(p_notice.m_receiver_ids).append("','")
			.append(p_notice.m_content).append("','")
			.append(p_notice.m_date).append("');");
		Database.execute(t_sb.toString());
	}
	
	public static void removeNotice(String p_notice_id) throws SQLException
	{
		String t_sql="delete from Notice where id='"+p_notice_id+"';";
		Database.execute(t_sql);
	}
	
}
