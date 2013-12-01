package coursesystem.request;

import coursesystem.request.superclass.Request;

public class DeanCompulsoryRequest extends Request
{

	private static final long serialVersionUID=0;
	
	public String m_dean_id;
	public String m_grade;
	public boolean m_compulsory_uncompulsory;
	public String m_course_id;

}
