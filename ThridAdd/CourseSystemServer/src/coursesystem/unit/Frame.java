package coursesystem.unit;

import java.io.Serializable;

public class Frame implements Serializable
{
	
	private static final long serialVersionUID=0;
	
	public String m_id;
	public String m_modules;
	
	public void fillNull()
	{
		m_id=m_id!=null?m_id:"";
		m_modules=m_modules!=null?m_modules:"";
	}

}
