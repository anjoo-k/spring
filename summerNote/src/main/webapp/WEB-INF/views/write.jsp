<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

</head>

<body>

	<form method="post">
		<input type= "text" name="title">
		<br><br>
  		
  		<textarea id="summernote" name="content"></textarea>
  		<br>
  		
  		<input type="submit" value="작성">
	</form>

	
	<script>
	$(document).ready(function() {
		  $('#summernote').summernote({
			  placeholder: "게시글을 작성해주세요.",
			  height: 600,
			  maxHeight: 1000,
			  width: 800,
			  toolbar: [
				    // [groupName, [list of button]]
				    ['style', ['bold', 'italic', 'underline', 'clear']],
				    ['font', ['strikethrough', 'superscript', 'subscript']],
				    ['fontsize', ['fontsize']],
				    ['color', ['color']],
				    ['para', ['ul', 'ol', 'paragraph']],
				    ['height', ['height']],
				    ['Insert', ['picture']]
				  ],
			  callbacks: {
			    onImageUpload: fileUpload
				// function(files) {
			    //   // upload image to server and create imgNode...
			    //   console.log(files); // 모르겠으면 콘솔에 찍어봐라

			    //   // ajax 통신을 이용한 이미지 업로드 필요

			    //   $summernote.summernote('insertNode', imgNode);
			    // }
			  }
			  
		  });
		});

		// 썸머노트에 이미지업로드가 발생하였을 때 동작하는 함수
		function fileUpload(fileList){
			// 썸머노트는 이미지를 추가하면 해당이미지파일을 전달해준다.
			// callback 함수를 작성하지 않을 경우 자동으로 이미지를 string으로 변환해서 보여주지만
			// customCallback을 작성할 경우 해당 이미지의 경로를 직접 주어야한다. 

			// 파일업로드를 할 때는 form태그에서 encType을 multipart/form-data 형식으로
			// 요청하는 것처럼 자바스크립트 객체에 FormData 객체를 이용해서 ajax 요청을 해준다.
			const fd = new FormData();
			for(let file of fileList){
				fd.append("fileList", file);
			}
			
			insertFileApi(fd, function(nameList){ // callback 타고 여기로 왔다
				for(let name of nameList){
					$("#summernote").summernote('insertImage', "/summer" + name);
				}				
				
			});


			
		}

		function insertFileApi(data, callback){
			$.ajax({
				url: "upload",
				type: "POST",
				data: data, // string으로 들어감
				processData: false, // 기본이 true로 true일 때는 전송하는 data를 string으로 변환해서 요청 
				contentType: false, //  application/x-www-form-urlencoded; charset=UTF-8; -> multipart/form-data로 보내기 위해 false로 설정
				dataType: "json", // 서버로부터 json으로 데이터를 받겠다.
				success: function(changeNameList){
					callback(changeNameList)
				},
				error: function(){
					console.log("파일업로드 api 요청 실패")
				}
			})

		}
	</script>
</body>
</html>