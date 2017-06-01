package com.zhenhappy.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangxd on 2016/4/6.
 */
public class EmailPattern {

    public EmailPattern() {

    }

    public boolean isEmailPattern(String str) {
        /*boolean flag = false;
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
        Matcher m = p.matcher(str);
        flag = m.matches();*/

        String emailStr = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(emailStr);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("(^13\\d{9}$)|(^14)[5,7]\\d{8}$|(^15[0,1,2,3,5,6,7,8,9]\\d{8}$)|(^17)[6,7,8]\\d{8}$|(^18\\d{9}$)");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
