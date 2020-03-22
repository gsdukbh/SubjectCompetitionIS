package werls.scis.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * session
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.security
 * @Description: TODO
 * @date Date : 2020年02月22日 22:00
 */
@Component
public class AppExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put("status",0);
        map.put("message","已经在另一台机器登录，您被迫下线。" + event.getSessionInformation().getLastRequest());
        String s = objectMapper.writeValueAsString(map);
        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write(s);
    }
}
