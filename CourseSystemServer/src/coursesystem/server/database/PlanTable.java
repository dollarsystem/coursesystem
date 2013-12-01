package coursesystem.server.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import coursesystem.unit.Plan;

class PlanTable
{

	public static void init() throws SQLException
	{
		Database.execute("create table if not exists Plan(id,faculty_id,modules);");
	}

	public static Plan getPlan(String p_plan_id) throws SQLException
	{
		String t_sql="select * from Plan where id='"+p_plan_id+"';";
		ResultSet t_records=Database.execute(t_sql);
		if(!t_records.next())
			return null;
		Plan t_plan=new Plan();
		t_plan.m_id=p_plan_id;
		t_plan.m_faculty_id=t_records.getString("faculty_id");
		t_plan.m_modules=t_records.getString("modules");
		return t_plan;
	}
	
	public static void setPlan(Plan p_plan) throws SQLException
	{
		String t_sql="delete from Plan where id='"+p_plan.m_id+"';";
		Database.execute(t_sql);
		StringBuilder t_sb=new StringBuilder("insert into Plan values('");
		t_sb.append(p_plan.m_id).append("','")
			.append(p_plan.m_faculty_id).append("','")
			.append(p_plan.m_modules).append("');");
		Database.execute(t_sb.toString());
	}
	
	public static void removePlan(String p_plan_id) throws SQLException
	{
		String t_sql="delete from Plan where id='"+p_plan_id+"';";
		Database.execute(t_sql);
	}
	
}
