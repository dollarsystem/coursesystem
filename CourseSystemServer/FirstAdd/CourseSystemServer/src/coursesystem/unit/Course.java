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
	
	public void fillNull()
	{
		m_id=m_id!=null?m_id:"";
		m_name=m_name!=null?m_name:"";
		m_type=m_type!=null?m_type:"";
		m_marks=m_marks!=null?m_marks:"";
		m_faculty_id=m_faculty_id!=null?m_faculty_id:"";
		m_teacher_id=m_teacher_id!=null?m_teacher_id:"";
		m_classes=m_classes!=null?m_classes:"";
		m_description=m_description!=null?m_description:"";
		m_student_ids=m_student_ids!=null?m_student_ids:"";
		m_term=m_term!=null?m_term:"";
	}

}
