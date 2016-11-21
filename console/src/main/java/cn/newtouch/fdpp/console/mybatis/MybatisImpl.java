package cn.newtouch.fdpp.console.mybatis;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class MybatisImpl implements Mybatis {

	private class Entity {

		long lastModified;

		SqlSessionFactory factory;

	}

	private ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

	private SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

	private Map<String, Entity> entityMap = new HashMap<String, Entity>();

	private Resource config;

	public Resource getConfig() {
		return config;
	}

	public void setConfig(Resource config) {
		this.config = config;
	}

	private String basepath;

	public String getBasepath() {
		return basepath;
	}

	public void setBasepath(String basepath) {
		if (!basepath.endsWith("/")) {
			basepath = basepath + '/';
		}
		this.basepath = basepath;
	}

	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private Resource getResource(String mapper) {
		return resolver.getResource(basepath + mapper + ".xml");
	}

	@Override
	public SqlSessionFactory buildFactory(String mapper) throws Exception {
		synchronized (config) {
			Entity entity = entityMap.get(mapper);
			if (entity == null) {
				try {
					System.out.println("创建mybatis工厂");
					entity = new Entity();
					entity.factory = builder.build(config.getInputStream());
					Resource resource = getResource(mapper);
					entity.lastModified = resource.lastModified();
					Configuration configuration = entity.factory
							.getConfiguration();
					Environment environment = new Environment(mapper,
							new SpringManagedTransactionFactory(), dataSource);
					configuration.setEnvironment(environment);
					XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(
							resource.getInputStream(), configuration,
							resource.toString(),
							configuration.getSqlFragments());
					xmlMapperBuilder.parse();
					entityMap.put(mapper, entity);
				} finally {
					ErrorContext.instance().reset();
				}
			}
			return entity.factory;
		}
	}

	@Override
	public SqlSessionFactory rebuildFactory(String mapper) throws Exception {
		synchronized (config) {
			entityMap.remove(mapper);
			return buildFactory(mapper);
		}
	}

	@Override
	public SqlSessionFactory getFactory(String mapper) throws Exception {
		Entity entity = entityMap.get(mapper);
		if (entity == null) {
			return buildFactory(mapper);
		}
		Resource resource = getResource(mapper);
		if (entity.lastModified < resource.lastModified()) {
			return rebuildFactory(mapper);
		}
		return entity.factory;
	}

	@Override
	public SqlSession session(String mapper) throws Exception {
		return getFactory(mapper).openSession();
	}
	
	@Override
	public <Bean> Bean queryObject(String mapper,//
			String method, Object data) throws Exception {
		if (data instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) data;
			map.put("sysdate", new Timestamp(System.currentTimeMillis()));
		}
		return session(mapper).selectOne(mapper + '.' + method, data);
	}
	
	@Override
	public <Bean> List<Bean> queryObjectList(String mapper, //
			String method, Object data) throws Exception {
		if (data instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) data;
			map.put("sysdate", new Timestamp(System.currentTimeMillis()));
		}
		return session(mapper).selectList(mapper + '.' + method, data);
	}

	@Override
	public Map<String, Object> query(String mapper,//
			String method, Object data) throws Exception {
		if (data instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) data;
			map.put("sysdate", new Timestamp(System.currentTimeMillis()));
		}
		return session(mapper).selectOne(mapper + '.' + method, data);
	}

	@Override
	public List<Map<String, Object>> queryList(String mapper, //
			String method, Object data) throws Exception {
		if (data instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) data;
			map.put("sysdate", new Timestamp(System.currentTimeMillis()));
			/*SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.sss");
			map.put("sysdate", "2016-11-01 "+sdf.format(System.currentTimeMillis()));*/
		}
		return session(mapper).selectList(mapper + '.' + method, data);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(String mapper,//
			String method, Object data) throws Exception {
		if (data instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) data;
			map.put("sysdate", new Timestamp(System.currentTimeMillis()));
		}
		return session(mapper).insert(mapper + '.' + method, data);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int update(String mapper, //
			String method, Object data) throws Exception {
		if (data instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) data;
			map.put("sysdate", new Timestamp(System.currentTimeMillis()));
		}
		return session(mapper).update(mapper + '.' + method, data);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int delete(String mapper,//
			String method, Object data) throws Exception {
		if (data instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) data;
			map.put("sysdate", new Timestamp(System.currentTimeMillis()));
		}
		return session(mapper).delete(mapper + '.' + method, data);
	}

}
