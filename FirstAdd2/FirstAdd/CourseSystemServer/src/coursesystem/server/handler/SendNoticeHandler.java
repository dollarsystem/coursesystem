package coursesystem.server.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import zjs.smartevents.SmartEvents;
import coursesystem.event.NewNoticeEvent;
import coursesystem.reply.SendNoticeReply;
import coursesystem.request.SendNoticeRequest;
import coursesystem.server.database.Database;
import coursesystem.unit.Notice;

public class SendNoticeHandler
{

	public static void onHandle(SendNoticeRequest p_request)
	{
		SendNoticeReply t_reply=new SendNoticeReply();
		try
		{
			Notice t_notice=p_request.m_notice;
			t_notice.m_id=""+System.currentTimeMillis()+Math.round(Math.random()*10000);
			SimpleDateFormat t_format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			t_notice.m_date=t_format.format(new Date());
			Database.setNotice(t_notice);
			t_reply.m_done=true;
		} 
		catch (Exception t_exp)
		{
			t_exp.printStackTrace();
			t_reply.m_done=false;
			t_reply.m_message="服务器故障：\n"+t_exp.getMessage();
		}
		SmartEvents.happen(t_reply,true,p_request.getFriend());
		NewNoticeEvent t_event=new NewNoticeEvent();
		t_event.m_notice=p_request.m_notice;
		SmartEvents.happen(t_event,SmartEvents.NETWORK_ONLY);
	}
	
}
