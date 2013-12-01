package coursesystem.client.handler;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;
import coursesystem.client.event.ManagerTableDeleteEvent;
import coursesystem.reply.DeleteStudentReply;
import coursesystem.request.DeleteStudentRequest;

/**
 * 
 * @author z w q
 * @added this
 * @added coursesystem.client.handler.[AllStudentInitHandler,AllTeacherInitHandler,AllDeanInitHandler,AllProvestInitHandler]
 * @added coursesystem.client.handler.[ModifyStudentInfoHandler,ModifyTeacherInfoHandler,ModifyDeanInfoHandler,ModifyProvestInfoHandler]
 * @added creat Handler series, delete Handler series
 * @since 2013.11.28
 */

public class DeleteStudentHandler {

	public static void onHandle(XMLContainer p_gui,String p_student_id) {
		JFrame t_frame=(JFrame)SmartGuis.find(p_gui,"frame");
		int t_flag=JOptionPane.showConfirmDialog(t_frame,"确定要删除该学生？","提示",JOptionPane.YES_NO_OPTION);
		if(t_flag!=JOptionPane.YES_OPTION)
			return;
		DeleteStudentRequest t_request=new DeleteStudentRequest();
		t_request.m_student_id=p_student_id;
		SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
		DeleteStudentReply t_reply=(DeleteStudentReply)SmartEvents.expect(DeleteStudentReply.class);
		if(!t_reply.m_done)
		{
			JOptionPane.showMessageDialog(t_frame,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else
		{
			ManagerTableDeleteEvent t_event=new ManagerTableDeleteEvent();
			t_event.m_type=ManagerTableDeleteEvent.STUDENT;
			t_event.m_id=p_student_id;
			SmartEvents.happen(t_event,SmartEvents.LOCAL_ONLY);
			SmartGuis.set(SmartGuis.find(p_gui,"last_opt"),"text","删除学生："+p_student_id);
			SmartGuis.set(SmartGuis.find(p_gui,"student_detail"),"enable","false");
			SmartGuis.set(SmartGuis.find(p_gui,"student_delete"),"enable","false");
		}
		
		//delete local
		XMLContainer t_table=SmartGuis.find(p_gui,"students_table");
		List<XMLContainer> t_childs=t_table.getChilds();
		List<XMLContainer> t_childs_new=new ArrayList<XMLContainer>();
		for(int t_i=0;t_i<t_childs.size();t_i++)
		{
			if(!t_childs.get(t_i).get("student_id").equals(p_student_id))
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
