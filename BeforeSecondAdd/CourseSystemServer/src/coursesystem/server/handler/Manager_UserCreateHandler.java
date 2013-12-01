/**
 * 
 */
package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.Manager_UserCreateReply;
import coursesystem.request.Manager_UserCreateRequest;

/**
 * @author Rillke
 * @create 20131130
 */
public class Manager_UserCreateHandler {
	public static void onHandle(Manager_UserCreateRequest p_request)
	{
		Manager_UserCreateReply t_reply=new Manager_UserCreateReply();
		try
		{
			if(p_request.m_type==Manager_UserCreateRequest.STUDENT)
			{
				t_reply.m_done=true;
			}
			else if(p_request.m_type==Manager_UserCreateRequest.TEACHER)
			{
				t_reply.m_done=true;
			}
			else if(p_request.m_type==Manager_UserCreateRequest.DEAN)
			{
				t_reply.m_done=true;
			}
			else if(p_request.m_type==Manager_UserCreateRequest.PROVEST)
			{
				t_reply.m_done=true;
			}
			
		}
		catch(IllegalArgumentException t_exp)
		{
			t_reply.m_done=false;
			t_reply.m_message=t_exp.getMessage();
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
