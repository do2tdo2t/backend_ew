/**
 * 
 */
document.addEventListener("DOMContentLoaded",function(){
   init();
})
var imgIdx = 0;
function init(){
    sildeImg();
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