package cn.newtouch.fdpp.console.mybatis;

import java.sql.Connection;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import cn.newtouch.fdpp.console.cache.RedisCache;
import cn.newtouch.fdpp.console.utils.BeanUtils;
import cn.newtouch.fdpp.console.utils.CommitTag;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {
                MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {
                MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class})})
public class MybatisCacheInterceptor implements Interceptor {

    public static final Logger LOGGER = Logger.getLogger("sql");

    private static final String DEFAULT_USER = "APP_USER";

    private static final Pattern PATTERN = Pattern
            .compile("\\s+(?i)(from|join)\\s+(((\\w)+\\.)?(\\w)+)\\s+");

    private static final Pattern PATTERN_0 = Pattern
            .compile("(?i)(insert\\s+into|delete(\\s+from)?|update)\\s+(((\\w)+\\.)?(\\w)+)(\\(|\\s)*");

    private Properties properties;

    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        Configuration configuration = ms.getConfiguration();
        BoundSql boundSql = ms.getBoundSql(parameter);
        Object result = null;
        if (SqlCommandType.SELECT.equals(ms.getSqlCommandType())) {
            // 首先冲缓存中
          String query = getSql(configuration, boundSql);
            String querySHAHex = DigestUtils.shaHex(query);
            if (LOGGER.isDebugEnabled()) {
                Logger.getLogger(ms.getId()).debug(query);
            } 
            try {
                //如果是系统公告展示页面，从数据库查询
//                if (!ms.getId().equals("ManageContent.noticeboard")) {
//                    result = RedisCache.get(querySHAHex);
//                    if (result != null) {
//                        return result;
//                    }
//                    RedisCache.addLock(querySHAHex);
//                    result = RedisCache.get(querySHAHex);
//                    if (result != null) {
//                        RedisCache.releaseLock(querySHAHex);
//                        return result;
//                    }
//                }
                // 缓存中没有就执行查询
                if (CommitTag.need()) {
                    Executor executor = (Executor) invocation.getTarget();
                    Transaction transaction = executor.getTransaction();
                    DataSource dataSource = (DataSource) BeanUtils.get(
                            transaction, "dataSource");
                    Connection connection = DataSourceUtils
                            .getConnection(dataSource);
                    try {
                        BeanUtils.set(transaction, "connection", connection);
//                        connection.getClientInfo().list(System.out);
//                        connection.setClientInfo("II_TIMEZONE_NAME", "GMT8");
                        connection.setAutoCommit(false);
                        connection.commit();
                        result = invocation.proceed();
                    } finally {
                        BeanUtils.set(transaction, "connection", null);
                        DataSourceUtils.releaseConnection(connection,
                                dataSource);
                    }
                } else {
                    result = invocation.proceed();
                }
                // System.out.println(querySHAHex + "~~~~~~~~~~~~~~~query:"
                // + query);
                // System.out.println(querySHAHex + "~~~~~~~~~~~~~~~result:"
                // + result);
                Matcher matcher = PATTERN.matcher(boundSql.getSql());
                // 将查询语句中设计到的表都加入这个sql缓存的映�?
                List<String> tables = new ArrayList<String>();
                while (matcher.find()) {
                    String table = matcher.group(2).toUpperCase();
                    if (table.indexOf(".") == -1) {
                        table = DEFAULT_USER + '.' + table;
                    }
                    tables.add(table);
                }
//                RedisCache.set(querySHAHex, result, tables);
            } finally {
//                RedisCache.releaseLock(querySHAHex);
            }
        } else {
            if (LOGGER.isDebugEnabled()) {
                String sql = getSql(configuration, boundSql);
                Logger.getLogger(ms.getId()).debug(sql);
            }
            result = invocation.proceed();
            Matcher matcher = PATTERN_0.matcher(boundSql.getSql());
            if (matcher.find()) {
                // 将查询以外的sql涉及到的表的映射的缓存都清楚
                String table = matcher.group(3).toUpperCase();
                if (table.indexOf(".") == -1) {
                    table = DEFAULT_USER + '.' + table;
                }
//                RedisCache.clear(table);
            }
        }
        return result;
    }

    public static String getSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> pms = boundSql.getParameterMappings();
        StringBuilder builder = new StringBuilder(boundSql.getSql().replaceAll(
                "(\\s)+", " "));
        if (pms.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration
                    .getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                builder.append(',').append(toString(parameterObject));
            } else {
                MetaObject metaObject = configuration
                        .newMetaObject(parameterObject);
                for (ParameterMapping pm : pms) {
                    String propertyName = pm.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        builder.append(',').append(toString(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql
                                .getAdditionalParameter(propertyName);
                        builder.append(',').append(toString(obj));
                    }
                }
            }
        }
        return builder.toString();
    }

    private static String toString(Object obj) {
        if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(
                    DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            return formatter.format(obj);
        } else {
            return String.valueOf(obj);
        }
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties0) {
        this.properties = properties0;
        System.out.println(properties);
    }
}