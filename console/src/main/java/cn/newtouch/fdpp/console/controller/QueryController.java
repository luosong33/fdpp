package cn.newtouch.fdpp.console.controller;

import java.util.List;
import java.util.Map;

import cn.newtouch.fdpp.console.service.AutoService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private AutoService autoService;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "{mapper}/{method}", method = RequestMethod.POST)
    public void execute(@PathVariable("mapper") String mapper,
                        @PathVariable("method") String method,
                        @RequestParam("params") String params,
                        @RequestParam("dicts") String dicts,
                        HttpServletResponse response) throws Exception {
        List<Map<String, Object>> result = autoService.query(mapper, method,
                this.mapper.readValue(params, Map.class), this.mapper.readValue(dicts, Map.class));

        response.setContentType("text/html; charset=UTF-8");
        this.mapper.writeValue(response.getWriter(), result);
    }


}
