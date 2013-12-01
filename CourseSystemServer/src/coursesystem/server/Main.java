package coursesystem.server;

import zjs.smartevents.SmartEvents;
import coursesystem.request.AllCoursesInitRequest;
import coursesystem.request.AllCoursesRequest;
import coursesystem.request.ChangeMailBoxRequest;
import coursesystem.request.ChangePasswordRequest;
import coursesystem.request.CourseDetailRequest;
import coursesystem.request.DeanCoursesRequest;
import coursesystem.request.FacultyDetailRequest;
import coursesystem.request.LoginRequest;
import coursesystem.request.NoticeDetailRequest;
import coursesystem.request.NoticesRequest;
import coursesystem.request.SelectCourseRequest;
import coursesystem.request.SendNoticeRequest;
import coursesystem.request.StudentCoursesRequest;
import coursesystem.request.StudentInfosRequest;
import coursesystem.request.QuitCourseRequest;
import coursesystem.request.StudentScoresRequest;
import coursesystem.request.StudyProgressRequest;
import coursesystem.request.TeacherDetailRequest;
import coursesystem.server.database.Database;
import coursesystem.server.handler.AllCoursesHandler;
import coursesystem.server.handler.AllCoursesInitHandler;
import coursesystem.server.handler.ChangeMailBoxHandler;
import coursesystem.server.handler.ChangePasswordHandler;
import coursesystem.server.handler.CourseDetailHandler;
import coursesystem.server.handler.DeanCoursesHandler;
import coursesystem.server.handler.FacultyDetailHandler;
import coursesystem.server.handler.LoginHandler;
import coursesystem.server.handler.NoticeDetailHandler;
import coursesystem.server.handler.NoticesHandler;
import coursesystem.server.handler.SelectCourseHandler;
import coursesystem.server.handler.SendNoticeHandler;
import coursesystem.server.handler.StudentCoursesHandler;
import coursesystem.server.handler.StudentInfosHandler;
import coursesystem.server.handler.QuitCourseHandler;
import coursesystem.server.handler.StudentScoresHandler;
import coursesystem.server.handler.StudyProgressHandler;
import coursesystem.server.handler.TeacherDetailHandler;

public class Main
{

	public static void main(String[] args) throws Exception
	{
		SmartEvents.bind(LoginRequest.class,LoginHandler.class,"onHandle");
		SmartEvents.bind(StudentInfosRequest.class,StudentInfosHandler.class,"onHandle");
		SmartEvents.bind(StudentCoursesRequest.class,StudentCoursesHandler.class,"onHandle");
		SmartEvents.bind(StudentScoresRequest.class,StudentScoresHandler.class,"onHandle");
		SmartEvents.bind(CourseDetailRequest.class,CourseDetailHandler.class,"onHandle");
		SmartEvents.bind(FacultyDetailRequest.class,FacultyDetailHandler.class,"onHandle");
		SmartEvents.bind(TeacherDetailRequest.class,TeacherDetailHandler.class,"onHandle");
		SmartEvents.bind(QuitCourseRequest.class,QuitCourseHandler.class,"onHandle");
		SmartEvents.bind(AllCoursesInitRequest.class,AllCoursesInitHandler.class,"onHandle");
		SmartEvents.bind(AllCoursesRequest.class,AllCoursesHandler.class,"onHandle");
		SmartEvents.bind(SelectCourseRequest.class,SelectCourseHandler.class,"onHandle");
		SmartEvents.bind(ChangePasswordRequest.class,ChangePasswordHandler.class,"onHandle");
		SmartEvents.bind(ChangeMailBoxRequest.class,ChangeMailBoxHandler.class,"onHandle");
		SmartEvents.bind(StudyProgressRequest.class,StudyProgressHandler.class,"onHandle");
		SmartEvents.bind(NoticesRequest.class,NoticesHandler.class,"onHandle");
		SmartEvents.bind(NoticeDetailRequest.class,NoticeDetailHandler.class,"onHandle");
		SmartEvents.bind(SendNoticeRequest.class,SendNoticeHandler.class,"onHandle");
		SmartEvents.bind(DeanCoursesRequest.class,DeanCoursesHandler.class,"onHandle");
		SmartEvents.acceptShareApply(5000);
		Database.init();
		Thread.sleep(10000000);
	}

}
