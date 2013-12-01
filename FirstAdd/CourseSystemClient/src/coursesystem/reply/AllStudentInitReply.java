package coursesystem.reply;

import java.util.List;

import coursesystem.reply.superclass.Reply;
import coursesystem.unit.Faculty;
import coursesystem.unit.Student;

/**
 * @author z w q
 * @added this
 * @added coursesystem.client.handler.[AllStudentInitHandler,AllTeacherInitHandler,AllDeanInitHandler,AllProvestInitHandler]
 * @since 2013.11.29
 *
 */

public class AllStudentInitReply extends Reply {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<Faculty> m_facultys;
	public List<Student> m_students;

}
