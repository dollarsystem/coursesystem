package coursesystem.client.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;
import coursesystem.client.handler.CourseDetailHandler;

public class CourseDetailDialog
{

	private XMLContainer m_gui;
	private String m_course_id;
	
	public CourseDetailDialog(String p_course_id) throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException
	{
		m_gui=SmartGuis.create(new FileInputStream("res/dialog/CourseDetailDialog.xml"));
		m_course_id=p_course_id;
		assign();
		CourseDetailHandler.onHandle(m_gui,m_course_id);
	}
	
	private void assign()
	{
		JButton t_faculty_detail=(JButton)SmartGuis.find(m_gui,"faculty_detail");
		t_faculty_detail.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				try
				{
					new FacultyDetailDialog(CourseDetailHandler.getFacultyId(m_gui));
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		JButton t_teacher_detail=(JButton)SmartGuis.find(m_gui,"teacher_detail");
		t_teacher_detail.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				try
				{
					new TeacherDetailDialog(CourseDetailHandler.getTeacherId(m_gui));
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
	}
	
}
