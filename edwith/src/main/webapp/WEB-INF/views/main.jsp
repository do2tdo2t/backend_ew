<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<img src="${pageContext.request.contextPath}/img/reservation-icon.png"
			alt="" class="gnb-img" style="margin:5px">
		<button class="gnb-bnt" style="margin:5px" >비회원로그인</button>
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
		style="width: 375px; height: 100%; background: white; position: relative">
		<div class="category-bar" style="width: 375px; height: 46px;">
			<ul>
				<li class="category-item" style="width:62px; height:25px; padding:10px 0; ">전체리스트</li>
				<li class="category-item" style="width:62px; height:25px; padding:10px 0;;">전시</li>
				<li class="category-item" style="width:62px; height:25px; padding:10px 0;">뮤지컬</li>
				<li class="category-item" style="width:62px; height:25px; padding:10px 0;">콘서트</li>
				<li class="category-item" style="width:62px; height:25px; padding:10px 0;">클래식</li>
				<li class="category-item" style="width:62px; height:25px; padding:10px 0;">연극</li>
			</ul>
		</div>
		<div class="count-bar" style="width: 375px; height: 45px;">
			<!-- ajax로 가져오자 -->
			<p class="count-text">바로 예매 가능한 전시, 공연, 행사가
			<p class="point-text">00개</p>
			있습니다.</p>
		</div>

		<!-- tab 영역 -->
		<div class="product-tab" style="width: 375px; height: 675px;">
			<div class="product-list-col" style="width: 185px;">
				<img src="${pageContext.request.contextPath}/img/1_th_1.png" alt=""
					class="product-img">
				<div class="product-content;">
					<span class="produt-title">{product-title}</span> <br
						class="product-underbar">
					<div class="product-description"
						style="height: 50px; overflow: hidden;">{product-description}</div>
					</div>
			</div>
			<div class="product-list-col" style="width: 185px;">
				<img src="${pageContext.request.contextPath}/img/3_th_9.png" alt=""
					class="product-img">
				<div class="product-content">
					<span class="produt-title">{product-title}</span> <br
						class="product-underbar">
					<div class="product-description"
						style="height: 50px; overflow: hidden;">{product-description}</div>
				</div>
			</div>
			<div class="product-list-col" style="width: 185px;">
				<img src="${pageContext.request.contextPath}/img/45_th_116.png"
					alt="" class="product-img">
				<div class="product-content">
					<span class="produt-title">{product-title}</span> <br
						class="product-underbar">
					<div class="product-description"
						style="height: 50px; overflow: hidden;">{product-description}x</div>
				</div>
			</div>
			<div class="product-list-col" style="width: 185px;">
				<img src="${pageContext.request.contextPath}/img/5_th_13.png" alt=""
					class="product-img">
				<div class="product-content">
					<span class="produt-title">{product-title}</span> <br
						class="product-underbar">
					<div class="product-description"
						style="height: 50px; overflow: hidden;">{product-description}</div>
				</div>
			</div>
		</div>

		<!-- footer 영역 -->
		<div class="footer" style="width: 375px">
			<div class="more"
				style="width: 370px; height: 25px; margin: 0 auto; padding: 10px 0">더보기</div>
			<div class="move-top"
				style="width: 370px; height: 25px; margin: 0 auto; padding: 10px 0">↑top</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</body>
</html>