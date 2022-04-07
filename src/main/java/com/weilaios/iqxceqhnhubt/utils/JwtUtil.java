package com.weilaios.iqxceqhnhubt.utils;

import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* @Author ldw
* @Date 2021/3/15 11:12
* @Description
**/
@Component
public class JwtUtil {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    private String privateKey = "MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEA0L7uFYsZWaDjC+0vd46A+ABnYNhURmBDNno2FW7SLynVOqPYqoEU7loNyEwUB++yCYyeuN2y7XtdRKFX+8MAqQIDAQABAkB8eDyy79xPy1VW+TkCiyBvwJ3j61plFGU/iKPTJ9+AatehK1Fg0MOxjACGHiWwgh8x2zD1w6ENNFLCGDYRtagBAiEA51HEND82hkrHR9Agi6AU+P5GME+8IuQSHl0fp8qwrykCIQDnBJda3RTIgipMKMmEnWqmdy5AtGKYe2ps1lLSQeV1gQIhAKI6PAoQUyL3iq8YjdqeWFcPrRXwtZcasIJO+QZqJmORAiEAws48U4LaY7pfsJk0VX/ozzbw6d8yxSJNPDn+3Q50UAECIQCQl5NgSeqw9fSgBCRCm/voEMCFNRMFNibZDprSBf7Duw==";
    private String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANC+7hWLGVmg4wvtL3eOgPgAZ2DYVEZgQzZ6NhVu0i8p1Tqj2KqBFO5aDchMFAfvsgmMnrjdsu17XUShV/vDAKkCAwEAAQ==";
    @Autowired
    private RedisUtil redisService;

    /***
    * 生成jwt
    * @param subject 主题
    * @param claimsMap 用户信息
    * @param expiraTime 过期时间 不传默认1小时
    * @return
    * @throws Exception
    */
    public String createJWT(String subject, Map<String, Object> claimsMap, Long expiraTime) throws Exception {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(System.currentTimeMillis());
        Map<String, Object> var1 = new HashMap<>();
        var1.put("alg", "RS256");
        var1.put("typ", "JWT");
        KeyFactory kf = KeyFactory.getInstance("RSA");

        PrivateKey privKey = kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey)));

        JwtBuilder builder = Jwts.builder()
        .setHeader(var1)
        .setId(String.valueOf(claimsMap.get(UserSession.USER_UUID)))
        .setIssuedAt(now)
        .setIssuer(String.valueOf(claimsMap.get(UserSession.USERNAME)))
        .setClaims(claimsMap)
        .setSubject(subject)
        .signWith(SignatureAlgorithm.RS256, privKey);
        /** 设置过期时间  单位：秒*/
        long expMillis = nowMillis;
        if (expiraTime == null || -1L == expiraTime) {
        expiraTime = 12 * 60L * 60L * 1000;
        }
        expMillis += expiraTime;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);
        String token = builder.compact();
        redisService.set(token, token, expiraTime);
        return token;
    }


    public Map<String, Object> checkToken(String token) {
        Map<String, Object> tokenBody = new HashMap<>();
            if (StringUtils.isEmpty(token)) {
                return null;
            }
            if (!redisService.hasKey(token)) {
                throw new BusinessException("请登录后再重试");
            }
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pubkey = kf.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(publicKey)));
            tokenBody = Jwts.parser()
            .setSigningKey(pubkey)
            .parseClaimsJws(token)
            .getBody();
            tokenBody.put(Constants.FLAG, Constants.SUCCESS);
        } catch (Exception e) {
            tokenBody.put(Constants.FLAG, Constants.FAIL);
        }

        return tokenBody;
    }

    public Map<String, Object> checkJwtToken(String token) {
        Map<String, Object> tokenBody = new HashMap<>();
        if (StringUtils.isEmpty(token)) {
            return null;
        }
//        if (!redisService.hasKey(token)) {
//            throw new BusinessException("请登录后再重试");
//        }
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pubkey = kf.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(publicKey)));
            tokenBody = Jwts.parser()
                    .setSigningKey(pubkey)
                    .parseClaimsJws(token)
                    .getBody();
            tokenBody.put(Constants.FLAG, Constants.SUCCESS);
        } catch (Exception e) {
            tokenBody.put(Constants.FLAG, Constants.FAIL);
        }

        return tokenBody;
    }

    //解析token
    public Claims getClaimsFromToken(String token) throws Exception {
        Claims claims;
        try {
            if (StringUtils.isEmpty(token)) {
            return null;
            }
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pubkey = kf.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(publicKey)));
            claims = Jwts.parser()
            .setSigningKey(pubkey)
            .parseClaimsJws(token)
            .getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        return claims;
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        }

        //得到token到期时间
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    //刷新token
    public String refreshToken(String token, Long expiraTime) {
        String refreshedToken = null;
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        if (!redisService.hasKey(token)) {
            throw new BusinessException("请登录后再重试");
        }
        try {
            final Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            refreshedToken = createJWT(claims.getSubject(), claims, expiraTime);
        }
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public String refreshJwtToken(String token, Long expiraTime) {
        String refreshedToken = null;
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        /** 设置过期时间  单位：秒*/
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis;
        if (expiraTime == null || -1L == expiraTime) {
            expiraTime = 12 * 60L * 60L * 1000;
        }

        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims != null) {
                refreshedToken = createJWT(claims.getSubject(), claims, expiraTime);
            }
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    //将redis token 设置过期
    public void abandonToken(String token) {
        redisService.expire(token, 1L);
    }

}
