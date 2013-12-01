package coursesystem.client.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import coursesystem.reply.CourseScoreListReply;
import coursesystem.request.CourseScoreListRequest;
import coursesystem.unit.Student;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class CourseScoreListHandler {

	public static void onHandle(XMLContainer p_gui, String p_course_id) {
		CourseScoreListRequest t_request = new CourseScoreListRequest();
		t_request.m_course_id = p_course_id;
		SmartEvents.happen(t_request, SmartEvents.NETWORK_ONLY);
		JFrame t_frame = (JFrame) SmartGuis.find(p_gui, "frame");
		CourseScoreListReply t_reply = (CourseScoreListReply) SmartEvents
				.expect(CourseScoreListReply.class);

		if (!t_reply.m_done) {
			JOptionPane.showMessageDialog(t_frame, t_reply.m_message, "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		XMLContainer t_table = SmartGuis.find(p_gui, "score_list");
		List<XMLContainer> t_childs = t_table.getChilds();
		for (int t_i = t_childs.size() - 1; t_i >= 0; t_i--)
			t_table.remove(t_childs.get(t_i));

		List<Student> t_students = t_reply.m_students;

		for (Student t_stu : t_students) {
			int t_index = 0;
			for (t_index = 0; t_index < t_stu.m_course_ids.split(",").length; t_index++) {
				if (t_stu.m_course_ids.split(",")[t_index]
						.endsWith(p_course_id)) {
					break;
				}
			}
			String t_score = t_stu.m_course_scores.split(",")[t_index];
			String t_line = "<Line items='" + t_stu.m_id + "," + t_stu.m_name
					+ "," + t_score + "'" + " student_id='" + t_stu.m_id
					+ "'/>";

			try {
				XMLContainer t_score_line = SmartGuis.create(t_line);
				SmartGuis.add(t_table, t_score_line);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
