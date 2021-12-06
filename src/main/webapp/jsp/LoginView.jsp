
<%@page import="in.co.college.att.mgt.controller.LoginCtl"%>
<%@page import="in.co.college.att.mgt.util.DataUtility"%>
<%@page import="in.co.college.att.mgt.util.ServletUtility"%>
<%@ include file="Header.jsp"%>
<div class="container" style="border: ridge;margin-top: 20px;margin-left: 50px">
 <div style="margin-top: 24px">
  <h2>Login</h2>
 </div>
 <jsp:useBean id="bean" class="in.co.college.att.mgt.bean.UserBean"
		scope="request"></jsp:useBean>
 <b><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></b>
 <b><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></b>
  <form method="post" action="<%=CASView.LOGIN_CTL%>" class="was-validated" style="width: 50%">
               <%
					String uri = (String) request.getAttribute("uri");
				%>

				<input type="hidden" name="uri" value="<%=uri%>"> <input
					type="hidden" name="id" value="<%=bean.getId()%>"> <input
					type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
				<input type="hidden" name="modifiedBy"
					value="<%=bean.getModifiedBy()%>"> <input type="hidden"
					name="createdDatetime"
					value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
				<input type="hidden" name="modifiedDatetime"
					value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
    <div class="form-group">
      <label for="uname">Login Id:</label>
      <input type="text" class="form-control"  name="login" placeholder="Enter Login Id"  value="<%=DataUtility.getStringData(bean.getLogin()) %>" >
     <font color="red" style="font-size: 13px"><%=ServletUtility.getErrorMessage("login", request)%></font>
    </div>
    <div class="form-group">
      <label for="pwd">Password:</label>
      <input type="password" class="form-control"  placeholder="Enter password" name="password" value="<%=DataUtility.getStringData(bean.getPassword())%>">
      <font color="red" style="font-size: 13px"><%=ServletUtility.getErrorMessage("password", request)%></font>
    </div>
    
    <input type="submit" name="operation" class="btn btn-primary"
					value="<%=LoginCtl.OP_SIGN_IN%>">or <input type="submit" name="operation" class="btn btn-primary"
					value="<%=LoginCtl.OP_SIGN_UP%>">
					<br><br>
					<a href="#" >Forget Password ?</a> 
    <br><br>
  </form>
</div>
<div style="margin-top: 90px">
<%@ include file="Footer.jsp"%>
</div>