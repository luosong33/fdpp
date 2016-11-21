package cn.newtouch.fdpp.console.service;

import java.util.ArrayList;
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

@Service("autoService")
@Transactional(readOnly = true)
public class AutoServiceImpl implements AutoService {

    @Autowired
    private Mybatis mybatis;

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

    public List<Map<String, Object>> query(String mapper, String method,
                                           Map<String, String> params, Map<String, String> dicts) throws Exception {
        try {
            CommitTag.set(true);
            List<Map<String, Object>> list = mybatis.queryList(mapper, method, params);

            if ("findBaofeiInfo".equals(method) || "findXzbfInfo".equals(method)
                    || "car_findBaofeiInfo".equals(method) || "biznocar_findBaofeiInfo".equals(method)
                    || "other_findBaofeiInfo".equals(method)) {
                list = this.findBaofeiInfo(list);
            } else if ("findjgInfo".equals(method)) {
                /*list = this.findjgInfo(list);*/
                return list;
            } else {
                if (list != null && !list.isEmpty() && dicts != null && !dicts.isEmpty()) {
                    for (Entry<String, String> entry : dicts.entrySet()) {
                        String value = entry.getValue();
                        String[] values = value.split("@");
                        String alias = values[0];
                        String type = values[1];
                        Map<String, String> dict = getDict(type);
                        for (Map<String, Object> map : list) {
                            Object code = map.get(entry.getKey());
                            String name = dict.get(code.toString());
                            map.put(alias, name);
                        }
                    }
                }

            }
            for (Map<String, Object> m : list) {
                for (String k : m.keySet()) {
                    System.out.println("AutoServiceImpl====================" + k + " : " + m.get(k));
                }
            }
            return list;
        } finally {
            CommitTag.set(false);
        }
    }

    private List<Map<String, Object>> findBaofeiInfo(List<Map<String, Object>> list) throws Exception {
        String time = "";
        String data1 = "";
        String data2 = "";
        String data5 = "";
        String data30 = "";
        for (Map<String, Object> map : list) {
            time += String.valueOf(map.get("time") == null ? "0" : map.get("time")) + ",";
            data1 += String.valueOf(map.get("data1") == null ? "0" : map.get("data1")) + ",";
            double data20 = Double.valueOf(String.valueOf(map.get("data2") == null ? "0" : map.get("data2")));
            double data3 = Double.valueOf(String.valueOf(map.get("data3") == null ? "0" : map.get("data3")));
            data2 += "[" + (data3 - data20) + "," + data3 + "," + (data3 - data20) + "," + data3 + "]" + ",";
            data5 += String.valueOf(map.get("data5") == null ? "0" : map.get("data5")) + ",";
            data30 += String.valueOf(map.get("data2") == null ? "0" : map.get("data2")) + ",";
        }
        if (!"".equals(time)) {
            time = time.substring(0, time.length() - 1);
            data1 = data1.substring(0, data1.length() - 1);
            data2 = data2.substring(0, data2.length() - 1);
            data30 = data30.substring(0, data30.length() - 1);
            data5 = data5.substring(0, data5.length() - 1);
        }
//        System.out.println("time===================="+time+"====================");

        Map maptemp = new HashMap();
        maptemp.put("time", time);
        maptemp.put("data1", data1);
        maptemp.put("data2", data2);
        maptemp.put("data3", data30);
        maptemp.put("data5", data5);
        List<Map<String, Object>> lis = new ArrayList<Map<String, Object>>();
        lis.add(maptemp);
        return lis;
    }

    public List<Map<String, Object>> findjgInfo(List<Map<String, Object>> list) throws Exception {

        String provincname = "";
        String dsignpremium = "";
        String dsignqty = "";

        for (Map<String, Object> map : list) {
            provincname += String.valueOf(map.get("provincname") == null ? "0" : map.get("provincname")) + ",";
            dsignpremium += String.valueOf(map.get("dsignpremium") == null ? "0" : map.get("dsignpremium")) + ",";
            dsignqty += String.valueOf(map.get("dsignqty") == null ? "0" : map.get("dsignqty")) + ",";
        }

        provincname = provincname.substring(0, provincname.length() - 1);
        dsignpremium = dsignpremium.substring(0, dsignpremium.length() - 1);
        dsignqty = dsignqty.substring(0, dsignqty.length() - 1);

        Map maptemp = new HashMap();
        maptemp.put("provincname", provincname);
        maptemp.put("dsignpremium", dsignpremium);
        maptemp.put("dsignqty", dsignqty);
        List<Map<String, Object>> lis = new ArrayList<Map<String, Object>>();
        lis.add(maptemp);
        return lis;
    }

}
