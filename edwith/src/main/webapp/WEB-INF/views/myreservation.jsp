<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<meta charset="utf-8">
<meta name="description"
	content="네이버 예약, 네이버 예약이 연동된 곳 어디서나 바로 예약하고, 네이버 예약 홈(나의예약)에서 모두 관리할 수 있습니다.">
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
<title>네이버 예약</title>
<link href="/edwith/css/style.css" rel="stylesheet">

</head>

<body>
	<div id="container">
		<div class="header">
			<header class="header_tit">
			<h1 class="logo">
				<a href="/edwith" class="lnk_logo" title="네이버"> <span
					class="spr_bi ico_n_logo">네이버</span>
				</a> <a href="/edwith" class="lnk_logo" title="예약"> <span
					class="spr_bi ico_bk_logo">예약</span>
				</a>
			</h1>
			<a href="/edwith/logout" class="btn_my"> <span title="내예약" class="viewReservation">로그아웃</span>
			</a>
			<input type="hidden" id="remail" value="${sessionScope.remail }"/>
			</header>
		</div>
		<hr>
		<div class="ct">
			<div class="section_my">
				<!-- 예약 현황 -->
				<div class="my_summary">
					<ul class="summary_board">
						<li class="item">
							<!--[D] 선택 후 .on 추가 link_summary_board --> <a href="#"
							class="link_summary_board on"> <i class="spr_book2 ico_book2"></i>
								<em class="tit">전체</em> <span class="figure" id="rev_cnt_total">0</span>
						</a>
						</li>
						<li class="item"><a href="#" class="link_summary_board">
								<i class="spr_book2 ico_book_ss"></i> <em class="tit">이용예정</em>
								<span class="figure"  id="rev_cnt_confirmed" >0</span>
						</a></li>
						<li class="item"><a href="#" class="link_summary_board">
								<i class="spr_book2 ico_check"></i> <em class="tit">이용완료</em> <span
								class="figure"  id="rev_cnt_used">0</span>
						</a></li>
						<li class="item"><a href="#" class="link_summary_board">
								<i class="spr_book2 ico_back"></i> <em class="tit" >취소·환불</em> <span
								class="figure" id="rev_cnt_cancel">0</span>
						</a></li>
					</ul>
				</div>

				<!-- 내 예약 리스트 -->
				<div class="wrap_mylist">
					<ul class="list_cards" ng-if="reservations.length > 0">
						<!----------------------------- 예약확정시  ----------------------------------->
						<li class="card confirmed" id="confirmed">
						<!-- 예약 확정 시 -->
							<div class="link_booking_details">
								<div class="card_header">
									<div class="left"></div>
									<div class="middle">
										<!--[D] 예약 신청중: .ico_clock, 예약확정&이용완료: .ico_check2, 취소된 예약: .ico_cancel 추가 spr_book -->
										<i class="spr_book2 ico_check2"></i> <span class="tit">예약확정</span>
									</div>
									<div class="right"></div>
								</div>
							</div>
							<div id="wrap_confirmed_item">
							
							</div>
						</li>
						<!--------------------------------------------------------------------------------->
						
						
						<!----------------------------- 이용완료시 ----------------------------------->
						
						<li class="card used">
							<div class="link_booking_details">
								<div class="card_header">
									<div class="left"></div>
									<div class="middle">
										<!--[D] 예약 신청중: .ico_clock, 예약확정&이용완료: .ico_check2, 취소된 예약: .ico_cancel 추가 spr_book -->
										<i class="spr_book2 ico_check2"></i> <span class="tit">이용 완료</span>
									</div>
									<div class="right"></div>
								</div>
							</div>
							<div id="wrap_used_item" style="margin:0; padding:0; width:100%">
							
							</div>
						</li>
						
						<!------------------------------------ 취소 시 -------------------------------->
						<li class="card used cancel">
							<div class="link_booking_details">
								<div class="card_header">
									<div class="left"></div>
									<div class="middle">
										<!--[D] 예약 신청중: .ico_clock, 예약확정&이용완료: .ico_check2, 취소된 예약: .ico_cancel 추가 spr_book -->
										<i class="spr_book2 ico_cancel"></i> <span class="tit">취소된 예약</span>
									</div>
									<div class="right"></div>
								</div>
							</div> 
							<div id="wrap_cancel_item">
							
							</div>
						</li>
					</ul>
				</div>
				<!--// 내 예약 리스트 -->
			</div>
		</div>
		<hr>
	</div>
	<footer>
	<div class="gototop">
		<a href="#" class="lnk_top"> <span class="lnk_top_text">TOP</span>
		</a>
	</div>
	<div id="footer" class="footer">
		<p class="dsc_footer">네이버(주)는 통신판매의 당사자가 아니며, 상품의정보, 거래조건, 이용 및 환불
			등과 관련한 의무와 책임은 각 회원에게 있습니다.</p>
		<span class="copyright">© NAVER Corp.</span>
	</div>
	</footer>

	<!-- 취소 팝업 -->
	<!-- [D] 활성화 display:block, 아니오 버튼 or 닫기 버튼 클릭 시 숨김 display:none; -->
	<div class="popup_booking_wrapper" style="display: none;">
		<input type="hidden" class="rev_id" />
		<div class="dimm_dark" style="display: block"></div>
		<div class="popup_booking refund">
			<h1 class="pop_tit">
				<span class="title"></span> <small class="sm">예약일 : <span class="rev_date"> </span></small>
			</h1>
			<div class="nomember_alert">
				<p>취소하시겠습니까?</p>
			</div>
			<div class="pop_bottom_btnarea">
				<div class="btn_gray">
					<a href="javascript:whenClickNo()" class="btn_bottom"><span>아니오</span></a>
				</div>
				<div class="btn_green">
					<a href="javascript:whenClickYes()" class="btn_bottom"><span>예</span></a>
				</div>
			</div>
			<!-- 닫기 -->
			<a href="#" class="popup_btn_close" title="close"> <i
				class="spr_book2 ico_cls"></i>
			</a>
			<!--// 닫기 -->
		</div>
	</div>
	<!--// 취소 팝업 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.2.0/handlebars.min.js" integrity="sha256-fIuSfP8oRbwWG9pRr+FaGfBU62kYY+7YqUsXSCgnYqM=" crossorigin="anonymous"></script>

