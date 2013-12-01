package coursesystem.client.handler;

import javax.swing.JOptionPane;

import coursesystem.client.event.PlanModuleChangedEvent;
import coursesystem.reply.PlanModuleAddReply;
import coursesystem.request.PlanModuleAddRequest;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class PlanModuleAddHandler
{
	
	public static void onHandle(XMLContainer p_gui,String p_dean_id,String p_course_id,boolean p_add_unadd,String p_type)
	{
		int t_flag=JOptionPane.showConfirmDialog(null,"确定要"+(p_add_unadd?"添加":"删除")+"该课程？","提示",JOptionPane.YES_NO_OPTION);
		if(t_flag!=JOptionPane.YES_OPTION)
			return;
		PlanModuleAddRequest t_request=new PlanModuleAddRequest();
		t_request.m_dean_id=p_dean_id;
		t_request.m_course_id=p_course_id;
		t_request.m_add_unadd=p_add_unadd;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		PlanModuleAddReply t_reply=(PlanModuleAddReply)SmartEvents.expect(PlanModuleAddReply.class);
		if(!t_reply.m_done)
		{
			JOptionPane.showMessageDialog(null,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			PlanModuleChangedEvent t_event=new PlanModuleChangedEvent();
			t_event.m_type=p_type;
			SmartEvents.happen(t_event,SmartEvents.LOCAL_ONLY);
			SmartGuis.set(SmartGuis.find(p_gui,"planmodule_detail"),"enable","false");
			SmartGuis.set(SmartGuis.find(p_gui,"planmodule_add"),"enable","false");
			JOptionPane.showMessageDialog(null,(p_add_unadd?"添加":"删除")+"成功！","提示",JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
