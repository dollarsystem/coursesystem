package coursesystem.client.handler;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import coursesystem.reply.FacultyDetailReply;
import coursesystem.request.FacultyDetailRequest;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class FacultyDetailHandler
{
	
	public static void onHandle(XMLContainer p_gui,String p_faculty_id)
	{
		FacultyDetailRequest t_request=new FacultyDetailRequest();
		t_request.m_faculty_id=p_faculty_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		FacultyDetailReply t_reply=(FacultyDetailReply)SmartEvents.expect(FacultyDetailReply.class);
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		if(!t_reply.m_done)
		{
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		SmartGuis.find(p_gui,"id").set("text",t_reply.m_faculty.m_id);
		SmartGuis.find(p_gui,"name").set("text",t_reply.m_faculty.m_name);
		SmartGuis.find(p_gui,"description").set("text",t_reply.m_faculty.m_description);
		t_frame.pack();
	}

}
