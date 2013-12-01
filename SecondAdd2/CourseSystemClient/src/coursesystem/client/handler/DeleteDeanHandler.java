package coursesystem.client.handler;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;
import coursesystem.client.event.ManagerTableDeleteEvent;
import coursesystem.reply.DeleteDeanReply;
import coursesystem.request.DeleteDeanRequest;

/**
 * 
 * @author z w q
 * @added this
 * @added coursesystem.client.handler.[AllStudentInitHandler,AllTeacherInitHandler,AllDeanInitHandler,AllProvestInitHandler]
 * @added coursesystem.client.handler.[ModifyStudentInfoHandler,ModifyTeacherInfoHandler,ModifyDeanInfoHandler,ModifyProvestInfoHandler]
 * @added creat Handler series, delete Handler series
 * @since 2013.11.28
 */

public class DeleteDeanHandler {

	public static void onHandle(XMLContainer p_gui,String p_dean_id) {
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		int t_flag=JOptionPane.showConfirmDialog(t_frame,"确定要删除该院系教务员？","提示",JOptionPane.YES_NO_OPTION);
		if(t_flag!=JOptionPane.YES_OPTION)
			return;
		DeleteDeanRequest t_request=new DeleteDeanRequest();
		t_request.m_dean_id=p_dean_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		DeleteDeanReply t_reply=(DeleteDeanReply)SmartEvents.expect(DeleteDeanReply.class);
		if(!t_reply.m_done)
		{
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			ManagerTableDeleteEvent t_event=new ManagerTableDeleteEvent();
			t_event.m_type=ManagerTableDeleteEvent.DEAN;
			t_event.m_id=p_dean_id;
			SmartEvents.happen(t_event,SmartEvents.LOCAL_ONLY);
			SmartGuis.set(SmartGuis.find(p_gui,"last_opt"),"text","删除院系教务员："+p_dean_id);
			SmartGuis.set(SmartGuis.find(p_gui,"dean_detail"),"enable","false");
			SmartGuis.set(SmartGuis.find(p_gui,"dean_delete"),"enable","false");
		}
		
		XMLContainer t_table=SmartGuis.find(p_gui,"deans_table");
		List<XMLContainer> t_childs=t_table.getChilds();
		List<XMLContainer> t_childs_new=new ArrayList<XMLContainer>();
		for(int t_i=0;t_i<t_childs.size();t_i++)
		{
			if(!t_childs.get(t_i).get("dean_id").equals(p_dean_id))
			{
				t_childs_new.add(t_childs.get(t_i));
			}
		}
		for(int t_i=t_childs.size()-1;t_i>=0;t_i--)
		{
			t_table.remove(t_childs.get(t_i));
		}
		for(int t_i=0;t_i<t_childs_new.size();t_i++)
		{
			SmartGuis.add(t_table, t_childs_new.get(t_i));
			
		}
	}

}
