package cn.newtouch.fdpp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/1.
 */
public class ProcessUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessUtils.class);
    private static final ProcessBuilder pb = new ProcessBuilder();

    static {
        Map<String, String> env = pb.environment();
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            env.put(entry.getKey(), entry.getValue());
        }
    }

    private static String loadStream(InputStream s) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(s));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
            sb.append(line).append("\n");
        return sb.toString();
    }

    public static void execute(String path, String cmd) throws Exception {
        Process p = null;
        try {
            pb.command("/bin/sh", "-c", cmd);
            p = pb.start();
            String output = loadStream(p.getInputStream());
            LOGGER.debug(output);
            String error = loadStream(p.getErrorStream());
            throw new RuntimeException(error);
        } finally {
            p.destroy();
        }
    }

}
