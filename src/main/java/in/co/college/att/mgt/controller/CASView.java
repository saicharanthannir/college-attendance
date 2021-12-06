package in.co.college.att.mgt.controller;

public interface CASView {
	
	public String APP_CONTEXT = "/College-Attendance-System";

	public String LAYOUT_VIEW = "/BaseLayout.jsp";
	public String PAGE_FOLDER = "/jsp";

	public String JAVA_DOC_VIEW = APP_CONTEXT + "/doc/index.html";

	public String ERROR_VIEW = PAGE_FOLDER + "/Error.jsp";

	
	
	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";	
	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
	
	
	public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";	
	public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";
	
	public String ATTENDANCE_VIEW = PAGE_FOLDER + "/AttendanceView.jsp";	
	public String ATTENDANCE_LIST_VIEW = PAGE_FOLDER + "/AttendanceListView.jsp";
		
	public String SEMESTER_ATTENDACE_VIEW = PAGE_FOLDER + "/SemesterAttendanceView.jsp";
	public String SEMESTER_ATTENDACE_LIST_VIEW = PAGE_FOLDER + "/SemesterAttendancelistView.jsp";
	
	public String MAKE_ATTENDANCE_VIEW = PAGE_FOLDER + "/MakeAttendaceView.jsp";
	
	public String ASSIGN_FACULTY_VIEW = PAGE_FOLDER + "/AssignFacultyView.jsp";	
	public String ASSIGN_FACULTY_LIST_VIEW = PAGE_FOLDER + "/AssignFacultyListView.jsp";
	
	public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";	
	public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";
	
	public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";	
	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";
	
	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";	
	
	
	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
	public String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";
	public String TWITTER_VIEW = PAGE_FOLDER + "/Twitter.jsp";
	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";

	
	

	public String ERROR_CTL = "/ctl/ErrorCtl";


	
	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/facultyList";
	
	public String ATTENDANCE_CTL = APP_CONTEXT + "/ctl/attendance";
	public String ATTENDANCE_LIST_CTL = APP_CONTEXT + "/ctl/attendanceList";
	
	public String SEMESTER_ATTENDACE_LIST_CTL = APP_CONTEXT + "/ctl/semesterAttendanceList";
	
	public String STUDENT_CTL = APP_CONTEXT + "/ctl/student";
	public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/studentList";
	
	public String COURSE_CTL = APP_CONTEXT + "/ctl/course";
	public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/courseList";
	
	public String ASSIGN_FACULTY_CTL = APP_CONTEXT + "/ctl/assignFaculty";
	public String ASSIGN_FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/assignFacultyList";
	
	public String SUBJECT_CTL = APP_CONTEXT + "/ctl/subject";
	public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectList1";
	
	public String FACULTY_CTL = APP_CONTEXT + "/ctl/faculty";

	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/userRegistration";
	public String FACULTY_REGISTRATION_CTL = APP_CONTEXT + "/ctl/faculty";
	public String LOGIN_CTL = APP_CONTEXT + "/login";
	public String WELCOME_CTL = APP_CONTEXT + "/welcome";
	public String TWITTER_CTL = APP_CONTEXT + "/twitter";
	public String LOGOUT_CTL = APP_CONTEXT + "/login";
	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/changePassword";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/myProfile";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";
}
