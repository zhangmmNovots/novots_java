package com.weilaios.iqxceqhnhubt.bl;


import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import com.weilaios.iqxceqhnhubt.model.ProjectFileStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class QiniuService  {

    public String getQiniuToken(ProjectFileStore dto) throws BusinessException {
        Auth auth = Auth.create(dto.getQnAccessKey(),dto.getQnSecretKey());
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        return auth.uploadToken(dto.getQnBucket() + ":" + dto.getFileName(), null, 3600L, putPolicy);
    }

}


