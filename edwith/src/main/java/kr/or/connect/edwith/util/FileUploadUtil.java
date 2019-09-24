package kr.or.connect.edwith.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	
	public static Integer fileUpload(String folder, String name, MultipartFile file) {

		File uploadFolder = new File(folder);
		if(!uploadFolder.isDirectory()) {
			System.out.println("존재하지 X.. ");
			uploadFolder.mkdir();
		}
		
		//String newFileName = getFileNewName(uploadFolderUrl,file.getOriginalFilename());
		
		//file 새로운 이름으로 생성
		try {
			file.transferTo(new File(folder, name));
		}catch(IOException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	public static String getNewFileName(String folder, String orgFileName) {
		SimpleDateFormat format = new SimpleDateFormat ( "yyyyMMddHHmmss");
		String date;
		int idx = orgFileName.lastIndexOf("."); // 마지막 .의 위치를 구함
		
		String fileExtension = orgFileName.substring(idx+1);
		
		String newFileName = format.format(new Date())+"."+fileExtension;
		File fCheck = new File(folder,newFileName);
	
		while(fCheck.exists()) {
			date = format.format(new Date());
			newFileName = date +"."+ fileExtension;
			fCheck = new File(folder,newFileName);
		}
		return newFileName;
	}
	

}
