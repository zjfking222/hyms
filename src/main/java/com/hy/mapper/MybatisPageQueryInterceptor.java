package com.hy.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/1 9:09
 * @Description:拦截mybatis查询sql，替换其中的过滤条件，使之完整
 */
@Intercepts({@Signature(type= Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})})
@Component
@Configuration
public class MybatisPageQueryInterceptor implements Interceptor, HandlerInterceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] queryArgs = invocation.getArgs();
        BoundSql boundSql = (BoundSql)queryArgs[5];
        String sql = boundSql.getSql();
        //判断当前查询中是否存在过滤条件参数，若存在，则替换该参数
        if(StringUtils.isNotEmpty(sql) && sql.contains("filters = filters")){
            //拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
            Map<String, Object> map = (Map<String, Object>)boundSql.getParameterObject();
            String fParam = (String)map.get("filters");
            if(StringUtils.isNotEmpty(fParam)){
                //根据传入的参数生成完整的过滤条件
                String filters = this.parseFilters(fParam);
                //替换新的sql语句到mybatis中执行
                sql = sql.replace("filters = filters", filters);
                Field field = this.getField(boundSql, "sql");
                if (field != null) {
                    field.setAccessible(true);
                    field.set(boundSql, sql);
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        //拦截封装目标对象
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {}

    /**
     * @Author 钱敏杰
     * @Description 解析过滤条件语句，生成相应的sql查询条件
     * @Date 2019/1/11 9:13
     * @Param [filters]
     * @return java.lang.String
     *filters的json格式：
     *{"name":{
     * 	"logic":"and",
     * 	"filters":[
     * 		{"operator":"neq","type":"string","value":"1"},
     * 		{"operator":"neq","type":"string","value":"2"}]},
     * "code":{
     * 	"logic":"and",
     * 	"filters":[
     * 	{"operator":"neq","type":"string","value":"7"}]},
     * 	。。。
     * }
     **/
    private String parseFilters(String filters){
        StringBuffer returnStr = new StringBuffer();
        if(StringUtils.isNotEmpty(filters)){
            //解析json数据
            Map<String, Object> allMap = JSONObject.parseObject(filters, Map.class);
            if(allMap != null && allMap.size() >0){
                Set<String> keyList = allMap.keySet();
                Map<String, Object> map;
                String type;
                String logic;
                List<Object> filterList;
                Map<String, String> filterMap;
                StringBuffer filterStr;
                //循环所有字段
                Iterator<String> keys = keyList.iterator();
                int i = 0;
                while(keys.hasNext()) {
                    //字段名
                    String key = keys.next();
                    filterStr = new StringBuffer();
                    map = (Map<String, Object>) allMap.get(key);
                    //逻辑
                    logic = (String)map.get("logic");
                    //过滤条件
                    filterList = (List<Object>) map.get("filters");
                    if(filterList != null && filterList.size() >0){
                        //循环添加当前字段的所有查询条件
                        for(int j=0;j<filterList.size();j++){
                            filterMap = (Map<String, String>)filterList.get(j);
                            if(j > 0){//首语句不用加逻辑词
                                if("or".equals(logic)){
                                    filterStr.append(" or ");
                                }else{
                                    filterStr.append(" and ");
                                }
                            }
                            //类型
                            type = filterMap.get("type");
                            //添加单个条件
                            filterStr.append(this.getSqlCondition(type, key, filterMap.get("operator").toUpperCase(), filterMap.get("value")));
                        }
                        if(i > 0){//首语句不用加逻辑词
                            returnStr.append(" and ");
                        }
                        returnStr.append("(" + filterStr.toString() + ")");
                    }
                    i++;
                }
            }
        }
        return returnStr.toString();
    }

    /**
     * @Author 钱敏杰
     * @Description 利用反射根据字段名取对象
     * @Date 2019/1/10 16:21
     * @Param [obj, fieldName]
     * @return java.lang.reflect.Field
     **/
    private Field getField(Object obj, String fieldName) {
        Field field = null;
        for (Class<?> clazz=obj.getClass(); clazz != Object.class; clazz=clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                //这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
            }
        }
        return field;
    }

    /**
     * @Auther: 钱敏杰
     * @Date: 2019/1/10 9:09
     * @Description:字符串型枚举类枚举过滤条件类型及其属性
     */
    private enum StringOperator{
        //等于
        EQ("eq", " field='value' "),
        //不等于
        NEQ("neq", " (field !='value' or field is null) "),
        //开头是
        STARTSWITH("startswith", " field like 'value%' "),
        //包含
        CONTAINS("contains", " field like '%value%' "),
        //不含
        DOESNOTCONTAIN("doesnotcontain", " (field not like '%value%' or field is null) "),
        //结尾是
        ENDSWITH("endswith", " field like '%value' "),
        //空字符串
        ISEMPTY("isempty", " field is null "),
        //非空字符串
        ISNOTEMPTY("isnotempty", " field is not null "),
        //为空
        ISNULL("isnull", " field is null "),
        //非空
        ISNOTNULL("isnotnull", " field is not null "),
        //无值（实际kendogrid的无值指的是无数据，故查询数据为空，由于前台无查询条件，故此处完全无用，只做记录）
        ISNULLOREMPTY("isnullorempty", " 1 != 1 "),
        //有值（实际kendogrid的无值指的是有数据，故查询全部，由于前台无查询条件，故此处完全无用，只做记录）
        ISNOTNULLOREMPTY("isnotnullorempty", " 1=1 ");

        //名称
        private String name;
        //sql语句使用代码
        private String code;
        StringOperator(String name, String code){
            this.name = name;
            this.code = code;
        }
    }

    /**
     * @Auther: 钱敏杰
     * @Date: 2019/1/10 9:09
     * @Description:日期型枚举类枚举过滤条件类型及其属性
     */
    private enum DateOperator{
        //等于
        EQ("eq", " datediff (date_format(field, '%Y-%m-%d'),'value') = 0 "),
        //不等于
        NEQ("neq", " (datediff (date_format(field, '%Y-%m-%d'),'value') != 0 or field is null) "),
        //晚于等于
        GTE("gte", " (field >str_to_date('value', '%Y-%m-%d') or datediff (date_format(field, '%Y-%m-%d'),'value') = 0) "),
        //晚于
        GT("gt", " field>str_to_date('value', '%Y-%m-%d') "),
        //早于等于
        LTE("lte", " (field<str_to_date('value', '%Y-%m-%d') or (datediff (date_format(field, '%Y-%m-%d'),'value') = 0) "),
        //早于
        LT("lt", " field<str_to_date('value', '%Y-%m-%d') "),
        //为空
        ISNULL("isnull", " field is null "),
        //非空
        ISNOTNULL("isnotnull", " field is not null ");

        //名称
        private String name;
        //sql语句使用代码
        private String code;
        DateOperator(String name, String code){
            this.name = name;
            this.code = code;
        }
    }

    /**
     * @Auther: 钱敏杰
     * @Date: 2019/1/10 9:09
     * @Description:数字型枚举类枚举过滤条件类型及其属性
     */
    private enum NumberOperator{
        //等于
        EQ("eq", " field=value "),
        //不等于
        NEQ("neq", " (field !=value or field is null) "),
        //晚于等于
        GTE("gte", " field >=value "),
        //晚于
        GT("gt", " field>value "),
        //早于等于
        LTE("lte", " field<=value "),
        //早于
        LT("lt", " field<value "),
        //为空
        ISNULL("isnull", " field is null "),
        //非空
        ISNOTNULL("isnotnull", " field is not null ");

        //名称
        private String name;
        //sql语句使用代码
        private String code;
        NumberOperator(String name, String code){
            this.name = name;
            this.code = code;
        }
    }

    /**
     * @Auther: 钱敏杰
     * @Date: 2019/1/10 9:09
     * @Description:枚举型枚举类枚举过滤条件类型及其属性
     */
    private enum EnumsOperator{
        //等于
        EQ("eq", " field='value' "),
        //不等于
        NEQ("neq", " (field !='value' or field is null) "),
        //为空
        ISNULL("isnull", " field is null "),
        //非空
        ISNOTNULL("isnotnull", " field is not null ");

        //名称
        private String name;
        //sql语句使用代码
        private String code;
        EnumsOperator(String name, String code){
            this.name = name;
            this.code = code;
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 根据过滤操作枚举，返回条件sql语句
     * @Date 2019/1/10 16:22
     * @Param [field, name, value]
     * @return java.lang.String
     **/
    private String getSqlCondition(String type, String field, String name, String value){
        String sql = "";
        if("string".equals(type)){
            StringOperator operator = StringOperator.valueOf(name);
            sql = operator.code;
        }else if("date".equals(type)){
            DateOperator operator = DateOperator.valueOf(name);
            sql = operator.code;
        }else if("number".equals(type)){
            NumberOperator operator = NumberOperator.valueOf(name);
            sql = operator.code;
        }else if("enums".equals(type)){
            EnumsOperator operator = EnumsOperator.valueOf(name);
            sql = operator.code;
        }
        //替换字段名
        sql = sql.replaceAll("field", field);
        //替换值
        sql = sql.replaceAll("value", value);
        return sql;
    }
}
