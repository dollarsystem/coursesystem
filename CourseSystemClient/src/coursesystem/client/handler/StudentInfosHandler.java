package coursesystem.client.handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import coursesystem.reply.StudentInfosReply;
import coursesystem.request.StudentInfosRequest;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class StudentInfosHandler
{
	
	public static void onHandle(XMLContainer p_gui,String p_student_id)
	{
		StudentInfosRequest t_request=new StudentInfosRequest();
		t_request.m_student_id=p_student_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		StudentInfosReply t_reply=(StudentInfosReply)SmartEvents.expect(StudentInfosReply.class);
		if(!t_reply.m_done)
		{
			JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		SmartGuis.find(p_gui,"info_id").set("text",t_reply.m_student.m_id);
		SmartGuis.find(p_gui,"info_name").set("text",t_reply.m_student.m_name);
		SmartGuis.find(p_gui,"info_sex").set("text",t_reply.m_student.m_sex);
		XMLContainer t_info_faculty=SmartGuis.find(p_gui,"info_faculty");
		t_info_faculty.set("text",t_reply.m_faculty.m_name);
		t_info_faculty.set("faculty_id",t_reply.m_faculty.m_id);
		SmartGuis.find(p_gui,"info_grade").set("text",t_reply.m_student.m_grade);
		SmartGuis.find(p_gui,"info_mailbox").set("text",t_reply.m_student.m_mailbox);
		SmartGuis.find(p_gui,"info_password").set("text",ChangePasswordHandler.getVisiblePassword(t_reply.m_student.m_password));
	}
	
	public static String getFacultyId(XMLContainer p_gui)
	{
		XMLContainer t_info_faculty=SmartGuis.find(p_gui,"info_faculty");
		return t_info_faculty.get("faculty_id");
	}
	
	public static String getStudentName(XMLContainer p_gui)
	{
		XMLContainer t_info_name=SmartGuis.find(p_gui,"info_name");
		return t_info_name.get("text");
	}

}
