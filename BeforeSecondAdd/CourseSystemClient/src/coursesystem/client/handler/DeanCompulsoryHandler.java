package coursesystem.client.handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import coursesystem.client.event.DeanCompulsedEvent;
import coursesystem.reply.DeanCompulsoryReply;
import coursesystem.request.DeanCompulsoryRequest;
import coursesystem.unit.Course;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class DeanCompulsoryHandler
{

	public static void onHandle(XMLContainer p_gui,String p_dean_id,String p_grade,boolean p_compulsory_uncompulsory,String p_course_id)
	{
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		int t_flag=JOptionPane.showConfirmDialog(t_frame,"确定要"+(p_compulsory_uncompulsory?"指选":"退选")+"该课程？","提示",JOptionPane.YES_NO_OPTION);
		if(t_flag!=JOptionPane.YES_OPTION)
			return;
		DeanCompulsoryRequest t_request=new DeanCompulsoryRequest();
		t_request.m_dean_id=p_dean_id;
		t_request.m_grade=p_grade;
		t_request.m_compulsory_uncompulsory=p_compulsory_uncompulsory;
		t_request.m_course_id=p_course_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		DeanCompulsoryReply t_reply=(DeanCompulsoryReply)SmartEvents.expect(DeanCompulsoryReply.class);
		if(!t_reply.m_done)
		{
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			DeanCompulsedEvent t_event=new DeanCompulsedEvent();
			SmartEvents.happen(t_event,SmartEvents.LOCAL_ONLY);
			SmartGuis.set(SmartGuis.find(p_gui,"last_opt"),"text",(p_compulsory_uncompulsory?"指选课程：":"退选课程：")+Course.getVisibleId(p_course_id));
			SmartGuis.set(SmartGuis.find(p_gui,"course_detail"),"enable","false");
			SmartGuis.set(SmartGuis.find(p_gui,"compulsory_uncompulsory"),"enable","false");
			JOptionPane.showMessageDialog(t_frame,(p_compulsory_uncompulsory?"指选课程":"退选课程")+"成功！","提示",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
}
