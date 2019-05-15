layui.config({
    base: '/extends/' //假设这是cookie.js所在的目录（本页面的相对路径）
}).extend({ //设定模块别名
    //cookie: 'cookie'   如果cookie.js是在根目录，也可以不用设定别名,因为我cookie.js的是在根目录，所以这句话其实也不用写也行。
});

layui.use(['element', 'layer', 'jquery', 'cookie', 'form', 'MD5'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        cookie = layui.cookie,
        form = layui.form,
        element = layui.element;

    /**
     * 表单验证
     */
    form.verify({
        vcode: function (value, item) {
            let r;
            $.ajax({
                url: "/user/checkVCode",
                type: "GET",
                data: {"vcode": value},
                async: false,
                success: function (result) {
                    r = result;
                }
            });
            if (r !== "success")
                return "验证码错误"
        },
        username: function (value, item) {
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '用户名不能有特殊字符';
            }
            if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                return '用户名首尾不能出现下划线\'_\'';
            }
            if (/^\d+\d+\d$/.test(value)) {
                return '用户名不能全为数字';
            }
        },
        pass: [
            /^[\S]{6,18}$/,
            '密码必须6到18位，且不能出现空格'
        ],
        repassword: function (value, item) {
            var pwd = $("input[name='password']").val();
            if(pwd !== value) {
                return "第二次输入的密码与第一次不同"
            }
        }

    });

    /**
     * 密码保护
     */
    form.on("submit(*)", function (data) {
        for (var i of data.elem) {
            if (i.name === "password") {
                var hash = md5.create();
                hash.update(i.value);
                i.value = hash.hex();
            }
        }
        return true;
    });


    /**
     * 检测用户是否登录
     */
    var username = $.cookie("username");
    if (username) {
        $(".user").html(`
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                    ${username}
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="/user/logout">退出登录</a></dd>
                </dl>
            </li>`);
        element.render("nav");
    }

    /**
     * 重新获取验证码
     */
    layui.changeImg = function () {
        var imgSrc = $("#imgObj");
        var src = imgSrc.attr("src");
        imgSrc.attr("src", chgUrl(src));
    };
    function chgUrl(url) {
        var timestamp = (new Date()).valueOf();
        url = url.substring(0, 21);
        // 时间戳
        // 为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
        if ((url.indexOf("&") >= 0)) {
            url = url + "?tamp=" + timestamp;
        } else {
            url = url + "?timestamp=" + timestamp;
        }
        return url;
    }

    /**
     * 登录弹窗
     */
    $("#lay-login").on('click', function () {
        layer.open({
            title: '登录',
            type: 1,
            area: ["500px", "400px"],
            content: `<form class="layui-form" action="/user/login" method="post" lay-filter="*">
                        <div class="layui-form-item">
                        <label class="layui-form-label">用户名</label>
                        <div class="layui-input-inline">
                        <input type="text" name="username" lay-verify="required|username" placeholder="请输入用户名" autocomplete="off"
                        class="layui-input">
                        </div>
                        </div>
                        <div class="layui-form-item">
                        <label class="layui-form-label">密码</label>
                        <div class="layui-input-inline">
                        <input type="password" name="password" lay-verify="required|pass" placeholder="请输入密码" autocomplete="off"
                        class="layui-input">
                        </div>
                        </div>
                        <div class="layui-form-item">
                        <label class="layui-form-label">验证码</label>
                        <div class="layui-input-inline">
                        <input type="text" name="vcode" lay-verify="vcode" placeholder="请输入验证码" autocomplete="off"
                        class="layui-input">
                        <img id="imgObj" onclick="layui.changeImg()" src="/user/getVCode" alt="">
                        </div>
                        </div>
                        <div class="layui-form-item">
                        <div class="layui-input-block">
                        <button lay-submit class="layui-btn">登录</button>
                        </div>
                        </div>
                        </form>
                        `
        });
    });

    /**
     * 注册弹窗
     */
    $("#lay-register").on('click', function () {
        layer.open({
            title: '注册',
            type: 1,
            area: ["500px", "550px"],
            content: `<form class="layui-form" action="/user/register" method="post" lay-filter="*">
                          <div class="layui-form-item">
                            <label class="layui-form-label">用户名</label>
                            <div class="layui-input-inline">
                              <input type="text" name="username" placeholder="请输入用户名" lay-verify="required|username" autocomplete="off" class="layui-input">
                            </div>
                          </div>
                          <div class="layui-form-item">
                            <label class="layui-form-label">密码</label>
                            <div class="layui-input-inline">
                              <input type="password" name="password" placeholder="请输入密码" lay-verify="required|pass" autocomplete="off" class="layui-input">
                            </div>
                          </div>
                          <div class="layui-form-item">
                            <label class="layui-form-label">确认密码</label>
                            <div class="layui-input-inline">
                              <input type="password" name="repassword" lay-verify="required|repassword" placeholder="请输入密码" autocomplete="off" class="layui-input">
                            </div>
                          </div>
                          <div class="layui-form-item">
                            <label class="layui-form-label">姓名</label>
                            <div class="layui-input-inline">
                              <input type="text" name="name" placeholder="请输入真实姓名" lay-verify="required" autocomplete="off" class="layui-input">
                            </div>
                          </div>
                          <div class="layui-form-item">
                            <label class="layui-form-label">邮箱地址</label>
                            <div class="layui-input-inline">
                              <input type="email" name="email" placeholder="请输入邮箱地址" lay-verify="required|email" autocomplete="off" class="layui-input">
                            </div>
                          </div>
                          <div class="layui-form-item">
                            <label class="layui-form-label">手机号</label>
                            <div class="layui-input-inline">
                              <input type="tel" name="phone" placeholder="请输入手机号" lay-verify="required|phone" autocomplete="off" class="layui-input">
                            </div>
                          </div>
                          <div class="layui-form-item">
                            <div class="layui-input-block">
                              <button class="layui-btn" lay-submit>注册</button>
                            </div>
                          </div>
                        </form>`
        });
        form.render(null, "register");
    })
});