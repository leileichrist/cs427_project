<%@page import="edu.ncsu.csc.itrust.action.ViewCauseOfDeathTrendsAction"%>
<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.beans.CauseOfDeathBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@page import="java.text.ParseException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Cause of Death Trends report";
	String view = request.getParameter("viewSelect");
	
	loggingAction.logEvent(TransactionType.CAUSEOFDEATH_TRENDS_VIEW, loggedInMID.longValue(), 0, "");

	ViewCauseOfDeathTrendsAction causeOfDeathTrends = new ViewCauseOfDeathTrendsAction(prodDAO);
	List<CauseOfDeathBean> dsBeans = null;
	
	//get form data
	String startYear = request.getParameter("startYear");
	String endYear = request.getParameter("endYear");
	String gender = request.getParameter("gender");	
	String p = request.getParameter("patients");
	
	if(view!=null && startYear!="" && endYear!="" && gender!="" && p!=""){
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date sDate = new Date(); 
			Date eDate = new Date();
			startYear = startYear + "-01-01";
			endYear = endYear + "-12-31";
			sDate = formatter.parse(startYear);
			eDate = formatter.parse(endYear);
			if(sDate.after(eDate)){
				%>
				<div align=center>
					<span class="iTrustError">
							<hr>Start year cannot be ahead of end year</hr>
					</span>
				</div>
				<%
				pageContext.getOut(); 
			}else{
				if(p.equals("allPatients")){
					dsBeans = causeOfDeathTrends.getCauseOfDeathTrends(null, startYear, endYear, gender);	
				}else{
					dsBeans = causeOfDeathTrends.getCauseOfDeathTrends(loggedInMID.longValue(), startYear, endYear, gender);
				}	
			}
		}catch (ParseException e) {		
			pageContext.getOut(); 
		}
	}
%>

<%@include file="/header.jsp"%>


<br />
<form action="viewCauseOfDeathTrends.jsp" method="post" id="formMain">
	<input type="hidden" name="viewSelect" value="trends" />
	<table class="fTable" align="center"
		id="causeOfDeathTrendsSelectionTable">
		<tr>
			<th colspan="4">Cause and Death Trends</th>
		</tr>
		<tr class="subHeader">
			<td style="text-align: center;">Gender:</td>
			<td><select name="gender" style="font-size: 10">
					<option value="All" selected>All</option>
					<option value="Male">Male</option>
					<option value="Female">Female</option>
			</select></td>
			<td>Data for</td>
			<td><select name="patients">
					<option value="myPatients" selected>My Patients</option>
					<option value="allPatients">All Patients</option>
			</select></td>
		</tr>
		<tr class="subHeader">
			<td>Start Year:</td>
			<td><select name="startYear" id="startYear">
			</select></td>
			<td>End Year:</td>
			<td><select name="endYear" id="endYear">
					<!-- <script>
						var myDate = new Date();
						var year = myDate.getFullYear();
						for (var i = year; i >= 1900; i--) {
							if(i==year){
								document.write('<option value="'+i+'" selected>' + i
										+ '</option>');								
							}else{
								document.write('<option value="'+i+'">' + i
										+ '</option>');
							}
						}
					</script> -->
			</select></td>
		</tr>

		<tr>
			<td colspan="4" style="text-align: center;"><input type="submit"
				id="select_causeOfDeath" value="View cause of death trends"></td>
		</tr>
	</table>

</form>

<br />

<script>
var year = 1900;
var till = 2014;
var options = "";
for(var y=till; y>=year; y--){
  options += "<option>"+ y +"</option>";
}
document.getElementById("endYear").innerHTML = options;
</script>

<script>
var year = 1900;
var till = 2014;
var options = "";
for(var y=year; y<=till; y++){
  options += "<option>"+ y +"</option>";
}
document.getElementById("startYear").innerHTML = options;
</script>




<%
	if (dsBeans != null) {
%>
<hr>Top two most common causes of death during <%=startYear %> to <%=endYear %> for <%=gender %> patients :</hr>
<table class="fTable" align="center" id="causeOfDeathTrendsTable">
	<tr>
		<th>ICD-9CM code</th>
		<th>Name</th>
		<th>Quantity of death</th>
	</tr>

	<%
		if (dsBeans.size() == 0) {
	%>
	<tr style="text-align: center;">
		<td>NO DATA FOUND</td>
	</tr>
	<%
		}
	%>
	<%
		//for (CauseOfDeathBean b : dsBeans) {
			for (int i=0; i<dsBeans.size();i++) {
				
			CauseOfDeathBean b = dsBeans.get(i);
	%>
	<tr style="text-align: center;">
		<td><%=b.getIcdcode()%></td>
		<td><%=b.getName()%></td>
		<td><%=b.getQuantityOfDeath()%></td>
	</tr>
	<%
		}
	%>
	<%
		}
	%>


</table>

<%@include file="/footer.jsp"%>
