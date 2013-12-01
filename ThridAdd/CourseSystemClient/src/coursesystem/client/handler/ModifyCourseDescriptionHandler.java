package coursesystem.client.handler;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import coursesystem.reply.ModifyCourseDescriptionReply;
import coursesystem.request.ModifyCourseDescriptionRequest;
import coursesystem.unit.Course;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class ModifyCourseDescriptionHandler {

	public static void onHandle(XMLContainer p_parent_gui, XMLContainer p_gui,
			String p_course_id, String p_text) {
		JFrame t_frame = (JFrame) SmartGuis.find(p_gui, "frame");
		JButton t_modify_course_description = (JButton) SmartGuis.find(p_gui,
				"modify_course_description");
		JButton t_save_course_description = (JButton) SmartGuis.find(p_gui,
				"save_course_description");
		JTextArea t_description = (JTextArea) SmartGuis.find(p_gui,
				"description");
		
		int t_flag = JOptionPane.showConfirmDialog(t_frame, "确定修改该课程？", "提示",
				JOptionPane.YES_NO_OPTION);

		if (t_flag != JOptionPane.YES_OPTION) {
			return;
		}

		
		ModifyCourseDescriptionRequest t_request = new ModifyCourseDescriptionRequest();
		t_request.m_course_id = p_course_id;
		t_request.m_description = p_text;

		SmartEvents.happen(t_request, SmartEvents.NETWORK_ONLY);

		ModifyCourseDescriptionReply t_reply = (ModifyCourseDescriptionReply) SmartEvents
				.expect(ModifyCourseDescriptionReply.class);

		if (!t_reply.m_done) {
			JOptionPane.showMessageDialog(t_frame, t_reply.m_message, "错误",
					JOptionPane.ERROR_MESSAGE);
		} else {

			SmartGuis.set(SmartGuis.find(p_parent_gui, "last_opt"), "text",
					"修改课程详情：" + Course.getVisibleId(p_course_id));
			JOptionPane.showMessageDialog(t_frame, "修改成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			t_modify_course_description.setEnabled(true);

			t_save_course_description.setEnabled(false);

			t_description.setEditable(false);
		}

	}

}
