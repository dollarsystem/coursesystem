package coursesystem.client;

import javax.swing.UIManager;
import zjs.smartevents.SmartEvents;
import coursesystem.client.dialog.NewNoticeDialog;
import coursesystem.client.dialog.NoticeDetailDialog;
import coursesystem.client.dialog.StudyProgressDialog;
import coursesystem.client.frame.DeanFrame;
import coursesystem.client.frame.LoginFrame;
import coursesystem.client.frame.StudentFrame;

public class Main
{

	public static void main(String[] args) throws Exception
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		SmartEvents.shareWith("localhost",5000);
		//new LoginFrame();
		//new StudentFrame("121250224");
		//new StudentFrame("121250226");
		//new NewNoticeDialog("student","121250224","周坚石","");
		//new NoticeDetailDialog("n1","teacher","rentw");
		new DeanFrame("chenlin");
	}
	
	/*
	public static void onClose(UnSharedEvent p)
	{
		System.out.println("close");
	}
*/
}
