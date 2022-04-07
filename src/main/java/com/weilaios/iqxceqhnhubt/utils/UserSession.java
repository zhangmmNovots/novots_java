package com.weilaios.iqxceqhnhubt.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author
 * @Date
 * @Description
 **/
public class UserSession implements Serializable {
    private static final long serialVersionUID = 117544492845913660L;

    public static final String USER_UUID = "userUuid";
    public static final String ADDRESS = "address";
    public static final String EMAIL = "email";
    public static final String IS_VERIFIED = "isVerified";
    public static final String USERNAME = "username";
    public static final String PHONE = "phone";
    public static final String MOBILE = "mobile";
    public static final String PERMISSIONS_LIST = "permissionsList";
    public static final String PLATFORM_PERMISSIONS_LIST = "platformPermissionsList";
    public static final String APPLICATION_PERMISSIONS_LIST = "applicationPermissionsList";
    public static final String HEAD_IMG = "headImg";
    public static final String PASS_WORD_UNSET = "flag";
    /**
     * 搭建端 1 个人用户 2
     */
    public static final String USER_TYPE = "userType";
    public static final String IS_ADMIN = "isAdmin";

    /***
     * 用户当前所处企业信息
     */
    public static final String ORGANIZATION_UUID = "organizationUuid";
    public static final String ORGANIZATION_NAME = "organizationName";
    public static final String MEMBER_UUID = "memberUuid";
    public static final String MEMBER_NAME = "memberName";
    public static final String MEMBER_HEAD_IMG = "memberHeadImg";
    public static final String ROLE_UUID = "roleUuid";
    public static final String DEPART_UUID = "departUuid";


    /***
     * 当前容器信息
     */
    public static final String CONTAINER_APP_UUID = "containerAppUuid";
    public static final String CONTAINER_APP_TYPE = "containerAppType";

    /***
     * 用户池id
     */
    public static final String USER_POOL_ID = "userPoolId";

    public UserSession(Map<String, Object> map) {
        this.map = map;
    }

    public UserSession() {
    }


    private Map<String, Object> map = new HashMap<String, Object>();

    public String getAttribute(String key) {

        return (String) map.get(key);
    }

    public Object getArrayAttribute(String key) {

        return map.get(key);
    }

    public void setAttribute(String key, Object value) {
        map.put(key, value);
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
