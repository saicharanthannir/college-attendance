package in.co.college.att.mgt.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.college.att.mgt.bean.AssignFacultyBean;
import in.co.college.att.mgt.bean.AttendaceBean;
import in.co.college.att.mgt.bean.BaseBean;
import in.co.college.att.mgt.bean.StudentBean;
import in.co.college.att.mgt.bean.SubjectBean;
import in.co.college.att.mgt.bean.UserBean;
import in.co.college.att.mgt.exception.ApplicationException;
import in.co.college.att.mgt.model.AssignFacultyModel;
import in.co.college.att.mgt.model.AttendanceModel;
import in.co.college.att.mgt.model.CourseModel;
import in.co.college.att.mgt.model.StudentModel;
import in.co.college.att.mgt.model.SubjectModel;
import in.co.college.att.mgt.util.DataUtility;
import in.co.college.att.mgt.util.DataValidator;
import in.co.college.att.mgt.util.PropertyReader;
import in.co.college.att.mgt.util.ServletUtility;

/**
 * Servlet implementation class SemesterAttendanceListCtl
 */
@WebServlet(name = "SemesterAttendanceListCtl", urlPatterns = { "/ctl/semesterAttendanceList" })
public class SemesterAttendanceListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
   
	private static Logger log = Logger.getLogger(SemesterAttendanceListCtl.class);
	@Override
	protected void preload(HttpServletRequest request) {
		log.debug("AttendanceCtl preload method start");
		CourseModel sModel=new CourseModel();
		try {
		
			List l2 =sModel.list();
			request.setAttribute("courseList", l2);
			
		} catch (ApplicationException e) {
			log.error(e);
		}
		log.debug("AttendanceCtl preload method end");
	}
	
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("AttendanceCtl Method validate Started");

		boolean pass = true;


		
		
		if ("-----Select-----".equalsIgnoreCase(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}
		if ("-----Select-----".equalsIgnoreCase(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}
			log.debug("AttendanceCtl Method validate Ended");

		return pass;
	}
	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("AttendanceListCtl populateBean method start");
		AttendaceBean bean = new AttendaceBean();
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setSemester(DataUtility.getString(request.getParameter("semester")));
		log.debug("AttendanceListCtl populateBean method end");
		return bean;
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("SubjectListCtl doPost method start");
		List list = null;


		AttendaceBean bean = (AttendaceBean) populateBean(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");

		AttendanceModel model = new AttendanceModel();

		SubjectModel sModel=new SubjectModel();
		SubjectBean sBean=new SubjectBean();
		StudentModel stModel=new StudentModel();
		StudentBean stBean=new StudentBean();

			if (OP_SUBMIT.equalsIgnoreCase(op)) {

				sBean.setCourseId(bean.getCourseId());
				sBean.setSemester(bean.getSemester());
				stBean.setCourseId(bean.getCourseId());
				stBean.setSemester(bean.getSemester());
				try {
					UserBean uBean=(UserBean)request.getSession().getAttribute("user");
					if(uBean.getRoleId()==3) {
						stBean.setUserId(uBean.getId());
					}
					list=sModel.search(sBean);
					ServletUtility.setList(list, request);
					request.setAttribute("stuList",stModel.search(stBean));
				} catch (ApplicationException e) {
					ServletUtility.handleException(e, request, response);
				}
				ServletUtility.forward(CASView.SEMESTER_ATTENDACE_LIST_VIEW, request, response);
				return;
			}
		
			ServletUtility.forward(getView(), request, response);	
			log.debug("SubjectListCtl doPost method end");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return CASView.SEMESTER_ATTENDACE_VIEW;
	}

}
