package coursesystem.unit;

import java.io.Serializable;

public class Faculty implements Serializable
{

	private static final long serialVersionUID=0;
	
	public String m_id;
	public String m_name;
	public String m_description;
	
	public void fillNull()
	{
		m_id=m_id!=null?m_id:"";
		m_name=m_name!=null?m_name:"";
		m_description=m_description!=null?m_description:"";
	}
	
}
