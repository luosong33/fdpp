package cn.newtouch.fdpp.console.service;

import java.util.List;
import java.util.Map;

public interface AutoService {

    List<Map<String, Object>> query(String mapper, String method,
                                    Map<String, String> params, Map<String, String> dicts) throws Exception;

}
