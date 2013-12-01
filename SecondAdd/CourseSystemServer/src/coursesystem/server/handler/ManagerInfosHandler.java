/**
 * 
 */
package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.ManagerInfosReply;
import coursesystem.request.ManagerInfosRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Manager;

/**
 * @author Rillke
 * @create 20131201
 */
public class ManagerInfosHandler {
	public static void onHandle(ManagerInfosRequest p_request)
	{
		ManagerInfosReply t_reply=new ManagerInfosReply();
		try
		{
			Manager t_manager=Database.getManager(p_request.m_manager_id);
			t_reply.m_manager=t_manager;
			t_reply.m_done=true;
		}
		catch(Exception t_exp)
		{
			t_exp.printStackTrace();
			t_reply.m_done=false;
			t_reply.m_message="服务器故障：\n"+t_exp.getMessage();
		}
		SmartEvents.happen(t_reply,true,p_request.getFriend());
	}
}
