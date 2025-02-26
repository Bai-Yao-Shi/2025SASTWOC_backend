package org.example.sastwoc.interceptor;


import org.example.sastwoc.entity.User;
import org.example.sastwoc.exception.SystemException;
import org.example.sastwoc.utils.JwtUtils;
import org.example.sastwoc.utils.UserThreadLocal;
import org.example.sastwoc.vo.Status;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      //访问的其他资源直接放行
      if (!(handler instanceof HandlerMethod)) {
         return true;
      }

      //首先从Header中取出JWT
      if(request.getHeader("Authorization")==null){
         throw new SystemException(Status.FAIL.getCode(), "未登录！");
      }

        String authorization=request.getHeader("Authorization");
        String token = authorization.replace("Bearer ", "");

        User user = JwtUtils.resolveJwt(token);


        if (authorization != null && authorization.startsWith("Bearer ")) {
         user = JwtUtils.resolveJwt(authorization.substring(7));
      }

      if (user == null) {
         throw new SystemException(Status.FAIL.getCode(), "未登录！");
      }

      //将用户信息存入threadlocal
      UserThreadLocal.put(user);

      return true;
   }

   @Override
   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
      //请求线程结束后释放threadlocal中的数据，以防内存泄露
      UserThreadLocal.remove();
   }
}