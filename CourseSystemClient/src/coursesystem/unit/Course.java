package coursesystem.unit;

import java.io.Serializable;

public class Course implements Serializable
{

	private static final long serialVersionUID=0;
	
	public String m_id;
	public String m_name;
	public String m_type;
	public String m_marks;
	public String m_faculty_id;
	public String m_teacher_id;
	public String m_term;
	public String m_classes;
	public String m_description;
	public String m_student_ids;
	
	public static String toCompuledFormat(String p_id)
	{
		return "!"+p_id;
	}
	
	public static boolean canQuit(String p_id)
	{
		return !p_id.startsWith("!");
	}
	
	public static String getSelectableCourseId(String p_id)
	{
		return p_id.substring(1);
	}
	
	public static String getVisibleId(String p_id)
	{
		int t_index=p_id.lastIndexOf('_');
		if(t_index!=-1)
			p_id=p_id.substring(0,t_index);
		return p_id;
	}

}
