package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.ModifyDescriptionReply;
import coursesystem.request.ModifyDescriptionRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Course;

public class ModifyDescriptionHandler
{

	public static void onHandle(ModifyDescriptionRequest p_request)
	{
		ModifyDescriptionReply t_reply=new ModifyDescriptionReply();
		try
		{
			Course t_course=Database.getCourse(p_request.m_course_id);
			t_course.m_description=p_request.m_description;
			Database.setCourse(t_course);
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
