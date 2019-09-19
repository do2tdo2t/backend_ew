/**
 * 
 */

document.addEventListener("DOMContentLoaded",function(){
	init();
});

function init(){
	ajaxReservations();
}

function ajaxReservations(){
	var email= 'dorosi@connect.co.kr';
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

function adjustTemplate(revList){
	//handlebar 사용하기 위한 템플릿
	var confirmedTemp = Handlebars.compile(document.querySelector("#rev_basic_item_template").innerHTML);
	var cancleTemp =Handlebars.compile(document.querySelector("#rev_cancle_item_template").innerHTML);
	var usedTemp =Handlebars.compile(document.querySelector("#rev_used_item_template").innerHTML);
	var noneHtml = document.querySelector("#rev_none_item_template").innerHTML
	
	var cnt_map = new Map( [["cancle", 0] , ["confirmed" , 0],["used" , 0]] );
	
	
	var obj = new Object();
	obj["key"] = "value";
	var obj2 = {}
	obj2["key"] = "value";
	
	
	var html;
	var confirmedHtml ='';
	var cancleHtml = '';
	var usedHtml = '';
	
	//오늘 날짜
	var today = new Date();
	var rdate; //reservationDate
	var diff;
	
	//각 아이템별로 취소, 사용완료, 예약확정을 구분하여 템플릿 적용
	revList.forEach(function(item){
		dateSrc = item.reservationDate.split(" ")[0];
		rdate = stringToDate(dateSrc);
		
		if( item.cancleYn == true) {
			//cancleTicket 취소 티켓
			cancleHtml += cancleTemp(item);
			cnt_map.set("cancle",cnt_map.get("cancle") + 1 );
		}else{
			if(isUsedTicket(new Date(), rdate) ){
				//usedTicket 사용완료 티켓
				usedHtml += usedTemp(item);
				cnt_map.set("used", cnt_map.get("used") + 1);
			}else{
				//confirmTicket 확정티켓
				confirmedHtml += confirmedTemp(item);
				cnt_map["confirmed"] = cnt_map["confirmed"] + 1;
			}
		}
	});
	
	//예약 내역이 없을 경우
	if(cancleHtml == ''){
		cancleHtml = noneHtml.replace("{{msg}}","취소된 예약이 없습니다.");
	}
	if(usedHtml == ''){
		usedHtml = noneHtml.replace("{{msg}}","예약 리스트가 없습니다.");
	}
	if(confirmedHtml == ''){
		confirmedHtml = noneHtml.replace("{{msg}}","예약 리스트가 없습니다.");
	}
	
	//템플릿이 적용 후 HTML 넣기
	document.querySelector("#wrap_cancle_item").innerHTML = cancleHtml;
	document.querySelector("#wrap_confirmed_item").innerHTML = confirmedHtml;
	document.querySelector("#wrap_used_item").innerHTML = usedHtml;
	
	adjustTotal(cnt_map);
}

function isUsedTicket(today, rdate){
	if(today.getTime() - rdate.getTime() > 0 ){
		return true;
	}else{
		return false;
	}
}

function stringToDate(dateSrc){
	var year = dateSrc.split("-")[0];
	var month = dateSrc.split("-")[1]-1;
	var day = dateSrc.split("-")[2];
	var date = new Date(year,month,day);
	return date;
}

function adjustTotal(cnt_map){
	var id;
	var total = 0;
	console.log(typeof(cnt_map));
	console.log(cnt_map);
	
	document.querySelector("#rev_cnt_total").innertText = total;
	
	
	cnt_map.forEach(function(value, key){
		id = "rev_cnt_"+key;
		document.querySelector("#"+id).innerText = value;
		
		total += value;
	});
	
	document.querySelector("#rev_cnt_total").innerText = total;
}
/*
Handlebars.registerHelper( 'wasRevComplete' ,function(options){
	
	var date = new Date();
	var tyear = date.getFullYear();
	var tmonth = date.getMonth() + 1
	var tday = date.getDate();
	//yyyy-mm-dd time
	
	var dateSrc = this.reservationDate.split(" ")[0];
	var year = dateSrc.split("-")[0];
	var month = dateSrc.split("-")[1]-1;
	var day = dateSrc.split("-")[2];
	var revDate = new Date(year,month,day);
	var diff = (date.getTime() - revDate.getTime());
	//diff가 양수이거나 0이면 예약 일자가 지남.
	console.log(date.getTime() +" " +  revDate.getTime());
	console.log(tyear+'-'+tmonth+'-'+tday);
	
	if(diff < 0 ){
		return options.fn(this);
	}else{
		return options.inverse(this);
	}
});
*/
