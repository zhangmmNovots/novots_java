package com.weilaios.iqxceqhnhubt.da.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weilaios.iqxceqhnhubt.model.UvsvS;
import java.util.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * uvsvS 表数据库访问层
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Mapper
@Repository
public interface UvsvSMapper extends BaseMapper<UvsvS> {

            

	List<Map<String,Object>> Activity_0cc1d0p(Map<String,Object> paramMap) throws Exception;
}