
<%@page import="in.co.college.att.mgt.bean.AssignFacultyBean"%>
<%@page import="in.co.college.att.mgt.model.AssignFacultyModel"%>
<%@page import="in.co.college.att.mgt.model.AttendanceModel"%>
<%@page import="in.co.college.att.mgt.bean.AttendaceBean"%>
<%@page import="in.co.college.att.mgt.bean.StudentBean"%>
<%@page import="in.co.college.att.mgt.model.StudentModel"%>
<%@page import="in.co.college.att.mgt.bean.SubjectBean"%>
<%@page import="in.co.college.att.mgt.controller.SubjectListCtl1"%>
<%@page import="in.co.college.att.mgt.model.CourseModel"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.college.att.mgt.bean.CourseBean"%>
<%@page import="in.co.college.att.mgt.controller.CourseListCtl"%>
<%@page import="in.co.college.att.mgt.util.ServletUtility"%>
<%@ include file="Header.jsp"%>
  <div style="margin-top: 18px">
  <h2>Attendance List</h2>
 </div>

<b><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></b>
			<b><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></b>
 <form  action="<%=CASView.SEMESTER_ATTENDACE_LIST_CTL%>" method="post">
  <div class="table-responsive">
    <table class="table table-bordered">
      <thead>
        <tr>
          
          <th>Roll Number</th>
          <th><center>Student Name</center></th>
          <%
          SubjectBean sBean = null;
			List list = ServletUtility.getList(request);
			Iterator<SubjectBean> iterator = list.iterator();
			while (iterator.hasNext()) {
				sBean = iterator.next(); %>
           <th colspan="3"><%=sBean.getName()%></th>
         <%} %>
         <th>Total Percentage</th>
        </tr>
      </thead>
      
        <%
        		
                    AttendanceModel aModel=new AttendanceModel();
					int index=1;
					StudentBean bean = null;
					SubjectBean sbBean=null;
					AttendaceBean aBean=null;
					AssignFacultyModel asModel=new  AssignFacultyModel();
					List list1 =(List)request.getAttribute("stuList");
					Iterator<StudentBean> it = list1.iterator();
					while (it.hasNext()) {
						bean = it.next();		
		 %>
      <tbody>
        <tr>
       
          <td><%=index++%></td>
          <td><%=bean.getFirstName()+" "+bean.getLastName()%></td>
         <%
         List list2=ServletUtility.getList(request);
			Iterator it2=list2.iterator();
			int alltotal=0;
			long subtotal=0;
         while(it2.hasNext()){ 
        	 sbBean=(SubjectBean)it2.next();
        	 AssignFacultyBean asBean=asModel.findBySubName(sbBean.getId());
        	 if(asBean!=null){
        	 alltotal=alltotal+Integer.parseInt(asBean.getTotalClass());
        	 }
        	 int id=aModel.FindByStudentAndSubjectMax(bean.getId(),sbBean.getId()); 
        
         %>	
         
         <%
         if(id>0){
     	     aBean=aModel.findByPK(id);  
     	   subtotal=subtotal+aBean.getTotal();
     	 %>
          <td><%=aBean.getTotalClass()%></td>
          <td><%=aBean.getTotal()%></td>
          <%  double totalClass=Double.parseDouble(aBean.getTotalClass());
              double total=(double)aBean.getTotal();
              double per=(total/totalClass)*100;%>
          <td><%=per%></td>
        <%} }%>
       
       
        <% 
        	double pert=((double)subtotal/(double)alltotal)*100;
        %>
        <td><%=pert%></td>
        </tr>
        
      </tbody>
      <%} %>
    </table>
    		
  </div>
   </form>
<div style="margin-top: 10px">
<%@ include file="Footer.jsp"%>
</div>