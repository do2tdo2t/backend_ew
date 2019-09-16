/**
 * 
 */

document.addEventListener("DOMContentLoaded",function(){
   init();
  
});

function init(){
	initForm();
}


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


function whenClickPlus(priceType, price){
	var qty = document.querySelector("#"+priceType+"_qty");
	var total_price = document.querySelector("#"+priceType+"_total_price");
	
	//수량 증가
	qty.value = parseInt(qty.value)+1;
	
	//총금액 증가
	total_price.innerText = parseInt( total_price.innerText ) + parseInt(price);

	//총 티켓수 증가
	changeTicketTotalCount(1);
}

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
	}
}	

function whenClickShowAgreement(num){
	var agreement = document.querySelectorAll(".agreement")[parseInt(num)];
	
	if(agreement.classList.contains("open"))
		agreement.classList.remove("open");
	else
		agreement.classList.add("open");
}

function changeTicketTotalCount(cnt){
	document.querySelector("#totalCount").innerText = parseInt(document.querySelector("#totalCount").innerText.trim())+parseInt(cnt);

}

function whenClickAgreement(){
	var chk = document.querySelector("#chk3").checked;
	console.log(chk);
	if(chk == true){
		document.querySelector(".bk_btn_wrap").classList.remove("disable");
	}else if(chk == false){
		document.querySelector(".bk_btn_wrap").classList.add("disable");
	}
}

//============ 예약하기 ============

function submit(){
	var bk_btn_wrap = document.querySelector(".bk_btn_wrap");
	if(bk_btn_wrap.classList.contains("disable")) return false;
	if(!check()) return false;
	
	ajax();
	
}

function ajax(){
	var queryString = $("#reservateForm").serialize();
	var childs = $("#reservateForm > input");
	var form = document.querySelector("#reservateForm");
	var childs = form.getElementsByTagName("input");
	console.log(childs);
	for(var i = 0, len = childs.length ; i < len  ; i++){
		console.log(childs[i] );
	}
	
	/*
	$.ajax({
		type : 'post',
		url : '/edwith/api/reservations',
		data : queryString,
		contentType :"application/json;charset=UTF-8",
		dataType : 'json',
		error: function(error){
			console.log(error);
		},
		success :function(json){
			alert(json);
		}
	});
	*/
}

/*
 * reservationName=parknan&
 * reservationTelephone=01099992222&
 * reservationEmail=crong%40connect.com&
 * reservationDate=20190921&
 * reservationYearMonthDay=20190921&
 * prices=12000&
 * prices=0&
 * prices=0&
 * totalPrice=12000&
 * productId=1&
 * displayInfoId=1
 * */

function serializeToJson(data){
	params = data.split("&");
	jsonStr = "";
	params.forEach(function(item){
		key = "\""+ item.split("=")[0] + "\"";
		value = "\""+ item.split("=")[1] + "\"";
		jsonStr += key + ":" + value;
		
	});
	
}

//form check
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
	
	fillHiddenfield();
	return true;
	
}

function fillHiddenfield(){
	var A_price = document.querySelector("#A_total_price").innerText; //어른
	var Y_price = document.querySelector("#Y_total_price").innerText; //청소년
	var B_price = document.querySelector("#B_total_price").innerText; //유아
	
		
	document.querySelector("#A_hidden_price").value = A_price;
	document.querySelector("#Y_hidden_price").value = Y_price;
	document.querySelector("#B_hidden_price").value = B_price;
	
	document.querySelector("#total_price").value = parseInt(A_price) + parseInt(B_price)+ parseInt(Y_price);
	
}

function checkTel(){
	var telexp = /^01[0|1|6|7|8|9]{1}[0-9]{7,8}$/;
	
	var rtel = document.querySelector("#tel").value.replace(/ /g,"").replace(/-/g,"");
	
	console.log(rtel);
	if(rtel == null || rtel == ""){
		return false;
	}
	
	if(telexp.test(rtel) == true){
		document.querySelector("#tel").value = rtel;
		return true;
	}

	return false;
	 
}

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

function checkName(){
	var englishExp = /[^A-Za-z]/;
	var korExp = /[^가-힣]/;
	var rname = document.querySelector("#name").value.replace(" ","");
	
	if(rname == null || rname == ""){
		return false;
	}
	if( englishExp.test(rname) && korExp.test(rname)){
		return false;
	}
	return true;
}