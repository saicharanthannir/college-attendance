
<%@page import="in.co.college.att.mgt.controller.CourseCtl"%>
<%@page import="in.co.college.att.mgt.controller.UserRegistrationCtl"%>
<%@page import="in.co.college.att.mgt.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.college.att.mgt.util.DataUtility"%>
<%@page import="in.co.college.att.mgt.util.ServletUtility"%>
<%@ include file="Header.jsp" %>
<div class="container" style="border: ridge;margin-top: 20px;margin-left: 50px">
<div style="margin-top: 18px">
  <h2>Add Course</h2>
 </div>
 
  <jsp:useBean id="bean" class="in.co.college.att.mgt.bean.CourseBean"
		scope="request"></jsp:useBean>
 <b><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></b>
 <b><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></b>
<form style="width: 60%;margin-top: 25px" action="<%=CASView.COURSE_CTL%>" method="post">

<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
				
 
  <div class="form-group">
    <label for="inputAddress">Name:</label>
    <input type="text" class="form-control" name="name"  placeholder="Enter Name..." value="<%=DataUtility.getStringData(bean.getName())%>" >
    	  <font color="red" style="font-size: 13px"><%=ServletUtility.getErrorMessage("name", request)%></font>
  </div>
  
   <div class="form-group">
    <label for="inputAddress">Description:</label>
    <textarea rows="4" cols="4" name="description" placeholder="Enter Description" class="form-control" ><%=DataUtility.getStringData(bean.getDescription()) %></textarea>
    	  <font color="red" style="font-size: 13px"><%=ServletUtility.getErrorMessage("description", request)%></font>
  </div>
 
			
	

    <input type="submit" name="operation" class="btn btn-primary"
					value="<%=CourseCtl.OP_SAVE%>"> or <input type="submit" name="operation" class="btn btn-primary"
					value="<%=CourseCtl.OP_RESET%>">
					
    <br><br>
</form>
</div>
<br><br>
<div style="margin-top: 7px">
<%@ include file="Footer.jsp" %>
</div>