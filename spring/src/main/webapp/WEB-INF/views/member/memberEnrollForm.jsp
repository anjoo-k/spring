<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>회원가입</title>
        </head>

        <body>

            <!-- 메뉴바 -->
            <jsp:include page="../common/header.jsp" />

            <div class="content">
                <br><br>
                <div class="innerOuter">
                    <h2>회원가입</h2>
                    <br>

                    <form action="insert.me" method="post" id="enrollForm">
                        <div class="form-group">
                            <label for="userId">* ID : </label> <!-- 이제 name은 객체의 필드값이랑 동일해야 스프링이 인식해서 자동으로 넣어준다 -->
                            <input type="text" class="form-control" id="userId" placeholder="Please Enter ID"
                                name="userId" required>
                            <div id="checkResult" style="font-size:0.7em; display:none;"></div>
                            <br>
                            <label for="userPwd">* Password : </label>
                            <input type="password" class="form-control" id="userPwd" placeholder="Please Enter Password"
                                name="userPwd" required> <br>

                            <label for="checkPwd">* Password Check : </label>
                            <input type="password" class="form-control" id="checkPwd"
                                placeholder="Please Enter Password" required> <br>

                            <label for="userName">* Name : </label>
                            <input type="text" class="form-control" id="userName" placeholder="Please Enter Name"
                                name="userName" required> <br>

                            <label for="email"> &nbsp; Email : </label>
                            <input type="text" class="form-control" id="email" placeholder="Please Enter Email"
                                name="email"> <br>

                            <label for="age"> &nbsp; Age : </label>
                            <input type="number" class="form-control" id="age" placeholder="Please Enter Age"
                                name="age"> <br>

                            <label for="phone"> &nbsp; Phone : </label>
                            <input type="tel" class="form-control" id="phone" placeholder="Please Enter Phone (-없이)"
                                name="phone"> <br>

                            <label for="address"> &nbsp; Address : </label>
                            <input type="text" class="form-control" id="address" placeholder="Please Enter Address"
                                name="address"> <br>

                            <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                            <input type="radio" id="Male" value="M" name="gender" checked>
                            <label for="Male">남자</label> &nbsp;&nbsp;
                            <input type="radio" id="Female" value="F" name="gender">
                            <label for="Female">여자</label> &nbsp;&nbsp;
                        </div>
                        <br>
                        <div class="btns" align="center">
                            <button type="submit" class="btn btn-primary" disabled>회원가입</button>
                            <button type="reset" class="btn btn-danger">초기화</button>
                        </div>
                    </form>
                </div>
                <br><br>

                <!-- 돔처리 다 끝나고 스크립트 읽어주는 방식으로 짤 것 -->

                <script>
                    $(function () {
                        const idInput = document.querySelector("#enrollForm input[name=userId]");
                        let eventFlag;
                        idInput.onkeyup = function (ev) {
                            clearTimeout(eventFlag);
                            // 키가 눌릴 때 마다 해당 아이가 중복이 되는지 검사
                            // 서버에 데이터를 보내서 응답을 받아야 한다 -> ajax를 사용해줘야
                            const str = ev.target.value;
                            if (str.trim().length >= 5) { // 5글자 이상

                                
                                eventFlag = setTimeout(function () {
                                    console.log("전송")
                                    $.ajax({
                                        url: "idCheck.me",
                                        data: { checkId: ev.target.value }, // 체크하고싶은, 사용자가 입력한 아이디
                                        success: function (result) {
                                            const checkResult = document.getElementById("checkResult");
                                            checkResult.style.display = "block";


                                            if(result === "NNNNN"){
                                                document.querySelector("#enrollForm button[type='submit']").disabled = true;
                                                checkResult.style.color = "red";
                                                checkResult.innerText = "이미 사용중인 아이디입니다."

                                            } else {
                                                document.querySelector("#enrollForm button[type='submit']").disabled = false;
                                                checkResult.style.color = "green";
                                                checkResult.innerText = "사용가능한 아이디입니다."
                                            }


                                            console.log(result)
                                        },
                                        error: function () {
                                            console.log("아이디 중복체크 ajax 실패");
                                        }
                                    })
                                }, 500)
                            } else { // 5글자 이하
                                //disabled상태 유지
                                document.querySelector("#enrollForm button[type='submit']").disabled = true;
                                //checkResult 안보이는 상태
                                document.getElementById("checkResult").style.display = "none";
                            }
                        }

                    })



                </script>

            </div>

            <!-- 푸터바 -->
            <jsp:include page="../common/footer.jsp" />

        </body>

        </html>