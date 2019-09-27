package com.lypgod.demo.dtx.txmsg.bank1.util;

import com.alibaba.fastjson.JSONObject;
import com.lypgod.demo.dtx.txmsg.bank1.util.message.AccountChangeEvent;
import org.springframework.stereotype.Component;

/**
 * @author lypgod
 */
@Component
public class JsonUtils {
    private static String JSON_OBJECT_KEY = "accountChange";

    public String toJsonString(AccountChangeEvent accountChangeEvent) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_OBJECT_KEY, accountChangeEvent);
        return jsonObject.toJSONString();
    }

    public AccountChangeEvent parseAccountChangeEvent(String source) {
        JSONObject jsonObject = JSONObject.parseObject(source);
        String accountChangeString = jsonObject.getString(JSON_OBJECT_KEY);
        return JSONObject.parseObject(accountChangeString, AccountChangeEvent.class);
    }
}
