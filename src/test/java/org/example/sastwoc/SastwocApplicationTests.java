package org.example.sastwoc;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.security.auth.login.AccountLockedException;
import java.util.Calendar;

@SpringBootTest
class SastwocApplicationTests {

    @Test
    void TestGenerateToken() {
        String key="12231ba";
        //
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.SECOND,60);
        //claim声明
        JWTCreator.Builder builder=JWT.create()
                //payload的内容由一个个claim组成
                .withClaim("userid",1233231)
                .withClaim("age",12332131)
                .withClaim("url",1233231)
                .withExpiresAt(calendar.getTime());
        //hmac256摘要算法，需指定一个key
        String token=builder.sign(Algorithm.HMAC256(key));
        System.out.println(token);
    }

    @Test
    void TestVerifyToken() {
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MzcxODcxMDUsInVzZXJpZCI6MTIzMzIzMSwiYWdlIjoxMjMzMjEzMSwidXJsIjoxMjMzMjMxfQ.YVEMgcIIr9bNoIA-g9c09wnY7S6r24oR7AK_T4gv7oc";
        DecodedJWT verify=null;
        try {
            verify=JWT.require(Algorithm.HMAC256("12231ba")).build().verify(token);
        }catch (SignatureException e){
            e.printStackTrace();
            System.out.println("签名不一致");
         }catch (TokenExpiredException e){
            e.printStackTrace();
            System.out.println("令牌过期");
         }catch (AlgorithmMismatchException e){
            e.printStackTrace();
            System.out.println("算法不匹配");
         }catch (InvalidClaimException e){
            e.printStackTrace();
            System.out.println("payload不可用");
         }catch (Exception e){
            e.printStackTrace();
            System.out.println("校验失败");
         }
        if(verify!=null){
            //注意类型
            int userid=verify.getClaim("userid").asInt();
            int age=verify.getClaim("age").asInt();
            int url=verify.getClaim("url").asInt();
            System.out.println(userid);
            System.out.println(age);
            System.out.println(url);


        }
        System.out.println(verify);

    }

}
