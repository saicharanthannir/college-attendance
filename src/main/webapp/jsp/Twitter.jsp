<%@ include file="Header.jsp" %>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.URL"%>
<%@page import="org.apache.commons.io.IOUtils"%>
<!--header ends here-->
	
	<%
	URL url = new URL("https://us-central1-studentattendance-334117.cloudfunctions.net/test-function");
	String content = IOUtils.toString(new InputStreamReader(
	                        url.openStream()));
	
	System.out.println(content);
	%>
	<!--content starts here-->
	<div class="home-content-wrapper">
		<div class="overlay-box">
			<div class="container">
				<a class="twitter-timeline" data-theme="light" href=<%= content %>>Tweets by utarlington</a> 
				<script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>	
			</div>
		</div>	
	</div>
	<!--content ends here-->



