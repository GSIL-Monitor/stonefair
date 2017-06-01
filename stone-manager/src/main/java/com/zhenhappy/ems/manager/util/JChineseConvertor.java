package com.zhenhappy.ems.manager.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/18.
 */
public class JChineseConvertor {
    private Map<Character, Character> ts;
    private Map<Character, Character> st;
    private static JChineseConvertor convertor;

    public static JChineseConvertor getInstance() throws IOException {
        if(convertor == null) {
            convertor = new JChineseConvertor();
        }

        return convertor;
    }

    public String t2s(String s) {
        char[] cs = new char[s.length()];

        for(int i = 0; i < s.length(); ++i) {
            cs[i] = this.t2s(s.charAt(i)).charValue();
        }

        return new String(cs);
    }

    public String s2t(String s) {
        char[] cs = new char[s.length()];

        for(int i = 0; i < s.length(); ++i) {
            cs[i] = this.s2t(s.charAt(i)).charValue();
        }

        return new String(cs);
    }

    public Character t2s(char c) {
        return this.ts.get(Character.valueOf(c)) == null?Character.valueOf(c):(Character)this.ts.get(Character.valueOf(c));
    }

    public Character s2t(char c) {
        return this.st.get(Character.valueOf(c)) == null?Character.valueOf(c):(Character)this.st.get(Character.valueOf(c));
    }

    private List<Character> loadTable() throws IOException {
        List cs = this.loadChar("/ts.tab", "UTF-8");
        if(cs.size() % 2 != 0) {
            throw new RuntimeException("The conversion table may be damaged or not exists");
        } else {
            return cs;
        }
    }

    private JChineseConvertor() throws IOException {
        List cs = this.loadTable();
        this.ts = new HashMap();
        this.st = new HashMap();

        for(int i = 0; i < cs.size(); i += 2) {
            this.ts.put((Character)cs.get(i), (Character)cs.get(i + 1));
            this.st.put((Character)cs.get(i + 1), (Character)cs.get(i));
        }

    }

    private List<Character> loadChar(String file, String charset) throws IOException {
        ArrayList content = new ArrayList();
        BufferedReader in = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file), charset));

        int c;
        while((c = in.read()) != -1) {
            content.add(Character.valueOf((char)c));
        }

        in.close();
        return content;
    }
}
