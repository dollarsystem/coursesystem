package coursesystem.client.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import coursesystem.client.handler.CourseScoreListHandler;
import coursesystem.client.handler.RecordScoreHandler;
import zjs.smartguis.SmartGuis;
import zjs.smartguis.XMLContainer;

public class TeacherScoreListDialog {
	private XMLContainer m_gui;
	private XMLContainer m_parent_gui;
	private String m_course_id;

	public TeacherScoreListDialog(String p_course_id, XMLContainer p_parent_gui)
			throws InstantiationException, IllegalAccessException,
			FileNotFoundException, ParserConfigurationException, SAXException,
			IOException {

		m_gui = SmartGuis.create(new FileInputStream(
				"res/dialog/TeacherScoreList.xml"));
		m_course_id = p_course_id;
		m_parent_gui = p_parent_gui;
		assign();
		CourseScoreListHandler.onHandle(m_gui, m_course_id);
	}

	private void assign() {
		JButton t_save_score = (JButton) SmartGuis.find(m_gui, "save_score");
		JButton t_input_file = (JButton) SmartGuis.find(m_gui, "input_file");

		t_save_score.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent t_evt) {
				RecordScoreHandler.onHandle(m_parent_gui, m_gui, m_course_id);
			}

		});
		// 暂时设定txt文件中分数和学号的格式为"id,score"这样
		t_input_file.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent t_evt) {
				JFrame t_frame = (JFrame) SmartGuis.find(m_gui, "frame");
				JTable t_score_list = (JTable) SmartGuis.find(m_gui,
						"score_list");
				JFileChooser t_file_chooser = new JFileChooser();
				t_file_chooser
						.addChoosableFileFilter(new ScoreFileFilter("txt"));
				int t_result = t_file_chooser.showOpenDialog(t_frame);
				if (t_result == JFileChooser.APPROVE_OPTION) {
					try {
						ArrayList<String> t_lines = new ArrayList<String>();
						BufferedReader t_br = new BufferedReader(
								new FileReader(t_file_chooser.getSelectedFile()));
						String t_line = null;

						while ((t_line = t_br.readLine()) != null) {
							t_lines.add(t_line);
						}
						t_br.close();

						// 删除特殊行
						for (int t_i = 0; t_i < t_lines.size(); t_i++) {
							String t_element = t_lines.get(t_i);
							if (!t_element.contains(",")) {
								t_lines.remove(t_i);
								t_i = t_i - 1;
							}
						}

						if (t_lines.size() != t_score_list.getRowCount()) {
							JOptionPane.showMessageDialog(t_frame,
									"导入成绩单和现有学生数目不符合", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}

						ArrayList<String> t_students = new ArrayList<String>();
						ArrayList<String> t_scores = new ArrayList<String>();
						for (String t_arr : t_lines) {
							t_students.add(t_arr.split(",")[0]);
							t_scores.add(t_arr.split(",")[1]);
						}
						XMLContainer t_score_table = SmartGuis.find(m_gui,
								"score_list");
						t_score_table.getChilds();
						for (int t_i = 0; t_i < t_score_list.getRowCount(); t_i++) {
							int t_index = t_students
									.indexOf((String) t_score_list.getValueAt(
											t_i, 0));
							t_score_list.setValueAt(t_scores.get(t_index), t_i,
									2);

						}

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		});

	}
}

class ScoreFileFilter extends FileFilter {
	private String m_ext;

	ScoreFileFilter(String p_ext) {
		m_ext = p_ext;
	}

	public boolean accept(File p_f) {
		String t_name = p_f.getName();
		int t_index = t_name.lastIndexOf(".");
		if (t_index > 0 && t_index < t_name.length() - 1) {
			String t_ext = t_name.substring(t_index + 1).toLowerCase();
			if (p_f.isDirectory() && t_ext.equals(m_ext)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		if (m_ext.equals("txt")) {
			return "Txt File (*.txt)";
		}
		return null;
	}

}
