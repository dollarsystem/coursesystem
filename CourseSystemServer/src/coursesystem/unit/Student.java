package coursesystem.unit;

import java.io.Serializable;

public class Student implements Serializable
{

	private static final long serialVersionUID=0;
	
	public String m_id;
	public String m_name;
	public String m_sex;
	public String m_faculty_id;
	public String m_grade;
	public String m_mailbox;
	public String m_password;
	public String m_course_ids;
	public String m_course_scores;
	public String m_history_course_ids;
	public String m_history_course_scores;
	
	public void fillNull()
	{
		m_id=m_id!=null?m_id:"";
		m_name=m_name!=null?m_name:"";
		m_sex=m_sex!=null?m_sex:"";
		m_faculty_id=m_faculty_id!=null?m_faculty_id:"";
		m_grade=m_grade!=null?m_grade:"";
		m_mailbox=m_mailbox!=null?m_mailbox:"";
		m_password=m_password!=null?m_password:"";
		m_course_ids=m_course_ids!=null?m_course_ids:"";
		m_course_scores=m_course_scores!=null?m_course_scores:"";
		m_history_course_ids=m_history_course_ids!=null?m_history_course_ids:"";
		m_history_course_scores=m_history_course_scores!=null?m_history_course_scores:"";
	}
	
}
