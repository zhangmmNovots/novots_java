package com.weilaios.iqxceqhnhubt.api;

import cn.hutool.extra.spring.SpringUtil;
import com.weilaios.iqxceqhnhubt.utils.Result;
import com.weilaios.iqxceqhnhubt.bl.QiniuService;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.model.ProjectFileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class QiniuController {

    @Autowired
    private QiniuService qiniuService;

    @GetMapping("/v1/getQnUploadToken/{uuid}")
    public Result<String> getQnUploadToken(@PathVariable String uuid,@RequestParam String fileName) {
        String qnAccessKey = SpringUtil.getProperty("qn.accessKey");
        String qnSecretKey = SpringUtil.getProperty("qn.secretKey");
        String qnBucket = SpringUtil.getProperty("qn.bucket");
        ProjectFileStore projectFileStore = new ProjectFileStore();
        projectFileStore.setQnAccessKey(qnAccessKey);
        projectFileStore.setFileName(fileName);
        projectFileStore.setQnSecretKey(qnSecretKey);
        projectFileStore.setQnBucket(qnBucket);
        String qiniuToken = qiniuService.getQiniuToken(projectFileStore);
        return Result.SUCCESS(qiniuToken);
    }


}

