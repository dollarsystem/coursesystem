package coursesystem.client.handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import coursesystem.client.event.ClassesChangedEvent;
import coursesystem.reply.SelectCourseReply;
import coursesystem.request.SelectCourseRequest;
import coursesystem.unit.Course;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class SelectCourseHandler
{

	public static void onHandle(XMLContainer p_gui,String p_student_id,String p_course_id)
	{
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		int t_flag=JOptionPane.showConfirmDialog(t_frame,"确定要选该课程？","提示",JOptionPane.YES_NO_OPTION);
		if(t_flag!=JOptionPane.YES_OPTION)
			return;
		SelectCourseRequest t_request=new SelectCourseRequest();
		t_request.m_student_id=p_student_id;
		t_request.m_course_id=p_course_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		SelectCourseReply t_reply=(SelectCourseReply)SmartEvents.expect(SelectCourseReply.class);
		if(!t_reply.m_done)
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
		else
		{
			ClassesChangedEvent t_event=new ClassesChangedEvent();
			SmartEvents.happen(t_event,SmartEvents.LOCAL_ONLY);
			SmartGuis.set(SmartGuis.find(p_gui,"last_opt"),"text","选修课程："+Course.getVisibleId(p_course_id));
			JOptionPane.showMessageDialog(t_frame,"选课成功！","提示",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
}
