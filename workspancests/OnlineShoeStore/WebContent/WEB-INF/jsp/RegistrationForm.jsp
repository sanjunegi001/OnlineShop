  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>HCL_United</title>
<!-- Registration Page -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<link href="http://fonts.googleapis.com/css?family=Montserrat"
	rel="stylesheet" type="text/css">

<link href="http://fonts.googleapis.com/css?family=Lato"
	rel="stylesheet" type="text/css">
<style type="text/css">
body {
    font: 400 15px/1.8 Lato, sans-serif;
    color: #777;
}

</style>
<!-- <link href="css/style.css" rel="stylesheet"> -->
<script type="text/javascript">

	function validate(){
		//========customerId valid========//
		var id=document.myform.id.value;
		if(id==null || id==""){
			alert("Enter customerid");
			document.myform.id.focus();
			return false;
		}else if (isNaN(id)) {
			alert("customerid should be digit");
			document.myform.id.focus();
			return false;
		}
		var regex=/^[0-9]{3}$/;
		if((id.match(regex))==null){
			alert("Enter Customer Id 3 number");
			document.myform.phone.focus();
			return false;
		}
		
		//=========cusomerName================//
		var name=document.myform.customerName.value;
		if(name==null || name==""){
			alert("Enter customername");
			document.myform.customerName.focus();
			return false;
		}
		var regex=/^[a-zA-Z\_ ]{2,30}$/;
		if((name.match(regex))==null){
			alert("Accept alphabets and  only space character and total character in field should be between 2 to 30");
			document.myform.customerName.focus();
			return false;
		}
		//==========email validation========//
		var eml=document.myform.email.value;
		if(eml==null || eml==""){
			alert("Enter email");
			document.myform.email.focus();
			return false;
		}
		
		//=======password====//
		 if(document.myform.password.value != "" && document.myform.password.value == document.myform.confirmPassword.value) {
		      if(document.myform.password.value.length < 6) {
		        alert("Error: Password must contain at least six characters!");
		        document.myform.password.focus();
		        return false;
		      }
		      if(document.myform.password.value == document.myform.customerName.value) {
		        alert("Error: Password must be different from Username!");
		        document.myform.password.focus();
		        return false;
		      }
		      re = /[0-9]/;
		      if(!re.test(document.myform.password.value)) {
		        alert("Error: password must contain at least one number (0-9)!");
		        document.myform.password.focus();
		        return false;
		      }
		      re = /[a-z]/;
		      if(!re.test(document.myform.password.value)) {
		        alert("Error: password must contain at least one lowercase letter (a-z)!");
		        document.myform.password.focus();
		        return false;
		      }
		      re = /[A-Z]/;
		      if(!re.test(document.myform.password.value)) {
		        alert("Error: password must contain at least one uppercase letter (A-Z)!");
		        document.myform.password.focus();
		        return false;
		      }
		    } else {
		      alert("Error: Please check that you've entered and confirmed your password!");
		      document.myform.password.focus();
		      return false;
		    }

		     alert("Successfully entered : " + document.myform.email.value); 
		   
		  			
		//=====contact details====///
		var mobile=document.myform.phone.value;
		if(mobile==null || mobile==""){
			alert("Enter Mobile Number");
			document.myform.phone.focus();
			return false;
		} 
		var regex=/^[0-9]{10}$/;
		if((mobile.match(regex))==null){
			alert("Enter 10 digit Mobile number");
			document.myform.phone.focus();
			return false;
		}
		
		//========address=======//
		var address = document.myform.address.value;
		if (address == null || address == "") {
			alert("please enter a valid address");
			document.myform.address.focus();
			return false;
		}
		var regex2 = /^[a-zA-Z,0-9\_ ]{2,100}$/;      
		if ((address.match(regex2)) ==null) {
			alert("accept alphabets, digits and only space character ");
			document.myform.address.focus();
			return false;
		}
		return true;
		}
		
</script>
</head>

<body>

<div class="container" style="background-color:#f2f2f2">
  <p class="text-center"><em>Registration Form</em></p>
  <p class="text-center"><em>Fill up the following details in a jizz</em></p>
  <p class="text-center"><em>And get access to use our services</em></p>
  

			<form:form name="myform" action="addCustomer" method="post" commandName="customerTo" onsubmit="return validate() ">

<div class="form-group">
					<label>Customer ID:</label> 
					<input type="text"	class="form-control" name="id" placeholder="Please Enter CustomerId">
				</div>
				<div class="form-group">
					<label>Username:</label> 
					<input type="text"	class="form-control" name="customerName" placeholder="Please Enter Username">
				</div>

				<div class="form-group">
						<label for="email">Email:</label> <input type="email" name="email"
							class="form-control" id="email" placeholder="Enter your email address">
				</div>


				<div class="form-group">
					<label>Password:</label> 
					<input type="password" class="form-control"	name="password" placeholder="Enter a 6-Digit Password">
				</div>
				
				<div class="form-group">
					<label>Confirm Password:</label> 
					<input type="password" class="form-control"	name="confirmPassword" placeholder="Re-type your password">
				</div>

				

				<div class="form-group">
					<label>Contact Details:</label> 
					<input class="form-control" name="phone" placeholder="How can we reach you?">
				</div>

				<div class="form-group">
						<label for="address">How can we contact you:</label>
						<textarea class="form-control" rows="5" id="address" name="address"
							placeholder="Leave us an address..."></textarea>
				</div>
				

				<div>
					<input type="submit" class="btn btn-primary" value="Register Me!" >
					<input type="reset" class="btn btn-primary" value="clear">
				</div>

			</form:form>

		</div>
  
  
</div>
</body>
</html>