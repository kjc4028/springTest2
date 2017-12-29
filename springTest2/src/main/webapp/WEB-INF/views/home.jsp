<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
</head>
<style>
.main_left { width:calc( 49% - 35px );float:left;height:auto;border:1px solid #e5e5e5;background:white;padding:35px;margin-bottom:20px; }
.main_right { width:calc( 49% - 35px );float:right;height:auto;border:1px solid #e5e5e5;background:white;padding:35px;margin-bottom:20px; }
</style>
<body>

<div>
	<h1>home1</h1> <br>
	<P>  The time on the server is ${serverTime}.테스트 </P> <br>
	<input type="button" id="home2btn" value="HOME2 이동"> <br>
	<input type="button"value="HOME2 이동2" onclick="location.href='/list?curPage=1'"> <br>
</div>
<div>
	<fmt:parseDate value="20171225" var="parsePrmisnDe" pattern="yyyyMMdd" />
	<fmt:formatDate value="${parsePrmisnDe}" pattern="yyyy-MM-dd"/>
</div>
<div class="text-center">
<c:import url="include.jsp"></c:import><br>
</div>

<div class="main_left">
	<form action="test/insert" method="post" name="testFrm">
		<div class="inputData" id="dataInput">
			<input class="textInput" type="text" id="userId" name="userId" placeholder="ID"> <br>
			<input class="textInput" type="text" id="password" name="password" placeholder="PW"> <br>
			<input class="textInput" type="text" id="name" name="name" placeholder="NAME"> <br>
			<input class="textInput" type="text" id="age" name="age" placeholder="AGE"> <br>
		</div>
		<input type="button" id="insertBtn" value="등록" onclick="App.insert();"> <br>
	</form>
	<input type="button" id="ajaxInsert" value="ajax등록">
	<input type="button" id="ajaxSelect" value="ajax조회"><br>
</div>

<div class="main_right">
	<div id="div1">
		<table class="table" id="rsTB" >
		
		</table>
	</div>
</div><br>




<script type="text/javascript">
$("#home2btn").click(function(){
	location.href="/page2"; 
	
});

    /* ajax등록 */
	$("#ajaxInsert").click(function(){
    	/* var userId= $('#userId').val();
    	var password= $('#password').val();
    	var name= $('#name').val();
    	var age= $('#age').val(); */
    	 var param = $("form[name=testFrm]").serialize();
    	$.ajax({   
    	      type: "POST",   
    	      url: "/test/ajaxInsert",   
    	      /* data: { userId: userId, password: password, name: name, age: age }   */
    	      data: param,
    	      success: function(){
    	    	  document.testFrm.reset();
    	    	  aSelect();
    	      }
    	});
    });
    
    /* ajax조회 */
     $('#ajaxSelect').click(function(){
    	aSelect();
    }); 
    
    function aSelect(){
    	 var str="";
    	 $.ajax({
    		url:"/test/ajaxSelect",
    		type:"GET",
    		dataType: "JSON",
    		success:function(data){
    				str = "	<tr><td>NUM</td><td>ID</td><td>PW</td><td>NAME</td><td>AGE</td></tr>";
	    			$.each(data,function(i,val){
        				str += "<tr>" + 
			        				"<td>" +val.ROWNUM + "</td>" + 
			        				"<td>" +val.ID + "</td>" + 
			        				"<td>" +val.PW + "</td>" + 
			        				"<td>" +val.NAME + "</td>" + 
			        				"<td>" +val.AGE + "</td>" + 
		        				"</tr>";
        			});
    				$("#div1 #rsTB").html(str);
    			}
    	});
    };
    
   /*  function aSelect(){
   	 var str="";
   	 $.ajax({
   		url:"/test/ajaxSelect",
   		type:"GET",
   		dataType: "JSON",
   		success:function(data){
   			$.each(data,function(i,val){
   				console.log(i+val.ID);
   				str += i + " : " + val.ID + "<br>";
   			});
   			$("#div1").html(str);
   		}
   	});
   }; */
   


</script>
	
</body>

</html>
