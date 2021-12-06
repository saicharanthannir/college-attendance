
<%@page import="in.co.college.att.mgt.controller.AssignFacultyCtl"%>
<%@page import="in.co.college.att.mgt.controller.SubjectCtl"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.college.att.mgt.controller.UserRegistrationCtl"%>
<%@page import="in.co.college.att.mgt.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.college.att.mgt.util.DataUtility"%>
<%@page import="in.co.college.att.mgt.util.ServletUtility"%>
<%@ include file="Header.jsp" %>
<div class="container" style="border: ridge;margin-top: 20px;margin-left: 50px">
<div style="margin-top: 18px">
  <h2>Add Assign Faculty</h2>
 </div>
 
  <jsp:useBean id="bean" class="in.co.college.att.mgt.bean.AssignFacultyBean"
		scope="request"></jsp:useBean>
 <b><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></b>
 <b><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></b>
<form style="width: 60%;margin-top: 25px" action="<%=CASView.ASSIGN_FACULTY_CTL%>" method="post">

<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
			<% List list=(List)request.getAttribute("subjectList");
			   List list2=(List)request.getAttribute("userList");
			%>
				

  <div class="form-group">
    <label for="inputAddress">Faculty Name:</label>
   <%=HTMLUtility.getList("userId",String.valueOf(bean.getUserId()), list2)%>
    	  <font color="red" style="font-size: 13px"><%=ServletUtility.getErrorMessage("userId", request)%></font>
  </div>
  
   <div class="form-group">
    <label for="inputAddress">Course Name:</label>
   <%=HTMLUtility.getList("subjectId",String.valueOf(bean.getUserId()), list)%>
    	  <font color="red" style="font-size: 13px"><%=ServletUtility.getErrorMessage("subjectId", request)%></font>
  </div>
  
  <div class="form-group">
    <label for="inputAddress">Total Class:</label>
    <input type="text" class="form-control" name="noClass" placeholder="Enter Total Class..." value="<%=DataUtility.getStringData(bean.getTotalClass())%>" >
    	  <font color="red" style="font-size: 13px"><%=ServletUtility.getErrorMessage("noClass", request)%></font>
  </div>
		
	

    <input type="submit" name="operation" class="btn btn-primary"
					value="<%=AssignFacultyCtl.OP_SAVE%>"> or <input type="submit" name="operation" class="btn btn-primary"
					value="<%=AssignFacultyCtl.OP_RESET%>">
					
    <br><br>
</form>
</div>
<br><br>
<%@ include file="Footer.jsp" %>