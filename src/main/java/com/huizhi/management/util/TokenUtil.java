package com.huizhi.management.util;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.huizhi.management.entity.Token;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {
    
    /**
     * 生成token的静态方法，
     * 传入用户的帐号和id
     * @param username
     * @param id
     * @return token
     */
    public static String createToken(String username,long id,Integer type){
        final String issuer = "https://mydomain.com/";
        final String secret = "123";

        final long iat = System.currentTimeMillis()/1000;
        final long exp = iat + 7200L;

        final JWTSigner signer = new JWTSigner(secret);
        final HashMap<String, Object> claims = new HashMap<String, Object>();
        String audience;
        switch (type) {
        case 0:
            audience = "admin";
            claims.put("id", id);
            claims.put("audience", audience);
            claims.put("iss", issuer);
            claims.put("exp", exp);
            claims.put("iat", iat);
            break;

        case 1:
            audience = "teacher";
            claims.put("id", id);
            claims.put("audience", audience);
            claims.put("iss", issuer);
            claims.put("exp", exp);
            claims.put("iat", iat);
            break;
        case 2:
            audience = "teacher";
            claims.put("id", id);
            claims.put("audience", audience);
            claims.put("iss", issuer);
            claims.put("exp", exp);
            claims.put("iat", iat);
            break;
        case 3:
            audience = "teacher";
            claims.put("id", id);
            claims.put("audience", audience);
            claims.put("iss", issuer);
            claims.put("exp", exp);
            claims.put("iat", iat);
            break;
        }
        
        final String token = signer.sign(claims);
 
        return token;
    }
    
    /**
     * 解析Token，其中可以获取id参数
     * @throws JWTVerifyException
     * @throws IOException 
     * @throws SignatureException 
     * @throws IllegalStateException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception
     */
    public static Token parseToken(String jwt) throws NullPointerException, InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, SignatureException, IOException, JWTVerifyException {
        final String secret = "123";
        Token token = new Token();
        try {
            final JWTVerifier verifier = new JWTVerifier(secret);
            Map<String, Object> claims = null;
            claims = verifier.verify(jwt);
            token.setId(Long.valueOf(claims.get("id").toString()));
        } catch (JWTVerifyException e) {
            System.out.println("invalid token");
        }
        return token;
    }
   
}
