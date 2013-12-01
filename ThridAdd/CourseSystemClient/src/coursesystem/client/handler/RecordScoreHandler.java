package coursesystem.client.handler;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import coursesystem.client.event.ScoreListChangedEvent;
import coursesystem.reply.RecordScoreReply;
import coursesystem.request.RecordScoreRequest;
import coursesystem.unit.Course;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class RecordScoreHandler {

	public static void onHandle(XMLContainer p_parent_gui, XMLContainer p_gui,
			String p_course_id) {

		RecordScoreRequest t_request = new RecordScoreRequest();
		t_request.m_course_id = p_course_id;
		JTable t_score_list = (JTable) SmartGuis.find(p_gui, "score_list");
		ArrayList<String> t_students = new ArrayList<String>();
		ArrayList<String> t_scores = new ArrayList<String>();
		for (int t_i = 0; t_i < t_score_list.getRowCount(); t_i++) {
			t_students.add((String) t_score_list.getValueAt(t_i, 0));
			t_scores.add((String) t_score_list.getValueAt(t_i, 2));
		}
		t_request.m_scores = t_scores;
		t_request.m_students = t_students;

		SmartEvents.happen(t_request, SmartEvents.NETWORK_ONLY);

		RecordScoreReply t_reply = (RecordScoreReply) SmartEvents
				.expect(RecordScoreReply.class);
		JFrame t_frame = (JFrame) SmartGuis.find(p_gui, "frame");
		if (!t_reply.m_done) {
			JOptionPane.showMessageDialog(t_frame, t_reply.m_message, "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		ScoreListChangedEvent t_event = new ScoreListChangedEvent();
		SmartEvents.happen(t_event, SmartEvents.LOCAL_ONLY);
		SmartGuis.set(SmartGuis.find(p_parent_gui, "last_opt"), "text", "修改课程："
				+ Course.getVisibleId(p_course_id) + " 成绩 ");
		JOptionPane.showMessageDialog(t_frame, "成绩保存成功！", "提示",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
