<html>

	<head>
		<title>Add a New Tutor</title>
		<link rel="stylesheet" href="style.css" />		
	</head>

	<body>
		<h1>Add a new Tutor</h1>
		
		<form method="POST" ACTION="addNewTutor.html">
			<p><label>Name</label><input type="TEXT" NAME="NAME"/></p>
			<p><label>Salary</label><input type="TEXT" NAME="SALARY"/></p>

			<p><input type="SUBMIT" value="Add the Tutor" class="button"/></p>
		</form>
		
		<jsp:include page="/footer.jsp"/>
	</body>
</html>