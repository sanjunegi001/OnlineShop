<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<!-- search page result -->
<title>HCL_United Inc.</title>
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


<script>
	/* scroll up */

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
							<li><a href="updateCustomerByEmail">View My Profile</a></li>
							
						</ul></li>

					<li><a href="#"><span
							class="glyphicon glyphicon-shopping-cart"></span> My Cart</a></li>

					<li><a href="selectShoe"><span class="glyphicon glyphicon-search"></span>
							Search</a></li>

				</ul>
			</div>
			<!-- /.nav-collapse -->
		</div>
		<!-- /.container -->
	</nav>
	<!-- /.navbar -->

	<div class="container-fluid">
		<div class="row content">
			<div class="col-sm-4 sidenav">

				<h3>Filter Your Search</h3>
			<!-- Brand List -->
				<form:form action="search" commandName="productTO">
					<div class="form-group">
						<br> Brand

						<form:select path="productBrand" class="form-control" id="sel1">
							<form:option value="" label="--SELECT--"></form:option>
							<c:forEach var="brand" items="${brandList}">
								<form:option value="${brand.brandName}"
									label="${brand.brandName}" />
							</c:forEach>
					</div>
					</form:select>
			</div>
			<br>

			<div class="form-group">
	<!-- Price list -->
				<label>Select Price Range:</label> <br> <label
					class="radio-inline"><form:radiobutton path="productPrice"
						value="1000" /> 500-1000INR</label> <label class="radio-inline"><form:radiobutton
						path="productPrice" value="2000" /> 1000-2000INR</label> <label
					class="radio-inline"><form:radiobutton path="productPrice"
						value="4000" /> 2000-4000INR </label>
			</div>
			
			<div class="form-group">
	<!-- Product category -->
				<label>Select Category:</label> <br> <label
					class="radio-inline"><form:radiobutton path="productCategory"
						value="M" />Men</label> <label class="radio-inline"><form:radiobutton
						path="productCategory" value="F" />Women</label> <label
					class="radio-inline"> </label><br><input type="submit" value="SEARCH"
					height="15" />
			</div>

			</form:form>

		</div>

		<div class="col-sm-8">
		<form class="form-inline" action="SearchEngine">
			Search: <input type="text" class="form-control" size="50"
				placeholder="Ask me!" name="search"/>
			<input type="submit" class="btn btn-danger" value="Search"/>
		</form>
		


			<div class="container text-center">
	<!--  Product List -->
				<table class="table table-hover">

					<thead>
						<tr>
							<th>Product Id</th>
							<th>Product Brand</th>
							<th>Shoe</th>
							<th>Product Name</th>
						</tr>
					</thead>
					<c:forEach var="product" items="${imageList}">
						<tr>
							<td><a href="ProductDetail/${product.productId}">${product.productId}</a>
							</td>


							<td>${product.productBrand}</td>
							<td><img src="${product.productPicture}"
								class="img-responsive" style="width: 50%" alt="Image" /></td>
							<td>${product.productName}</td>
						</tr>
					</c:forEach>
				</table>


			</div>
		</div>







	</div>
	</div>
	</div>

	<footer class="container-fluid text-center">


		<p>Online Store Copyright 2016 @HCL TalentCare</p>


	</footer>

</body>
</html>