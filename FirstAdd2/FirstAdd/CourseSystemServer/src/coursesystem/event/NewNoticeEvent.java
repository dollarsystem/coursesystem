package coursesystem.event;

import coursesystem.unit.Notice;
import zjs.smartevents.events.Event;

public class NewNoticeEvent extends Event
{

	private static final long serialVersionUID=0;
	
	public Notice m_notice;

}
