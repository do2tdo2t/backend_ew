/**
 * 
 */

document.addEventListener("DOMContentLoaded",function(){
   init();
});

function init(){
	addClickEvent();
}

/*
 * tab 클릭시 변경 이벤트
 * */
function addClickEvent(){
	document.querySelectorAll(".tabanchor").forEach(function(item){
		item.addEventListener("click",function(evt){
			whenClickTab(evt.target);
		})
	});	
	
}



/*
 * HTML Templating 작업을 위한 class
 * Template
 *  - addClass(element, className): element의 클래스에 className 추가 
 *  - wrap(tag, htmlString) : tag로 html 감싸기
 *  - insertChildHtml(parent, child) : 자식 노드를 부모 노드에 HTML로 추가
 *  - insertChildElement(parent, child): 자식 노드를 부모 노드에 DOM 노드로 추가
 *  - replace(obj,html) : html의 데이터 변경 부분을 데이터로 변경 작업, return string
 *  - replaceAll(obj,html) : html의 데이터 변경 부분을 리스트의 데이토로 변경 작업 return string of list
 * html에서 {key} 부분을 해당 json 객체의 value로 replace 처리하여 반환
 * */
class Template{
	addClass(element,className){
		element.classList.add(className);
	}
	
	wrap(tag,htmlString){
		var wrapped = document.createElement(tag);
		wrapped.innerHTML = htmlString;
		return wrapped;
	}
	
	insertChildHtml(parent,child){
		parent.insertAdjacentHTML('beforeend',child);
	}
	
	insertChildElement(parent,child){
		parent.insertAdjacentElement('beforeend',child);
	}
	
	replace(obj,html){
		var result = html;
		var keys = Object.keys(obj);
		keys.forEach(function(key){
			result = result.replace('{'+key+'}',obj[key]);
		});
		return result;
	}
	
	replaceAll(objs, html){
		var list = [];
		var result;
		var keys;
		objs.forEach(function(obj){
			result = html;
			keys = Object.keys(obj);
			keys.forEach(function(key){
				result = result.replace('{'+key+'}',obj[key]);
			});
			list.push(result);
		});
		return list;
	}
}

/*
 * 
 * ajax로 한줄평 가져오기
 * */
function ajaxComments(productId){

	var url = "/edwith/api/reservations/"+productId;
	var oReq = new XMLHttpRequest();
	oReq.addEventListener("load", function(){
		var jsonData = JSON.parse(oReq.responseText);
		console.log(jsonData);
		addComments(jsonData.comments);
	});
    oReq.open("GET",url);
    oReq.send();
}

//ajax로 가져온 한줄평 화면에 템플릿으로 추가하기
function addComments(comments){
	console.log("?");
	var template = new Template();
	var comment = '';
	var commentList = [];
	var commentHtml = document.querySelector("#comment_item_template").innerText;
	var image = '';
	var imageHtml = document.querySelector("#comment_image_item_template").innerText;
	var newHtml = '';
	var ul = document.querySelector(".list_short_review");
	
	//yyyymmdd
	var date;
	console.log(commentHtml);
	
	comments.forEach(function(item){
		comment = template.replace(item,commentHtml);
		
		rdate  = item.reservationDate;
		rdate = rdate.substring(0,4)+"."+rdate.substring(4,6)+"."+rdate.substring(6,9)+".";
		
		comment = comment.replace("{date}",rdate);
		
		if (item.commentImages.length > 0 ){
			image = template.replace(item.commentImages[0],imageHtml );
			image = image.replace("{count}",item.commentImages.length);
			comment = comment.replace("<!-- comment-image -->",image);
		}
		commentList.push(comment);
	});
	
	ul.innerHTML = '';
	
	commentList.forEach(function(item){
		template.insertChildHtml(ul,item);
	});
	
	//예매자 한줄평 지우기
	document.querySelector('.btn_review_more').style.display= "none";

}


//상세보기, 오시는길 전환하는 탭
function whenClickTab(target){
	//클릭된 a 태그에 active 클래스 추가
	document.querySelectorAll(".tabanchor").forEach(function(item){
		item.classList.remove("active");
	});
	target.classList.add("active");
	
	
	document.querySelectorAll(".tab_item").forEach(function(item){
		if(item.classList.contains("hide")){
			item.classList.remove("hide");
		}else{
			item.classList.add("hide");
		}
	});
}

function whenClickOpen(){
	document.querySelector(".store_details").classList.remove("close3");
	document.querySelector("._close").style.display = "block";
	document.querySelector("._open").style.display = "none";
}

function whenClickClose(){
	document.querySelector(".store_details").classList.add("close3");
	document.querySelector("._close").style.display = "none";
	document.querySelector("._open").style.display = "block";
}

function whenClickReserve(displayInfoId){
	
	
	
}
