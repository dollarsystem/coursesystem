package coursesystem.client.handler;

import coursesystem.client.component.PlanModuleComponent;
import coursesystem.reply.PlanInitReply;
import coursesystem.request.PlanInitRequest;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;
import javax.swing.JTabbedPane;

public class PlanInitHandler
{

	public static void onHandle(XMLContainer p_gui,String p_dean_id)
	{
		PlanInitRequest t_request=new PlanInitRequest();
		t_request.m_dean_id=p_dean_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		PlanInitReply t_reply=(PlanInitReply)SmartEvents.expect(PlanInitReply.class);
		XMLContainer t_tab=SmartGuis.find(p_gui,"modules_tab");
		for(String t_module:t_reply.m_frame.m_modules.split(";"))
		{
			try
			{
				XMLContainer t_panel=new PlanModuleComponent(t_module,p_dean_id).getGui();
				t_tab.add(t_panel);
			}
			catch(Exception t_exp)
			{
				t_exp.printStackTrace();
			}
		}
		((JTabbedPane)t_tab).remove(0);
	}
	
}
