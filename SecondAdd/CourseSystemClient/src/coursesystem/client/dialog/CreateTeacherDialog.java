package coursesystem.client.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;
import coursesystem.client.handler.AllTeacherInitHandler;
import coursesystem.reply.CreateUserReply;
import coursesystem.request.CreateUserRequest;
import coursesystem.unit.Teacher;

/**
 * @author z w q
 * @create 20131130
 *
 */
public class CreateTeacherDialog {
	private XMLContainer m_gui;
	private XMLContainer m_father;
	//private String m_student_id;
	
	public CreateTeacherDialog(XMLContainer p_gui) throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException
	{
		m_father=p_gui;
		m_gui=SmartGuis.create(new FileInputStream("res/dialog/CreateTeacherDialog.xml"));
		AllTeacherInitHandler.onHandle(m_gui);
		assign();
	}
	
	private void assign()
	{
		JButton t_save=(JButton)SmartGuis.find(m_gui,"save");
		final JTextField t_id=(JTextField)SmartGuis.find(m_gui, "id");
		final JTextField t_name=(JTextField)SmartGuis.find(m_gui, "name");
		final JTextField t_sex=(JTextField)SmartGuis.find(m_gui, "sex");
		final JTextField t_mailbox=(JTextField)SmartGuis.find(m_gui, "mailbox");
		final JTextField t_password=(JTextField)SmartGuis.find(m_gui, "password");
		t_save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				CreateUserRequest t_request=new CreateUserRequest();
				t_request.m_type=CreateUserRequest.TEACHER;
				Teacher s=new Teacher();
				s.m_id=t_id.getText();
				s.m_name=t_name.getText();
				s.m_sex=t_sex.getText();
				s.m_faculty_id=AllTeacherInitHandler.getSelectedFacultyId(m_gui);
				//s.m_grade=t_grade.getText();
				s.m_mailbox=t_mailbox.getText();
				s.m_password=t_password.getText();
				if(s.m_password.length()<6)
				{
					JOptionPane.showMessageDialog((JFrame) m_gui,"密码长度不得小于6位",null, JOptionPane.ERROR_MESSAGE);
					return;
				}
				t_request.m_teacher=s;
				SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
				CreateUserReply t_reply=(CreateUserReply) SmartEvents.expect(CreateUserReply.class);
				if(!t_reply.m_done)
				{
					JOptionPane.showMessageDialog((JFrame) m_gui,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					XMLContainer t_table = SmartGuis.find(m_father, "teachers_table");
					StringBuilder t_sb=new StringBuilder();
					t_sb.append("<Line items='")
					.append(s.m_id).append(",")
					.append(s.m_name).append(",")
					.append(s.m_sex).append(",")
					.append(s.m_faculty_id).append("'")
					.append(" teacher_id='").append(s.m_id).append("' />");
					try 
					{
						XMLContainer t_line= SmartGuis.create(t_sb.toString());
						
						SmartGuis.add(t_table, t_line);
					} 
					catch(Exception t_exp)
					{
						t_exp.printStackTrace();
					}
					
					SmartGuis.find(m_father, "last_opt").set("text", " 新建任课老师 "+s.m_id);
					
				}
			}
		});
	}
}
