/**
 * 
 */

document.addEventListener("DOMContentLoaded",function(){
   init();
});

document.querySelector(".more-item").addEventListener("click",function(evt){
	
})

var imgIdx = 0;

function init(){
    //sildeImg();
    ajaxProduct(1,1);
    
    var categoryItems = document.querySelectorAll(".category-item");
    
    categoryItems.forEach(function(item){
    	item.addEventListener("click",function(evt){
        	var categoryId = evt.target.value;
        	console.log(evt.target.value);
        	
        	ajaxProduct(1,categoryId);
        });
    });
}


function makeProductTemplate(jsonData){

	//1. 개수 적용
	var totalCnt = document.querySelector(".point-text");
	totalCnt.innerText = jsonData.totalCnt;
	
	//2. list에 전시 item 붙이기 
	//각각은 2개씩 행으로 붙임 
	var products = jsonData.products;
	var eventBoxDiv = document.querySelector(".wrap_event_box");
	var ul = document.createElement("ul");
	ul.classList.add("lst_event_box");
	var html = document.querySelector("#itemList").innerText;
	var newHtml = "";
	var cnt = 0;
	var more = document.querySelector(".more");
	
	products.forEach(function(product){
		newHtml += html.replace(/{imgUrl}/g,product.productImageUrl )
		.replace(/{content}/g,product.productContent)
		.replace(/{description}/g,product.productDescription)
		.replace(/{productId}/g,product.productId)
		.replace(/{id}/g,product.displayInfoId)
		.replace(/${placeName}/g,product.placeName);
		
		ul.insertAdjacentHTML('afterbegin',newHtml);
		console.log(newHtml);
		
		newHtml = "";
		
		cnt++;
		if(cnt == 2){
			eventBoxDiv.insertAdjacentElement('afterbegin',ul);
			//ul = newHtml;
			console.log("## UL을 추가합니다..");
			console.log(eventBoxDiv.innerHTML);
			ul = document.createElement("ul");
			ul.classList.add("lst_event_box");
			cnt=0;
		}
	});
	

	
}


function ajaxProduct(start, categoryId){
	var url = "/edwith/api/products"
    var params = {}
    params["start"] = start;
	params["categoryId"] = categoryId;
	
	var newUrl = appendParams(params , url);
    var oReq = new XMLHttpRequest();
    
    console.log("ajax event...." + newUrl);
    oReq.addEventListener("load",function(){
    	console.log(oReq.responseText);
    	var jsonData = JSON.parse(oReq.responseText);
    	makeProductTemplate(jsonData);
    });
    oReq.open("GET",newUrl);
    oReq.send();
    
}


//return newUrl
function appendParams(dict,url){

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

function sildeImg(){
    var imgList =document.querySelectorAll(".promotion-img");
    if(imgIdx >= imgList.length) imgIdx = 0;
    
    for(var i = 0 ,len = imgList.length; i < len ;i++){
        imgList[i].style.display = "none";
    }
    imgList[imgIdx].style.display = "block";
    imgIdx++;
    setTimeout(sildeImg,2000);
}
