<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0,user-scalable=no">
	
<title>main page</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
<body>
	<div class="gnb-bar" style="width: 375px; height: 46px;">
		 <a href="https://m.naver.com/" class="lnk_logo" title="네이버">
		 <img src="${pageContext.request.contextPath}/img/favicon.ico"
			alt="" class="gnb-img" style="margin:5px"></a>
		<a href="./bookinglogin.html" class="btn_my">
			<span class="gnb-bnt" style="margin:5px" >비회원로그인</span>
		</a>
	</div>
	<div class="promotion-section"
		style="width: 375px; height: 185px; overflow: hidden;">
		<img src="${pageContext.request.contextPath}/img/10_ma_28.png" alt=""
			class="promotion-img" style="width: 100%; display: none"> <img
			src="${pageContext.request.contextPath}/img/2_ma_4.png" alt=""
			class="promotion-img" style="width: 100%; display: none"> <img
			src="${pageContext.request.contextPath}/img/4_ma_12.png" alt=""
			class="promotion-img" style="width: 100%; display: none">
	</div>
	<div class="main"
		style="width: 375px; background: white; position: relative">
		<div class="category-bar" style="width: 375px; height: 46px;">
			<ul>
				<li class="category-item" style="width:62px; height:25px; padding:10px 0; " value="0" >전체리스트</li>
				<li class="category-item" style="width:62px; height:25px; padding:10px 0;" value="1" >전시</li>
				<li class="category-item" style="width:62px; height:25px; padding:10px 0;" value="2">뮤지컬</li>
				<li class="category-item" style="width:62px; height:25px; padding:10px 0;" value="3">콘서트</li>
				<li class="category-item" style="width:62px; height:25px; padding:10px 0;" value="4">클래식</li>
				<li class="category-item" style="width:62px; height:25px; padding:10px 0;" value="5">연극</li>
			</ul>
		</div>
		<div class="count-bar" style="width: 375px; height: 45px;">
			<!-- ajax로 가져오자 -->
			<p class="count-text">바로 예매 가능한 전시, 공연, 행사가 
			<p class="point-text">00</p>개 있습니다.</p>
		</div>

		<!-- tab 영역 -->
		<div class="product-tab" style="width: 375px; ">
		</div>
	
	<!-- footer 영역 -->
	<div class="footer" style="width: 375px">
		<div class="more" style="width: 370px; height: 25px; ">더보기</div>
		<div class="move-top" style="width: 370px; height: 25px; "><a href="#">↑top</a></div>
	</div>
	</div>
	
	<script src="${pageContext.request.contextPath}/js/main.js"></script>
	
	<script id="product-list-template">
	
		<div class="product-list-col" style="width: 185px;">
				<img src="${pageContext.request.contextPath}/{productImageUrl}" alt="" class="product-img">
				<div class="product-content;">
					<span class="produt-description" style="font-size:1.25em">{productDescription}</span> 
					<hr class="product-underbar">
					<div class="product-content" style=" max-height:4.8em;  ">{productContent}</div>
				</div>
				<div style="display:none" id="productId">{productId}</div>
				<div style="display:none" id="displayInfoId">{displayInfoId}</div>
		</div>
		
	</script>
	<script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
</body>
</html>