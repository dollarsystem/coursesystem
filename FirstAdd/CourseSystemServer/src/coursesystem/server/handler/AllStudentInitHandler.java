/**
 * 
 */
package coursesystem.server.handler;

import java.util.List;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.AllStudentInitReply;
import coursesystem.request.AllStudentInitRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Faculty;

/**
 * @author Rillke
 * @create 20131130
 */
public class AllStudentInitHandler {
	public static void onHandle(AllStudentInitRequest p_request)
	{
		AllStudentInitReply t_reply=new AllStudentInitReply();
		try
		{
			List<Faculty> t_facultys=Database.getAllFacultys();
			t_reply.m_facultys=t_facultys;
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
