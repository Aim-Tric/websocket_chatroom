<%--
  Created by IntelliJ IDEA.
  User: Aim-Trick
  Date: 2019/4/30
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/header.jsp" %>
<script>
    layui.use(['jquery', 'cookie', 'laytpl', 'flow', 'laypage', 'mousewheel', 'layedit'], function () {
        var $ = layui.jquery,
            laytpl = layui.laytpl,
            layedit = layui.layedit,
            flow = layui.flow,
            laypage = layui.laypage,
            mousewheel = layui.mousewheel;

        var Screen = function (el) {
            var $this = this;
            $this.init = function () {
                $this.el = $(el);
            }

        };
        layedit.set({
            uploadImage: {
                url: ''
            }
        });

        var editor = layedit.build('input-area', {
            height: 180,
            tool: ['face','image','|','strong','italic','underline','del']
        });

        var screen = ".info-area";
        var uri = "ws://<%=request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/chat";
        var ChatSocket = function () {
            var $this = this;
            (function (uri) {
                $this.socket = new WebSocket(uri);
                $this.screen = new Screen(screen);
                $this.socket.onopen = function (ev) {
                    layer.msg("连接成功");
                    $("#send").on('click', function () {
                        sendMessage();
                    });

                    $.get("${pageContext.request.contextPath}/history/getCount", function (res) {
                        count = res;
                    });

                    $richArea.on('mousewheel', function (e) {
                        isIdle = false;
                        var isTop = $richArea.scrollTop() === 0;
                        var delta = e.originalEvent.wheelDelta;

                        isIdle = delta < 0
                            && $richArea.scrollTop() + $richArea.innerHeight() >= $richArea[0].scrollHeight;

                        if (isTop) {
                            layer.msg("正在拉取历史记录!")
                        }
                        clearTimeout($.data(this, 'timer'));
                        $.data(this, 'timer', setTimeout(function () {
                            if (isTop) {
                                if (Math.ceil(count / max) >= page) {
                                    getRecordsByMouseWheel();
                                } else {
                                    layer.msg("没有更多记录了!")
                                }
                            }
                        }, 200));
                    });

                    $input.on('keypress', function (e) {
                        var eCode = e.keyCode;
                        if (eCode === 10) {
                            e.cancelBubble = true;
                            e.preventDefault();
                            e.stopPropagation();
                            sendMessage();
                        }
                    });

                    $history.on('click', function () {
                        layer.open({
                            title: "历史聊天记录",
                            area: ['600px', '600px'],
                            type: 1,
                            content: "<div class='history-layer' id='history-layer'></div>"
                        });
                        // 分页获取data
                        laypage.render({
                            elem: 'history-layer' //注意，这里的 test1 是 ID，不用加 # 号
                            , count: count //数据总数，从服务端得到
                            , limit: 30
                            , jump: function (obj, first) {
                                // console.log(obj.limit);得到每页显示的条数
                                getRecords(obj.curr, function (data) {
                                    laytpl($infoItem.html()).render(data, function (html) {
                                        $(".history-layer").prepend(html);
                                    })
                                });
                            }
                        });
                    })


                };
                $this.socket.onmessage = function (ev) {
                    var data = JSON.parse(ev.data);
                    var claz = nickname === data.nickname ? "self" : "other";
                    var date = data.date ? dateFtt("yyyy-MM-dd hh:mm:ss", new Date(data.date)) : null;
                    if (data.nickname === "system") {
                        claz = 'system';
                    }
                    /**
                     * 将接收到的信息渲染到页面上
                     */
                    renderMessageBlock({
                        date: date,
                        claz: claz,
                        nickname: data.nickname,
                        message: data.message
                    }, false);
                    if (isIdle) {
                        $richArea.scrollTop($richArea[0].scrollHeight);
                    }
                };
                $this.socket.onerror = function (ev) {
                    layer.alert(ev.message, {icon: 2});
                };
                $this.socket.onclose = function (ev) {
                    layer.msg("你已断开连接，正在为你重连");
                    setTimeout(function () {
                        new ChatSocket();
                    }, 1000);
                };
            })(uri);
        };
        var ws = new ChatSocket();


        // 判断scroll结束
        $.fn.scrollEnd = function (callback, timeout) {
            $(this).scroll(function () {
                var $this = $(this);
                if ($this.data('scrollTimeout')) {
                    clearTimeout($this.data('scrollTimeout'));
                }
                $this.data('scrollTimeout', setTimeout(callback, timeout));
            });
        };

        function dateFtt(fmt, date) {
            var o = {
                "M+": date.getMonth() + 1,                 //月份
                "d+": date.getDate(),                    //日
                "h+": date.getHours(),                   //小时
                "m+": date.getMinutes(),                 //分
                "s+": date.getSeconds(),                 //秒
                "q+": Math.floor((date.getMonth() + 3) / 3), //季度
                "S": date.getMilliseconds()             //毫秒
            };
            if (/(y+)/.test(fmt))
                fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        }

        function renderMessageBlock(data, isPre) {
            isPre ? laytpl($infoItem.html()).render(data, function (html) {
                    $(".history-area").prepend(html);
                })
                : laytpl($infoItem.html()).render(data, function (html) {
                    $richArea.append(html);
                })
        }

        function sendMessage() {
            isIdle = true;
            var content = layedit.getContent(editor);
            if (content === "") {
                layer.msg("不能发送空白信息");
                return
            }
            $('#LAY_layedit_1').contents().find('body').html('');
            var blob = new Blob([content], {type:"text/plain;charset=utf-8"});
            ws.socket.send(blob);
        }

        function segmentation(c) {
            var arr = [];
            var len = c.length;
            if(len > 125) {
                for(var i = 0, l = len / 125; i <= l; i++) {
                    arr.push(c.slice(i*125, 125 * (i + 1)))
                }
            }
            return arr;
        }

        function getRecords(p, callback, after) {
            $.get("${pageContext.request.contextPath}/history/getRecords?page=" + p, function (res) {
                res = JSON.parse(res);
                var data = res.data;
                for (var i in data) {
                    var claz = nickname === data[i].nickname ? "self" : "other";
                    var date = data[i].date ? dateFtt("yyyy-MM-dd hh:mm:ss", new Date(data[i].date)) : null;
                    callback && callback({
                        date: date,
                        claz: claz,
                        nickname: data[i].nickname,
                        message: data[i].message
                    });
                }
                after && after(data)
            });
        }

        function getRecordsByMouseWheel() {
            getRecords(page, function (data) {
                renderMessageBlock(data, true);
            }, function (data) {
                if (page > 1 && data.length > 0) {
                    $richArea.scrollTop(((data.length - 1) * 50));
                } else {
                    $richArea.scrollTop($richArea[0].scrollHeight);
                }
                page++;
            })
        }

        var isIdle = true;
        var page = 1;
        var count = 0;
        var max = 30;
        var nickname = $.cookie("nickname");
        var $infoItem = $("#infoItem");
        var $input = $(".rich-textarea");
        var $richArea = $(".info-area");
        var $history = $(".history-btn");

        $input.focus();
    })

