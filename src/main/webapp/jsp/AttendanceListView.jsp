
<%@page import="in.co.college.att.mgt.util.DataUtility"%>
<%@page import="in.co.college.att.mgt.bean.AttendaceBean"%>
<%@page import="in.co.college.att.mgt.controller.AttendanceListCtl"%>
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
  <h2>Attendance  List</h2>
 </div>
  
<div style="height: 62px;margin-top: 27px;margin-left: 10px">
  <form class="form-inline" action="<%=CASView.ATTENDANCE_LIST_CTL%>" method="post">
    <div class="form-group">
      <label for="email">Course Name:</label>
      &nbsp;&nbsp;&nbsp;<input type="text" class="form-control"  placeholder="Enter Name" name="name" value="<%=ServletUtility.getParameter("name", request)%>">
    </div>
    &nbsp;&nbsp;
     <div class="form-group">
      <label for="email">Student Name:</label>
      &nbsp;&nbsp;&nbsp;<input type="text" class="form-control"  placeholder="Enter Student Name" name="cName" value="<%=ServletUtility.getParameter("cName", request)%>">
    </div>
    
   
   &nbsp;&nbsp;&nbsp; <input type="submit" name="operation" class="btn btn-primary"
						value="<%=AttendanceListCtl.OP_SEARCH%>">&emsp; <input
						type="submit" name="operation" class="btn btn-primary"
						value="<%=AttendanceListCtl.OP_RESET%>">
  </form>
</div>


<b><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></b>
			<b><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></b>
 <form  action="<%=CASView.ATTENDANCE_LIST_CTL%>" method="post">
  <div class="table-responsive">
    <table class="table table-bordered">
      <thead>
        <tr>
          
          <th>#</th>
           <th>Course Name</th>
           <th>Student Name</th>
           <th>Semester</th>
           <th>Date</th>
           <th>Status</th>
        </tr>
      </thead>
      
      <%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;
					int size=ServletUtility.getSize(request);
					AttendaceBean bean = null;
					List list = ServletUtility.getList(request);
					Iterator<AttendaceBean> iterator = list.iterator();
					while (iterator.hasNext()) {
						bean = iterator.next();
		%>
      <tbody>
        <tr>
        
          <td><%=index++%></td>
          <td><%=bean.getSubjectName()%></td>
          <td><%=bean.getStudentName()%></td>
          <td><%=bean.getSemester()%></td>
          <td><%=DataUtility.getDateString(bean.getDate())%></td>
         <%if(bean.getStatus()==1){ %>
          <td>Present</td>
          <%}else{%>
          <td>Absent</td>
          <%} %>
        </tr>
      </tbody>
      <%} %>
    </table>
    <table width="99%" >
				<tr>
					<td><input type="submit" name="operation" class="btn btn-primary"
						value="<%=AttendanceListCtl.OP_PREVIOUS%>"
						<%=(pageNo == 1) ? "disabled" : ""%>></td>


					
					
					<td align="right"><input type="submit" name="operation" class="btn btn-primary"
						value="<%=AttendanceListCtl.OP_NEXT%>"
						<%=((list.size() < pageSize) || size == pageNo*pageSize) ? "disabled" : ""%>></td>

				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
				
				
  </div>
   </form>
<div style="margin-top: 10px">
<%@ include file="Footer.jsp"%>
</div>