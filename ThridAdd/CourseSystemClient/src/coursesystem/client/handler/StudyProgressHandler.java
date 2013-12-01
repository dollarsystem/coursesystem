package coursesystem.client.handler;

import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import coursesystem.reply.StudyProgressReply;
import coursesystem.request.StudyProgressRequest;
import coursesystem.unit.Course;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class StudyProgressHandler
{

	public static void onHandle(XMLContainer p_gui,String p_student_id)
	{
		StudyProgressRequest t_request=new StudyProgressRequest();
		t_request.m_student_id=p_student_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		StudyProgressReply t_reply=(StudyProgressReply)SmartEvents.expect(StudyProgressReply.class);
		if(!t_reply.m_done)
		{
			JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		HashMap<String,_Progress> t_progresses_map=new HashMap<String,_Progress>();
		for(String t_items:t_reply.m_plan.m_modules.split(";"))
		{
			int t_index_1=t_items.indexOf(":");
			int t_index_2=t_items.indexOf("#");
			_Progress t_progress=new _Progress();
			String t_type=t_items.substring(0,t_index_1);
			t_progresses_map.put(t_type,t_progress);
			t_progress.m_type=t_type;
			String t_lower=t_items.substring(t_index_1+1,t_index_2);
			t_progress.m_lower=Integer.parseInt(t_lower);
		}
		for(Course t_course:t_reply.m_courses)
		{
			_Progress t_progress=t_progresses_map.get(t_course.m_type);
			if(t_progress!=null)
				t_progress.m_total+=Integer.parseInt(t_course.m_marks);
		}
		StringBuilder t_sb=new StringBuilder();
		t_sb.append("<Panel place='center' layout='grid,").append(t_progresses_map.size()).append(",2'>");
		for(_Progress t_progress:t_progresses_map.values())
		{
			t_sb.append("<Label text='").append(t_progress.m_type).append("'/>");
			t_sb.append("<ProgressBar max='").append(t_progress.m_lower)
				.append("' value='").append(t_progress.m_total)
				.append("' text='").append(t_progress.m_total+"/"+t_progress.m_lower)
				.append("' paint='true'/>");
		}
		t_sb.append("</Panel>");
		XMLContainer t_panel=null;
		try
		{
			t_panel=SmartGuis.create(t_sb.toString());
		}
		catch(Exception t_exp)
		{
			t_exp.printStackTrace();
		}
		XMLContainer t_scroll=SmartGuis.find(p_gui,"progresses");
		SmartGuis.add(t_scroll,t_panel);
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		t_frame.pack();
	}

}

class _Progress
{
	
	public String m_type;
	public int m_lower;
	public int m_total;
	
}
