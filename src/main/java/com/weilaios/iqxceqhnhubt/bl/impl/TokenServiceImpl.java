package com.weilaios.iqxceqhnhubt.bl.impl;


import com.weilaios.iqxceqhnhubt.bl.TokenService;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import com.weilaios.iqxceqhnhubt.utils.JwtUtil;
import com.weilaios.iqxceqhnhubt.utils.Result;
import com.weilaios.iqxceqhnhubt.utils.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Result createToken(Map paramsMap){
        String mobile = (String) paramsMap.get("mobile");
        Assert.hasText(mobile, "电话号码不能为空");
        String uuid = (String) paramsMap.get("uuid");
        Assert.hasText(uuid, "uuid不能为空");
        String name = (String) paramsMap.get("name");
        Assert.hasText(name, "姓名不能为空");

        UserSession userSession = new UserSession();
        userSession.setAttribute(UserSession.USER_UUID, uuid);
        userSession.setAttribute(UserSession.USERNAME, name);
        userSession.setAttribute(UserSession.MOBILE, mobile);
        String token = null;
        try {
            token = jwtUtil.createJWT("novots", userSession.getMap(), -1L);
        } catch (Exception e) {
            log.error("jwt生成token失败", e);
            throw new BusinessException("jwt生成token失败", e);
        }

        return Result.SUCCESS(token);
    }

    /**
     * token设置过期
     * @param  token 用户信息
     * @return
     */
    @Override
    public Result abandonTicket(String token){
        Assert.hasText(token, "token不能为空");
        jwtUtil.abandonToken(token);
        return Result.SUCCESS();
    }




}
