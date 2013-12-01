package coursesystem.client.handler;

import coursesystem.reply.CourseDetailReply;
import coursesystem.request.CourseDetailRequest;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class CourseDetailHandler
{

	public static void onHandle(XMLContainer p_gui,String p_course_id)
	{
		CourseDetailRequest t_request=new CourseDetailRequest();
		t_request.m_course_id=p_course_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		CourseDetailReply t_reply=(CourseDetailReply)SmartEvents.expect(CourseDetailReply.class);
		if(!t_reply.m_done)
		{
			JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		int t_index=t_reply.m_course.m_id.lastIndexOf('_');
		if(t_index!=-1)
			t_reply.m_course.m_id=t_reply.m_course.m_id.substring(0,t_index);
		SmartGuis.find(p_gui,"id").set("text",t_reply.m_course.m_id);
		SmartGuis.find(p_gui,"name").set("text",t_reply.m_course.m_name);
		SmartGuis.find(p_gui,"type").set("text",t_reply.m_course.m_type);
		SmartGuis.find(p_gui,"marks").set("text",t_reply.m_course.m_marks);
		SmartGuis.find(p_gui,"faculty").set("text",t_reply.m_faculty.m_name);
		SmartGuis.find(p_gui,"teacher").set("text",t_reply.m_teacher.m_name);
		SmartGuis.find(p_gui,"students").set("text",""+t_reply.m_course.m_student_ids.split(",").length);
		SmartGuis.find(p_gui,"description").set("text",t_reply.m_course.m_description);
		XMLContainer t_frame=SmartGuis.find(p_gui,"frame");
		((JFrame)t_frame).pack();
		XMLContainer t_textarea=SmartGuis.find(p_gui,"description");
		((JTextArea)t_textarea).setEditable(false);
	}
	
}
