package coursesystem.server.handler;

import java.util.ArrayList;

import zjs.smartevents.SmartEvents;
import coursesystem.reply.CourseScoreListReply;
import coursesystem.request.CourseScoreListRequest;
import coursesystem.unit.Student;

public class CourseScoreListHandler {
	public static void onHandle(CourseScoreListRequest p_request) {
		/*
		CourseScoreListReply t_reply = new CourseScoreListReply();
		Student t_stu1 = new Student("121250224", "周坚石", "男", "software", "大二",
				"zjs12@software.nju.edu.cn", "121250224", "!c0807_2,?c0784_2,",
				"98,-1,", "c6062,", "92,");
		Student t_stu2 = new Student("121250226", "周文卿", "男", "software", "大二",
				"zwq12@software.nju.edu.cn", "121233323", "!c0807_2,?c6062,",
				"88,-1,", "c6062,", "71,");
		ArrayList<Student> t_students = new ArrayList<Student>();
		t_students.add(t_stu2);
		t_students.add(t_stu1);
		t_reply.m_done = true;
		t_reply.m_students = t_students;
		SmartEvents.happen(t_reply, true, p_request.getFriend());
		*/
	}
}
