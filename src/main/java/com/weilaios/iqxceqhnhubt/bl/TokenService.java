package com.weilaios.iqxceqhnhubt.bl;

import com.weilaios.iqxceqhnhubt.utils.Result;

import java.util.Map;

public interface TokenService {

    public Result createToken(Map paramsMap);

    public Result abandonTicket(String token);



}
