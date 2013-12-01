package coursesystem.client.handler;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;
import coursesystem.reply.AllTeacherInitReply;
import coursesystem.request.AllTeacherInitRequest;
import coursesystem.unit.Faculty;

/**
 * @author z w q
 * @added this
 * @added coursesystem.client.handler.[AllStudentInitHandler,AllTeacherInitHandler,AllDeanInitHandler,AllProvestInitHandler]
 * @since 2013.11.29
 *
 */

public class AllTeacherInitHandler {
	public static void onHandle(XMLContainer p_gui) {
		
		AllTeacherInitRequest t_request=new AllTeacherInitRequest();
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		AllTeacherInitReply t_reply=(AllTeacherInitReply)SmartEvents.expect(AllTeacherInitReply.class);
		if(!t_reply.m_done)
		{
			JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		XMLContainer t_teacher_faculty_combo=SmartGuis.find(p_gui,"teacher_faculty_combo");
		for(int t_i=0;t_i<t_reply.m_facultys.size();t_i++)
		{
			try
			{
				Faculty t_faculty=t_reply.m_facultys.get(t_i);
				XMLContainer t_option=SmartGuis.create("<Option text=\""+t_faculty.m_name+"\" faculty_id=\""+t_faculty.m_id+"\"/>");
				t_teacher_faculty_combo.add(t_option);
			} 
			catch(Exception t_exp)
			{
				t_exp.printStackTrace();
			}
		}		
	}
	@SuppressWarnings("rawtypes")
	public static String getSelectedFacultyId(XMLContainer p_gui)
	{
		XMLContainer t_teacher_faculty_combo=SmartGuis.find(p_gui,"teacher_faculty_combo");
		return t_teacher_faculty_combo.getChilds().get(((JComboBox)t_teacher_faculty_combo).getSelectedIndex()).get("faculty_id");
	}

}
