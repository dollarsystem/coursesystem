package coursesystem.client.handler;

import java.util.List;

import coursesystem.reply.AllCoursesReply;
import coursesystem.request.AllCoursesRequest;
import coursesystem.unit.Course;
import coursesystem.unit.Faculty;
import coursesystem.unit.Teacher;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class AllCoursesHandler
{

	public static void onHandle(XMLContainer p_gui)
	{
		AllCoursesRequest t_request=new AllCoursesRequest();
		t_request.m_term=AllCoursesInitHandler.getSelectedTerm(p_gui);
		t_request.m_faculty_id=AllCoursesInitHandler.getSelectedFacultyId(p_gui);
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		AllCoursesReply t_reply=(AllCoursesReply)SmartEvents.expect(AllCoursesReply.class);
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		if(!t_reply.m_done)
		{
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		XMLContainer t_table=SmartGuis.find(p_gui,"all_courses_table");
		List<XMLContainer> t_childs=t_table.getChilds();
		for(int t_i=t_childs.size()-1;t_i>=0;t_i--)
			t_table.remove(t_childs.get(t_i));
		if(t_reply.m_courses.size()==0)
		{
			JOptionPane.showMessageDialog(t_frame,"暂无记录...","提示",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		StringBuilder t_sb=new StringBuilder();
		for(int t_i=0;t_i<t_reply.m_courses.size();t_i++)
		{
			Course t_course=t_reply.m_courses.get(t_i);
			Teacher t_teacher=t_reply.m_teachers.get(t_i);
			Faculty t_faculty=t_reply.m_facultys.get(t_i);
			t_sb.append("<Line items='")
				.append(Course.getVisibleId(t_course.m_id)).append(",")
				.append(t_course.m_name).append(",")
				.append(t_teacher.m_name).append(",")
				.append(t_course.m_type).append(",")
				.append(t_faculty.m_name).append(",")
				.append(t_course.m_marks).append("'")
				.append(" course_id='").append(t_course.m_id).append("'");
			if(t_i%2==1)
				t_sb.append(" bgcolor='#fafdc1'/>");
			else
				t_sb.append(" bgcolor='#cbfef1'/>");
			try
			{
				XMLContainer t_line_class=SmartGuis.create(t_sb.toString());
				SmartGuis.add(t_table,t_line_class);
			} 
			catch(Exception t_exp)
			{
				t_exp.printStackTrace();
			}
			t_sb.setLength(0);
		}
	}
	
	public static String getSelectedCourseId(XMLContainer p_gui)
	{
		XMLContainer t_all_courses_table=SmartGuis.find(p_gui,"all_courses_table");
		String t_course_id=t_all_courses_table.getChilds().get(((JTable)t_all_courses_table).getSelectedRow()).get("course_id");
		return t_course_id;
	}
	
}
