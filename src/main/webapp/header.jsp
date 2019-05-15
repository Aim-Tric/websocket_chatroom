<%--
  Created by IntelliJ IDEA.
  User: Aim-Trick
  Date: 2019/4/30
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <script src="${pageContext.request.contextPath}/layui/layui.js"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <title>网页聊天室</title>
</head>
<body>
<script>
    layui.config({
        base: '${pageContext.request.contextPath}/extends/' //假设这是cookie.js所在的目录（本页面的相对路径）
    }).extend({ //设定模块别名
        //cookie: 'cookie'   如果cookie.js是在根目录，也可以不用设定别名,因为我cookie.js的是在根目录，所以这句话其实也不用写也行。
    });

    layui.use(['element', 'jquery', 'form', 'MD5'], function () {
        var $ = layui.jquery,
            form = layui.form,
            element = layui.element;

        form.verify({
            username: function (value, item) { //value：表单的值、item：表单的DOM对象
                if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                    return '用户名不能有特殊字符';
                }
                if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                    return '用户名首尾不能出现下划线\'_\'';
                }
                if (/^\d+\d+\d$/.test(value)) {
                    return '用户名不能全为数字';
                }
            }
            , pass: [
                /^[\S]{6,18}$/
                , '密码必须6到18位，且不能出现空格'
            ]
            , repassword: function (value, item) {
                var pwd = $("input[name='password']").val();
                if (pwd !== value) {
                    return "第二次输入的密码与第一次不同"
                }
            }
            , nickname: function (value) {
                if (value.length == 0) {
                    return "昵称不能为空"
                }
            }
            , realname: function (value) {
                if (value.length === 0) {
                    return "真实姓名不能为空"
                }
            }
        });

        form.on("submit(user-form)", function (data) {
            var passInput = data.field['password'];
            var hash = md5.create();
            hash.update(passInput);
            data.field['password'] = hash.hex();
            if (data.form.id === "login-form") {
                $.ajax({
                    contentType: "application/ajax;charset=UTF-8",
                    method: "post",
                    url: "${pageContext.request.contextPath}/user/login.do",
                    data: JSON.stringify(data.field),
                    success: function (res) {
                        res = JSON.parse(res);
                        if (res.result !== "success") {
                            layer.msg(res.result);
                            return
                        }
                        window.location.href = "${pageContext.request.contextPath}/user/chat";
                    }
                });
            }
            return false
        })

        form.on("submit(guest)", function (data) {
            $.ajax({
                contentType: "application/ajax;charset=UTF-8",
                method: "post",
                url: "${pageContext.request.contextPath}/user/guest.do",
                data: JSON.stringify(data.field),
                success: function (res) {
                    res = JSON.parse(res);
                    if (res.result !== "success") {
                        layer.msg(res.result);
                        return
                    }
                    window.location.href = "${pageContext.request.contextPath}/user/chat";
                }
            });
            return false
        })

    });
</script>

