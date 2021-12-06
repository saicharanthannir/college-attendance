
<%@page import="in.co.college.att.mgt.controller.UserRegistrationCtl"%>
<%@page import="in.co.college.att.mgt.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.college.att.mgt.util.DataUtility"%>
<%@page import="in.co.college.att.mgt.util.ServletUtility"%>
<%@ include file="Header.jsp" %>
<div class="container" style="border: ridge;margin-top: 20px;margin-left: 50px">
<div style="margin-top: 18px">
  <h2>User Registration</h2>
 </div>
 
  <jsp:useBean id="bean" class="in.co.college.att.mgt.bean.UserBean"
		scope="request"></jsp:useBean>
 <b><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></b>
 <b><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></b>
<form style="width: 60%;margin-top: 25px" action="<%=CASView.USER_REGISTRATION_CTL%>" method="post">

<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
				
  <div class="form-row">
    <div class="form-group col-md-6">
      <label for="inputEmail4">First Name:</label>
    
      <input type="text" class="form-control" name="firstName"  placeholder="Enter First Name" value="<%=DataUtility.getStringData(bean.getFirstName())%>" >
    	  <font color="red" style="font-size: 13px"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
    </div>
    <div class="form-group col-md-6">
      <label for="inputPassword4">Last Name:</label>
       <input type="text" class="form-control" name="lastName"  placeholder="Enter Last Name" value="<%=DataUtility.getStringData(bean.getLastName())%>" >
    	  <font color="red" style="font-size: 13px"><%=ServletUtility.getErrorMessage("lastName", request)%></font>
    </div>
  </div>
  <div class="form-group">
    <label for="inputAddress">Login Id:</label>
    <input type="text" class="form-control" name="login"  placeholder="Enter Login Id..." value="<%=DataUtility.getStringData(bean.getLogin())%>" >
    	  <font color="red" style="font-size: 13px"><%=ServletUtility.getErrorMessage("login", request)%></font>
  </div>
  <div class="form-row">
    <div class="form-group col-md-6">
      <label for="inputEmail4">Password:</label>
    
      <input type="password" class="form-control" name="password"  placeholder="Enter Password..." value="<%=DataUtility.getStringData(bean.getPassword())%>" >
    	  <font color="red" style="font-size: 13px"><%=ServletUtility.getErrorMessage("password", request)%></font>
    </div>
    <div class="form-group col-md-6">
      <label for="inputPassword4">Confirm Password:</label>
       <input type="password" class="form-control" name="confirmPassword"  placeholder="Enter Confirm Password" value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>" >
    	  <font color="red" style="font-size: 13px"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></font>
    </div>
  </div>
           <%HashMap map = new HashMap();
			map.put("Male", "Male");
			map.put("Female", "Female"); %>
			
			<div class="form-group">
            <label for="inputAddress">Gender:</label>
           <%=HTMLUtility.getList("gender",String.valueOf(bean.getGender()), map) %>
    	      <font color="red" style="font-size: 13px"><%=ServletUtility.getErrorMessage("gender", request)%></font>
  </div>
			
	<div class="form-row">
    <div class="form-group col-md-6">
      <label for="inputEmail4">Date Of Birth:</label>
    
      <input type="text" class="form-control" name="dob" id="datepicker1"  readonly="readonly" placeholder="Enter Date Of Birth.." value="<%=DataUtility.getDateString(bean.getDob())%>" >
    	  <font color="red" style="font-size: 13px"><%=ServletUtility.getErrorMessage("dob", request)%></font>
    </div>
    <div class="form-group col-md-6">
      <label for="inputPassword4">Mobile No:</label>
       <input type="text" class="form-control" name="mobile"  placeholder="Enter Mobile No" value="<%=DataUtility.getStringData(bean.getMobileNo())%>" >
    	  <font color="red" style="font-size: 13px"><%=ServletUtility.getErrorMessage("mobile", request)%></font>
    </div>
  </div>

    <input type="submit" name="operation" class="btn btn-primary"
					value="<%=UserRegistrationCtl.OP_SAVE%>"> or <input type="submit" name="operation" class="btn btn-primary"
					value="<%=UserRegistrationCtl.OP_RESET%>">
					
    <br><br>
</form>
</div>
<br><br>
<%@ include file="Footer.jsp" %>