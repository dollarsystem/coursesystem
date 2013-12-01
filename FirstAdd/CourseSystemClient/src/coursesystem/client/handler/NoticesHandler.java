package coursesystem.client.handler;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import coursesystem.reply.NoticesReply;
import coursesystem.request.NoticesRequest;
import coursesystem.unit.Notice;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class NoticesHandler
{

	public static void onHandle(XMLContainer p_gui,String p_type,String p_id)
	{
		NoticesRequest t_request=new NoticesRequest();
		t_request.m_type=p_type;
		t_request.m_id=p_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		NoticesReply t_reply=(NoticesReply)SmartEvents.expect(NoticesReply.class);
		if(!t_reply.m_done)
		{
			JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		Collections.sort(t_reply.m_notices,new Comparator<Notice>()
		{
			public int compare(Notice t_one,Notice t_two)
			{
				return -t_one.m_date.compareTo(t_two.m_date);
			}
		});
		XMLContainer t_table=SmartGuis.find(p_gui,"notice_table");
		List<XMLContainer> t_childs=t_table.getChilds();
		for(int t_i=t_childs.size()-1;t_i>=0;t_i--)
			t_table.remove(t_childs.get(t_i));
		StringBuilder t_sb=new StringBuilder();
		for(int t_i=0;t_i<t_reply.m_notices.size();t_i++)
		{
			Notice t_notice=t_reply.m_notices.get(t_i);
			t_sb.append("<Line items='")
				.append(t_notice.m_date).append(",")
				.append(Notice.getSenderName(t_notice.m_sender)).append(",")
				.append(t_notice.m_content).append("'")
				.append(" notice_id='").append(t_notice.m_id).append("'")
				.append(" sender_type='").append(Notice.getSenderType(t_notice.m_sender)).append("'")
				.append(" sender_id='").append(Notice.getSenderId(t_notice.m_sender)).append("'");
			if(t_i%2==1)
				t_sb.append(" bgcolor='#fafdc1'/>");
			else
				t_sb.append(" bgcolor='#cbfef1'/>");
			try
			{
				XMLContainer t_line=SmartGuis.create(t_sb.toString());
				SmartGuis.add(t_table,t_line);
			} 
			catch(Exception t_exp)
			{
				t_exp.printStackTrace();
			}
			t_sb.setLength(0);
		}
	}
	
	public static void onHandle(XMLContainer p_gui,String p_type,String p_id,Notice p_newnotice)
	{
		if(!(p_newnotice.m_receiver_ids.contains(p_type+"s")||p_newnotice.m_receiver_ids.contains(p_type+"#"+p_id)))
			return;
		XMLContainer t_table=SmartGuis.find(p_gui,"notice_table");
		Stack<XMLContainer> t_childs_new=new Stack<XMLContainer>();
		List<XMLContainer> t_childs=t_table.getChilds();
		for(int t_i=t_childs.size()-1;t_i>=0;t_i--)
		{
			XMLContainer t_child=t_childs.get(t_i);
			t_table.remove(t_child);
			t_childs_new.push(t_child);
		}
		StringBuilder t_sb=new StringBuilder();
		t_sb.append("<Line items='")
			.append(p_newnotice.m_date).append(",")
			.append(Notice.getSenderName(p_newnotice.m_sender)).append(",")
			.append(p_newnotice.m_content).append("'")
			.append(" notice_id='").append(p_newnotice.m_id).append("'")
			.append(" sender_type='").append(Notice.getSenderType(p_newnotice.m_sender)).append("'")
			.append(" sender_id='").append(Notice.getSenderId(p_newnotice.m_sender)).append("'/>");
		try
		{
			XMLContainer t_line=SmartGuis.create(t_sb.toString());
			t_childs_new.push(t_line);
		} 
		catch(Exception t_exp)
		{
			t_exp.printStackTrace();
		}
		for(int t_i=t_childs_new.size()-1;t_i>=0;t_i--)
		{
			XMLContainer t_child=t_childs_new.pop();
			if(t_i%2==0)
				SmartGuis.set(t_child,"bgcolor","#fafdc1");
			else
				SmartGuis.set(t_child,"bgcolor","#cbfef1");
			SmartGuis.add(t_table,t_child);
		}
	}
	
	public static String getSelectedNoticeId(XMLContainer p_gui)
	{
		XMLContainer t_notice_table=SmartGuis.find(p_gui,"notice_table");
		JTable t_table=(JTable)t_notice_table;
		int t_selectedrow=t_table.getSelectedRow();
		List<XMLContainer> t_childs=t_notice_table.getChilds();
		XMLContainer t_line=t_childs.get(t_selectedrow);
		String t_course_id=t_line.get("notice_id");
		return t_course_id;
	}
	
	public static String getSelectedSenderId(XMLContainer p_gui)
	{
		XMLContainer t_notice_table=SmartGuis.find(p_gui,"notice_table");
		JTable t_table=(JTable)t_notice_table;
		int t_selectedrow=t_table.getSelectedRow();
		List<XMLContainer> t_childs=t_notice_table.getChilds();
		XMLContainer t_line=t_childs.get(t_selectedrow);
		String t_course_id=t_line.get("sender_id");
		return t_course_id;
	}
	
	public static String getSelectedSenderType(XMLContainer p_gui)
	{
		XMLContainer t_notice_table=SmartGuis.find(p_gui,"notice_table");
		JTable t_table=(JTable)t_notice_table;
		int t_selectedrow=t_table.getSelectedRow();
		List<XMLContainer> t_childs=t_notice_table.getChilds();
		XMLContainer t_line=t_childs.get(t_selectedrow);
		String t_course_id=t_line.get("sender_type");
		return t_course_id;
	}
	
}
