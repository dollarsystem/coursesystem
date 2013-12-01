package coursesystem.server.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import coursesystem.unit.Notice;

class NoticeTable
{

	public static void init() throws SQLException
	{
		Database.execute("create table if not exists Notice(id,student_ids,teacher_ids,dean_ids,provest_ids,manager_ids,content,date);");
	}

	public static Notice getNotice(String p_notice_id) throws SQLException
	{
		String t_sql="select * from Notice where id='"+p_notice_id+"';";
		ResultSet t_records=Database.execute(t_sql);
		if(!t_records.next())
			return null;
		Notice t_notice=new Notice();
		t_notice.m_id=p_notice_id;
		t_notice.m_student_ids=t_records.getString("student_ids");
		t_notice.m_teacher_ids=t_records.getString("teacher_ids");
		t_notice.m_dean_ids=t_records.getString("dean_ids");
		t_notice.m_provest_ids=t_records.getString("provest_ids");
		t_notice.m_manager_ids=t_records.getString("manager_ids");
		t_notice.m_content=t_records.getString("content");
		t_notice.m_date=t_records.getString("date");
		return t_notice;
	}
	
	public static void setNotice(Notice p_notice) throws SQLException
	{
		String t_sql="delete from Notice where id='"+p_notice.m_id+"';";
		Database.execute(t_sql);
		StringBuilder t_sb=new StringBuilder("insert into Notice values('");
		t_sb.append(p_notice.m_id).append("','")
			.append(p_notice.m_student_ids).append("','")
			.append(p_notice.m_teacher_ids).append("','")
			.append(p_notice.m_dean_ids).append("','")
			.append(p_notice.m_provest_ids).append("','")
			.append(p_notice.m_manager_ids).append("','")
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
