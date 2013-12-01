package coursesystem.request;

import coursesystem.request.superclass.Request;

public class PlanModuleAddRequest extends Request
{

	private static final long serialVersionUID=0;
	
	public String m_dean_id;
	public String m_course_id;
	public boolean m_add_unadd;

}
