/**
 * 
 */
package coursesystem.server.handler;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.ProvestDetailReply;
import coursesystem.request.ProvestDetailRequest;
import coursesystem.server.database.Database;

/**
 * @author Rillke
 * @create 20131130
 */
public class ProvestDetailHandler {
	public static void onHandle(ProvestDetailRequest p_request)
	{
		ProvestDetailReply t_reply=new ProvestDetailReply();
		try
		{
			t_reply.m_provest=Database.getProvest(p_request.m_id);
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