</script>

<%-- 聊天信息块 --%>
<script id="infoItem" type="text/html">
    <div class="info-block">
        {{# if (d.date) { }}
        <h6>{{ d.date }}</h6>
        {{# } }}
        <div class="info-item {{ d.claz }}">
            {{# if (d.claz === 'self') { }}
            <div class="message">{{ d.message }}</div>
            <div class="nickname">:{{ d.nickname }}</div>
            {{# } else if (d.claz === 'other') { }}
            <div class="nickname">{{ d.nickname }}:</div>
            <div class="message">{{ d.message }}</div>
            {{# } else { }}
            <div class="nickname">{{ d.nickname }}:</div>
            <div>{{ d.message }}</div>
            {{# } }}
        </div>
    </div>
</script>

<div class="login-page">
    <div class="chat-body">
        <div class="title-area chat-item">
            <h2>简单聊天室</h2>
        </div>
        <div class="info-area chat-item" id="info-area">
            <div class="history-area">

            </div>
        </div>
        <div class="input-area chat-item" id="input-area">

        </div>
        <div class="btn-area chat-item">
            <div class="view-btn-bar">
                <button class="layui-btn layui-btn-sm layui-btn-primary history-btn" title="滚轮上滚">聊天记录</button>
            </div>
            <div class="btn-bar">
                <button id="send" class="layui-btn layui-btn-sm layui-btn-primary send-btn" title="快捷键Alt+Enter">
                    发送(快捷键Alt+Enter)
                </button>
            </div>
        </div>
    </div>
</div>

</body>
</html>

