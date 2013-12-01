package coursesystem.client.handler;

import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import coursesystem.reply.SendNoticeReply;
import coursesystem.request.SendNoticeRequest;
import coursesystem.unit.Notice;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class SendNoticeHandler
{

	public static void onHandle(XMLContainer p_gui,String p_type,String p_id,String p_name)
	{
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		int t_flag=JOptionPane.showConfirmDialog(t_frame,"确定要发送该通知吗？","提示",JOptionPane.YES_NO_OPTION);
		if(t_flag!=JOptionPane.YES_OPTION)
			return;
		String t_receiver_ids=((JTextField)SmartGuis.find(p_gui,"receiver_ids")).getText();
		if(t_receiver_ids.equals(""))
		{
			JOptionPane.showMessageDialog(t_frame,"请填写收件人！","错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!formatCheck(t_receiver_ids))
		{
			JOptionPane.showMessageDialog(t_frame,"收件人格式错误！请使用 <类型>#<学号/工号> 表示一个收件人，多个收件人之间用';'隔开。形如'teacher#rentw;student#121250224'...","错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		t_receiver_ids=t_receiver_ids.replaceAll(" ","");
		String t_content=((JTextArea)SmartGuis.find(p_gui,"content")).getText();
		if(t_content.equals(""))
		{
			JOptionPane.showMessageDialog(t_frame,"请填写通知内容！","错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		Notice t_notice=new Notice();
		t_notice.m_sender=p_type+","+p_id+","+p_name;
		t_notice.m_receiver_ids=t_receiver_ids;
		t_notice.m_content=t_content;
		SendNoticeRequest t_request=new SendNoticeRequest();
		t_request.m_notice=t_notice;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		SendNoticeReply t_reply=(SendNoticeReply)SmartEvents.expect(SendNoticeReply.class);
		if(!t_reply.m_done)
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
		else
			JOptionPane.showMessageDialog(t_frame,"发送成功！","提示",JOptionPane.INFORMATION_MESSAGE);
	}
	
	private static boolean formatCheck(String p_receiver_ids)
	{
		HashSet<String> t_types=new HashSet<String>();
		t_types.add("teacher");t_types.add("student");t_types.add("dean");
		t_types.add("provest");t_types.add("manager");
		String[] t_receiver_ids=p_receiver_ids.split(";");
		for(String t_receiver_id:t_receiver_ids)
		{
			if(t_receiver_id.endsWith("s"))
				if(t_types.contains(t_receiver_id.subSequence(0,t_receiver_id.length()-1)))
					continue;
			int t_index=t_receiver_id.indexOf("#");
			if(t_index==-1)
				return false;
			String t_type=t_receiver_id.substring(0,t_index);
			if(!t_types.contains(t_type))
				return false;
			String t_id=t_receiver_id.substring(t_index+1);
			if(t_id.equals(""))
				return false;
		}
		return true;
	}
	
}
