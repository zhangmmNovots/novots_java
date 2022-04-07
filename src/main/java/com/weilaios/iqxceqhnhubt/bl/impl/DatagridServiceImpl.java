package com.weilaios.iqxceqhnhubt.bl.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharPool;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.weilaios.iqxceqhnhubt.bl.DatagridService;
import com.weilaios.iqxceqhnhubt.bl.ParamHandleService;
import com.weilaios.iqxceqhnhubt.da.mapper.*;
import com.weilaios.iqxceqhnhubt.da.service.*;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import com.weilaios.iqxceqhnhubt.model.*;
import com.weilaios.iqxceqhnhubt.model.vo.RelationConditionVO;
import com.weilaios.iqxceqhnhubt.model.vo.WlosLookupVO;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.utils.DataOperateConstants;
import com.weilaios.iqxceqhnhubt.utils.WlosPagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DatagridServiceImpl implements DatagridService {

    private transient Logger log = LoggerFactory.getLogger(getClass());

    

	/***
	 * Model  use static constantsPool
	 */
	private static final HashMap<String, String> realEnNames = new HashMap<String, String>() {{
		 put("uvsv_tse", "uvsv_tse");
		 put("uvsv_s_user", "uvsv_s_user");
		 put("uvsv_s_employee_test", "uvsv_s_employee_test");
		 put("uvsv_commodity", "uvsv_commodity");
		 put("uvsv_background_user", "uvsv_background_user");
		 put("uvsv_s_", "uvsv_s_");
		 put("uvsv_application_revenu", "uvsv_application_revenu");
		 put("uvsv_project", "uvsv_project");
		 put("uvsv_application", "uvsv_application");
		 put("uvsv_entity_uwwj", "uvsv_entity_uwwj");
		 put("uvsv_role", "uvsv_role");
		 put("uvsv_jurisdiction", "uvsv_jurisdiction");
		 put("uvsv_user_table", "uvsv_user_table");
		 put("null", "null");
		 put("uvsv_contract", "uvsv_contract");
		 put("uvsv_entity_vept", "uvsv_entity_vept");
		 put("uvsv_boig", "uvsv_boig");
		 put("uvsv_mzvr", "uvsv_mzvr");
	}};
    

	/***
	 * ModelPrimaryKey  use static constantsPool
	 */
	private static final HashMap<String, String> primarykeys = new HashMap<String, String>() {{
		 put("uvsv_tse", "uvsv_tse_id");
		 put("uvsv_s_user", "uvsv_s_user_id");
		 put("uvsv_s_employee_test", "uvsv_s_employee_test_id");
		 put("uvsv_commodity", "uvsv_commodity_id");
		 put("uvsv_background_user", "uvsv_background_user_id");
		 put("uvsv_s_", "uvsv_s__id");
		 put("uvsv_application_revenu", "uvsv_application_revenu_id");
		 put("uvsv_project", "uvsv_project_id");
		 put("uvsv_application", "uvsv_application_id");
		 put("uvsv_entity_uwwj", "uvsv_entity_uwwj_id");
		 put("uvsv_role", "uvsv_role_id");
		 put("uvsv_jurisdiction", "uvsv_jurisdiction_id");
		 put("uvsv_user_table", "uvsv_user_table_id");
		 put("null", "null_id");
		 put("uvsv_contract", "uvsv_contract_id");
		 put("uvsv_entity_vept", "uvsv_entity_vept_id");
		 put("uvsv_boig", "uvsv_boig_id");
		 put("uvsv_mzvr", "uvsv_mzvr_id");
	}};



    @Autowired
    private ParamHandleService paramHandleService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UvsvTseService uvsvTseService;
    @Autowired
    private UvsvSUserService uvsvSUserService;
    @Autowired
    private UvsvSEmployeeTestService uvsvSEmployeeTestService;
    @Autowired
    private UvsvCommodityService uvsvCommodityService;
    @Autowired
    private UvsvBackgroundUserService uvsvBackgroundUserService;
    @Autowired
    private UvsvSService uvsvSService;
    @Autowired
    private UvsvApplicationRevenuService uvsvApplicationRevenuService;
    @Autowired
    private UvsvProjectService uvsvProjectService;
    @Autowired
    private UvsvApplicationService uvsvApplicationService;
    @Autowired
    private UvsvEntityUwwjService uvsvEntityUwwjService;
    @Autowired
    private UvsvRoleService uvsvRoleService;
    @Autowired
    private UvsvJurisdictionService uvsvJurisdictionService;
    @Autowired
    private UvsvUserTableService uvsvUserTableService;
    @Autowired
    private UvsvContractService uvsvContractService;
    @Autowired
    private UvsvEntityVeptService uvsvEntityVeptService;
    @Autowired
    private UvsvBoigService uvsvBoigService;
    @Autowired
    private UvsvMzvrService uvsvMzvrService;


    @Override
    public void datagridSave(String data) throws BusinessException {
        //校验参数是否为空
        if (StringUtils.isEmpty(data)) {
            throw new BusinessException("操作异常：请传入保存内容");
        }

        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        DocumentContext dataJsonDocument = JsonPath.using(conf).parse(data);

        //获取修改的表
        String modelUuid = dataJsonDocument.read("$.model_uuid");
        if (StringUtils.isBlank(modelUuid)) {
            throw new BusinessException("操作异常：请传入操作实体");
        }

        //新增数据
        List<Map> contentList = null;
        try {
            contentList = dataJsonDocument.read("$.insertContent.*");
        } catch (Exception e) {
        }
        //判断是否存在新增数据的内容
        if (null == contentList || contentList.size() < 1) {
            throw new BusinessException("操作异常：请传入新增数据的内容");
        }

        //获取项目编号，当前操作人编号
        String operator_uuid = null;
        String project_uuid = null;
        Map<String, String> relationObject = null;
        try {
            operator_uuid = dataJsonDocument.read("$.system.operator_uuid");
            project_uuid = dataJsonDocument.read("$.project_uuid");
            relationObject = dataJsonDocument.read("$.lookup_relation");
        } catch (Exception e) {
        }

        //递归处理表单及子表单的新增操作
        handleRecursionSave(contentList, null, null, null, modelUuid, project_uuid, relationObject, operator_uuid, conf);

    }

    /**
     * 递归处理表单及子表单的嵌套新增
     *
     * @param contentList   --子表新增内容
     * @param mainModelUuid --主表uuid
     * @param mainDataJson  --主表数据json
     * @param modelUuid     --子表uuid
     * @param projectUuid   --项目uuid
     * @param operatorUuid  --当前操作人uuid
     * @param conf          --jsonpath 解析conf
     * @throws BusinessException
     */
    public void handleRecursionSave(List<Map> contentList, String mainModelUuid, JSONObject mainDataJson, String lookupUuid, String modelUuid, String projectUuid, Map<String, String> relationObject, String operatorUuid, Configuration conf) throws BusinessException {
        if (null != contentList && contentList.size() > 0) {
            for (int i = 0; i < contentList.size(); i++) {
                //获取新增的数据
                JSONObject signalObject = new JSONObject(contentList.get(i));
                //获取添加的主表数据
                JSONObject saveDataJson = handleFormDataAndSubInsert(mainModelUuid, mainDataJson, lookupUuid, modelUuid, signalObject, operatorUuid, projectUuid, relationObject);
                //处理子表单新增数据
                JSONArray subFormArray = signalObject.getJSONArray("sub_form");
                if (null == subFormArray || subFormArray.size() < 1) {
                    continue;
                }
                for (int j = 0; j < subFormArray.size(); j++) {
                    //获取子表单的实体
                    DocumentContext subDataJsonDocument = JsonPath.using(conf).parse(subFormArray.get(j));
                    //获取子表单的content
                    String subModelUuid = subDataJsonDocument.read("$.model_uuid");
                    String subLookupUuid = subDataJsonDocument.read("$.lookup_uuid");
                    Map<String, String> subRelationObject = subDataJsonDocument.read("$.lookup_relation");

                    //如果子表表名为空，则结束本次循环
                    if (StringUtils.isBlank(subModelUuid)) {
                        continue;
                    }
                    //新增数据
                    List<Map> subContentList = new ArrayList<>();
                    try {
                        subContentList = subDataJsonDocument.read("$.insertContent.*");
                    } catch (Exception e) {

                    }
                    //如果子表添加的数据为空，则结束本次循环
                    if (null == subContentList || subContentList.size() < 1) {
                        continue;
                    }
                    //递归处理子表单里的子表单数据
                    handleRecursionSave(subContentList, modelUuid, saveDataJson, subLookupUuid, subModelUuid, projectUuid, subRelationObject, operatorUuid, conf);
                }
            }
        }
    }

    /**
     * 表单添加数据及创建主子表建的关系
     *
     * @param mainModelUuid
     * @param mainData
     * @param subModelUuid
     * @param subSignalJson
     * @param operatorUuid
     * @param projectUuid
     * @return
     * @throws BusinessException
     */
    public JSONObject handleFormDataAndSubInsert(String mainModelUuid, JSONObject mainData, String lookupUuid, String subModelUuid, JSONObject subSignalJson, String operatorUuid, String projectUuid, Map<String, String> relationObject) throws BusinessException {
        //处理新增的字段，值等
        //处理lookup关
        if (StringUtils.isNotBlank(mainModelUuid) && null != mainData) {
            String relationKey = relationObject.get("relationKey");
            subSignalJson.put(relationKey, mainModelUuid);
        }
        JSONObject dataInsert = handleSaveInsert(subSignalJson, subModelUuid);
        //判断主表是否存在：如果主表存在，则新增数据，创建关系 如果主表不存在，则只新增数据
        //解析新增的数据，并组装给dm的json数据
        //添加子表单数据
        this.handleDmSave(subModelUuid, dataInsert, operatorUuid, projectUuid);
        return dataInsert;
    }

    /**
     * 与dm交互，保存数据
     *
     * @param modelUuid
     * @param insertData
     * @param operatorUuid
     * @param projectUuid
     * @return
     * @throws BusinessException
     */
    public Map<String, Object> handleDmSave(String modelUuid, JSONObject insertData, String operatorUuid, String projectUuid) throws BusinessException {
        //组装给data-model的数据
        //新增数据
        JSONObject mm = new JSONObject();
        mm.put("insert", insertData);
        JSONObject dmMap = new JSONObject();
        dmMap.put("operator_uuid", !StringUtils.isEmpty(operatorUuid) ? operatorUuid : "");
        dmMap.put(Constants.DM_PROJECT_UUID, projectUuid);
        dmMap.put("operate_type", Constants.DM_OPERATE_TYPE_DML);
        dmMap.put("action_type", Constants.DM_ACTION_TYPE_INSERT);
        dmMap.put("model_uuid", modelUuid);
        dmMap.put("dml", mm);
        log.info("=========dm params json===" + JSON.toJSONString(dmMap, SerializerFeature.WriteMapNullValue));
        List<Map<String, Object>> resultList = new ArrayList<>();
        resultList.add(insertData);
        insertDataByMysql(modelUuid, resultList);
        return insertData;

    }

    /**
     * 处理新增的字段，值等
     *
     * @param signalObject
     * @return
     * @throws BusinessException
     */
    public JSONObject handleSaveInsert(JSONObject signalObject, String modelEnName) throws BusinessException {


        EntityField wlosEntityField1 = new EntityField();
        wlosEntityField1.setEntityEnName(modelEnName);
        List<EntityField> wlosEntityFields1 = entityFieldMapper.selectList(new QueryWrapper<>(wlosEntityField1));

        Entity entityQuery = new Entity();
        entityQuery.setEnName(modelEnName);
        Entity entity = entityMapper.selectOne(new QueryWrapper<>(entityQuery));
        JSONArray columnArray = signalObject.getJSONArray("insert");
        JSONObject insert = new JSONObject();
        //单条数据是否有不为空的值
        boolean nullFlag = false;
        for (int j = 0; j < columnArray.size(); j++) {
            //字段名称
            String table_column = columnArray.getJSONObject(j).getString("table_column");
            if (StringUtils.isEmpty(table_column)) {
                continue;
            }
            //字段值
            Object value = columnArray.getJSONObject(j).get("value");
            String paramSourceType = String.valueOf(columnArray.getJSONObject(j).get("paramSourceType"));

            //判断单条数据是否有不为空的值：如果全部为空，则过滤掉该条数据
            if (null != value && "" != value) {

                nullFlag = true;
            }
            //    insert.put(com.baomidou.mybatisplus.core.toolkit.StringUtils.underlineToCamel(table_column), null != value && "" != value ? value : "");
            insert.put(table_column, null != value && "" != value ? value : "");
        }


        JSONObject reInsert = new JSONObject();
        for (EntityField entityField : wlosEntityFields1) {
            try {
                fillEntityData(entityField, entity, insert, reInsert);
            } catch (ParseException e) {
                log.error("新增数据转换异常：{}", e);
                throw new BusinessException("新增数据转换异常");
            } catch (ScriptException e) {
                log.error("新增数据转换异常：{}", e);
                throw new BusinessException("新增数据转换异常");
            }
        }
        // 存储实体中配置的lookup字段
        List<String> lookupFields = wlosEntityFields1.stream().filter(item -> "Lookup".equals(item.getType())).map(EntityField::getEnName).collect(Collectors.toList());
        // 判断是否有lookup字段的写入,存储lookup字段
        List<String> relationKeyList = new ArrayList<>();
        insert.keySet().forEach(key -> {
            if (lookupFields.contains(key)) {
                relationKeyList.add(key);
            }
        });
        if (relationKeyList.size() > 0) {
            relationKeyList.forEach(item -> {
                // 查询关系详情
                LambdaQueryWrapper<EntityRelation> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
                objectLambdaQueryWrapper.eq(EntityRelation::getRelationKey, item);
                EntityRelation wlosEntityRelation = entityRelationEntityMapper.selectOne(objectLambdaQueryWrapper);
                if (ObjectUtils.isNotEmpty(wlosEntityRelation)) {
                    List<Map<String, Object>> insertData = new ArrayList<>();
                    insertData.add(reInsert);
                    insertBatch(modelEnName, wlosEntityRelation, insertData);
                }
            });
        }
        return reInsert;

    }

    @Override
    public void datagridUpdate(String data) throws BusinessException {
        //校验参数是否为空
        if (StringUtils.isEmpty(data)) {
            throw new BusinessException("操作异常：请传入查询内容");
        }

        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        DocumentContext dataJsonDocument = JsonPath.using(conf).parse(data);

        //获取修改的表
        String modelUuid = dataJsonDocument.read("$.model_uuid");

        //校验参数是否为空
        if (StringUtils.isEmpty(modelUuid)) {
            throw new BusinessException("操作异常：请传入修改数据的实体");
        }

        //获取新增或者删除或者编辑的json
        List<Map> insertContentList = null;
        List<Map> deleteContentList = null;
        List<Map> updateContentList = null;
        try {
            insertContentList = dataJsonDocument.read("$.insertContent.*");
        } catch (Exception e) {
        }

        try {
            deleteContentList = dataJsonDocument.read("$.delete.*");
        } catch (Exception e) {
        }

        try {
            updateContentList = dataJsonDocument.read("$.updateContent.*");
        } catch (Exception e) {
        }

        //获取项目编号，当前登录用户编号，token
        String operatorUuid = null;
        String projectUuid = null;
        Map<String, String> relationLookup = null;
        String token = null;
        try {
            token = dataJsonDocument.read("$.system.token");
            operatorUuid = dataJsonDocument.read("$.system.operator_uuid");
            projectUuid = dataJsonDocument.read("$.project_uuid");
            relationLookup = dataJsonDocument.read("$.lookup_relation");
        } catch (Exception e) {
        }
        handleRecursionUpdate(insertContentList, deleteContentList, updateContentList, null, null, null, modelUuid, projectUuid, relationLookup, operatorUuid, token, conf);
    }

    public void handleRecursionUpdate(List<Map> insertContentList, List<Map> deleteContentList, List<Map> updateContentList, String mainModelUuid, JSONObject mainDataJson, String lookupUuid, String modelUuid, String projectUuid, Map<String, String> relationLookup, String operatorUuid, String token, Configuration conf) throws BusinessException {
        //处理新增的表单json
        if (null != insertContentList && insertContentList.size() > 0) {
            handleRecursionSave(insertContentList, mainModelUuid, mainDataJson, lookupUuid, modelUuid, projectUuid, relationLookup, operatorUuid, conf);
        }

        //处理删除的表单json
        if (null != deleteContentList && deleteContentList.size() > 0) {
            handleSignalDeleteContent(deleteContentList, modelUuid, token, operatorUuid, projectUuid, relationLookup, conf);
        }

        //处理修改的表单json
        if (null != updateContentList && updateContentList.size() > 0) {
            //循环处理编辑的数据
            for (int i = 0; i < updateContentList.size(); i++) {
                JSONObject signalJson = new JSONObject(updateContentList.get(i));
                //处理修改数据
                handleSignalUpdateContent(signalJson, modelUuid, token, operatorUuid, projectUuid);

                //获取子表单的操作json
                JSONArray subFormArray = signalJson.getJSONArray("sub_form");
                if (null == subFormArray || subFormArray.size() < 1) {
                    continue;
                }
                for (int j = 0; j < subFormArray.size(); j++) {
                    //获取子表单的实体
                    DocumentContext subDataJsonDocument = JsonPath.using(conf).parse(subFormArray.get(j));
                    //获取子表单的content
                    String subModelUuid = subDataJsonDocument.read("$.model_uuid");
                    String subLookupUuid = subDataJsonDocument.read("$.lookup_uuid");
                    Map<String, String> subRelationLookup = subDataJsonDocument.read("$.lookup_relation");
                    //如果子表表名为空，则结束本次循环
                    if (StringUtils.isBlank(subModelUuid)) {
                        continue;
                    }
                    //获取新增或者删除或者编辑的json
                    List<Map> subInsertContentList = null;
                    List<Map> subDeleteContentList = null;
                    List<Map> subUpdateContentList = null;
                    try {
                        subInsertContentList = subDataJsonDocument.read("$.insertContent.*");
                    } catch (Exception e) {
                    }

                    try {
                        subDeleteContentList = subDataJsonDocument.read("$.delete.*");
                    } catch (Exception e) {
                    }

                    try {
                        subUpdateContentList = subDataJsonDocument.read("$.updateContent.*");
                    } catch (Exception e) {
                    }

                    //获取当前层级的数据主键id
                    String subMainDataId = signalJson.getString("main_data_id");
                    JSONObject subMainDataJson = new JSONObject();
                    subMainDataJson.put(com.baomidou.mybatisplus.core.toolkit.StringUtils.underlineToCamel(primarykeys.get(modelUuid)), subMainDataId);
                    //递归处理子表单里的子表单数据
                    handleRecursionUpdate(subInsertContentList, subDeleteContentList, subUpdateContentList, modelUuid, subMainDataJson, subLookupUuid, subModelUuid, projectUuid, subRelationLookup, operatorUuid, token, conf);
                }
            }
        }
    }

    public void handleSignalDeleteContent(List deleteContentList, String modelUuid, String token, String operatorUuid, String projectUuid, Map<String, String> relationLookup, Configuration conf) throws BusinessException {
        //处理删除的表单json
        if (null != deleteContentList && deleteContentList.size() > 0) {
            for (int i = 0; i < deleteContentList.size(); i++) {
                //调用删除数据
                DocumentContext dc = JsonPath.using(conf).parse(deleteContentList.get(i));
                this.handleDatagridDelete(modelUuid, dc, token, operatorUuid, projectUuid);
            }
        }
    }

    public void handleSignalUpdateContent(JSONObject signalJson, String modelUuid, String token, String operatorUuid, String projectUuid) throws BusinessException {
        //处理修改数据
        //获取主表数据变更内容体：单条
        JSONObject mainForm = signalJson.getJSONObject("main_form");
        String updatetableId = signalJson.getString("main_data_id");

        if (null != mainForm && mainForm.keySet().size() > 0) {
            //如果主表数据存在修改内容，则修改主表信息
            List list = new ArrayList();
            list.add(mainForm);
            this.handleDatagridUpdate(modelUuid, list, token, operatorUuid, projectUuid, updatetableId);
        }
    }

    /**
     * 实体数据的修改处理
     *
     * @param modelUuid
     * @param dataList
     * @param token
     * @param operatorUuid
     * @throws BusinessException
     */
    public void handleDatagridUpdate(String modelUuid, List dataList, String token, String operatorUuid, String projectId, String updatetableId) throws BusinessException {
        //校验参数是否为空

        if (StringUtils.isEmpty(modelUuid)) {
            throw new BusinessException("操作异常：请传入修改数据的实体");
        }
        if (null == dataList || dataList.size() < 1) {
            throw new BusinessException("操作异常：请传入查询内容");
        }
        for (int i = 0; i < dataList.size(); i++) {
            Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
            DocumentContext dataJsonDocument = JsonPath.using(conf).parse(dataList.get(i));

            //修改内容
            List<Map> contentList = new ArrayList<>();
            try {
                contentList = dataJsonDocument.read("$.content.*");
            } catch (Exception e) {
                log.error("获取参数异常", e);
            }
            if (null == contentList || contentList.size() < 1) {
                continue;
            }

            JSONObject updateData = new JSONObject();
            for (Map con : contentList) {
                String table_column = String.valueOf(con.get("table_column"));
                Object value = con.get("value");
                Object lookupRelation = con.get("lookup_relation");
                updateData.put(table_column, null != value && "" != value ? value : "");
            }
            //===================================

            //处理修改条件
            JSONObject whereCondition = this.handelWhereOrCondition(modelUuid, dataJsonDocument, token, Constants.DM_ACTION_TYPE_UPDATE, "1");

            //组装给data-model的数据
            JSONObject mm = new JSONObject();
            Map<String, Object> ordinaryUpdate = new HashMap<>();
            Map<String, Object> lookupUpdate = new HashMap<>();

            // 获取当前project下 lookup字段
            // 获取该实体的所有字段

            mm.put("update", updateData);
            if (null != whereCondition
                    && null != whereCondition.getJSONArray("or")
                    && whereCondition.getJSONArray("or").size() > 0) {
                //查询条件
                mm.put(Constants.DM_SQL_KEYWORDS_WHERE, whereCondition);
            }

            JSONObject dmMap = new JSONObject();
            dmMap.put("operator_uuid", !StringUtils.isEmpty(operatorUuid) ? operatorUuid : "");
            dmMap.put(Constants.DM_PROJECT_UUID, projectId);
            dmMap.put("operate_type", Constants.DM_OPERATE_TYPE_DML);
            dmMap.put("action_type", Constants.DM_ACTION_TYPE_UPDATE);
            dmMap.put("model_uuid", modelUuid);
            dmMap.put("dml", mm);

            log.info("============dm sql===" + dmMap.toJSONString());

            EntityField wlosEntityField1 = new EntityField();
            wlosEntityField1.setEntityEnName(modelUuid);
            List<EntityField> wlosEntityFields = entityFieldMapper.selectList(new QueryWrapper<>(wlosEntityField1));

            List<String> lookFields = new ArrayList<>();
            for (EntityField wlosEntityField : wlosEntityFields) {
                if (wlosEntityField.getType().equals("Lookup")) {
                    lookFields.add(wlosEntityField.getEnName());
                }
            }
            updateData.forEach((key, value) -> {
                if (lookFields.contains(key)) {
                    lookupUpdate.put(key, value);
                } else {
                    ordinaryUpdate.put(key, value);
                }
            });
            transferData(ordinaryUpdate, wlosEntityFields);
            Object document = Configuration.defaultConfiguration().jsonProvider().parse(dmMap.toJSONString());
            String resolveJson = updateResolveJson(document, ordinaryUpdate);
            log.info("==表格修改 sql===" + resolveJson);
            int update = jdbcTemplate.update(resolveJson);
            log.info("");
            if (!lookupUpdate.isEmpty()) {
                String primaryKey = getPrimaryKey(modelUuid);
                HashMap<String, Object> primaryTable = new HashMap<>();
                primaryTable.put(primaryKey, updatetableId);
                // 获取关系详情
                lookupUpdate.keySet().forEach(item -> {
                    EntityRelation relationByRelationKey = getRelationByRelationKey(item);
                    Assert.notNull(relationByRelationKey, "[" + item + "]关系不存在，请确认！");
                    updateLookup(modelUuid, relationByRelationKey, lookupUpdate, primaryTable);
                });
            }
        }
    }

    /**
     * @param modelUuid        实体uuid
     * @param dataJsonDocument 参数json
     * @param token            用户token
     * @param actionType       crud类型
     * @param whereType        查询类型：1-where查询 2-query_builder查询
     * @return
     * @throws BusinessException
     */

    public JSONObject handelWhereOrCondition(String modelUuid, DocumentContext dataJsonDocument, String token, String actionType, String whereType) throws BusinessException {

        JSONObject whereConditionOr = null;
        //获取查询条件
        List<Map> orList = new ArrayList<>();
        try {
            String keyPath = "$.where.or.*";
            if ("2".equals(whereType)) {
                keyPath = "$.query_builder.or.*";
            }
            orList = (List<Map>) dataJsonDocument.read(keyPath);
        } catch (Exception e) {
//                log.error("获取参数异常", e);
        }
        if (null != orList && orList.size() > 0) {
            //判断单组条件是and拼接方式还是condition拼接方式
            boolean andFlag = orList.get(0).containsKey("and");

            if (andFlag) {

                //处理 and拼接方式
                whereConditionOr = new JSONObject();
                whereConditionOr.put("or", new JSONArray());
                boolean subOrParamTrue = false;
                for (Map<String, Object> subOr : orList) {
                    //处理and节点
                    List<Map<String, Object>> andList = new ArrayList<Map<String, Object>>();
                    try {
                        //处理参数路径不存在的异常
                        andList = (List<Map<String, Object>>) subOr.get("and");
                    } catch (Exception e) {
                        log.error("取值异常", e);
                    }

                    JSONObject andCondition = new JSONObject();
                    //标识同组and判断里面的条件都设置上了，如果有一个条件为空，则该组and条件废弃
                    boolean subAndParamTrue = true;
                    for (Map<String, Object> subAnd : andList) {
                        String tableUuid = (String) subAnd.get("table_uuid");
                        if (org.springframework.util.StringUtils.isEmpty(tableUuid)) {
//                                continue;
                            //兼容修改删除没有该字段的情况
                            tableUuid = modelUuid;
                        }
                        //获取查询条件左边的字段/关联
                        String tableType = (String) subAnd.get("left_type");//查询左边表类型：实体/关联关系/lookup
                        if (org.springframework.util.StringUtils.isEmpty(tableType)) {
                            //兼容修改删除没有该字段的情况
                            tableType = "entity";
                        }

                        String tableColumn = (String) subAnd.get("table_column");
                        if (org.springframework.util.StringUtils.isEmpty(tableColumn)) {
                            continue;
                        }

                        String condition = subAnd.get("condition").toString();

                        Object tableColumnValueKeyPath = null;
                        if (Constants.DM_ACTION_TYPE_QUERY.equals(actionType)) {
                            tableColumnValueKeyPath = subAnd.get("right"); //查询构造器里传入的就是值，无须再处理
                        } else {

                            tableColumnValueKeyPath = subAnd.get("value"); //查询构造器里传入的就是值，无须再处理
                        }
                        //获取查询右边的字段/关联/值
                        String rightTableType = (String) subAnd.get("right_type");//查询左边表类型：实体/关联关系
                        if (org.springframework.util.StringUtils.isEmpty(rightTableType)) {
                            //兼容修改删除没有该字段的情况
                            rightTableType = "value";
                        }

                        String paramSourceType = String.valueOf(subAnd.get("paramSourceType"));
                        //获取参数值
                        Object tableColumnValue = tableColumnValueKeyPath;
                        if (!Constants.INPUT_PARAM_SOURCE_TYPE_3.equals(paramSourceType)) {
                            //根据参数类型获取参数的值，然后替换原json中的值
                            //判断是否为获取系统环境变量中的项目编号
                            if (Constants.INPUT_PARAM_SOURCE_TYPE_4.equals(paramSourceType)
                                    && Constants.GLOBAL_VARIABLE_KEY_PROJECT_UUID.equals(tableColumnValueKeyPath)) {
                                tableColumnValue = dataJsonDocument.read("$.project_uuid");
                            } else {
                                tableColumnValue = paramHandleService.getGlobalVariableValue(token, tableColumnValueKeyPath, paramSourceType);
                            }
                        }

                        //过滤查询条件：查询条件不为空的  或者  查询条件为空且查询是查询xxx是空的情况才拼接
                            /*if (!org.springframework.util.StringUtils.isEmpty(tableColumnValue) ||
                                    (org.springframework.util.StringUtils.isEmpty(tableColumnValue)
                                            && ("201".equals(condition) || "200".equals(condition)
                                            || "411".equals(condition) || "410".equals(condition)))) {*/

                        //如果值为空的话，则不添加
                        JSONArray jsonBuilderArray = new JSONArray();
                        if (andCondition.containsKey(condition)) {
                            jsonBuilderArray = andCondition.getJSONArray(condition);
                        }

                        JSONObject conditionBuilderJson = new JSONObject();
                        conditionBuilderJson.put("left", tableUuid + "." + tableColumn);
                        conditionBuilderJson.put("left_type", tableType);
                        conditionBuilderJson.put("right", tableColumnValue);
                        conditionBuilderJson.put("right_type", rightTableType);
                        conditionBuilderJson.put("lookup_relation", subAnd.get("lookup_relation"));

                        //判断是不是lookup条件，如果是lookup的话，则获取relation_key
                        if ("lookup".equals(tableType)) {
                            String relationKey = (String) subAnd.get("relation_key");
                            conditionBuilderJson.put("relation_key", relationKey);
                        }
                        jsonBuilderArray.add(conditionBuilderJson);
                        andCondition.put(condition, jsonBuilderArray);
                            /*} else {
                                subAndParamTrue = false;
                            }*/
                    }
                    if (andCondition.keySet().size() > 0) {
                        subOrParamTrue = true;
                        whereConditionOr.getJSONArray("or").add(andCondition);
                    }
                }

                //判断流程设置的条件是否有效
//                    if (!subOrParamTrue) {
//                        throw new BusinessException("操作异常：流程设置的查询条件无效");
//                    }


            } else {
                //处理condition拼接方式
                //处理where条件
                //处理单组条件
                for (Map singleOr : orList) {
                    singleOr.keySet().forEach(key -> {
                        List<Map> singleCondition = (List<Map>) singleOr.get(key);
                        for (Map condition : singleCondition) {
                            //兼容之前的版本，如果没有参数取值类型，则默认是固定值类型
                            String paramSourceType = Constants.INPUT_PARAM_SOURCE_TYPE_3;
                            if (condition.containsKey("paramSourceType")) {
                                paramSourceType = (String) condition.get("paramSourceType");
                            }

                            Object rigthValueKey = condition.get("right");

                            if (!Constants.INPUT_PARAM_SOURCE_TYPE_3.equals(paramSourceType)) {
                                //根据参数类型获取参数的值，然后替换原json中的值
                                Object rigthValue = null;
                                if (Constants.INPUT_PARAM_SOURCE_TYPE_4.equals(paramSourceType)
                                        && Constants.GLOBAL_VARIABLE_KEY_PROJECT_UUID.equals(rigthValueKey)) {
                                    rigthValue = dataJsonDocument.read("$.project_uuid");
                                } else {
                                    rigthValue = paramHandleService.getGlobalVariableValue(token, rigthValueKey, paramSourceType);
                                }
                                condition.put("right", null != rigthValue && !"".equals(rigthValue) ? rigthValue : "");
                            }
                        }
                    });
                }
            }
        }
        return whereConditionOr;
    }

    @Override
    public void datagridDelete(String data) throws BusinessException {

        //校验参数是否为空
        if (StringUtils.isEmpty(data)) {
            throw new BusinessException("操作异常：请传入查询内容");
        }

        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        DocumentContext dataJsonDocument = JsonPath.using(conf).parse(data);
        //获取修改的表
        String model_uuid = dataJsonDocument.read("$.model_uuid");

        //校验参数是否为空
        if (StringUtils.isEmpty(model_uuid)) {
            throw new BusinessException("操作异常：请传入删除数据的实体");
        }

        String operator_uuid = null;
        String project_uuid = null;
        try {
            operator_uuid = dataJsonDocument.read("$.system.operator_uuid");
            project_uuid = dataJsonDocument.read("$.project_uuid");
        } catch (Exception e) {
            log.debug("捕获异常", e);
        }

        String token = dataJsonDocument.read("$.system.token");

        //调用删除处理
        handleDatagridDelete(model_uuid, dataJsonDocument, token, operator_uuid, project_uuid);

    }

    /**
     * 删除数据组装
     *
     * @param modelUuid
     * @param dataJsonDocument
     * @param token
     * @param operatorUuid
     * @return void
     * @author zjj
     * @date 2021/4/12 13:18
     */
    public void handleDatagridDelete(String modelUuid, DocumentContext dataJsonDocument, String token, String operatorUuid, String projectId) throws BusinessException {
        JSONObject dmlMap = new JSONObject();
        //处理修改条件
        JSONObject whereCondition = this.handelWhereOrCondition(modelUuid, dataJsonDocument, token, Constants.DM_ACTION_TYPE_DELETE, "1");

        if (null != whereCondition
                && null != whereCondition.getJSONArray("or")
                && whereCondition.getJSONArray("or").size() > 0) {
            //查询条件
            dmlMap.put(Constants.DM_SQL_KEYWORDS_WHERE, whereCondition);
        }
        JSONObject dmMap = new JSONObject();
        dmMap.put("operator_uuid", !StringUtils.isEmpty(operatorUuid) ? operatorUuid : "");
        dmMap.put(Constants.DM_PROJECT_UUID, projectId);
        dmMap.put("operate_type", Constants.DM_OPERATE_TYPE_DML);
        dmMap.put("action_type", Constants.DM_ACTION_TYPE_DELETE);
        dmMap.put("model_uuid", modelUuid);
        dmMap.put("dml", dmlMap);

        log.info("==============dm sql==================" + dmMap.toJSONString());
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(dmMap.toJSONString());
        String resolveJson = deleteResolveJson(document);
        jdbcTemplate.execute(resolveJson);
        log.info("============dm return===" + resolveJson);

    }

    /**
     * 数据表格查询
     *
     * @param data
     * @param data--relations
     * @param data--content
     * @param data--where
     * @param data--orderBy
     * @return java.lang.String
     * @author zjj
     * @date 2020/11/12 11:03
     */
    @Override
    public JSONObject datagridQuery(String data, String queryFlag) throws BusinessException {
        //校验参数是否为空
        if (StringUtils.isEmpty(data)) {
            throw new BusinessException("操作异常：请传入查询内容");
        }

        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        DocumentContext dataJsonDocument = JsonPath.using(conf).parse(data);
        //组装给data-model的数据
        JSONObject dataJson = JSONObject.parseObject(data);

        //判断查询内容是否为空
        if (null == dataJson.get(Constants.DM_SQL_KEYWORDS_CONTENT) || "".equals(dataJson.get(Constants.DM_SQL_KEYWORDS_CONTENT))) {
            throw new BusinessException("操作异常：请传入查询内容");
        }

        //校验查询内容
        List<Map> contentList = (List<Map>) dataJson.get(Constants.DM_SQL_KEYWORDS_CONTENT);
        for (Map con : contentList) {
            if (null == con.get(Constants.DM_TABLE_UUID) || "" == con.get(Constants.DM_TABLE_UUID)) {
                throw new BusinessException("操作异常：请传入查询实体");
            }
        }

        //判断查询内容是否为空：如果为空，则默认从content中获取一个
        String modelUuid = "";
        if (null != dataJson.get(Constants.DM_MODEL_UUID) && !"".equals(dataJson.get(Constants.DM_MODEL_UUID))) {
            modelUuid = String.valueOf(dataJson.get(Constants.DM_MODEL_UUID));
        } else {
            modelUuid = String.valueOf(contentList.get(0).get(Constants.DM_TABLE_UUID));
        }

        //查询内容
        JSONObject selectMap = new JSONObject();
        selectMap.put(Constants.DM_SQL_KEYWORDS_CONTENT, dataJson.get(Constants.DM_SQL_KEYWORDS_CONTENT));

        //lookup处理
        JSONObject lookupJson = new JSONObject();
        //兼容没有lookup的流程
        if (dataJson.containsKey("lookup")) {
            try {
                List<Map> lookupList = dataJsonDocument.read("$.lookup");
                if (null != lookupList && lookupList.size() > 0) {
                    for (Map mapLook : lookupList) {
                        Map map = (Map) mapLook.get("look_up");
                        if (map.keySet().size() > 0) {
                            lookupJson.putAll(map);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("====查询中获取lookup异常", e);
            }
        }

        selectMap.put(Constants.DM_SQL_KEYWORDS_LOOKUP, lookupJson);

        JSONObject dmlMap = new JSONObject();
        //组装查询带关联关系的关系数组
        List relations = (List) dataJson.get(Constants.DM_RELATION);

        dmlMap.put(Constants.DM_RELATION, relations);

        //处理分页
        dmlMap = this.handleQueryPage(dataJson, dmlMap);
        //处理取值范围
        dmlMap = this.handleQueryRange(dataJson, dmlMap);
        //处理是否树形
        dmlMap = this.handleTreeStructure(dataJson, dmlMap);

        //查询条件
        dmlMap.put(Constants.DM_SQL_KEYWORDS_WHERE, new JSONObject());

        String token = null;
        try {
            token = dataJsonDocument.read("$.system.token");
        } catch (Exception e) {

        }

        if (null != dataJson.get(Constants.DM_SQL_KEYWORDS_WHERE)) {
            JSONObject whereConditionOr = this.handelWhereOrCondition(modelUuid, dataJsonDocument, token, Constants.DM_ACTION_TYPE_QUERY, "1");
            if (null != whereConditionOr && null != whereConditionOr.getJSONArray("or") && whereConditionOr.getJSONArray("or").size() > 0) {
                //查询条件
                dmlMap.put(Constants.DM_SQL_KEYWORDS_WHERE, whereConditionOr);
            }
        }

        //查询条件--查询构造器
        dmlMap.put(Constants.DM_SQL_KEYWORDS_QUERY_BUILDER, new JSONObject());

        if (null != dataJson.get(Constants.DM_SQL_KEYWORDS_QUERY_BUILDER)) {
                /*List<Map> orList = new ArrayList<>();
                try {
                    orList = (List<Map>) dataJsonDocument.read("$.query_builder.or.*");
                } catch (Exception e) {
                    log.error("获取参数异常", e);
                }
                if (null != orList && orList.size() > 0) {
                    dmlMap.put(Constants.DM_SQL_KEYWORDS_QUERY_BUILDER, dataJson.get(Constants.DM_SQL_KEYWORDS_QUERY_BUILDER));
                }*/

//                String token = dataJsonDocument.read("$.system.token");
            JSONObject whereConditionOr = this.handelWhereOrCondition(modelUuid, dataJsonDocument, token, Constants.DM_ACTION_TYPE_QUERY, "2");
            if (null != whereConditionOr && null != whereConditionOr.getJSONArray("or") && whereConditionOr.getJSONArray("or").size() > 0) {
                //查询条件
                dmlMap.put(Constants.DM_SQL_KEYWORDS_QUERY_BUILDER, whereConditionOr);
            }
        }

        //排序
        dmlMap.put(Constants.DM_SQL_KEYWORDS_ORDER_BY, new JSONObject());
        if (null != dataJson.get(Constants.DM_SQL_KEYWORDS_ORDER_BY) && !"".equals(dataJson.get(Constants.DM_SQL_KEYWORDS_ORDER_BY))) {

            dmlMap.put(Constants.DM_SQL_KEYWORDS_ORDER_BY, dataJson.get(Constants.DM_SQL_KEYWORDS_ORDER_BY));
        }
        //设置dml参数：select
        dmlMap.put(Constants.DM_SQL_KEYWORDS_SELECT, selectMap);

        boolean chartsFlag = false;
        //处理数据统计
        if (dataJson.containsKey("charts")) {
            try {
                //如果传入的json需要做数据统计
                Map charts = (Map) dataJson.get("charts");
                String chartType = (String) charts.get("chart_type");
                //维度
                List<Map> dimensionList = (List<Map>) charts.get("dimension");
                //指标
                List<Map> quotaList = (List<Map>) charts.get("quota");
                //解析数据并组装
                JSONObject dimensionJson = new JSONObject();
                if (null != dimensionList && dimensionList.size() > 0) {
                    for (Map dimensionMap : dimensionList) {
                        String table_uuid = (String) dimensionMap.get("table_uuid");
                        String column_uuid = (String) dimensionMap.get("column_uuid");
                        if (StringUtils.isEmpty(table_uuid) || StringUtils.isEmpty(column_uuid)) {
                            continue;
                        }
                        String agg_type = (String) dimensionMap.get("agg_type");
                        String rank_type = (String) dimensionMap.get("rank_type");

                        JSONObject aggJson = new JSONObject();
                        if (StringUtils.isNotBlank(agg_type)) {
                            aggJson.put("agg_type", agg_type);
                        }
                        if (StringUtils.isNotBlank(rank_type)) {
                            aggJson.put("rank_type", rank_type);
                        }

                        if (null != dimensionMap.get("lookup")) {

                            aggJson.put("lookup", dimensionMap.get("lookup"));
                        }

                        dimensionJson.put(table_uuid + "." + column_uuid, aggJson);
                    }
                }
                JSONObject quotaJson = new JSONObject();
                if (null != quotaList && quotaList.size() > 0) {
                    for (Map quotaMap : quotaList) {
                        String table_uuid = (String) quotaMap.get("table_uuid");
                        String column_uuid = (String) quotaMap.get("column_uuid");
                        if (StringUtils.isEmpty(table_uuid) || StringUtils.isEmpty(column_uuid)) {
                            continue;
                        }

                        String agg_type = (String) quotaMap.get("agg_type");
                        JSONObject aggJson = new JSONObject();
                        if (StringUtils.isNotBlank(agg_type)) {
                            aggJson.put("agg_type", agg_type);
                        }

                        if (null != quotaMap.get("lookup")) {

                            aggJson.put("lookup", quotaMap.get("lookup"));
                        }

                        quotaJson.put(table_uuid + "." + column_uuid, aggJson);
                    }

                    //获取max
                    Object max = charts.get("max");
                    quotaJson.put("max", max);
                }
                chartsFlag = true;
                dmlMap.put(Constants.DM_CHARTS_CHART_TYPE, chartType);
                dmlMap.put(Constants.DM_CHARTS_DIMENSION, dimensionJson);
                dmlMap.put(Constants.DM_CHARTS_QUOTA, quotaJson);

            } catch (Exception e) {
                log.error("解析统计数据异常", e);
            }
        }

        //组装查询data-model的json
        JSONObject dmMap = new JSONObject();
        dmMap.put(Constants.DM_PROJECT_UUID, dataJson.get(Constants.DM_PROJECT_UUID));
        dmMap.put(Constants.DM_APPLICATION_UUID, dataJson.get(Constants.DM_APPLICATION_UUID));
        dmMap.put(Constants.DM_OPERATE_TYPE, Constants.DM_OPERATE_TYPE_DML);
        dmMap.put(Constants.DM_ACTION_TYPE, Constants.DM_ACTION_TYPE_QUERY);
        dmMap.put(Constants.DM_DML, dmlMap);
        dmMap.put(Constants.DM_MODEL_UUID, modelUuid);

        log.info("=========dm params json===" + dmMap.toJSONString());

        String returnStr = "";
        HashMap<String, Object> resultData = new HashMap<>();

        if (chartsFlag) {
            log.info("==============执行数据统计========");
//                returnStr = dataModelService.dataAnalysis(token, dmMap.toJSONString());
        } else {
            queryBuild(modelUuid, dmMap, resultData);
//                returnStr = dataModelService.queryDataInfo(dmMap.toJSONString());
        }
                Map treeStructure = (Map) dmlMap.get(Constants.DM_SQL_KEYWORDS_TREE_STRUCTURE);
        // 获取是否转为树形结构的判断条件
        Boolean isTree = false;
        String treeField = null;
        log.info("=====树形标识=======：{}",JSONObject.toJSONString(treeStructure));
        if (MapUtil.isNotEmpty(treeStructure)) {
            isTree = (Boolean) treeStructure.get("is_tree");
            treeField = String.valueOf(treeStructure.get("field"));
            log.info("=====树形标识2=======：{},{}",isTree,treeField);

            if (isTree && StringUtils.isNotBlank(treeField)) {

                List<Map<String, Object>> tableResult = (List<Map<String, Object>>)resultData.get("resultData");
                 log.info("=====树形处理==开始=：{}",JSONArray.toJSONString(tableResult));
                tableResult = transformDataToTree(tableResult, treeField);
                 log.info("=====树形处理==结束=：{}",JSONArray.toJSONString(tableResult));
                resultData.put("resultData",tableResult);
            }
        }

        JSONObject jsonObject = new JSONObject();
        if ("list".equals(queryFlag)) {

            jsonObject.put("list", resultData);

        } else {
            jsonObject.put("info", null);
            try {
                //判断是否获取到了数据
                Object resultDataSingle = resultData.get("resultData");
                if (resultDataSingle instanceof ArrayList) {
                    ArrayList single = (ArrayList) resultDataSingle;
                    if(ObjectUtils.isNotEmpty(single)&& single.size()>0){
                        jsonObject.put("info", single.get(0));
                    }else {
                        jsonObject.put("info", resultDataSingle);
                    }
                }
            } catch (Exception e) {
                log.error("捕获的异常", e);
            }
        }
        return jsonObject;
    }

    /***
     * 根据流程json查询对应数据
     * @param modelUuid 实体enname
     * @param dmMap 流程拼接完成json
     * @param resultData 返回数据组装Map
     */
    private void queryBuild(String modelUuid, JSONObject dmMap, HashMap<String, Object> resultData) {
        log.info("==============执行查询========");
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(dmMap.toJSONString());
        Map<String, Object> resolveJson = queryResolveJson(document);
        Object primaryTable = resolveJson.get("primaryTable");
        Object lookupTable = resolveJson.get("lookupTable");
        log.info("表格查询，构造完成：主表：{},lookup：{}", primaryTable, lookupTable);
        List<Map<String, Object>> primaryTableResult = jdbcTemplate.queryForList(String.valueOf(primaryTable));
        List<Map<String, Object>> tableResult = new ArrayList<>();
        EntityField wlosEntityField1 = new EntityField();
        wlosEntityField1.setEntityEnName(modelUuid);
        List<EntityField> wlosEntityFields1 = entityFieldMapper.selectList(new QueryWrapper<>(wlosEntityField1));
        String primaryKey = getPrimaryKey(modelUuid);
        tableResult = coverDataToNewData(primaryKey, primaryTableResult, wlosEntityFields1);


        resultData.put("resultData", tableResult);
        resultData.put("pagination", null);
        // 如果主表分页不为空，执行分页语句
        Object primaryTableCountObj = resolveJson.get("primaryTableCount");
        if (ObjectUtils.isNotEmpty(primaryTableCountObj)) {
            Map<String, Object> primaryTableCount = (Map<String, Object>) primaryTableCountObj;
            Map<String, Object> countResult = jdbcTemplate.queryForMap(String.valueOf(primaryTableCount.get("primaryTableCount")));
            WlosPagination wlosPagination = new WlosPagination();
            long count = (long) countResult.get("count(*)");
            wlosPagination.setPage_size((Integer) primaryTableCount.get("page_size"));
            wlosPagination.setCurrent_page((Integer) primaryTableCount.get("current_page"));
            wlosPagination.setTotal_record((int) count);
            resultData.put("pagination", wlosPagination);
        }
        final String primaryTableId = primarykeys.get(modelUuid);
        if (ObjectUtils.isNotEmpty(primaryTableResult)) {
            if (ObjectUtils.isNotEmpty(lookupTable)) {
                List<Map<String, String>> lookupList = (ArrayList<Map<String, String>>) lookupTable;
                for (Map<String, String> item : lookupList) {
                    String relationKey = item.get("relationKey");
                    String relationTableId = item.get("relationTableId");
                    String relationTableName = item.get("relationTableName");
                    String statement = item.get("statement");

                    String flag = statement.substring(statement.length() - 4, statement.length());
                    if (DataOperateConstants.LEFT_TABLE.equals(flag)) {

                        //取relationKey
                        List<String> relationKeys = tableResult.stream().map(ite -> (String) ite.get(relationKey)).filter(itemr -> !"".equals(itemr) && null != itemr).distinct().collect(Collectors.toList());
                        String placeString = statement.replaceAll(flag, "");
                        StringBuilder lookupSelect = new StringBuilder(placeString);
                        lookupSelect.append(" ( '");
                        lookupSelect.append(StringUtils.join(relationKeys, "','"));
                        lookupSelect.append("')");
                        log.info("表格关联查询lookup取relationKey，relationKey：{},sql:{}", relationKey, lookupSelect.toString());

                        List<Map<String, Object>> maps = jdbcTemplate.queryForList(lookupSelect.toString());
                        LambdaQueryWrapper<EntityField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                        lambdaQueryWrapper.eq(EntityField::getEntityEnName, relationTableName);

                        List<EntityField> relationFields = entityFieldMapper.selectList(lambdaQueryWrapper);
                        maps = coverDataToNewData(relationTableId, maps, relationFields);


                        for (Map<String, Object> itemPrimaryData : tableResult) {
                            String relation = (String) itemPrimaryData.get(relationKey);
                            List<Map<String, Object>> collect = maps.stream().filter(itraw -> itraw.get(relationTableId).equals(relation)).distinct().collect(Collectors.toList());
                            ArrayList<Map<String, Object>> lookupResultList = new ArrayList<>();
                            for (Map<String, Object> itemLookup : collect) {

                                Map<String, Object> lookupMap = new HashMap<>();
                                lookupMap.put(Constants.UUID, itemLookup.get(relationTableId));
                                lookupMap.put(Constants.COLUMNS, itemLookup);
                                lookupResultList.add(lookupMap);
                            }
                            itemPrimaryData.put(relationKey, lookupResultList);
                        }

                    } else {
                        //取主键
                        List<String> primaryTableIdList = tableResult.stream().map(ite -> (String) ite.get(primaryTableId)).filter(itemr -> !"".equals(itemr) && null != itemr).collect(Collectors.toList());
                        String placeString = statement.replaceAll(flag, "");
                        StringBuilder lookupSelect = new StringBuilder(placeString);
                        lookupSelect.append(" ( '");
                        lookupSelect.append(StringUtils.join(primaryTableIdList, "','"));
                        lookupSelect.append("')");
                        log.info("表格关联查询lookup取主键，relationKey：{},sql{}", relationKey, lookupSelect.toString());
                        List<Map<String, Object>> maps = jdbcTemplate.queryForList(lookupSelect.toString());

                        LambdaQueryWrapper<EntityField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                        lambdaQueryWrapper.eq(EntityField::getEntityEnName, relationTableName);

                        List<EntityField> relationFields = entityFieldMapper.selectList(lambdaQueryWrapper);
                        maps = coverDataToNewData(relationTableId, maps, relationFields);

                        for (Map<String, Object> itemPrimaryData : tableResult) {
                            String primaryId = (String) itemPrimaryData.get(primaryTableId);
                            List<Map<String, Object>> collect = maps.stream().filter(itraw -> itraw.get(relationKey).equals(primaryId)).distinct().collect(Collectors.toList());
                            ArrayList<Map<String, Object>> lookupResultList = new ArrayList<>();
                            for (Map<String, Object> itemLookup : collect) {
                                Map<String, Object> lookupMap = new HashMap<>();
                                lookupMap.put(Constants.UUID, itemLookup.get(relationTableId));
                                lookupMap.put(Constants.COLUMNS, itemLookup);
                                lookupResultList.add(lookupMap);
                            }
                            itemPrimaryData.put(relationKey, lookupResultList);
                        }
                    }

                }
            }
        }
    }

    /**
     * 处理查询的分页逻辑
     *
     * @param dataJson
     * @param dmlJson
     * @return
     * @throws BusinessException
     */
    public JSONObject handleQueryPage(JSONObject dataJson, JSONObject dmlJson) throws BusinessException {
        if (!dataJson.containsKey(Constants.DM_SQL_KEYWORDS_PAGINATION)) {
            return dmlJson;
        }
        //处理分页
        JSONObject pageMap = dataJson.getJSONObject(Constants.DM_SQL_KEYWORDS_PAGINATION);
        if (null != pageMap && pageMap.keySet().size() > 0) {
            //获取是否分页
            String pageMode = pageMap.getString("pageMode");
            //如果分页
            if (Constants.PAGE_MODE_2.equals(pageMode)) {
                //分页
                Integer currentPage = 1;
                Integer pageSize = 10;
                try {
                    //获取分页对象
                    JSONObject pageInfo = pageMap.getJSONObject("pageInfo");
                    if (null == pageInfo) {
                        return dmlJson;
                    }

                    String pageSizeStr = pageInfo.getString("pageSize");
                    if (StringUtils.isNotBlank(pageSizeStr)) {
                        pageSize = Integer.valueOf(pageSizeStr);
                    }

                    String currentPageStr = pageInfo.getString("currentPage");
                    if (StringUtils.isNotBlank(currentPageStr)) {
                        currentPage = Integer.valueOf(currentPageStr);
                    }
                } catch (Exception e) {

                }

                if (null == currentPage) {
                    currentPage = 1;
                }
                if (null == pageSize) {
                    //如果为空，则默认10条
                    pageSize = 10;
                }
                JSONObject pageJson = new JSONObject();
                pageJson.put(Constants.DM_SQL_KEYWORDS_CURRENT_PAGE, currentPage);
                pageJson.put(Constants.DM_SQL_KEYWORDS_PAGE_SIZE, pageSize);
                dmlJson.put(Constants.DM_SQL_KEYWORDS_PAGINATION, pageJson);
            }
        }
        return dmlJson;

    }

    public JSONObject handleTreeStructure(JSONObject dataJson, JSONObject dmlJson) throws BusinessException {
        if (!dataJson.containsKey(Constants.DM_SQL_KEYWORDS_TREE_STRUCTURE)) {
            return dmlJson;
        }
        //处理是否要转为树形结构
        JSONObject treeMap = dataJson.getJSONObject(Constants.DM_SQL_KEYWORDS_TREE_STRUCTURE);
        dmlJson.put(Constants.DM_SQL_KEYWORDS_TREE_STRUCTURE, treeMap);
        return dmlJson;

    }

    /**
     * 处理查询的取值范围逻辑
     *
     * @param dataJson
     * @param dmlJson
     * @return
     * @throws BusinessException
     */
    public JSONObject handleQueryRange(JSONObject dataJson, JSONObject dmlJson) throws BusinessException {
        if (!dataJson.containsKey(Constants.DM_SQL_KEYWORDS_PAGINATION)) {
            return dmlJson;
        }
        //处理按范围取值
        JSONObject pageMap = dataJson.getJSONObject(Constants.DM_SQL_KEYWORDS_PAGINATION);
        if (null != pageMap && pageMap.keySet().size() > 0) {
            //获取范围
            JSONObject rangeMap = pageMap.getJSONObject(Constants.DM_SQL_KEYWORDS_RANGE);
            if (null != rangeMap && rangeMap.keySet().size() > 0) {
                Integer startpoint = null;
                Integer endpoint = null;
                try {
                    startpoint = rangeMap.getInteger("startpoint");
                    endpoint = rangeMap.getInteger("endpoint");
                } catch (Exception e) {
                    log.debug("捕获取值异常", e);
                }
                //判断是否设置了取值范围
                if (null != startpoint && null != endpoint) {
                    //如果设置了开始结束取值范围，则查询组合里面加入按范围取值
                    JSONObject rangeJson = new JSONObject();
                    rangeJson.put("startpoint", startpoint);
                    rangeJson.put("endpoint", endpoint);
                    dmlJson.put(Constants.DM_SQL_KEYWORDS_RANGE, rangeJson);
                }
            }
        }
        return dmlJson;

    }

    @Override
    public JSONObject datagridInfo(String data) throws BusinessException {
        //校验参数是否为空
        if (StringUtils.isEmpty(data)) {
            throw new BusinessException("操作异常：请传入查询内容");
        }

        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        DocumentContext dataJsonDocument = JsonPath.using(conf).parse(data);
        //组装给data-model的数据
        JSONObject dataJson = JSONObject.parseObject(data);

        //判断查询内容是否为空
        if (null == dataJson.get(Constants.DM_SQL_KEYWORDS_CONTENT) || "".equals(dataJson.get(Constants.DM_SQL_KEYWORDS_CONTENT))) {
            throw new BusinessException("操作异常：请传入查询内容");
        }

        //校验查询内容
        List<Map> contentList = (List<Map>) dataJson.get(Constants.DM_SQL_KEYWORDS_CONTENT);
        for (Map con : contentList) {
            if (null == con.get(Constants.DM_TABLE_UUID) || "" == con.get(Constants.DM_TABLE_UUID)) {
                throw new BusinessException("操作异常：请传入查询实体");
            }
        }

        //判断查询内容是否为空：如果为空，则默认从content中获取一个
        String modelUuid = "";
        if (null != dataJson.get(Constants.DM_MODEL_UUID) && !"".equals(dataJson.get(Constants.DM_MODEL_UUID))) {
            modelUuid = String.valueOf(dataJson.get(Constants.DM_MODEL_UUID));
        } else {
            modelUuid = String.valueOf(contentList.get(0).get(Constants.DM_TABLE_UUID));
        }


        //查询内容
        JSONObject selectMap = new JSONObject();
        selectMap.put(Constants.DM_SQL_KEYWORDS_CONTENT, dataJson.get(Constants.DM_SQL_KEYWORDS_CONTENT));

        JSONObject dmlMap = new JSONObject();
        //组装查询带关联关系的关系数组
        List relations = (List) dataJson.get(Constants.DM_RELATION);

        dmlMap.put(Constants.DM_RELATION, relations);
        //查询条件
        dmlMap.put(Constants.DM_SQL_KEYWORDS_WHERE, new JSONObject());

        if (null != dataJson.get(Constants.DM_SQL_KEYWORDS_WHERE)) {
            List<Map> orList = new ArrayList<>();
            try {

                orList = (List<Map>) dataJsonDocument.read("$.where.or.*");
            } catch (Exception e) {
                log.error("获取参数异常", e);
            }
            if (null != orList && orList.size() > 0) {
                dmlMap.put(Constants.DM_SQL_KEYWORDS_WHERE, dataJson.get(Constants.DM_SQL_KEYWORDS_WHERE));
            }
        }

        //设置dml参数：select
        dmlMap.put(Constants.DM_SQL_KEYWORDS_SELECT, selectMap);

        String operator_uuid = null;
        String project_uuid = null;
        try {
            operator_uuid = dataJsonDocument.read("$.system.operator_uuid");
            project_uuid = dataJsonDocument.read("$.project_uuid");
        } catch (Exception e) {
            log.debug("捕获异常", e);
        }

        //组装查询data-model的json
        JSONObject dmMap = new JSONObject();
        dmMap.put(Constants.DM_PROJECT_UUID, project_uuid);
        dmMap.put(Constants.DM_OPERATE_TYPE, Constants.DM_OPERATE_TYPE_DML);
        dmMap.put(Constants.DM_ACTION_TYPE, Constants.DM_ACTION_TYPE_QUERY);
        dmMap.put(Constants.DM_DML, dmlMap);
        dmMap.put(Constants.DM_MODEL_UUID, modelUuid);

        log.info("=========dm params json===" + dmMap.toJSONString());

        log.info("==============执行查询");
        HashMap<String, Object> resultData = new HashMap<>();
        queryBuild(modelUuid, dmMap, resultData);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("info", null);


        try {
            //判断是否获取到了数据
            Object resultDataSingle = resultData.get("resultData");
            if (resultDataSingle instanceof ArrayList) {
                ArrayList single = (ArrayList) resultDataSingle;
                if (ObjectUtils.isNotEmpty(single)) {
                    jsonObject.put("info", single.get(0));
                }
            }
        } catch (Exception e) {
            log.error("捕获的异常", e);
        }

        return jsonObject;

    }

    @Override
    public JSONObject datagridQueryById(String data) throws BusinessException {
        //校验参数是否为空
        if (StringUtils.isEmpty(data)) {
            throw new BusinessException("操作异常：请传入查询内容");
        }

        //参数json
        JSONObject dataJson = JSONObject.parseObject(data);
        //获取表和id值
        if (dataJson.keySet().size() < 1) {
            throw new BusinessException("操作异常：请传入查询内容");
        }
        String tableUuid = dataJson.getString("tableUuid");
        String idValue = dataJson.getString("id");

        //校验参数是否为空
        if (StringUtils.isEmpty(tableUuid)) {
            throw new BusinessException("操作异常：请传入查询的表名");
        }
        if (StringUtils.isEmpty(idValue)) {
            throw new BusinessException("操作异常：请传入数据编号值");
        }

        //查询内容
        List clist = new ArrayList();
        JSONObject json = new JSONObject();
        json.put("table_uuid", tableUuid);
        json.put("columns", new ArrayList());
        clist.add(json);
        JSONObject selectMap = new JSONObject();
        selectMap.put(Constants.DM_SQL_KEYWORDS_CONTENT, clist);

        JSONObject dmlMap = new JSONObject();
        //组装查询带关联关系的关系数组
        dmlMap.put(Constants.DM_RELATION, new ArrayList());
        //查询条件

        JSONObject conditionJson = new JSONObject();
        conditionJson.put("left", tableUuid + "." + primarykeys.get(tableUuid));
        conditionJson.put("left_type", "entity");
        conditionJson.put("right", idValue);
        conditionJson.put("right_type", "value");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(conditionJson);
        JSONObject singleCondition = new JSONObject();
        singleCondition.put("11", jsonArray);
        JSONObject conditionOr = new JSONObject();
        conditionOr.put("or", new JSONArray());
        conditionOr.getJSONArray("or").add(singleCondition);
        dmlMap.put(Constants.DM_SQL_KEYWORDS_WHERE, conditionOr);

        //设置dml参数：select
        dmlMap.put(Constants.DM_SQL_KEYWORDS_SELECT, selectMap);

        String operator_uuid = null;
        String project_uuid = null;
        try {
            operator_uuid = dataJson.getString("$.system.operator_uuid");
            project_uuid = dataJson.getString("$.project_uuid");
        } catch (Exception e) {
            log.debug("捕获异常", e);
        }


        //组装查询data-model的json
        JSONObject dmMap = new JSONObject();
        dmMap.put(Constants.DM_PROJECT_UUID, project_uuid);
        dmMap.put(Constants.DM_OPERATE_TYPE, Constants.DM_OPERATE_TYPE_DML);
        dmMap.put(Constants.DM_ACTION_TYPE, Constants.DM_ACTION_TYPE_QUERY);
        dmMap.put(Constants.DM_DML, dmlMap);
        dmMap.put(Constants.DM_MODEL_UUID, tableUuid);

        log.info("=========dm params json===" + dmMap.toJSONString());

        log.info("==============执行查询");
        HashMap<String, Object> resultData = new HashMap<>();

        queryBuild(tableUuid, dmMap, resultData);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("info", null);
        try {
            //判断是否获取到了数据
            Object resultDataSingle = resultData.get("resultData");
            if (resultDataSingle instanceof ArrayList) {
                ArrayList single = (ArrayList) resultDataSingle;
                if (ObjectUtils.isNotEmpty(single)) {
                    jsonObject.put("info", single.get(0));
                }
            }
        } catch (Exception e) {
            log.error("捕获的异常", e);
        }
        return jsonObject;
    }



    /***
     * 解析为mybatis格式 class ？+ xml
     * @param document 文档
     * @return
     */
    public String updateResolveJson(Object document, Map<String, Object> ordinaryUpdate) {
        String modelUuid = JsonPath.read(document, "$.model_uuid");

        //Map<String, Object> update = JsonPath.read(document, "$.dml.update");
        StringBuilder updateJoinBuilder = new StringBuilder();
        StringBuilder updateJoinBuilderXml = new StringBuilder();
        updateJoinBuilder.append(CharPool.LF).append(CharPool.LF).append(CharPool.TAB);
        updateJoinBuilderXml.append("update ").append(realEnNames.get(modelUuid)).append(" set ");
        StringBuilder updateField = new StringBuilder();
        ordinaryUpdate.forEach((key, value) -> {
            if (null == key || null == value) {
                throw new BusinessException(-3271, "method:方法无字段或字段空，请确认");
            }
            updateField.append(key);

            if(ObjectUtils.isEmpty(value)){
                updateField.append( " = null ");
            }else {
                if (value instanceof String) {
                    updateField.append(" = '").append(value).append("'");
                } else {
                    updateField.append(" = '").append(JSON.toJSONString(value)).append("'  ");
                }
            }
            updateField.append(", ");
        });

        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(updateField)) {
            updateField.replace(updateField.length() - 2, updateField.length(), "");
            updateJoinBuilderXml.append(updateField);
        }

        StringBuilder whereBuiler = new StringBuilder();
        Map whereStr = JsonPath.read(document, "$.dml.where");
        List<Object> orArrayList = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(whereStr)) {
            List<String> orArray = JsonPath.read(document, "$.dml.where.or");
            orArrayList.addAll(orArray);
        }

        if (ObjectUtils.isNotEmpty(whereStr)) {
            whereBuiler.append(" where ");
        }

        for (int i = 0; i < orArrayList.size(); i++) {
            Map<String, Object> map = null;
            if (orArrayList.get(i) instanceof Map) {
                map = (Map<String, Object>) orArrayList.get(i);
            }
            if (orArrayList.size() > 1) {
                whereBuiler.append(" ( ");
            }
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                String key = next.getKey();
                Object value = next.getValue();
                List<HashMap<String, Object>> valueOperator = null;
                if (value instanceof ArrayList) {
                    valueOperator = (List<HashMap<String, Object>>) value;
                }
                //组装where
                joinWhereBuilder(whereBuiler, valueOperator, key);
                if (iterator.hasNext()) {
                    whereBuiler.append(" AND ");
                }
            }

            if (orArrayList.size() > 1) {
                whereBuiler.append(" ) ");
            }
            if (i != orArrayList.size() - 1) {
                whereBuiler.append(" OR ");
            }
            String whereOperatered = whereBuiler.toString();
            if (whereOperatered.endsWith(" and ")) {
//                whereOperatered.
            }
        }
        updateJoinBuilderXml.append(whereBuiler);
        log.info("拼接语句结束：{}", updateJoinBuilderXml);
        return updateJoinBuilderXml.toString();
    }

    /***
     * 删除解析xml mybatis 格式
     * @param document 文档
     * @return
     */
    public String deleteResolveJson(Object document) {
        String modelUuid = JsonPath.read(document, "$.model_uuid");
        Object readDelete = null;
        try {
            readDelete = JsonPath.read(document, "$.dml.where.or");
        } catch (Exception e) {

        }

        if (ObjectUtils.isEmpty(readDelete)) {
            throw new BusinessException(-3006, "请输入删除条件");
        }
        StringBuilder deleteBuilderXml = new StringBuilder();

        deleteBuilderXml.append("DELETE FROM ").append("`").append(realEnNames.get(modelUuid)).append("`");

        StringBuilder whereBuiler = new StringBuilder();
        Map whereStr = JsonPath.read(document, "$.dml.where");
        List<Object> orArrayList = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(whereStr)) {
            List<String> orArray = JsonPath.read(document, "$.dml.where.or");
            orArrayList.addAll(orArray);
            whereBuiler.append(" where ");
        }

        for (int i = 0; i < orArrayList.size(); i++) {
            Map<String, Object> map = null;
            if (orArrayList.get(i) instanceof Map) {
                map = (Map<String, Object>) orArrayList.get(i);
            }
            if (orArrayList.size() > 1) {
                whereBuiler.append(" ( ");
            }
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                String key = next.getKey();
                Object value = next.getValue();
                List<HashMap<String, Object>> valueOperator = null;
                if (value instanceof ArrayList) {
                    valueOperator = (List<HashMap<String, Object>>) value;
                }
                //组装where
                joinWhereBuilder(whereBuiler, valueOperator, key);
                if (iterator.hasNext()) {
                    whereBuiler.append(" AND ");
                }
            }

            if (orArrayList.size() > 1) {
                whereBuiler.append(" ) ");
            }
            if (i != orArrayList.size() - 1) {
                whereBuiler.append(" OR ");
            }
            String whereOperatered = whereBuiler.toString();
            if (whereOperatered.endsWith(" and ")) {
//                whereOperatered.
            }
        }
        deleteBuilderXml.append(whereBuiler);
        return deleteBuilderXml.toString();
    }


    /***
     * 查询构造转换 0，4  解析
     * mybatis查询
     * @param document bson 文档
     *      * @return 解析后xml+class格式
     * @return 转换后method
     */
    public Map<String, Object> queryResolveJson(Object document) {
        String modelUuid = JsonPath.read(document, "$.model_uuid");
        assert JsonPath.read(document, "$.dml.select.look_up") == null;
        Object lookupObj = null;
        try {
            lookupObj = JsonPath.read(document, "$.dml.select.look_up");
        } catch (Exception e) {
            log.warn("查询无lookup");
        }
        Map<String, Object> lookMapPre = new HashMap<>();
        if (ObjectUtils.isNotEmpty(lookupObj)) {
            if (lookupObj instanceof Map) {
                lookMapPre = BeanUtil.beanToMap(lookupObj);
            }
        }

        assert JsonPath.read(document, "$.dml.select.relation_condition") != null;
        Object relationCondition = null;
        try {
            relationCondition = JsonPath.read(document, "$.dml.select.relation_condition");
        } catch (Exception e) {
            log.warn("查询无relation_condition");
        }
        List relationConditionListPre = new ArrayList();
        if (ObjectUtils.isNotEmpty(relationCondition)) {
            if (relationCondition instanceof List)
                relationConditionListPre = (List) relationCondition;
        }
        //此处需手动处理类型，否则报caseException
        List<RelationConditionVO> relationConditionVOS = new ArrayList<>();
        for (Object item : relationConditionListPre) {
            String conditonStr = JSON.toJSONString(item);
            RelationConditionVO relationConditionVO = JSONObject.parseObject(conditonStr, RelationConditionVO.class);
            relationConditionVOS.add(relationConditionVO);
        }
        Map<String, WlosLookupVO> lookMap = new HashMap<>(lookMapPre.size());
        //lookup 数据结构处理
        lookMapPre.forEach((key, object) -> {
            String valueStr = JSON.toJSONString(object);
            WlosLookupVO wlosLookupVO = JSON.parseObject(valueStr, WlosLookupVO.class);
            wlosLookupVO.setRelationKey(key);
            lookMap.put(key, wlosLookupVO);
        });
        StringBuilder selectConditionTransformXml = new StringBuilder();
        //拼装map及对应xml
        Object contentObject = null;
        try {
            contentObject = JsonPath.read(document, "$.dml.select.content");
        } catch (Exception e) {
            log.warn("查询无content");
        }
        //遍历select节点与where节点 ，寻找并集实体，提前处理表连接

        String contentStr = JSON.toJSONString(contentObject);
        List<WlosLookupVO> wlosLookupVOS = JSONArray.parseArray(contentStr, WlosLookupVO.class);
        //解解析lookup字段
//        Map<String, WlosLookupVO> lookMap = JSONObject.parseObject(lookupStr, Map.class);
        Collection<WlosLookupVO> values = lookMap.values();
        List<WlosLookupVO> lookupColumns = values.stream().filter(distinctByKey(WlosLookupVO::getRelationKey)).collect(Collectors.toList());
        Set<String> aliasArray = new HashSet<>(lookupColumns.size() + 1);
        Map<String, String> inquiredJoinTable = new HashMap<>();
        String primarytableId = "";
        primarytableId = primarykeys.get(modelUuid);
        if (ObjectUtils.isEmpty(wlosLookupVOS)) {
            selectConditionTransformXml.append("select *");
            selectConditionTransformXml.append(",");
            selectConditionTransformXml.append("`");
            selectConditionTransformXml.append(primarytableId);
            selectConditionTransformXml.append("`");
            selectConditionTransformXml.append(" as id ");
            wlosLookupVOS = new ArrayList<>();
            //primarytableId = modelUuid + "_id";
        } else {
            selectConditionTransformXml.append("select *");
            selectConditionTransformXml.append(",");
            selectConditionTransformXml.append("`");
            selectConditionTransformXml.append(primarytableId);
            selectConditionTransformXml.append("`");
            selectConditionTransformXml.append(" as id ");
            List<String> columns = wlosLookupVOS.get(0).getColumns();

            if (ObjectUtils.isEmpty(columns)) {
                // 此次可采取两种方案，方案一，不传column则查所有，方案二，抛出异常
                log.error("查询无columns:========methodName:{}");
//                selectConditionTransformXml.append("select a.*");
                throw new BusinessException(-3301, "methodName:{}方法无查询字段columns，请确认！");
            } else {
                List<String> collect = columns.stream().filter(item -> DataOperateConstants.RELATION_TABLE_ID_TEMP.contains(item)).collect(Collectors.toList());
                //    if (ObjectUtils.isNotEmpty(collect)) {
                //      primarytableId = collect.get(0);
                //} else {
                //      primarytableId = modelUuid + "_id";
                //  }
                // selectConditionTransformXml.append("select ");
            }
        }
        //all table name
        // for (int i = 0; i < wlosLookupVOS.size(); i++) {
        //     WlosLookupVO wlosLookupVO = wlosLookupVOS.get(i);
        //     List<String> columns = wlosLookupVO.getColumns();
        //     String table_uuid = wlosLookupVO.getTable_uuid();

        //     for (int y = 0; y < columns.size(); y++) {
        //         String column = columns.get(y);
        //         String alias = inquiredJoinTable.get(table_uuid);

        //         selectConditionTransformXml.append("`");
        //         selectConditionTransformXml.append(column);
        //         selectConditionTransformXml.append("`");

        //         if (y != (columns.size() - 1)) {
        //             selectConditionTransformXml.append(",");
        //         }else {
        //             selectConditionTransformXml.append(",");
        //             selectConditionTransformXml.append("`");
        //             selectConditionTransformXml.append(primarytableId);
        //             selectConditionTransformXml.append("`");
        //             selectConditionTransformXml.append(" as id");
        //         }
        //     }
        //     if(!columns.contains(primarytableId)){
        //         selectConditionTransformXml.append(",");
        //         selectConditionTransformXml.append("`");
        //         selectConditionTransformXml.append(primarytableId);
        //         selectConditionTransformXml.append("`");
        //     }


        //         if (i != wlosLookupVOS.size() - 1) {
        //         selectConditionTransformXml.append(",");

        //     }
        // }
//        ArrayList<MultiValueMap<String, String>> lookupList = new ArrayList<>();
        ArrayList<Map<String, String>> lookupList = new ArrayList<>();
        ArrayList<Map<String, String>> lookupStatement = new ArrayList<>();
        //定义区间表
        lookupColumns.forEach(item -> {
//            MultiValueMap<String, String> methodNameRelationTableMap = new LinkedMultiValueMap<>();
            StringBuilder lookupSelectStatement = new StringBuilder();
            Map<String, String> methodNameRelationTableMap = new HashMap<>();

            String table_uuid = item.getTable_uuid();
            String relationKey = item.getRelationKey();
            List<String> columns = item.getColumns();


            lookupList.add(methodNameRelationTableMap);


//            methodNameRelationTableMap.put("name", alias + "_");
            methodNameRelationTableMap.put("relationKey", item.getRelationKey());
//            methodNameRelationTableMap.add(alias + "_", item.getRelationKey());
            WlosLookupVO.LookupRelation lookupRelation = item.getLookup_relation();
            if (ObjectUtils.isEmpty(lookupRelation)) {
                throw new BusinessException(-3421, "lookup_relation不能为空");
            }
            String type = lookupRelation.getType();
            String leftEntityUuid = lookupRelation.getLeftEntityUuid();
            String relationTableId = primarykeys.get(table_uuid);
            lookupSelectStatement.append("select ");

//            for (String column : columns) {
//                lookupSelectStatement.append("`");
//                lookupSelectStatement.append(column);
//                lookupSelectStatement.append("`");
//                lookupSelectStatement.append(",");
//            }
            lookupSelectStatement.append("*");
            lookupSelectStatement.append(",");

            lookupSelectStatement.append(" `");
            lookupSelectStatement.append(relationTableId);
            lookupSelectStatement.append("`");

            lookupSelectStatement.append(" from ").append("`");
            String relationRealName = realEnNames.get(table_uuid);
            lookupSelectStatement.append(relationRealName).append("`");
            lookupSelectStatement.append(" where ");

            String rightEntityUuid = lookupRelation.getRightEntityUuid();
            String leftRelationField = lookupRelation.getLeftRelationField();
            String rightRelationField = lookupRelation.getRightRelationField();
            Map<String, String> lookmap = new HashMap<>();
            lookmap.put("relationTableName", table_uuid);
            lookmap.put("relationKey", relationKey);
            lookmap.put("relationTableId", relationTableId);


            if (type.equals("1") || type.equals("2")) {
                if (leftEntityUuid.equals(table_uuid)) {
                    lookupSelectStatement.append(relationTableId).append(" IN ").append(DataOperateConstants.LEFT_TABLE);
                } else {
                    lookupSelectStatement.append(relationKey).append(" IN ").append(DataOperateConstants.RIGHT_TABLE);
                }
            } else if (type.equals("3")) {
                if (leftEntityUuid.equals(table_uuid)) {
                    lookupSelectStatement.append(relationKey).append(" IN ").append(DataOperateConstants.RIGHT_TABLE);

                } else {
                    lookupSelectStatement.append(relationTableId).append(" IN ").append(DataOperateConstants.LEFT_TABLE);
                }

            } else if (type.equals("4")) {
                EntityRelation entityRelation = getRelationByRelationKey(relationKey);
                String midEntityEnName = entityRelation.getMidEntityEnName();
                if (StringUtils.isBlank(midEntityEnName)) {
                    throw new BusinessException("多对多中间表不存在!");
                }
                EntityRelation relationByMiddleOrgin1 = getRelationByMiddleOrgin(leftEntityUuid, midEntityEnName, midEntityEnName);
                EntityRelation relationByMiddleOrgin2 = getRelationByMiddleOrgin(rightEntityUuid, midEntityEnName, midEntityEnName);
                if ((ObjectUtils.isEmpty(relationByMiddleOrgin1)) || (ObjectUtils.isEmpty(relationByMiddleOrgin2))) {
                    throw new BusinessException("多对多关系实体与中间表关系不存在!");
                }
                lookupSelectStatement =new StringBuilder();
                lookupSelectStatement.append(" select a.* ,a.").append("`").append(relationTableId).append("`");
                if (leftEntityUuid.equals(table_uuid)) {
                    lookupSelectStatement.append(",b.`").append(relationByMiddleOrgin2.getRightRelationField()).append("` as ").append(relationKey);
                    lookupSelectStatement.append(" from ").append(relationRealName).append(" a inner join ").append(midEntityEnName).append(" b ");

                    lookupSelectStatement.append("on a.").append(relationTableId).append(" = b.").append(relationByMiddleOrgin1.getRightRelationField());
                    lookupSelectStatement.append(" where b.").append(relationByMiddleOrgin2.getRightRelationField()).append(" in ").append(DataOperateConstants.RIGHT_TABLE);
                } else {
                    lookupSelectStatement.append(",b.`").append(relationByMiddleOrgin1.getRightRelationField()).append("` as ").append(relationKey);

                    lookupSelectStatement.append(" from ").append(relationRealName).append(" a inner join ").append(midEntityEnName).append(" b ");

                    lookupSelectStatement.append("on a.").append(relationTableId).append(" = b.").append(relationByMiddleOrgin2.getRightRelationField());
                    lookupSelectStatement.append(" where b.").append(relationByMiddleOrgin1.getRightRelationField()).append(" in ").append(DataOperateConstants.RIGHT_TABLE);
                }

            }

            lookmap.put("statement", lookupSelectStatement.toString());
            lookupStatement.add(lookmap);

        });


        Object queryBuilderObject = null;
        try {
            queryBuilderObject = JsonPath.read(document, "$.dml.query_builder");
        } catch (Exception e) {
            log.warn("查询无query_builder");
        }
        Map<String, Object> queryBuilder = new HashMap<>();
        if (ObjectUtils.isNotEmpty(queryBuilderObject)) {
            if (queryBuilderObject instanceof Map) {
                queryBuilder = BeanUtil.beanToMap(queryBuilderObject);
            } else {
                throw new BusinessException(-3011, "query_builder格式不正确！");
            }
        }


        Object paginationObj = null;
        try {
            paginationObj = JsonPath.read(document, "$.dml.pagination");
        } catch (Exception e) {
            log.warn("查询无pagination");
        }

        Object orderObj = null;
        try {
            orderObj = JsonPath.read(document, "$.dml.order_by");
        } catch (Exception e) {
            log.warn("查询无order_by");
        }
        List order = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(orderObj)) {
            if (orderObj instanceof List) {
                order = (List) orderObj;
            } else {
                throw new BusinessException(-3011, "order_by格式不正确！");
            }
        }


        Map<String, Object> conditionMap = new HashMap<>(16);
        StringBuilder whereBuiler = new StringBuilder();
        Object whereObject = null;

        try {
            whereObject = JsonPath.read(document, "$.dml.where");
        } catch (Exception e) {
            log.warn("查询无where:========methodName:{}");
        }
        Map whereStr = new HashMap();
        if (ObjectUtils.isNotEmpty(whereObject)) {
            if (whereObject instanceof Map) {
                whereStr = (Map) whereObject;
            } else {
                throw new BusinessException(-3018, "where格式不正确！");
            }
        }

        List<Object> orArrayList = new ArrayList<>();
        String where = "";
        if (ObjectUtils.isNotEmpty(whereStr)) {
            List<String> orArray = JsonPath.read(document, "$.dml.where.or");
            orArrayList.addAll(orArray);

        }
        List<Object> queryOr = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(queryBuilder)) {
             queryOr = JsonPath.read(document, "$.dml.query_builder.or");
        }

        if (ObjectUtils.isNotEmpty(queryBuilder) || ObjectUtils.isNotEmpty(whereStr)) {
            whereBuiler.append(" where ");
        }
        for (int i = 0; i < orArrayList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            if (orArrayList.get(i) instanceof Map) {
                map = (Map<String, Object>) orArrayList.get(i);
            }
            if (orArrayList.size() > 1) {
                whereBuiler.append(" ( ");
            }
//            Map<String, Object> map = JSON.parseObject(, Map.class);
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                String key = next.getKey();
                Object value = next.getValue();
                List<HashMap<String, Object>> valueOperator = null;
                if (value instanceof ArrayList) {
                    valueOperator = (List<HashMap<String, Object>>) value;
                }
                //组装where
                joinWhereBuilder(whereBuiler, valueOperator, key);
                if (iterator.hasNext()) {
                    whereBuiler.append(" AND ");
                }
            }
            if (orArrayList.size() > 1) {
                whereBuiler.append(" ) ");
            }
            if (i != orArrayList.size() - 1) {
                whereBuiler.append(" OR ");
            }
            String whereOperatered = whereBuiler.toString();
            if (whereOperatered.endsWith(" and ")) {
//                whereOperatered.
            }
        }
        //拼接表关联
        //拼装表级关联
        if (orArrayList.size() > 0 && queryOr.size() > 0) {
            whereBuiler.append(" AND ");
        }

          for (int i = 0; i < queryOr.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            if (queryOr.get(i) instanceof Map) {
                map = (Map<String, Object>) queryOr.get(i);
            }
            if (queryOr.size() > 1) {
                whereBuiler.append(" ( ");
            }
//            Map<String, Object> map = JSON.parseObject(, Map.class);
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                String key = next.getKey();
                Object value = next.getValue();
                List<HashMap<String, Object>> valueOperator = null;
                if (value instanceof ArrayList) {
                    valueOperator = (List<HashMap<String, Object>>) value;
                }
                //组装where
                joinWhereBuilder(whereBuiler, valueOperator, key);
                if (iterator.hasNext()) {
                    whereBuiler.append(" AND ");
                }
            }
            if (queryOr.size() > 1) {
                whereBuiler.append(" ) ");
            }
            if (i != queryOr.size() - 1) {
                whereBuiler.append(" OR ");
            }
            String whereOperatered = whereBuiler.toString();
            if (whereOperatered.endsWith(" and ")) {
//                whereOperatered.
            }
        }

        selectConditionTransformXml.append(" FROM ");
        selectConditionTransformXml.append(realEnNames.get(modelUuid));
        selectConditionTransformXml.append(" ");
        //拼装where条件
        selectConditionTransformXml.append(whereBuiler);

        //构造排序
        StringBuilder orderBuilder = new StringBuilder();
        for (int i = 0; i < order.size(); i++) {
            if (i == 0) {
                orderBuilder.append(" ORDER BY ");
            }

            Object orderObject = order.get(i);
            if (orderObject instanceof Map) {
                Map orderMap = (Map) orderObject;
                orderMap.forEach((key, value) -> {
                    if (key instanceof String) {
                        String orderTableNameAndField = (String) key;
                        if (orderTableNameAndField.contains(".")) {
                            String[] splited = orderTableNameAndField.split("\\.");
                            String orderTable = splited[0];
                            String orderField = splited[1];
                            if (value instanceof Integer) {
                                Integer orderFlag = (Integer) value;
                                orderBuilder.append(orderField);
                                if (1 == orderFlag) {
                                    orderBuilder.append(" ASC");
                                } else {
                                    orderBuilder.append(" DESC");
                                }
                            }
                            if (value instanceof String) {
                                String orderFlag = (String) value;
                                orderBuilder.append(orderField);
                                if ("1".equals(orderFlag)) {
                                    orderBuilder.append(" ASC ");
                                } else {
                                    orderBuilder.append(" DESC ");
                                }
                            }
                        }

                    }
                });
                if (i != order.size() - 1) {
                    orderBuilder.append(",");
                }
            }

        }

        //构造分页
        WlosPagination wlosPagination = JSONObject.parseObject(JSON.toJSONString(paginationObj), WlosPagination.class);
        StringBuilder pagninationBuilder = new StringBuilder();
        // 如果构造分页则需要查询满足条件的结果集总数
        StringBuffer selectConditionCountXml = new StringBuffer("SELECT count(*) FROM ");
        Map<String, Object> primaryTableCount = new HashMap<>();
        if (ObjectUtils.isNotEmpty(wlosPagination)) {
            Integer current_page = wlosPagination.getCurrent_page();
            Integer page_size = wlosPagination.getPage_size();
            primaryTableCount.put("current_page", current_page);
            primaryTableCount.put("page_size", page_size);
            pagninationBuilder.append(" LIMIT ");
            if (ObjectUtils.isEmpty(current_page)) {
                throw new BusinessException(-3218, "method:分页无占位符，请确认！");
            } else {
                if (String.valueOf(current_page).startsWith("$$")) {
                    String placeHolderCurrentPage = String.valueOf(current_page).replaceAll("\\$", "");
                    pagninationBuilder.append("#?{");
                    pagninationBuilder.append(placeHolderCurrentPage);
                    pagninationBuilder.append("}");
                } else {
                    int offset;
                    try {
                        offset = (current_page - 1) * (page_size);
                        pagninationBuilder.append(offset);
                    } catch (NumberFormatException e) {
                        throw new BusinessException(-3871, "method:分页字段值格式不正确，请确认！");
                    }
                }
            }
            pagninationBuilder.append(",");
            if (ObjectUtils.isEmpty(page_size)) {
                throw new BusinessException(-3218, "method:分页无占位符，请确认！");

            } else {
                if (String.valueOf(page_size).startsWith("$$")) {
                    pagninationBuilder.append("#?{");
                    String placeHolderPageSize = String.valueOf(page_size).replaceAll("\\$", "");
                    pagninationBuilder.append(placeHolderPageSize);
                    pagninationBuilder.append("}");
                } else {
                    try {
                        pagninationBuilder.append(page_size);
                    } catch (NumberFormatException e) {
                        throw new BusinessException(-3871, "method:分页字段值格式不正确，请确认！");
                    }
                }
            }
            // 继续拼接主表查总数的语句
            selectConditionCountXml.append(realEnNames.get(modelUuid));
            selectConditionCountXml.append(whereBuiler);
        }


        selectConditionTransformXml.append(orderBuilder);
        //拼接分页
        selectConditionTransformXml.append(pagninationBuilder);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("primaryTable", selectConditionTransformXml.toString());
        resultMap.put("lookupTable", lookupStatement);
        // 如果主表需要分页则增加总数查询的语句返回
        if (MapUtil.isNotEmpty(primaryTableCount)) {
            primaryTableCount.put("primaryTableCount", selectConditionCountXml.toString());
            resultMap.put("primaryTableCount", primaryTableCount);
        }
        return resultMap;
    }


    /***
     *  where解析为hql
     * @param whereBuiler
     * @param value
     * @param operator
     */
    protected  void joinWhereBuilder(StringBuilder whereBuiler, List<HashMap<String, Object>> value, String operator) {
        Iterator<HashMap<String, Object>> conditionIterator = value.iterator();
        while (conditionIterator.hasNext()) {
            HashMap<String, Object> itemCondition = conditionIterator.next();
            Object tableNameAndWhereFieldStr = itemCondition.get("left");
            String leftType = String.valueOf(itemCondition.get("left_type"));
            if (ObjectUtils.isNotEmpty(tableNameAndWhereFieldStr)) {
                if (tableNameAndWhereFieldStr instanceof String) {
                    String tableNameAndWhereFieldOperator = String.valueOf(tableNameAndWhereFieldStr);
                    String[] tableNameAndWhereField = tableNameAndWhereFieldOperator.split("\\.");
                    String tableName = tableNameAndWhereField[0];
                    String field = tableNameAndWhereField[1];
                    String fieldWhereValue = "";
                    Object fieldWhereValueObject = itemCondition.get("right");
                    fieldWhereValue = String.valueOf(fieldWhereValueObject);
                    String relationType = "";
                    if (Objects.equals(DataOperateConstants.LOOKUP, leftType)) {
                        String relationKey = (String) itemCondition.get("relation_key");
                        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isBlank(tableName)) {
                            throw new BusinessException(-4232, "where条件lookup,relation_key为空,请确认！");
                        }
                        if(StringUtils.isBlank(relationKey)){
                            throw new BusinessException(-4232, "where条件lookup,relation_key为空,请确认！");
                        }
                        EntityRelation relationByRelationKey = getRelationByRelationKey(relationKey);
                        if(ObjectUtils.isEmpty(relationByRelationKey)){
                            throw new BusinessException(-4232, "where条件lookup关系不存在,请确认！");
                        }
                        WlosLookupVO wlosLookupVO = new WlosLookupVO();
                        wlosLookupVO.setTable_uuid(tableName);
                        wlosLookupVO.setRelationKey(relationKey);
                        WlosLookupVO.LookupRelation lookupRelation = new WlosLookupVO.LookupRelation();
                        String leftEntityUuid = relationByRelationKey.getLeftEntityUuid();
                        String rightEntityUuid = relationByRelationKey.getRightEntityUuid();
                        relationType = relationByRelationKey.getType();
                        lookupRelation.setRelationKey(relationKey);
                        lookupRelation.setLeftRelationField(relationByRelationKey.getLeftRelationField());
                        lookupRelation.setRightRelationField(relationByRelationKey.getRightRelationField());
                        lookupRelation.setType(relationByRelationKey.getType());
                        lookupRelation.setLeftEntityUuid(relationByRelationKey.getLeftEntityUuid());
                        lookupRelation.setRightEntityUuid(relationByRelationKey.getRightEntityUuid());
                        wlosLookupVO.setLookup_relation(lookupRelation);


                        switch (relationType) {
                            case "1":
                                if (rightEntityUuid.equals(tableName)) {
                                    whereBuiler.append(primarykeys.get(leftEntityUuid)).append(" IN (select ").append(relationKey).append(" from ").append(realEnNames.get(tableName));
                                    whereBuiler.append(" where ");
                                } else {
                                    whereBuiler.append(relationKey).append(" IN (select ").append(primarykeys.get(tableName)).append(" from ").append(realEnNames.get(tableName));
                                    whereBuiler.append(" where ");
                                }
                                break;
                            case "2":
                                if (rightEntityUuid.equals(tableName)) {
                                    whereBuiler.append(primarykeys.get(leftEntityUuid)).append(" IN (select ").append(relationKey).append(" from ").append(realEnNames.get(tableName));
                                    whereBuiler.append(" where ");
                                } else {
                                    whereBuiler.append(relationKey).append(" IN (select ").append(primarykeys.get(tableName)).append(" from ").append(realEnNames.get(tableName));
                                    whereBuiler.append(" where ");
                                }
                                break;
                            case "3":
                                if (leftEntityUuid.equals(tableName)) {
                                    whereBuiler.append(primarykeys.get(rightEntityUuid)).append(" IN (select ").append(relationKey).append(" from ").append(realEnNames.get(tableName));
                                    whereBuiler.append(" where ");
                                } else {
                                    whereBuiler.append(relationKey).append(" IN (select ").append(primarykeys.get(tableName)).append(" from ").append(realEnNames.get(tableName));
                                    whereBuiler.append(" where ");
                                }
                                break;
                            case "4":
                               
                                //  select *,`yvkn_member_id` as id  FROM yvkn_member  WHERE yvkn_vzhi in(select * FROM yvkn_nmqr WHERE yvkn_exch )
                                if (StringUtils.isBlank(relationKey)) {
                                    throw new BusinessException("多对多中间表不存在!");
                                }
                                EntityRelation relationByMiddleOrgin1 = getRelationByMiddleOrgin(leftEntityUuid, relationKey, relationKey);
                                EntityRelation relationByMiddleOrgin2 = getRelationByMiddleOrgin(rightEntityUuid, relationKey, relationKey);
                                if ((ObjectUtils.isEmpty(relationByMiddleOrgin1)) || (ObjectUtils.isEmpty(relationByMiddleOrgin2))) {
                                    throw new BusinessException("多对多关系实体与中间表关系不存在!");
                                }
                                String leftPrimaryKey = getPrimaryKey(leftEntityUuid);
                                String rightPrimaryKey = getPrimaryKey(rightEntityUuid);
                                if (leftEntityUuid.equals(tableName)) {
                                    whereBuiler.append(rightPrimaryKey).append(" IN (select ").append(relationByMiddleOrgin2.getRightRelationField()).append(" from ").append(relationKey);
                                    whereBuiler.append(" where ").append(relationByMiddleOrgin1.getRightRelationField()).append(" ");

                                } else {
                                    whereBuiler.append(leftPrimaryKey).append(" IN (select ").append(relationByMiddleOrgin1.getRightRelationField()).append(" from ").append(relationKey);
                                    whereBuiler.append(" where ").append(relationByMiddleOrgin2.getRightRelationField()).append(" ");
                                }
                                break;
                        }

                    }


                    //删除语句不支持别名，此处需处理
                    if (StringUtils.isNotBlank(relationType) && Constants.MANY_TO_MANY.equals(relationType)){

                    }else {
                        whereBuiler.append(field);
                    }
                    switch (operator) {
                        case DataOperateConstants.EQ:
                            whereBuiler.append(" = ");
                            whereBuiler.append("'");
                            whereBuiler.append(fieldWhereValue);
                            whereBuiler.append("'");

                            break;
                        case DataOperateConstants.NE:
                            whereBuiler.append(" != ");
                            whereBuiler.append("'");

                            whereBuiler.append(fieldWhereValue);
                            whereBuiler.append("'");

                            break;
                        case DataOperateConstants.GTE:
                            whereBuiler.append(" >= ");
                            whereBuiler.append("'");

                            whereBuiler.append(fieldWhereValue);
                            whereBuiler.append("'");

                            break;
                        case DataOperateConstants.GT:
                            whereBuiler.append(" > ");
                            whereBuiler.append("'");

                            whereBuiler.append(fieldWhereValue);
                            whereBuiler.append("'");

                            break;
                        case DataOperateConstants.LTE:
                            whereBuiler.append(" <= ");
                            whereBuiler.append("'");

                            whereBuiler.append(fieldWhereValue);
                            whereBuiler.append("'");

                            break;
                        case DataOperateConstants.LT:
                            whereBuiler.append(" < ");
                            whereBuiler.append("'");

                            whereBuiler.append(fieldWhereValue);
                            whereBuiler.append("'");

                            break;
                        case DataOperateConstants.NONE:
                            whereBuiler.append(" IS NULL ");
                            break;
                        case DataOperateConstants.NOT_NONE:
                            whereBuiler.append(" IS NOT NULL ");
                            break;
                        case DataOperateConstants.START_WITH:
                            if (fieldWhereValue instanceof String) {
                                whereBuiler.append(" LIKE  CONCAT('");
                                whereBuiler.append(fieldWhereValue);
                                whereBuiler.append("',");
                                whereBuiler.append("'%') ");
                            }
                            break;
                        case DataOperateConstants.END_WITH:
                            if (fieldWhereValue instanceof String) {
                                whereBuiler.append(" LIKE CONCAT(");
                                whereBuiler.append("'%','");

                                whereBuiler.append(fieldWhereValue).append("')");
                            }
                            break;
                        case DataOperateConstants.INCLUDE:
                            if (fieldWhereValue instanceof String) {
                                whereBuiler.append(" LIKE CONCAT(");
                                whereBuiler.append("'%','");

                                whereBuiler.append(fieldWhereValue).append("',");
                                whereBuiler.append("'%') ");
                            }
                            break;
                        case DataOperateConstants.NOT_INCLUDE:
                            if (fieldWhereValue instanceof String) {
                                whereBuiler.append(" NOT LIKE CONCAT(");
                                whereBuiler.append("'%','");
                                whereBuiler.append(fieldWhereValue).append("',");
                                whereBuiler.append("'%') ");
                            }
                            break;
                        case DataOperateConstants.IN:
                            whereBuiler.append(" IN ");
                            whereBuiler.append("(");
                            if (fieldWhereValueObject instanceof ArrayList) {
                                List<String> listValue = (ArrayList) fieldWhereValueObject;
                                String join = StringUtils.join(listValue.toArray(), ",");
                                whereBuiler.append(join).append(")");
                            }

                            break;
                        case DataOperateConstants.NOT_IN:
                            whereBuiler.append(" NOT IN ");
                            whereBuiler.append("(");
                            if (fieldWhereValueObject instanceof ArrayList) {
                                List<String> listValue = (ArrayList) fieldWhereValueObject;
                                String join = StringUtils.join(listValue.toArray(), ",");
                                whereBuiler.append(join).append(")");
                            } else {
                                throw new BusinessException(-3251, "where in 条件类型不匹配");
                            }
                            break;

                        default:
                            whereBuiler.append(" = ");
                            whereBuiler.append("'");
                            whereBuiler.append(fieldWhereValue);
                            whereBuiler.append("'");

                            break;

                    }
                    if (DataOperateConstants.LOOKUP.equals(leftType)) {
                        whereBuiler.append(")");
                    }
                    if (conditionIterator.hasNext()) {
                        whereBuiler.append(" AND ");
                    }
                }

            }
        }
    }

    /***
     * 按 某字段进行过滤
     * @param keyExtractor 字段lambda
     * @param <T> 过后函数
     * @return
     */
    protected static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


    /***
     * 首字母转大写
     * @param modelUuid 字段名称
     * @return 首字母大写
     */
    private static String camelToFirstWordUpper(String modelUuid) {
        char[] chars = modelUuid.toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    }


    public void fillEntityData(EntityField wlosEntityField, Entity wlosEntity, JSONObject insertData, Map<String, Object> map) throws ParseException, ScriptException {
        // 对于mysql数据库生成插入数据，实体为系统实体并且字段为用户自定义字段时不处理
        if ("uuid".equalsIgnoreCase(wlosEntityField.getEnName())) {
            String uuid = IdUtil.objectId();
            map.put(wlosEntityField.getEnName(), uuid);
        }
        if ("id".equalsIgnoreCase(wlosEntityField.getEnName()) && (!wlosEntityField.getBaseType().contains("int"))) {
            String uuid = IdUtil.objectId();
            map.put(wlosEntityField.getEnName(), uuid);
        }
        if (DataOperateConstants.PRIMARYKEY.equals(wlosEntityField.getType()) && (!wlosEntityField.getBaseType().contains("int"))) {
            String uuid = IdUtil.objectId();
            map.put(wlosEntityField.getEnName(), uuid);
        }
        if ("Uuid".equals(wlosEntityField.getType())) {
            map.put(wlosEntityField.getEnName(), IdUtil.objectId());
        }
        List<Object> dateField = Arrays.asList(DataOperateConstants.DATE_TIME, DataOperateConstants.DATE, DataOperateConstants.TIME, DataOperateConstants.YEAR);

        // 处理公式字段的写入
        if (DataOperateConstants.FORMULA.equals(wlosEntityField.getType())) {
            FormulaSetting queryWlosFormulaSetting = new FormulaSetting();
            queryWlosFormulaSetting.setProjectUuid(wlosEntity.getProjectUuid());
            queryWlosFormulaSetting.setEntityUuid(wlosEntity.getUuid());
            queryWlosFormulaSetting.setEnName(wlosEntityField.getEnName());
            FormulaSetting wlosFormulaSetting = formulaSettingMapper.selectOne(new QueryWrapper<>(queryWlosFormulaSetting));
            if (null != wlosFormulaSetting) {
                String params = wlosFormulaSetting.getParameters();
                if (StringUtils.isBlank(params)) {
                    throw new BusinessException("公式字段异常，参数列表为空!");
                }
                String[] paramList = params.split(",");
                String rule = wlosFormulaSetting.getRule();
                Map<String, Object> bindings = new HashMap<>();
                for (String paramValue : paramList) {
                    bindings.put(paramValue, insertData.get(paramValue));
                }
                Object evalValue = null;
                try {
                    evalValue = calculateFormula(bindings, rule);
                } catch (ScriptException e) {
                    throw new BusinessException("公式字段计算失败，请检查规则及参数！");
                }
                if (ObjectUtils.isNotEmpty(evalValue) && evalValue instanceof Double) {
                    if (Double.isInfinite(Double.valueOf(String.valueOf(evalValue)))) {
                        evalValue = 0;
                    }
                    if (Double.isNaN(Double.valueOf(String.valueOf(evalValue)))) {
                        evalValue = 0;
                    }
                }
                map.put(wlosEntityField.getEnName(), evalValue);
            }
        }

        if (wlosEntityField.getType().equals("Option")) {
            Object value = insertData.get(wlosEntityField.getEnName());
            // 选项集
            if (ObjectUtils.isNotEmpty(value)) {
                String[] valueList = value.toString().split(",");
                List<String> valueIds = Arrays.asList(valueList);
                List<OptionData> optionData = optionDataMapper.selectBatchIds(valueIds);
                if (ObjectUtils.isNotEmpty(optionData)) {
                    OptionData wlosOptionData1 = optionData.get(0);
                    Entity entityQuery = new Entity();
                    entityQuery.setEnName(wlosOptionData1.getOptionUuid());
                    Entity wlosEntity3 = entityMapper.selectOne(new QueryWrapper<>(entityQuery));
                    optionData.forEach(entry -> entry.setDisplayField(wlosEntity3.getDisplayField()));
                }
                Map<String, Object> mapDetail = new HashMap<>();
                mapDetail.put("option_uuids", valueList);
                mapDetail.put("fields", optionData);
                map.put(wlosEntityField.getEnName(), mapDetail);
            }
        }

        Object val = insertData.get(wlosEntityField.getEnName());
        if (null != val && !"".equals(val)) {
            if (dateField.contains(wlosEntityField.getType())) {
                if (!wlosEntity.getDbEngine().equals("mysql")) {
                    Integer dateFormat = wlosEntityField.getDateFormat();
                    SimpleDateFormat sdf = new SimpleDateFormat(dateForamtMap.get(dateFormat));
                    Date newVal = sdf.parse((String) val);
                    map.put(wlosEntityField.getEnName(), sdf.format(newVal));
                } else {
                    map.put(wlosEntityField.getEnName(), val);
                }
            } else if (Arrays.asList(DataOperateConstants.DOUBLE, DataOperateConstants.DECIMAL, DataOperateConstants.AMOUNT, DataOperateConstants.PERCENTAGE).stream().anyMatch(it -> it.equals(wlosEntityField.getType()))) {
                Double newVal = Double.valueOf(String.valueOf(val));
                map.put(wlosEntityField.getEnName(), newVal);
            } else if (Arrays.asList(DataOperateConstants.INTEGER, DataOperateConstants.LONG).stream().anyMatch(it -> it.equals(wlosEntityField.getType()))) {
                map.put(wlosEntityField.getEnName(), NumberUtil.parseInt(String.valueOf(val)));
            } else if (DataOperateConstants.BOOLEAN.equals(wlosEntityField.getType())) {
                if (val.equals("true") || val.equals("false")) {
                    Boolean newVal = booleanFormatMap.get(val);
                    map.put(wlosEntityField.getEnName(), newVal);
                } else {
                    map.put(wlosEntityField.getEnName(), val);
                }
            } else if (DataOperateConstants.STRING.equals(wlosEntityField.getType())) {
                String newVal = val.toString();
                // 对字符串的插入长度做限制
                if (ObjectUtils.isNotEmpty(wlosEntityField.getLength()) && (newVal.length() > Integer.parseInt(wlosEntityField.getLength()))) {
                    log.error("长度超过设置的最大值!{},{}", wlosEntityField.getEnName(), newVal);
                    throw new BusinessException("字符串类型字段:[" + wlosEntityField.getCnName() + "]插入值的长度超过设置的最大值!");
                }
                map.put(wlosEntityField.getEnName(), newVal);
            } else if (DataOperateConstants.JSON.equals(wlosEntityField.getType())) {
                String usageValue = wlosEntityField.getUsageValue();
                List<EntityField> jsonFields = JSONArray.parseArray(usageValue, EntityField.class);
                if (ObjectUtils.isNotEmpty(jsonFields)) {
                    Map<String, EntityField> wlosEntityFieldMap = new HashMap<>();
                    for (EntityField wlosEntityField1 : jsonFields) {
                        wlosEntityFieldMap.put(wlosEntityField1.getEnName(), wlosEntityField1);
                    }
                    fullFillData((JSONObject) val, wlosEntityFieldMap);
                    map.put(wlosEntityField.getEnName(), JSONObject.toJSONString(val));
                } else {
                    map.put(wlosEntityField.getEnName(), "");
                }

            } else if (DataOperateConstants.OPTION.equals(wlosEntityField.getType())) {
                // 兼容：如果字段类型是静态实体，但是传来的值是一个map，此时从map中找出option_uuids的值
                if (val instanceof Map) {
                    String realVal = "";
                    Object option_uuids = ((Map<?, ?>) val).get("option_uuids");
                    List<String> optionUuids = new ArrayList<>();
                    if (ObjectUtils.isNotEmpty(option_uuids)) {
                        if (option_uuids instanceof JSONArray) {
                            optionUuids = JSONArray.parseArray(JSON.toJSONString(option_uuids), String.class);
                        } else {
                            List<String> asList = Arrays.asList(option_uuids.toString().split(","));
                            optionUuids.addAll(asList);
                        }
                    }
                    if (ObjectUtils.isNotEmpty(optionUuids)) {
                        realVal = String.join(",", optionUuids);
                    }
                    map.put(wlosEntityField.getEnName(), realVal);
                } else {
                    map.put(wlosEntityField.getEnName(), val);
                }
            } else {
                map.put(wlosEntityField.getEnName(), val);
            }
        } else {
            //字段无值时,需处理默认值
            if (ObjectUtils.isNotEmpty(wlosEntityField) && !map.containsKey(wlosEntityField.getEnName())) {
                Object defaultValue = wlosEntityField.getDefaultValue();
                List<Object> defaultValueList = null;
                String join = "";
                if (ObjectUtils.isNotEmpty(defaultValue)) {
                    String defaultValueStr = defaultValue.toString();
                    if (defaultValueStr.startsWith("[")) {
                        defaultValueList = JSONArray.parseArray(defaultValueStr, Object.class);
                    }
                    String type = wlosEntityField.getType();
                    if (DataOperateConstants.OPTION.equals(type)) {
                        join = StringUtils.join(defaultValueList.toArray(), ",");
                        map.put(wlosEntityField.getEnName(), join);
                    } else if (dateField.contains(type)) {
                        Integer dateFormat = wlosEntityField.getDateFormat();
                        SimpleDateFormat sdf = new SimpleDateFormat(dateForamtMap.get(dateFormat));
                        Date newVal = null;
                        try {
                            newVal = sdf.parse(defaultValueStr);
                        } catch (ParseException e) {
                            log.error("时间类型值错误", e);
                            throw new BusinessException("时间类型默认值格式不正确");
                        }
                        map.put(wlosEntityField.getEnName(), sdf.format(newVal));
                    } else {
                        map.put(wlosEntityField.getEnName(), null == defaultValueList ? defaultValue : defaultValueStr);
                    }
                } else {
                    if (dateField.contains(wlosEntityField.getType())) {
                        Integer fillMethd = wlosEntityField.getDateFillMethod();
                        if (null != fillMethd && fillMethd.equals(1)) {
                            SimpleDateFormat simpleDateFormat;
                            if (DataOperateConstants.YEAR.equals(wlosEntityField.getType())) {
                                simpleDateFormat = new SimpleDateFormat("yyyy");
                            } else {
                                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            }
                            map.put(wlosEntityField.getEnName(), simpleDateFormat.format(new Date()));
                        }
                    } else {
                        if ((null != wlosEntityField.getIsNull()) && (wlosEntityField.getIsNull().equals(0)) && (!wlosEntityField.getType().equals(DataOperateConstants.PRIMARYKEY))) {
                            throw new BusinessException("字段 {" + wlosEntityField.getCnName() + "} 的值不能为空");
                        }
                    }
                }
            }


            // 这里为实体中可能存在的自动编号字段赋值
            List<Map<String, Object>> maps = new ArrayList<>();
            maps.add(map);
            List<Map<String, List<String>>> autoNumber = autoincColumnSettingService.genNumber(wlosEntity.getUuid(), maps);
            if (autoNumber.size() > 0) {
                for (Map<String, List<String>> autoValue : autoNumber) {
                    for (Map.Entry<String, List<String>> value : autoValue.entrySet()) {
                        map.put(value.getKey(), value.getValue().get(0));
                    }
                }
            }
        }
    }


    public void fullFillData(Map<String, Object> insertData, Map<String, EntityField> wlosEntityFieldMap) {
        for (String enName : wlosEntityFieldMap.keySet()) {
            EntityField wlosEntityField = wlosEntityFieldMap.get(enName);
            Object value = insertData.get(enName);
            // 如果不存在的话有默认值咋需要填充默认值
            if (ObjectUtils.isEmpty(value) && ObjectUtils.isNotEmpty(wlosEntityField)) {
                Object defaultValue = wlosEntityField.getDefaultValue();
                if (ObjectUtils.isNotEmpty(defaultValue)) {
                    List<Object> defaultValueList = null;
                    String defaultValueStr = defaultValue.toString();
                    if (defaultValueStr.startsWith("[")) {
                        defaultValueList = JSONArray.parseArray(defaultValueStr, Object.class);
                    }
                    insertData.put(enName, null == defaultValueList ? defaultValue : defaultValueList);
                } else {
                    // 对于日期类型的如果要求添加当前时间，则添加当前时间
                    if (Arrays.asList(DataOperateConstants.DATE_TIME, DataOperateConstants.DATE, DataOperateConstants.TIME, DataOperateConstants.YEAR).stream().anyMatch(it -> it.equals(wlosEntityField.getType()))) {
                        Integer fillMethd = wlosEntityField.getDateFillMethod();
                        if (null != fillMethd && fillMethd.equals(1)) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            insertData.put(enName, simpleDateFormat.format(new Date()));
                        }
                    }
                }
            }
        }
    }

    private static Map<Integer, String> dateForamtMap = new HashMap<>();
    private static Map<Object, Boolean> booleanFormatMap = new HashMap<>();

    @Autowired
    private OptionDataMapper optionDataMapper;

    @Resource
    private EntityMapper entityMapper;
    @Resource
    private EntityFieldMapper entityFieldMapper;

    @Resource
    private EntityRelationMapper entityRelationEntityMapper;

    @Resource
    private AutoincFieldSettingService autoincColumnSettingService;
    @Resource
    private FormulaSettingMapper formulaSettingMapper;

    static {
        dateForamtMap.put(1, "yyyy");
        dateForamtMap.put(2, "yyyy-MM");
        dateForamtMap.put(3, "yyyy-MM-dd");
        dateForamtMap.put(4, "yyyy-MM-dd HH:mm:ss");
        dateForamtMap.put(5, "yyyy-dd");
        dateForamtMap.put(6, "yyyy/MM");
        dateForamtMap.put(7, "yyyy/MM/dd");
        dateForamtMap.put(8, "yyyy/dd");
        dateForamtMap.put(9, "HH:mm:ss");
        dateForamtMap.put(10, "HH:mm");
        dateForamtMap.put(11, "yyyy-MM-dd HH:mm");
        dateForamtMap.put(12, "yyyy/MM/dd HH:mm:ss");
        dateForamtMap.put(13, "yyyy/MM/dd HH:mm");
        booleanFormatMap.put("true", true);
        booleanFormatMap.put(true, true);
        booleanFormatMap.put("false", false);
        booleanFormatMap.put(false, false);
    }


    public void transferData(Map<String, Object> updateDict, List<EntityField> wlosEntityFields) {
        // 实体字段信息转为map存储
        Map<String, EntityField> wlosEntityFieldMap = new HashMap<>();
        for (EntityField wlosEntityField : wlosEntityFields) {
            wlosEntityFieldMap.put(wlosEntityField.getEnName(), wlosEntityField);
        }
        for (Map.Entry<String, Object> data : updateDict.entrySet()) {
            EntityField wlosEntityField = wlosEntityFieldMap.get(data.getKey());
            if (ObjectUtils.isEmpty(wlosEntityField)) {
                throw new BusinessException(-3416, "[" + data.getKey() + "]字段不存在");
            }
            // 如果值不存在则先根据是否有默认值进行填充
            if (null == data.getValue()) {
                Object defaultValue = wlosEntityField.getDefaultValue();
                if (ObjectUtils.isNotEmpty(defaultValue)) {
                    List<Object> defaultValueList = null;
                    String defaultValueStr = defaultValue.toString();
                    if (defaultValueStr.startsWith("[")) {
                        defaultValueList = JSONArray.parseArray(defaultValueStr, Object.class);
                    }
                    String type = wlosEntityField.getType();
                    if (DataOperateConstants.OPTION.equals(type)) {
                        String join = StringUtils.join(defaultValueList.toArray(), ",");
                        data.setValue(join);
                    } else {
                        data.setValue(null == defaultValueList ? defaultValue : defaultValueStr);
                    }
                } else {
                    // 对于日期类型的如果要求添加当前时间，则添加当前时间
                    if (Arrays.asList(DataOperateConstants.DATE_TIME, DataOperateConstants.DATE, DataOperateConstants.TIME, DataOperateConstants.YEAR).stream().anyMatch(it -> it.equals(wlosEntityField.getType()))) {
                        Integer fillMethd = wlosEntityField.getDateFillMethod();
                        if (null != fillMethd && fillMethd.equals(1)) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            data.setValue(simpleDateFormat.format(new Date()));
                        }
                    }
                }
            }
            // 对于需要json化的字段进行json化
            if (wlosEntityField.getType().equals(DataOperateConstants.JSON)) {
                String usageValue = wlosEntityField.getUsageValue();
                List<EntityField> jsonFields = JSONArray.parseArray(usageValue, EntityField.class);
                if (ObjectUtils.isNotEmpty(data.getValue())) {
                    transferData((Map<String, Object>) data.getValue(), jsonFields);
                }
                data.setValue(JSONObject.toJSONString(data.getValue()));
            }
            // 对于布尔值把 "true"，"false"转为"1","0"
            if (wlosEntityField.getType().equals(DataOperateConstants.BOOLEAN)) {
                if ("true".equals(data.getValue())) {
                    data.setValue(1);
                } else if ("false".equals(data.getValue())) {
                    data.setValue(0);
                }
            }
            // 兼容：如果字段类型是静态实体，但是传来的值是一个map，此时从map中找出option_uuids的值
            if ((wlosEntityField.getType().equals(DataOperateConstants.OPTION)) && (data.getValue() instanceof Map)) {
                String realVal = "";
                List<String> optionUuids = (List<String>) ((Map<?, ?>) data.getValue()).get("option_uuids");
                if (ObjectUtils.isNotEmpty(optionUuids)) {
                    realVal = String.join(",", optionUuids);
                }
                data.setValue(realVal);
            }
        }
    }


    public String getPrimaryKey(String entityEnName) {
        EntityField wlosEntityField = new EntityField();
        wlosEntityField.setEntityEnName(entityEnName);
        wlosEntityField.setType(DataOperateConstants.PRIMARYKEY);
        EntityField wlosEntityField1 = entityFieldMapper.selectOne(new QueryWrapper<>(wlosEntityField));
        if (ObjectUtils.isEmpty(wlosEntityField1)) {
            throw new BusinessException("实体主键不存在!");
        }
        String primaryKey = wlosEntityField1.getEnName();
        return primaryKey;
    }


    public List<Map<String, Object>> coverDataToNewData(String primaryKey, List<Map<String, Object>> resultData, List<EntityField> wlosEntityFields) {
        if (ObjectUtils.isEmpty(resultData)) {
            return new ArrayList<>();
        }
        Map<String, EntityField> wlosEntityFieldMap = new HashMap<>();
        for (EntityField wlosEntityField : wlosEntityFields) {
            wlosEntityFieldMap.put(wlosEntityField.getEnName(), wlosEntityField);
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        Set<String> newField = new HashSet<>();
        // 获取当前实体的主键
        //查询
        List<String> lists = Arrays.asList(DataOperateConstants.DATE_TYPE, DataOperateConstants.DATETIME_TYPE, DataOperateConstants.TIMESTAMP_TYPE, DataOperateConstants.TIME_TYPE);
        List<EntityField> dateFormat = wlosEntityFields.stream().filter(item -> lists.contains(item.getBaseType())).collect(Collectors.toList());
        List<EntityField> optionFormat = wlosEntityFields.stream().filter(item -> DataOperateConstants.OPTION.equals(item.getType())).collect(Collectors.toList());
        List<String> optionFormatKeyList = optionFormat.stream().map(item -> item.getEnName()).collect(Collectors.toList());
        Set<String> existsFields = new HashSet<>();
        Map<String, Integer> dateFormatMap = new HashMap<>();
        for (EntityField wlosEntityField : dateFormat) {
            dateFormatMap.put(wlosEntityField.getEnName(), wlosEntityField.getDateFormat());
        }
        resultData.forEach(item -> {
            Map<String, Object> newData = new HashMap<>();
            item.forEach((key, value) -> {
                existsFields.add(key);
                EntityField keyField = wlosEntityFieldMap.get(key);
                if (ObjectUtils.isNotEmpty(keyField)) {


                    if (value instanceof BigDecimal || value instanceof Float || value instanceof Double) {
                        if (ObjectUtils.isNotEmpty(keyField) && null != keyField.getDecimalNumber() && keyField.getDecimalNumber() > 0) {
                            // 取保留小数
                            BigDecimal bd = new BigDecimal(String.valueOf(value)).setScale(keyField.getDecimalNumber());
                            newData.put(key, bd);
                        } else {
                            newData.put(key, value);
                        }
                    } else if (value instanceof Date) {
                        // 日期处理
                        Integer dateFormatValue = dateFormatMap.get(key);
                        if (null == dateFormatValue) {
                            dateFormatValue = 4;
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat(dateForamtMap.get(dateFormatValue));
                        String transferDate = sdf.format(value);
                        newData.put(key, transferDate);
                    } else if (ObjectUtils.isNotEmpty(optionFormatKeyList) && optionFormatKeyList.contains(key)) {
                        // 选项集
                        if (null != value) {
                            String[] valueList = value.toString().split(",");
                            List<String> valueIds = Arrays.asList(valueList);
                            List<OptionData> wlosOptionData = optionDataMapper.selectBatchIds(valueIds);
                            if (ObjectUtils.isNotEmpty(wlosOptionData)) {
                                OptionData wlosOptionData1 = wlosOptionData.get(0);

                                Entity entityQuery = new Entity();
                                entityQuery.setEnName(wlosOptionData1.getOptionUuid());
                                Entity wlosEntity = entityMapper.selectOne(new QueryWrapper<>(entityQuery));
                                wlosOptionData.forEach(entry -> entry.setDisplayField(wlosEntity.getDisplayField()));
                            }

                            Map<String, Object> map = new HashMap<>();
                            map.put("option_uuids", valueList);
                            map.put("fields", wlosOptionData);
                            newData.put(key, map);
                        } else {
                            newData.put(key, value);
                        }
                    } else if (ObjectUtils.isNotEmpty(keyField) && DataOperateConstants.JSON.equals(keyField.getType())) {
                        JSONObject jsonValue = JSONObject.parseObject(String.valueOf(value));
                        if (ObjectUtils.isNotEmpty(jsonValue)) {
                            String usageValue = keyField.getUsageValue();
                            List<EntityField> jsonFields = JSONArray.parseArray(usageValue, EntityField.class);
                            Map<String, EntityField> jsonFieldsMap = new HashMap<>();
                            for (EntityField wlosEntityField : jsonFields) {
                                jsonFieldsMap.put(wlosEntityField.getEnName(), wlosEntityField);
                            }
                            for (String jsonKey : jsonValue.keySet()) {
                                EntityField wlosEntityField = jsonFieldsMap.get(jsonKey);
                                if (ObjectUtils.isNotEmpty(wlosEntityField)) {
                                    if (DataOperateConstants.BOOLEAN.equals(wlosEntityField.getType())) {
                                        if (String.valueOf(jsonValue.get(jsonKey)).equals("1")) {
                                            jsonValue.put(jsonKey, "true");
                                        } else {
                                            jsonValue.put(jsonKey, "false");
                                        }
                                    }
                                }
                            }
                        }
                        newData.put(key, jsonValue);
                    } else if (DataOperateConstants.BOOLEAN.equals(keyField.getType())) {
                        if (String.valueOf(value).equals("1")) {
                            newData.put(key, "true");
                        } else {
                            newData.put(key, "false");
                        }
                    } else {
                        newData.put(key, value);
                    }
                }
            });
            newData.put("id", String.valueOf(item.get(primaryKey)));
            List<String> newFields = newField.stream().filter(ite -> !existsFields.contains(ite)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(newFields)) {
                newFields.forEach(itemField -> {
                    newData.put(itemField, "");
                });
            }
            resultList.add(newData);
        });
        return resultList;
    }


    public void insertDataByMysql(String entityEnName, List<Map<String, Object>> insertData) {
        if (insertData.size() > 0) {
            // 获得插入字段的顺序
            List<String> columnList = new ArrayList<>();
            List<String> insertColumnList = new ArrayList<>();
            insertData.get(0).forEach((key, value) -> {
                columnList.add(key);
                insertColumnList.add("`" + key + "`");
            });
            // 拼接值得到需要格式
            Set<String> excludeColum = new HashSet<>();
            StringBuffer values = new StringBuffer();
            for (int k = 0; k < insertData.size(); k++) {
                StringBuffer singleValue = new StringBuffer("(");
                Map<String, Object> singleData = insertData.get(k);
                for (int i = 0; i < columnList.size(); i++) {
                    Object columnValue = singleData.get(columnList.get(i));
                    if (ObjectUtils.isEmpty(columnValue)) {
                        excludeColum.add("`" + columnList.get(i) + "`");
                        continue;
                    }
                    if (columnValue instanceof String) {
                        if (((String) columnValue).contains("'")) {
                            columnValue = ((String) columnValue).replace("'", "\\'");
                        }
                        singleValue.append("'").append(columnValue).append("'");
                    } else if (columnValue instanceof Timestamp) {
                        singleValue.append("'").append(columnValue).append("'");
                    } else if (columnValue instanceof Date) {
                        singleValue.append("'").append(columnValue).append("'");
                    } else {
                        singleValue.append(columnValue);
                    }
                    if (i != columnList.size() - 1) {
                        singleValue.append(",");
                    }
                }
                singleValue.append(")");
                if (k != insertData.size() - 1) {
                    singleValue.append(",");
                }
                values.append(singleValue);
            }
            insertColumnList.removeAll(excludeColum);
            String columns = StringUtils.join(insertColumnList, ",");
            // 生成mysql插入语句
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO " + "`" + entityEnName + "`" + "(" + columns + ") VALUES ");
            sql.append(values);
            log.info("生成的SQL插入语句:{}", JSON.toJSONString(sql));
            int update = jdbcTemplate.update(sql.toString());
            log.info("生成的SQL插入语句影响树：{}", update);

            // 根据projectUuid获取数据库的配置信息
        }
    }


    /**
     * 计算公式得出结果
     *
     * @param bindings
     * @param rule
     * @return
     * @throws ScriptException
     */
    public Object calculateFormula(Map<String, Object> bindings, String rule) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        Object evalValue = engine.eval(rule, new SimpleBindings(bindings));
        return evalValue;
    }


    public void insertBatch(String entityEnName, EntityRelation wlosEntityRelation, List<Map<String, Object>> insertData) {
        String leftEntityEnName = wlosEntityRelation.getLeftEntityUuid();
        String rightEntityEnName = wlosEntityRelation.getRightEntityUuid();
        String relationType = wlosEntityRelation.getType();
        Entity leftEntity = getEntityByEnName(leftEntityEnName);
        Entity rightEntity = getEntityByEnName(rightEntityEnName);
        Assert.notNull(leftEntity, "[" + wlosEntityRelation.getRelationKey() + "]实体不存在");
        Assert.notNull(rightEntity, "[" + wlosEntityRelation.getRelationKey() + "]实体不存在");
        Assert.hasText(leftEntityEnName, "[" + wlosEntityRelation.getRelationKey() + "]左关联表名为空");
        Assert.notNull(rightEntityEnName, "[" + wlosEntityRelation.getRelationKey() + "]右关联表名为空");

        // 确定两个实体的主键id
        // 获取该实体的主键
        String leftPrimaryKey = getPrimaryKey(leftEntityEnName);
        String rightPrimaryKey = getPrimaryKey(rightEntityEnName);
        Assert.hasText(leftPrimaryKey, "[" + leftEntityEnName + "]实体不存在");
        Assert.hasText(rightPrimaryKey, "[" + rightEntityEnName + "]实体不存在");

        Iterator<Map<String, Object>> iterator = insertData.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> data = iterator.next();
            List<String> relationValues = new ArrayList<>();
            Object value = data.get(wlosEntityRelation.getRelationKey());
            if(ObjectUtils.isEmpty(value)){
                continue;
            }
            if (value instanceof JSONArray) {
                relationValues = JSONArray.parseArray(JSON.toJSONString(value), String.class);
            } else {
                List<String> asList = Arrays.asList(value.toString().split(","));
                relationValues.addAll(asList);
            }
            switch (relationType) {
                case "1":
                    if (leftEntityEnName.equals(entityEnName)) {
                        data.remove(wlosEntityRelation.getRelationKey());
                        if (ObjectUtils.isNotEmpty(relationValues)) {
                            // 获取主键
                            String entityUuidValue = String.valueOf(data.get(leftPrimaryKey));
                            // 执行update语句，建立关系
                            Map<String, Object> searchDict = new HashMap<>();
                            searchDict.put(rightPrimaryKey, relationValues);
                            Assert.hasText(entityUuidValue, "新增实体主表主键为空");
                            String columnsValue = wlosEntityRelation.getRightRelationField() + "=" + "'" + entityUuidValue + "'";
                            String sql = update(rightEntity, columnsValue, searchDict);
                            log.info("更新关系拼接的语句:{}", sql);
                            // 执行更新语句
                            // 根据projectUuid获取数据库的配置信息
                            int update = jdbcTemplate.update(sql);
                        }
                    } else {
                        if (ObjectUtils.isNotEmpty(relationValues)) {
                            // 执行update语句，建立关系
                            Map<String, Object> searchDict = new HashMap<>();
                            searchDict.put(wlosEntityRelation.getRelationKey(), relationValues.get(0));
                            String columnsValue = wlosEntityRelation.getRelationKey() + "=" + "'" + "'";
                            String sql = update(rightEntity, columnsValue, searchDict);
                            log.info("更新一对一关系拼接的语句:{}", sql);
                            // 执行更新语句
                            int update = jdbcTemplate.update(sql);
                        }
                    }
                    break;
                case "2":
                    if (leftEntityEnName.equals(entityEnName) && (!leftEntityEnName.equals(rightEntityEnName))) {
                        data.remove(wlosEntityRelation.getRelationKey());
                        if (ObjectUtils.isNotEmpty(relationValues)) {
                            // 获取主键
                            String entityUuidValue = String.valueOf(data.get(leftPrimaryKey));
                            Assert.hasText(entityUuidValue, "新增实体主表主键为空");

                            // 执行update语句，建立关系
                            Map<String, Object> searchDict = new HashMap<>();
                            searchDict.put(rightPrimaryKey, relationValues);
                            String columnsValue = wlosEntityRelation.getRightRelationField() + "=" + "'" + entityUuidValue + "'";
                            String sql = update(rightEntity, columnsValue, searchDict);
                            log.info("更新关系拼接的语句:{}", sql);
                            int update = jdbcTemplate.update(sql);

                        }
                    }
                    break;
                case "3":
                    if (rightEntityEnName.equals(entityEnName)) {
                        // 兼容两个实体相同的情况
                        if (!leftEntityEnName.equals(rightEntityEnName)) {
                            data.remove(wlosEntityRelation.getRelationKey());
                            if (ObjectUtils.isNotEmpty(relationValues)) {
                                // 获取主键
                                String entityUuidValue = String.valueOf(data.get(rightPrimaryKey));
                                Assert.hasText(entityUuidValue, "新增实体主表主键为空");

                                // 执行update语句，建立关系
                                Map<String, Object> searchDict = new HashMap<>();
                                searchDict.put(leftPrimaryKey, relationValues);
                                String columnsValue = wlosEntityRelation.getLeftRelationField() + "=" + "'" + entityUuidValue + "'";
                                String sql = update(leftEntity, columnsValue, searchDict);
                                log.info("更新关系拼接的语句:{}", sql);
                                int update = jdbcTemplate.update(sql);
                            }
                        }
                    }
                    break;
                case "4":
                    String midEntityEnName = wlosEntityRelation.getMidEntityEnName();
                    if (StringUtils.isNotBlank(midEntityEnName)) {
                        data.remove(wlosEntityRelation.getRelationKey());
                        // 分别获取左表,右表 与中间表创建的一对多关系记录
                        EntityRelation wlosEntityRelation1 = getRelationByMiddleOrgin(leftEntityEnName, midEntityEnName, midEntityEnName);
                        EntityRelation wlosEntityRelation2 = getRelationByMiddleOrgin(rightEntityEnName, midEntityEnName, midEntityEnName);
                        Entity midEntity = getEntityByEnName(midEntityEnName);
                        Assert.notNull(midEntity, "[" + midEntityEnName + "]实体不存在");

                        String midPrimaryKey = getPrimaryKey(midEntityEnName);
                        Assert.hasText(midPrimaryKey, "[" + midEntityEnName + "]实体不存在");

                        // 存储中间表的数据
                        List<Map<String, Object>> midInsertData = new ArrayList<>();
                        if (entityEnName.equals(leftEntityEnName)) {
                            // 新增关系数据
                            for (String relationValue : relationValues) {
                                Map<String, Object> map = new HashMap<>();
                                map.put(midPrimaryKey, IdUtil.objectId());
                                map.put(wlosEntityRelation1.getRightRelationField(), data.get(leftPrimaryKey));
                                map.put(wlosEntityRelation2.getRightRelationField(), relationValue);
                                midInsertData.add(map);
                            }
                            if (midInsertData.size() > 0) {
                                insertDataByMysql(midEntityEnName, midInsertData);
                            }
                        } else {
                            // 新增关系数据
                            for (String relationValue : relationValues) {
                                Map<String, Object> map = new HashMap<>();
                                map.put(midPrimaryKey, IdUtil.objectId());
                                map.put(wlosEntityRelation2.getRightRelationField(), data.get(rightPrimaryKey));
                                map.put(wlosEntityRelation1.getRightRelationField(), relationValue);
                                midInsertData.add(map);
                            }
                            if (insertData.size() > 0) {
                                insertDataByMysql(midEntityEnName, midInsertData);
                            }
                        }
                    } else {
                        throw new BusinessException("修改多对多关系中间表不存在!");
                    }
                    break;
                default:
            }
        }
    }


    public void updateLookup(String entityEnName, EntityRelation wlosEntityRelation, Map<String, Object> updateData, Map<String, Object> primaryTable) {
        String leftEntityEnName = wlosEntityRelation.getLeftEntityUuid();
        String rightEntityEnName = wlosEntityRelation.getRightEntityUuid();
        String relationKey = wlosEntityRelation.getRelationKey();

        Assert.hasText(leftEntityEnName, "[" + relationKey + "]左关联表名为空");
        Assert.hasText(rightEntityEnName, "[" + relationKey + "]右关联表名为空");
        Entity leftEntity = getEntityByEnName(leftEntityEnName);
        Entity rightEntity = getEntityByEnName(rightEntityEnName);
        Assert.notNull(leftEntity, "[" + relationKey + "]实体不存在");
        Assert.notNull(rightEntity, "[" + relationKey + "]实体不存在");
        Assert.hasText(leftEntityEnName, "[" + relationKey + "]左关联表名为空");
        Assert.notNull(rightEntityEnName, "[" + relationKey + "]右关联表名为空");
        String leftPrimaryKey = getPrimaryKey(leftEntityEnName);
        String rightPrimaryKey = getPrimaryKey(rightEntityEnName);
        Assert.hasText(leftPrimaryKey, "[" + leftEntityEnName + "]主键不存在");
        Assert.hasText(rightPrimaryKey, "[" + rightEntityEnName + "]主键不存在");

        Object leftPrimaryKeyData = "";
        if (ObjectUtils.isNotEmpty(primaryTable)) {
            leftPrimaryKeyData = primaryTable.get(leftPrimaryKey);
        }
        Object rightPrimaryKeyData = "";
        if (ObjectUtils.isNotEmpty(primaryTable)) {
            rightPrimaryKeyData = primaryTable.get(rightPrimaryKey);
        }
        Object value = updateData.get(relationKey);
        // 查询关系详情
        List<String> relationKeys = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(value)) {
            if (value instanceof JSONArray) {
                relationKeys = JSONArray.parseArray(JSON.toJSONString(value), String.class);
            } else {
                List<String> asList = Arrays.asList(value.toString().split(","));
                relationKeys.addAll(asList);
            }
        }
        switch (wlosEntityRelation.getType()) {
            case "1":
                if (entityEnName.equals(leftEntityEnName)) {
                    // 右表执行两次update操作
                    Map<String, Object> whereDict = new HashMap<>();
                    whereDict.put(wlosEntityRelation.getRightRelationField(), String.valueOf(leftPrimaryKeyData));
                    String columnsValue = wlosEntityRelation.getRightRelationField() + "=" + "'" + "'";
                    String sql = update(rightEntity, columnsValue, whereDict);
                    log.info("更新关系拼接的语句:{}", sql);
                    jdbcTemplate.update(sql);
                    // 执行更新语句
                    Map<String, Object> whereDictSecond = new HashMap<>();
                    if (relationKeys.size() > 0) {
                        whereDictSecond.put(rightPrimaryKey, relationKeys.toArray(new String[]{}));
                        String columnValueSecond = wlosEntityRelation.getRightRelationField() + "=" + "'" + leftPrimaryKeyData + "'";
                        String sqlSecond = update(rightEntity, columnValueSecond, whereDictSecond);
                        log.info("更新关系拼接的语句Second:{}", sqlSecond);
                        jdbcTemplate.update(sqlSecond);
                    }
                } else {

                    // 执行update语句，建立关系
                    Map<String, Object> whereDictSecond = new HashMap<>();
                    if (relationKeys.size() > 0) {
                        whereDictSecond.put(rightPrimaryKey, relationKeys.toArray(new String[]{}));
                        String columnValueSecond = wlosEntityRelation.getRightRelationField() + "=" + "'" + leftPrimaryKeyData + "'";
                        String sqlSecond = update(rightEntity, columnValueSecond, whereDictSecond);
                        log.info("更新关系拼接的语句Second:{}", sqlSecond);
                        jdbcTemplate.update(sqlSecond);
                    } else {
                        String columnsValue = wlosEntityRelation.getRightRelationField() + "=" + "'" + "'";
                        String sql = update(rightEntity, columnsValue, primaryTable);
                        jdbcTemplate.update(sql);

                    }
                }
                break;
            case "2":
                // 对于一对多关系，如果是右多对多关系生成的与中间的一对一关系，则不处理（先如此处理，后续可以优化）
                if ((ObjectUtils.isEmpty(wlosEntityRelation.getOrigin())) || ((ObjectUtils.isNotEmpty(wlosEntityRelation.getOrigin())) && (!wlosEntityRelation.getOrigin().equals(leftEntityEnName)) && (!wlosEntityRelation.getOrigin().equals(rightEntityEnName)))) {
                    if (entityEnName.equals(leftEntityEnName)) {
                        // 右表执行两次update操作
                        Map<String, Object> whereDict = new HashMap<>();
                        whereDict.put(wlosEntityRelation.getRightRelationField(), String.valueOf(leftPrimaryKeyData));
                        String columnsValue = wlosEntityRelation.getRightRelationField() + "=" + "'" + "'";
                        String sql = update(rightEntity, columnsValue, whereDict);
                        log.info("更新关系拼接的语句:{}", sql);
                        jdbcTemplate.update(sql);
                        // 执行更新语句
                        Map<String, Object> whereDictSecond = new HashMap<>();
                        if (relationKeys.size() > 0) {
                            whereDictSecond.put(rightPrimaryKey, relationKeys.toArray(new String[]{}));
                            String columnValueSecond = wlosEntityRelation.getRightRelationField() + "=" + "'" + leftPrimaryKeyData + "'";
                            String sqlSecond = update(rightEntity, columnValueSecond, whereDictSecond);
                            log.info("更新关系拼接的语句Second:{}", sqlSecond);
                            jdbcTemplate.update(sqlSecond);
                        }
                    } else {
                        // 右表执行一次update操作
                        String sql;
                        if (relationKeys.size() > 0) {
                            String columnsValue = wlosEntityRelation.getRightRelationField() + "=" + "'" + relationKeys.get(0) + "'";
                            sql = update(rightEntity, columnsValue, primaryTable);
                        } else {
                            String columnsValue = wlosEntityRelation.getRightRelationField() + "=" + "'" + "'";
                            sql = update(rightEntity, columnsValue, primaryTable);
                        }
                        jdbcTemplate.update(sql);
                    }
                }
                break;
            case "3":
                if (entityEnName.equals(leftEntityEnName)) {
                    // 左表执行一次update操作
                    String sql = "";
                    if (relationKeys.size() > 0) {
                        String columnsValue = wlosEntityRelation.getLeftRelationField() + "=" + "'" + relationKeys.get(0) + "'";
                        sql = update(leftEntity, columnsValue, primaryTable);
                    } else {
                        String columnsValue = wlosEntityRelation.getLeftRelationField() + "=" + "'" + "'";
                        sql = update(leftEntity, columnsValue, primaryTable);
                    }
                    jdbcTemplate.update(sql);
                } else {
                    // 左表执行两次update操作
                    Map<String, Object> whereDict = new HashMap<>();
                    whereDict.put(wlosEntityRelation.getLeftRelationField(), String.valueOf(rightPrimaryKeyData));
                    String columnsValue = wlosEntityRelation.getLeftRelationField() + "=" + "'" + "'";
                    String sql = update(leftEntity, columnsValue, whereDict);
                    log.info("更新关系拼接的语句:{}", sql);
                    jdbcTemplate.update(sql);
                    // 再次执行
                    Map<String, Object> whereDictSecond = new HashMap<>();
                    if (relationKeys.size() > 0) {
                        whereDictSecond.put(leftPrimaryKey, relationKeys.toArray(new String[]{}));
                        String columnValueSecond = wlosEntityRelation.getLeftRelationField() + "=" + "'" + rightPrimaryKeyData + "'";
                        String sqlSecond = update(leftEntity, columnValueSecond, whereDictSecond);
                        log.info("更新关系拼接的语句Second:{}", sqlSecond);
                        jdbcTemplate.update(sqlSecond);
                    }
                }
                break;
            case "4":
                // 右表执行两次update操作
                String midEntityEnName = wlosEntityRelation.getMidEntityEnName();
                if (StringUtils.isNotBlank(midEntityEnName)) {
                    // 分别获取左表,右表 与中间表创建的一对多关系记录
                    EntityRelation wlosEntityRelation1 = getRelationByMiddleOrgin(leftEntityEnName, midEntityEnName, midEntityEnName);
                    EntityRelation wlosEntityRelation2 = getRelationByMiddleOrgin(rightEntityEnName, midEntityEnName, midEntityEnName);
                    // 对于中间表的关系数据先删除再新增
                    Entity midEntity = getEntityByEnName(midEntityEnName);
                    String midPrimaryKey = getPrimaryKey(midEntityEnName);
                    List<Map<String, Object>> insertData = new ArrayList<>();
                    List<String> relationValues;
                    if (updateData.get(relationKey) instanceof String) {
                        relationValues = Arrays.asList(updateData.get(relationKey).toString().split(","));
                    } else {
                        relationValues = JSONArray.parseArray(String.valueOf(updateData.get(relationKey)), String.class);
                    }
                    if (entityEnName.equals(leftEntityEnName)) {
                        Map<String, Object> whereDict = new HashMap<>();
                        whereDict.put(wlosEntityRelation1.getRightRelationField(), leftPrimaryKeyData);
                        String sql = delete(midEntity, whereDict);
                        jdbcTemplate.update(sql);
                        // 新增关系数据
                        for (String relationValue : relationValues) {
                            Map<String, Object> map = new HashMap<>();
                            map.put(midPrimaryKey, IdUtil.objectId());
                            map.put(wlosEntityRelation1.getRightRelationField(), leftPrimaryKeyData);
                            map.put(wlosEntityRelation2.getRightRelationField(), relationValue);
                            insertData.add(map);
                        }
                        if (insertData.size() > 0) {
                            insertDataByMysql(midEntityEnName, insertData);
                        }
                    } else {
                        Map<String, Object> whereDict = new HashMap<>();
                        whereDict.put(wlosEntityRelation2.getRightRelationField(), rightPrimaryKeyData);
                        String sql = delete(midEntity, whereDict);
                        jdbcTemplate.update(sql);
                        // 新增关系数据
                        for (String relationValue : relationValues) {
                            Map<String, Object> map = new HashMap<>();
                            map.put(midPrimaryKey, IdUtil.objectId());
                            map.put(wlosEntityRelation2.getRightRelationField(), rightPrimaryKeyData);
                            map.put(wlosEntityRelation1.getRightRelationField(), relationValue);
                            insertData.add(map);
                        }
                        if (insertData.size() > 0) {
                            insertDataByMysql(midEntityEnName, insertData);
                        }
                    }
                } else {
                    throw new BusinessException("修改多对多关系中间表不存在!");
                }
                break;
            default:
        }
    }


    public static String update(Entity entity, String columnsValue, Map<String, Object> where) {
        String tableName = getRealEnName(entity);
        StringBuffer sql = new StringBuffer();
        sql.append(" UPDATE " + "`" + tableName + "`" + " SET ");
        sql.append(columnsValue);
        if (ObjectUtils.isNotEmpty(where)) {
            sql.append(" WHERE ");
            String whereSql = conductWhere(where);
            sql.append(whereSql);
        }
        return sql.toString();
    }

    public static String delete(Entity wlosEntity, Map<String, Object> where) {
        String tableName = getRealEnName(wlosEntity);
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM " + "`" + tableName + "`");
        if (ObjectUtils.isNotEmpty(where)) {
            sql.append(" WHERE ");
            String whereSql = conductWhere(where);
            sql.append(whereSql);
        }
        return sql.toString();
    }

    /**
     * 构造where条件
     *
     * @param where where条件
     * @return sql
     */
    private static String conductWhere(Map<String, Object> where) {
        StringBuffer sql = new StringBuffer();
        if (where != null && where.size() > 0) {
            handleQuery(sql, where);
        }
        return sql.toString();
    }

    /***
     * 获取实体真实数据库表名
     * @return
     */
    public static String getRealEnName(Entity entity) {
        String tableName = entity.getEnName();
        if (null != entity.getDefineMode() && entity.getDefineMode().equals(2)) {
            tableName = tableName.replace(entity.getProjectPrefix() + "_", "");
        }
        return tableName;
    }

    /**
     * 处理where条件的生成
     */
    public static void handleQuery(StringBuffer sql, Map<String, Object> where) {
        Integer travelTimes = 0;
        for (Map.Entry<String, Object> me : where.entrySet()) {
            String columnName = me.getKey();
            // 如果某个字段值为Long[] 那么这个字段where使用in
            if (me.getValue() instanceof Long[] || me.getValue() instanceof Integer[] || me.getValue() instanceof String[]) {
                if (ObjectUtils.isNotEmpty(me.getValue())) {
                    Object[] value = (Object[]) me.getValue();
                    sql.append("`").append(columnName).append("`").append("  IN (");
                    for (int k = 0; k < value.length; k++) {
                        if (value[k] instanceof String) {
                            sql.append("'").append(value[k]).append("'");
                        } else {
                            sql.append(value[k]);
                        }
                        if (k < value.length - 1) {
                            sql.append(",");
                        }
                    }
                    sql.append(" ) ");
                    if (travelTimes < where.size() - 1) {
                        sql.append(" AND ");
                    }
                }
            } else if (me.getValue() instanceof Integer) {
                sql.append("`").append(columnName).append("`").append(" = ");
                sql.append(me.getValue());
                if (travelTimes < where.size() - 1) {
                    sql.append(" AND ");
                }
            } else if (me.getValue() instanceof ArrayList) {
                ArrayList arrayList = (ArrayList) me.getValue();
                sql.append("`").append(columnName).append("`").append("  IN (");
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i) instanceof String) {
                        sql.append("'").append(arrayList.get(i)).append("'");
                    } else {
                        sql.append(arrayList.get(i));
                    }
                    if (i < arrayList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(" ) ");
                if (travelTimes < where.size() - 1) {
                    sql.append(" AND ");
                }
            } else if (me.getValue() instanceof JSONArray) {
                JSONArray value = (JSONArray) me.getValue();
                sql.append("`").append(columnName).append("`").append("  IN (");
                for (int i = 0; i < value.size(); i++) {
                    if (value.get(i) instanceof String) {
                        sql.append("'").append(value.get(i)).append("'");
                    } else {
                        sql.append(value.get(i));
                    }
                    if (i < value.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(" ) ");
                if (travelTimes < where.size() - 1) {
                    sql.append(" AND ");
                }
            } else if (me.getValue() == null) {
                sql.append("`").append(columnName).append("`").append(" is null ");
                if (travelTimes < where.size() - 1) {
                    sql.append(" AND ");
                }
            } else {
                sql.append("`").append(columnName).append("`").append(" = ").append("'").append(me.getValue()).append("'");
                if (travelTimes < where.size() - 1) {
                    sql.append(" AND ");
                }
            }
            travelTimes += 1;
        }
    }

    public Entity getEntityByEnName(String enName) {
        LambdaQueryWrapper<Entity> entityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        entityLambdaQueryWrapper.eq(Entity::getEnName, enName);
        Entity entity = entityMapper.selectOne(entityLambdaQueryWrapper);
        if (ObjectUtils.isEmpty(entity)) {
            entity = new Entity();
        }
        return entity;
    }


    public EntityRelation getRelationByRelationKey(String relationKey) {
        LambdaQueryWrapper<EntityRelation> entityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        entityLambdaQueryWrapper.eq(EntityRelation::getRelationKey, relationKey);
        EntityRelation entityRelation = entityRelationEntityMapper.selectOne(entityLambdaQueryWrapper);
        if (ObjectUtils.isEmpty(entityRelation)) {
            entityRelation = new EntityRelation();
        }
        return entityRelation;
    }


    public EntityRelation getRelationByMiddleOrgin(String leftEnName, String rightEnName, String middleEnname) {
        LambdaQueryWrapper<EntityRelation> entityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        entityLambdaQueryWrapper.eq(EntityRelation::getLeftEntityUuid, leftEnName);
        entityLambdaQueryWrapper.eq(EntityRelation::getRightEntityUuid, rightEnName);
        entityLambdaQueryWrapper.eq(EntityRelation::getOrigin, middleEnname);
        EntityRelation entityRelation = entityRelationEntityMapper.selectOne(entityLambdaQueryWrapper);
        if (ObjectUtils.isEmpty(entityRelation)) {
            entityRelation = new EntityRelation();
        }
        return entityRelation;
    }



    

    private List<Map<String, Object>> transformDataToTree(List<Map<String, Object>> resultData, String lookupField) {
        // 存储父级数据ID
        List<String> parentDataIdList = new ArrayList<>();
        // 存储最终结果
        List<Map<String, Object>> result = new ArrayList<>();
        // 存储父级数据
        List<Map<String, Object>> parentDataList = new ArrayList<>();
        // 为每条数据添加一个children数组
        for (Map singleData : resultData) {
            singleData.put("children", new ArrayList());
            Object lookupFieldData = singleData.get(lookupField);
            if (ObjectUtils.isEmpty(lookupFieldData)) {
                parentDataList.add(singleData);
                parentDataIdList.add(singleData.get("id").toString());
            }
        }
        setChildrenData(parentDataList, resultData, lookupField);
        // 只返回父级数据
        for (Map lastData : resultData) {
            if (parentDataIdList.contains(lastData.get("id").toString())) {
                result.add(lastData);
            }
        }
        return result;
    }

    private void setChildrenData(List<Map<String, Object>> parentDataList, List<Map<String, Object>> resultData, String lookupField) {
        parentDataList.parallelStream().forEach(item -> {
            List childrenList = (List) item.get("children");
            List<Map<String, Object>> nextParentDataList = new ArrayList<>();
            String parentDataId = item.get("id").toString();
            for (Map singleData : resultData) {
                Object lookupFieldDataId = singleData.get(lookupField);
                if (lookupFieldDataId instanceof String) {
                    if (ObjectUtils.isNotEmpty(lookupFieldDataId)) {
                        if (lookupFieldDataId.equals(parentDataId)) {
                            childrenList.add(singleData);
                            nextParentDataList.add(singleData);
                        }
                    }
                } else if (lookupFieldDataId instanceof List) {
                    List newlookupFieldDataId = (List) lookupFieldDataId;

                    if (newlookupFieldDataId.size() > 0) {
                        Map lookupFieldData = (Map) newlookupFieldDataId.get(0);
                        String lookupFieldDataUuid = lookupFieldData.get("uuid").toString();
                        if (lookupFieldDataUuid.equals(parentDataId)) {
                            childrenList.add(singleData);
                            nextParentDataList.add(singleData);
                        }
                    }
                }

            }
            if (nextParentDataList.size() > 0) {
                setChildrenData(nextParentDataList, resultData, lookupField);
            }
        });
    }

}


