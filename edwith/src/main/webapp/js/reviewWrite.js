
document.addEventListener("DOMContentLoaded", function(){
	init();
})

document.querySelectorAll(".rating_rdo").forEach(function(item){
	item.addEventListener("click",function(evt){
		var target = evt.target;
		var checkboxs = document.querySelectorAll(".rating_rdo");
		
		checkboxs.forEach(function(checkbox){
			if(checkbox.value <= target.value){
				if(!checkbox.classList.contains("checked"))
					checkbox.classList.add("checked");
				
				checkbox.checked = true;
			}else{
				if(checkbox.classList.contains("checked"))
					checkbox.classList.remove("checked");
				checkbox.checked = false;
			}
		});
		var star_rank = document.querySelector(".star_rank");
		star_rank.innerText = target.value;
		star_rank.classList.remove("gray_star");
	});
})


	
document.querySelector("#reviewImageFileOpenInput").addEventListener("change",function(event){
	//file preview 만들기
	var get_file = event.target.files;
	var name = '';
	
	
	/*FileReader 객체 생성*/
	var reader = new FileReader();
	/*
	 1. FileReader 객체가 #reviewImageFileOpenInput의 업로드된 파일을 읽는 행위가 종료되면 load 이벤트가 트리거 됨.
	 2.이때 이벤트가 트리거된 target에는 파일 이름과 같은 정보와 base64 인코딩 된 스트링 데이터가 result 속성에 담겨진다.
	 3. template에서 이미지 템플릿을 가져와 데이터를 세팅하면 끝. 
	 */
	reader.onload = (function(e){
		var html = document.querySelector("#img_template").innerHTML;	
		var src = e.target.result;
		console.log(name);
		html = html.replace(/{{imgUrl}}/g, name )
						.replace(/{{src}}/g,src);
		var ul = document.querySelector(".lst_thumb");
		ul.innerHTML = ul.innerHTML + html; 
	});
	
	if(get_file){
		reader.readAsDataURL(this.files[0]);
		name = event.target.files[0].name;
	}
});

function init(){
	
}

function focusReviewArea(){
	var target = document.querySelector(".review_write_info");
	target.style.cssText = "display:none";
	document.querySelector("#review_textarea").focus();
	
}