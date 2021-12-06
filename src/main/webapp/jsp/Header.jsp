<%@page import="in.co.college.att.mgt.controller.LoginCtl"%>
<%@page import="in.co.college.att.mgt.bean.UserBean"%>
<%@page import="in.co.college.att.mgt.controller.CASView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <link rel="stylesheet" type="text/css"
	href="/College-Attendance-System/css/style.css">-->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>  
  
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <link rel="stylesheet" href="/College-Attendance-System/css/home.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() {
    $( "#datepicker" ).datepicker();
  } );
  $( function() {
	    $( "#datepicker1" ).datepicker({
	      changeMonth: true,
	      changeYear: true,
	      yearRange: '1950:2023'
	    });
	  } );
  </script>
  
  <script language="javascript">
	$(function() {
		$("#selectall").click(function() {
			$('.case').attr('checked', this.checked);
		});
		$(".case").click(function() {

			if ($(".case").length == $(".case:checked").length) {
				$("#selectall").attr("checked", "checked");
			} else {
				$("#selectall").removeAttr("checked");
			}

		});
	});
</script>
<title>e-attendance</title>
</head>
<body>

	<%
		UserBean userBean = (UserBean) session.getAttribute("user");

		boolean userLoggedIn = userBean != null;

		String welcomeMsg = "Hi, ";

		if (userLoggedIn) {
			String role = (String) session.getAttribute("role");
			welcomeMsg += userBean.getFirstName() + " (" + role + ")";
		} else {
			welcomeMsg += "Guest";
		}
	%>
	<div style="height: 39px">
		<div >
			<div style="margin: 16px">
				<span style="font-size: 20px; font-family: sans-serif;" class="logo">UTA Attendance System</span>
				<span style="float: right;" class="menu"><%=welcomeMsg%></span>
			</div>
			
		</div>
	</div>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
       <li class="nav-item active">
        <a class="nav-link" href="<%=CASView.WELCOME_CTL%>">Home <span class="sr-only">(current)</span></a>
      </li>
    <%
		if (userLoggedIn) {
	%>

			<%
				if (userBean.getRoleId() == 1) {
			%>
			
			 <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
           Faculty
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="<%=CASView.FACULTY_REGISTRATION_CTL%>">Add Faculty</a>
          <a class="dropdown-item" href="<%=CASView.USER_LIST_CTL%>">Faculty List</a>
        </div>
      </li>
     
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
           Student
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="<%=CASView.STUDENT_LIST_CTL%>">Student List</a>
        </div>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
           Course
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="<%=CASView.COURSE_CTL%>">Add Course</a>
          <a class="dropdown-item" href="<%=CASView.COURSE_LIST_CTL%>">Course List</a>
        </div>
      </li>
      
       <!-- <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Subject
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="<%=CASView.SUBJECT_CTL%>">Add Subject</a>
          <a class="dropdown-item" href="<%=CASView.SUBJECT_LIST_CTL%>">Subject List</a>
        </div>
      </li> --> 
      
        <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Assign Faculty
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="<%=CASView.ASSIGN_FACULTY_CTL%>">Assign Faculty to a Course</a>
          <a class="dropdown-item" href="<%=CASView.ASSIGN_FACULTY_LIST_CTL%>">Assigned Faculty List</a>
        </div>
      </li>
     	
      
      
      <%}else if (userBean.getRoleId() == 2) {%>
     
     <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
           Student
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="<%=CASView.STUDENT_LIST_CTL%>">Student List</a>
        </div>
      </li>
     <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Attendance
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="<%=CASView.ATTENDANCE_CTL%>">Add Attendance</a>
           <a class="dropdown-item" href="<%=CASView.ATTENDANCE_LIST_CTL%>">Attendance List</a>
           <a class="dropdown-item" href="<%=CASView.SEMESTER_ATTENDACE_LIST_CTL%>">Semester Attendance</a>
        </div>
      </li>

      
      <% }else if (userBean.getRoleId() == 3) {%>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Attendance
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
           <a class="dropdown-item" href="<%=CASView.ATTENDANCE_LIST_CTL%>">Attendance List</a>
           <a class="dropdown-item" href="<%=CASView.SEMESTER_ATTENDACE_LIST_CTL%>">Semester Attendance</a>
        </div>
      </li>
      
      <%}}else{%>
      <li class="nav-item">
        <a class="nav-link" href="<%=CASView.LOGIN_CTL%>">SignIn</a>
      </li>
      <a class="nav-link"  href="<%=CASView.TWITTER_CTL%>">UTA Twitter Feed</a>
      </li>
      
      	
      <%} %>
    </ul>
    <form class="form-inline my-2 my-lg-0">
    <%
	if (userLoggedIn) {
		%>
    <ul class="navbar-nav mr-auto">
    <li class="nav-item">
        <a class="nav-link" href="<%=CASView.MY_PROFILE_CTL%>">My Profile</a>
        
      </li>
     <li class="nav-item">
        <a class="nav-link" href="<%=CASView.CHANGE_PASSWORD_CTL%>">Change Password</a>
        
      </li>
      <li class="nav-item">
      <a class="nav-link" href="<%=CASView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">Logout</a>
      </li>

</ul>   
<%} %>
 </form>
  </div>
</nav>
				
				