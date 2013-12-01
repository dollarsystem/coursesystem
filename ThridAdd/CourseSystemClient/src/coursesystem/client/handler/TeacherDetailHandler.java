package coursesystem.client.handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import coursesystem.reply.TeacherDetailReply;
import coursesystem.request.TeacherDetailRequest;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class TeacherDetailHandler
{

	public static void onHandle(XMLContainer p_gui,String p_teacher_id)
	{
		TeacherDetailRequest t_request=new TeacherDetailRequest();
		t_request.m_teacher_id=p_teacher_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		TeacherDetailReply t_reply=(TeacherDetailReply)SmartEvents.expect(TeacherDetailReply.class);
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		if(!t_reply.m_done)
		{
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		SmartGuis.find(p_gui,"id").set("text",t_reply.m_teacher.m_id);
		SmartGuis.find(p_gui,"name").set("text",t_reply.m_teacher.m_name);
		SmartGuis.find(p_gui,"sex").set("text",t_reply.m_teacher.m_sex);
		XMLContainer t_faculty=SmartGuis.find(p_gui,"faculty");
		t_faculty.set("text",t_reply.m_faculty.m_name);
		t_faculty.set("faculty_id",t_reply.m_faculty.m_id);
		SmartGuis.find(p_gui,"mailbox").set("text",t_reply.m_teacher.m_mailbox);
		t_frame.pack();
	}
	
}
