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
import coursesystem.client.event.StudentQuitCourseEvent;
import coursesystem.client.handler.StudentCoursesHandler;
import coursesystem.client.handler.StudentInfosHandler;
import coursesystem.client.handler.StudentQuitCourseHandler;

public class StudentFrame
{
	
	private static XMLContainer sm_gui;
	private static String sm_student_id;

	public static void show(String p_student_id) throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException
	{
		sm_gui=SmartGuis.create(new FileInputStream("res/frame/StudentFrame.xml"));
		sm_student_id=p_student_id;
		StudentCoursesHandler.onHandle(sm_gui,sm_student_id);
		StudentInfosHandler.onHandle(sm_gui,sm_student_id);
		final JTable t_my_class_table=(JTable)SmartGuis.find(sm_gui,"my_class_table");
		final JButton t_my_class_detail=(JButton)SmartGuis.find(sm_gui,"my_class_detail");
		final JButton t_my_class_quit=(JButton)SmartGuis.find(sm_gui,"my_class_quit");
		t_my_class_table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent t_evt) 
			{
				String[] t_cells=((XMLContainer)t_my_class_table).getChilds().get(t_my_class_table.getSelectedRow()).get("items").split(",");
				boolean t_flag=t_cells.length>2&&!t_cells[1].equals("");
				t_my_class_detail.setEnabled(t_flag);
				t_my_class_quit.setEnabled(t_flag);
			}
		});
		t_my_class_detail.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				String t_course_id=((XMLContainer)t_my_class_table).getChilds().get(t_my_class_table.getSelectedRow()).get("course_id");
				try
				{
					CourseDetailDialog.show(t_course_id);
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		t_my_class_quit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				String t_course_id=((XMLContainer)t_my_class_table).getChilds().get(t_my_class_table.getSelectedRow()).get("course_id");
				StudentQuitCourseHandler.onHandle(sm_gui,sm_student_id,t_course_id);
			}
		});
	}
	
	public static void onQuitCourse(StudentQuitCourseEvent t_event)
	{
		StudentCoursesHandler.onHandle(sm_gui,sm_student_id);
	}
	
}
