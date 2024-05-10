<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>
    <h1>실시간 대기오염 정보</h1>

    지역:
    <select id="location">
        <option>서울</option>
        <option>대전</option>
        <option>대구</option>
        <option>부산</option>
    </select>
    <button id="btn1">해당 지역 대기 오염정보</button>
    <br><br>

    <table id="result">
        <thead>
            <tr>
                <td>측정소명</td>
                <th>측정일시</th>
                <th>통합대기환경수치</th>
                <th>미세먼지농도</th>
                <th>일산화탄소농도</th>
                <th>일산화질소농도</th>
                <th>오존농도</th>
            </tr>
        </thead>
        <tbody>
            <!-- 서버로부터 받아온 데이터 출력 --> 



        </tbody>
    </table>
</body>
<script>
    const btn1 = document.querySelector("#btn1");
    
    btn1.onclick = function(){
        $.ajax ({
            url: "air",
            data: {
                location : document.querySelector("#location").value
            },
            success: function(data){
                const itemArr = data.response.body.items;
                drawAirBody(itemArr)
            },
            error: function(){
                console.log("대기정보 api 요청 실패")
            }
        })
    }

    function drawAirBody(itemArr){
        const tbody = document.querySelector("#result tbody");
        tbody.innerHTML = "";

        for(let item of itemArr){
            tbody.innerHTML += "<tr>"
                                   + "<td>" + item.stationName + "</td>"
                                   + "<th>" + item.dataTime + "</th>"
                                   + "<th>" + item.khaiValue + "</th>"
                                   + "<th>" + item.pm10Value + "</th>"
                                   + "<th>" + item.coValue + "</th>"
                                   + "<th>" + item.no2Value + "</th>"
                                   + "<th>" + item.o3Value + "</th>"
                                + "</tr>"
        }
    }

</script>
</html>