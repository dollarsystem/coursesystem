package coursesystem.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import coursesystem.unit.Course;
import coursesystem.unit.Dean;
import coursesystem.unit.Faculty;
import coursesystem.unit.Frame;
import coursesystem.unit.Manager;
import coursesystem.unit.Notice;
import coursesystem.unit.Plan;
import coursesystem.unit.Provest;
import coursesystem.unit.Student;
import coursesystem.unit.Teacher;

public final class Database
{

	private static Connection sm_connect;
	private static Statement sm_state;
	
	public static void init() throws ClassNotFoundException,SQLException
	{
		Class.forName("org.sqlite.JDBC");
	    sm_connect=DriverManager.getConnection("jdbc:sqlite:CourseSystemServer.db");
	    sm_state=sm_connect.createStatement();
	    CourseTable.init();
	    DeanTable.init();
	    FacultyTable.init();
	    FrameTable.init();
	    ManagerTable.init();
	    NoticeTable.init();
	    PlanTable.init();
	    ProvestTable.init();
	    StudentTable.init();
	    TeacherTable.init();
	}
	
	public static void exit() throws SQLException
	{
		sm_state.close();
		sm_connect.close();
	}
	
	public synchronized static Course getCourse(String p_course_id) throws SQLException
	{
		return CourseTable.getCourse(p_course_id);
	}
	
	public synchronized static void setCourse(Course p_course) throws SQLException
	{
		CourseTable.setCourse(p_course);
	}
	
	public synchronized static void removeCourse(String p_course_id) throws SQLException
	{
		CourseTable.removeCourse(p_course_id);
	}
	
	public synchronized static Dean getDean(String p_dean_id) throws SQLException
	{
		return DeanTable.getDean(p_dean_id);
	}
	
	public synchronized static void setDean(Dean p_dean) throws SQLException
	{
		DeanTable.setDean(p_dean);
	}
	
	public synchronized static void removeDean(String p_dean_id) throws SQLException
	{
		DeanTable.removeDean(p_dean_id);
	}
	
	public synchronized static Faculty getFaculty(String p_faculty_id) throws SQLException
	{
		return FacultyTable.getFaculty(p_faculty_id);
	}
	
	public synchronized static void setFaculty(Faculty p_faculty) throws SQLException
	{
		FacultyTable.setFaculty(p_faculty);
	}
	
	public synchronized static void removeFaculty(String p_faculty_id) throws SQLException
	{
		FacultyTable.removeFaculty(p_faculty_id);
	}
	
	public synchronized static Frame getFrame(String p_frame_id) throws SQLException
	{
		return FrameTable.getFrame(p_frame_id);
	}
	
	public synchronized static void setFrame(Frame p_frame) throws SQLException
	{
		FrameTable.setFrame(p_frame);
	}
	
	public synchronized static void removeFrame(String p_frame_id) throws SQLException
	{
		FrameTable.removeFrame(p_frame_id);
	}
	
	public synchronized static Manager getManager(String p_manager_id) throws SQLException
	{
		return ManagerTable.getManager(p_manager_id);
	}
	
	public synchronized static void setManager(Manager p_manager) throws SQLException
	{
		ManagerTable.setManager(p_manager);
	}
	
	public synchronized static void removeManager(String p_manager_id) throws SQLException
	{
		ManagerTable.removeManager(p_manager_id);
	}
	
	public synchronized static Notice getNotice(String p_notice_id) throws SQLException
	{
		return NoticeTable.getNotice(p_notice_id);
	}
	
	public synchronized static void setNotice(Notice p_notice) throws SQLException
	{
		NoticeTable.setNotice(p_notice);
	}
	
	public synchronized static void removeNotice(String p_notice_id) throws SQLException
	{
		NoticeTable.removeNotice(p_notice_id);
	}
	
	public synchronized static Plan getPlan(String p_plan_id) throws SQLException
	{
		return PlanTable.getPlan(p_plan_id);
	}
	
	public synchronized static void setPlan(Plan p_plan) throws SQLException
	{
		PlanTable.setPlan(p_plan);
	}
	
	public synchronized static void removePlan(String p_plan_id) throws SQLException
	{
		PlanTable.removePlan(p_plan_id);
	}
	
	public synchronized static Provest getProvest(String p_provest_id) throws SQLException
	{
		return ProvestTable.getProvest(p_provest_id);
	}
	
	public synchronized static void setProvest(Provest p_provest) throws SQLException
	{
		ProvestTable.setProvest(p_provest);
	}
	
	public synchronized static void removeProvest(String p_provest_id) throws SQLException
	{
		ProvestTable.removeProvest(p_provest_id);
	}
	
	public synchronized static Student getStudent(String p_student_id) throws SQLException
	{
		return StudentTable.getStudent(p_student_id);
	}
	
	public synchronized static void setStudent(Student p_student) throws SQLException
	{
		StudentTable.setStudent(p_student);
	}
	
	public synchronized static void removeStudent(String p_student_id) throws SQLException
	{
		StudentTable.removeStudent(p_student_id);
	}
	
	public synchronized static Teacher getTeacher(String p_teacher_id) throws SQLException
	{
		return TeacherTable.getTeacher(p_teacher_id);
	}
	
	public synchronized static void setTeacher(Teacher p_teacher) throws SQLException
	{
		TeacherTable.setTeacher(p_teacher);
	}
	
	public synchronized static void removeTeacher(String p_teacher_id) throws SQLException
	{
		TeacherTable.removeTeacher(p_teacher_id);
	}
	
	protected synchronized static ResultSet execute(String p_sql) throws SQLException
	{
		sm_state.execute(p_sql);
		if(p_sql.toLowerCase().startsWith("select"))
			return sm_state.getResultSet();
		return null;
	}
	
}
