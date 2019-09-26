/*
 * myreservation.jsp에서 참조
 */

document.addEventListener("DOMContentLoaded",function(){
	init();
});

function init(){
	ajaxReservations();
}

/*
 * 서버에 예약 목록 요청 - ajax
 * */
function ajaxReservations(){
	var email = document.querySelector("#remail").value;
	var url = "/edwith/api/reservations";
	url = url + "?reservationEmail="+email;
	$.ajax({
		url: url,
		//beforeSend: function(xhr){
		//xhr.overrideMimeType( "json/application; charset=utf-8" );
		//},
		contentType :"application/json;charset=UTF-8",
		dataType : 'json',
		statusCode: {
		    404: function() {
		      alert( "page not found" );
		    }
		},
		error: function(error){
			console.log(error);
		},
		success :function(json){
			json = JSON.stringify(json);
			data = JSON.parse(json);
			console.log(data);
			adjustTemplate(data.reservations);
		}
	});
}

/*
 * 예약 목록 템플릿 작업
 * */
function adjustTemplate(revList){
	//handlebar 사용하기 위한 템플릿
	var confirmedTemp = Handlebars.compile(document.querySelector("#rev_basic_item_template").innerHTML);
	var cancelTemp =Handlebars.compile(document.querySelector("#rev_cancel_item_template").innerHTML);
	var usedTemp =Handlebars.compile(document.querySelector("#rev_used_item_template").innerHTML);
	var noneHtml = document.querySelector("#rev_none_item_template").innerHTML
	
	var cnt_map = new Map( [["cancel", 0] , ["confirmed" , 0],["used" , 0]] );
	
	var obj = new Object();
	obj["key"] = "value";
	var obj2 = {}
	obj2["key"] = "value";
	
	var html;
	var confirmedHtml ='';
	var cancelHtml = '';
	var usedHtml = '';
	
	//오늘 날짜
	var today = new Date();
	var rdate; //reservationDate
	var diff;
	
	//각 아이템별로 취소, 사용완료, 예약확정을 구분하여 템플릿 적용
	revList.forEach(function(item){
		dateSrc = item.reservationDate.split(" ")[0];
		rdate = stringToDate(dateSrc);
		
		if( item.cancelYn == true) {
			//cancelTicket 취소 티켓
			cancelHtml += cancelTemp(item);
			cnt_map.set("cancel",cnt_map.get("cancel") + 1 );
		}else{
			if(isUsedTicket(new Date(), rdate) ){
				//usedTicket 사용완료 티켓
				usedHtml += usedTemp(item);
				cnt_map.set("used", cnt_map.get("used") + 1);
			}else{
				//confirmTicket 확정티켓
				confirmedHtml += confirmedTemp(item);
				cnt_map.set("confirmed", cnt_map.get("confirmed") + 1 );
			}
		}
	});
	
	//예약 내역이 없을 경우
	if(cancelHtml == ''){
		cancelHtml = noneHtml.replace("{{msg}}","취소된 예약이 없습니다.");
	}
	if(usedHtml == ''){
		usedHtml = noneHtml.replace("{{msg}}","예약 리스트가 없습니다.");
	}
	if(confirmedHtml == ''){
		confirmedHtml = noneHtml.replace("{{msg}}","예약 리스트가 없습니다.");
	}
	
	//템플릿이 적용 후 HTML 넣기
	document.querySelector("#wrap_cancel_item").innerHTML = cancelHtml;
	document.querySelector("#wrap_confirmed_item").innerHTML = confirmedHtml;
	document.querySelector("#wrap_used_item").innerHTML = usedHtml;
	
	adjustTotal(cnt_map);
}


/*
 * 예약일 기준 사용한 티켓 여부 판단
 * */
function isUsedTicket(today, rdate){
	if(today.getTime() - rdate.getTime() > 0 ){
		return true;
	}else{
		return false;
	}
}

/*
 * 문자열을 날짜 데이터로
 * */
function stringToDate(dateSrc){
	var year = dateSrc.split("-")[0];
	var month = dateSrc.split("-")[1]-1;
	var day = dateSrc.split("-")[2];
	var date = new Date(year,month,day);
	return date;
}
/*
 * 예약 전체 통계 정보 변경
 *  - 전체예약, 예약확정, 취소예약, 예약완료 건수 표시
 * */
function adjustTotal(cnt_map){
	var id;
	var total = 0;
	console.log(typeof(cnt_map));
	console.log(cnt_map);
	
	document.querySelector("#rev_cnt_total").innertText = total;
	
	cnt_map.forEach(function(value, key){
		id = "rev_cnt_"+key;
		console.log(id);
		document.querySelector("#"+id).innerText = value;
		total += value;
	});
	
	document.querySelector("#rev_cnt_total").innerText = total;
}

/*
 * 예약 취소 버튼 클릭시 이벤트 처리
 * */
function whenClickCancelBtn(id){
	var modal = document.querySelector(".popup_booking_wrapper");
	var detail = document.querySelector("#card_detail_"+id);
	
	var title = detail.querySelector(".tit").innerText;
	var rev_date =  detail.querySelector(".rev_date").innerText;
	
	modal.querySelector(".title").innerText = title;
	modal.querySelector(".rev_date").innerText = rev_date;
	modal.querySelector(".rev_id").value = id;
	
	//modal 보이기
	modal.style.display = 'block';
}

/*
 * 예약 취소 모듈에서 "아니오" 버튼 클릭시 이벤트 처리
 * */
function whenClickNo(){
	var modal = document.querySelector(".popup_booking_wrapper");
	modal.style.display = 'none';
}

/*
 * 예약 취소 모듈에서 "예" 버튼 클릭시 이벤트 처리
 * */
function whenClickYes(){
	var modal = document.querySelector(".popup_booking_wrapper");
	var id = modal.querySelector(".rev_id").value;
	
	modal.style.display = 'none';
	
	//ajax
	ajaxDeleteRev(id);
}

/*
 * 예약 취소 시  ajax로 서버에 취소 반영 요청 - ajax
 * */
function ajaxDeleteRev(id){
	var url = "/edwith/api/reservations/"+id;
	$.ajax({
		url: url,
		type: 'PUT',
		//beforeSend: function(xhr){
		//xhr.overrideMimeType( "json/application; charset=utf-8" );
		//},
		contentType :"application/json;charset=UTF-8",
		dataType : 'json',
		data : id,
		statusCode: {
		    404: function() {
		      alert( "page not found" );
		    }
		},
		error: function(error){
			console.log(error);
		},
		success :function(json){
			json = JSON.stringify(json);
			data = JSON.parse(json);
			if(data == 200 ){
				alert("예약이 정상적으로 취소되었습니다.");
			}else{
				alert("예약 취소에 실패했습니다. 잠시후 다시 시도해주세요.");
			}
			ajaxReservations();
		}
	});
}
