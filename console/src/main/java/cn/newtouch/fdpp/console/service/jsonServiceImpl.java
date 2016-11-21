package cn.newtouch.fdpp.console.service;

import cn.newtouch.fdpp.console.utils.CommitTag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.newtouch.fdpp.console.mybatis.Mybatis;
import cn.newtouch.fdpp.console.utils.CommitTag;
import cn.newtouch.fdpp.console.utils.DictUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/11/4.
 */
@Service("jsonService")
@Transactional(readOnly = true)
public class jsonServiceImpl implements jsonService {
    @Autowired
    private Mybatis mybatis;
    public List<Map<String, Object>> query(String mapper, String method,
                                           Map<String, String> params, Map<String, String> dicts) throws Exception {
        try {
            CommitTag.set(true);
            List<Map<String, Object>> list = mybatis.queryList(mapper, method, params);

            return list;
        } finally {
            CommitTag.set(false);
        }
    }
    private Map<String, String> getDict(String type) throws Exception {
        Map<String, String> dict = DictUtils.get(type);
        if (dict == null) {
            dict = new HashMap<>();
            List<Map<String, Object>> list = mybatis.queryList("code", "query", type);
            for (Map<String, Object> map : list) {
                dict.put((String) map.get("code"), (String) map.get("name"));
            }
            DictUtils.put(type, dict);
        }
        return dict;
    }
}
