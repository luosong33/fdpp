package cn.newtouch.fdpp.console.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public interface Mybatis {
	
	SqlSessionFactory buildFactory(String mapper) throws Exception;
	
	SqlSessionFactory rebuildFactory(String mapper) throws Exception;
	
	SqlSessionFactory getFactory(String mapper) throws Exception;
	
	SqlSession session(String mapper) throws Exception;
	
	<Bean> Bean queryObject(String mapper, String method, Object data) throws Exception;
	
	<Bean> List<Bean> queryObjectList(String mapper, String method, Object data) throws Exception;
	
	Map<String, Object> query(String mapper, String method, Object data) throws Exception;
	
	List<Map<String, Object>> queryList(String mapper, String method, Object data) throws Exception;
	
	int insert(String mapper, String method, Object data) throws Exception;
	
	int update(String mapper, String method, Object data) throws Exception;
	
	int delete(String mapper, String method, Object data) throws Exception;

}
