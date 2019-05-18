<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<sql:setDataSource
	driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://cs3.calstatela.edu/cs3220stu111"
	user="cs3220stu111"
	password="iMG2nvUI" />
	
<sql:query var="items">
 	select name from files where owner_id = ${id}
</sql:query>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>Your files</title>
</head>
<body>

	<div class="container-fluid">
		<div class="jumbotron my-4">
			<h1 class="display-4">Your Files</h1>
		</div>
	
	<table class="table table-bordered table-striped table-hover">
  		<c:forEach items="${items.columnNames}" var="col">
			<td>${col}</td>
		</c:forEach>
		<c:forEach items="${items.rowsByIndex}" var="row">
			<tr>
			<c:forEach items="${row}" var="col">      
				    <td>${col}</td>
			</c:forEach>
			</tr>
  		</c:forEach>
	</table>
	
		<form action="Upload" method="post" enctype="multipart/form-data">
		
			<div class="input-group">
			  	<div class="custom-file">
			    	<input type="file" name="file" class="custom-file-input" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04">
			    	<label class="custom-file-label" for="inputGroupFile04">Choose file</label>
			  	</div>
			  	<div class="input-group-append">
			  	
			    	<input type="submit" class="btn btn-outline-secondary" id="inputGroupFileAddon04" aria-describedby="inputGroupFileAddon04" name="upload" value="Upload">
			    	
			  	</div>
			</div>
			
		</form>
	</div>
	
<script>
	$('#inputGroupFile04').on('change',function(){
    	//get the file name
        var fileName = $(this).val();
        var cleanFileName = fileName.replace('C:\\fakepath\\', " ");

        //replace the "Choose a file" label
        $(this).next('.custom-file-label').html(cleanFileName);
    });
    
</script>

</body>
</html>
