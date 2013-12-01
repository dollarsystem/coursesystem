package coursesystem.client.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import coursesystem.reply.StudentCoursesReply;
import coursesystem.request.StudentCoursesRequest;
import coursesystem.unit.Course;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class StudentCoursesHandler
{
	
	private static final String[] WEEKDAYS={"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
	
	public static void onHandle(XMLContainer p_gui,String p_student_id)
	{
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		StudentCoursesRequest t_request=new StudentCoursesRequest();
		t_request.m_student_id=p_student_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		StudentCoursesReply t_reply=(StudentCoursesReply)SmartEvents.expect(StudentCoursesReply.class);
		if(!t_reply.m_done)
		{
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		HashMap<String,ArrayList<String>> t_course_table=new HashMap<String,ArrayList<String>>();
		for(String t_weekday:WEEKDAYS)
			t_course_table.put(t_weekday,new ArrayList<String>());
		for(Course t_course:t_reply.m_courses)
		{
			for(String t_day:t_course.m_classes.split("#"))
			{
				String[] t_day_classes=t_day.split(":");
				String t_weekday=t_day_classes[0];
				ArrayList<String> t_this_day_course=t_course_table.get(t_weekday);
				for(String t_class:t_day_classes[1].split(";"))
					t_this_day_course.add(","+t_class+","+t_course.m_name+","+t_course.m_id);
			}
		}
		XMLContainer t_table=SmartGuis.find(p_gui,"my_class_table");
		List<XMLContainer> t_childs=t_table.getChilds();
		for(int t_i=t_childs.size()-1;t_i>=0;t_i--)
			t_table.remove(t_childs.get(t_i));
		for(String t_weekday:WEEKDAYS)
		{
			try
			{
				ArrayList<String> t_courses_of_weekday=t_course_table.get(t_weekday);
				if(t_courses_of_weekday.size()==0)
					continue;
				XMLContainer t_line_weekday=SmartGuis.create("<Line items='"+t_weekday+"' bgcolor='#ffffff' bgcolors='#cbfef1'/>");
				SmartGuis.add(t_table,t_line_weekday);
				Collections.sort(t_courses_of_weekday);
				for(String t_class:t_courses_of_weekday)
				{
					int t_index=t_class.lastIndexOf(',');
					XMLContainer t_line_class=SmartGuis.create("<Line items='"+t_class.substring(0,t_index)+"' bgcolor='#fafdc1' bgcolors='#ffffff' course_id='"+t_class.substring(t_index+1)+"'/>");
					SmartGuis.add(t_table,t_line_class);
				}
			}
			catch(Exception t_exp)
			{
				t_exp.printStackTrace();
			}
		}
		for(String t_weekday:WEEKDAYS)
		{
			ArrayList<String> t_courses=t_course_table.get(t_weekday);
			for(int t_i=1;t_i<t_courses.size();t_i++)
			{
				String[] t_course_1=t_courses.get(t_i-1).split(",");
				String[] t_course_2=t_courses.get(t_i).split(",");
				if(t_course_1[1].equals(t_course_2[1]))
				{
					String t_warning=t_weekday+t_course_1[1]+"： "+t_course_1[3]+" 与 "+t_course_2[3]+" 课时冲突！";
					JOptionPane.showMessageDialog(t_frame,t_warning,"警告",JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
	
	public static String getSelectedCourseId(XMLContainer p_gui)
	{
		XMLContainer t_my_class_table=SmartGuis.find(p_gui,"my_class_table");
		JTable t_table=(JTable)t_my_class_table;
		int t_selectedrow=t_table.getSelectedRow();
		List<XMLContainer> t_childs=t_my_class_table.getChilds();
		XMLContainer t_line=t_childs.get(t_selectedrow);
		String t_course_id=t_line.get("course_id");
		return t_course_id;
	}

}
