package cn.newtouch.fdpp.console.service;

/**
 * Created by Administrator on 2016/11/4.
 */

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

public interface jsonService {

    public List<Map<String, Object>> query(String mapper, String method,
                                           Map<String, String> params, Map<String, String> dicts) throws Exception;
}
