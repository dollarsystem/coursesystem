package coursesystem.client.handler;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import coursesystem.reply.AllStudentInitReply;
import coursesystem.request.AllStudentInitRequest;
import coursesystem.unit.Faculty;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

/**
 * @author z w q
 * @added this
 * @added coursesystem.client.handler.[AllStudentInitHandler,AllTeacherInitHandler,AllDeanInitHandler,AllProvestInitHandler]
 * @since 2013.11.29
 *
 */

public class AllStudentInitHandler 
{

	public static void onHandle(XMLContainer p_gui) {
		
		AllStudentInitRequest t_request=new AllStudentInitRequest();
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		AllStudentInitReply t_reply=(AllStudentInitReply)SmartEvents.expect(AllStudentInitReply.class);
		if(!t_reply.m_done)
		{
			JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		XMLContainer t_student_grade_combo=SmartGuis.find(p_gui,"student_grade_combo");
		XMLContainer t_student_faculty_combo=SmartGuis.find(p_gui,"student_faculty_combo");
		for(int t_i=0;t_i<t_reply.m_facultys.size();t_i++)
		{
			try
			{
				Faculty t_faculty=t_reply.m_facultys.get(t_i);
				XMLContainer t_option=SmartGuis.create("<Option text=\""+t_faculty.m_name+"\" faculty_id=\""+t_faculty.m_id+"\"/>");
				t_student_faculty_combo.add(t_option);
			} 
			catch(Exception t_exp)
			{
				t_exp.printStackTrace();
			}
		}
		try
		{
			XMLContainer t_option=SmartGuis.create("<Option text=\"大一\" grade_id=\"1\"/>");
			t_student_grade_combo.add(t_option);
			t_option=SmartGuis.create("<Option text=\"大二\" grade_id=\"2\"/>");
			t_student_grade_combo.add(t_option);
			t_option=SmartGuis.create("<Option text=\"大三\" grade_id=\"3\"/>");
			t_student_grade_combo.add(t_option);
			t_option=SmartGuis.create("<Option text=\"大四\" grade_id=\"4\"/>");
			t_student_grade_combo.add(t_option);
		}
		catch(Exception t_exp)
		{
			t_exp.printStackTrace();
		}
		
	}
	@SuppressWarnings("rawtypes")
	public static String getSelectedFacultyId(XMLContainer p_gui)
	{
		XMLContainer t_student_faculty_combo=SmartGuis.find(p_gui,"student_faculty_combo");
		return t_student_faculty_combo.getChilds().get(((JComboBox)t_student_faculty_combo).getSelectedIndex()).get("faculty_id");
	}
	@SuppressWarnings("rawtypes")
	public static String getSelectedGrade(XMLContainer p_gui)
	{
		XMLContainer t_student_grade_combo=SmartGuis.find(p_gui,"student_grade_combo");
		return ((JComboBox)t_student_grade_combo).getSelectedItem().toString();
	}

}
