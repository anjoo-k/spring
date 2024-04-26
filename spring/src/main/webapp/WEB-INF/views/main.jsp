<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body onload="init()">
	<jsp:include page="common/header.jsp"/>
	<div class="content">
		<br><br>
		<div class="innerOUrt">
			<h4>게시글 Top 5</h4>
			<br>
			<table id="boardList" class="table table-hover" align="center">
				<thead>
					<tr>
						<th>글번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>첨부파일</th>
					</tr>
				</thead>
				<tbody>
					<!-- 현재 조횟수가 가장 높은 상위 5개 게시글을 조회해서 그리기(ajax) -->
					<tr>
						<th>${b.boardNo}</th>
						<th>${b.boardTitle}</th>
						<th>${b.boardWriter}</th>
						<th>${b.count}</th>
						<th>${b.createDat }</th>
						<th>${b.upfile}</th>
					</tr>
				</tbody>
			</table>
			
			<script>
				function init(){
					// 서버로부터 조회수가 높은 게시글 5개 조회해서 가져오기(ajax)
					// tbody 요소로써 추가해주기

					getTopBoardList(function(list){ //callback 함수를 실행하는 함수
						drawTpoListBody(list);
					})

				}
				
				function drawTpoListBody(list){
					const topBody = documnet.querySelector("#boardList > tbody");
					$(topBody).empty();
					
					for(let b of list) {
						const tr = documnet.dreatElement("tr");
						tr.innerHTML = "<td>" + b.boardNo + "</td>"
										+ "<td>" + b.boardTitle + "</td>"
										+ "<td>" + b.boardWriter + "</td>"
										+ "<td>" + b.count + "</td>"
										+ "<td>" + b.createDate + "</td>"
										+ "<td>" + (b.originName != null ? "★" : "") + "</td>"

						tr.onclick = function(){
							location.href = 'detail.bo?bno=' + b.boardNo;
						}
					

						topBody.appendChild(tr);
					}

				}


				// 서버로부터 조회수가 높은 게시글 5개 조회해서 가져오는 함수
				function getTopBoardList(callback){
					$.ajax({
						url: "topList.bo",
						success: callback,
						error: function(){
							console.log("top5 ajax 실패")
						}

						
					})
				}





			// function topCount(){
			// 	let topCountList = document.querySelctor("tbody");
			// 	$.ajax(
			// 		url : "mainTopList.ma",
	        //         data : topCountList, 
	        //         success : function(res){
	        //               callback(res)
	                     
	        //               replyRow.innerHTML = `<th>` + reply.replyWriter + `</th>
            //               <td>` + reply.replyContent + `</td>
            //               <td>` + reply.createDate + `</td>`;
                          
	        //         }, error(){
	        //             console.log("ajax 실패");
	        //         }
				
			// 	)				
			//}
				
			</script>
			
		</div>
		</div>
	<jsp:include page="common/footer.jsp"/>

</body>
</html>