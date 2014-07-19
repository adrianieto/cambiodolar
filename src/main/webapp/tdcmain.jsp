<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<%=request.getContextPath()%>/css/stylesheet.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/base.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/skeleton.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/layout.css"
	rel="stylesheet">
<title>Tipo de cambio dolar</title>
</head>
<body>
<fmt:formatDate var="BanamexDate" value="${Banamex.fecha}" 
				type="date" pattern="dd-MMM-yyyy HH:mm:ss" />
<fmt:formatDate var="BancomerDate" value="${Bancomer.fecha}" 
				type="date" pattern="dd-MMM-yyyy HH:mm:ss" />
<fmt:formatDate var="BanorteDate" value="${Banorte.fecha}" 
				type="date" pattern="dd-MMM-yyyy HH:mm:ss" />
<fmt:formatDate var="HsbcDate" value="${HSBC.fecha}" 
				type="date" pattern="dd-MMM-yyyy HH:mm:ss" />
<fmt:formatDate var="SantanderDate" value="${Santander.fecha}" 
				type="date" pattern="dd-MMM-yyyy HH:mm:ss" />
<fmt:formatDate var="SkotiabankDate" value="${Skotiabank.fecha}" 
				type="date" pattern="dd-MMM-yyyy HH:mm:ss" />
<br>
	<div class="container ">	
		<h2>Tipo de cambio dolar</h2>
		<hr>
		<div class="innercon bkg">
		<div class="fifteen columns bkg">
			<div class="fifteen columns">
				<div class="five columns omega bkgw banamex">
					<div class="four columns bkgtdc">
					<br>
					<h3>Banamex</h3>
					<br><br>
					<strong><p>Compra:$ ${Banamex.compra}</p>
					<p>Venta: $ ${Banamex.venta}</p>
					</strong>
					<br>
					</div>
					<div class="four columns bkgdate">
					<p>Fecha: ${BanamexDate}</p>
					</div>
				</div>
				
				<div class="five columns omega bkgw bancomer">
					<div class="four columns bkgtdc">
					<br>
					<h3>Bancomer</h3>
					<br><br>
					<strong>
					<p>Compra: $ ${Bancomer.compra}</p>
					<p>Venta: $ ${Bancomer.venta}</p>
					</strong>
					<br>
					</div>
					<div class="four columns bkgdate">
					<p>Fecha: ${BancomerDate}</p>
					</div>
				</div>
				<div class="five columns omega bkgw banorte">
					<div class="four columns bkgtdc">
					<br>
					<h3>Banorte</h3>
					<br><br>
					<strong>
                    <p>Compra: $ ${Banorte.compra}</p>
					<p>Venta: $ ${Banorte.venta}</p>
					</strong>
					<br>
					</div>
					<div class="four columns bkgdate">
					<p>Fecha: ${BanorteDate}</p>
					</div>
				</div>
			</div>
			
			<div class="fifteen columns bkg">
			<br>
			</div>
			<div class="fifteen columns ">
				<div class="five columns omega bkgw">
					<div class="four columns bkgtdc">
					<br>
					<h3>Santander</h3>
					<br><br>
					<strong>
					<p>Compra: $ ${Santander.compra}</p>
					<p>Venta: $ ${Santander.venta}</p>
					</strong>
					<br>
					</div>
					<div class="four columns bkgdate">
					<p>Fecha: ${SantanderDate}</p>
					</div>
				</div>
				<div class="five columns omega bkgw">
					<div class="four columns bkgtdc">
					<br>
					<h3>HSBC</h3>
					<br><br>
					<strong>
					<p>Compra: $ ${HSBC.compra}</p>
					<p>Venta: $ ${HSBC.venta}</p>
					</strong>
					<br>
					</div>
					<div class="four columns bkgdate">
					<p>Fecha: ${HsbcDate}</p>
					</div>
				</div>
				<div class="five columns omega bkgw">
					<div class="four columns bkgtdc">
					<br>
					<h3>Skotiabank</h3>
					<br><br>
					<strong>
					<p>Compra: $ ${Skotiabank.compra}</p>
					<p>Venta: $ ${Skotiabank.venta}</p>
					</strong>
					<br>
					</div>
					<div class="four columns bkgdate">
					<p>Fecha: ${SkotiabankDate}</p>
					</div>
				</div>
			</div>
		</div>
		</div>
		
	</div>

</body>
</html>
