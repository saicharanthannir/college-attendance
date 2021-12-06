
<%@page import="in.co.college.att.mgt.bean.AssignFacultyBean"%>
<%@page import="in.co.college.att.mgt.controller.AssignFacultyListCtl"%>
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
  <h2>AssignFaculty  List</h2>
 </div>
  
<div style="height: 62px;margin-top: 27px;margin-left: 10px">
  <form class="form-inline" action="<%=CASView.ASSIGN_FACULTY_LIST_CTL%>" method="post">
    <div class="form-group">
      <label for="email">Course Name:</label>
      &nbsp;&nbsp;&nbsp;<input type="text" class="form-control"  placeholder="Enter Name" name="name" value="<%=ServletUtility.getParameter("name", request)%>">
    </div>
    &nbsp;&nbsp;
     <div class="form-group">
      <label for="email">Faculty Name:</label>
      &nbsp;&nbsp;&nbsp;<input type="text" class="form-control"  placeholder="Enter Faculty Name" name="uName" value="<%=ServletUtility.getParameter("uName", request)%>">
    </div>
    
   
   &nbsp;&nbsp;&nbsp; <input type="submit" name="operation" class="btn btn-primary"
						value="<%=AssignFacultyListCtl.OP_SEARCH%>">&emsp; <input
						type="submit" name="operation" class="btn btn-primary"
						value="<%=AssignFacultyListCtl.OP_RESET%>">
  </form>
</div>


<b><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></b>
			<b><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></b>
 <form  action="<%=CASView.ASSIGN_FACULTY_LIST_CTL%>" method="post">
  <div class="table-responsive">
    <table class="table table-bordered">
      <thead>
        <tr>
          <th><input type="checkbox" id="selectall">Select All</th>
          <th>#</th>
           <th>Faculty Name</th>
           <th>Course Name</th>
          <th>Subject Name</th>
           <th>Semester</th>
           <th>Total Class</th>
          <th>Action</th>
        </tr>
      </thead>
      
      <%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;
					int size=ServletUtility.getSize(request);
					AssignFacultyBean bean = null;
					List list = ServletUtility.getList(request);
					Iterator<AssignFacultyBean> iterator = list.iterator();
					while (iterator.hasNext()) {
						bean = iterator.next();
		%>
      <tbody>
        <tr>
          <td><input type="checkbox" class="case"
						name="ids" value="<%=bean.getId()%>"></td>
          <td><%=index++%></td>
          <td><%=bean.getUserName()%></td>
          <td><%=bean.getCourseName()%></td>
          <td><%=bean.getSubjectName()%></td>
          <td><%=bean.getSemester()%></td>
          <td><%=bean.getTotalClass()%></td>
          <td align="center"><a class="btn btn-primary" href="assignFaculty?id=<%=bean.getId()%>">Edit</a></td>
        </tr>
      </tbody>
      <%} %>
    </table>
    <table width="99%" >
				<tr>
					<td><input type="submit" name="operation" class="btn btn-primary"
						value="<%=AssignFacultyListCtl.OP_PREVIOUS%>"
						<%=(pageNo == 1) ? "disabled" : ""%>></td>


					<td><input type="submit" name="operation" class="btn btn-success"
						value="<%=AssignFacultyListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation" class="btn btn-danger"
						value="<%=AssignFacultyListCtl.OP_DELETE%>"
						<%=(list.size() == 0) ? "disabled" : ""%>></td>
					
					<td align="right"><input type="submit" name="operation" class="btn btn-primary"
						value="<%=AssignFacultyListCtl.OP_NEXT%>"
						<%=((list.size() < pageSize) || size == pageNo*pageSize) ? "disabled" : ""%>></td>

				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
				
				
  </div>
   </form>
<div style="margin-top: 10px">
<%@ include file="Footer.jsp"%>
