<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="utf-8">
    <meta name="description" content="네이버 예약, 네이버 예약이 연동된 곳 어디서나 바로 예약하고, 네이버 예약 홈(나의예약)에서 모두 관리할 수 있습니다.">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
    <title>네이버 예약</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>


<body>
    <div id="container">
        <div class="header">
            <header class="header_tit">
                <h1 class="logo">
                    <a href="https://m.naver.com/" class="lnk_logo" title="네이버"> <span class="spr_bi ico_n_logo">네이버</span> </a>
                    <a href="/edwith" class="lnk_logo" title="예약"> <span class="spr_bi ico_bk_logo">예약</span> </a>
                </h1>
                <c:if test="${sessionScope.rChk == 'y' }">
                	<a href="/edwith/api/reservations/mypage" class="btn_my"> <span class="viewReservation" title="예약확인">${sessionScope.remail }</span> </a>
                </c:if>
                <c:if test="${sessionScope.rChk != 'y' }">
                	<a href="/edwith/login/page" class="btn_my"> <span class="viewReservation" title="예약확인">예약확인</span> </a>
                </c:if>
               
            </header>
        </div>
        <hr>
        <div class="event">
            <div class="section_visual">
                <div class="group_visual">
                    <div class="container_visual">
                        <div class="prev_e" style="display:none;">
                            <div class="prev_inn">
                                <a href="#" class="btn_pre_e" title="이전"> <i class="spr_book_event spr_event_pre">이전</i> </a>
                            </div>
                        </div>
                        <div class="nxt_e" style="display:none;">
                            <div class="nxt_inn">
                                <a href="#" class="btn_nxt_e" title="다음"> <i class="spr_book_event spr_event_nxt">다음</i> </a>
                            </div>
                        </div>
                        <div>
                            <div class="container_visual">
                                <!-- 슬라이딩기능: 이미지 (type = 'th')를 순차적으로 노출 -->
                                <ul class="visual_img">
                                	
                                
                                </ul>
                            </div>
                            <span class="nxt_fix" style="display:none;"></span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="section_event_tab">
                <ul class="event_tab_lst tab_lst_min">
                    <li class="item" data-category="0">
                        <a class="anchor active" data-category="0"> 전체리스트</a>
                    </li>
                    <li class="item" data-category="1">
                        <a class="anchor" data-category="1"> 전시 </a>
                    </li>
                    <li class="item" data-category="2">
                        <a class="anchor" data-category="2"> 뮤지컬 </a>
                    </li>
                    <li class="item" data-category="3">
                        <a class="anchor" data-category="3"> 콘서트 </a>
                    </li>
                    <li class="item" data-category="4">
                        <a class="anchor" data-category="4"> 클래식</a>
                    </li>
                    <li class="item" data-category="5">
                        <a class="anchor" data-category="5">연극</a>
                    </li>
                    <!-- li class="item" data-category="7">
                        <a class="anchor"> <span>클래스</span> </a>
                    </li>
                    <li class="item" data-category="8">
                        <a class="anchor"> <span>체험</span> </a>
                    </li>
                    <li class="item" data-category="9">
                        <a class="anchor last"> <span>키즈</span> </a>
                    </li -->
                </ul>
            </div>
            
            <input type="hidden" id="cur_category" value="0" />
            <input type="hidden" id="cur_page" value="1" />
            
            <div class="section_event_lst">
                <p class="event_lst_txt">바로 예매 가능한 행사가 <span class="pink point-text">10</span>개 있습니다</p>
                <div class="wrap_event_box">
                    <!-- [D] lst_event_box 가 2컬럼으로 좌우로 나뉨, 더보기를 클릭할때마다 좌우 ul에 li가 추가됨 -->
                    <!-- 더보기 -->
                    <div class="more">
                        <button class="btn more-item" style="display:block"><span>더보기</span></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <footer>
        <div class="gototop">
            <a href="#" class="lnk_top"> <span class="lnk_top_text">TOP</span> </a>
        </div>
        <div class="footer">
            <p class="dsc_footer">네이버(주)는 통신판매의 당사자가 아니며, 상품의정보, 거래조건, 이용 및 환불 등과 관련한 의무와 책임은 각 회원에게 있습니다.</p>
            <span class="copyright">© NAVER Corp.</span>
        </div>
    </footer>

	<script>
		document.addEventListener("DOMContentLoaded",function(){
			document.querySelector(".visual_img");
				
			
		});

	</script>
	
    <script type="rv-template" id="promotionItem">
    <li class="item" style=" width:100%; background-image: url(${pageContext.request.contextPath}/{productImageUrl});">
        <a href="#"> 
			<span class="img_btm_border"></span> 
			<span class="img_right_border"></span> 
			<span class="img_bg_gra"></span>
            <div class="event_txt">
                <h4 class="event_txt_tit">{productDescription}</h4>
                <p class="event_txt_adr"></p>
                <p class="event_txt_dsc"></p>
            </div>
        </a>
    </li>
    </script>

    <script type="rv-template" id="itemList">
        <li class="item">
            <a href="/edwith/api/products/{displayInfoId}" class="item_book">
                <div class="item_preview">
                    <img class="img_thumb" src="${pageContext.request.contextPath}/{productImageUrl}">
                    <span class="img_border"></span>
                </div>
                <div class="event_txt">
                    <h4 class="event_txt_tit"> <span>{productDescription}</span> <small class="sm">{placeName}</small> </h4>
                    <p class="event_txt_dsc">{productContent}</p>
                </div>
            </a>
        </li>
    </script>
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>

</html>
