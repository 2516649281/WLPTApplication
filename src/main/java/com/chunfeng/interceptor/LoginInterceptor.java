package com.chunfeng.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.chunfeng.result.JsonRequest;
import com.chunfeng.result.ServiceException;
import com.chunfeng.utils.RedisClientsUtils;
import com.chunfeng.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截器配置
 *
 * @author by 春风能解释
 * <p>
 * 2022/10/20
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * redis客户端注入
     */
    @Autowired
    private RedisClientsUtils redisClientsUtils;

    /**
     * 登录拦截器
     *
     * @param request  当前http请求
     * @param response 当前http响应
     * @param handler  头部信息
     * @return {@code true} 如果执行链应该继续下一个拦截器或处理程序本身，则为 true。否则，DispatcherServlet 假定此拦截器已经处理了响应本身。
     * @throws Exception 万一出现错误
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //主页面设备查询逻辑放行
        if (request.getMethod().equals("GET") && !request.getRequestURI().equals("/user")) {
            log.info("拦截器放行特殊路径!");
            return true;
        }
        //获取请求头的信息
        String token = request.getHeader("token");
        if (token == null) {
            log.warn("拦截器拦截!原因:请求头未携带token或未获取到token!");
            getJson(response);
            return false;
        }
        //解析token
        Object user = TokenUtils.checkToken(token);
        //获取Redis中的用户信息
        String user1 = redisClientsUtils.get("user");
        //redis中为空
        if (user1 == null) {
            getJson(response);
            return false;
        }
        //比较
        if (!user1.equals(user.toString())) {
            log.warn("拦截器拦截!原因:请求token与本地token不符!需要的用户Id:{},实际传入用户Id:{}", user1, user.toString());
            getJson(response);
            return false;
        }
        log.info("拦截器放行!");
        return true;
    }

    /**
     * 响应体打印
     *
     * @param response 相应对象
     * @throws IOException 如果响应体创建失败则抛出此异常
     */
    private void getJson(HttpServletResponse response) throws IOException {
        response.setStatus(ServiceException.UNAUTHORIZED.getStatus());
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        JsonRequest<Void> json = JsonRequest.error(ServiceException.UNAUTHORIZED);
        String jsonString = JSONObject.toJSONString(json);
        response.getWriter().write(jsonString);
        log.info("响应体打印:{}", jsonString);
    }
}
