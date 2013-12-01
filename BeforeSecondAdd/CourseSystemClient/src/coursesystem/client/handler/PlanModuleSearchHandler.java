package coursesystem.client.handler;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import coursesystem.reply.PlanModuleSearchReply;
import coursesystem.request.PlanModuleSearchRequest;
import coursesystem.unit.Course;
import coursesystem.unit.Teacher;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.Panel;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class PlanModuleSearchHandler
{

	public static void onHandle(XMLContainer p_gui,String p_dean_id)
	{
		@SuppressWarnings("unchecked")
		JComboBox<String> t_types=(JComboBox<String>)SmartGuis.find(p_gui,"planmodule_types");
		String t_type=t_types.getSelectedItem().toString();
		PlanModuleSearchRequest t_request=new PlanModuleSearchRequest();
		t_request.m_dean_id=p_dean_id;
		t_request.m_type=t_type;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		PlanModuleSearchReply t_reply=(PlanModuleSearchReply)SmartEvents.expect(PlanModuleSearchReply.class);
		if(!t_reply.m_done)
		{
			Panel t_panel=(Panel)SmartGuis.find(p_gui,"root");
			JOptionPane.showMessageDialog(t_panel,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		XMLContainer t_table=SmartGuis.find(p_gui,"planmodule_table");
		List<XMLContainer> t_childs=t_table.getChilds();
		for(int t_i=t_childs.size()-1;t_i>=0;t_i--)
			t_table.remove(t_childs.get(t_i));
		int t_totalmarks=0;
		try
		{
			XMLContainer t_line_added=SmartGuis.create("<Line items='已添加' bgcolor='#ffffff' bgcolors='#cbfef1'/>");
			t_table.add(t_line_added);
			StringBuilder t_sb=new StringBuilder();
			for(int t_i=0;t_i<t_reply.m_added.size();t_i++)
			{
				Course t_course=t_reply.m_added.get(t_i);
				t_totalmarks+=Integer.parseInt(t_course.m_marks);
				Teacher t_teacher=t_reply.m_added_teachers.get(t_i);
				t_sb.append("<Line items=',")
					.append(Course.getVisibleId(t_course.m_id)).append(",")
					.append(t_course.m_name).append(",")
					.append(t_teacher.m_name).append(",")
					.append(t_course.m_marks).append("'")
					.append(" course_id='").append(t_course.m_id).append("'")
					.append(" is_added='true'")
					.append(" bgcolor='#fafdc1' bgcolors='#ffffff'/>");
				XMLContainer t_line_course=SmartGuis.create(t_sb.toString());
				SmartGuis.add(t_table,t_line_course);
				t_sb.setLength(0);
			}
			XMLContainer t_line_unadded=SmartGuis.create("<Line items='未添加' bgcolor='#ffffff' bgcolors='#cbfef1'/>");
			t_table.add(t_line_unadded);
			for(int t_i=0;t_i<t_reply.m_unadded.size();t_i++)
			{
				Course t_course=t_reply.m_unadded.get(t_i);
				Teacher t_teacher=t_reply.m_unadded_teachers.get(t_i);
				t_sb.append("<Line items=',")
					.append(Course.getVisibleId(t_course.m_id)).append(",")
					.append(t_course.m_name).append(",")
					.append(t_teacher.m_name).append(",")
					.append(t_course.m_marks).append("'")
					.append(" course_id='").append(t_course.m_id).append("'")
					.append(" is_added='false'")
					.append(" bgcolor='#fafdc1' bgcolors='#ffffff'/>");
				XMLContainer t_line_course=SmartGuis.create(t_sb.toString());
				SmartGuis.add(t_table,t_line_course);
				t_sb.setLength(0);
			}
			t_table.set("totalmarks",""+t_totalmarks);
		}
		catch(Exception t_exp)
		{
			t_exp.printStackTrace();
		}
	}
	
	public static String getSelectedCourseId(XMLContainer p_gui)
	{
		XMLContainer t_planmodule_table=SmartGuis.find(p_gui,"planmodule_table");
		String t_course_id=t_planmodule_table.getChilds().get(((JTable)t_planmodule_table).getSelectedRow()).get("course_id");
		return t_course_id;
	}
	
	public static boolean isSelectedCourseAdded(XMLContainer p_gui)
	{
		XMLContainer t_planmodule_table=SmartGuis.find(p_gui,"planmodule_table");
		String t_is_added=t_planmodule_table.getChilds().get(((JTable)t_planmodule_table).getSelectedRow()).get("is_added");
		return Boolean.parseBoolean(t_is_added);
	}
	
	public static int getTotalMarks(XMLContainer p_gui)
	{
		XMLContainer t_table=SmartGuis.find(p_gui,"planmodule_table");
		return Integer.parseInt(t_table.get("totalmarks"));
	}
	
}
