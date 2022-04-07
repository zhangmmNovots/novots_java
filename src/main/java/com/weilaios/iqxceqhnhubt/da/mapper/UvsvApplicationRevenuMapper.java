package com.weilaios.iqxceqhnhubt.da.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weilaios.iqxceqhnhubt.model.UvsvApplicationRevenu;
import java.util.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * uvsvApplicationRevenu 表数据库访问层
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Mapper
@Repository
public interface UvsvApplicationRevenuMapper extends BaseMapper<UvsvApplicationRevenu> {

            

	List<Map<String,Object>> Activity_0f2xovj(Map<String,Object> paramMap) throws Exception;
            

	int Activity_0u8fygp(Map<String,Object> paramMap) throws Exception;
            

	List<Map<String,Object>> Activity_0f3s7ye(Map<String,Object> paramMap) throws Exception;
}