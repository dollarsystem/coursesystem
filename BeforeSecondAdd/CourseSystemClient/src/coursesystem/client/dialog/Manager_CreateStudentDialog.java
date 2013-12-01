/**
 * 
 */
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
import coursesystem.reply.Manager_UserCreateReply;
import coursesystem.request.Manager_UserCreateRequest;
import coursesystem.unit.Student;

/**
 * @author z w q
 * @create 20131130
 *
 */
public class Manager_CreateStudentDialog {
	private XMLContainer m_gui;
	private XMLContainer m_father;
	//private String m_student_id;
	
	public Manager_CreateStudentDialog(XMLContainer p_gui) throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException
	{
		m_father=p_gui;
		m_gui=SmartGuis.create(new FileInputStream("res/dialog/Manager_CreateStudentDialog.xml"));
		assign();
	}
	
	private void assign()
	{
		JButton t_save=(JButton)SmartGuis.find(m_gui,"save");
		final JTextField t_id=(JTextField)SmartGuis.find(m_gui, "id");
		final JTextField t_name=(JTextField)SmartGuis.find(m_gui, "name");
		final JTextField t_sex=(JTextField)SmartGuis.find(m_gui, "sex");
		final JTextField t_faculty=(JTextField)SmartGuis.find(m_gui, "faculty");
		final JTextField t_grade=(JTextField)SmartGuis.find(m_gui, "grade");
		final JTextField t_mailbox=(JTextField)SmartGuis.find(m_gui, "mailbox");
		final JTextField t_password=(JTextField)SmartGuis.find(m_gui, "password");
		t_save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				Manager_UserCreateRequest t_request=new Manager_UserCreateRequest();
				t_request.m_type=Manager_UserCreateRequest.STUDENT;
				Student s=new Student();
				s.m_id=t_id.getText();
				s.m_name=t_name.getText();
				s.m_sex=t_sex.getText();
				s.m_faculty_id=t_faculty.getText();
				s.m_grade=t_grade.getText();
				s.m_mailbox=t_mailbox.getText();
				s.m_password=t_password.getText();
				t_request.m_student=s;
				SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
				Manager_UserCreateReply t_reply=new Manager_UserCreateReply();
				if(!t_reply.m_done)
				{
					JOptionPane.showMessageDialog((JFrame) m_gui,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					XMLContainer t_table = SmartGuis.find(m_father, "students_table");
					StringBuilder t_sb=new StringBuilder();
					t_sb.append("<Line items=")
					.append(s.m_id).append(",")
					.append(s.m_name).append(",")
					.append(s.m_sex).append(",")
					.append(s.m_faculty_id).append(",")
					.append(s.m_grade).append(",")
					//.append(s.m_mailbox).append(",")
					//.append(s.m_password).append(",")
					.append(" student_id='").append(s.m_id).append("' bgcolor='#cbfef1'/>");
					try 
					{
						XMLContainer t_line= SmartGuis.create(t_sb.toString());
						
						SmartGuis.add(t_table, t_line);
					} 
					catch(Exception t_exp)
					{
						t_exp.printStackTrace();
					}
					
					
					
				}
			}
		});
	}
	

}
