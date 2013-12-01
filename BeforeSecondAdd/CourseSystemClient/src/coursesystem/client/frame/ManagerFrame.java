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
import coursesystem.client.dialog.Manager_CreateStudentDialog;
import coursesystem.client.dialog.Manager_CreateTeacherDialog;
import coursesystem.client.dialog.Manager_CreateDeanDialog;
import coursesystem.client.dialog.Manager_CreateProvestDialog;
import coursesystem.client.dialog.Manager_StudentDetailDialog;
import coursesystem.client.dialog.Manager_DeanDetailDialog;
import coursesystem.client.dialog.ProvestDetailDialog;
import coursesystem.client.handler.AllDeanHandler;
import coursesystem.client.handler.AllDeanInitHandler;
import coursesystem.client.handler.AllProvestHandler;
import coursesystem.client.handler.AllStudentHandler;
import coursesystem.client.handler.AllStudentInitHandler;
import coursesystem.client.handler.AllTeacherHandler;
import coursesystem.client.handler.AllTeacherInitHandler;
import coursesystem.client.handler.DeleteDeanHandler;
import coursesystem.client.handler.DeleteProvestHandler;
import coursesystem.client.handler.DeleteStudentHandler;
import coursesystem.client.handler.DeleteTeacherHandler;
import coursesystem.client.handler.NoticesHandler;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;
import coursesystem.client.dialog.TeacherDetailDialog;

public class ManagerFrame 
{

	private XMLContainer m_gui;
	private String m_manager_id;
	
	public ManagerFrame(String p_manager_id) throws InstantiationException, IllegalAccessException, FileNotFoundException, ParserConfigurationException, SAXException, IOException, NoSuchMethodException
	{
		m_gui=SmartGuis.create(new FileInputStream("res/frame/ManagerFrame.xml"));
		m_manager_id=p_manager_id;
		assign();
		AllStudentInitHandler.onHandle(m_gui);
		AllTeacherInitHandler.onHandle(m_gui);
		AllDeanInitHandler.onHandle(m_gui);
		AllProvestHandler.onHandle(m_gui);
		NoticesHandler.onHandle(m_gui,"manager",m_manager_id);
	}
	private void assign() 
	{
		final JTable t_students_table=(JTable)SmartGuis.find(m_gui,"students_table");
		final JButton t_students_search=(JButton)SmartGuis.find(m_gui,"students_search");
		final JButton t_student_new=(JButton)SmartGuis.find(m_gui,"student_new");
		final JButton t_student_detail=(JButton)SmartGuis.find(m_gui,"student_detail");
		final JButton t_student_delete=(JButton)SmartGuis.find(m_gui,"student_delete");		
		t_students_search.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				t_student_detail.setEnabled(false);
				t_student_delete.setEnabled(false);
				AllStudentHandler.onHandle(m_gui);
			}
		});
		t_students_table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent t_evt) 
			{
				boolean t_flag=AllStudentHandler.getSelectedStudentId(m_gui)!=null;
				t_student_detail.setEnabled(t_flag);
				t_student_delete.setEnabled(t_flag);
			}
		});
		t_student_new.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent t_evt)
			{
				try
				{
					new Manager_CreateStudentDialog(m_gui);//after create send event to frame itself
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		t_student_detail.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent t_evt)
			{
				try
				{
					new Manager_StudentDetailDialog(AllStudentHandler.getSelectedStudentId(m_gui));
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		t_student_delete.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent t_evt)
			{
				DeleteStudentHandler.onHandle(m_gui,AllStudentHandler.getSelectedStudentId(m_gui));
			}
		});
		final JTable t_teachers_table=(JTable)SmartGuis.find(m_gui,"teachers_table");
		final JButton t_teachers_search=(JButton)SmartGuis.find(m_gui,"teachers_search");
		final JButton t_teacher_new=(JButton)SmartGuis.find(m_gui,"teacher_new");
		final JButton t_teacher_detail=(JButton)SmartGuis.find(m_gui,"teacher_detail");
		final JButton t_teacher_delete=(JButton)SmartGuis.find(m_gui,"teacher_delete");		
		t_teachers_search.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				t_teacher_detail.setEnabled(false);
				t_teacher_delete.setEnabled(false);
				AllTeacherHandler.onHandle(m_gui);
			}
		});
		t_teachers_table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent t_evt) 
			{
				boolean t_flag=AllTeacherHandler.getSelectedTeacherId(m_gui)!=null;
				t_teacher_detail.setEnabled(t_flag);
				t_teacher_delete.setEnabled(t_flag);
			}
		});
		t_teacher_new.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent t_evt)
			{
				try
				{
					new Manager_CreateTeacherDialog(m_gui);
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		t_teacher_detail.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent t_evt)
			{
				try
				{
					new TeacherDetailDialog(AllTeacherHandler.getSelectedTeacherId(m_gui));
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		t_teacher_delete.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent t_evt)
			{
				DeleteTeacherHandler.onHandle(m_gui,AllTeacherHandler.getSelectedTeacherId(m_gui));
			}
		});
		final JTable t_deans_table=(JTable)SmartGuis.find(m_gui,"deans_table");
		final JButton t_deans_search=(JButton)SmartGuis.find(m_gui,"deans_search");
		final JButton t_dean_new=(JButton)SmartGuis.find(m_gui,"dean_new");
		final JButton t_dean_detail=(JButton)SmartGuis.find(m_gui,"dean_detail");
		final JButton t_dean_delete=(JButton)SmartGuis.find(m_gui,"dean_delete");		
		t_deans_search.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				t_dean_detail.setEnabled(false);
				t_dean_delete.setEnabled(false);
				AllDeanHandler.onHandle(m_gui);
			}
		});
		t_deans_table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent t_evt) 
			{
				boolean t_flag=AllDeanHandler.getSelectedDeanId(m_gui)!=null;
				t_dean_detail.setEnabled(t_flag);
				t_dean_delete.setEnabled(t_flag);
			}
		});
		t_dean_new.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent t_evt)
			{
				try
				{
					new Manager_CreateDeanDialog(m_gui);
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		t_dean_detail.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent t_evt)
			{
				try
				{
					new Manager_DeanDetailDialog(AllDeanHandler.getSelectedDeanId(m_gui));
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		t_dean_delete.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent t_evt)
			{
				DeleteDeanHandler.onHandle(m_gui,AllDeanHandler.getSelectedDeanId(m_gui));
			}
		});
		final JTable t_provests_table=(JTable)SmartGuis.find(m_gui,"provests_table");
		final JButton t_provest_new=(JButton)SmartGuis.find(m_gui,"provest_new");
		final JButton t_provest_detail=(JButton)SmartGuis.find(m_gui,"provest_detail");
		final JButton t_provest_delete=(JButton)SmartGuis.find(m_gui,"provest_delete");
		
		t_provest_detail.setEnabled(false);
		t_provest_delete.setEnabled(false);
		
		t_provests_table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent t_evt) 
			{
				boolean t_flag=AllProvestHandler.getSelectedProvestId(m_gui)!=null;
				t_provest_detail.setEnabled(t_flag);
				t_provest_delete.setEnabled(t_flag);
			}
		});
		t_provest_new.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent t_evt)
			{
				try
				{
					new Manager_CreateProvestDialog(m_gui);
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		t_provest_detail.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent t_evt)
			{
				try
				{
					new ProvestDetailDialog(AllProvestHandler.getSelectedProvestId(m_gui));
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		t_provest_delete.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent t_evt)
			{
				DeleteProvestHandler.onHandle(m_gui,AllProvestHandler.getSelectedProvestId(m_gui));
			}
		});
		
	}

}
