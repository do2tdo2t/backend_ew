/*
 * 예매하기 페이지(buy.jsp)에 적용  
 */

document.addEventListener("DOMContentLoaded",function(){
   init();
  
});

function init(){
	initForm();
}

/*
 * 페이지 입력 폼 초기화
 * */
function initForm(){
	document.querySelectorAll(".chk_agree").forEach(function(item){
		item.checked = false;
	});
	
	document.querySelectorAll(".total_price").forEach(function(item){
		item.innerText = "0";
	});
	
	document.querySelectorAll(".count_control_input").forEach(function(item){
		item.value = 0;
	});
	
}

/*
 * 예약 정보 파라미터 클래스
 * */
class ReservationParam{
	constructor(displayInfoId,prices, productId, reservationEmail, reservationName, reservationTelephone,reservationYearMonthDay){
		this.displayInfoId = displayInfoId;
		this.prices = prices;
		this.productId = productId;
		this.reservationEmail = reservationEmail;
		this.reservationName = reservationName;
		this.reservationTelephone = reservationTelephone;
		this.reservationDate = reservationYearMonthDay;
		this.reservationYearMonthDay = reservationYearMonthDay;
	}	
}


/*
 * 공연, 전시 가격 클래스
 * */
class ProductPrice{
	constructor(count,productPriceId){
		this.count = count;
		this.productPriceId = productPriceId;
	}
}


/*
 * 수량 플러스 버튼 클릭 시 이벤트
 * 1. 수량 텍스트 변경
 * 2. 총 금액 텍스트 변경
 * 3. 총 티켓수 텍스트 변경
 * 4. 총 가격 폼 증가
 * */
function whenClickPlus(priceType, price){
	var qty = document.querySelector("#"+priceType+"_qty");
	var total_price = document.querySelector("#"+priceType+"_total_price");
	
	//수량 증가
	qty.value = parseInt(qty.value)+1;
	
	//총금액 증가
	total_price.innerText = parseInt( total_price.innerText ) + parseInt(price);

	//총 티켓수 증가
	changeTicketTotalCount(1);
	
	//총 가격 증가
	getTotalPrice( parseInt(price) );
}

/*
 * 수량 마이너스 버튼 클릭 시 이벤트
 * 1. 수량 텍스트 변경
 * 2. 총 금액 텍스트 변경
 * 3. 총 티켓수 텍스트 변경
 * 4. 총 가격 폼 증가
 * */
function whenClickMinus(priceType, price){
	var qty = document.querySelector("#"+priceType+"_qty");
	var total_price = document.querySelector("#"+priceType+"_total_price");
	
	if(qty.value != 0){
	 	//수량감소
		qty.value = parseInt(qty.value)-1 ;
		
		//총금액 감소
		total_price.innerText = parseInt( total_price.innerText ) - parseInt(price);
		
		//총 티켓수 감소
		changeTicketTotalCount(-1);
		
		//총 가격 증가
		getTotalPrice( parseInt(price) * -1 );
		
	}
}


/*
 * 티켓의 전체 개수 텍스트 변경 (DOM 조작)
 * */
function changeTicketTotalCount(cnt){
	document.querySelector("#totalCount").innerText = parseInt(document.querySelector("#totalCount").innerText.trim())+parseInt(cnt);
}


/*
 * 이용자 약관 동의 항목의 보기 클릭 시 이벤트 처리
 * */
function whenClickShowAgreement(num){
	var agreement = document.querySelectorAll(".agreement")[parseInt(num)];
	
	if(agreement.classList.contains("open"))
		agreement.classList.remove("open");
	else
		agreement.classList.add("open");
}

/*
 * 전체 동의 클릭 시 에약하기 버튼 활성화 이벤트 처리
 * */
function whenClickAgreement(){
	var chk = document.querySelector("#chk3").checked;
	console.log(chk);
	if(chk == true){
		document.querySelector(".bk_btn_wrap").classList.remove("disable");
	}else if(chk == false){
		document.querySelector(".bk_btn_wrap").classList.add("disable");
	}
}


/**************************************************************************
 * 예약하기
 ***************************************************************************/
/*
 * submit 이벤트 처리
 *  - 예약하기 버튼이 활성화 상태가 아닐 경우 return false
 * */
function submit(){
	var bk_btn_wrap = document.querySelector(".bk_btn_wrap");
	if(bk_btn_wrap.classList.contains("disable")) return false;
	if(!check()) return false;
	
	ajax();
}

