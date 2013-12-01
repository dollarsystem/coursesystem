package coursesystem.request;

import java.util.List;

import coursesystem.request.superclass.Request;

public class RecordScoreRequest extends Request {

	private static final long serialVersionUID = 1L;

	public String m_course_id;
	public List<String> m_students;
	public List<String> m_scores;
}
