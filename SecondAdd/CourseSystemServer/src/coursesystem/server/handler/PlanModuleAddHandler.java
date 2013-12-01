package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.PlanModuleAddReply;
import coursesystem.request.PlanModuleAddRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Course;
import coursesystem.unit.Dean;
import coursesystem.unit.Plan;

public class PlanModuleAddHandler
{

	public static void onHandle(PlanModuleAddRequest p_request)
	{
		PlanModuleAddReply t_reply=new PlanModuleAddReply();
		try
		{
			Dean t_dean=Database.getDean(p_request.m_dean_id);
			Plan t_plan=Database.getPlan(t_dean.m_faculty_id);
			Course t_course=Database.getCourse(p_request.m_course_id);
			StringBuilder t_sb=new StringBuilder();
			if(p_request.m_add_unadd)
			{
				boolean t_found=false;
				for(String t_module:t_plan.m_modules.split(";"))
				{
					int t_index_1=t_module.indexOf(":");
					String t_type=t_module.substring(0,t_index_1);
					if(t_type.equals(t_course.m_type))
					{
						int t_index_2=t_module.indexOf("#");
						int t_marks=Integer.parseInt(t_module.substring(t_index_1+1,t_index_2))
									+Integer.parseInt(t_course.m_marks);
						String t_courselist=t_module.substring(t_index_2+1);
						t_sb.append(t_type).append(":").append(t_marks).append("#").append(t_courselist)
							.append(t_course.m_id).append(",").append(";");
						t_found=true;
					}
					else
						t_sb.append(t_module).append(";");
				}
				if(!t_found)
					t_sb.append(t_course.m_type).append(":").append(t_course.m_marks).append("#")
						.append(t_course.m_id).append(",;");
			}
			else
			{
				for(String t_module:t_plan.m_modules.split(";"))
				{
					int t_index_1=t_module.indexOf(":");
					String t_type=t_module.substring(0,t_index_1);
					if(t_type.equals(t_course.m_type))
					{
						int t_index_2=t_module.indexOf("#");
						int t_marks=Integer.parseInt(t_module.substring(t_index_1+1,t_index_2))
									-Integer.parseInt(t_course.m_marks);
						if(t_marks>0)
						{
							String t_courselist=t_module.substring(t_index_2+1);
							t_courselist=t_courselist.replace(t_course.m_id+",","");
							t_sb.append(t_type).append(":").append(t_marks).append("#").append(t_courselist).append(";");
						}
					}
					else
						t_sb.append(t_module).append(";");
				}
			}
			t_plan.m_modules=t_sb.toString();
			Database.setPlan(t_plan);
			t_reply.m_done=true;
		} 
		catch (Exception t_exp)
		{
			t_exp.printStackTrace();
			t_reply.m_done=false;
			t_reply.m_message="服务器故障：\n"+t_exp.getMessage();
		}
		SmartEvents.happen(t_reply,true,p_request.getFriend());
	}
	
}
