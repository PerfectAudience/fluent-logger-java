package org.fluentd.logger;

import java.util.HashMap;
import java.util.Map;

public class FluentLoggerUtil {

    private static String RAW_KEY = "raw";

    public static Map<String, Object> getDataMapForRawEvent(String value) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(RAW_KEY, value);
        return data;
    }

}
