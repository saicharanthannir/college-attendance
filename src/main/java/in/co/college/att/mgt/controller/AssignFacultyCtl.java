package in.co.college.att.mgt.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.college.att.mgt.bean.AssignFacultyBean;
import in.co.college.att.mgt.bean.BaseBean;
import in.co.college.att.mgt.bean.CourseBean;
import in.co.college.att.mgt.bean.SubjectBean;
import in.co.college.att.mgt.bean.UserBean;
import in.co.college.att.mgt.exception.ApplicationException;
import in.co.college.att.mgt.exception.DuplicateRecordException;
import in.co.college.att.mgt.model.AssignFacultyModel;
import in.co.college.att.mgt.model.CourseModel;
import in.co.college.att.mgt.model.SubjectModel;
import in.co.college.att.mgt.model.UserModel;
import in.co.college.att.mgt.util.DataUtility;
import in.co.college.att.mgt.util.DataValidator;
import in.co.college.att.mgt.util.PropertyReader;
import in.co.college.att.mgt.util.ServletUtility;

/**
 * Servlet implementation class AssignFacultyCtl
 */
@WebServlet(name = "AssignFacultyCtl", urlPatterns = { "/ctl/assignFaculty" })
public class AssignFacultyCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(AssignFacultyCtl.class);
	
	@Override
	protected void preload(HttpServletRequest request) {
		log.debug("AssignFacultyCtl preload method start");
		SubjectModel sModel = new SubjectModel();
		UserModel uModel = new UserModel();
		try {
			
			List l = sModel.list();
			UserBean uBean=new UserBean();
			uBean.setRoleId(2L);
			List l2=uModel.search(uBean);
			request.setAttribute("subjectList", l);
			request.setAttribute("userList", l2);
		} catch (ApplicationException e) {
			log.error(e);
		}
		log.debug("AssignFacultyCtl preload method end");
	}
	

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("AssignFacultyCtl Method validate Started");

		boolean pass = true;

		
		

		if (DataValidator.isNull(request.getParameter("noClass"))) {
			request.setAttribute("noClass", PropertyReader.getValue("error.require", "Total Class"));
			pass = false;
		}
		

		

		

		if ("-----Select-----".equalsIgnoreCase(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}
		
		if ("-----Select-----".equalsIgnoreCase(request.getParameter("userId"))) {
			request.setAttribute("userId", PropertyReader.getValue("error.require", "Faculty Name"));
			pass = false;
		}
		

		log.debug("AssignFacultyCtl Method validate Ended");

		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("AssignFacultyCtl Method populatebean Started");

		AssignFacultyBean bean = new AssignFacultyBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));


		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		bean.setUserId(DataUtility.getLong(request.getParameter("userId")));
		
		bean.setTotalClass(DataUtility.getString(request.getParameter("noClass")));
		

		populateDTO(bean, request);

		log.debug("AssignFacultyCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("AssignFacultyCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
        // get model
        
		AssignFacultyModel model = new AssignFacultyModel();
        
		long id = DataUtility.getLong(request.getParameter("id"));
       
		if (id > 0 || op != null) {
          
            
			AssignFacultyBean bean;
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
        log.debug("AssignFacultyCtl Method doGet Ended");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("AssignFacultyCtl Method doPost Started");
        String op = DataUtility.getString(request.getParameter("operation"));
        // get model
        AssignFacultyModel model = new AssignFacultyModel();
        long id = DataUtility.getLong(request.getParameter("id"));
        if (OP_SAVE.equalsIgnoreCase(op)) {
        	AssignFacultyBean bean = (AssignFacultyBean) populateBean(request);
           
            
            try {
            	
            	UserModel cModel=new UserModel();
             	UserBean cBean= cModel.findByPK(bean.getUserId());
            	 bean.setUserName(cBean.getFirstName()+" "+cBean.getLastName());
            	 
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

        	AssignFacultyBean bean = (AssignFacultyBean) populateBean(request);
            try {
                model.delete(bean);
                ServletUtility.redirect(CASView.ASSIGN_FACULTY_LIST_CTL, request,
                        response);
                return;
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
              
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
        	ServletUtility.redirect(CASView.ASSIGN_FACULTY_LIST_CTL, request, response);
        	
        }else if (OP_RESET.equalsIgnoreCase(op)) {
    		ServletUtility.redirect(CASView.ASSIGN_FACULTY_CTL, request, response);
    		return;
    }
	
        ServletUtility.forward(getView(), request, response);
        

        log.debug("AssignFacultyCtl Method doPostEnded");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return CASView.ASSIGN_FACULTY_VIEW;
	}

}
