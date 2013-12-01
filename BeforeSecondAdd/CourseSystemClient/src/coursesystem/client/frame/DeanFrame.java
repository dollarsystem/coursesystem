package coursesystem.client.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import coursesystem.client.event.DeanCompulsedEvent;
import coursesystem.client.handler.ChangeMailBoxHandler;
import coursesystem.client.handler.ChangePasswordHandler;
import coursesystem.client.handler.DeanCompulsoryHandler;
import coursesystem.client.handler.DeanCoursesHandler;
import coursesystem.client.handler.DeanInfosHandler;
import coursesystem.client.handler.NoticesHandler;
import coursesystem.client.handler.PlanInitHandler;
import coursesystem.client.handler.StudentInfosHandler;
import coursesystem.event.NewNoticeEvent;

public class DeanFrame
{

	private XMLContainer m_gui;
	private String m_dean_id;

	public DeanFrame(String p_dean_id) throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException,NoSuchMethodException
	{
		m_gui=SmartGuis.create(new FileInputStream("res/frame/DeanFrame.xml"));
		m_dean_id=p_dean_id;
		assign();
		bind();
		PlanInitHandler.onHandle(m_gui,m_dean_id);
		DeanInfosHandler.onHandle(m_gui,m_dean_id);
		NoticesHandler.onHandle(m_gui,"dean",m_dean_id);
	}
	
	private void assign()
	{
		final JButton t_search=(JButton)SmartGuis.find(m_gui,"compulsory_search");
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
		@SuppressWarnings("unchecked")
		final JComboBox<String> t_grades=(JComboBox<String>)SmartGuis.find(m_gui,"grade_combo");
		t_compulsory_uncompulsory.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				boolean t_compulsory_uncompulsory=DeanCoursesHandler.isSelectedCourseCompuled(m_gui);
				String t_course_id=DeanCoursesHandler.getSelectedCourseId(m_gui);
				DeanCompulsoryHandler.onHandle(m_gui,m_dean_id,t_grades.getSelectedItem().toString(),!t_compulsory_uncompulsory,t_course_id);
			}
		});
		final JButton t_faculty_detail=(JButton)SmartGuis.find(m_gui,"faculty_detail");
		t_faculty_detail.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				try
				{
					new FacultyDetailDialog(DeanInfosHandler.getFacultyId(m_gui));
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
				ChangeMailBoxHandler.onHandle(m_gui,"院系教务员",m_dean_id);
			}
		});
		final JButton t_change_password=(JButton)SmartGuis.find(m_gui,"change_password");
		t_change_password.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				ChangePasswordHandler.onHandle(m_gui,"院系教务员",m_dean_id);
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
					new NoticeDetailDialog("dean",m_dean_id,t_name,t_notice_id,t_sender_type,t_sender_id);
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
					new NewNoticeDialog("dean",m_dean_id,t_name,"");
				}
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
	}
	
	public void onDeanCompulsed(DeanCompulsedEvent t_event)
	{
		DeanCoursesHandler.onHandle(m_gui,m_dean_id);
	}
	
	private void bind() throws NoSuchMethodException
	{
		SmartEvents.bind(DeanCompulsedEvent.class,this,"onDeanCompulsed");
		SmartEvents.bind(NewNoticeEvent.class,this,"onNewNotice");
	}
	
	public void onNewNotice(NewNoticeEvent t_event)
	{
		System.out.println(m_dean_id+" "+t_event);
		NoticesHandler.onHandle(m_gui,"dean",m_dean_id,t_event.m_notice);
	}
	
}