/*
 * ajax
 *  - 예약 완료 시 메인 페이지로 이동
 * */
function ajax(){
	var params = getParamStr();

	$.ajax({
		type : 'post',
		url : '/edwith/api/reservations',
		data : params,
		contentType :"application/json;charset=UTF-8",
		dataType : 'json',
		error: function(error){
			console.log(error);
		},
		success :function(json){
			alert("예약이 완료 되었습니다.");
			location.replace('/edwith');
		}
	});
	
}

/*
 * 서버로 넘길 파라미터 가져오기
 * */
function getParamStr(){
	var prices = [];
	var productPrices = document.querySelectorAll(".productPriceId");
	productPrices.forEach(function(item){
		var id = item.value;
		var count = document.querySelector( "#"+item.name+"_qty" ).value;
		prices.push(new ProductPrice(count,id));
	});
	var did = document.querySelector("#displayInfoId").value;
	var pid = document.querySelector("#productId").value;
	var date = document.querySelector("#reservateDate").value;
	var tel = document.querySelector("#tel").value;
	var email = document.querySelector("#email").value;
	var name = document.querySelector("#name").value;

	var reservationParam = new ReservationParam(did,prices, pid, email, name, tel ,date);
	return JSON.stringify(reservationParam);
}


/*
 * 입력 폼의 유효값 확인
 * */
function check(){
	var chk = document.querySelector("#chk3").checked;
	var ticketCnt = document.querySelector("#totalCount").innerText;
	
	if(parseInt(ticketCnt) < 1){
		alert("수량을 선택해주세요");
		return false;
	}
	
	if(!chk){
		alert("동의 버튼을 눌러주세요.");
		return false;
	}
	
	if(!checkName()){
		alert("입력한 성함을 확인해주세요.");
		return false;
	}
	
	if(!checkTel()){
		alert("입력한 전화번호를 확인해주세요.");
		return false;
	}
	
	if(!checkEmail()){
		alert("입력한 이메일 주소를 확인해주세요.");
		return false;
	}
	
	return true;
}

/*
 * 이메일 유효성 검사
 * */
function checkEmail(){
	var emailExp = /^[0-9a-zA-Z]{1}([-_.0-9a-zA-Z])*@([0-9a-zA-Z]+).([a-zA-Z]+)(.[a-zA-Z]+){0,1}$/;
	var remail = document.querySelector("#email").value.replace(/ /g,"");
	
	if(remail == null || remail == ""){
		return false;
	}
	
	if( emailExp.test(remail) == false){
		return false;
	}
	return true;
}

/*
 * 이름 유효성 검사
 * */
function checkName(){
	var englishExp = /[^A-Za-z]/;
	var korExp = /[^가-힣]/;
	var rname = document.querySelector("#name").value.replace(/ /g,"");
	
	if(rname == null || rname == ""){
		return false;
	}
	if( englishExp.test(rname) && korExp.test(rname)){
		return false;
	}
	return true;
}

/*
 * 전화번호 유효성 검사
 * */
function checkTel(){
	var telexp = /^01[0|1|6|7|8|9]{1}[0-9]{7,8}$/;
	var rtel = document.querySelector("#tel").value.replace(/ /g,"").replace(/-/g,"");
	
	if(rtel == null || rtel == ""){
		return false;
	}
	
	if(telexp.test(rtel) == true){	
		var tel = changeTelFormat(rtel);
		//document.querySelector("#tel").value = tel;
		return true;
	}
	return false;
}

/*
 * 000-0000-0000 format으로 변경
 * */
function changeTelFormat(tel){
	var list = [];
	if (tel.length == 7)
		mid = 3;
	else mid = 4;
	
	list.push(tel.substring(0,3));
	list.push(tel.substring(3,mid));
	list.push(tel.substring((3+mid),4));
	tel = list.join('-');
	console.log(tel);
	return tel;
}

/*
 * 전체 가격 가져오기
 * */
function getTotalPrice(price){
	var total_price = document.querySelector("#total_price").value;

	total_price.value = parseInt(total_price.value) + price;
}

/*
 * Dom에 해당 id를 가진 노드가 있는지 확인
 * */
function isExistDomNodeById(id){
	if(document.querySdocument.querySelector("#"+id)!= null )return true;
	return false;
}


