package werls.scis.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.util
 * @Description: TODO
 * @date Date : 2020年04月03日 22:39
 */
@Component
public class Tools {

    public Map<String, Object> getStringObjectMap(Map<String, Object> res, List<Map<String, Object>> ret, List<String> strings) {
        for (String string : strings) {
            Map<String, Object> type = new ConcurrentHashMap<>(1);
            if (string != null) {
                type.put("value", string);
            }
            ret.add(type);
        }
        res.put("data", ret);
        return res;
    }

}
