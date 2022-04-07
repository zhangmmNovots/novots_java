package com.weilaios.iqxceqhnhubt.da.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weilaios.iqxceqhnhubt.model.WlosOptionDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * (WlosOptionSet)表数据库访问层
 *
 * @author jyh
 * @since 2021-04-15 16:11:46
 */
@Mapper
@Repository
public interface WlosOptionDetailMapper  extends BaseMapper<WlosOptionDetail> {

    
}