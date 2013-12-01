package coursesystem.client.handler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import coursesystem.reply.StudentScoresReply;
import coursesystem.request.StudentScoresRequest;
import coursesystem.unit.Course;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class StudentScoresHandler
{

	public static void onHandle(XMLContainer p_gui,String p_student_id)
	{
		StudentScoresRequest t_request=new StudentScoresRequest();
		t_request.m_student_id=p_student_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		StudentScoresReply t_reply=(StudentScoresReply)SmartEvents.expect(StudentScoresReply.class);
		if(!t_reply.m_done)
		{
			JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(t_reply.m_courses.size()==0)
			return;
		String[] t_ids=t_reply.m_student.m_history_course_ids.split(",");
		ArrayList<String> t_course_ids=new ArrayList<String>();
		for(int t_i=0;t_i<t_ids.length;t_i++)
			t_course_ids.add(t_ids[t_i]);
		String[] t_scores=t_reply.m_student.m_history_course_scores.split(",");
		ArrayList<String> t_courses=new ArrayList<String>();
		StringBuilder t_sb=new StringBuilder();
		int t_sum_marks=0;
		int t_sum_scores=0;
		for(Course t_course:t_reply.m_courses)
		{
			String t_score=t_scores[t_course_ids.indexOf(t_course.m_id)];
			t_sb.append(t_course.m_term).append(",")
				.append(t_course.m_id).append(",")
				.append(t_course.m_name).append(",")
				.append(t_course.m_type).append(",")
				.append(t_course.m_marks).append(",")
				.append(t_score);
			t_courses.add(t_sb.toString());
			t_sb.setLength(0);
			int t_mark=Integer.parseInt(t_course.m_marks);
			t_sum_marks+=t_mark;
			t_sum_scores+=Integer.parseInt(t_score)*t_mark;
		}
		float t_score=(float)t_sum_scores/t_sum_marks/20;
		SmartGuis.set(SmartGuis.find(p_gui,"my_scores_marks"),"text","总学分："+t_sum_marks+"          学分绩："+new DecimalFormat("#.00").format(t_score));
		Collections.sort(t_courses);
		Collections.reverse(t_courses);
		XMLContainer t_table=SmartGuis.find(p_gui,"my_scores_table");
		String t_now_term=null;
		for(int t_i=0;t_i<t_courses.size();t_i++)
		{
			String t_course_info=t_courses.get(t_i);
			String[] t_course_infos=t_courses.get(t_i).split(",");
			String t_term=t_course_infos[0];
			String t_id=t_course_infos[1];
			String t_items=t_course_info.substring(t_term.length()+t_id.length()+1);
			if(!t_term.equals(t_now_term))
			{
				t_now_term=t_term;
				try
				{
					XMLContainer t_line_now_term=SmartGuis.create("<Line items='"+t_now_term+"'  bgcolors='#cbfef1'/>");
					SmartGuis.add(t_table,t_line_now_term);
				} 
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
			try
			{
				XMLContainer t_line_course=SmartGuis.create("<Line items='"+t_items+"' bgcolor='#fafdc1' bgcolors='#00ffffff' course_id='"+t_id+"'/>");
				SmartGuis.add(t_table,t_line_course);
			} 
			catch(Exception t_exp)
			{
				t_exp.printStackTrace();
			}
		}
	}
	
	public static String getSelectedCourseId(XMLContainer p_gui)
	{
		XMLContainer t_my_scores_table=SmartGuis.find(p_gui,"my_scores_table");
		String t_course_id=t_my_scores_table.getChilds().get(((JTable)t_my_scores_table).getSelectedRow()).get("course_id");
		return t_course_id;
	}
}