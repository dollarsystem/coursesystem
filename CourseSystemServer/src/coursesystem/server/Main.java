package coursesystem.server;

import zjs.smartevents.SmartEvents;
import coursesystem.request.CourseDetailRequest;
import coursesystem.request.LoginRequest;
import coursesystem.request.StudentCoursesRequest;
import coursesystem.request.StudentInfosRequest;
import coursesystem.request.StudentQuitCourseRequest;
import coursesystem.server.database.Database;
import coursesystem.server.handler.CourseDetailHandler;
import coursesystem.server.handler.LoginHandler;
import coursesystem.server.handler.StudentCoursesHandler;
import coursesystem.server.handler.StudentInfosHandler;
import coursesystem.server.handler.StudentQuitCourseHandler;

public class Main
{

	public static void main(String[] args) throws Exception
	{
		SmartEvents.bind(LoginRequest.class,LoginHandler.class,"onHandle");
		SmartEvents.bind(StudentInfosRequest.class,StudentInfosHandler.class,"onHandle");
		SmartEvents.bind(StudentCoursesRequest.class,StudentCoursesHandler.class,"onHandle");
		SmartEvents.bind(CourseDetailRequest.class,CourseDetailHandler.class,"onHandle");
		SmartEvents.bind(StudentQuitCourseRequest.class,StudentQuitCourseHandler.class,"onHandle");
		SmartEvents.acceptShareApply(5000);
		Database.init();
		Thread.sleep(100000);
	}

}
