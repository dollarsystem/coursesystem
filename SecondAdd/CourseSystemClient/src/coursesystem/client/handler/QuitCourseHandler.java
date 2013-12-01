package coursesystem.client.handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import coursesystem.client.event.ClassesChangedEvent;
import coursesystem.reply.QuitCourseReply;
import coursesystem.request.QuitCourseRequest;
import coursesystem.unit.Course;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class QuitCourseHandler
{

	public static void onHandle(XMLContainer p_gui,String p_student_id,String p_course_id)
	{
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		int t_flag=JOptionPane.showConfirmDialog(t_frame,"确定要退选该课程？","提示",JOptionPane.YES_NO_OPTION);
		if(t_flag!=JOptionPane.YES_OPTION)
			return;
		QuitCourseRequest t_request=new QuitCourseRequest();
		t_request.m_student_id=p_student_id;
		t_request.m_course_id=p_course_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		QuitCourseReply t_reply=(QuitCourseReply)SmartEvents.expect(QuitCourseReply.class);
		if(!t_reply.m_done)
		{
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			ClassesChangedEvent t_event=new ClassesChangedEvent();
			SmartEvents.happen(t_event,SmartEvents.LOCAL_ONLY);
			SmartGuis.set(SmartGuis.find(p_gui,"last_opt"),"text","退选课程："+Course.getVisibleId(p_course_id));
			SmartGuis.set(SmartGuis.find(p_gui,"my_class_detail"),"enable","false");
			SmartGuis.set(SmartGuis.find(p_gui,"my_class_quit"),"enable","false");
			JOptionPane.showMessageDialog(t_frame,"课程退选成功！","提示",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
}
