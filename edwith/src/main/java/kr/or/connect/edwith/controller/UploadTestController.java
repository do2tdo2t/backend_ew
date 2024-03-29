package kr.or.connect.edwith.controller;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/* 사용하지 X
 * 업로드 테스트용 컨트롤러 입니다.
 * */
@Controller
class UploadTestController {
	
	
	 // get방식으로 요청이 올 경우 업로드 폼을 보여줍니다.
	@GetMapping("/uploadform")
	public String uploadform() {
		return "uploadform";
	}
	
	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file) {
		
		System.out.println("파일 이름 : " + file.getOriginalFilename());
		System.out.println("파일 크기 : " + file.getSize());
		
        try(
                // 맥일 경우 
                //FileOutputStream fos = new FileOutputStream("/tmp/" + file.getOriginalFilename());
                // 윈도우일 경우
                FileOutputStream fos = new FileOutputStream("c:/tmp/" + file.getOriginalFilename());
                InputStream is = file.getInputStream();
        ){
        	    int readCount = 0;
        	    byte[] buffer = new byte[1024];
            while((readCount = is.read(buffer)) != -1){
                fos.write(buffer,0,readCount);
            }
        }catch(Exception ex){
            throw new RuntimeException("file Save Error");
        }
		
		
		return "uploadok";
	}

}
