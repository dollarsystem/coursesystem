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
import coursesystem.client.dialog.StudyProgressDialog;
import coursesystem.client.event.ClassesChangedEvent;
import coursesystem.client.handler.AllCoursesHandler;
import coursesystem.client.handler.AllCoursesInitHandler;
import coursesystem.client.handler.ChangeMailBoxHandler;
import coursesystem.client.handler.ChangePasswordHandler;
import coursesystem.client.handler.NoticesHandler;
import coursesystem.client.handler.SelectCourseHandler;
import coursesystem.client.handler.StudentCoursesHandler;
import coursesystem.client.handler.StudentInfosHandler;
import coursesystem.client.handler.QuitCourseHandler;
import coursesystem.client.handler.StudentScoresHandler;
import coursesystem.event.NewNoticeEvent;
import coursesystem.unit.Course;

public class StudentFrame
{
	
	private XMLContainer m_gui;
	private String m_student_id;

	public StudentFrame(String p_student_id) throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException,NoSuchMethodException
	{
		m_gui=SmartGuis.create(new FileInputStream("res/frame/StudentFrame.xml"));
		m_student_id=p_student_id;
		assign();
		bind();
		StudentCoursesHandler.onHandle(m_gui,m_student_id);
		StudentInfosHandler.onHandle(m_gui,m_student_id);
		AllCoursesInitHandler.onHandle(m_gui);
		StudentScoresHandler.onHandle(m_gui,m_student_id);
		NoticesHandler.onHandle(m_gui,"student",m_student_id);
	}
	
	private void assign()
	{
		final JTable t_my_class_table=(JTable)SmartGuis.find(m_gui,"my_class_table");
		final JButton t_my_class_detail=(JButton)SmartGuis.find(m_gui,"my_class_detail");
		final JButton t_my_class_quit=(JButton)SmartGuis.find(m_gui,"my_class_quit");
		t_my_class_table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent t_evt) 
			{
				String t_course_id=StudentCoursesHandler.getSelectedCourseId(m_gui);
				boolean t_selected=t_course_id!=null;
				boolean t_canquit=t_selected&&Course.canQuit(t_course_id);
				t_my_class_detail.setEnabled(t_selected);
				t_my_class_quit.setEnabled(t_canquit);
			}
		});
		t_my_class_detail.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				try
				{
					String t_course_id=Course.getSelectableCourseId(StudentCoursesHandler.getSelectedCourseId(m_gui));
					new CourseDetailDialog(t_course_id);
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
				String t_course_id=StudentCoursesHandler.getSelectedCourseId(m_gui);
				QuitCourseHandler.onHandle(m_gui,m_student_id,t_course_id);
			}
		});
		final JButton t_all_courses_search=(JButton)SmartGuis.find(m_gui,"all_courses_search");
		final JTable t_all_courses_table=(JTable)SmartGuis.find(m_gui,"all_courses_table");
		final JButton t_all_courses_detail=(JButton)SmartGuis.find(m_gui,"all_courses_detail");
		final JButton t_all_courses_select=(JButton)SmartGuis.find(m_gui,"all_courses_select");
		t_all_courses_search.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				t_all_courses_detail.setEnabled(false);
				t_all_courses_select.setEnabled(false);
				AllCoursesHandler.onHandle(m_gui);
			}
		});
		t_all_courses_table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent t_evt) 
			{
				boolean t_isthisterm=AllCoursesInitHandler.isThisTerm(m_gui);
				boolean t_isselected=AllCoursesHandler.getSelectedCourseId(m_gui)!=null;
				t_all_courses_detail.setEnabled(t_isselected);
				t_all_courses_select.setEnabled(t_isthisterm&&t_isselected);
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
		t_all_courses_select.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				String t_course_id=AllCoursesHandler.getSelectedCourseId(m_gui);
				SelectCourseHandler.onHandle(m_gui,m_student_id,t_course_id);
			}
		});
		final JButton t_faculty_detail=(JButton)SmartGuis.find(m_gui,"faculty_detail");
		t_faculty_detail.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				try
				{
					new FacultyDetailDialog(StudentInfosHandler.getFacultyId(m_gui));
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
				ChangeMailBoxHandler.onHandle(m_gui,"学生",m_student_id);
			}
		});
		final JButton t_change_password=(JButton)SmartGuis.find(m_gui,"change_password");
		t_change_password.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				ChangePasswordHandler.onHandle(m_gui,"学生",m_student_id);
			}
		});
		final JTable t_my_scores_table=(JTable)SmartGuis.find(m_gui,"my_scores_table");
		final JButton t_my_scores_process=(JButton)SmartGuis.find(m_gui,"my_scores_process");
		final JButton t_my_scores_detail=(JButton)SmartGuis.find(m_gui,"my_scores_detail");
		t_my_scores_table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent t_evt) 
			{
				boolean t_flag=StudentScoresHandler.getSelectedCourseId(m_gui)!=null;
				t_my_scores_detail.setEnabled(t_flag);
			}
		});
		t_my_scores_process.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				try
				{
					new StudyProgressDialog(m_student_id);
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		t_my_scores_detail.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				try
				{
					new CourseDetailDialog(StudentScoresHandler.getSelectedCourseId(m_gui));
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
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
					new NoticeDetailDialog("student",m_student_id,t_name,t_notice_id,t_sender_type,t_sender_id);
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
					new NewNoticeDialog("student",m_student_id,t_name,"");
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
		SmartEvents.bind(ClassesChangedEvent.class,this,"onClassesChanged");
		SmartEvents.bind(NewNoticeEvent.class,this,"onNewNotice");
	}
	
	public void onClassesChanged(ClassesChangedEvent t_event)
	{
		StudentCoursesHandler.onHandle(m_gui,m_student_id);
	}
	
	public void onNewNotice(NewNoticeEvent t_event)
	{
		NoticesHandler.onHandle(m_gui,"student",m_student_id,t_event.m_notice);
	}
	
}
