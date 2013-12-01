package coursesystem.client.event;

import coursesystem.unit.Dean;
import coursesystem.unit.Provest;
import coursesystem.unit.Student;
import coursesystem.unit.Teacher;
import zjs.smartevents.events.Event;

/**
 * 
 * @author z w q
 * @create 20131130
 *
 */
public class ManagerTableUpdateEvent extends Event {
	private static final long serialVersionUID = 1L;
	public static final int STUDENT=1;
	public static final int TEACHER=2;
	public static final int DEAN=3;
	public static final int PROVEST=4;
	
	public static final int UPDATE=11;
	public static final int CREATE=12;
		
	public int m_type;
	public int m_operation;
	
	public Student m_student=null;
	public Teacher m_teacher=null;
	public Dean m_dean=null;
	public Provest m_provest=null;
}
