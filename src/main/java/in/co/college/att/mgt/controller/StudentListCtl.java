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
import in.co.college.att.mgt.bean.StudentBean;
import in.co.college.att.mgt.exception.ApplicationException;
import in.co.college.att.mgt.model.StudentModel;
import in.co.college.att.mgt.util.DataUtility;
import in.co.college.att.mgt.util.PropertyReader;
import in.co.college.att.mgt.util.ServletUtility;



/**
 * Servlet implementation class StudentListCtl
 */

/**
 * Student List functionality Controller. Performs operation for list, search
 * and delete operations of Student
 * 
 * @author Navigable Set
 * @version 1.0
 * @Copyright (c) Navigable Set
 */

@WebServlet(name = "StudentListCtl", urlPatterns = { "/ctl/studentList" })
public class StudentListCtl extends BaseCtl{
	private static final long serialVersionUID = 1L;
	
	private static Logger log=Logger.getLogger(StudentListCtl.class);
	/**
	 * Populates bean object from request parameters
	 * 
	 * @param request
	 * @return
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("StudentListCtl populateBean method start");
		StudentBean bean = new StudentBean();

		bean.setFirstName(DataUtility.getString(request
				.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setEmailId(DataUtility.getString(request.getParameter("email")));
		log.debug("StudentListCtl populateBean method end");
		return bean;
	}

	/**
	 * Contains Display logics
	 */
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.debug("StudentListCtl doGet method start");
				List list = null;

				int pageNo = 1;

				int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

				StudentBean bean = (StudentBean) populateBean(request);

				String op = DataUtility.getString(request.getParameter("operation"));

				StudentModel model = new StudentModel();
				try {
					list = model.search(bean, pageNo, pageSize);
					ServletUtility.setList(list, request);
					if (list == null || list.size() == 0) {
						ServletUtility.setErrorMessage("No record found ", request);
					}
					ServletUtility.setList(list, request);
					ServletUtility.setSize(model.search(bean).size(), request);
					ServletUtility.setPageNo(pageNo, request);
					ServletUtility.setPageSize(pageSize, request);
					ServletUtility.forward(getView(), request, response);

				} catch (ApplicationException e) {
					ServletUtility.handleException(e, request, response);
					return;
				}
				log.debug("StudentListCtl doGet method end");
			}

	 /**
     * Contains Submit logics
     */
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.debug("StudentListCtl doPost method start");		
		List list = null;
				int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
				int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
				pageNo = (pageNo == 0) ? 1 : pageNo;
				pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
						.getValue("page.size")) : pageSize;

				StudentBean bean = (StudentBean) populateBean(request);
				String op = DataUtility.getString(request.getParameter("operation"));
				StudentModel model = new StudentModel();
				String[] ids = request.getParameterValues("ids");


				try {

					if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
							|| "Previous".equalsIgnoreCase(op)) {

						if (OP_SEARCH.equalsIgnoreCase(op)) {
							pageNo = 1;
						} else if (OP_NEXT.equalsIgnoreCase(op)) {
							pageNo++;
						} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
							pageNo--;
						}else if (OP_NEW.equalsIgnoreCase(op)) {
		                      ServletUtility.redirect(CASView.STUDENT_CTL, request, response);
		                      return;
		                  }
						
					}else if (OP_NEW.equalsIgnoreCase(op)) {
						ServletUtility.redirect(CASView.STUDENT_CTL, request, response);
						return;
					} else if (OP_DELETE.equalsIgnoreCase(op)) {
						pageNo = 1;
						if (ids != null && ids.length > 0) {
							StudentBean deletebean = new StudentBean();
							for (String id : ids) {
								deletebean.setId(DataUtility.getInt(id));
								model.delete(deletebean);
							}
							 ServletUtility.setSuccessMessage("Data Deleted Successfully" , request);
						} else {
							ServletUtility.setErrorMessage(
									"Select at least one record", request);
						}
					}else if (OP_RESET.equalsIgnoreCase(op)) {
		            	ServletUtility.redirect(CASView.STUDENT_LIST_CTL, request, response);
		            	return;
						
					}
		        
					list = model.search(bean, pageNo, pageSize);
					if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
						ServletUtility.setErrorMessage("No record found ", request);
					}
					ServletUtility.setList(list, request);
					ServletUtility.setSize(model.search(bean).size(), request);
					ServletUtility.setPageNo(pageNo, request);
					ServletUtility.setPageSize(pageSize, request);
					ServletUtility.forward(getView(), request, response);

				} catch (ApplicationException e) {
					ServletUtility.handleException(e, request, response);
					return;
				}
				log.debug("StudentListCtl doPost method end");
	}
	/**
	 * Returns the VIEW page of this Controller
	 * 
	 * @return
	 */
	@Override
	protected String getView() {
		return CASView.STUDENT_LIST_VIEW;
	}


}
