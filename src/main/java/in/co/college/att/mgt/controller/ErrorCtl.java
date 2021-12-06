package in.co.college.att.mgt.controller;

/**
 * 
 * @author Navigable Set
 * @version 1.0
 * @Copyright (c) Navigable Set
 */


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.college.att.mgt.util.ServletUtility;


/**
 * Servlet implementation class ErrorCtl
 */
@WebServlet(name = "ErrorCtl", urlPatterns = { "/ctl/ErrorCtl" })
public class ErrorCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in do get method ERRor Cl");
		ServletUtility.forward(getView(), request, response);
		return;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in do get method ERRor Cl");
		ServletUtility.forward(getView(), request, response);
		return;
	}
	
	@Override
	protected String getView() {
		
		return CASView.ERROR_VIEW;
	}

}
