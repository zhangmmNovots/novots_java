package com.weilaios.iqxceqhnhubt.da.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weilaios.iqxceqhnhubt.da.mapper.WlosOptionDetailMapper;
import com.weilaios.iqxceqhnhubt.da.mapper.WlosOptionSetMapper;
import com.weilaios.iqxceqhnhubt.model.WlosOptionDetail;
import com.weilaios.iqxceqhnhubt.model.WlosOptionSet;
import com.weilaios.iqxceqhnhubt.utils.WlosPagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (WlosOptionSet)表服务实现类
 *
 * @author jyh
 * @since 2021-04-15 16:11:41
 */
@Service("wlosOptionSetService")
public class WlosOptionSetService {

    @Resource
    private WlosOptionSetMapper wlosOptionSetMapper;
    @Resource
    private WlosOptionDetailMapper wlosOptionDetailMapper; 

    public Map<String, Object> querySetsAll(Integer current_page, Integer page_size, String project_id, String data_set_name) {
        Map<String, Object> map = new HashMap<>();
        List<WlosOptionSet> wlosOptionSets = null;
        WlosOptionSet wlosOptionSet = new WlosOptionSet();
        if (StringUtils.isBlank(data_set_name)) {
            wlosOptionSet.setProjectUuid(project_id);
            wlosOptionSet.setType(0);
            wlosOptionSets = wlosOptionSetMapper.selectList(new QueryWrapper<>(wlosOptionSet));
            WlosPagination wlosPagination = new WlosPagination();
            wlosPagination.setCurrent_page(current_page);
            wlosPagination.setPage_size(page_size);
            wlosPagination.setTotal_record(wlosOptionSets.size());
            map.put("pagination", wlosPagination);
        } else {
            wlosOptionSet.setUuid(data_set_name);
            wlosOptionSet.setProjectUuid(project_id);
            wlosOptionSets = wlosOptionSetMapper.selectList(new QueryWrapper<>(wlosOptionSet));
            wlosOptionSets = queryWlosOptionDetails(wlosOptionSets);
        }
        map.put("wlosOptionSets", wlosOptionSets);
        return map;
    }


    public List queryWlosOptionDetails(List<WlosOptionSet> wlosOptionSets) {
        wlosOptionSets.parallelStream().forEach(item -> {
            WlosOptionDetail wlosOptionDetail = new WlosOptionDetail();
            wlosOptionDetail.setSetUuid(item.getUuid());
            List<WlosOptionDetail> wlosOptionDetailList = wlosOptionDetailMapper.selectList(new QueryWrapper<>(wlosOptionDetail));
            WlosOptionDetail wlosOptionDetail1 = new WlosOptionDetail();
            wlosOptionDetail1.setSetUuid(item.getUuid());
            wlosOptionDetail1.setParentUuid("");
            List<WlosOptionDetail> wlosOptionDetails = wlosOptionDetailMapper.selectList(new QueryWrapper<>(wlosOptionDetail1));
            wlosOptionDetails.parallelStream().forEach(wlosOptionDetailEntity -> {
                List list = queryWlosOptionDetail(wlosOptionDetailEntity.getUuid(), wlosOptionDetailList);
                wlosOptionDetailEntity.setchildren(list);
            });
            item.setWlosOptionDetailList(wlosOptionDetails);
        });
        return wlosOptionSets;
    }

    public List queryWlosOptionDetail(String uuid, List<WlosOptionDetail> wlosOptionDetailList) {
        List<WlosOptionDetail> wlosOptionDetails = wlosOptionDetailList.stream().filter(entity -> uuid.equals(entity.getParentUuid())).collect(Collectors.toList());
        wlosOptionDetails.parallelStream().forEach(item -> {
            List list = queryWlosOptionDetail(item.getUuid(), wlosOptionDetailList);
            item.setchildren(list);
        });
        return wlosOptionDetails;
    }

}