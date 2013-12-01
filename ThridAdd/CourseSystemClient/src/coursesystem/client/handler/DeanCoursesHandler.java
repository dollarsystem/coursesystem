package coursesystem.client.handler;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;
import coursesystem.reply.DeanCoursesReply;
import coursesystem.request.DeanCoursesRequest;
import coursesystem.unit.Course;
import coursesystem.unit.Teacher;

public class DeanCoursesHandler
{

	@SuppressWarnings("rawtypes")
	public static void onHandle(XMLContainer p_gui,String p_dean_id)
	{
		XMLContainer t_grade_combo=SmartGuis.find(p_gui,"grade_combo");
		String t_grade=t_grade_combo.getChilds().get(((JComboBox)t_grade_combo).getSelectedIndex()).get("text");
		DeanCoursesRequest t_request=new DeanCoursesRequest();
		t_request.m_dean_id=p_dean_id;
		t_request.m_grade=t_grade;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		DeanCoursesReply t_reply=(DeanCoursesReply)SmartEvents.expect(DeanCoursesReply.class);
		if(!t_reply.m_done)
		{
			JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		XMLContainer t_table=SmartGuis.find(p_gui,"compulsory_table");
		List<XMLContainer> t_childs=t_table.getChilds();
		for(int t_i=t_childs.size()-1;t_i>=0;t_i--)
			t_table.remove(t_childs.get(t_i));
		try
		{
			XMLContainer t_line_compulsory=SmartGuis.create("<Line items='已指选' bgcolor='#ffffff' bgcolors='#cbfef1'/>");
			t_table.add(t_line_compulsory);
			StringBuilder t_sb=new StringBuilder();
			for(int t_i=0;t_i<t_reply.m_compuled_courses.size();t_i++)
			{
				Course t_course=t_reply.m_compuled_courses.get(t_i);
				Teacher t_teacher=t_reply.m_compuled_course_teachers.get(t_i);
				t_sb.append("<Line items=',")
					.append(Course.getVisibleId(t_course.m_id)).append(",")
					.append(t_course.m_name).append(",")
					.append(t_teacher.m_name).append(",")
					.append(t_course.m_type).append(",")
					.append(t_course.m_marks).append("'")
					.append(" course_id='").append(t_course.m_id).append("'")
					.append(" is_compuled='true'")
					.append(" bgcolor='#fafdc1' bgcolors='#ffffff'/>");
				XMLContainer t_line_course=SmartGuis.create(t_sb.toString());
				SmartGuis.add(t_table,t_line_course);
				t_sb.setLength(0);
			}
			XMLContainer t_line_uncompulsory=SmartGuis.create("<Line items='未指选' bgcolor='#ffffff' bgcolors='#cbfef1'/>");
			t_table.add(t_line_uncompulsory);
			for(int t_i=0;t_i<t_reply.m_uncompuled_courses.size();t_i++)
			{
				Course t_course=t_reply.m_uncompuled_courses.get(t_i);
				Teacher t_teacher=t_reply.m_uncompuled_course_teachers.get(t_i);
				t_sb.append("<Line items=',")
					.append(Course.getVisibleId(t_course.m_id)).append(",")
					.append(t_course.m_name).append(",")
					.append(t_teacher.m_name).append(",")
					.append(t_course.m_type).append(",")
					.append(t_course.m_marks).append("'")
					.append(" course_id='").append(t_course.m_id).append("'")
					.append(" is_compuled='false'")
					.append(" bgcolor='#fafdc1'  bgcolors='#ffffff'/>");
				XMLContainer t_line_course=SmartGuis.create(t_sb.toString());
				SmartGuis.add(t_table,t_line_course);
				t_sb.setLength(0);
			}
		}
		catch(Exception t_exp)
		{
			t_exp.printStackTrace();
		}
	}
	
	public static String getSelectedCourseId(XMLContainer p_gui)
	{
		XMLContainer t_compulsory_table=SmartGuis.find(p_gui,"compulsory_table");
		String t_course_id=t_compulsory_table.getChilds().get(((JTable)t_compulsory_table).getSelectedRow()).get("course_id");
		return t_course_id;
	}
	
	public static boolean isSelectedCourseCompuled(XMLContainer p_gui)
	{
		XMLContainer t_compulsory_table=SmartGuis.find(p_gui,"compulsory_table");
		String t_is_compuled=t_compulsory_table.getChilds().get(((JTable)t_compulsory_table).getSelectedRow()).get("is_compuled");
		return Boolean.parseBoolean(t_is_compuled);
	}
	
}
