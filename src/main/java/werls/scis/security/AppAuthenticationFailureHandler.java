package werls.scis.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.security
 * 
 * @Description: TODO
 * @date Date : 2020年02月22日 11:11
 */
@Component
public class AppAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * Called when an authentication attempt fails.
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        logger.info("登录失败");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put("code",401);
        if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
            map.put("message","用户名或密码错误");
        } else if (exception instanceof DisabledException) {
            map.put("message","账户被禁用");
        } else {
            map.put("message","登录失败!");
        }
        out.write(objectMapper.writeValueAsString(map));
        out.flush();
        out.close();
    }
}