<!--// 예약 현황 -->
<script type="rev_template" id="rev_total_template">

</script>

<script type="rev_template" id="rev_basic_item_template">
	<article class="card_item"> 
		
		<div class="card_body">
			<div class="left"></div>
			<div class="middle">
				<div class="card_detail" id="card_detail_{{reservationInfoId}}">
					<em class="booking_number">No.{{reservationInfoId}}</em>
					<a href="" class="link_booking_details"> <h4 class="tit">{{displayInfo.productDescription}}</h4> </a> 
					<ul class="detail">
						<li class="item">
							<span class="item_tit">예약일</span> 
							<em class="item_dsc rev_date">{{reservationDate}}</em>
						</li>
						<li class="item">
							<span class="item_tit">카테고리</span> 
							<em class="item_dsc"> {{displayInfo.categoryName}}</em></li>
						<li class="item">
							<span class="item_tit">장소</span> 
							<em class="item_dsc"> {{displayInfo.placeName}}</em></li>
						<li class="item">
							<span class="item_tit">전화번호</span> 
							<em class="item_dsc"> {{displayInfo.telephone}} </em></li>
					</ul>
					<div class="price_summary">
						<span class="price_tit">결제 예정금액</span>
						<em class="price_amount"> 
							<span>{{totalPrice}}</span> <span class="unit">원</span>
						</em>
					</div>
					<!-- [D] 예약 신청중, 예약 확정 만 취소가능, 취소 버튼 클릭 시 취소 팝업 활성화 -->
					<div class="booking_cancel">
						<button class="btn" onclick="whenClickCancelBtn({{reservationInfoId}})"><span>취소</span></button>
					</div>
				</div>
			</div>
			<div class="right"></div>
		</div>
		<div class="card_footer">
			<div class="left"></div>
			<div class="middle "></div>
			<div class="right"></div>
		</div>
	
	<a href="#" class="fn fn-share1 naver-splugin btn_goto_share" title="공유하기"></a> 
