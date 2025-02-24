package org.example.sastwoc.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.example.sastwoc.entity.User;
import org.example.sastwoc.exception.SystemException;
import org.example.sastwoc.vo.Status;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtils {
   //Jwt秘钥
   private static final String key = "abcdefghijklmn";

   //根据用户信息创建Jwt令牌
   public static String createJwt(User user){
      Calendar calendar = Calendar.getInstance();
      Date now = calendar.getTime();

      //过期时间为7天
      calendar.add(Calendar.SECOND, 3600 * 24 * 7);
      return JWT.create()
            //配置JWT自定义信息
            //.withClaim("id", user.getId())
            .withClaim("userCode", user.getUser_code())
//            .withExpiresAt(calendar.getTime())  //设置过期时间
//            .withIssuedAt(now)    //设置创建时间
            .sign(Algorithm.HMAC256(key));   //最终签名
   }

   //根据Jwt验证并解析用户信息
   public static User resolveJwt(String token){
      log.info("收到的 Token: {}", token);
      log.info("收到的 Token: {}", token);
      log.info("收到的 Token: {}", token);
      log.info("收到的 Token: {}", token);
      log.info("收到的 Token: {}", token);
      log.info("收到的 Token: {}", token);

      try {
         JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(key)).build();
         DecodedJWT verify = jwtVerifier.verify(token);  //对JWT令牌进行验证，看看是否被修改
         Map<String, Claim> claims = verify.getClaims();  //获取令牌中内容

         User user = new User();
         //user.setId(claims.get("id").asInt());
         user.setUser_code(claims.get("userCode").asString());
         user.setSalt(null);

         return user;
      } catch (TokenExpiredException e) {
         throw new SystemException(Status.FAIL.getCode(), "token过期！");
      } catch (Exception e) {
         throw new SystemException(Status.FAIL.getCode(), "token有误！");
      }
   }

}