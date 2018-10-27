<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HCL_United Inc.</title>
<!-- Update customer -->
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



<style>
body {
	font: 400 15px/1.8 Lato, sans-serif;
	color: #777;
}

.container {
	padding: 80px 120px;
}

.scrollup {
	width: 40px;
	height: 40px;
	opacity: 0.5;
	bottom: 55px;
	position: fixed;
	right: 95px;
	text-indent: -999px;
	background: url('http://i66.tinypic.com/6t3jba.png') no-repeat;
	display: none;
	cursor: pointer;
}

/* Nav-bar */
.navbar-brand {
	font-size: 24px;
}

.navbar-container {
	padding: 20px 0 20px 0;
}

.navbar.navbar-fixed-top.fixed-theme {
	background-color: #222;
	border-color: #080808;
	box-shadow: 0 0 5px rgba(0, 0, 0, .8);
}

.navbar-brand.fixed-theme {
	font-size: 18px;
}

.navbar-container.fixed-theme {
	padding: 0;
}

.navbar-brand.fixed-theme, .navbar-container.fixed-theme, .navbar.navbar-fixed-top.fixed-theme,
	.navbar-brand, .navbar-container {
	transition: 0.8s;
	-webkit-transition: 0.8s;
}
</style>

<script type="text/javascript">



function validate(){
	var id=document.myform.id.value;
	var regex=/^[0-9]{10}$/;
	if((mobile.match(regex))==null){
		alert("Enter 10 digit Mobile number");
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
<script type="text/javascript">



	$(document).ready(function() {

		$(window).scroll(function() {
			if ($(this).scrollTop() > 100) {
				$('.scrollup').fadeIn();
			} else {
				$('.scrollup').fadeOut();
			}
		});

		$('.scrollup').click(function() {
			$("html, body").animate({
				scrollTop : 0
			}, 600);
			return false;
		});

	});

	/* hover dropdown */

	$(function() {
		$(".dropdown").hover(function() {
			$('.dropdown-menu', this).stop(true, true).fadeIn("fast");
			$(this).toggleClass('open');
			$('b', this).toggleClass("caret caret-up");
		}, function() {
			$('.dropdown-menu', this).stop(true, true).fadeOut("fast");
			$(this).toggleClass('open');
			$('b', this).toggleClass("caret caret-up");
		});
	});

	/* Nav bar affix animation */

	$(document)
			.ready(
					function() {

						/**
						 * This object controls the nav bar. Implement the add and remove
						 * action over the elements of the nav bar that we want to change.
						 *
						 * @type {{flagAdd: boolean, elements: string[], add: Function, remove: Function}}
						 */
						var myNavBar = {

							flagAdd : true,

							elements : [],

							init : function(elements) {
								this.elements = elements;
							},

							add : function() {
								if (this.flagAdd) {
									for (var i = 0; i < this.elements.length; i++) {
										document
												.getElementById(this.elements[i]).className += " fixed-theme";
									}
									this.flagAdd = false;
								}
							},

							remove : function() {
								for (var i = 0; i < this.elements.length; i++) {
									document.getElementById(this.elements[i]).className = document
											.getElementById(this.elements[i]).className
											.replace(
													/(?:^|\s)fixed-theme(?!\S)/g,
													'');
								}
								this.flagAdd = true;
							}

						};

						/**
						 * Init the object. Pass the object the array of elements
						 * that we want to change when the scroll goes down
						 */
						myNavBar
								.init([ "header", "header-container", "brand" ]);

						/**
						 * Function that manage the direction
						 * of the scroll
						 */
						function offSetManager() {

							var yOffset = 0;
							var currYOffSet = window.pageYOffset;

							if (yOffset < currYOffSet) {
								myNavBar.add();
							} else if (currYOffSet == yOffset) {
								myNavBar.remove();
							}

						}

						/**
						 * bind to the document scroll detection
						 */
						window.onscroll = function(e) {
							offSetManager();
						}

						/**
						 * We have to do a first detectation of offset because the page
						 * could be load with scroll down set.
						 */
						offSetManager();
					});
</script>


</head>
<body>

	<div class="jumbotron">
		<div class="container text-center">
			<h1>HCL United Online Store</h1>
			<p>
				<i>We prioritize your needs, in our creation</i>
			</p>
		</div>
	</div>


	<nav id="header" class="navbar navbar-fixed-top">
		<div id="header-container" class="container navbar-container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a id="brand" class="navbar-brand" href="#">HCL United Inc.</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li class="active"><a href="showUpdate">Home</a></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Collections <span
							class="caret"></span></a>
						<ul class="dropdown-menu">

							<li><a href="MenProducts">Mens Shoe</a></li>
							<li><a href="WomenProducts">Women Shoe</a></li>

						</ul></li>
					
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#"><span
							class="glyphicon glyphicon-user"></span> My Account</a>
						<ul class="dropdown-menu">
							<li><a href="#">View My Profile</a></li>
							
						</ul></li>

					<li><a href="#"><span
							class="glyphicon glyphicon-shopping-cart"></span> My Cart</a></li>

					<li><a href="#"><span class="glyphicon glyphicon-search"></span>
							Search</a></li>

				</ul>
			</div>
			<!-- /.nav-collapse -->
		</div>
		<!-- /.container -->
	</nav>
	<!-- /.navbar -->


<form:form  name="myform"  action="updateCustomer" commandName="customerTo" onsubmit="return validate() ">
<div class="container text-center">
				
				<table class="table table-hover">
<tr>
<td>Customer ID: </td>
<td><form:input type="text" path="id" value="${customerData.id}" readonly="readonly"/></td>

</tr>

<tr>
<td>Customer Email ID: </td>
<td><form:input path="email" value="${customerData.email}"  readonly="true"/></td>

</tr>
<tr>
<td>Customer Name: </td>
<td><input type="text" name="customerName" value="${customerData.customerName}"/></td>

</tr>
<tr>
<td>Customer Password: </td>
<td><input type="password" name="password" value="${customerData.password}" /></td>

</tr>
<tr>
<td>Customer Confirm Password: </td>
<td><input type="password" name="confirmPassword" value="${customerData.confirmPassword}" /></td>

</tr>
<tr>
<td>Phone Number: </td>
<td><input type="text" name="phone" value="${customerData.phone}"/></td>

</tr>
<tr>
<td>Address: </td>
<td><input type="text" name="address" value="${customerData.address}"/></td>

</tr>

<tr>
<td colspan="3" align="center"><input type="submit" value="Edit"></td>

</tr>
</table>

		</div>
</form:form>





</body>
</html>