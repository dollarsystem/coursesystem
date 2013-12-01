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
import coursesystem.client.handler.TeacherDetailHandler;

public class TeacherDetailDialog
{

	private XMLContainer m_gui;
	private String m_teacher_id;
	
	public TeacherDetailDialog(String p_teacher_id) throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException
	{
		m_gui=SmartGuis.create(new FileInputStream("res/dialog/TeacherDetailDialog.xml"));
		m_teacher_id=p_teacher_id;
		assign();
		TeacherDetailHandler.onHandle(m_gui,m_teacher_id);
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
	}
	
}
