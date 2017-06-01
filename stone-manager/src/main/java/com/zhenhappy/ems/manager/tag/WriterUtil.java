package com.zhenhappy.ems.manager.tag;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wangxd on 2016/5/13.
 */
public class WriterUtil {

    public static void write(HttpServletResponse response, String obj) {
        try {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println(obj);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writePlain(HttpServletResponse response, String obj) {
        try {
            response.setContentType("text/plain; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println(obj);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
