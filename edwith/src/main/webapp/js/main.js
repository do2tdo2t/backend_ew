/**
 * 
 */
document.addEventListener("DOMContentLoaded",function(){
   init();
})
var imgIdx = 0;

function init(){
    sildeImg();
    ajaxProduct(1,1);
    window.scrollTo(500, 0); 
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
	
	//2.
	var products = jsonData.products;
	var html = document.querySelector("#product-list-template").innerHTML;
	var newHtml = "";

	products.forEach(function(cur){
		newHtml += html.replace("{productImageUrl}",cur.productImageUrl)
		.replace("{productContent}",cur.productContent)
		.replace("{productDescription}",cur.productDescription)
		.replace("{productId}",cur.productId)
		.replace("{displayInfoId}",cur.displayInfoId);
	});
	var productTab = document.querySelector(".product-tab");
	productTab.innerHTML = newHtml;
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
