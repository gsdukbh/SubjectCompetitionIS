package werls.scis.security;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

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
public class AppAuthenticationSuccessHandler  extends SimpleUrlAuthenticationSuccessHandler {

}
