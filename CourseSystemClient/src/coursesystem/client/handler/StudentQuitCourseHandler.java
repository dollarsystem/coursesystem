package coursesystem.client.handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import coursesystem.client.event.StudentQuitCourseEvent;
import coursesystem.reply.StudentQuitCourseReply;
import coursesystem.request.StudentQuitCourseRequest;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class StudentQuitCourseHandler
{

	public static void onHandle(XMLContainer p_gui,String p_student_id,String p_course_id)
	{
		StudentQuitCourseRequest t_request=new StudentQuitCourseRequest();
		t_request.m_student_id=p_student_id;
		t_request.m_course_id=p_course_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		StudentQuitCourseReply t_reply=(StudentQuitCourseReply)SmartEvents.expect(StudentQuitCourseReply.class);
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		if(!t_reply.m_done)
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
		else
		{
			StudentQuitCourseEvent t_event=new StudentQuitCourseEvent();
			SmartEvents.happen(t_event,SmartEvents.LOCAL_ONLY);
			SmartGuis.find(p_gui,"last_opt").set("text","退选课程"+p_course_id);
			JOptionPane.showMessageDialog(t_frame,"课程退选成功！","提示",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
}
