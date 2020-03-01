package werls.scis.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功，根据角色进行分配路径
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.security
 * @Description: TODO
 * @date Date : 2020年02月21日 23:22
 */
@Component
public class AppAuthenticationSuccessHandler  implements AuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
     logger.info("登录成功,{}",authentication);

        System.out.println(Arrays.toString(request.getCookies()));
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",200);
        map.put("message","登录成功");
        map.put("data",authentication);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(objectMapper.writeValueAsString(map));
        out.flush();
        out.close();
    }
}
