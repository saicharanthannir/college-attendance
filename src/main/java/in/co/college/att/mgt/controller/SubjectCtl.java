package in.co.college.att.mgt.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.college.att.mgt.bean.BaseBean;
import in.co.college.att.mgt.bean.CourseBean;
import in.co.college.att.mgt.bean.SubjectBean;
import in.co.college.att.mgt.exception.ApplicationException;
import in.co.college.att.mgt.exception.DuplicateRecordException;
import in.co.college.att.mgt.model.CourseModel;
import in.co.college.att.mgt.model.SubjectModel;
import in.co.college.att.mgt.util.DataUtility;
import in.co.college.att.mgt.util.DataValidator;
import in.co.college.att.mgt.util.PropertyReader;
import in.co.college.att.mgt.util.ServletUtility;



/**
 * Servlet implementation class SubjectCtl
 */
@WebServlet(name = "SubjectCtl", urlPatterns = { "/ctl/subject" })
public class SubjectCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(SubjectCtl.class);
	@Override
	protected void preload(HttpServletRequest request) {
		log.debug("SubjectCtl preload method start");
		CourseModel model = new CourseModel();
		try {
			List l = model.list();
			request.setAttribute("courseList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}
		log.debug("SubjectCtl preload method end");
	}
	
	
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("AssignFacultyCtl Method validate Started");

		boolean pass = true;

		
		

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectCode"))) {
			request.setAttribute("subjectCode", PropertyReader.getValue("error.require", "Subject Code"));
			pass = false;
		}

		

		

		if ("-----Select-----".equalsIgnoreCase(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}
		
		if ("-----Select-----".equalsIgnoreCase(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description",
					PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}
		
		

		log.debug("SubjectCtl Method validate Ended");

		return pass;
	}
	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("SubjectCtl Method populatebean Started");

		SubjectBean bean = new SubjectBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setName(DataUtility.getString(request	.getParameter("name")));

		bean.setSemester(DataUtility.getString(request.getParameter("semester")));
		bean.setSubjectCode(DataUtility.getString(request.getParameter("subjectCode")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		

		populateDTO(bean, request);

		log.debug("SubjectCtl Method populatebean Ended");

		return bean;
	}
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("StudentCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
        // get model
        
		SubjectModel model = new SubjectModel();
        
		long id = DataUtility.getLong(request.getParameter("id"));
       
		if (id > 0 || op != null) {
          
            
			SubjectBean bean;
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
        log.debug("StudentCtl Method doGet Ended");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("SubjectCtl Method doPost Started");
        String op = DataUtility.getString(request.getParameter("operation"));
        // get model
        SubjectModel model = new SubjectModel();
        long id = DataUtility.getLong(request.getParameter("id"));
        if (OP_SAVE.equalsIgnoreCase(op)) {
            SubjectBean bean = (SubjectBean) populateBean(request);
           
            
            try {
            	
            	CourseModel cModel=new CourseModel();
             	CourseBean cBean= cModel.findByPK(bean.getCourseId());
            	 bean.setCourseName(cBean.getName());
            	 
                if (id > 0) {
                	
                    model.update(bean);
                    ServletUtility.setSuccessMessage("Data is successfully Updated", request);
                } else {
                	
                    long pk = model.add(bean);
                    ServletUtility.setSuccessMessage("Data is successfully saved",request);
                }

               
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage(e.getMessage(), request);
            }
            ServletUtility.forward(getView(), request, response);
        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            SubjectBean bean = (SubjectBean) populateBean(request);
            try {
                model.delete(bean);
                ServletUtility.redirect(CASView.SUBJECT_LIST_CTL, request,
                        response);
                return;
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
              
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
        	ServletUtility.redirect(CASView.SUBJECT_LIST_CTL, request, response);
        	
        }else if (OP_RESET.equalsIgnoreCase(op)) {
    		ServletUtility.redirect(CASView.SUBJECT_CTL, request, response);
    		return;
    }
    				
    		
        ServletUtility.forward(getView(), request, response);
        

        log.debug("SubjectCtl Method doPostEnded");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return CASView.SUBJECT_VIEW;
	}

}
