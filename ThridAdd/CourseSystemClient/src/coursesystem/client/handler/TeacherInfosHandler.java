package coursesystem.client.handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import coursesystem.reply.TeacherInfosReply;
import coursesystem.request.TeacherInfosRequest;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class TeacherInfosHandler 
{

	public static void onHandle(XMLContainer p_gui,String p_teacher_id)
	{
		TeacherInfosRequest t_request=new TeacherInfosRequest();
		t_request.m_teacher_id=p_teacher_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		TeacherInfosReply t_reply=(TeacherInfosReply)SmartEvents.expect(TeacherInfosReply.class);
		if(!t_reply.m_done)
		{
			JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		SmartGuis.find(p_gui,"info_id").set("text",t_reply.m_teacher.m_id);
		SmartGuis.find(p_gui,"info_name").set("text",t_reply.m_teacher.m_name);
		SmartGuis.find(p_gui,"info_sex").set("text",t_reply.m_teacher.m_sex);
		XMLContainer t_info_faculty=SmartGuis.find(p_gui,"info_faculty");
		t_info_faculty.set("text",t_reply.m_faculty.m_name);
		t_info_faculty.set("faculty_id",t_reply.m_faculty.m_id);
		SmartGuis.find(p_gui,"info_mailbox").set("text",t_reply.m_teacher.m_mailbox);
		SmartGuis.find(p_gui,"info_password").set("text",ChangePasswordHandler.getVisiblePassword(t_reply.m_teacher.m_password));
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
