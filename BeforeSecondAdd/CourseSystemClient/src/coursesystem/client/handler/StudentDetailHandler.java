/**
 * 
 */
package coursesystem.client.handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import coursesystem.reply.StudentDetailReply;
import coursesystem.request.StudentDetailRequest;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

/**
 * @author Rillke
 * @create 20131130
 */
public class StudentDetailHandler {

	public static void onHandle(XMLContainer p_gui, String p_id) {
		StudentDetailRequest t_request=new StudentDetailRequest();
		t_request.m_id=p_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		StudentDetailReply t_reply=(StudentDetailReply)SmartEvents.expect(StudentDetailReply.class);
		if(!t_reply.m_done)
		{
			JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		SmartGuis.find(p_gui,"info_id").set("text",t_reply.m_student.m_id);
		SmartGuis.find(p_gui,"info_name").set("text",t_reply.m_student.m_name);
		SmartGuis.find(p_gui,"info_sex").set("text",t_reply.m_student.m_sex);
		SmartGuis.find(p_gui,"info_faculty").set("text",t_reply.m_student.m_faculty_id);
		SmartGuis.find(p_gui,"info_grade").set("text",t_reply.m_student.m_grade);
		SmartGuis.find(p_gui,"info_mailbox").set("text",t_reply.m_student.m_mailbox);
		SmartGuis.find(p_gui,"info_password").set("text",t_reply.m_student.m_password);
		
		XMLContainer t_frame=SmartGuis.find(p_gui,"frame");
		((JFrame)t_frame).pack();
	}

}
