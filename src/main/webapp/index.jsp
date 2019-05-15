<%@page pageEncoding="UTF-8" language="java" %>
<%@include file="/header.jsp" %>
<div class="login-page">
    <div class="layui-card login-card">
        <div class="layui-tab" lay-filter="user-tab">
            <ul class="layui-tab-title">
                <li class="layui-this">用户登录</li>
                <li>访客登录</li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <div class="layui-card-body">
                        <form class="layui-form" id="login-form"> <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
                            <div class="layui-form-item">
                                <label class="layui-form-label">用户名</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="username" lay-verify="username" placeholder="请输入用户名"
                                           autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">密码</label>
                                <div class="layui-input-inline">
                                    <input type="password" name="password" lay-verify="pass" placeholder="请输入密码"
                                           autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-input-block">
                                    <button class="layui-btn" lay-submit lay-filter="user-form">登录</button>
                                    <a href="${pageContext.request.contextPath}/user/register">
                                        <button type="button" class="layui-btn layui-btn-primary">注册</button>
                                    </a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <div class="layui-card-body">
                        <form class="layui-form" id="guest-form">
                            <!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
                            <div class="layui-form-item">
                                <label class="layui-form-label">昵称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="nickname" lay-verify="username" placeholder="请输入昵称"
                                           autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">密码</label>
                                <div class="layui-input-inline">
                                    <input type="password" name="password" disabled
                                           placeholder="请输入密码"
                                           autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-input-block">
                                    <button class="layui-btn" lay-submit lay-filter="guest">登录</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>