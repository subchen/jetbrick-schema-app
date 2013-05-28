package jetbrick.schema.app.modal.methods;

import com.alibaba.fastjson.JSON;

public class JsonUtils {

    public static String toJSON(Object object) {
        return JSON.toJSONString(object);
    }
}
