package com.weilaios.iqxceqhnhubt.da.service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weilaios.iqxceqhnhubt.da.mapper.ConstantValueMapper;
import com.weilaios.iqxceqhnhubt.model.ConstantValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.weilaios.iqxceqhnhubt.utils.Result;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
/**
 * (ConstantValue)表服务实现类
 *
 * @author jyh
 * @since 2021-04-15 16:11:41
 */
@Service
public class ConstantValueService {

    @Resource
    private ConstantValueMapper constantValueMapper;


    public Result getById(String uuid) {
        ConstantValue constantValue = constantValueMapper.selectById(uuid);
        return Result.SUCCESS(constantValue);
    }

}