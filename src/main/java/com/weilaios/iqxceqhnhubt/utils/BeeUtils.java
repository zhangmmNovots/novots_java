package com.weilaios.iqxceqhnhubt.utils;

import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public class BeeUtils {


    /**
     * 判断属性是否为空或null 不能判断集合是否为空！
     * @param fieldName
     * @param fieldValue
     * @throws BusinessException
     */
    public static void isEmpty(String fieldName, Object fieldValue) throws BusinessException {
        if (null == fieldValue || !StringUtils.hasText(fieldValue.toString())) {
            throw new BusinessException(fieldName + "不能为空");
        }
    }

    /**
     * 检查email
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkIdCard(String idNum){
        boolean flag=false;
        try{
            String pattern = "([0-9]{17}([0-9]|X))|([0-9]{15})";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(idNum);
            flag=m.matches();
        }catch (Exception e){
            flag=false;
        }
        return  flag;
    }

    /**
     * 验证手机号码 验证规则为1开头的11位数字
     *
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag;
        try {
            Pattern regex = Pattern.compile("^1\\d{10}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 判断手机号码是否合法（不能为空且满足校验规则） 校验规则：首位为1开始，且为11位
     *
     * @param fieldName
     * @param mobile
     * @throws BusinessException
     */
    public static void mobileIsLegal(String fieldName, String mobile) throws BusinessException {
        if (StringUtils.isEmpty(mobile)) {
            throw new BusinessException(fieldName + "--" + "手机号码为空");
        }
        if (!BeeUtils.checkMobileNumber(mobile)) {
            throw new BusinessException(fieldName + "--" + "手机号输入错误，请重新输入");
        }
    }

    /**
     *
     ********************************************************* .<br>
     * [方法] matchPassword <br>
     * [描述] 验证密码是否是符合规范的要求 <br>
     * 验证密码输入格式：需由小写字母、数字同时组成，且长度为：6-20位 <br>
     * [参数] (对参数的描述) <br>
     * [返回] boolean <br>
     ********************************************************* .<br>
     */
    public static boolean matchPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{6,20}$");
    }

    /**
     *
     ********************************************************* .<br>
     * [方法] hideMobileNumber <br>
     * [描述] 隐藏手机号码关键信息(首3位+****+后4位)：136**1186 <br>
     * [参数] TODO(对参数的描述) <br>
     * [返回] String <br>
     ********************************************************* .<br>
     */
    public static String hideMobileNumber(String mobileNumber) throws BusinessException {
        if (!checkMobileNumber(mobileNumber)) {
            throw new BusinessException("手机号输入错误，请重新输入");
        }
        return mobileNumber.substring(0, 3) + "****" + mobileNumber.substring(7, 11);
    }

    /**
     *
     ********************************************************* .<br>
     * [方法] hideEmail <br>
     * [描述] 隐藏邮箱关键信息：隐藏格式：1.开始2位+****+此处一位+@及其后缀 如：17****4@qq.com<br>
     * [参数] TODO(对参数的描述) <br>
     * [返回] String <br>
     ********************************************************* .<br>
     */
    public static String hideEmail(String email) throws BusinessException {
        if (!checkEmail(email)) {
            throw new BusinessException("邮箱格式不正确");
        }
        if (email.indexOf("@") > 3) { // 说明@前多余三位：17***3@qq.com 格式展示
            return email.substring(0, 2) + "****" + email.substring(email.indexOf("@") - 1, email.length());
        } else {// 说明@前<=三位 ：全部展示
            return email;
        }
    }

    /**
     *
     ********************************************************* .<br>
     * [方法] checkMoney <br>
     * [描述] 验证金额格式是否正确：[正数+小数部位可有可无] <br>
     * [参数] TODO(对参数的描述) <br>
     * [返回] boolean <br>
     ********************************************************* .<br>
     */
    public static boolean checkMoney(Object value) {
        Pattern pattern = Pattern
                .compile("^(([1-9]{0}\\d{0,12})(\\.(\\d){1,4})?)|(0\\.0[1-9]{1,3})|(0\\.[1-9]{1,3}(\\d{1,3})?)$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(value.toString());
        return match.matches();
    }

    /**
     *
     ********************************************************* .<br>
     * [方法] checkPostCode <br>
     * [描述] 验证邮政编码：6位数字 <br>
     * [参数] TODO(对参数的描述) <br>
     * [返回] boolean <br>
     ********************************************************* .<br>
     */
    public static boolean checkPostCode(Object value) {
        Pattern pattern = Pattern.compile("^\\d{6}$"); // 判断邮编:6位数字
        Matcher match = pattern.matcher(value.toString());
        return match.matches();
    }

    // 判断是否为正数
    public static boolean isNumber(Object value) {
        Pattern pattern = Pattern.compile("^[1-9]\\d*$");
        Matcher match = pattern.matcher(value.toString());
        return match.matches();
    }
}
