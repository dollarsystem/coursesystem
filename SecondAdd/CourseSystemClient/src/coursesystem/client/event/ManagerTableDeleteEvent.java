package coursesystem.client.event;

import zjs.smartevents.events.Event;
/**
 * 
 * @author z w q
 * @create 20131130
 *
 */
public class ManagerTableDeleteEvent extends Event{

	private static final long serialVersionUID = 1L;
	public static final int STUDENT=1;
	public static final int TEACHER=2;
	public static final int DEAN=3;
	public static final int PROVEST=4;
		
	public int m_type;
	public String m_id;

}
