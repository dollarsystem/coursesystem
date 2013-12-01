package coursesystem.unit;

import java.io.Serializable;

public class Plan implements Serializable
{
	
	private static final long serialVersionUID=0;
	
	public String m_faculty_id;
	public String m_modules;
	
	public void fillNull()
	{
		m_faculty_id=m_faculty_id!=null?m_faculty_id:"";
		m_modules=m_modules!=null?m_modules:"";
	}

}
