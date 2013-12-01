package coursesystem.unit;

import java.io.Serializable;

public class Notice implements Serializable
{
	
	private static final long serialVersionUID=0;
	
	public String m_id;
	public String m_sender;
	public String m_receiver_ids;
	public String m_content;
	public String m_date;
	
	public void fillNull()
	{
		m_id=m_id!=null?m_id:"";
		m_sender=m_sender!=null?m_sender:"";
		m_receiver_ids= m_receiver_ids!=null? m_receiver_ids:"";
		m_content=m_content!=null?m_content:"";
		m_date=m_date!=null?m_date:"";
	}
	
	public static String getSenderType(String p_sender)
	{
		return p_sender.split(",")[0];
	}
	
	public static String getSenderId(String p_sender)
	{
		return p_sender.split(",")[1];
	}
	
	public static String getSenderName(String p_sender)
	{
		return p_sender.split(",")[2];
	}

}
