package com.lvtulife.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/23 0023.
 */
public class SimpleDateFormatFactory {
    private static final Map<String, SimpleDateFormat> sdfMap = new HashMap<String, SimpleDateFormat>();

    public static SimpleDateFormat getInstance() {
        return getInstance(DateUtil.DATE_FORMAT);
    }

    public static synchronized SimpleDateFormat getInstance(String format) {
        SimpleDateFormat sdf = sdfMap.get(format);
        if (null == sdf) {
            sdf = new SimpleDateFormat(format);
            sdfMap.put(format, sdf);
        }
        return sdf;
    }
}
