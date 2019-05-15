<%--
  Created by IntelliJ IDEA.
  User: Aim-Trick
  Date: 2019/4/30
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/header.jsp"%>

<div class="login-page">
    <div class="layui-card login-card">
        <div class="layui-card-header">用户注册</div>
        <div class="layui-card-body">
            <form class="layui-form" action="/user/register.do" method="post"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="username" lay-verify="username" placeholder="请输入用户名" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-inline">
                        <input type="password" name="password" lay-verify="pass" placeholder="请输入密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">确认密码</label>
                    <div class="layui-input-inline">
                        <input type="password" name="repassword" lay-verify="repass" placeholder="请输入确认密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">昵称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="nickname" lay-verify="nickname" placeholder="请输入昵称(聊天显示的名称)" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">真实姓名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="realname" lay-verify="realname" placeholder="请输入真实姓名" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" name="submit" lay-submit lay-filter="user-form">完成注册</button>
                        <a href="${pageContext.request.contextPath}/user/login"><button type="button" class="layui-btn layui-btn-primary">登录</button></a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
