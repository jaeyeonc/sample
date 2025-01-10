<!DOCTYPE html>
<meta charset="UTF-8"> <!-- 인코딩 설정 -->
<meta name="viewport" content="width=device-width, initial-scale=1.0"> <!-- 반응형 웹 디자인 -->
<meta name="description" content="로그인 폼 유효성 검사 예시"> <!-- 페이지 설명 -->
<meta name="author" content="작성자"> <!-- 작성자 정보 -->
<html>
<head>
    <title>Login Form</title>
    <script>
        // JavaScript를 이용한 클라이언트 측 Validation
        function validateForm() {
            const form = document.forms["loginForm"];
            const userIdField = form["userId"].value.trim();
            const passwordField = form["password"].value.trim();

            // 필드가 비어있는지 검증
            if (!isFieldFilled(userIdField, "아이디를 입력하세요.")) return false;
            if (!isFieldFilled(passwordField, "비밀번호를 입력하세요.")) return false;

            // 유효성 검사: 아이디 형식
            if (!isValidUserId(userIdField)) {
                alert("아이디는 4~15자 사이의 영문자와 숫자만 가능합니다.");
                return false;
            }

            return true; // 모든 검증 통과

            // 헬퍼 함수들
            function isFieldFilled(value, alertMessage) {
                if (value === "") {
                    alert(alertMessage);
                    return false;
                }
                return true;
            }

            function isValidUserId(userId) {
                const userIdRegex = /^[a-zA-Z0-9_-]{4,15}$/;
                return userIdRegex.test(userId);
            }
        }
    </script>
</head>
<body>
    <h2>로그인</h2>
    <!-- 로그인 폼 -->
    <form name="loginForm" action="login.jsp" method="post" onsubmit="return validateForm();">
        <label for="userId">아이디:</label>
        <input type="text" name="userId" id="userId"><br><br>

        <label for="password">비밀번호:</label>
        <input type="password" name="password" id="password"><br><br>

        <button type="submit">로그인</button>
    </form>
</body>
</html>