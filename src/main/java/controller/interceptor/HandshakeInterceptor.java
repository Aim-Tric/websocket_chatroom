package controller.interceptor;

import common.Const;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import pojo.qo.UserQO;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 握手拦截器
 * HTTP 提升为 WebSocket 协议后，
 * 将 HTTP 的 session 中的数据过度到 WebSocket 中
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex) {
        System.err.println(ex.getMessage());
        super.afterHandshake(request, response, wsHandler, ex);
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes)
            throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                UserQO user = (UserQO) session.getAttribute(Const.USER);
                if(user != null) {
                    attributes.put(Const.USER, user);
                } else {
                    return false;
                }
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

}
