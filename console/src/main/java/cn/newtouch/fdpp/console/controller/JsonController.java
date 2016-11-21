package cn.newtouch.fdpp.console.controller;

import cn.newtouch.fdpp.console.service.jsonService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/4.
 */
@RequestMapping("/json")
public class JsonController {


    @Autowired
    private jsonService jsonService;

    private ObjectMapper mapper = new ObjectMapper();
/*
    @RequestMapping(value = "json/findBaofeiInfo", method = RequestMethod.GET)
    public void findBaofeiInfo(@PathVariable("mapper") String mapper,
                        @PathVariable("method") String method,
                        @RequestParam("params") String params,
                        @RequestParam("dicts") String dicts,
                        HttpServletResponse response) throws Exception {
        List<Map<String, Object>> result = jsonService.query(mapper, method,
                this.mapper.readValue(params, Map.class), this.mapper.readValue(dicts, Map.class));

        response.setContentType("text/html; charset=UTF-8");
        this.mapper.writeValue(response.getWriter(), result);
    }*/
    @RequestMapping(value = "{mapper}/{method}", method = RequestMethod.GET)
    public void execute(@PathVariable("mapper") String mapper,
                               @PathVariable("method") String method,
                               @RequestParam("params") String params,
                               @RequestParam("dicts") String dicts,
                               HttpServletResponse response) throws Exception {
        List<Map<String, Object>> result = jsonService.query(mapper, method,
                this.mapper.readValue(params, Map.class), this.mapper.readValue(dicts, Map.class));

        response.setContentType("text/html; charset=UTF-8");
        this.mapper.writeValue(response.getWriter(), result);
    }
}