</article>

</script>

<script type="rev_template" id="rev_used_item_template">
<article class="card_item"> 
	<a href="/edwith/api/products/{{displayInfoId}}" class="link_booking_details">
		<div class="card_body">
			<div class="left"></div>
				<div class="middle">
					<div class="card_detail">
					<em class="booking_number">No.{{reservationInfoId}}</em>
					<h4 class="tit">{{displayInfo.productDescription}}</h4>
					<ul class="detail">

					<li class="item">
						<span class="item_tit">예약일</span> 
						<em class="item_dsc">{{reservationDate}}</em>
					</li>
					<li class="item">
						<span class="item_tit">카테고리</span> 
						<em class="item_dsc"> {{displayInfo.categoryName}}</em>
					</li>
					<li class="item">
						<span class="item_tit">장소</span> 
						<em class="item_dsc"> {{displayInfo.placeName}}</em>
					</li>
					<li class="item">
						<span class="item_tit">전화번호</span> 
						<em class="item_dsc"> {{displayInfo.telephone}} </em>
					</li>
											
					</ul>
					<div class="price_summary">
						<span class="price_tit">결제 예정금액</span>
						<em class="price_amount"> 
							<span>{{totalPrice}}</span> <span class="unit">원</span>
						</em>
					</div>
					<div class="booking_cancel">
						<a href="/edwith/api/reservations/review/{{displayInfo.productId}}/{{reservationInfoId}}"><button class="btn"><span>예매자 리뷰 남기기</span></button></a>
					</div>
				</div>
			</div>
			<div class="right"></div>
		</div>
		<div class="card_footer">
			<div class="left"></div>
			<div class="middle"></div>
			<div class="right"></div>
		</div>
	</a>
</article> 

</script>
<script type="rev_template" id="rev_cancel_item_template" >
	<article class="card_item"> 
		<a href="/edwith/api/products/{{displayInfoId}}" class="link_booking_details">
		<div class="card_body">
			<div class="left"></div>
			<div class="middle">
				<div class="card_detail">
					<em class="booking_number">No.{{reservationInfoId}}</em>
					<h4 class="tit">{{displayInfo.productDescription}}</h4>
					<ul class="detail">
						<li class="item">
							<span class="item_tit">예약일</span> 
							<em class="item_dsc">{{reservationDate}}</em>
						</li>
						<li class="item">
							<span class="item_tit">카테고리</span> 
							<em class="item_dsc"> {{displayInfo.categoryName}}</em></li>
						<li class="item">
							<span class="item_tit">장소</span> 
							<em class="item_dsc"> {{displayInfo.placeName}}</em></li>
						<li class="item">
							<span class="item_tit">전화번호</span> 
							<em class="item_dsc"> {{displayInfo.telephone}} </em></li>
					</ul>
					<div class="price_summary">
						<span class="price_tit">취소 금액</span>
						<em class="price_amount"> 
							<span>{{totalPrice}}</span> <span class="unit">원</span>
						</em>
					</div>
				</div>
			</div>
			<div class="right"></div>
		</div>
		<div class="card_footer">
			<div class="left"></div>
			<div class="middle "></div>
			<div class="right"></div>
		</div>
	</a>
</article>

</script>
<script type="rev_template" id="rev_none_item_template">
	<div class="err">
		<i class="spr_book ico_info_nolist"></i>
		<h1 class="tit">{{msg}}</h1>
	</div>
</script>



<script src="/edwith/js/myreservation.js"></script>
<script src="/edwith/js/jquery-3.4.1.min.js"></script>

</body>

</html>