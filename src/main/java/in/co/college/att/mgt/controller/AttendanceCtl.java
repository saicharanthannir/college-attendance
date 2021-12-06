package in.co.college.att.mgt.controller;

import java.io.IOException;
import java.util.Iterator;
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
import in.co.college.att.mgt.bean.CourseBean;
import in.co.college.att.mgt.bean.StudentBean;
import in.co.college.att.mgt.bean.SubjectBean;
import in.co.college.att.mgt.bean.UserBean;
import in.co.college.att.mgt.exception.ApplicationException;
import in.co.college.att.mgt.exception.DatabaseException;
import in.co.college.att.mgt.exception.DuplicateRecordException;
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
 * Servlet implementation class AttendanceCtl
 */
@WebServlet(name = "AttendanceCtl", urlPatterns = { "/ctl/attendance" })
public class AttendanceCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(AttendanceCtl.class);
	@Override
	protected void preload(HttpServletRequest request) {
		log.debug("AttendanceCtl preload method start");
		AssignFacultyModel sModel=new AssignFacultyModel();
		try {
			HttpSession session=request.getSession();
			UserBean uBean=(UserBean)session.getAttribute("user");
			if(uBean.getRoleId()==2) {
				AssignFacultyBean bean=new AssignFacultyBean();
				bean.setUserId(uBean.getId());
			List l2 =sModel.search(bean);
			request.setAttribute("subjectList", l2);
			}
		} catch (ApplicationException e) {
			log.error(e);
		}
		log.debug("AttendanceCtl preload method end");
	}
	
	
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("AttendanceCtl Method validate Started");

		boolean pass = true;


		
		
		if ("-----Select-----".equalsIgnoreCase(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}
		
		

		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date",
					PropertyReader.getValue("error.require", "Date"));
			pass = false;
		}
		
		

		log.debug("AttendanceCtl Method validate Ended");

		return pass;
	}
	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("AttendanceCtl Method populatebean Started");

		AttendaceBean bean = new AttendaceBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setDate(DataUtility.getDate(request.getParameter("date")));
		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		

		populateDTO(bean, request);

		log.debug("AttendanceCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("AttendanceCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
        // get model
        
		AttendanceModel model = new AttendanceModel();
        
		long id = DataUtility.getLong(request.getParameter("id"));
       
		if (id > 0 || op != null) {
          
            
			AttendaceBean bean;
            try {
                bean = model.findByPK(id);
             
                ServletUtility.setBean(bean, request);
            
            } catch (ApplicationException e) {
                log.error(e);
            
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
        log.debug("AttendanceCtl Method doGet Ended");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("SubjectCtl Method doPost Started");
        String op = DataUtility.getString(request.getParameter("operation"));
        // get model
        AttendanceModel model = new AttendanceModel();
        AttendaceBean bean = (AttendaceBean) populateBean(request);
        
        SubjectModel sModel=new SubjectModel();
        long id = DataUtility.getLong(request.getParameter("id"));
        if (OP_SUBMIT.equalsIgnoreCase(op)) {
        	
        	
        	try {
        		
        		AttendaceBean attBean=model.findBySubAndDate(bean.getStudentId(),bean.getDate());
        		if(attBean==null) {
        			
        		SubjectBean sBean=sModel.findByPK(bean.getSubjectId());
				bean.setCourseName(sBean.getCourseName());
				bean.setSemester(sBean.getSemester());
				bean.setStudentName(sBean.getName());
				
				StudentBean stBean=new StudentBean();
				StudentModel stModel=new StudentModel();
				stBean.setCourseId(sBean.getCourseId());
				stBean.setSemester(sBean.getSemester());
				List list=stModel.search(stBean);
				
				if (list == null || list.size() == 0) {
					ServletUtility.setErrorMessage("No studnent found in this course ", request);
				}
				ServletUtility.setList(list, request);
				ServletUtility.setBean(bean, request);
			
        	ServletUtility.forward(CASView.MAKE_ATTENDANCE_VIEW, request, response);
        	return;
        	}else {
        		ServletUtility.setErrorMessage("Attendance already Exist In this Date", request);
        		
        	}
        	} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else if (OP_DELETE.equalsIgnoreCase(op)) {

        	
            try {
                model.delete(bean);
                ServletUtility.redirect(CASView.ATTENDANCE_LIST_CTL, request,
                        response);
                return;
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
              
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
        	ServletUtility.redirect(CASView.ATTENDANCE_LIST_CTL, request, response);
        	
        }else if (OP_RESET.equalsIgnoreCase(op)) {
    		ServletUtility.redirect(CASView.ATTENDANCE_CTL, request, response);
    		return;
    }else if(OP_SAVE.equalsIgnoreCase(op)) {
    	
    	HttpSession session=request.getSession();
		UserBean uBean=(UserBean)session.getAttribute("user");
    	
    	
		try {
			SubjectBean sBean=sModel.findByPK(bean.getSubjectId());
			bean.setCourseName(sBean.getCourseName());
			bean.setSemester(sBean.getSemester());
			bean.setStudentName(sBean.getName());
			
			StudentBean stBean=new StudentBean();
			StudentModel stModel=new StudentModel();
			stBean.setCourseId(sBean.getCourseId());
			stBean.setSemester(sBean.getSemester());
			bean.setSubjectName(sBean.getName());
		
			AssignFacultyModel aModel=new AssignFacultyModel();
			AssignFacultyBean aBean = aModel.findBySubNameAndFac(uBean.getId(),bean.getSubjectId());
			bean.setTotalClass(aBean.getTotalClass());
			
			List list=stModel.search(stBean);
			
	    	int index=1;
	    	Iterator<StudentBean>it=list.iterator();
	    	while (it.hasNext()) {
				 stBean = it.next();
				 bean.setStudentId(stBean.getId());
				 bean.setStudentName(stBean.getFirstName()+" "+stBean.getLastName());
				 long status=DataUtility.getLong(request.getParameter("status"+index));
				 bean.setStatus(status);
				 int total=model.MaxTotal(stBean.getId(),bean.getSubjectId());
				 bean.setTotal(total+status);
				 long per=(bean.getTotal()/Long.parseLong(bean.getTotalClass())*100);
				 bean.setPercentage(String.valueOf(per));
				 bean.setUserId(stBean.getUserId());
				 model.add(bean);
				 index=index+1;
			}
	    	ServletUtility.setSuccessMessage("Attendance Insert Succsessfully", request);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }	
        ServletUtility.forward(getView(), request, response);
        

        log.debug("SubjectCtl Method doPostEnded");
	}
	

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return CASView.ATTENDANCE_VIEW;
	}

}
