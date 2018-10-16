<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/refinclude/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body class="layui-layout-body">
<h2>Hello World</h2>
<button id="export">导出excel测试</button>
<button id="addJob">addJob</button>
<button id="qrCode">qrCode</button>
</body>
</html>
<script>
    $("#export").click(function () {
        window.open(contextPath + '/index/export');
        return false;
    });
    $("#addJob").click(function () {
        $.get(contextPath + '/index/addJob', null, function (rtn) {
            console.log(rtn);
            if ("success" === rtn.value) {

            } else {

            }
        });
        return false;
    });
    $("#qrCode").click(function () {
        window.open(contextPath + '/index/getQRCode');
        return false;
    });
</script>