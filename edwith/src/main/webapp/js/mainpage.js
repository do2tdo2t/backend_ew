/**
 * 
 */

document.addEventListener("DOMContentLoaded",function(){
   init();
});


var imgIdx = 0;

function init(){
	var map ={}
	
    //sildeImg();
	ajaxProduct(1,0,false);
	
	//category 
	addClickEvent();
 
	//promotion
	ajaxPromotion();
}

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

/************************************************************************
 * class
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
 * URL Class
 * */
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

/****************************************************************
************************* EventListener *************************/
function whenClickCategory(evt){
	
	var categoryId = evt.target.dataset.category;
	var anchors = document.querySelectorAll(".anchor");
	
	document.querySelector("#cur_category").value = categoryId;
	document.querySelector("#cur_page").value = 1;
	
	//anchor active
	anchors.forEach(function(a){
		a.classList.remove('active');
	});
	
	evt.target.classList.add('active');
	ajaxProduct(1,categoryId,false);
}

function whenClickMore(evt){
	var cur_page = document.querySelector("#cur_page").value;
	var cur_category = document.querySelector("#cur_category").value;
	document.querySelector("#cur_page").value = ++cur_page;
	ajaxProduct(cur_page,cur_category,false);
}



/************************** product 추가*********************************
 **********************************************************************/
function ajaxProduct(start, categoryId){
	var url = "/edwith/api/products"
    var params = {}
    params["start"] = start;
	params["categoryId"] = categoryId;
	
	var newUrl = new URLUtil().appendParams(params , url);
	console.log("ajax event...." + newUrl);
	
	//ajax
    var oReq = new XMLHttpRequest();
    oReq.addEventListener("load",function(){
    	
    	var jsonData = JSON.parse(oReq.responseText);
    	addProduct(jsonData.products);
    	totalCnt(jsonData.totalCnt);
    	
    	//button 설정 변경하기
    	if(jsonData.products.length == jsonData.totalCnt ){
    		document.querySelector('.more-item').style.display = "none";
    	}else{
    		document.querySelector('.more-item').style.display = "block";
    	}
    });
    oReq.open("GET",newUrl);
    oReq.send();
}

function totalCnt(totalCnt){
	document.querySelector('.point-text').innerText = totalCnt;
}


function addProduct(products){
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



/**********************************************************************
 * promotion 추가
 **********************************************************************/
function ajaxPromotion(){
	var url = "/edwith/api/promotions";
	var oReq = new XMLHttpRequest();
	
	oReq.addEventListener("load",function(){
		var jsonData = JSON.parse(oReq.responseText);
		var items = jsonData.items;
		addPromotion(items);
	});
	
    oReq.open("GET",url);
    oReq.send();
}

function addPromotion(items){
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
