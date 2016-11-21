package cn.newtouch.fdpp.console.mybatis;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/14.
 */
public class Tools {

    public Date now() {
        return new Date();
    }

    public String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public Date parse(String source, String pattern) throws Exception {
        return new SimpleDateFormat(pattern).parse(source);
    }

}
