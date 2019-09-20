<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>uploadform</title>
</head>
<body>
<h1>Upload Form</h1>
<br><br>
 <form method="post" action="/api/comments/1" enctype="multipart/form-data">

    file :    <input type="file" name="file"><br>
		<input name="comment" value="하하하하하">
		<input name="score" value="3">
		<input name="reservationInfoId" value="1">
        <input type="submit">
 </form>    
</body>
<
</html>
 

파일 업로드를 처리하는 FileController를 작성합니다.
