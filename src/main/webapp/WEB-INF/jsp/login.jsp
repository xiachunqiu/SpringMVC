<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resource/login/css/reset.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resource/login/css/style.css">
    <style>
        body {
            background-color: black;
            background-size: cover;
        }
    </style>
</head>
<body>
<div class="page-container">
    <h1>登录</h1>
    <form id="loginForm">
        <input type="text" name="username" id="username" class="username" placeholder="登录名">
        <input type="password" name="password" id="password" class="password" placeholder="密码">
        <div>
            <br>
            <div id="captcha">
                <p id="wait" class="show">正在加载验证码......</p>
            </div>
        </div>
        <button type="button" id="submitBtn"> 点击登录</button>
    </form>
</div>
<script>
    let contextPath = '${pageContext.request.contextPath}';
</script>
<script src="<%=request.getContextPath()%>/resource/login/js/jquery-1.8.2.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/login/js/supersized.3.2.7.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/login/js/layui.js"></script>
<script src="<%=request.getContextPath()%>/resource/login/js/scripts.js?t=4"></script>
<script src="<%=request.getContextPath()%>/resource/login/js/gt.js"></script>
</body>
</html>