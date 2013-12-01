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
import coursesystem.reply.CreateUserReply;
import coursesystem.request.CreateUserRequest;
import coursesystem.unit.Provest;

/**
 * @author z w q
 * @create 20131130
 *
 */
public class CreateProvestDialog {
	private XMLContainer m_gui;
	private XMLContainer m_father;
	
	public CreateProvestDialog(XMLContainer p_gui) throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException
	{
		m_father=p_gui;
		m_gui=SmartGuis.create(new FileInputStream("res/dialog/CreateProvestDialog.xml"));
		assign();
	}
	
	private void assign()
	{
		JButton t_save=(JButton)SmartGuis.find(m_gui,"save");
		final JTextField t_id=(JTextField)SmartGuis.find(m_gui, "id");
		final JTextField t_name=(JTextField)SmartGuis.find(m_gui, "name");
		final JTextField t_sex=(JTextField)SmartGuis.find(m_gui, "sex");
		//final JTextField t_faculty=(JTextField)SmartGuis.find(m_gui, "faculty");
		//final JTextField t_grade=(JTextField)SmartGuis.find(m_gui, "grade");
		final JTextField t_mailbox=(JTextField)SmartGuis.find(m_gui, "mailbox");
		final JTextField t_password=(JTextField)SmartGuis.find(m_gui, "password");
		t_save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				CreateUserRequest t_request=new CreateUserRequest();
				t_request.m_type=CreateUserRequest.PROVEST;
				Provest s=new Provest();
				s.m_id=t_id.getText();
				s.m_name=t_name.getText();
				s.m_sex=t_sex.getText();
				//s.m_faculty_id=t_faculty.getText();
				//s.m_grade=t_grade.getText();
				s.m_mailbox=t_mailbox.getText();
				s.m_password=t_password.getText();
				if(s.m_password.length()<6)
				{
					JOptionPane.showMessageDialog((JFrame) m_gui,"密码长度不得小于6位",null, JOptionPane.ERROR_MESSAGE);
					return;
				}
				t_request.m_provest=s;
				SmartEvents.happen(t_request,SmartEvents.NETWORK_ONLY);
				CreateUserReply t_reply=(CreateUserReply) SmartEvents.expect(CreateUserReply.class);
				if(!t_reply.m_done)
				{
					JOptionPane.showMessageDialog((JFrame) m_gui,t_reply.m_message,"错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					XMLContainer t_table = SmartGuis.find(m_father, "provests_table");
					StringBuilder t_sb=new StringBuilder();
					t_sb.append("<Line items='")
					.append(s.m_id).append(",")
					.append(s.m_name).append(",")
					.append(s.m_sex).append("'")
					//.append(s.m_faculty_id).append(",")
					//.append(s.m_grade).append(",")
					//.append(s.m_mailbox).append(",")
					//.append(s.m_password).append(",")
					.append(" provest_id='").append(s.m_id).append("' />");
					try 
					{
						XMLContainer t_line= SmartGuis.create(t_sb.toString());
						
						SmartGuis.add(t_table, t_line);
					} 
					catch(Exception t_exp)
					{
						t_exp.printStackTrace();
					}
					
					SmartGuis.find(m_father, "last_opt").set("text", " 新建教务处老师 "+s.m_id);
					
				}
			}
		});
	}

}
