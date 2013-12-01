package coursesystem.client.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;
import coursesystem.client.dialog.CourseDetailDialog;
import coursesystem.client.handler.DeanCoursesHandler;

public class DeanFrame
{

	private XMLContainer m_gui;
	private String m_dean_id;

	public DeanFrame(String p_dean_id) throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException,NoSuchMethodException
	{
		m_gui=SmartGuis.create(new FileInputStream("res/frame/DeanFrame.xml"));
		m_dean_id=p_dean_id;
		assign();
	}
	
	private void assign()
	{
		final JButton t_search=(JButton)SmartGuis.find(m_gui,"search");
		t_search.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				DeanCoursesHandler.onHandle(m_gui,m_dean_id);
			}
		});
		final JTable t_compulsory_table=(JTable)SmartGuis.find(m_gui,"compulsory_table");
		final JButton t_course_detail=(JButton)SmartGuis.find(m_gui,"course_detail");
		final JButton t_compulsory_uncompulsory=(JButton)SmartGuis.find(m_gui,"compulsory_uncompulsory");
		t_compulsory_table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent t_evt) 
			{
				boolean t_selected=DeanCoursesHandler.getSelectedCourseId(m_gui)!=null;
				t_course_detail.setEnabled(t_selected);
				t_compulsory_uncompulsory.setEnabled(t_selected);
				if(t_selected)
					t_compulsory_uncompulsory.setText(DeanCoursesHandler.isSelectedCourseCompuled(m_gui)?" 退 选 ":" 指 选 ");
			}
		});
		t_course_detail.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				String t_course_id=DeanCoursesHandler.getSelectedCourseId(m_gui);
				try
				{
					new CourseDetailDialog(t_course_id);
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
	}
	
}
