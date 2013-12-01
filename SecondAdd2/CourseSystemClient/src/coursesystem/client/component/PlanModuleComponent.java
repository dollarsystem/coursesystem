package coursesystem.client.component;

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
import coursesystem.client.dialog.CourseDetailDialog;
import coursesystem.client.event.PlanModuleChangedEvent;
import coursesystem.client.handler.PlanModuleAddHandler;
import coursesystem.client.handler.PlanModuleSearchHandler;
import zjs.smartevents.SmartEvents;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class PlanModuleComponent
{
	
	private XMLContainer m_gui;
	private String m_dean_id;

	public PlanModuleComponent(String p_module,String p_dean_id) throws InstantiationException,IllegalAccessException,FileNotFoundException,ParserConfigurationException,SAXException,IOException, NoSuchMethodException
	{
		m_gui=SmartGuis.create(new FileInputStream("res/component/PlanModuleComponent.xml"));
		m_dean_id=p_dean_id;
		arrange(p_module);
		assign();
		bind();
	}
	
	public XMLContainer getGui()
	{
		return m_gui;
	}
	
	private void arrange(String p_module) throws InstantiationException, IllegalAccessException, SAXException, IOException, ParserConfigurationException
	{
		int t_index_1=p_module.indexOf(":");
		int t_index_2=p_module.indexOf("#");
		String t_name=p_module.substring(0,t_index_1);
		String t_markrange=p_module.substring(t_index_1+1,t_index_2);
		String t_types=p_module.substring(t_index_2+1);
		m_gui.set("title",t_name);
		XMLContainer t_types_combo=SmartGuis.find(m_gui,"planmodule_types");
		StringBuilder t_sb=new StringBuilder();
		for(String t_type:t_types.split(","))
		{
			t_sb.append("<Option text='").append(t_type).append("'/>");
			XMLContainer t_option=SmartGuis.create(t_sb.toString());
			SmartGuis.add(t_types_combo,t_option);
			t_sb.setLength(0);
		}
		XMLContainer t_mark_range=SmartGuis.find(m_gui,"mark_range");
		t_mark_range.set("text","学分区间："+t_markrange);
		String[] t_min_max=t_markrange.split("-");
		t_mark_range.set("min_marks",t_min_max[0]);
		t_mark_range.set("max_marks",t_min_max[1]);
	}
	
	private void assign()
	{
		final JButton t_search=(JButton)SmartGuis.find(m_gui,"planmodule_search");
		t_search.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				PlanModuleSearchHandler.onHandle(m_gui,m_dean_id);
			}
		});
		final JTable t_planmodule_table=(JTable)SmartGuis.find(m_gui,"planmodule_table");
		final JButton t_detail=(JButton)SmartGuis.find(m_gui,"planmodule_detail");
		final JButton t_add=(JButton)SmartGuis.find(m_gui,"planmodule_add");
		t_planmodule_table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent t_evt) 
			{
				String t_course_id=PlanModuleSearchHandler.getSelectedCourseId(m_gui);
				boolean t_selected=t_course_id!=null;
				t_detail.setEnabled(t_selected);
				t_add.setEnabled(t_selected);
				if(t_selected)
					t_add.setText(PlanModuleSearchHandler.isSelectedCourseAdded(m_gui)?" 删 除 ":" 添 加 ");
			}
		});
		t_detail.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				try
				{
					new CourseDetailDialog(PlanModuleSearchHandler.getSelectedCourseId(m_gui));
				} 
				catch(Exception t_exp)
				{
					t_exp.printStackTrace();
				}
			}
		});
		t_add.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t_evt)
			{
				String t_course_id=PlanModuleSearchHandler.getSelectedCourseId(m_gui);
				boolean t_add_unadd=!PlanModuleSearchHandler.isSelectedCourseAdded(m_gui);
				PlanModuleAddHandler.onHandle(m_gui,m_dean_id,t_course_id,t_add_unadd,getSelectedType());
			}
		});
	}
	
	private void bind() throws NoSuchMethodException
	{
		SmartEvents.bind(PlanModuleChangedEvent.class,this,"onPlanModuleChanged");
	}
	
	public void onPlanModuleChanged(PlanModuleChangedEvent p_event)
	{
		if(getSelectedType().equals(p_event.m_type))
			PlanModuleSearchHandler.onHandle(m_gui,m_dean_id);
	}
	
	public String getSelectedType()
	{
		@SuppressWarnings("unchecked")
		JComboBox<String> t_type=(JComboBox<String>)SmartGuis.find(m_gui,"planmodule_types");
		return t_type.getSelectedItem().toString();
	}
	
}
