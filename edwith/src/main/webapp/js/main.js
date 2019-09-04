/**
 * 
 */
document.addEventListener("DOMContentLoaded",function(){
   init();
})
var imgIdx = 0;

function init(){
    sildeImg();
    ajaxProductList();
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

function ajaxProductList(start, categoryId){
	var url = "/edwith/api/products"
    var params = {}
    params["start"] = start;
	params["categoryId"] = categoryId;
	
	var newUrl = appendParams(params , url);
    var oReq = new XMLHttpRequest();

    console.log(url);
    oReq.addEventListener("load",function(){
    	console.log(oReq.responseText);
    	var jsonData = JSON.parse(oReq.responseText);
    	console.log(jsonData);
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