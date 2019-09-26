/**
 * main.jsp에서 참조하는 소스파일
 */

document.addEventListener("DOMContentLoaded",function(){
   init();
});


function init(){
	
	ajaxProduct(1,0,false);
	
	addClickEvent();
 
	ajaxPromotion();
}

/*
 * 클릭 이벤트 등록
 */
function addClickEvent(){
	   document.querySelectorAll(".anchor").forEach(function(item){
	    	item.addEventListener("click",function(evt){
	    		whenClickCategory(evt);
	        });
	    });
	    
	    document.querySelector(".more").addEventListener("click",function(evt){
	    	whenClickMore(evt);
	    });
}

/*
 * 
 * HTML Templating 작업을 위한 class Template - addClass(element, className):
 * element의 클래스에 className 추가 - wrap(tag, htmlString) : tag로 html 감싸기 -
 * insertChildHtml(parent, child) : 자식 노드를 부모 노드에 HTML로 추가 -
 * insertChildElement(parent, child): 자식 노드를 부모 노드에 DOM 노드로 추가 -
 * replace(obj,html) : html의 데이터 변경 부분을 데이터로 변경 작업, return string -
 * replaceAll(obj,html) : html의 데이터 변경 부분을 리스트의 데이토로 변경 작업 return string of list
 * html에서 {key} 부분을 해당 json 객체의 value로 replace 처리하여 반환
 * 
 */
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
	
	replace(obj){
		var result = this.html;
		var keys = Object.keys(obj);
		keys.forEach(function(key){
			result = result.replace('{'+key+'}',product[key]);
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
 * URL Class url 처리를 위한 클래스 - appendParams : url에 파라미터 생성
 */
class URLUtil{
	
	appendParams(dict,url){
		var isFirstParam = true;
		for(var key in dict){
			if(dict[key] == undefined) break;
			if(dict[key] == "") break;
			
			if(isFirstParam){
				url+="?";
				isFirstParam = false;
			}else{
				url+="&";
			}
			url+=key+"="+dict[key];
		}
		return url;
	}
	
}


/*
 * 카테고리 항목 클릭 시 아래 공연, 전시 탭 데이터 변경 ajax로 데이터 가져와서 Templating 작업
 */
function whenClickCategory(evt){
	
	var categoryId = evt.target.dataset.category;
	var anchors = document.querySelectorAll(".anchor");
	
	document.querySelector("#cur_category").value = categoryId;
	document.querySelector("#cur_page").value = 1;
	
	// anchor active
	anchors.forEach(function(a){
		a.classList.remove('active');
	});
	
	evt.target.classList.add('active');
	ajaxProduct(1,categoryId,false);
}

/*
 * "더보기" 버튼 클릭 시 4개의 공연,전시 정보 가져와서 추가 1. ajax로 데이터 서버에 요청
 */
function whenClickMore(evt){
	var cur_page = document.querySelector("#cur_page").value;
	var cur_category = document.querySelector("#cur_category").value;
	document.querySelector("#cur_page").value = ++cur_page;
	ajaxProduct(cur_page,cur_category,false);
}

/*
 * 카테고리에 맞는 공연, 전시 정보로 탭 내용 변경 - ajax 1. ajax로 데이터 서버에 요청
 */
function ajaxPromotion(){
	var url = "/edwith/api/promotions";
	var oReq = new XMLHttpRequest();
	
	oReq.addEventListener("load",function(){
		var jsonData = JSON.parse(oReq.responseText);
		var promotions = jsonData.items;
		templatePromotion(promotions);
	});
	
    oReq.open("GET",url);
    oReq.send();
}



/*
 * 공연, 전시 정보 서버에 요청 - ajax 1. ajax로 데이터 서버에 요청 2. HTML tempating 작업 3. 공연, 전시 정보
 * 전체 수 변경 4. 모든 공연, 전시 가져왔을 경우 더보기 버튼 없애기
 */
function ajaxProduct(start, categoryId){
	var url = "/edwith/api/products"
    var params = {}
    params["start"] = start;
	params["categoryId"] = categoryId;
	
	var newUrl = new URLUtil().appendParams(params , url);
	console.log("ajax event...." + newUrl);
	
	// 1. ajax로 데이터 서버에 요청
    var oReq = new XMLHttpRequest();
    oReq.addEventListener("load",function(){
    	
    	var jsonData = JSON.parse(oReq.responseText);
    	// 2. HTML tempating 작업
    	templateProduct(jsonData.products);
    	
    	// 3. 공연, 전시 정보 전체 수 변경
    	totalCnt(jsonData.totalCnt);
    	
    	// 4. 모든 공연, 전시 가져왔을 경우 더보기 버튼 없애기
    	if(jsonData.products.length == jsonData.totalCnt ){
    		document.querySelector('.more-item').style.display = "none";
    	}else{
    		document.querySelector('.more-item').style.display = "block";
    	}
    });
    oReq.open("GET",newUrl);
    oReq.send();
}

/*
 * product 템플릿 작업 - ajaxProduct 함수에서 호출
 */
function templateProduct(products){
	var html = document.querySelector('#itemList').innerHTML;
	var template = new Template();
	var htmlList = template.replaceAll(products,html);
	var ulList = [];
	var ul;
	var cnt = 0;
	var more = document.querySelector('.more');
	var box = document.querySelector(".wrap_event_box");
	var len = products.length/2;

	htmlList.forEach(function(item){
		
		if(cnt%len == 0){
			ul = template.wrap('ul',item);
			template.addClass(ul,'lst_event_box');
		}else{
			template.insertChildHtml(ul,item);
			ulList.push(ul);
		}
		cnt++;
	});
	
	box.innerHTML = "";
	
	ulList.forEach(function(item){
		template.insertChildElement(box,item);
	});
	
	template.insertChildElement(box,more);
}


/*
 * 공연,전시 목록의 총 개수 텍스트 변경 DOM 작업
 */
function totalCnt(totalCnt){
	document.querySelector('.point-text').innerText = totalCnt;
}

/*
 * 프로모션 템플릿 작업 - ajaxPromotion 함수에서 호출
 */
function templatePromotion(items){
	console.log(items);
	var html = document.querySelector("#promotionItem").innerHTML;
	
	var template = new Template();
	var list = template.replaceAll(items,html);
	
	html = '';
	list.forEach(function(item){
		html += item;
	});
	
	console.log(html);
	
	document.querySelector('.visual_img').innerHTML = html;
}
