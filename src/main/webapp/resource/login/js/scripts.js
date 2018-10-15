layui.use(['element', 'jquery', 'layer', 'form'], function () {
    let layer = layui.layer;
    let $ = layui.jquery;
    $('#submitBtn').click(function () {
        let username = $('#username').val();
        let password = $('#password').val();
        if (username === '') {
            layer.msg("请输入用户名");
            return false;
        }
        if (password === '') {
            layer.msg("请输入密码");
            return false;
        }
        $("#submitBtn").attr("disabled", true);
        let data = {
            "user-Id": username,
            "username": username,
            "password": password
        };
        $.post(contextPath + '/index/login', data, function (data) {
            if ("success" === data.value) {
                layer.msg(data.des);
                window.location = contextPath + "/index/indexHome";
            } else {
                layer.msg(data.des);
            }
        });
        $("#submitBtn").html("点击登录");
        $("#submitBtn").attr("disabled", false);
        return false;
    });
    $().keyup(function (event) {
        if (event.keyCode === 13) {
            $('#submitBtn').click();
            return false;
        }
    });
});