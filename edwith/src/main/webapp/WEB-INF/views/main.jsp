<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>main page</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
    <body>
        <div class="gnb-bar">
            <img src="${pageContext.request.contextPath}/img/reservation-icon.png" alt="" class="gnb-img">
            <button class="gnb-bnt">비회원로그인</button>
        </div>
        <div class="promotion-section">
            <img src="${pageContext.request.contextPath}/img/10_ma_28.png" alt="" class="promotion-img" style="width:100%; display:none">
            <img src="${pageContext.request.contextPath}/img/2_ma_4.png" alt="" class="promotion-img" style="width:100%;  display:none">
            <img src="${pageContext.request.contextPath}/img/4_ma_12.png" alt="" class="promotion-img" style="width:100%;  display:none">
        </div>

            <div class="category-bar">
                <ul>
                <li class="category-item">전체리스트</li>
                <li class="category-item">전시</li>
                <li class="category-item">뮤지컬</li>
                <li class="category-item">콘서트</li>
                <li class="category-item">클래식</li>
                <li class="category-item">연극</li>
                </ul>
            </div>
            <div class="count-bar">
                <!-- ajax로 가져오자 -->
                <p class="count-text">바로 예매 가능한 전시, 공연, 행사가 <p class="point-text"> 00개</p> 있습니다.</p>
            </div>

            <div class="main">
            <!-- tab 영역 -->
            <div class="product-tab">
                <ul class="product-list-row">
                    <li class="product-list-col">
                        <img src="${pageContext.request.contextPath}/img/1_th_1.png" alt="" class="product-img">
                        <div class="product-content">
                            <div class="produt-title">{product-title}</div>
                            <br class="product-underbar">
                            <div class="product-description">{product-description}</div>
                        </div>
                    </li>
                    <li class="product-list-col">
                            <img src="${pageContext.request.contextPath}/img/1_th_1.png" alt="" class="product-img">
                            <div class="product-content">
                                <div class="produt-title">{product-title}</div>
                                <br class="product-underbar">
                                <div class="product-description">{product-description}</div>
                            </div>
                    </li>
                </ul>
                <ul class="product-list-row">
                        <li class="product-list-col">
                                <img src="${pageContext.request.contextPath}/img/1_th_1.png" alt="" class="product-img">
                                <div class="product-content">
                                    <div class="produt-title">{product-title}</div>
                                    <br class="product-underbar">
                                    <div class="product-description">{product-description}</div>
                                </div>
                        </li>
                        <li class="product-list-col">
                                <img src="${pageContext.request.contextPath}/img/1_th_1.png" alt="" class="product-img">
                                <div class="product-content">
                                    <div class="produt-title">{product-title}</div>
                                    <br class="product-underbar">
                                    <div class="product-description">{product-description}</div>
                                </div>
                        </li>
                </ul>
            </div>
            
            <!-- footer 영역 -->
            <div class="footer">
                <div class="more">더보기</div>
                <div class = "move-top">↑top</div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/js/main.js"></script>
    </body>
</body>
</html>