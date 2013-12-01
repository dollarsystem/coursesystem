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
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;
import coursesystem.client.dialog.CourseDetailDialog;
import coursesystem.client.dialog.FacultyDetailDialog;
import coursesystem.client.dialog.NewNoticeDialog;
import coursesystem.client.dialog.NoticeDetailDialog;
import coursesystem.client.handler.AllCoursesHandler;
import coursesystem.client.handler.AllCoursesInitHandler;
import coursesystem.client.handler.ChangeMailBoxHandler;
import coursesystem.client.handler.ChangePasswordHandler;
import coursesystem.client.handler.ModifyDescriptionHandler;
import coursesystem.client.handler.NoticesHandler;
import coursesystem.client.handler.StudentCoursesHandler;
import coursesystem.client.handler.StudentInfosHandler;
import coursesystem.client.handler.TeacherCoursesHandler;
import coursesystem.client.handler.TeacherInfosHandler;
import coursesystem.event.NewNoticeEvent;

public class TeacherFrame
{

	private XMLContainer m_gui;
	private String m_teacher_id;

	public TeacherFrame(String p_teacher_id) throws InstantiationException,IllegalAccessException, FileNotFoundException,ParserConfigurationException, SAXException, IOException,NoSuchMethodException
	{
		m_gui=SmartGuis.create(new FileInputStream("res/frame/TeacherFrame.xml"));
		m_teacher_id=p_teacher_id;
		TeacherCoursesHandler.onHandle(m_gui,m_teacher_id);
		TeacherInfosHandler.onHandle(m_gui,m_teacher_id);
		AllCoursesInitHandler.onHandle(m_gui);
		NoticesHandler.onHandle(m_gui,"teacher",m_teacher_id);
		assign();
		bind();
	}

	private void assign()
	{
		final JTable t_my_class_table=(JTable)SmartGuis.find(m_gui,"my_class_table");
		final JButton t_my_class_detail=(JButton)SmartGuis.find(m_gui,"my_class_detail");
		final JButton t_modify_description=(JButton)SmartGuis.find(m_gui,"modify_description");
		final JButton t_record_score=(JButton)SmartGuis.find(m_gui,"record_score");
		t_my_class_table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent t_evt) 
			{
				String t_course_id=StudentCoursesHandler.getSelectedCourseId(m_gui);
				boolean t_selected=t_course_id!=null;
				t_my_class_detail.setEnabled(t_selected);
				t_modify_description.setEnabled(t_selected);
				t_record_score.setEnabled(t_selected);
			}
		});
		t_my_class_detail.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				try
				{
					String t_course_id=TeacherCoursesHandler.getSelectedCourseId(m_gui);
					new CourseDetailDialog(t_course_id);
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		t_modify_description.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				String t_course_id=TeacherCoursesHandler.getSelectedCourseId(m_gui);
				ModifyDescriptionHandler.onHandle(m_gui,t_course_id);
			}
		});
		final JButton t_all_courses_search=(JButton)SmartGuis.find(m_gui,"all_courses_search");
		final JTable t_all_courses_table=(JTable)SmartGuis.find(m_gui,"all_courses_table");
		final JButton t_all_courses_detail=(JButton)SmartGuis.find(m_gui,"all_courses_detail");
		t_all_courses_search.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				t_all_courses_detail.setEnabled(false);
				AllCoursesHandler.onHandle(m_gui);
			}
		});
		t_all_courses_table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent t_evt) 
			{
				boolean t_isselected=AllCoursesHandler.getSelectedCourseId(m_gui)!=null;
				t_all_courses_detail.setEnabled(t_isselected);
			}
		});
		t_all_courses_detail.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				try
				{
					new CourseDetailDialog(AllCoursesHandler.getSelectedCourseId(m_gui));
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		final JButton t_faculty_detail=(JButton)SmartGuis.find(m_gui,"faculty_detail");
		t_faculty_detail.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				try
				{
					new FacultyDetailDialog(TeacherInfosHandler.getFacultyId(m_gui));
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		final JButton t_change_mailbox=(JButton)SmartGuis.find(m_gui,"change_mailbox");
		t_change_mailbox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				ChangeMailBoxHandler.onHandle(m_gui,"教师",m_teacher_id);
			}
		});
		final JButton t_change_password=(JButton)SmartGuis.find(m_gui,"change_password");
		t_change_password.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				ChangePasswordHandler.onHandle(m_gui,"教师",m_teacher_id);
			}
		});
		final JTable t_notice_table=(JTable)SmartGuis.find(m_gui,"notice_table");
		final JButton t_read_notice=(JButton)SmartGuis.find(m_gui,"read_notice");
		final JButton t_new_notice=(JButton)SmartGuis.find(m_gui,"new_notice");
		t_notice_table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent t_evt) 
			{
				boolean t_flag=NoticesHandler.getSelectedNoticeId(m_gui)!=null;
				t_read_notice.setEnabled(t_flag);
			}
		});
		t_read_notice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				try
				{
					String t_name=StudentInfosHandler.getStudentName(m_gui);
					String t_notice_id=NoticesHandler.getSelectedNoticeId(m_gui);
					String t_sender_type=NoticesHandler.getSelectedSenderType(m_gui);
					String t_sender_id=NoticesHandler.getSelectedSenderId(m_gui);
					new NoticeDetailDialog("student",m_teacher_id,t_name,t_notice_id,t_sender_type,t_sender_id);
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		t_new_notice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				try
				{
					String t_name=StudentInfosHandler.getStudentName(m_gui);
					new NewNoticeDialog("student",m_teacher_id,t_name,"");
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
	}
	
	private void bind() throws NoSuchMethodException
	{
		SmartEvents.bind(NewNoticeEvent.class,this,"onNewNotice");
	}
	
	public void onNewNotice(NewNoticeEvent t_event)
	{
		NoticesHandler.onHandle(m_gui,"teacher",m_teacher_id,t_event.m_notice);
	}

}
