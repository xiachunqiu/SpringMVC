<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/refinclude/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body class="layui-layout-body">
<h2>Hello World</h2>
<button id="export">导出excel测试</button>
</body>
</html>
<script>
    $("#export").click(function () {
        window.open(contextPath + '/index/export?' + $('#searchForm').serialize());
        return false;
    });
</script>