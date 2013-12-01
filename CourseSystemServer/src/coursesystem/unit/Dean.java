package coursesystem.unit;

import java.io.Serializable;

public class Dean  implements Serializable
{

	private static final long serialVersionUID=0;
	
	public String m_id;
	public String m_name;
	public String m_sex;
	public String m_faculty_id;
	public String m_mailbox;
	public String m_password;
	
	public void fillNull()
	{
		m_id=m_id!=null?m_id:"";
		m_name=m_name!=null?m_name:"";
		m_sex=m_sex!=null?m_sex:"";
		m_faculty_id=m_faculty_id!=null?m_faculty_id:"";
		m_mailbox=m_mailbox!=null?m_mailbox:"";
		m_password=m_password!=null?m_password:"";
	}

}
