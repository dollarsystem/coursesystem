package coursesystem.request;

import coursesystem.request.superclass.Request;
import coursesystem.unit.Dean;
import coursesystem.unit.Provest;
import coursesystem.unit.Student;
import coursesystem.unit.Teacher;
/**
 * @author z w q
 * @create 20131130
 *
 */
public class Manager_UserCreateRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static int STUDENT=0;
	public static int TEACHER=1;
	public static int DEAN=2;
	public static int PROVEST=3;
	
//	public static int CREATE=10;
//	public static int UPDATE=11;
	
	public int m_type;
//	public int m_operation;
	
	public Student m_student=null;
	public Teacher m_teacher=null;
	public Dean m_dean=null;
	public Provest m_provest=null;	
	

	
}
