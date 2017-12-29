<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="common" uri="/WEB-INF/tld/common.tld" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>

<h1>
	home2  
</h1>
${proptest}<br>
<input type="button" value="홈으로" onclick="location.href='/'"> <br>

<!-- <form action="test/insert" method="post" name="testFrm">
	<div class="inputData" id="dataInput">
		<input class="textInput" type="text" id="userId" name="userId" placeholder="ID"> <br>
		<input class="textInput" type="text" id="password" name="password" placeholder="PW"> <br>
		<input class="textInput" type="text" id="name" name="name" placeholder="NAME"> <br>
		<input class="textInput" type="text" id="age" name="age" placeholder="AGE"> <br>
	</div>
	<input type="button" id="insertBtn" value="등록" onclick="App.insert();"> <br>
</form> -->

<%-- <table class="table">
	<tr>
		<td>ID</td> 
		<td>PW</td> 
		<td>NAME</td> 
		<td>AGE</td>
	</tr>
	<c:forEach items="${list}" var="list">
	<tr>
		<td>${list.ID}</td> 
		<td>${list.PW}</td> 
		<td>${list.NAME}</td> 
		<td>${list.AGE}</td>
	</tr>
	</c:forEach>
</table> --%>
<br>
<table class="table">
	<tr>
		<td>ID</td> 
		<td>PW</td> 
		<td>NAME</td> 
		<td>AGE</td>
		<td>MOD</td>
	</tr>
	<c:forEach items="${listHelper.list}" var ="listRow" varStatus="idx">
	<tr>
		
		<td>
			${listRow.ID}
		</td>
		<td>${listRow.PW}</td>
		<td>${listRow.NAME}</td>
		<td>${listRow.AGE}</td>
		<td>
			<div class="dataTemplate">
				<input type="hidden" name="ID" value="${listRow.ID}">
				<input type="hidden" name="PW" value="${listRow.PW}">
				<input type="hidden" name="NAME" value="${listRow.NAME}">
				<input type="hidden" name="AGE" value="${listRow.AGE}">
			</div>
			<input type="button" id="MOD" name="${idx.index}" value="MOD" onclick="App.updateForm(this);"> 
			<input type="button" id="DEL" name="${idx.index}" value="DEL" onclick="App.deleteData(this);"> 
		</td>
	</tr>
	</c:forEach>
	
	<tr id="hiddenTR" style="display: none;">
		<td>
			<span id="ID"></span>
			<input type="hidden" name="ID" value=""> 
		</td>
		<td><input class="hiddenData" type="text" id="PW" name="PW"></td>
		<td><input class="hiddenData" type="text" id="NAME" name="NAME"></td>
		<td><input class="hiddenData" type="text" id="AGE" name="AGE"></td>
		<td>
			<input type="button" id="SAVE" value="SAVE" onclick="App.update();">
			<input type="button" id="CANCEL" value="CANCEL" onclick="App.cancel();"> 
		</td>
	</tr>
</table>



<div>
<common:paging listHelper="${listHelper}" />
</div>

</body>
</html>
