package com.weilaios.iqxceqhnhubt.utils;

import org.apache.http.HttpStatus;

/**
 * 工具类
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public class Constants {
    public static final String REQUEST_MAPPING_PREFIX = "process";
    public static final String PROJECT_ID_VALUE = "PRXlQaVwMjS0CXEiLx";
    public static final String TOKEN = "TOKEN";

    public static final Integer HTTP_STATUS_OK = HttpStatus.SC_OK;
    public static final String SUCCESS = "1";

    public static final String PROCESS_KEY_PREFIX = "auto_";

    public static final String FAIL = "0";

    public static final String FLAG = "flag";

    public static final String CATEGORY = "CATEGORY";
    public static final String CATEGORY_MOBILE = "MOBILE";
    public static final String COLUMNS = "columns";
    public static final String UUID = "uuid";
    public static final String PAGINATION = "pagination";
    public static final String RESULT_DATA= "resultData";

    /**
    * 用户定义的实体间关系的类型
    */
    public static final String ONE_TO_ONE = "1";
    public static final String ONE_TO_MANY = "2";
    public static final String MANY_TO_ONE = "3";
    public static final String MANY_TO_MANY = "4";

    public static final Long   TIME_OUT = Long.valueOf(7*24*60*60);
    public static final String TOKEN_ERROR="-1";
    public static final String RESULT_SUCCESS = "1";
    public static final String RESULT_FAIL = "0";
    public static final String RESULT_ERROR = "2";
    //DM服务调用异常
    public static final String RESULT_FAIL_2 = "2";

    /**操作类型 01DDL 02DML*/
    public static final String DM_OPERATE_TYPE_DDL = "01";
    public static final String DM_OPERATE_TYPE_DML = "02";
    /**
     * 动作类型 0查询，1新增，2修改，3删除
     */
    public static final String DM_ACTION_TYPE_QUERY = "0";
    public static final String DM_ACTION_TYPE_INSERT = "1";
    public static final String DM_ACTION_TYPE_UPDATE = "2";
    public static final String DM_ACTION_TYPE_DELETE = "3";

    /**
     * DDL类型  1对列操作 2对表操作
     */
    public static final String DM_DDL_TYPE_COLUMN = "1";
    public static final String DM_DDL_TYPE_TABLE = "2";

    /**
     * 结构的一些约定
     **/
    public static final String DM_PROJECT_UUID = "project_uuid";
    public static final String DM_APPLICATION_UUID = "application_uuid";
    public static final String DM_MODEL_UUID = "model_uuid";
    public static final String DM_TABLE_UUID = "table_uuid";
    public static final String DM_ACTION_TYPE = "action_type";
    public static final String DM_OPERATE_TYPE = "operate_type";

    public static final String DM_COLUMNS = "columns";
    public static final String DM_TYPE = "type";
    public static final String DM_DDL = "ddl";
    public static final String DM_DML = "dml";

    public static final String DM_MODEL_NAME = "model_name";
    public static final String DM_MODEL_DESC = "model_desc";
    public static final String DM_SQL_KEYWORDS_INSERT = "insert";
    public static final String DM_SQL_KEYWORDS_UPDATE = "update";
    public static final String DM_SQL_KEYWORDS_SELECT = "select";
    public static final String DM_SQL_KEYWORDS_WHERE = "where";
    public static final String DM_SQL_KEYWORDS_QUERY_BUILDER = "query_builder";
    public static final String DM_SQL_KEYWORDS_ORDER_BY = "order_by";
    public static final String DM_SQL_KEYWORDS_CURRENT_PAGE = "current_page";
    public static final String DM_SQL_KEYWORDS_PAGE_SIZE = "page_size";
    public static final String DM_SQL_KEYWORDS_CONTENT = "content";
    public static final String DM_SQL_KEYWORDS_OR = "or";
    public static final String DM_SQL_KEYWORDS_LOOKUP = "look_up";
    public static final String DM_SQL_KEYWORDS_PAGINATION = "pagination";
    public static final String DM_SQL_KEYWORDS_RANGE = "range";
    public static final String DM_SQL_KEYWORDS_DISTINCT = "distinct";
    public static final String DM_SQL_KEYWORDS_TREE_STRUCTURE = "tree_structure";

    public static final String DM_RELATION = "relations";
    public static final String DM_RELATION_UUID = "relation_uuid";
    public static final String DM_RELATION_LVALUE = "lvalue";
    public static final String DM_RELATION_RVALUE = "rvalue";
    public static final String DM_CHARTS_CHART_TYPE = "chart_type";
    public static final String DM_CHARTS_DIMENSION = "dimension";
    public static final String DM_CHARTS_QUOTA = "quota";
    /**分页方式：1-不分页 2-按条数分页**/
    public static final String PAGE_MODE_1="1";
    /**分页方式：1-不分页 2-按条数分页**/
    public static final String PAGE_MODE_2="2";






    /**状态机类型****/
    public static final String TYPE_CODE_TASK = "Task";
    public static final String TYPE_CODE_CHOICE = "Choice";
    public static final String TYPE_CODE_MAP = "Map";
    public static final String TYPE_CODE_CALCULATE = "Calculate";
    public static final String TYPE_CODE_RETURN = "Return";
    public static final String TYPE_CODE_RESOURCE = "Resource";
    public static final String TYPE_CODE_DOWN_EXCEL_TEMPLATE = "DownExcelTemplate";
    public static final String TYPE_CODE_DOWN_EXCEL_DATA = "DownExcelData";
    public static final String TYPE_CODE_UPLOAD_EXCEL = "UploadExcel";
    public static final String TYPE_CODE_PRIVILEGE = "Privilege";
    public static final String TYPE_CODE_EXECUTESUB = "ExecuteSub";
    public static final String TYPE_CODE_MQTTPUBLIC = "MqttPublic";
    public static final String TYPE_CODE_BLOCKLY = "Blockly";

    /**flowable状态机类型****/
    public static final String TYPE_CODE_FLOWABLE_ENTITYADD = "entityAdd";
    public static final String TYPE_CODE_FLOWABLE_ENTITYQUERY = "entityQuery";
    public static final String TYPE_CODE_FLOWABLE_ENTITYDELETE = "entityDelete";
    public static final String TYPE_CODE_FLOWABLE_ENTITYUPDATE = "entityUpdate";
    public static final String TYPE_CODE_FLOWABLE_RELATIONADD = "relationAdd";
    public static final String TYPE_CODE_FLOWABLE_RELATIONUPDATE = "relationUpdate";
    public static final String TYPE_CODE_FLOWABLE_RELATIONDELETE = "relationDelete";
    public static final String TYPE_CODE_FLOWABLE_CHOICE = "choice";
    public static final String TYPE_CODE_FLOWABLE_MAP = "map";
    public static final String TYPE_CODE_FLOWABLE_CALCULATE = "calculate";
    public static final String TYPE_CODE_FLOWABLE_RETURN = "return";
    public static final String TYPE_CODE_FLOWABLE_RESOURCE = "resource";
    public static final String TYPE_CODE_FLOWABLE_DOWN_EXCEL_TEMPLATE = "downExcelTemplate";
    public static final String TYPE_CODE_FLOWABLE_DOWN_EXCEL_DATA = "downExcelData";
    public static final String TYPE_CODE_FLOWABLE_DOWN_FILE = "downloadFile";
    public static final String TYPE_CODE_FLOWABLE_UPLOAD_EXCEL = "uploadExcel";
    public static final String TYPE_CODE_FLOWABLE_PRIVILEGE = "privilege";
    public static final String TYPE_CODE_FLOWABLE_EXECUTESUB = "executeSub";
    public static final String TYPE_CODE_FLOWABLE_MQTTPUBLIC = "mqttPublic";
    public static final String TYPE_CODE_FLOWABLE_BLOCKLY = "blockly";
    public static final String TYPE_CODE_FLOWABLE_PRODUCEJSON = "produceJson";
    public static final String TYPE_CODE_FLOWABLE_ANALYSISJSON = "analysisJson";
    public static final String TYPE_CODE_FLOWABLE_MD5ENCRYPT = "md5Encrypt";
    public static final String TYPE_CODE_FLOWABLE_DECRYPT = "decrypt";
    public static final String TYPE_CODE_FLOWABLE_SENDAPPMESSAGE = "sendAppMessage";
    public static final String TYPE_CODE_FLOWABLE_SENDMAIL = "sendMail";
    /** 流程操作标识：变量赋值 *****/
    public static final String TYPE_CODE_FLOWABLE_SETVARIABLE = "setVariable";
    /** 流程操作标识：http请求 *****/
    public static final String TYPE_CODE_FLOWABLE_DOHTTP = "doHttp";

    /**
    * 流程节点唯一标识：调用业务流
    */
    public static final String FLOW_IDENTIFIER_EXECTUE_BUSINESS_FLOW = "ExectueSubFlow";

    public static final String FLOWABLE_SYSTEM_DATA_KEY = "flowableSystemInitData";
    public static final String FLOWABLE_SYSTEM_FORM_DATA_KEY = "flowableSystemFormData";
    /**流程用户任务节点相关数据存储key****/
    public static final String FLOWABLE_SYSTEM_TASK_DATA_KEY = "flowableSystemTaskData";
    public static final String TYPE_CODE_FLOWABLE_SYSTEM_DOWN_FILE_DATA_KEY = "flowableSystemDownFileData";


    /**Choices 支持的比较运算符*****/
    public static final String MATCH_TYPE_OR ="Or";
    public static final String MATCH_TYPE_AND ="And";
    public static final String MATCH_TYPE_NOT ="Not";

    public static final String MATCH_TYPE_BOOLEAN_EQ ="BooleanEq";

    /**数值操作符**/
    public static final String MATCH_TYPE_NUMERIC_EQ ="NumericEq";
    public static final String MATCH_TYPE_NUMERIC_GT ="NumericGt";
    public static final String MATCH_TYPE_NUMERIC_GE ="NumericGe";
    public static final String MATCH_TYPE_NUMERIC_LT ="NumericLt";
    public static final String MATCH_TYPE_NUMERIC_LE ="NumericLe";
    public static final String MATCH_TYPE_NUMERIC_NE ="NumericNe";
    /**金额操作符**/
    public static final String MATCH_TYPE_BIGDECIMAL_EQ ="BigDecimalEq";
    public static final String MATCH_TYPE_BIGDECIMAL_GT ="BigDecimalGt";
    public static final String MATCH_TYPE_BIGDECIMAL_GE ="BigDecimalGe";
    public static final String MATCH_TYPE_BIGDECIMAL_LT ="BigDecimalLt";
    public static final String MATCH_TYPE_BIGDECIMAL_LE ="BigDecimalLe";
    public static final String MATCH_TYPE_BIGDECIMAL_NE ="BigDecimalNe";
    /**字符串操作符**/
    public static final String MATCH_TYPE_STRING_EQ ="StringEq";
    public static final String MATCH_TYPE_STRING_GT ="StringGt";
    public static final String MATCH_TYPE_STRING_GE ="StringGe";
    public static final String MATCH_TYPE_STRING_LT ="StringLt";
    public static final String MATCH_TYPE_STRING_LE ="StringLe";
    public static final String MATCH_TYPE_STRING_NE ="StringNe";
    public static final String MATCH_TYPE_STRING_CONTAIN ="StringContain"; //包含
    public static final String MATCH_TYPE_STRING_NOT_CONTAIN ="StringNotContain"; //不包含

    public static final String MATCH_TYPE_TIMESTAMP_EQ ="TimestampEq";
    public static final String MATCH_TYPE_TIMESTAMP_GT ="TimestampGt";
    public static final String MATCH_TYPE_TIMESTAMP_GE ="TimestampGe";
    public static final String MATCH_TYPE_TIMESTAMP_LT ="TimestampLt";
    public static final String MATCH_TYPE_TIMESTAMP_LE ="TimestampLe";
    public static final String MATCH_TYPE_TIMESTAMP_NE ="TimestampNe";

    public static final String MATCH_TYPE_NULL_EQ ="NullEq";
    public static final String MATCH_TYPE_NOT_NULL_EQ ="NotNullEq";

    /**比较类型： 是/等于 ***/
    public static final String MATCH_TYPE_EQ_11 = "11";
    /**比较类型： 不是/不等于***/
    public static final String MATCH_TYPE_NE_10 = "10";
    /**比较类型： 大于等于 ***/
    public static final String MATCH_TYPE_GE_111 = "111";
    /**比较类型： 大于 ***/
    public static final String MATCH_TYPE_GT_110 = "110";
    /**比较类型： 小于等于 ***/
    public static final String MATCH_TYPE_LE_101 = "101";
    /**比较类型： 小于 ***/
    public static final String MATCH_TYPE_LT_100 = "100";
    /**比较类型： 为空 ***/
    public static final String MATCH_TYPE_NULL_201 = "201";
    /**比较类型： 不为空 ***/
    public static final String MATCH_TYPE_NOT_NULL_200 = "200";
    /**比较类型： 开始以 ***/
    public static final String MATCH_TYPE_START_WITH_300 = "300";
    /**比较类型： 结束以 ***/
    public static final String MATCH_TYPE_END_WITH_301 = "301";
    /**比较类型： 包含 ***/
    public static final String MATCH_TYPE_CONTAIN_302 = "302";
    /**比较类型： 不包含 ***/
    public static final String MATCH_TYPE_NOT_CONTAIN_303 = "303";



    /**正则校验****/
    public static final String MATCH_TYPE_CHECK_TELEPHONE ="CheckTelephone";
    public static final String MATCH_TYPE_CHECK_EMAIL ="CheckEmail";
    public static final String MATCH_TYPE_CHECK_BANK_CARD_NO ="CheckBankCardNo";
    public static final String MATCH_TYPE_CHECK_MONEY ="CheckMoney";

    /**
     * 参数来源类型：
     * 0传入的参数：页面传入的参数或者节点处理的返回值数据
     * 1 循环的参数体--从循环体选择的数据
     * 2 数据字典-选项集
     * 3 固定值，即直接获取到的是值，无须再处理
     * 4-系统数据--用户登录存储到redis里的数据
     * 5-实体
     * 6-关系
     * 7-自定义全局变量--自定义存储到redis里的信息
     * 8-单一选项
     * 9-blockly
     */
    public static final String INPUT_PARAM_SOURCE_TYPE_0 ="0";
    public static final String INPUT_PARAM_SOURCE_TYPE_1 ="1";
    public static final String INPUT_PARAM_SOURCE_TYPE_2 ="2";
    public static final String INPUT_PARAM_SOURCE_TYPE_3 ="3";
    public static final String INPUT_PARAM_SOURCE_TYPE_4 ="4";
    public static final String INPUT_PARAM_SOURCE_TYPE_5 ="5";
    public static final String INPUT_PARAM_SOURCE_TYPE_6 ="6";
    public static final String INPUT_PARAM_SOURCE_TYPE_7 ="7";
    public static final String INPUT_PARAM_SOURCE_TYPE_8 ="8";
    public static final String INPUT_PARAM_SOURCE_TYPE_9 ="9";

    /**
     * 循环的参数体 key值，提前约定好
     */
    public static final String CIRCULATION_CONTENT_KEY ="map_content_key";

    public static final String CAL_FORMULA_TYPE_CHOICE ="choice";
    public static final String CAL_FORMULA_TYPE_SYMBOL ="symbol";
    public static final String CAL_FORMULA_TYPE_INPUT ="input";
    public static final String CAL_FORMULA_TYPE_SUM ="sum";
    public static final String CAL_FORMULA_TYPE_AVG ="avg";


    //用户所属平台标识
    public static final String PLATEFORM_FLAG = "plateformFlag";
    //平台标识：主站--大平台、搭建平台
    public static final String PLATEFORM_FLAG_MAIN = "1";
    //平台标识：用户端
    public static final String PLATEFORM_FLAG_CLIENT = "2";
    //平台标识：3-搭建端模拟用户
    public static final String PLATEFORM_FLAG_MODEL_USER = "3";



    //wlos client 集群参数
    public static final String WLOS_CLUSTER_KEY = "wlosServer";

    /**
     * 数据类型
     */
    public static final String DM_DATA_TYPE_FILE ="File";
    public static final String DM_DATA_TYPE_IMAGE ="Image";
    public static final String DM_DATA_TYPE_OPTION ="Option";
    public static final String DM_DATA_TYPE_STRING ="String";
    public static final String DM_DATA_TYPE_INTEGER ="Integer";
    public static final String DM_DATA_TYPE_AMOUNT ="Amount";
    public static final String DM_DATA_TYPE_DATE ="Date";
    public static final String DM_DATA_TYPE_DATE_TIME ="Datetime";
    public static final String DM_DATA_TYPE_TIME ="Time";
    public static final String DM_DATA_TYPE_DECIMAL ="Decimal";
    public static final String DM_DATA_TYPE_DOUBLE ="Double";
    public static final String DM_DATA_TYPE_ADDRESS ="Address";
    public static final String DM_DATA_TYPE_BOOLEAN ="Boolean";

    /**消息接收人群组类型：1-审批人元语  2-审批用户组 3-部门 4-成员 **/
    public static final String RECEIVE_GROUP_TYPE_1 ="1";
    public static final String RECEIVE_GROUP_TYPE_2 ="2";
    public static final String RECEIVE_GROUP_TYPE_3 ="3";
    public static final String RECEIVE_GROUP_TYPE_4 ="4";

    /**访问来源：sourceFlag  1-搭建平台 2-用户端*****/
    public static final String SOURCE_FLAG_1 ="1";
    /**访问来源：sourceFlag  1-搭建平台 2-用户端*****/
    public static final String SOURCE_FLAG_2 ="2";

    /** 用户审批结果标识 *****/
    public static final String APPROVAL_RESULT_KEY ="approvalResult";
    /** 任务审批人***/
    public static final String APPROVAL_TASK_ASSIGNEE_NAME_KEY ="taskAssigneeName";
    /** 用户审批结果标识--驳回 *****/
    public static final String APPROVAL_RESULT_REJECT ="reject";
    /** 用户审批结果标识--同意 *****/
    public static final String APPROVAL_RESULT_AGREE ="agree";

    /** 用户审批结果--驳回数量 *****/
    public static final String APPROVAL_REJECT_COUNT ="rejectCount";
    /** 用户审批结果标识--同意数量 *****/
    public static final String APPROVAL_AGREE_COUNT ="agreeCount";

    /** 用户签批-审批方式：1-或签  2-会签 *****/
    public static final String APPROVAL_TYPE_1 ="1";
    /** 用户签批-审批方式：1-或签  2-会签 *****/
    public static final String APPROVAL_TYPE_2 ="2";

    /** 会签无法判断审批结果，默认审批结果为：1-同意 2-拒绝 *****/
    public static final String APPROVAL_DEFAULT_RESULT_1 ="1";
    /** 会签无法判断审批结果，默认审批结果为：1-同意 2-拒绝 *****/
    public static final String APPROVAL_DEFAULT_RESULT_2 ="2";

    /** 会签判断方式：1-比列 2-一票否决 *****/
    public static final String APPROVAL_JUDGE_RULE_1 ="1";
    /** 会签判断方式：1-比列 2-一票否决 *****/
    public static final String APPROVAL_JUDGE_RULE_2 ="2";

    /** 用户审批状态：1-处理中 2-通过 3-驳回 4-审批结束自动完成 5-撤销 *****/
    public static final String USER_APPROVAL_STATUS_1 ="1";
    /** 用户审批状态：1-处理中 2-通过 3-驳回 4-审批结束自动完成 5-撤销 *****/
    public static final String USER_APPROVAL_STATUS_2 ="2";
    /** 用户审批状态：1-处理中 2-通过 3-驳回 4-审批结束自动完成 5-撤销 *****/
    public static final String USER_APPROVAL_STATUS_3 ="3";
    /** 用户审批状态：1-处理中 2-通过 3-驳回 4-审批结束自动完成 5-撤销 *****/
    public static final String USER_APPROVAL_STATUS_4 ="4";
    /** 用户审批状态：1-处理中 2-通过 3-驳回 4-审批结束自动完成 5-撤销 *****/
    public static final String USER_APPROVAL_STATUS_5 ="5";

    /** 流程已执行过的最后一个用户任务的节点审批结果：true-通过 false-未通过 *****/
    public static final String LAST_TASK_APPROVAL_RESULT_KEY ="lastTaskApprovalResult";


    /***参数排序规则：1 -不排序 2-字母正序  3-字母倒叙***/
    public static final String FLOW_SORT_RULE_1 ="1";
    /***参数排序规则：1 -不排序 2-字母正序  3-字母倒叙***/
    public static final String FLOW_SORT_RULE_2 ="2";
    /***参数排序规则：1 -不排序 2-字母正序  3-字母倒叙***/
    public static final String FLOW_SORT_RULE_3 ="3";

    /***加密方式：1-md5加密  2-hmacsha256 3-hmacsha1***/
    public static final String ENCRYPT_MODE_1 ="1";
    /***加密方式：1-md5加密  2-hmacsha256 3-hmacsha1***/
    public static final String ENCRYPT_MODE_2 ="2";
    /***加密方式：1-md5加密  2-hmacsha256 3-hmacsha1***/
    public static final String ENCRYPT_MODE_3 ="3";
    /***加密方式：4-base64***/
    public static final String ENCRYPT_MODE_4 ="4";

    public static final String KEY_MAC_SHA1 = "HmacSHA1";
    public static final String KEY_MAC_SHA256 = "HmacSHA256";
    public static final String KEY_DES = "DES";
    public static final String KEY_AES = "AES";
    public static final String KEY_3DES = "DESede";
    public static final String KEY_RSA = "RSA";
    public static final String KEY_ECC = "EC";

    /** 超级管理员标识 1-超级管理员  2-普通员工  0-个人用户 */
    public static final String USER_IS_ADMIN_1 = "1";

    /** 存储文件导出标识key */
    public static final String DOWN_FILE_FLAG_KEY = "system_flow_down_file_flag";
    public static final String DOWN_FILE_KEY_PATH = "system_flow_down_file_key_path";
    public static final String SYSTEM_RETURN_KEY_NAME = "system_return_key";
    public static final String SYSTEM_RETURN_TOKEN = "system_returnToken";
    /** 存储文件导出标识key对应的value 1-是文件下载 0-非文件下载*/
    public static final String DOWN_FILE_FLAG_VALUE_1 = "1";
    /** 存储文件导出标识key对应的value 1-是文件下载 0-非文件下载*/
    public static final String DOWN_FILE_FLAG_VALUE_0 = "0";

    /** 文件下载类型 0-数据文件节点下载 1-文件下载节点下载*/
    public static final String DOWN_FILE_FROM_TYPE_EXCEL = "0";
    public static final String DOWN_FILE_FROM_TYPE_FILE = "1";
    /** 导出的数据文件类型：1-xls 2-csv 3-json **/
    public static final String DOWN_DATA_FILE_TYPE_XLS = "1";
    public static final String DOWN_DATA_FILE_TYPE_CSV = "2";
    public static final String DOWN_DATA_FILE_TYPE_JSON = "3";


    /** mqtt--设备选择类型：1-指定设备 2-动态设备 3-空设备 */
    public static final String MQTT_DEVICE_CHOOSE_TYPE_1 = "1";
    /** mqtt--设备选择类型：1-指定设备 2-动态设备 3-空设备 */
    public static final String MQTT_DEVICE_CHOOSE_TYPE_2 = "2";
    /** mqtt--设备选择类型：1-指定设备 2-动态设备 3-空设备 */
    public static final String MQTT_DEVICE_CHOOSE_TYPE_3 = "3";


    /** flow xml标签元素 */
    public static final String FLOW_ELEMENT_PARAMS_NAME = "paramsName";
    public static final String FLOW_ELEMENT_PARAMS_VALUE = "paramsValue";
    public static final String FLOW_ELEMENT_BIND_KEY = "bindKey";
    public static final String FLOW_ELEMENT_BIND_VALUE = "bindValue";



    public static final String JWT_FLAG = "flag";

    /** 系统环境变量的key */
    public static final String GLOBAL_VARIABLE_KEY_PROJECT_UUID = "projectUuid";


    /** 字符集 */
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String CHARSET_GBK = "GBK";


    /** content type */
    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE_APPLICATION_FORM_DATA = "application/x-www-form-urlencoded";

    /** 流程执行结果key标识后缀 */
    public static final String FLOW_EXECCODE = "execCode";
    /** 流程执行结果错误信息key标识后缀 */
    public static final String FLOW_EXECMSG = "execMsg";
    /** 流程执行结果返回值key标识后缀 */
    public static final String FLOW_RETURN_FLAG = "_return";

    /** event事件 */
    public static final String FLOW_EVENT_BASE_PAEKAGE = "com.weilaios.event.type";
    public static final String FLOW_EVENT_TYPE = "com.weilaios.event.type.FLOW_EXECUTION";
    public static final String FLOW_EVENT_EXCEPTION_TYPE = "com.weilaios.exception.type.STACK_OVERFLOW";
    public static final String FLOW_EVENT_TOPIC = "FlowExecutionTopic";
    public static final String FLOW_EVENT_SOURCE = "WOS_FD";

    //流程事件状态
    public static final String FLOW_EVENT_STATUS_SUCCESS = "success";
    public static final String FLOW_EVENT_STATUS_ERROR = "error";
    public static final String FLOW_EVENT_STATUS_EXCEPTION = "exception";

    /** event事件：流程执行开始-流程开始时 */
    public static final String FLOW_EVENT_FD_FLOW_START = "FD_FLOW_START";
    /** event事件：流程过程执行开始-过程开始时 */
    public static final String FLOW_EVENT_FD_PROC_START = "FD_PROC_START";
    /** event事件：流程过程执行结束-过程结束时 */
    public static final String FLOW_EVENT_FD_PROC_END = "FD_PROC_END";
    /** event事件：流程过程执行错误，业务逻辑错误-过程错误结束时 */
    public static final String FLOW_EVENT_FD_PROC_ERROR = "FD_PROC_ERROR";
    /** event事件：流程过程执行异常-过程异常结束时 */
    public static final String FLOW_EVENT_FD_PROC_EXCEPT = "FD_PROC_EXCEPT";
    /** event事件：流程执行结束-流程结束时 */
    public static final String FLOW_EVENT_FD_FLOW_END = "FD_FLOW_END";
    /** event事件：流程执行错误，业务逻辑错误-流程错误结束时 */
    public static final String FLOW_EVENT_FD_FLOW_ERROR = "FD_FLOW_ERROR";
    /** event事件：流程执行异常-流程异常结束时 */
    public static final String FLOW_EVENT_FD_FLOW_EXCEPT = "FD_FLOW_EXCEPT";

    public static final String FLOW_START_TIME_KEY = "_startTime";
    public static final String FLOW_END_TIME_KEY = "_endTime";

    /**
    * 是否已经删除 0：未删除 1：删除
    */
    public static final String IS_DEL_0 = "0";
    public static final String IS_DEL_1 = "1";
    /**
    * 启停状态 1：启动 0：停止
    */
    public static final String STATUS_ON = "1";
    public static final String STATUS_DOWN = "0";
    /***
    * cron 时间表达式,频率
    */
    public static final String XXL_HAS_CRON_DAY="1";
    public static final String XXL_HAS_CRON_WEEK="2";
    public static final String XXL_HAS_CRON_MONTH="3";
    public static final String XXL_HAS_CRON_YEAR="4";
    public static final String XXL_HAS_CRON_HOUR="5";
    public static final String XXL_HAS_CRON_MINUTE="6";
    public static final String XXL_HAS_CRON_SECOND="7";
    public static final String XXL_HAS_CRON_SPECIAL="8";

    /**
    * code
    */
    public static final String CODE = "code";
    public static final String MSG = "msg";

    /**
    * IS_SUB_0: 不是辅助定时器 IS_SUB_1：是辅助定时器
    */
    public static final int IS_SUB_0 = 0;
    public static final int IS_SUB_1 = 1;
}
