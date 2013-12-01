package coursesystem.request;

import coursesystem.request.superclass.Request;

public class ChangePasswordRequest extends Request
{

	private static final long serialVersionUID=0;
	
	public String m_type;
	public String m_id;
	public String m_new_password;

}
