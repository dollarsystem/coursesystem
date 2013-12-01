/**
 * 
 */
package coursesystem.client.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;



import coursesystem.reply.AllTeacherReply;
import coursesystem.request.AllTeacherRequest;
import coursesystem.unit.Teacher;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

/**
 * @author z w q
 * @added this
 * @added coursesystem.client.handler.[AllStudentInitHandler,AllTeacherInitHandler,AllDeanInitHandler,AllProvestInitHandler]
 * @since 2013.11.28
 *
 */
public class AllTeacherHandler {


	public static void onHandle(XMLContainer p_gui) 
	{
		AllTeacherRequest t_request=new AllTeacherRequest();
		t_request.m_faculty_id=AllTeacherInitHandler.getSelectedFacultyId(p_gui);
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		AllTeacherReply t_reply=(AllTeacherReply)SmartEvents.expect(AllTeacherReply.class);
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		if(!t_reply.m_done)
		{
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		XMLContainer t_table=SmartGuis.find(p_gui,"teachers_table");
		List<XMLContainer> t_childs=t_table.getChilds();
		//clear table
		for(int t_i=t_childs.size()-1;t_i>=0;t_i--)
			t_table.remove(t_childs.get(t_i));
		if(t_reply.m_teachers.size()==0)
		{
			JOptionPane.showMessageDialog(t_frame,"暂无记录...","提示",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		StringBuilder t_sb=new StringBuilder();
		ArrayList<String> t_lines=new ArrayList<String>();
		for(int t_i=0;t_i<t_reply.m_teachers.size();t_i++)
		{
			Teacher t_teacher=t_reply.m_teachers.get(t_i);
			t_sb.append("<Line items='")	
			.append(t_teacher.m_id).append(",")
			.append(t_teacher.m_name).append(",")
			.append(t_teacher.m_sex).append(",")
			.append(t_teacher.m_faculty_id).append("'")
			.append(" teacher_id='").append(t_teacher.m_id).append("'");
			t_lines.add(t_sb.toString());
			t_sb.setLength(0);
		}
		Collections.sort(t_lines);
		for(int t_i=0;t_i<t_lines.size();t_i++)
		{
			t_sb.append(t_lines.get(t_i));
			if(t_i%2==1)
				t_sb.append(" bgcolor='#fafdc1'/>");
			else
				t_sb.append(" bgcolor='#cbfef1'/>");
			t_lines.set(t_i,t_sb.toString());
			t_sb.setLength(0);
		}
		for(int t_i=0;t_i<t_lines.size();t_i++)
		{
			try
			{
				XMLContainer t_line_class=SmartGuis.create(t_lines.get(t_i));
				SmartGuis.add(t_table,t_line_class);
			} 
			catch(Exception t_exp)
			{
				t_exp.printStackTrace();
			}
		}
	}
	public static String getSelectedTeacherId(XMLContainer p_gui) 
	{
		XMLContainer t_all_teacher_table=SmartGuis.find(p_gui,"teachers_table");
		String t_teacher_id=t_all_teacher_table.getChilds().get(((JTable)t_all_teacher_table).getSelectedRow()).get("teacher_id");
		return t_teacher_id;
	}
	
}
