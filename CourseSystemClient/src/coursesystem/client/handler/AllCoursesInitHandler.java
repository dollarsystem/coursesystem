package coursesystem.client.handler;

import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import coursesystem.reply.AllCoursesInitReply;
import coursesystem.request.AllCoursesInitRequest;
import coursesystem.unit.Faculty;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class AllCoursesInitHandler
{

	@SuppressWarnings("deprecation")
	public static void onHandle(XMLContainer p_gui)
	{
		XMLContainer t_term_combo=SmartGuis.find(p_gui,"term_combo");
		Date t_date=new Date();
		int t_year=t_date.getYear()+1900;
		boolean t_term=t_date.getMonth()>7;
		for(int t_i=0;t_i<8;t_i++)
		{
			try
			{
				String t_text=t_year+"-"+(t_year+1)+(t_term?"第二学期":"第一学期");
				XMLContainer t_option=SmartGuis.create("<Option text=\""+t_text+"\"/>");
				t_term_combo.add(t_option);
				t_term=!t_term;
				if(t_term)
					t_year--;
			} 
			catch(Exception t_exp)
			{
				t_exp.printStackTrace();
			}
		}
		AllCoursesInitRequest t_request=new AllCoursesInitRequest();
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		AllCoursesInitReply t_reply=(AllCoursesInitReply)SmartEvents.expect(AllCoursesInitReply.class);
		if(!t_reply.m_done)
		{
			JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		XMLContainer t_faculty_combo=SmartGuis.find(p_gui,"faculty_combo");
		for(int t_i=0;t_i<t_reply.m_facultys.size();t_i++)
		{
			try
			{
				Faculty t_faculty=t_reply.m_facultys.get(t_i);
				XMLContainer t_option=SmartGuis.create("<Option text=\""+t_faculty.m_name+"\" faculty_id=\""+t_faculty.m_id+"\"/>");
				t_faculty_combo.add(t_option);
			} 
			catch(Exception t_exp)
			{
				t_exp.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static String getSelectedTerm(XMLContainer p_gui)
	{
		XMLContainer t_term_combo=SmartGuis.find(p_gui,"term_combo");
		return ((JComboBox)t_term_combo).getSelectedItem().toString();
	}
	
	@SuppressWarnings("rawtypes")
	public static String getSelectedFacultyId(XMLContainer p_gui)
	{
		XMLContainer t_faculty_combo=SmartGuis.find(p_gui,"faculty_combo");
		return t_faculty_combo.getChilds().get(((JComboBox)t_faculty_combo).getSelectedIndex()).get("faculty_id");
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isThisTerm(XMLContainer p_gui)
	{
		XMLContainer t_term_combo=SmartGuis.find(p_gui,"term_combo");
		return ((JComboBox)t_term_combo).getSelectedIndex()==1;
	}
	
}
