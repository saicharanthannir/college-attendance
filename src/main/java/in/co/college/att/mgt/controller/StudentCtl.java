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
import in.co.college.att.mgt.bean.StudentBean;
import in.co.college.att.mgt.exception.ApplicationException;
import in.co.college.att.mgt.exception.DuplicateRecordException;
import in.co.college.att.mgt.model.CourseModel;
import in.co.college.att.mgt.model.StudentModel;
import in.co.college.att.mgt.util.DataUtility;
import in.co.college.att.mgt.util.DataValidator;
import in.co.college.att.mgt.util.PropertyReader;
import in.co.college.att.mgt.util.ServletUtility;


/**
 * Servlet implementation class StudentCtl
 */
/**
 * Student functionality Controller. Performs operation for add, update, delete
 * and get Student
 * 
 * @author Navigable Set
 * @version 1.0
 * @Copyright (c) Navigable Set
 */
@ WebServlet(name="StudentCtl",urlPatterns={"/ctl/student"})
public class StudentCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(StudentCtl.class);
	/**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request
	 */
    @Override
    protected void preload(HttpServletRequest request) {
    	log.debug("StudentCtl preload method start");
    	CourseModel model = new CourseModel();
        try {
            List l = model.list();
            request.setAttribute("courseList", l);
        } catch (ApplicationException e) {
            log.error(e);
        }
        log.debug("StudentCtl preload method end");
    }
    /**
	 * Validate input Data Entered By User
	 * 
	 * @param request
	 * @return
	 */
    @Override
    protected boolean validate(HttpServletRequest request) {
    	log.debug("StudentCtl validate method start");
        boolean pass = true;

        
        
        if (DataValidator.isNull(request.getParameter("firstName"))) {
            request.setAttribute("firstName",PropertyReader.getValue("error.require", "First Name"));
            pass = false;
        }else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName",PropertyReader.getValue("error.name", "First Name"));
			pass = false;
		}

        if (DataValidator.isNull(request.getParameter("lastName"))) {
            request.setAttribute("lastName",PropertyReader.getValue("error.require", "Last Name"));
            pass = false;
            
        }else if (!DataValidator.isName(request.getParameter("lastName"))) 
        {
			request.setAttribute("lastName",PropertyReader.getValue("error.name", "LastName"));
			pass = false;
		}

        if (DataValidator.isNull(request.getParameter("mobileNo"))) 
        {
            request.setAttribute("mobileNo",PropertyReader.getValue("error.require", "Mobile No"));
            pass = false;
            
        }else if(!DataValidator.isPhoneNo(request.getParameter("mobileNo")))
        {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.invalid","MobileNo"));
			pass=false;		
		}
        
        if (DataValidator.isNull(request.getParameter("fmobileNo"))) 
        {
            request.setAttribute("fmobileNo",PropertyReader.getValue("error.require", "Father Mobile No"));
            pass = false;
            
        }else if(!DataValidator.isPhoneNo(request.getParameter("fmobileNo")))
        {
			request.setAttribute("fmobileNo", PropertyReader.getValue("error.invalid","Father MobileNo"));
			pass=false;		
		}
        
        if (DataValidator.isNull(request.getParameter("email"))) 
        {
            request.setAttribute("email",PropertyReader.getValue("error.require", "Email "));
            pass = false;
        } else if (!DataValidator.isEmail(request.getParameter("email"))) {
            request.setAttribute("email",PropertyReader.getValue("error.email", "Email "));
            pass = false;
        }
        
        if (DataValidator.isNull(request.getParameter("fatherEmail"))) 
        {
            request.setAttribute("fatherEmail",PropertyReader.getValue("error.require", "Father Email "));
            pass = false;
        } else if (!DataValidator.isEmail(request.getParameter("fatherEmail"))) {
            request.setAttribute("fatherEmail",PropertyReader.getValue("error.email", "Father Email"));
            pass = false;
        }
        
        if("-----Select-----".equalsIgnoreCase(request.getParameter("courseId"))){
        	request.setAttribute("courseId",PropertyReader.getValue("error.require", "Course Name"));
        	pass=false;
        }
        if("-----Select-----".equalsIgnoreCase(request.getParameter("semester"))){
        	request.setAttribute("semester",PropertyReader.getValue("error.require", "Semester Name"));
        	pass=false;
        }
        if (DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob",PropertyReader.getValue("error.require", "Date Of Birth"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("dob"))) {
            request.setAttribute("dob","Date is require");
            pass = false;
        }	
        if("-----Select-----".equalsIgnoreCase(request.getParameter("gender"))){
        	request.setAttribute("gender",PropertyReader.getValue("error.require", "Gender"));
        	pass=false;
        }


        log.debug("StudentCtl validate method end");
        return pass;
    }
    /**
	 * Populates bean object from request parameters
	 * 
	 * @param request
	 * @return
	 */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("StudentCtl Method populatebean Started");

        StudentBean bean = new StudentBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));

        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
        
        bean.setGender(DataUtility.getString(request.getParameter("gender")));
        bean.setSemester(DataUtility.getString(request.getParameter("semester")));
  
        bean.setDob(DataUtility.getDate1(request.getParameter("dob")));

        bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        bean.setFatherMobileNo(DataUtility.getString(request.getParameter("fmobileNo")));

        bean.setEmailId(DataUtility.getString(request.getParameter("email")));
        bean.setFatherEmailId(DataUtility.getString(request.getParameter("fatherEmail")));

        bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

        populateDTO(bean, request);

        log.debug("StudentCtl Method populatebean Ended");

        return bean;
    }

    /**
     * Contains Display logics
     */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("StudentCtl Method doGet Started");
        System.out.println("in do get");
        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));

        // get model
        ServletUtility.setOpration("Add", request);
        StudentModel model = new StudentModel();
        if (id > 0 || op != null) {
            StudentBean bean;
            try {
                bean = model.findByPK(id);
                ServletUtility.setOpration("Edit", request);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
        log.debug("StudentCtl Method doGett Ended");
    }

    /**
     * Contains Submit logics
     */
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("StudentCtl Method doPost Started");
        System.out.println("in do post");
        String op = DataUtility.getString(request.getParameter("operation"));

        // get model

        StudentModel model = new StudentModel();

        long id = DataUtility.getLong(request.getParameter("id"));
        ServletUtility.setOpration("Add", request);
       
        if (OP_SAVE.equalsIgnoreCase(op)) {
            StudentBean bean = (StudentBean) populateBean(request);
            try {
            	 CourseModel cModel=new CourseModel();
                 CourseBean cBean=cModel.findByPK(bean.getCourseId());
                 bean.setCourseName(cBean.getName());
                if (id > 0) {
                	 StudentBean sBean=model.findByPK(id);
                	 bean.setRollNo(sBean.getRollNo());
                	 bean.setUserId(sBean.getUserId());
                    model.update(bean);
                    ServletUtility.setSuccessMessage("Data is successfully Updated",request);
                    ServletUtility.setOpration("Edit", request);
                    ServletUtility.setBean(bean, request);
                } else {
                    long pk = model.add(bean);
                   // bean.setId(pk);
                    ServletUtility.setSuccessMessage("Data is successfully saved",request);
                }

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Student Email Id already exists", request);
            }
            ServletUtility.forward(getView(), request, response);
        }

        else if (OP_DELETE.equalsIgnoreCase(op)) {

            StudentBean bean = (StudentBean) populateBean(request);
            try {
                model.delete(bean);
                ServletUtility.redirect(CASView.STUDENT_LIST_CTL, request,response);
                return;

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
        	 
        	ServletUtility.redirect(CASView.STUDENT_LIST_CTL, request, response);


        }else if (OP_RESET.equalsIgnoreCase(op)) {
    		ServletUtility.redirect(CASView.STUDENT_CTL, request, response);
    		return;
    }
        
        log.debug("StudentCtl Method doPost Ended");
	}
	/**
	 * Returns the VIEW page of this Controller
	 * 
	 * @return
	 */
	@Override
    protected String getView() {
     return CASView.STUDENT_VIEW;
    }

}
